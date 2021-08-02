package io.dentall.totoro.business.service.nhi.util;

import io.dentall.totoro.business.service.ImageGcsBusinessService;
import io.dentall.totoro.business.service.NhiRuleCheckInfoType;
import io.dentall.totoro.business.service.NhiRuleCheckSourceType;
import io.dentall.totoro.business.service.nhi.*;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckBody;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckTxSnapshot;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.NhiMonthDeclarationRuleCheckReport;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.enumeration.BackupFileCatalog;
import io.dentall.totoro.domain.enumeration.BatchStatus;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.dto.table.DisposalTable;
import io.dentall.totoro.service.dto.table.NhiExtendDisposalTable;
import io.dentall.totoro.service.dto.table.PatientTable;
import io.dentall.totoro.service.mapper.DisposalMapper;
import io.dentall.totoro.service.mapper.NhiExtendDisposalMapper;
import io.dentall.totoro.service.mapper.PatientMapper;
import io.dentall.totoro.service.util.CsvUtil;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.micrometer.core.annotation.Timed;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 共用的 rule check 邏輯會整合在這裡。
 * naming rule,
 * - find*: 表示會查詢 db 且回傳應有資料
 */
@Service
public class NhiRuleCheckUtil {

    private final ApplicationContext applicationContext;

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    private final PatientRepository patientRepository;

    private final NhiExtendDisposalMapper nhiExtendDisposalMapper;

    private final DisposalRepository disposalRepository;

    private final NhiMonthDeclarationRuleCheckReportRepository nhiMonthDeclarationRuleCheckReportRepository;

    public NhiRuleCheckUtil(
        ApplicationContext applicationContext,
        NhiExtendDisposalRepository nhiExtendDisposalRepository,
        NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository,
        PatientRepository patientRepository,
        NhiExtendDisposalMapper nhiExtendDisposalMapper,
        DisposalRepository disposalRepository,
        NhiMonthDeclarationRuleCheckReportRepository nhiMonthDeclarationRuleCheckReportRepository
    ) {
        this.applicationContext = applicationContext;
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.nhiExtendTreatmentProcedureRepository = nhiExtendTreatmentProcedureRepository;
        this.patientRepository = patientRepository;
        this.nhiExtendDisposalMapper = nhiExtendDisposalMapper;
        this.disposalRepository = disposalRepository;
        this.nhiMonthDeclarationRuleCheckReportRepository = nhiMonthDeclarationRuleCheckReportRepository;
    }

    public NhiRuleCheckResultVM dispatch(
        String code,
        NhiRuleCheckBody body
    ) throws NoSuchFieldException,
        NoSuchMethodException,
        InvocationTargetException,
        IllegalAccessException {
        // 轉換至統一入口 Object
        NhiRuleCheckDTO dto = this.convertVmToDto(code, body);

        // 分派到號碼群的腳本當中
        NhiRuleCheckScriptType scriptType =
            Arrays.stream(NhiRuleCheckScriptType.values())
                .filter(e -> e.getRegex().matcher(code).matches())
                .findFirst()
                .orElseThrow(NoSuchFieldException::new);

        Object scriptBean = applicationContext.getBean(scriptType.getScriptClass());

        // 並在該腳本中找到對應函式
        NhiRuleCheckResultVM rvm = (NhiRuleCheckResultVM) scriptBean
            .getClass()
            .getMethod("validate".concat(code), NhiRuleCheckDTO.class)
            .invoke(scriptBean, dto);

        return rvm;
    }

    /**
     * 解析 健保代碼 若是區間型態，則切分並產生，包含頭、尾、區間三個部位全部的代碼，目前僅支援
     * [number-low][single-alphabet]~[number-high][single-alphabet] 這種格式，且 [single-alphabet] 需相同，且 [single-alphabet] 相同
     * 若不符合此項規則，則不分割，直接回傳。
     * e.g.
     * 91001C~91020C 會被展開成 91001C, 91002C, 91003C, ..., 91020C
     * ["91001C~91003C", "ABCD123"] => ["91001C", "91002C", "91003C", "ABCD123"]
     *
     * @param nhiCodes 健保代碼，或健保代碼區間
     * @return string list of 健保代碼，健保代碼區間也會被切割產生單一健保代碼
     */
    public List<String> parseNhiCode(List<String> nhiCodes) {
        List<String> result = new ArrayList<>();

        if (nhiCodes == null ||
            nhiCodes.isEmpty()
        ) {
            return result;
        }

        for (String code : nhiCodes) {
            try {
                String[] tildeCodes = code.split("~");
                if (tildeCodes.length != 2) {
                    result.add(code);
                    continue;
                }

                Pattern pattern = Pattern.compile("(^([1-9][0-9]{0,4})([A-Z])$|^([1-9][0-9]{0,5})$)");

                Matcher lowCodeMatcher = pattern.matcher(tildeCodes[0]);
                Matcher highCodeMatcher = pattern.matcher(tildeCodes[1]);

                if (lowCodeMatcher.matches() &&
                    highCodeMatcher.matches() &&
                    lowCodeMatcher.group(2) != null &&
                    lowCodeMatcher.group(3) != null &&
                    highCodeMatcher.group(2) != null &&
                    highCodeMatcher.group(3) != null
                ) {
                    Integer lowCodeNumber = Integer.parseInt(lowCodeMatcher.group(2));
                    String lowCodeAlpha = lowCodeMatcher.group(3);
                    Integer highCodeNumber = Integer.parseInt(highCodeMatcher.group(2));
                    String highCodeAlpha = highCodeMatcher.group(3);

                    if (lowCodeAlpha.equals(highCodeAlpha) &&
                        lowCodeNumber < highCodeNumber
                    ) {
                        for (int i = lowCodeNumber; i <= highCodeNumber; i++) {
                            result.add(String.valueOf(i).concat(lowCodeAlpha));
                        }
                    } else {
                        result.add(code);
                    }

                } else if (lowCodeMatcher.matches() &&
                    highCodeMatcher.matches() &&
                    lowCodeMatcher.group(4) != null &&
                    highCodeMatcher.group(4) != null
                ) {
                    Integer lowCodeNumber = Integer.parseInt(lowCodeMatcher.group(4));
                    Integer highCodeNumber = Integer.parseInt(highCodeMatcher.group(4));

                    for (int i = lowCodeNumber; i <= highCodeNumber; i++) {
                        result.add(String.valueOf(i));
                    }

                } else {
                    result.add(code);
                    continue;
                }


            } catch (NumberFormatException e) {
                // do nothing
            }
        }

        return result;
    }

    /**
     * 用來把數個後端檢核結果總結，並以前端所需格式輸出。你會想知道應用上每個塞入的 vm 都是依樣是為何？
     * 因為要保持 domain, vm, dto 的 POJO 特性。 Why POJO ？ 這類資料可能會塞入其它套件應用，
     * 此外也容易遺忘複查 get/set 裡面的邏輯，為了避免 vm, dto 太過複雜，
     * 應把轉換邏輯提出，且保持 get/set。
     *
     * @param dto 後端檢驗後的結果
     * @param vm  前端檢驗後的結果
     */
    public void addResultToVm(@NotNull NhiRuleCheckResultDTO dto, @NotNull NhiRuleCheckResultVM vm) {

        vm.getCheckHistory().add(dto);

        vm.setValidated(
            vm.isValidated() && dto.isValidated()
        );

        if (StringUtils.isNotBlank(dto.getMessage())) {
            vm.getMessages().add(dto.getMessage());
        }

    }

    public void addResultToVm(@NotNull List<NhiRuleCheckResultDTO> dtos, @NotNull NhiRuleCheckResultVM vm) {
        for (NhiRuleCheckResultDTO d : dtos) {
            this.addResultToVm(d, vm);
        }
    }

    /**
     * 作用是轉化 body 取得到的值，檢核、查詢對應 Nhi Disposal, Nhi Treatment Procedure, Patient 資料，以利後續功能使用。
     * 此 method 使用情境有二
     * 1. 診療項目 尚未被產生，需要預先進行確認，所需資料會用到 patientId, a71, a73, a74, a75，a71, a73
     * 這邊會自動計算帶入；a73 則是打 api 時就會認定想驗證的目標在 api path。
     * 2. 診療項目 已被產生，需帶入 patientId, treatmentProcedureId(treatmentProcedureId === nhiExtendTreatmentProcedureId),
     * a74, a75，若檢查結果不同則以新帶入的欄位為檢驗項目
     * 並查詢取得對應資料。
     *
     * @param code 來源於 api path，及其預計想檢核的目標
     * @param body 來自於前端的輸入
     * @return 後續檢核統一 `輸入` 的介面
     */
    public NhiRuleCheckDTO convertVmToDto(
        @NotNull String code,
        @NotNull NhiRuleCheckBody body
    ) {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();
        HashMap<Long, NhiExtendTreatmentProcedure> oldNetpMap = new HashMap<>();
        ArrayList<Long> excludeTreatmentProcedureIds = new ArrayList<>();
        ArrayList<String> includeNhiCode = new ArrayList<>();

        // Assign patient
        if (body.getPatientId() != null) {
            Optional<PatientTable> optionalPt = patientRepository.findPatientById(body.getPatientId());
            if (optionalPt.isPresent()) {
                dto.setPatient(PatientMapper.patientTableToPatient(optionalPt.get()));
            } else {
                dto.setPatient(new Patient());
            }
        }

        // Assign nhi extend disposal
        if (body.getDisposalId() != null) {
            Optional<DisposalTable> optionalDt = disposalRepository.findDisposalById(body.getDisposalId());
            if (optionalDt.isPresent()) {
                /**
                 * 為了可以偵測為儲存的資料用，效能非常差，所以在月申報檢核時必須跳過。
                 */
                if (body.getSourceData() == null ||
                    body.getSourceData().size() == 0
                ) {
                    nhiExtendTreatmentProcedureRepository.findNhiExtendTreatmentProcedureByTreatmentProcedure_Disposal_Id(optionalDt.get().getId())
                        .forEach(netp -> oldNetpMap.put(netp.getId(), netp));
                }

                List<NhiExtendDisposalTable> nedts =
                    nhiExtendDisposalRepository.findByDisposal_IdOrderByIdDesc(optionalDt.get().getId(), NhiExtendDisposalTable.class).stream().collect(Collectors.toList());
                if (nedts != null &&
                    nedts.size() > 0
                ) {
                    NhiExtendDisposal ned = nhiExtendDisposalMapper.nhiExtendDisposalTableToNhiExtendDisposal(
                        nedts.get(0)
                    );
                    dto.setNhiExtendDisposal(
                        ned
                    );

                }
            }

            if (dto.getNhiExtendDisposal() == null) {
                NhiExtendDisposal ned = new NhiExtendDisposal();
                if (optionalDt.isPresent()) {
                    ned.setDisposal(
                        DisposalMapper.disposalTableToDisposal(optionalDt.get())
                    );
                }
                ned.setA17(
                    body.getDisposalTime()
                );
                dto.setNhiExtendDisposal(ned);
            }

            dto.getNhiExtendDisposal().setA23(body.getNhiCategory());

            List<Long> excludeDisposalIdsAndCurrentDisposalId = null;
            if (body.getExcludeDisposalIds() != null) {
                excludeDisposalIdsAndCurrentDisposalId = new ArrayList<>(body.getExcludeDisposalIds());
            } else {
               excludeDisposalIdsAndCurrentDisposalId = new ArrayList<>();
            }
            excludeDisposalIdsAndCurrentDisposalId.add(
                body.getDisposalId() != null
                    ? body.getDisposalId()
                    : 0L
            );
            dto.setExcludeDisposalIds(excludeDisposalIdsAndCurrentDisposalId);
        } else {
            NhiExtendDisposal ned = new NhiExtendDisposal();
            ned.setA17(
                body.getDisposalTime()
            );
            dto.setNhiExtendDisposal(ned);
            dto.setExcludeDisposalIds(body.getExcludeDisposalIds());
        }

        // Assign nhi treatment procedure a.k.a target treatment procedure
        if (body.getTxSnapshots() != null &&
            body.getTxSnapshots().size() > 0
        ) {
            body.getTxSnapshots().stream()
                .filter(Objects::nonNull)
                .forEach(txSnapshot -> {
                    // General
                    includeNhiCode.add(txSnapshot.getNhiCode());
                    // Update
                    if (txSnapshot.getId() != null &&
                        oldNetpMap.containsKey(txSnapshot.getId()) &&
                        !txSnapshot.equalsNhiExtendTreatmentProcedure(oldNetpMap.get(txSnapshot.getId()))
                    ) {
                        excludeTreatmentProcedureIds.add(txSnapshot.getId());
                    }
                    // Delete
                    if (txSnapshot.getId() != null &&
                        !oldNetpMap.containsKey(txSnapshot.getId())
                    ) {
                        excludeTreatmentProcedureIds.add(txSnapshot.getId());
                    }

                    // Check if it is belong to target nhi code
                    if (code.equals(txSnapshot.getNhiCode())) {
                        NhiExtendTreatmentProcedure netp = new NhiExtendTreatmentProcedure()
                            .a71(body.getDisposalTime())
                            .a73(txSnapshot.getNhiCode())
                            .a74(txSnapshot.getTeeth())
                            .a75(txSnapshot.getSurface());

                        dto.setNhiExtendTreatmentProcedure(netp);

                        if (txSnapshot.getId() != null &&
                            oldNetpMap.containsKey(txSnapshot.getId())
                        ) {
                            dto.getNhiExtendTreatmentProcedure().setId(txSnapshot.getId());
                            excludeTreatmentProcedureIds.add(txSnapshot.getId());
                        }
                    }
                });
            dto.setTxSnapshots(body.getTxSnapshots());
        }

        if (dto.getTxSnapshots() == null) {
            dto.setTxSnapshots(new ArrayList());
        }

        dto.setIncludeNhiCodes(includeNhiCode);
        if (body.getDoctorId() != null) {
            dto.setDoctorId(body.getDoctorId());
        }

        // Assign source data
        dto.setSourceData(body.getSourceData());

        return dto;
    }

    @Transactional
    @Timed
    public NhiMonthDeclarationRuleCheckReport createMonthDeclarationRuleCheckReportStatus(String yearMonthString) {
        NhiMonthDeclarationRuleCheckReport report = new NhiMonthDeclarationRuleCheckReport();
        report.setStatus(BatchStatus.LOCK);
        report.setYearMonth(yearMonthString);
        report.setComment("");
        return nhiMonthDeclarationRuleCheckReportRepository.save(report);
    }

    @Transactional
    @Timed
    public NhiMonthDeclarationRuleCheckReport updateMonthDeclarationRuleCheckReportStatus(
        Long id,
        BatchStatus status,
        String url
    ) {
        NhiMonthDeclarationRuleCheckReport result = null;
        Optional<NhiMonthDeclarationRuleCheckReport> report = nhiMonthDeclarationRuleCheckReportRepository.findById(id);
        if (report.isPresent()) {
            result = report.get();
            result.setStatus(status);
            result.setComment(url);
        }

        return result;
    }

    @Transactional
    @Timed
    public List<NhiMonthDeclarationRuleCheckReport> getMonthDeclarationRuleCheckReport(String yearMonthString) {
        return this.nhiMonthDeclarationRuleCheckReportRepository.findByYearMonthEquals(yearMonthString);
    }

    public synchronized String generateMonthDeclarationRuleCheckReport(
        Integer partialACDateTime,
        List<Long> excludeDisposals,
        ImageGcsBusinessService imageGcsBusinessService
    ) {
        List<String> csvRecords = new ArrayList<>();
        List<Long> keiesOrderByDisposalDateTime = new ArrayList<>();
        List<String> errorReport = new ArrayList<>();
        String fileUrl = "";

        // Csv header
        csvRecords.add(
            CsvUtil.convertDataToCsvRecord(
                Arrays.asList(
                    "編號",
                    "就醫治療時間",
                    "病患姓名",
                    "醫師姓名",
                    "內容"
                )
            )
        );

        // Csv records
        Map<Long, List<NhiRuleCheckMonthDeclarationTx>> disposalTx = nhiExtendTreatmentProcedureRepository.findNhiMonthDeclarationTx(
            String.valueOf(partialACDateTime).concat("%"),
            excludeDisposals != null &&
                excludeDisposals.size() > 0
                ? excludeDisposals
                : Arrays.asList(0L)
        ).stream()
            .map(d -> {
                if (!keiesOrderByDisposalDateTime.contains(d.getDisposalId())) {
                    keiesOrderByDisposalDateTime.add(d.getDisposalId());
                }
                return new NhiRuleCheckMonthDeclarationTx(
                    d.getDisposalId(),
                    d.getDisposalTime(),
                    d.getNhiCategory(),
                    d.getDoctorId(),
                    d.getDoctorName(),
                    d.getPatientId(),
                    d.getPatientName(),
                    d.getTreatmentProcedureId(),
                    d.getNhiCode(),
                    d.getTeeth(),
                    d.getSurface()
                );
            })
            .collect(Collectors.groupingBy(d -> d.getDisposalId()));

        int seq = 1;
        for (Long key : keiesOrderByDisposalDateTime) {
            List<NhiRuleCheckMonthDeclarationTx> vs = disposalTx.get(key);

            List<NhiRuleCheckTxSnapshot> txSnapshots = new ArrayList<>();
            NhiRuleCheckBody body = new NhiRuleCheckBody();
            boolean isFirst = true;
            String disposalTime = "";
            String patientName = "";
            String doctorName = "";
            String checkedMessage = "";
            Long patientId = 0L;

            // Assemble NRC body
            for (NhiRuleCheckMonthDeclarationTx v : vs) {
                if (isFirst) {
                    body = NhiRuleCheckMapper.INSTANCE.convertToNhiRuleCheckBody(v);
                    patientId = v.getPatientId();
                    disposalTime = v.getDisposalTime();
                    patientName = v.getPatientName();
                    doctorName = v.getDoctorName();
                    isFirst = false;
                }
                txSnapshots.add(
                    NhiRuleCheckMapper.INSTANCE.convertToNhiRuleCheckTxSnapshot(v)
                );
            }

            List<Long> excludeDisposalIdsAndSelfDisposalId = new ArrayList<>(excludeDisposals);
            excludeDisposalIdsAndSelfDisposalId.add(key);

            List<NhiHybridRecordDTO> sourceData = this.findNhiHypeRecordsDTO(
                patientId,
                excludeDisposalIdsAndSelfDisposalId
            );

            body.setTxSnapshots(txSnapshots);
            body.setSourceData(sourceData);
            body.setExcludeDisposalIds(excludeDisposalIdsAndSelfDisposalId);

            // Operate NRC logic for current disposal's treatment procedures
            for (NhiRuleCheckMonthDeclarationTx v : vs) {
                try {
                    NhiRuleCheckResultVM vm = this.dispatch(v.getNhiCode(), body);
                    List<String> distinctMessage = new ArrayList<>();
                    for (String vmMsg : vm.getMessages()) {
                        if (!checkedMessage.contains(vmMsg)) {
                            distinctMessage.add(vmMsg);
                        }
                    }
                    checkedMessage = checkedMessage
                        .concat(
                            String.join(
                                "\n",
                                distinctMessage
                            )
                        )
                        .concat("\n");
                }catch (Exception e) {
                    errorReport.add(
                        String.valueOf(
                            v.toString()
                        )
                        .concat(" :")
                        .concat(
                            e.getMessage() == null
                                ? ""
                                : e.getMessage()
                        )
                    );
                }

            }

            csvRecords.add(
                CsvUtil.convertDataToCsvRecord(
                    Arrays.asList(
                        String.valueOf(seq),
                        DateTimeUtil.transformA71ToDisplayWithTime(
                            disposalTime
                        ),
                        patientName,
                        doctorName,
                        "\"".concat(
                            checkedMessage
                        )
                            .concat(
                                "\""
                            )
                    )
                )
            );
            seq++;
        }

        try {
            String fileName = Instant.now().toString()
                .concat(".csv");
            imageGcsBusinessService.uploadFile(
                    imageGcsBusinessService.getClinicName()
                    .concat("/")
                    .concat(BackupFileCatalog.MONTH_DECLARE_RULE_CHECK_REPORT.getRemotePath())
                    .concat("/"),
                fileName,
                CsvUtil.convertCsvStringToInputStream(csvRecords),
                "text/csv"
            );
            fileUrl = imageGcsBusinessService.getUrlForDownload()
                .concat(imageGcsBusinessService.getClinicName())
                .concat("/")
                .concat(BackupFileCatalog.MONTH_DECLARE_RULE_CHECK_REPORT.getRemotePath())
                .concat("/")
                .concat(fileName);
        } catch (Exception e) {
            errorReport.add(
                "Something go wrong when upload file to GCP"
                    .concat(" :")
                    .concat(
                        e.getMessage()
                    )
            );
        }

        return fileUrl;
    }

    /**
     * 病患 是否在 診療 當下年紀 < 12 歲
     *
     * @param dto 使用 patient.birth, nhiExtendTreatmentProcedure.A71
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO lessThanAge12(@NotNull NhiRuleCheckDTO dto) {

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("病患是否在診療當下年紀小於 12 歲")
            .validated(true);

        if (dto.getPatient().getBirth() == null) {
            result.validated(false);
        } else {
            Period p = Period.between(
                dto.getPatient().getBirth(),
                DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()));

            result.validated(p.getYears() < 12);
        }

        if (!result.isValidated()) {
            result
                .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                .message(
                    String.format(
                        NhiRuleCheckFormat.D3_1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        "未滿十二歲"
                    )
                );
        }

        return result;
    }

    /**
     * 病患 是否在 診療 當下年紀 >= 6 歲
     *
     * @param dto 使用 patient.birth, nhiExtendTreatmentProcedure.A71
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO lessThanAge6(@NotNull NhiRuleCheckDTO dto) {

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("病患是否在診療當下年紀小於 6 歲")
            .validated(true);

        if (dto.getPatient().getBirth() == null) {
            result.validated(false);
        } else {
            Period p = Period.between(
                dto.getPatient().getBirth(),
                DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()));
            result.validated(p.getYears() < 6);
        }

        if (!result.isValidated()) {
            result
                .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                .message(
                    String.format(
                        NhiRuleCheckFormat.D3_1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        "未滿六歲"
                    )
                );
        }

        return result;
    }

    /**
     * 病患 是否在 診療 當下年紀 <= 30 歲
     *
     * @param dto 使用 patient.birth, nhiExtendTreatmentProcedure.A71
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO moreThanOrEqualToAge30(@NotNull NhiRuleCheckDTO dto) {

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("病患是否在診療當下年紀大於 30 歲")
            .validated(true);

        if (dto.getPatient().getBirth() == null) {
            result.validated(false);
        } else {
            Period p = Period.between(
                dto.getPatient().getBirth(),
                DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()));
            result.validated(p.getYears() >= 30);
        }

        if (!result.isValidated()) {
            result
                .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                .message(
                    String.format(
                        NhiRuleCheckFormat.D3_1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        "年滿三十歲"
                    )
                );
        }

        return result;
    }

    /**
     * 病患 是否在 診療 當下年紀 >= 18 and < 30 歲
     *
     * @param dto 使用 patient.birth, nhiExtendTreatmentProcedure.A71
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO moreOrEqualToAge18AndLessThan30(@NotNull NhiRuleCheckDTO dto) {

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("病患是否在診療當下年紀大於等於 18 小於 30 歲")
            .validated(true);

        if (dto.getPatient().getBirth() == null) {
            result.validated(false);
        } else {
            Period p = Period.between(
                dto.getPatient().getBirth(),
                DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()));
            result.validated(p.getYears() >= 18 && p.getYears() < 30);
        }

        if (!result.isValidated()) {
            result
                .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                .message(
                    String.format(
                        NhiRuleCheckFormat.D3_1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        "年滿十八歲且未滿三十歲"
                    )
                );
        }

        return result;
    }

    // 找該病患所有混合資料，並排除指定 codes, disposal id
    public List<NhiHybridRecordDTO> findNhiHypeRecordsDTO(
        Long patientId,
        List<String> codes,
        List<Long> excludeDisposalIds
    ) {
        return nhiExtendDisposalRepository.findNhiHybridRecord(
            patientId,
            codes,
            excludeDisposalIds
        ).stream()
            .map(
                nhr -> new NhiHybridRecordDTO(
                    nhr.getRecordSource(),
                    nhr.getDisposalId(),
                    nhr.getDoctorId(),
                    nhr.getRecordDateTime(),
                    nhr.getCode(),
                    nhr.getTooth(),
                    nhr.getSurface()
                )
            )
            .collect(Collectors.toList());
    }

    // 找該病患所有混合資料，並排除指定 disposal id
    public List<NhiHybridRecordDTO> findNhiHypeRecordsDTO(
        Long patientId,
        List<Long> excludeDisposalIds
    ) {
        return nhiExtendDisposalRepository.findNhiHybridRecord(
            patientId,
            excludeDisposalIds
        ).stream()
            .map(
                nhr -> new NhiHybridRecordDTO(
                    nhr.getRecordSource(),
                    nhr.getDisposalId(),
                    nhr.getDoctorId(),
                    nhr.getRecordDateTime(),
                    nhr.getCode(),
                    nhr.getTooth(),
                    nhr.getSurface()
                )
            )
            .collect(Collectors.toList());
    }

    /**
     * 回訊息作為提醒用，檢核狀況算審核通過
     *
     * @param message 作為提醒用訊息
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO addNotification(String message) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.INFO)
            .validateTitle("回訊息作為提醒用，檢核狀況算審核通過")
            .validated(true)
            .message(message);

        return result;
    }

    /**
     * 限制牙面在 isAllLimitedSurface 以下
     *
     * @param dto 使用 nhiExtendTreatmentProcedure.a75
     * @param sc  牙面限制
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isAllLimitedSurface(
        NhiRuleCheckDTO dto,
        SurfaceConstraint sc
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validated(true)
            .validateTitle("限制牙面在 isAllLimitedSurface 以下");

        switch (sc) {
            case MIN_1_SURFACES:
                result.validated(
                        dto.getNhiExtendTreatmentProcedure().getA75() != null &&
                        dto.getNhiExtendTreatmentProcedure().getA75().length() >= SurfaceConstraint.MIN_1_SURFACES.getLimitNumber()
                    );

                break;
            case MIN_2_SURFACES:
                result.validated(
                        dto.getNhiExtendTreatmentProcedure().getA75() != null &&
                        dto.getNhiExtendTreatmentProcedure().getA75().length() >= SurfaceConstraint.MIN_2_SURFACES.getLimitNumber()
                    );

                break;
            case MIN_3_SURFACES:
                result.validated(
                        dto.getNhiExtendTreatmentProcedure().getA75() != null &&
                        dto.getNhiExtendTreatmentProcedure().getA75().length() >= SurfaceConstraint.MIN_3_SURFACES.getLimitNumber()
                    );

                break;
            case MAX_2_SURFACES:
                result.validated(
                        dto.getNhiExtendTreatmentProcedure().getA75() == null ||
                        dto.getNhiExtendTreatmentProcedure().getA75().length() <= SurfaceConstraint.MAX_2_SURFACES.getLimitNumber()
                    );

                break;
            case MAX_3_SURFACES:
                result.validated(
                        dto.getNhiExtendTreatmentProcedure().getA75() == null ||
                        dto.getNhiExtendTreatmentProcedure().getA75().length() <= SurfaceConstraint.MAX_3_SURFACES.getLimitNumber()
                    );

                break;
            case MUST_HAVE_M_D_O:
                result.validated(
                        dto.getNhiExtendTreatmentProcedure().getA75() != null &&
                        dto.getNhiExtendTreatmentProcedure().getA75().matches(SurfaceConstraint.MUST_HAVE_M_D_O.getLimitRegex())
                    );

                break;
            case EQUAL_4_SURFACES:
                result.validated(
                        dto.getNhiExtendTreatmentProcedure().getA75() != null &&
                        dto.getNhiExtendTreatmentProcedure().getA75().length() == SurfaceConstraint.EQUAL_4_SURFACES.getLimitNumber()
                    );

                break;
            default:
                break;
        }

        if (!result.isValidated()) {
            result.nhiRuleCheckInfoType(NhiRuleCheckInfoType.WARNING)
                .message(
                    this.getCurrentTxNhiCode(dto)
                        .concat(": ")
                        .concat(
                            sc.getErrorMessage()
                        )
                );
        }

        return result;
    }

    /**
     * 傳入 a74 自動切分為單牙，不可為空，並依照 給定的 ToothConstraint 來判定是否為核可牙位
     *
     * @param dto 使用 nhiExtendTreatmentProcedure.a74
     * @param tc  提供例如 前牙限定、後牙限定、FM限定⋯⋯等 regex
     * @return
     */
    public NhiRuleCheckResultDTO isAllLimitedTooth(
        NhiRuleCheckDTO dto,
        ToothConstraint tc
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("傳入 a74 自動切分為單牙，不可為空，並依照 給定的 ToothConstraint 來判定是否為核可牙位")
            .validated(true);

        result.validated(
            StringUtils.isNotBlank(dto.getNhiExtendTreatmentProcedure().getA74()) &&
                !ToothUtil.splitA74(dto.getNhiExtendTreatmentProcedure().getA74())
                    .stream()
                    .anyMatch(tooth -> !ToothUtil.validatedToothConstraint(tc, tooth))
        );

        if (!result.isValidated()) {
            result
                .nhiRuleCheckInfoType(NhiRuleCheckInfoType.WARNING)
                .message(
                    dto.getNhiExtendTreatmentProcedure().getA73()
                        .concat(
                            ": "
                        )
                        .concat(
                            ToothUtil.getToothConstraintsFailureMessage(
                                tc
                            )
                        )
                );
        }

        return result;
    }

    /**
     * 適用於，81, etc,. 這類非常規時間區間，事先在檢查前，算出最大間隔區間
     * example,
     * 7/15 --limitationMonth is 3 months--> 4/1, 6/30
     *
     * @return
     */
    public LocalDateDuration specialMonthDurationCalculation(NhiRuleCheckDTO dto, long limitationMonth) {
        LocalDate begin = LocalDate.now();
        LocalDate end = begin;

        LocalDate currentTxDate = this.getNhiExtendDisposalDateInDTO(dto);
        begin = currentTxDate.withDayOfMonth(1).minusMonths(limitationMonth - 1);
        end = currentTxDate.withDayOfMonth(currentTxDate.lengthOfMonth());

        return new LocalDateDuration()
            .begin(begin)
            .end(end);
    }

    public LocalDateDuration regularDayDurationCalculation(NhiRuleCheckDTO dto, Period days) {
        LocalDate begin = LocalDate.now();
        LocalDate end = begin;

        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());
        begin = currentTxDate.minus(days);
        end = currentTxDate;

        return new LocalDateDuration()
            .begin(begin)
            .end(end);
    }

    public NhiRuleCheckResultDTO isCodeBeforeDateV2(
        NhiRuleCheckDTO dto,
        NhiRuleCheckSourceType onlySourceType,
        List<String> originCodes,
        LocalDateDuration duration,
        String limitDisplayDuration,
        int limitTimes,
        NhiRuleCheckFormat format
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("指定 patient id, codes, 並排除 disposal")
            .validated(true);

        List<String> codes = this.parseNhiCode(originCodes);
        List<NhiRuleCheckTxSnapshot> currentDisposalMatches = new ArrayList<>();

        // 當前 disposal
        if (dto.getIncludeNhiCodes() != null &&
            dto.getIncludeNhiCodes().size() > 0
        ) {
            List<NhiRuleCheckTxSnapshot> ignoreTargetTxSnapshots = this.getIgnoreTargetTxSnapshots(dto);
            currentDisposalMatches = ignoreTargetTxSnapshots.stream()
                .filter(d -> codes.contains(d.getNhiCode()))
                .collect(Collectors.toList());

            if (currentDisposalMatches.size() >= limitTimes) {
                String m = this.generateErrorMessage(
                    format,
                    NhiRuleCheckSourceType.CURRENT_DISPOSAL,
                    dto.getNhiExtendTreatmentProcedure().getA73(),
                    currentDisposalMatches.get(0).getNhiCode(),
                    this.getNhiExtendDisposalDateInDTO(dto),
                    limitDisplayDuration,
                    null,
                    null
                );

                return result
                    .validated(false)
                    .nhiRuleCheckInfoType(format.getLevel())
                    .message(m);
            }
        }

        // 其他處置
        List<NhiHybridRecordDTO> sourceData = originCodes != null
            ? this.findNhiHypeRecordsDTO(
                    dto.getPatient().getId(),
                    codes,
                    Arrays.asList(
                        dto.getNhiExtendDisposal() != null &&
                            dto.getNhiExtendDisposal().getDisposal() != null &&
                            dto.getNhiExtendDisposal().getDisposal().getId() != null
                                ? dto.getNhiExtendDisposal().getDisposal().getId()
                                : 0L
                    )
                )
            : this.findNhiHypeRecordsDTO(
                    dto.getPatient().getId(),
                    Arrays.asList(
                        dto.getNhiExtendDisposal() != null &&
                            dto.getNhiExtendDisposal().getDisposal() != null &&
                            dto.getNhiExtendDisposal().getDisposal().getId() != null
                            ? dto.getNhiExtendDisposal().getDisposal().getId()
                            : 0L
                    )
                );

        if (NhiRuleCheckSourceType.SYSTEM_RECORD.equals(onlySourceType)) {
            sourceData = sourceData.stream()
                .filter(d -> "SYS".equals(d.getRecordSource()))
                .collect(Collectors.toList());
        }

        List<NhiHybridRecordDTO> matches = duration != null
            ? sourceData.stream()
                .filter(
                    d -> d.getRecordDateTime().isAfter(duration.getBegin()) && d.getRecordDateTime().isBefore(duration.getEnd()) ||
                        d.getRecordDateTime().isEqual(duration.getBegin()) ||
                        d.getRecordDateTime().isEqual(duration.getEnd())
                )
                .collect(Collectors.toList())
            : sourceData;

        if (matches != null &&
            matches.size() + currentDisposalMatches.size() >= limitTimes
        ) {
            if (currentDisposalMatches.size() == 0) {

                NhiRuleCheckSourceType matchedSourceType = NhiRuleCheckSourceType.SYSTEM_RECORD;
                NhiHybridRecordDTO latestNhiHybridRecord = matches.get(0);
                if (
                    "IC".equals(latestNhiHybridRecord.getRecordSource())
                ) {
                    matchedSourceType = NhiRuleCheckSourceType.NHI_CARD_RECORD;
                } else if (
                    latestNhiHybridRecord.getRecordDateTime().isEqual(
                        DateTimeUtil.transformROCDateToLocalDate(
                            dto.getNhiExtendTreatmentProcedure().getA71()
                        )
                    )
                ) {
                    matchedSourceType = NhiRuleCheckSourceType.TODAY_OTHER_DISPOSAL;
                }

                String m = this.generateErrorMessage(
                    format,
                    matchedSourceType,
                    dto.getNhiExtendTreatmentProcedure().getA73(),
                    latestNhiHybridRecord.getCode(),
                    latestNhiHybridRecord.getRecordDateTime(),
                    limitDisplayDuration,
                    null,
                    null
                );

                result
                    .validated(false)
                    .nhiRuleCheckInfoType(format.getLevel())
                    .message(m);
            } else {
                String m = this.generateErrorMessage(
                    format,
                    NhiRuleCheckSourceType.CURRENT_DISPOSAL,
                    dto.getNhiExtendTreatmentProcedure().getA73(),
                    currentDisposalMatches.get(0).getNhiCode(),
                    this.getNhiExtendDisposalDateInDTO(dto),
                    limitDisplayDuration,
                    null,
                    null
                );

                result
                    .validated(false)
                    .nhiRuleCheckInfoType(format.getLevel())
                    .message(m);
            }
        }

        return result;
    }

    /**
     * For 00316C, 01272C
     * @param dto
     * @return
     */
    public List<NhiRuleCheckResultDTO> isSpecialRuleForXray(NhiRuleCheckDTO dto) {
        List<NhiRuleCheckResultDTO> results = new ArrayList<>();
        String title = "Special rule for Xray";

        List<NhiRuleCheckTxSnapshot> ignoreTargetTxSnapshots = this.getIgnoreTargetTxSnapshots(dto);
        NhiRuleCheckTxSnapshot currentMatchTargetCode = null;

        for (NhiRuleCheckTxSnapshot ignoreTargetTxSnapshot : ignoreTargetTxSnapshots) {
            if (this.getCurrentTxNhiCode(dto).equals(ignoreTargetTxSnapshot.getNhiCode())) {
                currentMatchTargetCode = ignoreTargetTxSnapshot;
                break;
            }
        }

        List<NhiHybridRecordDTO> sourceData = dto.getSourceData() != null && dto.getSourceData().size() > 0
            ? dto.getSourceData()
            : this.findNhiHypeRecordsDTO(
            dto.getPatient().getId(),
            Arrays.asList(
                this.getDisposalIdInDTO(dto)
            )
        );

        List<NhiHybridRecordDTO> foundAnyInPast365Days = getSourceDataByDuration(
            sourceData,
            this.regularDayDurationCalculation(
                dto,
                DateTimeUtil.NHI_365_DAY
            )
        );

        List<NhiHybridRecordDTO> foundAnyTargetCodeInPast545Days = getSourceDataByDuration(
            sourceData,
            this.regularDayDurationCalculation(
                dto,
                DateTimeUtil.NHI_545_DAY
            )
        ).stream()
            .filter(d -> this.getCurrentTxNhiCode(dto).equals(d.getCode()))
            .collect(Collectors.toList());

        if (foundAnyInPast365Days.size() != 0) {
            if (currentMatchTargetCode != null ||
                foundAnyTargetCodeInPast545Days.size() != 0
            ) {
                NhiRuleCheckResultDTO r1 = new NhiRuleCheckResultDTO()
                    .validateTitle(title)
                    .validated(false)
                    .nhiRuleCheckInfoType(NhiRuleCheckFormat.D1_5.getLevel())
                    .message(
                        String.format(
                            NhiRuleCheckFormat.D1_5.getFormat(),
                            dto.getNhiExtendTreatmentProcedure().getA73(),
                            String.valueOf(DateTimeUtil.NHI_365_DAY.getDays())
                        )
                    );
                NhiRuleCheckResultDTO r2 = new NhiRuleCheckResultDTO()
                    .validateTitle(title)
                    .validated(false)
                    .nhiRuleCheckInfoType(NhiRuleCheckFormat.D4_1.getLevel())
                    .message(
                        this.generateErrorMessage(
                            NhiRuleCheckFormat.D4_1,
                            currentMatchTargetCode != null
                                ? NhiRuleCheckSourceType.CURRENT_DISPOSAL
                                : this.getSourceDataType(
                                    dto,
                                foundAnyTargetCodeInPast545Days.get(0)
                                ),
                            dto.getNhiExtendTreatmentProcedure().getA73(),
                            this.getCurrentTxNhiCode(dto),
                            currentMatchTargetCode != null
                                ? this.getNhiExtendDisposalDateInDTO(dto)
                                : foundAnyTargetCodeInPast545Days.get(0).getRecordDateTime(),
                            String.valueOf(DateTimeUtil.NHI_545_DAY.getDays()),
                            null,
                            null
                        )
                    );

                results.add(r1);
                results.add(r2);
            }
        }

        return results;
    }

    /**
     * 89XXX special: 前30天內不得有89006C，但如果這中間有90001C, 90002C, 90003C, 90019C, 90020C則例外
     *
     * @param dto patient.id, netp.id, excludeTreamentProcedureId
     * @return
     */
    public List<NhiRuleCheckResultDTO> isSpecialRuleFor89XXXC(NhiRuleCheckDTO dto) {
        List<NhiRuleCheckResultDTO> result = new ArrayList<>();

        List<String> queryCodes = Arrays.asList("89006C", "90001C", "90002C", "90003C", "90019C", "90020C");
        List<String> mustIncludeCodes = Arrays.asList("90001C", "90002C", "90003C", "90019C", "90020C");

        List<NhiHybridRecordDTO> sourceData = dto.getSourceData() != null && dto.getSourceData().size() > 0
            ? dto.getSourceData().stream().filter(d -> queryCodes.contains(d.getCode())).collect(Collectors.toList())
            : this.findNhiHypeRecordsDTO(
            dto.getPatient().getId(),
            queryCodes,
            Arrays.asList(
                this.getDisposalIdInDTO(dto)
            )
        );

        LocalDate currentDate = DateTimeUtil.transformROCDateToLocalDate(
            dto.getNhiExtendTreatmentProcedure().getA71()
        );

        Map<String, NhiHybridRecordDTO> detected89006CToothTxMap = new HashMap<>();
        for (NhiHybridRecordDTO d : sourceData) {
            List<String> duplicatedTooth = ToothUtil.listDuplicatedTooth(
                d.getTooth(),
                dto.getNhiExtendTreatmentProcedure().getA74()
            );
            duplicatedTooth.stream().forEach(t -> {
                if (d.getRecordDateTime().plus(DateTimeUtil.NHI_30_DAY.getDays(), ChronoUnit.DAYS).isEqual(currentDate) ||
                    d.getRecordDateTime().plus(DateTimeUtil.NHI_30_DAY.getDays(), ChronoUnit.DAYS).isAfter(currentDate)
                ) {
                    if (!detected89006CToothTxMap.containsKey(t)) {
                        detected89006CToothTxMap.put(t, d);
                    }
                }
            });
        }

        if (detected89006CToothTxMap.size() > 0) {
            for (NhiHybridRecordDTO data : sourceData) {
                if (mustIncludeCodes.contains(data.getCode())) {
                    ToothUtil.splitA74(data.getTooth()).forEach(t -> {
                        if (detected89006CToothTxMap.containsKey(t)) {
                            if (data.getRecordDateTime().isAfter(detected89006CToothTxMap.get(t).getRecordDateTime()) &&
                                data.getRecordDateTime().isBefore(this.getNhiExtendDisposalDateInDTO(dto)) ||
                                data.getRecordDateTime().isEqual(this.getNhiExtendDisposalDateInDTO(dto))
                            ) {
                                detected89006CToothTxMap.remove(t);
                            }
                        }
                    });
                }
            }

            if (detected89006CToothTxMap.size() > 0) {
                detected89006CToothTxMap.forEach((k, v) -> {
                    NhiRuleCheckSourceType sourceType;
                    if (v.getDisposalId() != null &&
                        v.getDisposalId().equals(this.getDisposalIdInDTO(dto))
                    ) {
                        sourceType = NhiRuleCheckSourceType.CURRENT_DISPOSAL;
                    } else if ("SYS".equals(v.getRecordSource()) &&
                        v.getRecordDateTime().isEqual(this.getNhiExtendDisposalDateInDTO(dto))
                    ) {
                        sourceType = NhiRuleCheckSourceType.TODAY_OTHER_DISPOSAL;
                    } else if ("SYS".equals(v.getRecordSource())
                    ) {
                        sourceType = NhiRuleCheckSourceType.SYSTEM_RECORD;
                    } else {
                        sourceType = NhiRuleCheckSourceType.NHI_CARD_RECORD;
                    }
                    result.add(new NhiRuleCheckResultDTO()
                        .validated(false)
                        .validateTitle("89XXX special: 前30天內不得有89006C，但如果這中間有90001C, 90002C, 90003C, 90019C, 90020C則例外")
                        .nhiRuleCheckInfoType(NhiRuleCheckFormat.D1_3.getLevel())
                        .message(
                            String.format(
                                NhiRuleCheckFormat.D1_3.getFormat(),
                                dto.getNhiExtendTreatmentProcedure().getA73(),
                                k,
                                "89006C",
                                sourceType.getValue(),
                                DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                    v.getRecordDateTime()
                                )
                            )
                        )
                    );
                });
            }
        }

        return result;
    }

    public NhiRuleCheckResultDTO isSpecialRuleFor91014C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("Special rule for 91014C")
            .validated(true);

        List<NhiRuleCheckTxSnapshot> ignoreTargetTxSnapshots = this.getIgnoreTargetTxSnapshots(dto);
        LocalDateDuration duration = new LocalDateDuration();
        String displayLimit = "";
        boolean foundTargetInCurrentDisposal = false;

        // 當前處置
        for (NhiRuleCheckTxSnapshot ignoreTargetTxSnapshot : ignoreTargetTxSnapshots) {
            String snapshotCode = ignoreTargetTxSnapshot.getNhiCode();
            if ("91004C".equals(snapshotCode) ||
                "91005C".equals(snapshotCode)
            ) {
                duration = this.regularDayDurationCalculation(
                    dto,
                    DateTimeUtil.NHI_360_DAY
                );
                foundTargetInCurrentDisposal = true;
                displayLimit = String.valueOf(DateTimeUtil.NHI_360_DAY.getDays());
                break;
            } else if ("91020C".equals(snapshotCode)) {
                duration = this.regularDayDurationCalculation(
                    dto,
                    DateTimeUtil.NHI_180_DAY
                );
                foundTargetInCurrentDisposal = true;
                displayLimit = String.valueOf(DateTimeUtil.NHI_180_DAY.getDays());
                break;
            }
        }

        // 當日其他處置 91004C, 91005C, 91020C
        List<String> queryCode = Arrays.asList(
            "91004C", "91005C", "91020C"
        );
        if (!foundTargetInCurrentDisposal) {
            List<NhiHybridRecordDTO> sourceData = dto.getSourceData() != null && dto.getSourceData().size() > 0
            ? dto.getSourceData().stream().filter(d -> queryCode.contains(d.getCode())).collect(Collectors.toList())
            : this.findNhiHypeRecordsDTO(
                dto.getPatient().getId(),
                queryCode,
                Arrays.asList(
                    this.getDisposalIdInDTO(dto)
                )
            );

            LocalDateDuration todayDuration = this.regularDayDurationCalculation(dto, DateTimeUtil.NHI_0_DAY);

            if (sourceData.stream()
                .filter(
                    d -> d.getRecordDateTime().isAfter(todayDuration.getBegin()) && d.getRecordDateTime().isBefore(todayDuration.getEnd()) ||
                        d.getRecordDateTime().isEqual(todayDuration.getBegin()) ||
                        d.getRecordDateTime().isEqual(todayDuration.getEnd())
                )
                .count() > 0
            ) {
                String clauseDataCode = sourceData.get(0).getCode();
                if ("91004C".equals(clauseDataCode) ||
                    "91005C".equals(clauseDataCode)
                ) {
                    duration = this.regularDayDurationCalculation(
                        dto,
                        DateTimeUtil.NHI_360_DAY
                    );
                    foundTargetInCurrentDisposal = true;
                } else if ("91020C".equals(clauseDataCode)) {
                    duration = this.regularDayDurationCalculation(
                        dto,
                        DateTimeUtil.NHI_180_DAY
                    );
                    foundTargetInCurrentDisposal = true;
                }
            }
        }

        // 查詢所有 91014C
        if (foundTargetInCurrentDisposal) {

            if (ignoreTargetTxSnapshots.stream()
                .filter(s -> "91014C".equals(s.getNhiCode()))
                .count() > 0
            ) {
                String m = this.generateErrorMessage(
                    NhiRuleCheckFormat.D4_1,
                    NhiRuleCheckSourceType.CURRENT_DISPOSAL,
                    "91014C",
                    "91014C",
                    this.getNhiExtendDisposalDateInDTO(dto),
                    displayLimit,
                    null,
                    null
                );

                return result
                    .validated(false)
                    .nhiRuleCheckInfoType(NhiRuleCheckFormat.D4_1.getLevel())
                    .message(m);

            }

            List<NhiHybridRecordDTO> sourceData = dto.getSourceData() != null && dto.getSourceData().size() > 0
            ? dto.getSourceData().stream().filter(d -> "91014C".equals(d.getCode())).collect(Collectors.toList())
            : this.findNhiHypeRecordsDTO(
                dto.getPatient().getId(),
                Arrays.asList(
                    "91014C"
                ),
                Arrays.asList(
                    this.getDisposalIdInDTO(dto)
                )
            );

            LocalDateDuration finalDuration = duration;
            List<NhiHybridRecordDTO> matches = duration != null
                ? sourceData.stream()
                .filter(
                    d -> d.getRecordDateTime().isAfter(finalDuration.getBegin()) && d.getRecordDateTime().isBefore(finalDuration.getEnd()) ||
                        d.getRecordDateTime().isEqual(finalDuration.getBegin()) ||
                        d.getRecordDateTime().isEqual(finalDuration.getEnd())
                )
                .collect(Collectors.toList())
                : sourceData;

            if (matches.size() > 0) {
                String m = this.generateErrorMessage(
                    NhiRuleCheckFormat.D4_1,
                    this.getSourceDataType(
                        dto,
                        matches.get(0)
                    ),
                    "91014C",
                    "91014C",
                    matches.get(0).getRecordDateTime(),
                    displayLimit,
                    null,
                    null
                );

                result
                    .validated(false)
                    .nhiRuleCheckInfoType(NhiRuleCheckFormat.D4_1.getLevel())
                    .message(m);
            }

        }

        return result;
    }

    public NhiRuleCheckResultDTO isSpecialRuleFor91018C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("special rule for 91018C")
            .validated(true);

        List<String> queryCode = Arrays.asList(
            "91022C", "91023C"
        );

        List<NhiHybridRecordDTO> sourceData = dto.getSourceData() != null && dto.getSourceData().size() > 0
            ? dto.getSourceData().stream().filter(d -> queryCode.contains(d.getCode())).collect(Collectors.toList())
            : this.findNhiHypeRecordsDTO(
            dto.getPatient().getId(),
            Arrays.asList(
                "91022C", "91023C"
            ),
            Arrays.asList(
                this.getDisposalIdInDTO(dto)
            )
        );

        LocalDateDuration duration = this.regularDayDurationCalculation(dto, DateTimeUtil.NHI_90_DAY);
        boolean has91022C = false;
        LocalDate date91022C = null;
        for (NhiHybridRecordDTO d : sourceData) {
            if ("91022C".equals(d.getCode()) &&
                d.getRecordDateTime().isBefore(duration.getBegin())
            ) {
                has91022C = true;
                date91022C = d.getRecordDateTime();
                break;
            }
        }

        boolean has91023C = false;
        if (has91022C) {
            for (NhiHybridRecordDTO d : sourceData) {
                if ("91023C".equals(d.getCode())) {
                    if (!d.getRecordDateTime().isEqual(date91022C) ||
                        !d.getRecordDateTime().isEqual(duration.getEnd())
                    ) {
                        if (d.getRecordDateTime().isAfter(date91022C) &&
                            d.getRecordDateTime().isBefore(duration.getEnd())
                        ) {
                            has91023C = true;
                            break;
                        }
                    }
                }
            }
        }

        if (!has91023C) {
            result
                .validated(false)
                .nhiRuleCheckInfoType(NhiRuleCheckFormat.PERIO_1.getLevel())
                .message(
                    String.format(
                        NhiRuleCheckFormat.PERIO_1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73()
                    )
                );
        }

        return result;
    }

    public List<NhiRuleCheckResultDTO> isD4_2(
        NhiRuleCheckDTO dto,
        NhiRuleCheckSourceType onlySourceType,
        List<String> originCodes,
        LocalDateDuration duration,
        String limitDisplayDuration
    ) {
        List<NhiRuleCheckResultDTO> results = new ArrayList<>();

        List<String> codes = this.parseNhiCode(originCodes);

        // 當前 disposal
        if (dto.getIncludeNhiCodes() != null &&
            dto.getIncludeNhiCodes().size() > 0
        ) {
            List<NhiRuleCheckTxSnapshot> ignoreTargetTxSnapshots = this.getIgnoreTargetTxSnapshots(dto);
            List<NhiRuleCheckTxSnapshot> currentDisposalMatches = ignoreTargetTxSnapshots.stream()
                .filter(d -> dto.getNhiExtendTreatmentProcedure().getA73().equals(d.getNhiCode()))
                .collect(Collectors.toList());

            for (NhiRuleCheckTxSnapshot snapshot : currentDisposalMatches) {
                if (
                    !codes.contains(
                        snapshot.getNhiCode()
                    )
                ) {
                    continue;
                }

                List<ToothUtil.ToothPhase> duplicatedToothPhase = ToothUtil.listDuplicatedToothPhase(
                    dto.getNhiExtendTreatmentProcedure().getA74(),
                    snapshot.getTeeth()
                );

                if (duplicatedToothPhase.size() > 0) {
                    duplicatedToothPhase.forEach(d -> {
                        String m = this.generateErrorMessage(
                            NhiRuleCheckFormat.D4_2,
                            NhiRuleCheckSourceType.CURRENT_DISPOSAL,
                            dto.getNhiExtendTreatmentProcedure().getA73(),
                            dto.getNhiExtendTreatmentProcedure().getA73(),
                            this.getNhiExtendDisposalDateInDTO(dto),
                            limitDisplayDuration,
                            d.getNameOfPhase(),
                            null
                        );

                        results.add(
                            new NhiRuleCheckResultDTO()
                                .validateTitle("指定代碼 於 指定時間 內，只得申報單一象限")
                                .nhiRuleCheckInfoType(NhiRuleCheckFormat.D4_2.getLevel())
                                .validated(false)
                                .message(m)
                        );
                    });
                }

                return results;
            }
        }

        // 其他處置
        List<NhiHybridRecordDTO> sourceData = dto.getSourceData() != null && dto.getSourceData().size() > 0
            ? dto.getSourceData().stream().filter(d -> codes.contains(d.getCode())).collect(Collectors.toList())
            : this.findNhiHypeRecordsDTO(
            dto.getPatient().getId(),
            codes,
            Arrays.asList(
                dto.getNhiExtendDisposal() != null &&
                    dto.getNhiExtendDisposal().getDisposal() != null &&
                    dto.getNhiExtendDisposal().getDisposal().getId() != null
                    ? dto.getNhiExtendDisposal().getDisposal().getId()
                    : 0L
            )
        );

        if (NhiRuleCheckSourceType.SYSTEM_RECORD.equals(onlySourceType)) {
            sourceData = sourceData.stream()
                .filter(d -> "SYS".equals(d.getRecordSource()))
                .collect(Collectors.toList());
        }

        List<NhiHybridRecordDTO> matches = duration != null
            ? sourceData.stream()
            .filter(
                d -> d.getRecordDateTime().isAfter(duration.getBegin()) && d.getRecordDateTime().isBefore(duration.getEnd()) ||
                    d.getRecordDateTime().isEqual(duration.getBegin()) ||
                    d.getRecordDateTime().isEqual(duration.getEnd())
            )
            .collect(Collectors.toList())
            : sourceData;


        for (NhiHybridRecordDTO record : matches) {
            List<ToothUtil.ToothPhase> duplicatedToothPhase = ToothUtil.listDuplicatedToothPhase(
                dto.getNhiExtendTreatmentProcedure().getA74(),
                record.getTooth()
            );

            if (duplicatedToothPhase.size() > 0) {
                duplicatedToothPhase.forEach(d -> {
                    String m = this.generateErrorMessage(
                        NhiRuleCheckFormat.D4_2,
                        this.getSourceDataType(dto, record),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        record.getRecordDateTime(),
                        limitDisplayDuration,
                        d.getNameOfPhase(),
                        null
                    );

                    results.add(
                        new NhiRuleCheckResultDTO()
                            .validateTitle("指定代碼 於 指定時間 內，只得申報單一象限")
                            .nhiRuleCheckInfoType(NhiRuleCheckFormat.D4_2.getLevel())
                            .validated(false)
                            .message(m)
                    );
                });
            }
        }

        return results;
    }

    public NhiRuleCheckResultDTO isD5_1(
        NhiRuleCheckDTO dto,
        NhiRuleCheckSourceType onlySourceType,
        List<String> originCodes,
        LocalDateDuration duration,
        String limitDisplayDuration,
        int limitTimes
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("D5_1")
            .validated(true);

        List<String> codes = this.parseNhiCode(originCodes);
        List<NhiRuleCheckTxSnapshot> currentDisposalMatches = new ArrayList<>();

        // 當前 disposal
        if (dto.getIncludeNhiCodes() != null &&
            dto.getIncludeNhiCodes().size() > 0
        ) {
            currentDisposalMatches = dto.getTxSnapshots().stream()
                .filter(d -> codes.contains(d.getNhiCode()))
                .collect(Collectors.toList());
        }

        // 其他處置
        List<NhiHybridRecordDTO> sourceData = dto.getSourceData() != null && dto.getSourceData().size() > 0
            ? dto.getSourceData().stream().filter(d -> codes.contains(d.getCode())).collect(Collectors.toList())
            : this.findNhiHypeRecordsDTO(
            dto.getPatient().getId(),
            codes,
            Arrays.asList(
                dto.getNhiExtendDisposal() != null &&
                    dto.getNhiExtendDisposal().getDisposal() != null &&
                    dto.getNhiExtendDisposal().getDisposal().getId() != null
                    ? dto.getNhiExtendDisposal().getDisposal().getId()
                    : 0L
            )
        );

        if (NhiRuleCheckSourceType.SYSTEM_RECORD.equals(onlySourceType)) {
            sourceData = sourceData.stream()
                .filter(d -> "SYS".equals(d.getRecordSource()))
                .collect(Collectors.toList());
        }

        List<NhiHybridRecordDTO> matches = duration != null
            ? sourceData.stream()
            .filter(
                d -> d.getRecordDateTime().isAfter(duration.getBegin()) && d.getRecordDateTime().isBefore(duration.getEnd()) ||
                    d.getRecordDateTime().isEqual(duration.getBegin()) ||
                    d.getRecordDateTime().isEqual(duration.getEnd())
            )
            .collect(Collectors.toList())
            : sourceData;

        if (currentDisposalMatches.size() +
            matches.size() >= limitTimes
        ) {
            List<String> stringDateList = new ArrayList<>();

            if (currentDisposalMatches.size() > 0 &&
                this.isCreatedDisposal(dto)
            ) {
                stringDateList.add(
                    DateTimeUtil.transformLocalDateToRocDateForDisplay(
                        this.getNhiExtendDisposalDateInDTO(dto)
                    )
                );
            }

            matches.forEach(m -> {
                stringDateList.add(
                    DateTimeUtil.transformLocalDateToRocDateForDisplay(
                        m.getRecordDateTime()
                    )
                );
            });

            String m = this.generateErrorMessage(
                NhiRuleCheckFormat.D5_1,
                null,
                dto.getNhiExtendTreatmentProcedure().getA73(),
                null,
                null,
                limitDisplayDuration,
                null,
                String.join(", ", stringDateList)
            );

            result
                .validated(false)
                .nhiRuleCheckInfoType(NhiRuleCheckFormat.D5_1.getLevel())
                .message(m);
        }

        return result;
    }

    public NhiRuleCheckResultDTO isSpecialRuleFor91003C(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("special rule for 91003C")
            .validated(true);

        List<NhiRuleCheckTxSnapshot> currentDisposalMatches = new ArrayList<>();
        List<NhiRuleCheckTxSnapshot> ignoreTargetTxSnapshots = new ArrayList<>();

        // 當前 disposal
        if (dto.getIncludeNhiCodes() != null &&
            dto.getIncludeNhiCodes().size() > 0
        ) {
            ignoreTargetTxSnapshots = this.getIgnoreTargetTxSnapshots(dto);
            currentDisposalMatches = ignoreTargetTxSnapshots.stream()
                .filter(d -> "91004C".equals(d.getNhiCode()))
                .collect(Collectors.toList());
        }

        // 其他處置
        List<String> queryCode = Arrays.asList("91004C");
        List<NhiHybridRecordDTO> sourceData = dto.getSourceData() != null && dto.getSourceData().size() > 0
            ? dto.getSourceData().stream().filter(d -> queryCode.contains(d.getCode())).collect(Collectors.toList())
            : this.findNhiHypeRecordsDTO(
            dto.getPatient().getId(),
            queryCode,
            Arrays.asList(
                this.getDisposalIdInDTO(dto)
            )
        );

        LocalDateDuration duration = this.regularDayDurationCalculation(dto, DateTimeUtil.NHI_180_DAY);

        List<NhiHybridRecordDTO> matches = duration != null
            ? sourceData.stream()
            .filter(
                d -> d.getRecordDateTime().isAfter(duration.getBegin()) && d.getRecordDateTime().isBefore(duration.getEnd()) ||
                    d.getRecordDateTime().isEqual(duration.getBegin()) ||
                    d.getRecordDateTime().isEqual(duration.getEnd())
            )
            .collect(Collectors.toList())
            : sourceData;

        if (currentDisposalMatches != null &&
            currentDisposalMatches.size() > 0 ||
            matches != null &&
            matches.size() > 0
        ) {
            List<String> stringDateList = new ArrayList<>();

            List<NhiHybridRecordDTO> past91003C = this.findNhiHypeRecordsDTO(
                dto.getPatient().getId(),
                Arrays.asList("91003C"),
                Arrays.asList(
                    this.getDisposalIdInDTO(dto)
                )
            );

            if (dto.getIncludeNhiCodes().stream().anyMatch(c -> "91003C".equals(c))) {
                stringDateList.add(
                    DateTimeUtil.transformLocalDateToRocDateForDisplay(
                        this.getNhiExtendDisposalDateInDTO(dto)
                    )
                );
            }

            this.getSourceDataByDuration(
                past91003C,
                this.regularDayDurationCalculation(dto, DateTimeUtil.NHI_180_DAY)
            ).forEach(pd -> {
                stringDateList.add(
                    DateTimeUtil.transformLocalDateToRocDateForDisplay(
                        pd.getRecordDateTime()
                    )
                );
            });

            String m = this.generateErrorMessage(
                NhiRuleCheckFormat.W1_1,
                currentDisposalMatches != null && currentDisposalMatches.size() > 0
                    ? NhiRuleCheckSourceType.CURRENT_DISPOSAL
                    : this.getSourceDataType(dto, matches.get(0)),
                "91003C",
                "91004C",
                currentDisposalMatches != null && currentDisposalMatches.size() > 0
                    ? this.getNhiExtendDisposalDateInDTO(dto)
                    : matches.get(0).getRecordDateTime(),
                String.valueOf(DateTimeUtil.NHI_180_DAY.getDays()),
                null,
                String.join(", ", stringDateList)
            );

            result.validated(false)
                .nhiRuleCheckInfoType(NhiRuleCheckFormat.W1_1.getLevel())
                .message(m);
        }

        return result;
    }

    /**
     * 當前處置單包含任意其他處置
     * @param dto
     * @return
     */
    public NhiRuleCheckResultDTO isW5_1(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.WARNING)
            .validateTitle("當前處置單包含任意其他處置")
            .validated(true);

        if (dto.getIncludeNhiCodes() != null &&
            dto.getIncludeNhiCodes().size() == 1
        ) {
            result.validated(false)
                .message(
                    String.format(
                        NhiRuleCheckFormat.W5_1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73()
                    )
                );
        }

        return result;
    }

    /**
     * 需依賴某個時間點上，曾經存在某個代碼
     * @param dto
     * @param onlySourceType 全查(by pass null) or 只有 sys
     * @param originCodes
     * @param duration
     * @param limitDisplayDuration
     * @param format 應為 D8_1 or D8_2
     * @return
     */
    public NhiRuleCheckResultDTO isDependOnCodeBeforeDate(
        NhiRuleCheckDTO dto,
        NhiRuleCheckSourceType onlySourceType,
        List<String> originCodes,
        LocalDateDuration duration,
        String limitDisplayDuration,
        NhiRuleCheckFormat format
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("需依賴某個時間點上，曾經存在某個代碼")
            .validated(true);

        List<String> codes = this.parseNhiCode(originCodes);
        List<NhiRuleCheckTxSnapshot> currentDisposalMatches = new ArrayList<>();

        // 當前 disposal
        if (dto.getIncludeNhiCodes() != null &&
            dto.getIncludeNhiCodes().size() > 0
        ) {
            List<NhiRuleCheckTxSnapshot> ignoreTargetTxSnapshots = this.getIgnoreTargetTxSnapshots(dto);
            currentDisposalMatches = ignoreTargetTxSnapshots.stream()
                .filter(d -> codes.contains(d.getNhiCode()))
                .collect(Collectors.toList());
        }

        // 其他處置
        List<NhiHybridRecordDTO> sourceData = dto.getSourceData() != null && dto.getSourceData().size() > 0
            ? dto.getSourceData().stream().filter(d -> codes.contains(d.getCode())).collect(Collectors.toList())
            : this.findNhiHypeRecordsDTO(
            dto.getPatient().getId(),
            codes,
            Arrays.asList(
                dto.getNhiExtendDisposal() != null &&
                    dto.getNhiExtendDisposal().getDisposal() != null &&
                    dto.getNhiExtendDisposal().getDisposal().getId() != null
                    ? dto.getNhiExtendDisposal().getDisposal().getId()
                    : 0L
            )
        );

        if (NhiRuleCheckSourceType.SYSTEM_RECORD.equals(onlySourceType)) {
            sourceData = sourceData.stream()
                .filter(d -> "SYS".equals(d.getRecordSource()))
                .collect(Collectors.toList());
        }

        List<NhiHybridRecordDTO> matches = duration != null
            ? sourceData.stream()
            .filter(
                d -> d.getRecordDateTime().isAfter(duration.getBegin()) && d.getRecordDateTime().isBefore(duration.getEnd()) ||
                    d.getRecordDateTime().isEqual(duration.getBegin()) ||
                    d.getRecordDateTime().isEqual(duration.getEnd())
            )
            .collect(Collectors.toList())
            : sourceData;

        if (matches != null &&
            matches.size() == 0 && currentDisposalMatches.size() == 0
        ) {
            String m = "";
            if (currentDisposalMatches.size() == 0) {
                m = this.generateErrorMessage(
                    format,
                    NhiRuleCheckSourceType.CURRENT_DISPOSAL, // 避免 null 出錯，此項分配到的 message 其實理論上應當用不到
                    dto.getNhiExtendTreatmentProcedure().getA73(),
                    String.join("/", codes),
                    this.getNhiExtendDisposalDateInDTO(dto),
                    limitDisplayDuration,
                    null,
                    null
                );
            } else {
                m = this.generateErrorMessage(
                    format,
                    NhiRuleCheckSourceType.CURRENT_DISPOSAL, // 避免 null 出錯，此項分配到的 message 其實理論上應當用不到
                    dto.getNhiExtendTreatmentProcedure().getA73(),
                    String.join("/", codes),
                    this.getNhiExtendDisposalDateInDTO(dto),
                    limitDisplayDuration,
                    null,
                    null
                );
            }

            result
                .validated(false)
                .nhiRuleCheckInfoType(format.getLevel())
                .message(m);
        }

        return result;
    }

    /**
     * 指定 patient id, codes, tooth 並排除 disposal，提示訊息不顯示牙齒
     * @param dto
     * @param onlySourceType
     * @param originCodes
     * @param deciduousToothDuration
     * @param permanentToothDuration
     * @param deciduousLimitDisplayDuration
     * @param permanentLimitDisplayDuration
     * @return
     */
    public List<NhiRuleCheckResultDTO> isNoCodeWithToothBeforeDate(
        NhiRuleCheckDTO dto,
        NhiRuleCheckSourceType onlySourceType,
        List<String> originCodes,
        LocalDateDuration deciduousToothDuration,
        LocalDateDuration permanentToothDuration,
        String deciduousLimitDisplayDuration,
        String permanentLimitDisplayDuration,
        NhiRuleCheckFormat format
    ) {
        List<NhiRuleCheckResultDTO> results = new ArrayList<>();

        List<String> codes = this.parseNhiCode(originCodes);
        List<String> targetTeeth = ToothUtil.splitA74(
            dto.getNhiExtendTreatmentProcedure().getA74()
        );
        List<NhiRuleCheckTxSnapshot> ignoreTargetTxSnapshots = this.getIgnoreTargetTxSnapshots(dto);

        // Current disposal
        ignoreTargetTxSnapshots.stream()
            .filter(d -> codes.contains(d.getNhiCode()))
            .forEach(d -> {
                List<String> dt = ToothUtil.splitA74(
                    d.getTeeth()
                );
                dt.forEach(t -> {
                    if (targetTeeth.contains(t)) {
                        boolean isRuleConflict = true;

                        if (format.equals(NhiRuleCheckFormat.D7_1) ||
                            format.equals(NhiRuleCheckFormat.D7_2)
                        ) {
                            if (dto.getNhiExtendDisposal().getA23() != null &&
                                dto.getNhiExtendDisposal().getA23().equals("AB")
                            ) {
                                isRuleConflict = false;
                            }
                        }

                        if (isRuleConflict) {
                            String limitDisplayDuration = ToothUtil.validatedToothConstraint(
                                ToothConstraint.DECIDUOUS_TOOTH,
                                t
                            )
                                ? deciduousLimitDisplayDuration
                                : permanentLimitDisplayDuration;
                            String m = this.generateErrorMessage(
                                format,
                                NhiRuleCheckSourceType.CURRENT_DISPOSAL,
                                dto.getNhiExtendTreatmentProcedure().getA73(),
                                d.getNhiCode(),
                                this.getNhiExtendDisposalDateInDTO(dto),
                                limitDisplayDuration,
                                t,
                                null
                            );
                            NhiRuleCheckResultDTO r = new NhiRuleCheckResultDTO()
                                .validateTitle("指定 patient id, codes, tooth 並排除 disposal")
                                .validated(false)
                                .nhiRuleCheckInfoType(format.getLevel())
                                .message(m);
                            results.add(r);
                        }
                    }
                });
            });

        if (results.size() > 0) {
            return results;
        }

        // Past disposal
        List<NhiHybridRecordDTO> sourceData = dto.getSourceData() != null && dto.getSourceData().size() > 0
            ? dto.getSourceData().stream().filter(d -> codes.contains(d.getCode())).collect(Collectors.toList())
            : this.findNhiHypeRecordsDTO(
            dto.getPatient().getId(),
            codes,
            Arrays.asList(
                this.getDisposalIdInDTO(dto)
            )
        );

        if (onlySourceType != null &&
            onlySourceType.equals(NhiRuleCheckSourceType.SYSTEM_RECORD)
        ) {
            sourceData = sourceData.stream()
                .filter(d -> "SYS".equals(d.getRecordSource()))
                .collect(Collectors.toList());
        }

        sourceData.forEach(d -> {
            List<String> dt = ToothUtil.splitA74(
                d.getTooth()
            );
            dt.forEach(t -> {
                if (targetTeeth.contains(t)) {
                    LocalDateDuration duration = ToothUtil.validatedToothConstraint(
                        ToothConstraint.DECIDUOUS_TOOTH,
                        t
                    )
                        ? deciduousToothDuration
                        : permanentToothDuration;
                    String limitDisplayDuration = ToothUtil.validatedToothConstraint(
                        ToothConstraint.DECIDUOUS_TOOTH,
                        t
                    )
                        ? deciduousLimitDisplayDuration
                        : permanentLimitDisplayDuration;

                    if (duration == null ||
                        d.getRecordDateTime().isAfter(duration.getBegin()) && d.getRecordDateTime().isBefore(duration.getEnd()) ||
                        d.getRecordDateTime().isEqual(duration.getBegin()) ||
                        d.getRecordDateTime().isEqual(duration.getEnd())
                    ) {
                        boolean isRuleConflict = true;

                        if (format.equals(NhiRuleCheckFormat.D7_1) ||
                            format.equals(NhiRuleCheckFormat.D7_2)
                        ) {
                            if (dto.getNhiExtendDisposal().getA23() != null &&
                                dto.getNhiExtendDisposal().getA23().equals("AB")
                            ) {
                                isRuleConflict = false;
                            }
                        }

                        if (isRuleConflict) {
                            String m = this.generateErrorMessage(
                                format,
                                this.getSourceDataType(
                                    dto,
                                    d
                                ),
                                dto.getNhiExtendTreatmentProcedure().getA73(),
                                d.getCode(),
                                d.getRecordDateTime(),
                                limitDisplayDuration,
                                t,
                                null
                            );
                            NhiRuleCheckResultDTO r = new NhiRuleCheckResultDTO()
                                .validateTitle("指定 patient id, codes, tooth 並排除 disposal")
                                .validated(false)
                                .nhiRuleCheckInfoType(format.getLevel())
                                .message(m);
                            results.add(r);
                        }
                    }
                }
            });
        });

        return results;
    }

    /**
     * 檢查該牙齒是否曾做過 92013C, 92014C, 92015C 等處置
     * @param dto
     * @return
     */
    public List<NhiRuleCheckResultDTO> isNoRemovedTeeth(NhiRuleCheckDTO dto) {
        List<NhiRuleCheckResultDTO> results = new ArrayList<>();

        Map<String, LocalDate> removeTeethFrom92013C = new HashMap<>();
        Map<String, LocalDate> removeTeethFrom92014C = new HashMap<>();
        Map<String, LocalDate> removeTeethFrom92015C = new HashMap<>();
        List<String> targetTooth = ToothUtil.splitA74(
            dto.getNhiExtendTreatmentProcedure().getA74()
        );

        // Cuurent disposal
        this.getIgnoreTargetTxSnapshots(dto).stream()
            .forEach(d -> {
                switch (d.getNhiCode()) {
                    case "92013C":
                        ToothUtil.splitA74(
                            d.getTeeth()
                        ).forEach(t -> {
                            if (targetTooth.contains(t) &&
                                !removeTeethFrom92013C.containsKey(t)
                            ) {
                                removeTeethFrom92013C.put(t, this.getNhiExtendDisposalDateInDTO(dto));
                                NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
                                    .validateTitle("檢查該牙齒是否曾做過 92013C, 92014C, 92015C 等處置")
                                    .validated(false)
                                    .nhiRuleCheckInfoType(NhiRuleCheckFormat.D1_3.getLevel())
                                    .message(
                                        String.format(
                                            NhiRuleCheckFormat.D1_3.getFormat(),
                                            dto.getNhiExtendTreatmentProcedure().getA73(),
                                            t,
                                            "92013C",
                                            NhiRuleCheckSourceType.CURRENT_DISPOSAL.getValue(),
                                            DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                                this.getNhiExtendDisposalDateInDTO(dto)
                                            )
                                        )
                                    );
                                results.add(result);
                            }
                        });
                        break;
                    case "92014C":
                        ToothUtil.splitA74(
                            d.getTeeth()
                        ).forEach(t -> {
                            if (targetTooth.contains(t) &&
                                !removeTeethFrom92014C.containsKey(t)
                            ) {
                                removeTeethFrom92014C.put(t, this.getNhiExtendDisposalDateInDTO(dto));
                                NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
                                    .validateTitle("檢查該牙齒是否曾做過 92013C, 92014C, 92015C 等處置")
                                    .validated(false)
                                    .nhiRuleCheckInfoType(NhiRuleCheckFormat.D1_3.getLevel())
                                    .message(
                                        String.format(
                                            NhiRuleCheckFormat.D1_3.getFormat(),
                                            dto.getNhiExtendTreatmentProcedure().getA73(),
                                            t,
                                            "92014C",
                                            NhiRuleCheckSourceType.CURRENT_DISPOSAL.getValue(),
                                            DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                                this.getNhiExtendDisposalDateInDTO(dto)
                                            )
                                        )
                                    );
                                results.add(result);
                            }
                        });
                        break;
                    case "92015C":
                        ToothUtil.splitA74(
                            d.getTeeth()
                        ).forEach(t -> {
                            if (targetTooth.contains(t) &&
                                !removeTeethFrom92015C.containsKey(t)
                            ) {
                                removeTeethFrom92015C.put(t, this.getNhiExtendDisposalDateInDTO(dto));
                                NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
                                    .validateTitle("檢查該牙齒是否曾做過 92013C, 92014C, 92015C 等處置")
                                    .validated(false)
                                    .nhiRuleCheckInfoType(NhiRuleCheckFormat.D1_3.getLevel())
                                    .message(
                                        String.format(
                                            NhiRuleCheckFormat.D1_3.getFormat(),
                                            dto.getNhiExtendTreatmentProcedure().getA73(),
                                            t,
                                            "92015C",
                                            NhiRuleCheckSourceType.CURRENT_DISPOSAL.getValue(),
                                            DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                                this.getNhiExtendDisposalDateInDTO(dto)
                                            )
                                        )
                                    );
                                results.add(result);
                            }
                        });
                        break;
                    default:
                        break;
                }
            });

        // Past disposal
        this.findNhiHypeRecordsDTO(
            dto.getPatient().getId(),
            Arrays.asList("92013C", "92014C", "92015C"),
            Arrays.asList(
                this.getDisposalIdInDTO(dto)
            )
        ).forEach(d -> {
            switch (d.getCode()) {
                case "92013C":
                    ToothUtil.splitA74(
                        d.getTooth()
                    ).forEach(t -> {
                        if (targetTooth.contains(t) &&
                            !removeTeethFrom92013C.containsKey(t)
                        ) {
                            removeTeethFrom92013C.put(t, this.getNhiExtendDisposalDateInDTO(dto));
                            NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
                                .validateTitle("檢查該牙齒是否曾做過 92013C, 92014C, 92015C 等處置")
                                .validated(false)
                                .nhiRuleCheckInfoType(NhiRuleCheckFormat.D1_3.getLevel())
                                .message(
                                    String.format(
                                        NhiRuleCheckFormat.D1_3.getFormat(),
                                        dto.getNhiExtendTreatmentProcedure().getA73(),
                                        t,
                                        "92013C",
                                        this.determinateSourceType(dto, d).getValue(),
                                        DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                            d.getRecordDateTime()
                                        )
                                    )
                                );
                            results.add(result);
                        }
                    });
                    break;
                case "92014C":
                    ToothUtil.splitA74(
                        d.getTooth()
                    ).forEach(t -> {
                        if (targetTooth.contains(t) &&
                            !removeTeethFrom92014C.containsKey(t)
                        ) {
                            removeTeethFrom92014C.put(t, this.getNhiExtendDisposalDateInDTO(dto));
                            NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
                                .validateTitle("檢查該牙齒是否曾做過 92013C, 92014C, 92015C 等處置")
                                .validated(false)
                                .nhiRuleCheckInfoType(NhiRuleCheckFormat.D1_3.getLevel())
                                .message(
                                    String.format(
                                        NhiRuleCheckFormat.D1_3.getFormat(),
                                        dto.getNhiExtendTreatmentProcedure().getA73(),
                                        t,
                                        "92014C",
                                        this.determinateSourceType(dto, d).getValue(),
                                        DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                            d.getRecordDateTime()
                                        )
                                    )
                                );
                            results.add(result);
                        }
                    });
                    break;
                case "92015C":
                    ToothUtil.splitA74(
                        d.getTooth()
                    ).forEach(t -> {
                        if (targetTooth.contains(t) &&
                            !removeTeethFrom92015C.containsKey(t)
                        ) {
                            removeTeethFrom92015C.put(t, this.getNhiExtendDisposalDateInDTO(dto));
                            NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
                                .validateTitle("檢查該牙齒是否曾做過 92013C, 92014C, 92015C 等處置")
                                .validated(false)
                                .nhiRuleCheckInfoType(NhiRuleCheckFormat.D1_3.getLevel())
                                .message(
                                    String.format(
                                        NhiRuleCheckFormat.D1_3.getFormat(),
                                        dto.getNhiExtendTreatmentProcedure().getA73(),
                                        t,
                                        "92015C",
                                        this.determinateSourceType(dto, d).getValue(),
                                        DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                            d.getRecordDateTime()
                                        )
                                    )
                                );
                            results.add(result);
                        }
                    });
                    break;
                default:
                    break;
            }
        });

        return results;
    }

    /**
     * 醫生對病患，終生一次
     */
    public NhiRuleCheckResultDTO isOnceInWholeLifeAtDoctor(
        NhiRuleCheckDTO dto,
        String code
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
            .validated(true)
            .validateTitle("醫生對病患，終生一次");

        if (dto.getIncludeNhiCodes() != null &&
            dto.getIncludeNhiCodes().stream()
                .filter(c -> c.equals(code))
                .count() > 1
        ) {
            result
                .validated(false)
                .message(
                    String.format(
                        NhiRuleCheckFormat.D2_3.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        NhiRuleCheckSourceType.CURRENT_DISPOSAL.getValue(),
                        DateTimeUtil.transformA71ToDisplay(
                            dto.getNhiExtendDisposal().getA17()
                        )
                    )
                );
        }

        if (dto.getNhiExtendDisposal() != null &&
            dto.getNhiExtendDisposal().getDisposal() != null &&
            dto.getNhiExtendDisposal().getDisposal().getId() != null
        ) {
            List<NhiHybridRecordDTO> sourceData = dto.getSourceData() != null && dto.getSourceData().size() > 0
            ? dto.getSourceData().stream().filter(d -> code.equals(d.getCode())).collect(Collectors.toList())
            : this.findNhiHypeRecordsDTO(
                dto.getPatient().getId(),
                Arrays.asList(
                    code
                ),
                Arrays.asList(
                    this.getDisposalIdInDTO(dto)
                )
            ).stream()
                .filter(d -> "SYS".equals(d.getRecordSource())) // 因為 IC 卡不會知道哪個醫師
                .filter(d -> dto.getDoctorId() != null && dto.getDoctorId().equals(d.getDoctorId()))
                .collect(Collectors.toList());

            if (sourceData != null &&
                sourceData.size() > 0
            ) {
                result
                    .validated(false)
                    .message(
                        String.format(
                            NhiRuleCheckFormat.D2_3.getFormat(),
                            dto.getNhiExtendTreatmentProcedure().getA73(),
                            NhiRuleCheckSourceType.SYSTEM_RECORD.getValue(),
                            DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                sourceData.get(0).getRecordDateTime()
                            )
                        )
                    );
            }
        }

        return result;
    }

    public NhiRuleCheckSourceType determinateSourceType(
        NhiRuleCheckDTO dto,
        NhiHybridRecordDTO data
    ) {
        NhiRuleCheckSourceType matchedSourceType = NhiRuleCheckSourceType.SYSTEM_RECORD;
        NhiHybridRecordDTO latestNhiHybridRecord = data;
        if (
            "IC".equals(latestNhiHybridRecord.getRecordSource())
        ) {
            matchedSourceType = NhiRuleCheckSourceType.NHI_CARD_RECORD;
        } else if (
            latestNhiHybridRecord.getRecordDateTime().isEqual(
                DateTimeUtil.transformROCDateToLocalDate(
                    dto.getNhiExtendTreatmentProcedure().getA71()
                )
            )
        ) {
            matchedSourceType = NhiRuleCheckSourceType.TODAY_OTHER_DISPOSAL;
        }

        return matchedSourceType;
    }

    public String generateErrorMessage(
        NhiRuleCheckFormat format,
        NhiRuleCheckSourceType sourceType,
        String targetCode,
        String matchCode,
        LocalDate matchDate,
        String limitValueString,
        String tooth,
        String customizedString
    ) {
        String m = "";
        try {
            switch (format) {
                case D1_1:
                    m = String.format(
                        NhiRuleCheckFormat.D1_1.getFormat(),
                        targetCode,
                        matchCode,
                        sourceType.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate),
                        targetCode
                    );
                    break;
                case D1_2:
                    m = String.format(
                        NhiRuleCheckFormat.D1_2.getFormat(),
                        targetCode,
                        matchCode,
                        sourceType.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate),
                        limitValueString,
                        targetCode
                    );
                    break;
                case D1_2_2:
                    m = String.format(
                        NhiRuleCheckFormat.D1_2_2.getFormat(),
                        targetCode,
                        matchCode,
                        sourceType.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate),
                        limitValueString,
                        targetCode
                    );
                    break;
                case D1_2_3:
                    m = String.format(
                        NhiRuleCheckFormat.D1_2_3.getFormat(),
                        targetCode,
                        matchCode,
                        sourceType.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate),
                        limitValueString,
                        targetCode
                    );
                    break;
                case D1_3:
                    m = String.format(
                        NhiRuleCheckFormat.D1_3.getFormat(),
                        targetCode,
                        tooth,
                        matchCode,
                        sourceType.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate)
                    );
                    break;
                case D2_1:
                    m = String.format(
                        NhiRuleCheckFormat.D2_1.getFormat(),
                        targetCode,
                        sourceType.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate)
                    );
                    break;
                case D2_2:
                    m = String.format(
                        NhiRuleCheckFormat.D2_2.getFormat(),
                        targetCode,
                        sourceType.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate)
                    );
                    break;
                case D4_1:
                    m = String.format(
                        NhiRuleCheckFormat.D4_1.getFormat(),
                        targetCode,
                        sourceType.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate)
                    );
                    break;
                case D4_2:
                    m = String.format(
                        NhiRuleCheckFormat.D4_2.getFormat(),
                        targetCode,
                        sourceType.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate),
                        tooth
                    );
                    break;
                case D5_1:
                    m = String.format(
                        NhiRuleCheckFormat.D5_1.getFormat(),
                        targetCode,
                        customizedString
                    );
                    break;
                case D6_1:
                    m = String.format(
                        NhiRuleCheckFormat.D6_1.getFormat(),
                        targetCode,
                        matchCode
                    );
                    break;
                case D7_1:
                    m = String.format(
                        NhiRuleCheckFormat.D7_1.getFormat(),
                        targetCode,
                        limitValueString,
                        sourceType.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate)
                    );
                    break;
                case D7_2:
                    m = String.format(
                        NhiRuleCheckFormat.D7_2.getFormat(),
                        targetCode,
                        limitValueString,
                        matchCode,
                        sourceType.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate),
                        tooth
                    );
                    break;
                case D8_1:
                    m = String.format(
                        NhiRuleCheckFormat.D8_1.getFormat(),
                        targetCode,
                        limitValueString,
                        matchCode
                    );
                    break;
                case D8_2:
                    m = String.format(
                        NhiRuleCheckFormat.D8_2.getFormat(),
                        targetCode,
                        matchCode
                    );
                    break;
                case W1_1:
                    m = String.format(
                        NhiRuleCheckFormat.W1_1.getFormat(),
                        targetCode,
                        matchCode,
                        sourceType.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(
                            matchDate
                        ),
                        limitValueString,
                        targetCode,
                        customizedString
                    );
                    break;
                case W3_1:
                    m = String.format(
                        NhiRuleCheckFormat.W3_1.getFormat(),
                        targetCode,
                        matchCode
                    );
                    break;
                case W4_1:
                    m = String.format(
                        NhiRuleCheckFormat.W4_1.getFormat(),
                        targetCode,
                        matchCode,
                        sourceType.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate)
                    );
                    break;
                case W6_1:
                    m = String.format(
                        NhiRuleCheckFormat.W6_1.getFormat(),
                        targetCode,
                        limitValueString,
                        matchCode,
                        sourceType.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate),
                        tooth
                    );
                    break;
                default:
                    m = "";
                    break;
            }
        } catch (Exception e) {
        }

        return m;
    }

    private LocalDate getNhiExtendDisposalDateInDTO(NhiRuleCheckDTO dto) {
        return dto.getNhiExtendDisposal().getA19() != null &&
            dto.getNhiExtendDisposal().getA19() == "2"
            ? dto.getNhiExtendDisposal().getReplenishmentDate()
            : dto.getNhiExtendDisposal().getDate();
    }

    private Long getDisposalIdInDTO(NhiRuleCheckDTO dto) {
        return dto.getNhiExtendDisposal() != null &&
            dto.getNhiExtendDisposal().getDisposal() != null &&
            dto.getNhiExtendDisposal().getDisposal().getId() != null
            ? dto.getNhiExtendDisposal().getDisposal().getId()
            : 0L;
    }

    private boolean isCreatedDisposal(NhiRuleCheckDTO dto) {
        return dto.getNhiExtendDisposal() != null &&
            dto.getNhiExtendDisposal().getDisposal() != null &&
            dto.getNhiExtendDisposal().getId() != null;
    }

    private List<NhiRuleCheckTxSnapshot> getIgnoreTargetTxSnapshots(NhiRuleCheckDTO dto) {
        List<NhiRuleCheckTxSnapshot> result = new ArrayList<>();
        boolean ignoreTheFirstSameData = true;

        for (NhiRuleCheckTxSnapshot snapshot : dto.getTxSnapshots()) {
            if (snapshot.equalsNhiExtendTreatmentProcedure(dto.getNhiExtendTreatmentProcedure()) &&
                ignoreTheFirstSameData
            ) {
                ignoreTheFirstSameData = false;
            } else {
                result.add(snapshot);
            }
        }

        return result;
    }

    private NhiRuleCheckSourceType getSourceDataType(
        NhiRuleCheckDTO dto,
        NhiHybridRecordDTO match
    ) {
        NhiRuleCheckSourceType matchedSourceType = NhiRuleCheckSourceType.SYSTEM_RECORD;
        if (
            "IC".equals(match.getRecordSource())
        ) {
            matchedSourceType = NhiRuleCheckSourceType.NHI_CARD_RECORD;
        } else if (
            match.getDisposalId() != null &&
            match.getDisposalId().equals(this.getDisposalIdInDTO(dto))
        ) {
            matchedSourceType = NhiRuleCheckSourceType.CURRENT_DISPOSAL;
        } else if (
            match.getRecordDateTime().isEqual(
                this.getNhiExtendDisposalDateInDTO(dto)
            )
        ) {
            matchedSourceType = NhiRuleCheckSourceType.TODAY_OTHER_DISPOSAL;
        }

        return matchedSourceType;
    }

    public Period getPatientAge(NhiRuleCheckDTO dto) {
        int age = 0;
        if (dto.getPatient().getBirth() != null) {
            return Period.between(
                dto.getPatient().getBirth(),
                this.getNhiExtendDisposalDateInDTO(dto)
            );
        }

        return Period.of(0, 0, 0);
    }

    public List<NhiRuleCheckTxSnapshot> getCurrentDisposalTxSnapshotByOnlySourceTypeAndCodes(
        NhiRuleCheckDTO dto,
        NhiRuleCheckSourceType onlySourceType,
        List<String> codes
    ) {
        List<NhiRuleCheckTxSnapshot> ignoreTargetTxSnapshots = this.getIgnoreTargetTxSnapshots(dto);
        return ignoreTargetTxSnapshots.stream()
            .filter(d -> codes.contains(d.getNhiCode()))
            .collect(Collectors.toList());
    }

    public List<NhiHybridRecordDTO> getSourceDataByOnlySourceTypeAndCodesAndDuration(
        NhiRuleCheckDTO dto,
        NhiRuleCheckSourceType onlySourceType,
        List<String> codes,
        LocalDateDuration duration
    ) {
        boolean result = false;

        List<NhiHybridRecordDTO> sourceData = codes != null
            ? this.findNhiHypeRecordsDTO(
                    dto.getPatient().getId(),
                    codes,
                    Arrays.asList(
                        this.getDisposalIdInDTO(dto)
                    )
                )
            : this.findNhiHypeRecordsDTO(
                    dto.getPatient().getId(),
                    codes,
                    Arrays.asList(
                        this.getDisposalIdInDTO(dto)
                    )
                );

        if (NhiRuleCheckSourceType.SYSTEM_RECORD.equals(onlySourceType)) {
            sourceData = sourceData.stream()
                .filter(d -> "SYS".equals(d.getRecordSource()))
                .collect(Collectors.toList());
        }

        List<NhiHybridRecordDTO> matches = duration != null
            ? sourceData.stream()
            .filter(
                d -> d.getRecordDateTime().isAfter(duration.getBegin()) && d.getRecordDateTime().isBefore(duration.getEnd()) ||
                    d.getRecordDateTime().isEqual(duration.getBegin()) ||
                    d.getRecordDateTime().isEqual(duration.getEnd())
            )
            .collect(Collectors.toList())
            : sourceData;

        return matches;
    }

    public List<NhiHybridRecordDTO> getSourceDataByDuration(
        List<NhiHybridRecordDTO> sourceData,
        LocalDateDuration duration
    ) {
        return duration != null
            ? sourceData.stream()
            .filter(
                d -> d.getRecordDateTime().isAfter(duration.getBegin()) && d.getRecordDateTime().isBefore(duration.getEnd()) ||
                    d.getRecordDateTime().isEqual(duration.getBegin()) ||
                    d.getRecordDateTime().isEqual(duration.getEnd())
            )
            .collect(Collectors.toList())
            : sourceData;
    }

    public String getCurrentTxNhiCode(NhiRuleCheckDTO dto) {
        return dto.getNhiExtendTreatmentProcedure().getA73();
    }
}
