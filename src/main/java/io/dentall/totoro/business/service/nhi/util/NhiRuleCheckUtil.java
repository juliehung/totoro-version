package io.dentall.totoro.business.service.nhi.util;

import io.dentall.totoro.business.service.NhiRuleCheckInfoType;
import io.dentall.totoro.business.service.NhiRuleCheckSourceType;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckDTO;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckResultDTO;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckBody;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.NhiMedicalRecord;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.dto.NhiMedicalRecordDTO;
import io.dentall.totoro.service.dto.table.DisposalTable;
import io.dentall.totoro.service.dto.table.NhiExtendDisposalTable;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentProcedureTable;
import io.dentall.totoro.service.dto.table.PatientTable;
import io.dentall.totoro.service.mapper.DisposalMapper;
import io.dentall.totoro.service.mapper.NhiExtendDisposalMapper;
import io.dentall.totoro.service.mapper.NhiExtendTreatmentProcedureMapper;
import io.dentall.totoro.service.mapper.PatientMapper;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.web.rest.errors.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Predicate;
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

    // 申報時常用說明
    public static final String DESC_MUST_FULFILL_SURFACE = "應於病歷詳列充填牙面部位";

    private final ApplicationContext applicationContext;

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    private final PatientRepository patientRepository;

    private final NhiMedicalRecordRepository nhiMedicalRecordRepository;

    private final NhiExtendDisposalMapper nhiExtendDisposalMapper;

    private final NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper;

    private final DisposalRepository disposalRepository;

    private final AppointmentRepository appointmentRepository;

    public NhiRuleCheckUtil(
        ApplicationContext applicationContext,
        NhiExtendDisposalRepository nhiExtendDisposalRepository,
        NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository,
        PatientRepository patientRepository,
        NhiMedicalRecordRepository nhiMedicalRecordRepository,
        NhiExtendDisposalMapper nhiExtendDisposalMapper,
        NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper,
        DisposalRepository disposalRepository,
        AppointmentRepository appointmentRepository
    ) {
        this.applicationContext = applicationContext;
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.nhiExtendTreatmentProcedureRepository = nhiExtendTreatmentProcedureRepository;
        this.patientRepository = patientRepository;
        this.nhiMedicalRecordRepository = nhiMedicalRecordRepository;
        this.nhiExtendDisposalMapper = nhiExtendDisposalMapper;
        this.nhiExtendTreatmentProcedureMapper = nhiExtendTreatmentProcedureMapper;
        this.disposalRepository = disposalRepository;
        this.appointmentRepository = appointmentRepository;
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

        // 若代碼檢核無異常，則根據不同情境回傳訊息
        if (rvm.isValidated()) {
            this.addResultToVm(
                this.appendSuccessSourceInfo(dto),
                rvm
            );
        }

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

    /**
     * 查詢 patient 並將取得資料塞入 dto 以利後續使用，或 response as not found
     *
     * @param dto       dto.patient 將會被 assign db data
     * @param patientId 病患 id for query db
     */
    private void assignDtoByPatientId(@NotNull NhiRuleCheckDTO dto, @NotNull Long patientId) {
        dto.setPatient(
            PatientMapper.patientTableToPatient(
                patientRepository.findPatientById(patientId)
                    .orElseThrow(() -> new ResourceNotFoundException("patient with " + patientId))));
    }

    /**
     * 查詢 nhi extend disposal 並將取得資料塞入 dto 以利後續使用。當還未寫卡時，會查詢 disposal 以及 patient 以取得 patient identity，
     * 若查無 patient 則自動帶入 H10
     *
     * @param dto         dto.nhiExtendDisposal 將會被 assign db data
     * @param treatmentId 診療 id
     */
    private void assignDtoByNhiExtendDisposalId(@NotNull NhiRuleCheckDTO dto, @NotNull Long treatmentId) {
        Optional<NhiExtendDisposalTable> optNed = nhiExtendDisposalRepository.findByDisposal_TreatmentProcedures_Id(treatmentId, NhiExtendDisposalTable.class)
            .stream()
            .filter(Objects::nonNull)
            .findAny();

        if (optNed.isPresent()) {
            dto.setNhiExtendDisposal(nhiExtendDisposalMapper.nhiExtendDisposalTableToNhiExtendDisposal(optNed.get()));
        } else {
            NhiExtendDisposal ned = new NhiExtendDisposal();

            Optional<DisposalTable> optD = disposalRepository.findByTreatmentProcedures_Id(treatmentId);
            if (optD.isPresent()) {
                Optional<Patient> optP = patientRepository.findByAppointments_Registration_Disposal_id(optD.get().getId());
                if (optP.isPresent()) {
                    ned.setPatientIdentity(optP.get().getPatientIdentity().getCode());
                } else {
                    ned.setPatientIdentity("H10");
                }

                ned.setDisposal(DisposalMapper.disposalTableToDisposal(optD.get()));
                dto.setNhiExtendDisposal(ned);
            }
        }
    }

    /**
     * 檢核輸入 code(a73), patient, 是否存在且關係匹，並將取得資料塞入 dto 以利後續使用配，或 response as not found
     *
     * @param dto                           dto.nhiExtendTreatmentProcedure 將會被 assign db data
     * @param code                          a.k.a a73 健保代碼
     * @param nhiExtendTreatmentProcedureId 診療 id
     * @param patientId                     病患 id
     */
    private void assignDtoByNhiExtendTreatmentProcedureId(
        @NotNull NhiRuleCheckDTO dto,
        @NotNull String code,
        @NotNull Long nhiExtendTreatmentProcedureId,
        @NotNull Long patientId
    ) {
        dto.setNhiExtendTreatmentProcedure(
            nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(
                nhiExtendTreatmentProcedureRepository.findByIdAndA73AndTreatmentProcedure_Disposal_Registration_Appointment_Patient_Id(
                    nhiExtendTreatmentProcedureId,
                    code,
                    patientId,
                    NhiExtendTreatmentProcedureTable.class)
                    .orElseThrow(() -> new ResourceNotFoundException(
                        "Treatment Procedure Id with "
                            .concat(nhiExtendTreatmentProcedureId.toString())
                            .concat(", Patient Id with ")
                            .concat(patientId.toString())
                            .concat(", Code(a73 from api path) ")
                            .concat(code)
                    ))));
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
                // TODO: 這段效能很差，若有反應檢查很慢可以先調整此項
                nhiExtendTreatmentProcedureRepository.findNhiExtendTreatmentProcedureByTreatmentProcedure_Disposal_Id(optionalDt.get().getId())
                    .forEach(netp -> oldNetpMap.put(netp.getId(), netp));

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
        } else {
            NhiExtendDisposal ned = new NhiExtendDisposal();
            ned.setA17(
                body.getDisposalTime()
            );
            dto.setNhiExtendDisposal(ned);
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
        }

        dto.setExcludeTreatmentProcedureIds(excludeTreatmentProcedureIds);
        dto.setIncludeNhiCodes(includeNhiCode);

        return dto;
    }

    /**
     * 病患 是否在 診療 當下年紀 >= 12 歲
     *
     * @param dto 使用 patient.birth, nhiExtendTreatmentProcedure.A71
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO equalsOrGreaterThanAge12(@NotNull NhiRuleCheckDTO dto) {

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("病患是否在診療當下年紀小於 12 歲")
            .validated(true);

        if (dto.getPatient().getBirth() == null) {
            result.validated(false);
        } else {
            Period p = Period.between(
                dto.getPatient().getBirth(),
                DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()));

            result.validated(p.getYears() >= 12);
        }

        if (!result.isValidated()) {
            result
                .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                .message(
                    String.format(
                        NhiRuleCheckFormat.D3_1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        "年滿十二歲"
                    )
                );
        }

        return result;
    }

    /**
     * 病患 是否在 診療 當下年紀 >= 12 歲
     *
     * @param dto 使用 patient.birth, nhiExtendTreatmentProcedure.A71
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO lessThanAge12(@NotNull NhiRuleCheckDTO dto) {

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("病患是否在診療當下年紀大於 12 歲")
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
     * 尋找 患者 在 時間區間 內，屬於 建制的健保代碼清單中，且 未超過時間區間 的 NhiExtendTreatmentProcedure
     *
     * @param patientId                     病患 id
     * @param treatmentProcedureId          欲檢驗的處置 id
     * @param currentTreatmentProcedureDate 當前處置的日期 a71 （此項是為了減少重複所加）
     * @param codes                         被限制的健保代碼清單
     * @param limitDays                     間隔時間
     * @return null 或 有衝突的 NhiExtendTreatmentProcedure
     */
    public NhiExtendTreatmentProcedure findPatientTreatmentProcedureAtCodesAndBeforePeriod(
        Long patientId,
        Long treatmentProcedureId,
        LocalDate currentTreatmentProcedureDate,
        @NotNull List<String> codes,
        @NotNull Period limitDays,
        List<Long> excludeTreatmentProcedureIds
    ) {
        List<NhiExtendTreatmentProcedure> matchedNhiExtendTreatmentProcedure = new ArrayList<>();

        List<String> parsedCodes = this.parseNhiCode(codes);

        nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73InOrderByA71Desc(
            patientId,
            parsedCodes)
            .stream()
            .filter(Objects::nonNull)
            .filter(netp -> StringUtils.isNotBlank(netp.getA71()) && netp.getTreatmentProcedure_Id() != null)
            .filter(netp -> !netp.getTreatmentProcedure_Id().equals(treatmentProcedureId))
            .filter(netp -> currentTreatmentProcedureDate.isEqual(DateTimeUtil.transformROCDateToLocalDate(netp.getA71())) ||
                currentTreatmentProcedureDate.isAfter(DateTimeUtil.transformROCDateToLocalDate(netp.getA71())))
            .filter(netp -> excludeTreatmentProcedureIds == null ||
                excludeTreatmentProcedureIds.size() == 0 ||
                !excludeTreatmentProcedureIds.contains(netp.getTreatmentProcedure_Id()))
            .forEach(netpt -> {
                LocalDate pastTxDate = DateTimeUtil.transformROCDateToLocalDate(netpt.getA71());
                if (pastTxDate.plus(limitDays).isEqual(currentTreatmentProcedureDate) || pastTxDate.plus(limitDays).isAfter(currentTreatmentProcedureDate)) {
                    matchedNhiExtendTreatmentProcedure.add(
                        nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(netpt));
                }
            });

        if (matchedNhiExtendTreatmentProcedure.size() > 0) {
            matchedNhiExtendTreatmentProcedure.stream()
                .forEach(mnetp -> {
                    List<NhiExtendDisposalTable> nedt =
                        nhiExtendDisposalRepository.findByDisposal_TreatmentProcedures_Id(mnetp.getId(), NhiExtendDisposalTable.class);
                    if (nedt.size() > 0) {
                        mnetp.setNhiExtendDisposal(nhiExtendDisposalMapper.nhiExtendDisposalTableToNhiExtendDisposal(nedt.get(0)));
                    }
                });
        }

        return matchedNhiExtendTreatmentProcedure.size() > 0 ? matchedNhiExtendTreatmentProcedure.get(0) : null;
    }

    /**
     * 尋找 患者 在 時間區間 內，屬於 建制的健保代碼清單中，且 未超指定時間區間 的 NhiExtendTreatmentProcedure
     *
     * @param patientId            病患 id
     * @param treatmentProcedureId 欲檢驗的處置 id
     * @param duration             指定時間區間
     * @param codes                被限制的健保代碼清單
     * @return null 或 有衝突的 NhiExtendTreatmentProcedure
     */
    public NhiExtendTreatmentProcedure findPatientTreatmentProcedureAtCodesAndBetweenDuration(
        Long patientId,
        Long treatmentProcedureId,
        LocalDateDuration duration,
        @NotNull List<String> codes,
        List<Long> excludeTreatmentProcedureIds
    ) {
        List<NhiExtendTreatmentProcedure> matchedNhiExtendTreatmentProcedure = new ArrayList<>();

        List<String> parsedCodes = this.parseNhiCode(codes);

        nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73InOrderByA71Desc(
            patientId,
            parsedCodes)
            .stream()
            .filter(Objects::nonNull)
            .filter(netp -> StringUtils.isNotBlank(netp.getA71()) && netp.getTreatmentProcedure_Id() != null)
            .filter(netp -> !netp.getTreatmentProcedure_Id().equals(treatmentProcedureId))
            .filter(netp -> excludeTreatmentProcedureIds == null ||
                excludeTreatmentProcedureIds.size() == 0 ||
                !excludeTreatmentProcedureIds.contains(netp.getTreatmentProcedure_Id()))
            .filter(netp -> {
                LocalDate netpLocalDate = DateTimeUtil.transformROCDateToLocalDate(netp.getA71());

                return duration.getBegin().isEqual(netpLocalDate) ||
                    duration.getEnd().isEqual(netpLocalDate) ||
                    duration.getBegin().isBefore(netpLocalDate) && duration.getEnd().isAfter(netpLocalDate);
            })
            .forEach(netpt -> {
                matchedNhiExtendTreatmentProcedure.add(
                    nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(netpt));
            });

        return matchedNhiExtendTreatmentProcedure.size() > 0 ? matchedNhiExtendTreatmentProcedure.get(0) : null;
    }

    /**
     * 尋找 患者 在 時間區間 內，屬於 建制的健保代碼清單中，且 未超過時間區間 多筆的 NhiExtendTreatmentProcedure
     *
     * @param patientId                     病患 id
     * @param treatmentProcedureId          欲檢驗的處置 id
     * @param currentTreatmentProcedureDate 當前處置的日期 a71 （此項是為了減少重複所加）
     * @param codes                         被限制的健保代碼清單
     * @param limitDays                     間隔時間
     * @return null 或 有衝突的 NhiExtendTreatmentProcedure
     */
    public List<NhiExtendTreatmentProcedure> findMultiplePatientTreatmentProcedureAtCodesAndBeforePeriod(
        Long patientId,
        Long treatmentProcedureId,
        LocalDate currentTreatmentProcedureDate,
        @NotNull List<String> codes,
        @NotNull Period limitDays,
        List<Long> excludeTreatmentProcedureIds
    ) {
        List<NhiExtendTreatmentProcedure> matchedNhiExtendTreatmentProcedure = new ArrayList<>();

        List<String> parsedCodes = this.parseNhiCode(codes);

        nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73InOrderByA71Desc(
            patientId,
            parsedCodes)
            .stream()
            .filter(Objects::nonNull)
            .filter(netp -> StringUtils.isNotBlank(netp.getA71()) && netp.getTreatmentProcedure_Id() != null)
            .filter(netp -> !netp.getTreatmentProcedure_Id().equals(treatmentProcedureId))
            .filter(netp -> currentTreatmentProcedureDate.isEqual(DateTimeUtil.transformROCDateToLocalDate(netp.getA71())) ||
                currentTreatmentProcedureDate.isAfter(DateTimeUtil.transformROCDateToLocalDate(netp.getA71())))
            .filter(netp -> excludeTreatmentProcedureIds == null ||
                excludeTreatmentProcedureIds.size() == 0 ||
                !excludeTreatmentProcedureIds.contains(netp.getTreatmentProcedure_Id()))
            .forEach(netpt -> {
                LocalDate pastTxDate = DateTimeUtil.transformROCDateToLocalDate(netpt.getA71());
                if (pastTxDate.plus(limitDays).isEqual(currentTreatmentProcedureDate) || pastTxDate.plus(limitDays).isAfter(currentTreatmentProcedureDate)) {
                    NhiExtendTreatmentProcedure netp = nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(netpt);
                    List<NhiExtendDisposalTable> nedt =
                        nhiExtendDisposalRepository.findByDisposal_TreatmentProcedures_Id(netp.getId(), NhiExtendDisposalTable.class);
                    if (nedt.size() > 0) {
                        netp.setNhiExtendDisposal(nhiExtendDisposalMapper.nhiExtendDisposalTableToNhiExtendDisposal(nedt.get(0)));
                    }
                    matchedNhiExtendTreatmentProcedure.add(netp);
                }
            });

        return matchedNhiExtendTreatmentProcedure;
    }

    /**
     * 尋找 患者 在 時間區間 內，屬於 建制的健保代碼清單中，且 未超過時間區間 且 相同牙位 的 NhiExtendTreatmentProcedure
     *
     * @param patientId                     病患 id
     * @param treatmentProcedureId          欲檢驗的處置 id
     * @param currentTreatmentProcedureDate 當前處置的日期 a71 （此項是為了減少重複所加）
     * @param codes                         被限制的健保代碼清單
     * @param limitDays                     間隔時間
     * @param tooth                         牙位
     * @return null 或 有衝突的 NhiExtendTreatmentProcedure
     */
    public NhiExtendTreatmentProcedure findPatientTreatmentProcedureAtCodesAndBeforePeriodAndTooth(
        Long patientId,
        Long treatmentProcedureId,
        LocalDate currentTreatmentProcedureDate,
        @NotNull List<String> codes,
        @NotNull Period limitDays,
        String tooth,
        List<Long> excludeTreatmentProcedureIds
    ) {
        List<NhiExtendTreatmentProcedure> matchedNhiExtendTreatmentProcedure = new ArrayList<>();

        List<String> parsedCodes = this.parseNhiCode(codes);

        nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73InOrderByA71Desc(
            patientId,
            parsedCodes)
            .stream()
            .filter(Objects::nonNull)
            .filter(netp -> StringUtils.isNotBlank(netp.getA71()) && netp.getTreatmentProcedure_Id() != null)
            .filter(netp -> !netp.getTreatmentProcedure_Id().equals(treatmentProcedureId))
            .filter(netp -> currentTreatmentProcedureDate.isEqual(DateTimeUtil.transformROCDateToLocalDate(netp.getA71())) ||
                currentTreatmentProcedureDate.isAfter(DateTimeUtil.transformROCDateToLocalDate(netp.getA71())))
            .filter(netp -> ToothUtil.splitA74(netp.getA74()).contains(tooth))
            .filter(netp -> excludeTreatmentProcedureIds == null ||
                excludeTreatmentProcedureIds.size() == 0 ||
                !excludeTreatmentProcedureIds.contains(netp.getTreatmentProcedure_Id()))
            .forEach(netpt -> {
                LocalDate pastTxDate = DateTimeUtil.transformROCDateToLocalDate(netpt.getA71());
                if (pastTxDate.plus(limitDays).isEqual(currentTreatmentProcedureDate) || pastTxDate.plus(limitDays).isAfter(currentTreatmentProcedureDate)) {
                    NhiExtendTreatmentProcedure netp = nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(netpt);
                    List<NhiExtendDisposalTable> nedt =
                        nhiExtendDisposalRepository.findByDisposal_TreatmentProcedures_Id(netp.getId(), NhiExtendDisposalTable.class);
                    if (nedt.size() > 0) {
                        netp.setNhiExtendDisposal(nhiExtendDisposalMapper.nhiExtendDisposalTableToNhiExtendDisposal(nedt.get(0)));
                    }
                    matchedNhiExtendTreatmentProcedure.add(
                        nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(netpt));
                }
            });

        return matchedNhiExtendTreatmentProcedure.size() > 0 ? matchedNhiExtendTreatmentProcedure.get(0) : null;
    }

    /**
     * 尋找 患者 在 時間區間 內，屬於 建制的健保代碼清單中，且 未超過時間區間 的 NhiMedicalRecord
     *
     * @param patientId                     病患 id
     * @param currentTreatmentProcedureDate 當前處置的日期 a71 （此項是為了減少重複所加）
     * @param codes                         被限制的健保代碼清單
     * @param limitDays                     間隔時間
     * @return null 或 有衝突的 NhiMedicalRecord
     */
    public NhiMedicalRecord findPatientMediaRecordAtCodesAndBeforePeriod(
        Long patientId,
        LocalDate currentTreatmentProcedureDate,
        @NotNull List<String> codes,
        @NotNull Period limitDays
    ) {
        List<NhiMedicalRecord> matchedNhiMedicalRecord = new ArrayList<>();

        List<String> parsedCodes = this.parseNhiCode(codes);

        nhiMedicalRecordRepository.findByNhiExtendPatient_Patient_IdAndNhiCodeInOrderByDateDesc(
            patientId,
            parsedCodes)
            .stream()
            .filter(Objects::nonNull)
            .filter(nmr -> currentTreatmentProcedureDate.isAfter(DateTimeUtil.transformROCDateToLocalDate(nmr.getDate())))
            .forEach(nmr -> {
                LocalDate pastTxDate = DateTimeUtil.transformROCDateToLocalDate(nmr.getDate());
                if (pastTxDate.plus(limitDays).isAfter(currentTreatmentProcedureDate)) {
                    matchedNhiMedicalRecord.add(nmr);
                }
            });

        return matchedNhiMedicalRecord.size() > 0 ? matchedNhiMedicalRecord.get(0) : null;
    }

    /**
     * 尋找 患者 在 時間區間 內，屬於 建制的健保代碼清單中，且 未超指定時間區間 的 NhiMedicalRecord
     *
     * @param patientId 病患 id
     * @param duration  指定時間區間
     * @param codes     被限制的健保代碼清單
     * @return null 或 有衝突的 NhiMedicalRecord
     */
    public NhiMedicalRecord findPatientMediaRecordAtCodesAndBetweenDuration(
        Long patientId,
        LocalDateDuration duration,
        @NotNull List<String> codes
    ) {
        List<String> parsedCodes = this.parseNhiCode(codes);

        List<NhiMedicalRecord> matchedNhiMedicalRecord = nhiMedicalRecordRepository.findByNhiExtendPatient_Patient_IdAndNhiCodeInOrderByDateDesc(
            patientId,
            parsedCodes)
            .stream()
            .filter(Objects::nonNull)
            .filter(nmr -> {
                LocalDate netpLocalDate = DateTimeUtil.transformROCDateToLocalDate(nmr.getDate());

                return duration.getBegin().isEqual(netpLocalDate) ||
                    duration.getEnd().isEqual(netpLocalDate) ||
                    duration.getBegin().isBefore(netpLocalDate) && duration.getEnd().isAfter(netpLocalDate);
            })
            .collect(Collectors.toList());

        return matchedNhiMedicalRecord.size() > 0 ? matchedNhiMedicalRecord.get(0) : null;
    }

    /**
     * 尋找 患者 在 時間區間 內，屬於 建制的健保代碼清單中，且 未超過時間區間 的 NhiMedicalRecord
     *
     * @param patientId                     病患 id
     * @param currentTreatmentProcedureDate 當前處置的日期 a71 （此項是為了減少重複所加）
     * @param codes                         被限制的健保代碼清單
     * @param limitDays                     間隔時間
     * @return null 或 有衝突的 NhiMedicalRecord
     */
    public List<NhiMedicalRecord> findMultiplePatientMediaRecordAtCodesAndBeforePeriod(
        Long patientId,
        LocalDate currentTreatmentProcedureDate,
        @NotNull List<String> codes,
        @NotNull Period limitDays
    ) {
        List<NhiMedicalRecord> matchedNhiMedicalRecord = new ArrayList<>();

        List<String> parsedCodes = this.parseNhiCode(codes);

        nhiMedicalRecordRepository.findByNhiExtendPatient_Patient_IdAndNhiCodeInOrderByDateDesc(
            patientId,
            parsedCodes)
            .stream()
            .filter(Objects::nonNull)
            .filter(nmr -> currentTreatmentProcedureDate.isAfter(DateTimeUtil.transformROCDateToLocalDate(nmr.getDate())))
            .forEach(nmr -> {
                LocalDate pastTxDate = DateTimeUtil.transformROCDateToLocalDate(nmr.getDate());
                if (pastTxDate.plus(limitDays).isAfter(currentTreatmentProcedureDate)) {
                    matchedNhiMedicalRecord.add(nmr);
                }
            });

        return matchedNhiMedicalRecord;
    }

    public List<NhiExtendTreatmentProcedure> findPatientTreatmentProcedureAtCodes(Long patientId, List<String> codes) {
        List<String> parsedCodes = this.parseNhiCode(codes);
        return nhiExtendTreatmentProcedureRepository
            .findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73InOrderByA71Desc(patientId, parsedCodes)
            .stream()
            .map(nhiExtendTreatmentProcedureMapper::nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable)
            .collect(Collectors.toList());
    }

    public List<NhiMedicalRecord> findPatientMedicalRecordAtCodes(Long patientId, List<String> codes) {
        List<String> parsedCodes = this.parseNhiCode(codes);
        return nhiMedicalRecordRepository.findByNhiExtendPatient_Patient_IdAndNhiCodeInOrderByDateDesc(patientId, parsedCodes);
    }

    /**
     * 指定的診療項目，在病患過去紀錄中（來自診所系統產生的紀錄），是否已經包含 codes，且未達間隔 limitDays。
     *
     * @param dto       使用 patient.id, nhiExtendTreatmentProcedure.id/.a71
     * @param codes     被限制的健保代碼清單
     * @param limitDays 間隔時間
     * @return 後續檢核統一 `回傳` 的介面
     */
    @Deprecated
    public NhiRuleCheckResultDTO isCodeBeforeDate(
        @NotNull NhiRuleCheckDTO dto,
        @NotNull List<String> codes,
        @NotNull Period limitDays
    ) {
        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        // 檢核 nhi extend treatment procedure
        NhiExtendTreatmentProcedure match =
            this.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
                dto.getPatient().getId(),
                dto.getNhiExtendTreatmentProcedure().getId(),
                currentTxDate,
                codes,
                limitDays,
                dto.getExcludeTreatmentProcedureIds());

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("指定的診療項目，在病患過去紀錄中（來自診所系統產生的紀錄），是否已經包含 codes，且未達間隔 limitDays。")
            .validated(match == null);

        if (!result.isValidated()) {
            LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getA71());

            result
                .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                .message(
                    String.format(
                        "%s： %s (%s-%s)，%s 天內不得申報 %s",
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        match.getA73(),
                        this.classifySourceType(
                            NhiRuleCheckSourceType.SYSTEM_RECORD,
                            matchDate,
                            match.getNhiExtendDisposal().getId(),
                            dto
                        ),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                        limitDays.getDays(),
                        dto.getNhiExtendTreatmentProcedure().getA73()
                    )
                );
        }

        return result;
    }

    /**
     * 指定的診療項目，在病患過去紀錄中（來自診所系統產生的紀錄），是否已經包含 codes，且未達間隔 limitDays。
     *
     * @param dto       使用 patient.id, nhiExtendTreatmentProcedure.id/.a71
     * @param codes     被限制的健保代碼清單
     * @param limitDays 間隔時間
     * @param format    回傳訊息格式
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isCodeBeforeDate(
        @NotNull NhiRuleCheckDTO dto,
        @NotNull List<String> codes,
        @NotNull Period limitDays,
        NhiRuleCheckFormat format
    ) {
        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        // 檢核 nhi extend treatment procedure
        NhiExtendTreatmentProcedure match =
            this.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
                dto.getPatient().getId(),
                dto.getNhiExtendTreatmentProcedure().getId(),
                currentTxDate,
                codes,
                limitDays,
                dto.getExcludeTreatmentProcedureIds());

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("指定的診療項目，在病患過去紀錄中（來自診所系統產生的紀錄），是否已經包含 codes，且未達間隔 limitDays。")
            .validated(match == null);

        if (!result.isValidated()) {
            LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getA71());

            String m = "";

            switch (format) {
                case D1_2:
                    m = String.format(
                        NhiRuleCheckFormat.D1_2.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        this.classifySourceType(
                            NhiRuleCheckSourceType.SYSTEM_RECORD,
                            matchDate,
                            match.getNhiExtendDisposal().getId(),
                            dto
                        ),
                        match.getA73(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                        limitDays.getDays(),
                        dto.getNhiExtendTreatmentProcedure().getA73()
                    );
                    break;
                case D4_1:
                    m = String.format(
                        NhiRuleCheckFormat.D4_1.getFormat(),
                        this.classifySourceType(
                            NhiRuleCheckSourceType.SYSTEM_RECORD,
                            matchDate,
                            match.getNhiExtendDisposal().getId(),
                            dto
                        ),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET))
                    );
                    break;
                case D7_1:
                    if (dto.getNhiExtendDisposal() != null &&
                        !"AB".equals(dto.getNhiExtendDisposal().getA23())
                    ) {
                        m = String.format(
                            NhiRuleCheckFormat.D7_1.getFormat(),
                            dto.getNhiExtendTreatmentProcedure().getA73(),
                            limitDays.getDays(),
                            match.getA73(),
                            this.classifySourceType(
                                NhiRuleCheckSourceType.SYSTEM_RECORD,
                                matchDate,
                                match.getNhiExtendDisposal().getId(),
                                dto
                            ),
                            DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET))
                        );
                    }
                    break;
                default:
                    m = String.format(
                        "%s： %s (%s-%s)，%s 天內不得申報 %s",
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        match.getA73(),
                        this.classifySourceType(
                            NhiRuleCheckSourceType.SYSTEM_RECORD,
                            matchDate,
                            match.getNhiExtendDisposal().getId(),
                            dto
                        ),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                        limitDays.getDays(),
                        dto.getNhiExtendTreatmentProcedure().getA73()
                    );
                    break;
            }

            result
                .nhiRuleCheckInfoType(format.getLevel())
                .message(m);
        }

        return result;
    }

    /**
     * 指定的診療項目，在病患過去紀錄中（來自診所系統產生的紀錄），是否已經包含 codes，且未達 指定時間間隔。
     *
     * @param dto      使用 patient.id, nhiExtendTreatmentProcedure.id/.a71
     * @param codes    被限制的健保代碼清單
     * @param duration 指定時間間隔
     * @param format   回傳訊息格式, 目前支援 D4_4
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isCodeBetweenDuration(
        @NotNull NhiRuleCheckDTO dto,
        @NotNull List<String> codes,
        LocalDateDuration duration,
        NhiRuleCheckFormat format
    ) {
        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        // 檢核 nhi extend treatment procedure
        NhiExtendTreatmentProcedure match =
            this.findPatientTreatmentProcedureAtCodesAndBetweenDuration(
                dto.getPatient().getId(),
                dto.getNhiExtendTreatmentProcedure().getId(),
                duration,
                codes,
                dto.getExcludeTreatmentProcedureIds());

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("指定的診療項目，在病患過去紀錄中（來自診所系統產生的紀錄），是否已經包含 codes，且未達間隔 limitDays。")
            .validated(match == null);

        if (!result.isValidated()) {
            LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getA71());

            String m = "";

            switch (format) {
                case D4_1:
                    m = String.format(
                        NhiRuleCheckFormat.D4_1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        this.classifySourceType(
                            NhiRuleCheckSourceType.SYSTEM_RECORD,
                            matchDate,
                            match.getNhiExtendDisposal().getId(),
                            dto
                        ),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET))
                    );
                    break;
                default:
                    m = String.format("");
                    break;
            }

            result
                .nhiRuleCheckInfoType(format.getLevel())
                .message(m);
        }

        return result;
    }

    /**
     * 指定的診療項目，在病患過去紀錄中（來自診所系統產生的紀錄），是否已經包含 codes，且未達間隔 limitDays，且超過 maxTimes。
     *
     * @param dto       使用 patient.id, nhiExtendTreatmentProcedure.id/.a71
     * @param codes     被限制的健保代碼清單
     * @param limitDays 間隔時間
     * @param maxTimes  時間內最大次數
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isCodeBeforeDateWithMaxTimes(
        @NotNull NhiRuleCheckDTO dto,
        @NotNull List<String> codes,
        @NotNull Period limitDays,
        int maxTimes,
        NhiRuleCheckFormat format
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("指定的診療項目，在病患過去紀錄中（來自診所系統產生的紀錄），是否已經包含 codes，且未達間隔 limitDays。")
            .validated(true);

        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        // 檢核 nhi extend treatment procedure
        List<NhiExtendTreatmentProcedure> match =
            this.findMultiplePatientTreatmentProcedureAtCodesAndBeforePeriod(
                dto.getPatient().getId(),
                dto.getNhiExtendTreatmentProcedure().getId(),
                currentTxDate,
                codes,
                limitDays,
                dto.getExcludeTreatmentProcedureIds());


        if (match.size() > maxTimes) {
            String msg = "";
            switch (format) {
                case D5_1:
                    msg = String.format(
                        NhiRuleCheckFormat.D5_1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        match.stream()
                            .map(m -> DateTimeUtil.transformA71ToDisplay(m.getA71()))
                            .collect(Collectors.toList())
                    );
                    break;
                case D1_2:
                    msg = String.format(
                        NhiRuleCheckFormat.D1_2.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        match.get(0).getA73(),
                        NhiRuleCheckSourceType.SYSTEM_RECORD.getValue(),
                        DateTimeUtil.transformA71ToDisplay(
                            match.get(0).getA71()
                        ),
                        limitDays.getDays(),
                        dto.getNhiExtendTreatmentProcedure().getA73()
                    );
                default:
                    break;
            }

            result
                .validated(false)
                .nhiRuleCheckInfoType(format.getLevel())
                .message(msg);
        }

        return result;
    }

    public NhiRuleCheckResultDTO isSinglePhase(
        @NotNull NhiRuleCheckDTO dto
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("處置只能單一象限")
            .validated(true);

        List<ToothUtil.ToothPhase> phases = new ArrayList<>();

        if (dto.getNhiExtendTreatmentProcedure() != null &&
            dto.getNhiExtendTreatmentProcedure().getA73() != null
        ) {
            phases = ToothUtil.markAsPhase(
                ToothUtil.splitA74(
                    dto.getNhiExtendTreatmentProcedure().getA74()
                )
            );
        }

        if (phases.size() > 1) {
            result
                .validated(false)
                .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                .message(
                    String.format(
                        NhiRuleCheckFormat.D4_2.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        NhiRuleCheckSourceType.CURRENT_DISPOSAL.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(
                            DateTimeUtil.transformROCDateToLocalDate(
                                dto.getNhiExtendTreatmentProcedure().getA71()
                            )
                                .atStartOfDay()
                                .toInstant(TimeConfig.ZONE_OFF_SET)
                        ),
                        phases.get(0).toString()
                    )
                );
        }

        return result;
    }

    /**
     * 指定的診療項目，在病患過去紀錄中（來自診所系統產生的紀錄），是否已經包含 codes，且未達間隔 limitDays，且相同象限。
     *
     * @param dto       使用 patient.id, nhiExtendTreatmentProcedure.id/.a71
     * @param codes     被限制的健保代碼清單
     * @param limitDays 間隔時間
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isCodeBeforeDateWithSamePhase(
        @NotNull NhiRuleCheckDTO dto,
        @NotNull List<String> codes,
        @NotNull Period limitDays
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("指定的診療項目，在病患過去紀錄中（來自診所系統產生的紀錄），是否已經包含 codes，且未達間隔 limitDays，且相同象限")
            .validated(true);

        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        // 列出被驗證之牙位所佔象限
        List<String> teeth = ToothUtil.splitA74(dto.getNhiExtendTreatmentProcedure().getA74());
        List<ToothUtil.ToothPhase> currentPhaseSet = ToothUtil.markAsPhase(teeth);

        // 檢核 nhi extend treatment procedure
        List<NhiExtendTreatmentProcedure> matches =
            this.findMultiplePatientTreatmentProcedureAtCodesAndBeforePeriod(
                dto.getPatient().getId(),
                dto.getNhiExtendTreatmentProcedure().getId(),
                currentTxDate,
                codes,
                limitDays,
                dto.getExcludeTreatmentProcedureIds());

        matches.stream()
            .forEach(match -> {
                if (match != null &&
                    result.isValidated()
                ) {
                    List<String> t = ToothUtil.splitA74(match.getA74());
                    List<ToothUtil.ToothPhase> tphs = ToothUtil.markAsPhase(t);
                    List<ToothUtil.ToothPhase> matchPhase = new ArrayList<>();

                    tphs.forEach(tph -> {
                        if (currentPhaseSet.contains(tph)) {
                            matchPhase.add(tph);
                        }
                    });

                    if (matchPhase.size() > 0) {
                        LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getA71());
                        matchPhase.sort(ToothUtil.toothPhaseComparator());
                        result
                            .validated(false)
                            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                            .message(
                                String.format(
                                    NhiRuleCheckFormat.D4_2.getFormat(),
                                    dto.getNhiExtendTreatmentProcedure().getA73(),
                                    classifySourceType(
                                        NhiRuleCheckSourceType.SYSTEM_RECORD,
                                        matchDate,
                                        match.getNhiExtendDisposal().getId(),
                                        dto
                                    ),
                                    DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                                    matchPhase.toString()
                                )
                            );
                    }
                }
            });

        return result;
    }

    /**
     * 指定的診療項目，在病患過去紀錄中（來自IC卡資料的紀錄），是否已經包含 codes，且未達間隔 limitDays，且相同象限。
     *
     * @param dto       使用 patient.id, nhiExtendTreatmentProcedure.id/.a71
     * @param codes     被限制的健保代碼清單
     * @param limitDays 間隔時間
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isCodeBeforeDateByNhiMedicalRecordWithSamePhase(
        @NotNull NhiRuleCheckDTO dto,
        @NotNull List<String> codes,
        @NotNull Period limitDays
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("指定的診療項目，在病患過去紀錄中（來自IC卡資料的紀錄），是否已經包含 codes，且未達間隔 limitDays，且相同象限")
            .validated(true);

        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        // 列出被驗證之牙位所佔象限
        List<String> teeth = ToothUtil.splitA74(dto.getNhiExtendTreatmentProcedure().getA74());
        List<ToothUtil.ToothPhase> currentPhaseSet = ToothUtil.markAsPhase(teeth);

        // 檢核 nhi extend treatment procedure
        List<NhiMedicalRecord> matches = this.findMultiplePatientMediaRecordAtCodesAndBeforePeriod(
            dto.getPatient().getId(),
            currentTxDate,
            codes,
            limitDays);

        matches.stream()
            .forEach(match -> {
                if (match != null &&
                    result.isValidated()
                ) {
                    List<String> t = ToothUtil.splitA74(match.getPart());
                    List<ToothUtil.ToothPhase> tphs = ToothUtil.markAsPhase(t);
                    List<ToothUtil.ToothPhase> matchPhase = new ArrayList<>();

                    tphs.forEach(tph -> {
                        if (currentPhaseSet.contains(tph)) {
                            matchPhase.add(tph);
                        }
                    });

                    if (matchPhase.size() > 0) {
                        LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getDate());
                        result
                            .validated(false)
                            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                            .message(
                                String.format(
                                    NhiRuleCheckFormat.D4_2.getFormat(),
                                    dto.getNhiExtendTreatmentProcedure().getA73(),
                                    this.classifySourceType(
                                        NhiRuleCheckSourceType.NHI_CARD_RECORD,
                                        matchDate,
                                        null,
                                        dto
                                    ),
                                    DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                                    matchPhase.get(0).getNameOfPhase()
                                )
                            );
                    }
                }
            });

        return result;
    }

    /**
     * 指定的診療項目，在病患過去紀錄中（來自診所系統產生的紀錄），是否已經包含 codes，且未達間隔 limitDays，且超過 maxTimes。
     *
     * @param dto       使用 patient.id, nhiExtendTreatmentProcedure.id/.a71
     * @param codes     被限制的健保代碼清單
     * @param limitDays 間隔時間
     * @param maxTimes  時間內最大次數
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isCodeBeforeDateByNhiMedicalRecordWithMaxTimes(
        @NotNull NhiRuleCheckDTO dto,
        @NotNull List<String> codes,
        @NotNull Period limitDays,
        int maxTimes
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("指定的診療項目，在病患過去紀錄中（來自診所系統產生的紀錄），是否已經包含 codes，且未達間隔 limitDays。")
            .validated(true);

        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        // 檢核 nhi extend treatment procedure
        List<NhiMedicalRecord> match =
            this.findMultiplePatientMediaRecordAtCodesAndBeforePeriod(
                dto.getPatient().getId(),
                currentTxDate,
                codes,
                limitDays);


        if (match.size() > maxTimes) {
            LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.get(0).getDate());

            result
                .validated(false)
                .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                .message(
                    String.format(
                        NhiRuleCheckFormat.D5_1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        match.stream()
                            .map(m -> DateTimeUtil.transformA71ToDisplay(m.getDate()))
                            .collect(Collectors.toList())
                    )
                );
        }

        return result;
    }

    /**
     * 指定的診療項目，在病患過去紀錄中（來自健保卡讀取的紀錄），是否已經包含 codes，且未達間隔 limitDays。
     *
     * @param dto       使用 patient.id
     * @param codes     被限制的健保代碼清單
     * @param limitDays 間隔時間
     * @return 後續檢核統一 `回傳` 的介面
     */
    @Deprecated
    public NhiRuleCheckResultDTO isCodeBeforeDateByNhiMedicalRecord(
        @NotNull NhiRuleCheckDTO dto,
        @NotNull List<String> codes,
        @NotNull Period limitDays
    ) {
        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        NhiMedicalRecord match = this.findPatientMediaRecordAtCodesAndBeforePeriod(
            dto.getPatient().getId(),
            currentTxDate,
            codes,
            limitDays);

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("指定的診療項目，在病患過去紀錄中（來自健保卡讀取的紀錄），是否已經包含 codes，且未達間隔 limitDays。")
            .validated(match == null);

        if (!result.isValidated()) {
            LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getDate());

            result
                .validated(false)
                .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                .nhiRuleCheckSourceType(NhiRuleCheckSourceType.NHI_CARD_RECORD)
                .message(
                    String.format(
                        NhiRuleCheckFormat.D1_2.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        match.getNhiCode(),
                        this.classifySourceType(
                            NhiRuleCheckSourceType.NHI_CARD_RECORD,
                            matchDate,
                            null,
                            dto
                        ),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                        limitDays.getDays(),
                        dto.getNhiExtendTreatmentProcedure().getA73()
                    )
                );
        }

        return result;
    }

    /**
     * 指定的診療項目，在病患過去紀錄中（來自健保卡讀取的紀錄），是否已經包含 codes，且未達間隔 limitDays。
     *
     * @param dto       使用 patient.id
     * @param codes     被限制的健保代碼清單
     * @param limitDays 間隔時間
     * @param format    回傳訊息格式
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isCodeBeforeDateByNhiMedicalRecord(
        @NotNull NhiRuleCheckDTO dto,
        @NotNull List<String> codes,
        @NotNull Period limitDays,
        NhiRuleCheckFormat format
    ) {
        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        NhiMedicalRecord match = this.findPatientMediaRecordAtCodesAndBeforePeriod(
            dto.getPatient().getId(),
            currentTxDate,
            codes,
            limitDays);

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("指定的診療項目，在病患過去紀錄中（來自健保卡讀取的紀錄），是否已經包含 codes，且未達間隔 limitDays。")
            .validated(match == null);

        if (!result.isValidated()) {
            LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getDate());
            String m = "";

            switch (format) {
                case D1_2:
                    m = String.format(
                        NhiRuleCheckFormat.D1_2.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        match.getNhiCode(),
                        NhiRuleCheckSourceType.NHI_CARD_RECORD.getValue(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                        limitDays,
                        dto.getNhiExtendTreatmentProcedure().getA73()
                    );
                    break;
                case D4_1:
                    m = String.format(
                        NhiRuleCheckFormat.D4_1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET))
                    );
                    break;
                default:
                    break;
            }

            result
                .validated(false)
                .nhiRuleCheckInfoType(format.getLevel())
                .nhiRuleCheckSourceType(NhiRuleCheckSourceType.NHI_CARD_RECORD)
                .message(m);
        }

        return result;
    }

    /**
     * 指定的診療項目，在病患過去紀錄中（來自健保卡讀取的紀錄），是否已經包含 codes，且未達 只定間隔時間。
     *
     * @param dto      使用 patient.id
     * @param codes    被限制的健保代碼清單
     * @param duration 只定間隔時間
     * @param format   回傳訊息格式
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isCodeBetweenDurationByNhiMedicalRecord(
        @NotNull NhiRuleCheckDTO dto,
        @NotNull List<String> codes,
        LocalDateDuration duration,
        NhiRuleCheckFormat format
    ) {
        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        NhiMedicalRecord match = this.findPatientMediaRecordAtCodesAndBetweenDuration(
            dto.getPatient().getId(),
            duration,
            codes);

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("指定的診療項目，在病患過去紀錄中（來自健保卡讀取的紀錄），是否已經包含 codes，且未達間隔 limitDays。")
            .validated(match == null);

        if (!result.isValidated()) {
            LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getDate());
            String m = "";

            switch (format) {
                case D4_1:
                    m = String.format(
                        NhiRuleCheckFormat.D4_1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        this.classifySourceType(
                            NhiRuleCheckSourceType.NHI_CARD_RECORD,
                            matchDate,
                            null,
                            dto
                        ),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET))
                    );
                    break;
                default:
                    m = String.format("");
                    break;
            }

            result
                .validated(false)
                .nhiRuleCheckInfoType(format.getLevel())
                .nhiRuleCheckSourceType(NhiRuleCheckSourceType.NHI_CARD_RECORD)
                .message(m);
        }

        return result;
    }

    /**
     * 指定的診療項目，在病患過去紀錄中（來自健保卡讀取的紀錄），是否已經包含 codes，且未達 只定間隔時間。
     *
     * @param dto      使用 patient.id
     * @param codes    被限制的健保代碼清單
     * @param duration 只定間隔時間
     * @param month    月份
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isCodeBetweenDurationByNhiMedicalRecord(
        @NotNull NhiRuleCheckDTO dto,
        @NotNull List<String> codes,
        LocalDateDuration duration,
        Long month
    ) {
        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        NhiMedicalRecord match = this.findPatientMediaRecordAtCodesAndBetweenDuration(
            dto.getPatient().getId(),
            duration,
            codes);

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("指定的診療項目，在病患過去紀錄中（來自健保卡讀取的紀錄），是否已經包含 codes，且未達間隔 limitDays。")
            .validated(match == null);

        if (!result.isValidated()) {
            LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getDate());

            String m = "";

            m = String.format(
                NhiRuleCheckFormat.D1_2_2.getFormat(),
                dto.getNhiExtendTreatmentProcedure().getA73(),
                match.getNhiCode(),
                NhiRuleCheckSourceType.NHI_CARD_RECORD.getValue(),
                DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                month,
                dto.getNhiExtendTreatmentProcedure().getA73()
            );

            result
                .nhiRuleCheckInfoType(
                    NhiRuleCheckFormat.D1_2_2.getLevel()
                )
                .message(
                    m
                );
        }

        return result;
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
     * 具有條件地，回訊息作為提醒用，檢核狀況算審核通過
     *
     * @param dto        根據 predicate 的不同許用不同的內容
     * @param message    應回傳訊息
     * @param predicates 條件式
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO addNotificationWithClause(
        NhiRuleCheckDTO dto,
        String message,
        Predicate<NhiRuleCheckDTO>... predicates
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.NONE)
            .validateTitle("具有條件地，回訊息作為提醒用，檢核狀況算審核通過")
            .validated(true)
            .message(message);

        if (Arrays.stream(predicates).allMatch(p -> p.test(dto))) {
            result.nhiRuleCheckInfoType(NhiRuleCheckInfoType.INFO)
                .message(message);
        }

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
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.WARNING)
            .validateTitle("限制牙面在 isAllLimitedSurface 以下");

        switch (sc) {
            case MIN_1_SURFACES:
                result
                    .validated(
                        dto.getNhiExtendTreatmentProcedure().getA75() != null &&
                            dto.getNhiExtendTreatmentProcedure().getA75().length() >= SurfaceConstraint.MIN_1_SURFACES.getLimitNumber()
                    );

                if (!result.isValidated()) {
                    result.message(SurfaceConstraint.MIN_1_SURFACES.getErrorMessage());
                }

                break;
            case MIN_2_SURFACES:
                result
                    .validated(
                        dto.getNhiExtendTreatmentProcedure().getA75() != null &&
                            dto.getNhiExtendTreatmentProcedure().getA75().length() >= SurfaceConstraint.MIN_2_SURFACES.getLimitNumber()
                    );

                if (!result.isValidated()) {
                    result.message(SurfaceConstraint.MIN_2_SURFACES.getErrorMessage());
                }

                break;
            case MIN_3_SURFACES:
                result
                    .validated(
                        dto.getNhiExtendTreatmentProcedure().getA75() != null &&
                            dto.getNhiExtendTreatmentProcedure().getA75().length() >= SurfaceConstraint.MIN_3_SURFACES.getLimitNumber()
                    );

                if (!result.isValidated()) {
                    result.message(SurfaceConstraint.MIN_3_SURFACES.getErrorMessage());
                }

                break;
            case MAX_2_SURFACES:
                result
                    .validated(
                        dto.getNhiExtendTreatmentProcedure().getA75() == null ||
                            dto.getNhiExtendTreatmentProcedure().getA75().length() <= SurfaceConstraint.MAX_2_SURFACES.getLimitNumber());
                if (!result.isValidated()) {
                    result
                        .nhiRuleCheckInfoType(NhiRuleCheckInfoType.WARNING)
                        .message(SurfaceConstraint.MAX_2_SURFACES.getErrorMessage());
                }

                break;
            case MAX_3_SURFACES:
                result
                    .validated(
                        dto.getNhiExtendTreatmentProcedure().getA75() == null ||
                            dto.getNhiExtendTreatmentProcedure().getA75().length() <= SurfaceConstraint.MAX_3_SURFACES.getLimitNumber());
                if (!result.isValidated()) {
                    result
                        .nhiRuleCheckInfoType(NhiRuleCheckInfoType.WARNING)
                        .message(SurfaceConstraint.MAX_3_SURFACES.getErrorMessage());
                }
                break;
            case MUST_HAVE_M_D_O:
                result
                    .validated(
                        dto.getNhiExtendTreatmentProcedure().getA75() != null &&
                            dto.getNhiExtendTreatmentProcedure().getA75().matches(SurfaceConstraint.MUST_HAVE_M_D_O.getLimitRegex()));
                if (!result.isValidated()) {
                    result
                        .nhiRuleCheckInfoType(NhiRuleCheckInfoType.WARNING)
                        .message(SurfaceConstraint.MUST_HAVE_M_D_O.getErrorMessage());
                }
                break;
            case EQUAL_4_SURFACES:
                result
                    .validated(
                        dto.getNhiExtendTreatmentProcedure().getA75() != null &&
                        dto.getNhiExtendTreatmentProcedure().getA75().length() ==
                            SurfaceConstraint.EQUAL_4_SURFACES.getLimitNumber()
                    );

                if (!result.isValidated()) {
                    result.message(SurfaceConstraint.EQUAL_4_SURFACES.getErrorMessage());
                }

                break;
            default:
                break;
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
     * 病患 牙齒 是否有 健保代碼 於某時間前已被申報過
     *
     * @param dto                     使用 nhiExtendTreatmentProcedure.id/a71/a73/a74, patient.id,
     * @param codes                   被限制的健保代碼清單
     * @param deciduousToothLimitDays 為乳牙時，所需時間間隔
     * @param permanentToothLimitDays 為恆牙時，所需時間間隔
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isPatientToothAtCodesBeforePeriod(
        NhiRuleCheckDTO dto,
        List<String> codes,
        Period deciduousToothLimitDays,
        Period permanentToothLimitDays,
        NhiRuleCheckFormat format
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("病患 牙齒 是否有 健保代碼 於某時間前已被申報過")
            .validated(true);

        List<String> teeth = ToothUtil.splitA74(dto.getNhiExtendTreatmentProcedure().getA74());

        teeth.stream()
            .forEach(tooth -> {
                Period selectedLimitDay = ToothUtil.validatedToothConstraint(ToothConstraint.DECIDUOUS_TOOTH, tooth)
                    ? deciduousToothLimitDays
                    : permanentToothLimitDays;
                NhiExtendTreatmentProcedure match = this.findPatientTreatmentProcedureAtCodesAndBeforePeriodAndTooth(
                    dto.getPatient().getId(),
                    dto.getNhiExtendTreatmentProcedure().getId(),
                    DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()),
                    codes,
                    selectedLimitDay,
                    tooth,
                    dto.getExcludeTreatmentProcedureIds()
                );

                if (match != null &&
                    result.isValidated()
                ) {
                    LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getA71());

                    String msg = "";

                    switch (format) {
                        case D1_2:
                            msg = String.format(
                                NhiRuleCheckFormat.D1_2.getFormat(),
                                dto.getNhiExtendTreatmentProcedure().getA73(),
                                match.getA73(),
                                this.classifySourceType(
                                    NhiRuleCheckSourceType.SYSTEM_RECORD,
                                    matchDate,
                                    match.getNhiExtendDisposal() != null ? match.getNhiExtendDisposal().getId() : null,
                                    dto
                                ),
                                DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                    matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                                ToothUtil.validatedToothConstraint(ToothConstraint.DECIDUOUS_TOOTH, tooth)
                                    ? deciduousToothLimitDays.getDays()
                                    : permanentToothLimitDays.getDays(),
                                dto.getNhiExtendTreatmentProcedure().getA73()
                            );
                            break;
                        case D1_3:
                            msg = String.format(
                                NhiRuleCheckFormat.D1_3.getFormat(),
                                dto.getNhiExtendTreatmentProcedure().getA73(),
                                tooth,
                                match.getA73(),
                                this.classifySourceType(
                                    NhiRuleCheckSourceType.SYSTEM_RECORD,
                                    matchDate,
                                    match.getNhiExtendDisposal() != null ? match.getNhiExtendDisposal().getId() : null,
                                    dto
                                ),
                                DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                    matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET))
                            );
                            break;
                        case D4_1:
                            msg = String.format(
                                NhiRuleCheckFormat.D4_1.getFormat(),
                                dto.getNhiExtendTreatmentProcedure().getA73(),
                                this.classifySourceType(
                                    NhiRuleCheckSourceType.SYSTEM_RECORD,
                                    matchDate,
                                    match.getNhiExtendDisposal() != null ? match.getNhiExtendDisposal().getId() : null,
                                    dto
                                ),
                                DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                    matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)
                                )
                            );
                            break;
                        case D7_2:
                            msg = String.format(
                                NhiRuleCheckFormat.D7_2.getFormat(),
                                dto.getNhiExtendTreatmentProcedure().getA73(),
                                ToothUtil.validatedToothConstraint(ToothConstraint.DECIDUOUS_TOOTH, tooth)
                                    ? deciduousToothLimitDays.getDays()
                                    : permanentToothLimitDays.getDays(),
                                match.getA73(),
                                this.classifySourceType(
                                    NhiRuleCheckSourceType.SYSTEM_RECORD,
                                    matchDate,
                                    match.getNhiExtendDisposal() != null ? match.getNhiExtendDisposal().getId() : null,
                                    dto
                                ),
                                DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                    matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                                tooth
                            );
                            break;
                        case W6_1:
                            msg = String.format(
                                NhiRuleCheckFormat.W6_1.getFormat(),
                                dto.getNhiExtendTreatmentProcedure().getA73(),
                                ToothUtil.validatedToothConstraint(ToothConstraint.DECIDUOUS_TOOTH, tooth)
                                    ? deciduousToothLimitDays.getDays()
                                    : permanentToothLimitDays.getDays(),
                                match.getA73(),
                                this.classifySourceType(
                                    NhiRuleCheckSourceType.SYSTEM_RECORD,
                                    matchDate,
                                    match.getNhiExtendDisposal() != null ? match.getNhiExtendDisposal().getId() : null,
                                    dto
                                ),
                                DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                    matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                                tooth
                            );
                            break;
                        default:
                            break;
                    }

                    result
                        .validated(false)
                        .nhiRuleCheckInfoType(format.getLevel())
                        .message(msg);
                }
            });

        return result;
    }

    /**
     * 病患 牙齒 是否有 健保代碼 於某時間前已被申報過 in IC card
     *
     * @param dto                     使用 nhiExtendTreatmentProcedure.id/a71/a73/a74, patient.id,
     * @param codes                   被限制的健保代碼清單
     * @param deciduousToothLimitDays 為乳牙時，所需時間間隔
     * @param permanentToothLimitDays 為恆牙時，所需時間間隔
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isPatientToothAtCodesBeforePeriodByNhiMedicalRecord(
        NhiRuleCheckDTO dto,
        List<String> codes,
        Period deciduousToothLimitDays,
        Period permanentToothLimitDays,
        NhiRuleCheckFormat format
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("病患 牙齒 是否有 健保代碼 於某時間前已被申報過 in IC card")
            .validated(true);

        List<String> teeth = ToothUtil.splitA74(dto.getNhiExtendTreatmentProcedure().getA74());

        teeth.stream()
            .forEach(tooth -> {
                Period selectedLimitDay = ToothUtil.validatedToothConstraint(ToothConstraint.DECIDUOUS_TOOTH, tooth)
                    ? deciduousToothLimitDays
                    : permanentToothLimitDays;
                NhiMedicalRecord match = this.findPatientMediaRecordAtCodesAndBeforePeriod(
                    dto.getPatient().getId(),
                    DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()),
                    codes,
                    selectedLimitDay
                );

                if (match == null) {
                    return;
                } else if (match != null &&
                    ToothUtil.splitA74(match.getPart()).contains(tooth) &&
                    result.isValidated()
                ) {
                    LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getPart());

                    String msg = "";

                    switch (format) {
                        case D1_2:
                            msg = String.format(
                                NhiRuleCheckFormat.D1_2.getFormat(),
                                dto.getNhiExtendTreatmentProcedure().getA73(),
                                match.getNhiCode(),
                                NhiRuleCheckSourceType.NHI_CARD_RECORD.getValue(),
                                DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                    matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                                ToothUtil.validatedToothConstraint(ToothConstraint.DECIDUOUS_TOOTH, tooth)
                                    ? deciduousToothLimitDays.getDays()
                                    : permanentToothLimitDays.getDays(),
                                dto.getNhiExtendTreatmentProcedure().getA73()
                            );
                            break;
                        case D1_3:
                            msg = String.format(
                                NhiRuleCheckFormat.D1_3.getFormat(),
                                dto.getNhiExtendTreatmentProcedure().getA73(),
                                tooth,
                                match.getNhiCode(),
                                NhiRuleCheckSourceType.NHI_CARD_RECORD.getValue(),
                                DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                    matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET))
                            );
                            break;
                        default:
                            break;
                    }

                    result
                        .validated(false)
                        .nhiRuleCheckInfoType(format.getLevel())
                        .message(msg);
                }
            });

        return result;
    }

    /**
     * 檢查 nhi extend disposal 是否被調整為 指定部分代碼
     *
     * @param dto 使用 nhiExtendDisposal.patientIdentity
     * @param cc  部分負擔代碼 a.k.a patientIdentity
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isPatientIdentityInclude(NhiRuleCheckDTO dto, CopaymentCode cc) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("檢查 nhi extend disposal 是否被調整為 指定部分代碼")
            .validated(
                StringUtils.isNotBlank(dto.getNhiExtendDisposal().getPatientIdentity()) &&
                    dto.getNhiExtendDisposal().getPatientIdentity().equals(cc.getCode()));

        if (!result.isValidated()) {
            if (CopaymentCode._001.getCode().equals(cc.getCode())) {
                result
                    .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                    .message(CopaymentCode._001.getNotification());
            }
        }

        return result;
    }

    /**
     * 當檢核都成功的狀況下，呼叫此 method 。查詢最近一筆的治療紀錄，並回傳成功訊息。
     *
     * @param dto 使用 patient.id, nhiExtendTreatmentProcedure.a73, nhiExtendDisposal.disposal.id
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO appendSuccessSourceInfo(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.SUCCESS)
            .validateTitle("當檢核都成功的狀況下，呼叫此 method 。查詢最近一筆的治療紀錄，並回傳成功訊息")
            .validated(true);

        LocalDate currentDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        // 查詢系統最新一筆資料
        List<NhiExtendTreatmentProcedureTable> netpts;
        if (dto.getNhiExtendDisposal() != null &&
            dto.getNhiExtendDisposal().getDisposal() != null &&
            dto.getNhiExtendDisposal().getDisposal().getId() != null
        ) {
            netpts = nhiExtendTreatmentProcedureRepository
                .findByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73AndTreatmentProcedure_Disposal_IdNotAndTreatmentProcedure_IdNotInOrderByA71Desc(
                    dto.getPatient().getId(),
                    dto.getNhiExtendTreatmentProcedure().getA73(),
                    dto.getNhiExtendDisposal().getDisposal().getId(),
                    dto.getExcludeTreatmentProcedureIds() == null ? Arrays.asList(0L) : dto.getExcludeTreatmentProcedureIds()
                );
        } else {
            netpts = nhiExtendTreatmentProcedureRepository
                .findByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73AndTreatmentProcedure_IdNotInOrderByA71Desc(
                    dto.getPatient().getId(),
                    dto.getNhiExtendTreatmentProcedure().getA73(),
                    dto.getExcludeTreatmentProcedureIds() == null ? Arrays.asList(0L) : dto.getExcludeTreatmentProcedureIds()
                );
        }
        // 取得紀錄中，離處置單時間最近的資料
        Optional<NhiExtendTreatmentProcedureTable> optionalNhiExtendTreatmentProcedureTable = netpts.stream().filter(netpt -> {
            LocalDate historyDate = null;
            if (netpt != null &&
                netpt.getA71() != null
            ) {
                historyDate = DateTimeUtil.transformROCDateToLocalDate(netpt.getA71());
            }
            return historyDate != null && historyDate.isEqual(currentDate) || historyDate != null && historyDate.isBefore(currentDate);
        }).findFirst();

        if (optionalNhiExtendTreatmentProcedureTable.isPresent()) {
            result.message(
                String.format(
                    "目前可申報, 近一次處置為系統中 %s （為提升準確率，請常讀取 IC 卡取得最新資訊）",
                    DateTimeUtil.transformA71ToDisplay(optionalNhiExtendTreatmentProcedureTable.get().getA71())
                )
            );
            return result;
        }

        // 查詢已紀錄健保IC卡最新一筆資料
        Optional<NhiMedicalRecord> optionalNhiMedicalRecord = nhiMedicalRecordRepository.findByNhiExtendPatient_Patient_IdAndNhiCodeOrderByDateDesc(
            dto.getPatient().getId(),
            dto.getNhiExtendTreatmentProcedure().getA73()).stream()
            .filter(nmr -> {
                LocalDate historyDate = null;
                if (nmr != null &&
                    nmr.getDate() != null
                ) {
                    historyDate = DateTimeUtil.transformROCDateToLocalDate(nmr.getDate());
                }
                return historyDate != null && historyDate.isEqual(currentDate) || historyDate != null && historyDate.isBefore(currentDate);
            })
            .findFirst();

        if (optionalNhiMedicalRecord.isPresent()) {
            result.message(
                String.format(
                    "目前可申報, 近一次處置為健保IC卡中 %s （為提升準確率，請常讀取 IC 卡取得最新資訊）",
                    DateTimeUtil.transformA71ToDisplay(optionalNhiMedicalRecord.get().getDate())
                )
            );
            return result;
        }

        return result.message("系統及健保卡皆無紀錄，請查詢雲端藥歷取得正確資訊");
    }

    /**
     * 檢查同一處置單，是否沒有健保定義的其他衝突診療
     *
     * @param dto 使用 includeNhiCodes
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isNoConflictNhiCode(NhiRuleCheckDTO dto, List<String> conflictCodes) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
            .validateTitle("檢查同一處置單，是否沒有健保定義的其他衝突診療")
            .validated(true);

        List<String> parsedCodes = this.parseNhiCode(conflictCodes);

        if (dto.getIncludeNhiCodes() != null &&
            dto.getIncludeNhiCodes().stream()
                .filter(Objects::nonNull)
                .anyMatch(parsedCodes::contains)) {
            result.validated(false)
                .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                .message(
                    String.format(
                        "不得與 %s 同時申報",
                        conflictCodes.toString()
                    )
                );
        }

        return result;
    }

    /**
     * 檢查同一處置單，是否沒有健保定義的其他衝突診療
     *
     * @param dto 使用 includeNhiCodes
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isNoSelfConflictNhiCode(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
            .validateTitle("檢查同一處置單，是否沒有健保定義的其他衝突診療")
            .validated(true);

        if (dto.getIncludeNhiCodes() != null &&
            dto.getIncludeNhiCodes().stream()
                .filter(Objects::nonNull)
                .filter(code -> dto.getNhiExtendTreatmentProcedure() != null &&
                    code.equals(dto.getNhiExtendTreatmentProcedure().getA73()))
                .count() > 1
        ) {
            result.validated(false)
                .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                .message(
                    String.format(
                        "同處置單已有 %s",
                        dto.getNhiExtendTreatmentProcedure().getA73()
                    )
                );
        }

        return result;
    }

    /**
     * 檢查同一處置單，是否沒有健保定義 必須 包含的診療
     *
     * @param dto 使用 includeNhiCodes
     * @return 後續檢核統一 `回傳` 的介面
     */
    @Deprecated
    public NhiRuleCheckResultDTO isMustIncludeNhiCode(NhiRuleCheckDTO dto, List<String> mustIncludeCodes) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
            .validateTitle("檢查同一處置單，是否沒有健保定義必須包含的診療")
            .validated(true);

        List<String> parsedCodes = this.parseNhiCode(mustIncludeCodes);

        if (dto.getIncludeNhiCodes() == null ||
            dto.getIncludeNhiCodes().stream()
                .filter(Objects::nonNull)
                .filter(parsedCodes::contains)
                .collect(Collectors.toList())
                .size() == 0
        ) {
            result.validated(false)
                .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                .message(
                    String.format(
                        NhiRuleCheckFormat.W3_1.getFormat(),
                        StringUtils.join(mustIncludeCodes, "/")
                    )
                );
        }

        return result;
    }

    /**
     * 檢查系統資料，過去時間，包含任何治療紀錄
     *
     * @param dto 使用 patient id
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isNoTreatmentInPeriod(NhiRuleCheckDTO dto, Period limitDays) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
            .validateTitle("檢查系統資料，過去時間，包含任何治療紀錄查過去時間")
            .validated(true);


        LocalDate currentDate = null;
        if (dto.getNhiExtendTreatmentProcedure() != null &&
            dto.getNhiExtendTreatmentProcedure().getA71() != null
        ) {
            currentDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());
        }

        if (dto.getPatient() != null &&
            dto.getPatient().getId() != null &&
            currentDate != null
        ) {
            Long disposalId =
                dto.getNhiExtendDisposal() != null &&
                    dto.getNhiExtendDisposal().getDisposal() != null &&
                    dto.getNhiExtendDisposal().getDisposal().getId() != null
                    ? dto.getNhiExtendDisposal().getDisposal().getId()
                    : 0L;
            Optional<NhiExtendTreatmentProcedureTable> optionalNetpt = nhiExtendTreatmentProcedureRepository
                .findTop1ByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndTreatmentProcedure_Disposal_DateTimeBetweenAndTreatmentProcedure_Disposal_IdNotInOrderByTreatmentProcedure_Disposal_DateTimeDesc(
                    dto.getPatient().getId(),
                    currentDate.atStartOfDay(TimeConfig.ZONE_OFF_SET).toInstant().minus(limitDays.getDays(), ChronoUnit.DAYS),
                    currentDate.atTime(LocalTime.MAX).atZone(TimeConfig.ZONE_OFF_SET).toInstant(),
                    disposalId
                );

            if (optionalNetpt.isPresent()) {
                List<NhiExtendDisposalTable> ned =
                    nhiExtendDisposalRepository.findByDisposal_TreatmentProcedures_Id(optionalNetpt.get().getTreatmentProcedure_Id(), NhiExtendDisposalTable.class);

                result.validated(false)
                    .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                    .message(
                        String.format(
                            NhiRuleCheckFormat.D1_3.getFormat(),
                            dto.getNhiExtendTreatmentProcedure().getA73(),
                            ToothUtil.multipleToothToDisplay(optionalNetpt.get().getA74()),
                            optionalNetpt.get().getA73(),
                            this.classifySourceType(
                                NhiRuleCheckSourceType.SYSTEM_RECORD,
                                ned.size() > 0 ? ned.get(0).getDate() : null,
                                ned.size() > 0 ? ned.get(0).getId() : null,
                                dto
                            ),
                            DateTimeUtil.transformA71ToDisplay(optionalNetpt.get().getA71())
                        )
                    );
            }

        }

        return result;
    }

    /**
     * 檢查IC紀錄，過去三年時間，包含任何治療紀錄
     *
     * @param dto 使用 patient id
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isNoTreatmentInPeriodByNhiMedicalRecord(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
            .validateTitle("檢查IC紀錄，過去時間，包含任何治療紀錄")
            .validated(true);


        LocalDate currentDate = null;
        if (dto.getNhiExtendTreatmentProcedure() != null &&
            dto.getNhiExtendTreatmentProcedure().getA71() != null
        ) {
            currentDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());
        }

        if (dto.getPatient() != null &&
            dto.getPatient().getId() != null &&
            currentDate != null
        ) {
            Instant currentDateTime = currentDate.atStartOfDay().toInstant(ZoneOffset.UTC);
            Instant ym3thDateTime = currentDate.atStartOfDay().toInstant(ZoneOffset.UTC).minus(DateTimeUtil.NHI_36_MONTH.getDays(), ChronoUnit.DAYS);
            String rocDateString = DateTimeUtil.transformLocalDateToRocDate(currentDate.atStartOfDay().toInstant(ZoneOffset.UTC));
            String rocDateStringYear = rocDateString.substring(0, 3);
            String ym1st = rocDateStringYear.concat("%");
            String ym2nd = String.valueOf(Integer.parseInt(rocDateStringYear) - 1).concat("%");
            String ym3th = String.valueOf(Integer.parseInt(rocDateStringYear) - 2).concat("%");

            Optional<NhiMedicalRecordDTO> optionalNmr = nhiMedicalRecordRepository
                .findByNhiExtendPatient_Patient_IdAndDateLikeOrNhiExtendPatient_Patient_IdAndDateLikeOrNhiExtendPatient_Patient_IdAndDateLikeOrderByDateDesc(
                    dto.getPatient().getId(),
                    ym1st,
                    dto.getPatient().getId(),
                    ym2nd,
                    dto.getPatient().getId(),
                    ym3th
                ).stream()
                .filter(nmr -> {
                    Instant nmrDateTime = DateTimeUtil.transformROCDateToLocalDate(nmr.getDate()).atStartOfDay().toInstant(ZoneOffset.UTC);
                    return nmrDateTime.isAfter(ym3thDateTime) && nmrDateTime.isBefore(currentDateTime);
                })
                .findFirst();

            if (optionalNmr.isPresent()) {
                result.validated(false)
                    .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                    .message(
                        String.format(
                            "建議 %s 後再行申報，近一次處置為健保IC卡中 %s",
                            currentDate.plus(DateTimeUtil.NHI_36_MONTH),
                            DateTimeUtil.transformA71ToDisplay(optionalNmr.get().getDate())
                        )
                    );
            }

        }

        return result;
    }

    /**
     * 任意時間點未曾申報過指定代碼
     *
     * @param dto
     * @param codes
     * @return
     */
    public NhiRuleCheckResultDTO isNoTreatment(
        NhiRuleCheckDTO dto,
        List<String> codes
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
            .validated(true)
            .validateTitle("任意時間點未曾申報過指定代碼");

        List<NhiExtendTreatmentProcedure> matches =
            this.findPatientTreatmentProcedureAtCodes(dto.getPatient().getId(), codes);

        result.validated(
            matches == null ||
                matches.size() == 0
        );

        if (!result.isValidated()) {
            NhiExtendTreatmentProcedure match = matches.get(0);
            List<NhiExtendDisposalTable> nedt =
                nhiExtendDisposalRepository.findByDisposal_TreatmentProcedures_Id(match.getId(), NhiExtendDisposalTable.class);
            result.message(
                String.format(
                    NhiRuleCheckFormat.D1_1.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73(),
                    match.getA73(),
                    this.classifySourceType(
                        NhiRuleCheckSourceType.SYSTEM_RECORD,
                        DateTimeUtil.transformROCDateToLocalDate(match.getA71()),
                        nedt != null && nedt.size() > 0 ? nedt.get(0).getId() : null,
                        dto
                    ),
                    DateTimeUtil.transformA71ToDisplay(match.getA71()),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            );
        }

        return result;
    }

    /**
     * 任意時間點未曾申報過指定代碼 (資料來源 IC)
     *
     * @param dto
     * @param codes
     * @return
     */
    public NhiRuleCheckResultDTO isNoTreatmentByNhiMedicalRecord(
        NhiRuleCheckDTO dto,
        List<String> codes
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
            .validated(true)
            .validateTitle("任意時間點未曾申報過指定代碼 (資料來源 IC)");

        List<NhiMedicalRecord> matches =
            this.findPatientMedicalRecordAtCodes(dto.getPatient().getId(), codes);

        result.validated(
            matches == null ||
                matches.size() == 0
        );

        if (!result.isValidated()) {
            NhiMedicalRecord match = matches.get(0);
            result.message(
                String.format(
                    NhiRuleCheckFormat.D1_1.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73(),
                    match.getNhiCode(),
                    NhiRuleCheckSourceType.NHI_CARD_RECORD.getValue(),
                    DateTimeUtil.transformA71ToDisplay(match.getDate()),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            );
        }

        return result;
    }

    /**
     * 同牙未曾申報過，指定代碼
     *
     * @param dto
     * @param codes
     * @return
     */
    public NhiRuleCheckResultDTO isNoTreatmentAtSpecificTooth(
        NhiRuleCheckDTO dto,
        List<String> codes
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
            .validated(true)
            .validateTitle("同牙未曾申報過，指定代碼");

        List<String> currentTeeth = ToothUtil.splitA74(dto.getNhiExtendTreatmentProcedure().getA74());

        this.findPatientTreatmentProcedureAtCodes(dto.getPatient().getId(), codes)
            .forEach(netp -> {
                List<String> oldTeeth = ToothUtil.splitA74(netp.getA74());
                currentTeeth
                    .forEach(currentTooth -> {
                        if (result.isValidated() && oldTeeth.contains(currentTooth)) {
                            List<NhiExtendDisposalTable> nedt =
                                nhiExtendDisposalRepository.findByDisposal_TreatmentProcedures_Id(netp.getId(), NhiExtendDisposalTable.class);

                            result.validated(false)
                                .message(
                                    String.format(
                                        NhiRuleCheckFormat.D1_3.getFormat(),
                                        dto.getNhiExtendTreatmentProcedure().getA73(),
                                        currentTooth,
                                        netp.getA73(),
                                        this.classifySourceType(
                                            NhiRuleCheckSourceType.SYSTEM_RECORD,
                                            DateTimeUtil.transformROCDateToLocalDate(netp.getA71()),
                                            nedt != null && nedt.size() > 0 ? nedt.get(0).getId() : null,
                                            dto
                                        ),
                                        DateTimeUtil.transformA71ToDisplay(netp.getA71())
                                    )
                                );
                        }
                    });
            });

        return result;
    }

    /**
     * 同牙未曾申報過，指定代碼 (資料來源 IC)
     *
     * @param dto
     * @param codes
     * @return
     */
    public NhiRuleCheckResultDTO isNoNhiMedicalRecordAtSpecificTooth(
        NhiRuleCheckDTO dto,
        List<String> codes
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
            .validated(true)
            .validateTitle("同牙未曾申報過，指定代碼 (資料來源 IC)");

        List<String> currentTeeth = ToothUtil.splitA74(dto.getNhiExtendTreatmentProcedure().getA74());

        this.findPatientMedicalRecordAtCodes(dto.getPatient().getId(), codes)
            .forEach(netp -> {
                List<String> oldTeeth = ToothUtil.splitA74(netp.getPart());
                currentTeeth
                    .forEach(currentTooth -> {
                        if (result.isValidated() && oldTeeth.contains(currentTooth)) {
                            List<NhiExtendDisposalTable> nedt =
                                nhiExtendDisposalRepository.findByDisposal_TreatmentProcedures_Id(netp.getId(), NhiExtendDisposalTable.class);

                            result.validated(false)
                                .message(
                                    String.format(
                                        NhiRuleCheckFormat.D1_3.getFormat(),
                                        dto.getNhiExtendTreatmentProcedure().getA73(),
                                        currentTooth,
                                        netp.getPart(),
                                        this.classifySourceType(
                                            NhiRuleCheckSourceType.SYSTEM_RECORD,
                                            DateTimeUtil.transformROCDateToLocalDate(netp.getDate()),
                                            nedt != null && nedt.size() > 0 ? nedt.get(0).getId() : null,
                                            dto
                                        ),
                                        DateTimeUtil.transformA71ToDisplay(netp.getDate())
                                    )
                                );
                        }
                    });
            });

        return result;
    }

    public NhiRuleCheckResultDTO isTreatmentDependOnCodeInDuration(
        NhiRuleCheckDTO dto,
        List<String> codes,
        Period limitDays,
        NhiRuleCheckFormat format
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
            .validateTitle("指定時間內，曾經有指定治療項目")
            .validated(true);

        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        NhiExtendTreatmentProcedure match =
            this.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
                dto.getPatient().getId(),
                dto.getNhiExtendTreatmentProcedure().getId(),
                currentTxDate,
                codes,
                limitDays,
                dto.getExcludeTreatmentProcedureIds()
            );

        if (match == null) {
            String msg = "";

            switch (format) {
                case D8_1:
                    msg = String.format(
                        NhiRuleCheckFormat.D8_1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        limitDays.getDays(),
                        codes.toString()
                    );
                    break;
                case W3_1:
                    msg = String.format(
                        NhiRuleCheckFormat.W3_1.getFormat(),
                        codes
                    );
                default:
                    break;
            }

            result.validated(false)
                .nhiRuleCheckInfoType(format.getLevel())
                .message(msg);
        }

        return result;
    }

    public NhiRuleCheckResultDTO isNhiMedicalRecordDependOnCodeInDuration(
        NhiRuleCheckDTO dto,
        List<String> codes,
        Period limitDays,
        NhiRuleCheckFormat format
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
            .validateTitle("指定時間內，曾經有指定治療項目")
            .validated(true);

        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        NhiMedicalRecord match = this.findPatientMediaRecordAtCodesAndBeforePeriod(
            dto.getPatient().getId(),
            currentTxDate,
            codes,
            limitDays
        );

        if (match == null) {
            String msg = "";

            switch (format) {
                case D8_1:
                    msg = String.format(
                        NhiRuleCheckFormat.D8_1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        limitDays.getDays(),
                        codes.toString()
                    );
                    break;
                default:
                    break;
            }

            result.validated(false)
                .nhiRuleCheckInfoType(format.getLevel())
                .message(msg);
        }

        return result;
    }

    /**
     * 89XXX special: 前30天內不得有89006C，但如果這中間有90001C, 90002C, 90003C, 90019C, 90020C則例外
     *
     * @param dto patient.id, netp.id, excludeTreamentProcedureId
     * @return
     */
    public NhiRuleCheckResultDTO specificRule_1_for89XXXC(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
            .validated(true)
            .validateTitle("89XXX special: 前30天內不得有89006C，但如果這中間有90001C, 90002C, 90003C, 90019C, 90020C則例外");

        NhiExtendTreatmentProcedure targetTp =
            this.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
                dto.getPatient().getId(),
                dto.getNhiExtendTreatmentProcedure().getId(),
                DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()),
                Arrays.asList("89006C"),
                DateTimeUtil.NHI_1_MONTH,
                dto.getExcludeTreatmentProcedureIds()
            );

        if (targetTp != null) {
            NhiExtendTreatmentProcedure outOfLimitationClause =
                this.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
                    dto.getPatient().getId(),
                    dto.getNhiExtendTreatmentProcedure().getId(),
                    DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()),
                    Arrays.asList("90001C~90003C", "90019C", "90020C"),
                    DateTimeUtil.NHI_1_MONTH,
                    dto.getExcludeTreatmentProcedureIds()
                );

            if (outOfLimitationClause == null ||
                DateTimeUtil.transformROCDateToLocalDate(outOfLimitationClause.getA71())
                    .isAfter(
                        DateTimeUtil.transformROCDateToLocalDate(
                            targetTp.getA71()
                        )
                    )
            ) {
                result.validated(false)
                    .message(
                        String.format(
                            NhiRuleCheckFormat.D1_2.getFormat(),
                            dto.getNhiExtendTreatmentProcedure().getA73(),
                            "89006C",
                            this.classifySourceType(
                                NhiRuleCheckSourceType.SYSTEM_RECORD,
                                DateTimeUtil.transformROCDateToLocalDate(outOfLimitationClause.getA71()),
                                outOfLimitationClause.getNhiExtendDisposal() != null
                                    ? outOfLimitationClause.getNhiExtendDisposal().getId()
                                    : null,
                                dto
                            ),
                            DateTimeUtil.transformA71ToDisplay(targetTp.getA71()),
                            DateTimeUtil.NHI_1_MONTH.getDays(),
                            dto.getNhiExtendTreatmentProcedure().getA73()
                        )
                    );
            }
        }

        return result;
    }

    /**
     * 條件式群
     */
    // 小於 12 歲
    public Predicate<NhiRuleCheckDTO> clauseIsLessThanAge12 = (dto) -> {
        if (dto.getPatient().getBirth() == null) {
            return false;
        } else {
            Period p = Period.between(
                dto.getPatient().getBirth(),
                DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()));

            return p.getYears() < 12;
        }
    };

    // 屬於 轉診
    public Predicate<NhiRuleCheckDTO> clauseIsReferral = NhiRuleCheckDTO::isReferral;

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

        if (dto != null &&
            dto.getNhiExtendTreatmentProcedure() != null &&
            dto.getNhiExtendTreatmentProcedure().getA71() != null
        ) {
            LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());
            begin = currentTxDate.withDayOfMonth(1).minusMonths(limitationMonth - 1);
            end = currentTxDate.withDayOfMonth(currentTxDate.lengthOfMonth()).minusDays(1);
        }

        return new LocalDateDuration()
            .begin(begin)
            .end(end);
    }

    /**
     * 依照 ned id 來決定是否為相同處置，依照日期來決定是否為同日處置，其餘則依照傳入類型做為結果
     *
     * @param originSrcType     由呼叫函式決定，當不符合狀態時的結果
     * @param matchedDate       查詢到的日期
     * @param matchedDisposalId 查詢到的 ned id
     * @param dto               欲檢查的內容
     * @return 來源類型
     */
    public String classifySourceType(
        @NotNull NhiRuleCheckSourceType originSrcType,
        LocalDate matchedDate,
        Long matchedDisposalId,
        NhiRuleCheckDTO dto
    ) {
        NhiRuleCheckSourceType result = originSrcType;
        try {
            if (dto != null &&
                dto.getNhiExtendDisposal() != null
            ) {
                if (matchedDisposalId != null &&
                    matchedDisposalId.equals(dto.getNhiExtendDisposal().getId())
                ) {
                    result = NhiRuleCheckSourceType.CURRENT_DISPOSAL;
                } else if (matchedDate != null &&
                    dto.getNhiExtendTreatmentProcedure() != null &&
                    dto.getNhiExtendTreatmentProcedure().getA71() != null &&
                    matchedDate.isEqual(DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()))
                ) {
                    result = NhiRuleCheckSourceType.TODAY_OTHER_DISPOSAL;
                }
            } else if (dto != null &&
                dto.getNhiExtendTreatmentProcedure() != null &&
                dto.getNhiExtendTreatmentProcedure().getA71() != null
            ) {
                LocalDate ld = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());
                if (matchedDate != null &&
                    matchedDate.isEqual(ld)
                ) {
                    result = NhiRuleCheckSourceType.TODAY_OTHER_DISPOSAL;
                } else {
                    result = originSrcType;
                }
            }
        } catch (Exception e) {

        }

        return result.getValue();
    }

    /**
     * 檢查當前處置單，當日其他處置單，是否包含目標代碼
     *
     * @param dto
     * @param codes
     * @return
     */
    public NhiRuleCheckResultDTO isCurrentDateHasCode(
        NhiRuleCheckDTO dto,
        List<String> codes
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.WARNING)
            .validated(true)
            .validateTitle("檢查當前處置單，當日其他處置單，是否包含目標代碼");

        List<String> parsedCodes = this.parseNhiCode(codes);

        if (dto.getIncludeNhiCodes() != null &&
            dto.getIncludeNhiCodes().stream()
                .anyMatch(parsedCodes::contains)
        ) {
            result.validated(
                false
            )
                .nhiRuleCheckInfoType(
                    NhiRuleCheckInfoType.WARNING
                )
                .message(
                    String.format(
                        NhiRuleCheckFormat.W4_1.getFormat(),
                        DateTimeUtil.transformA71ToDisplay(
                            dto.getNhiExtendTreatmentProcedure().getA71()
                        ),
                        codes.toString(),
                        NhiRuleCheckSourceType.CURRENT_DISPOSAL.getValue(),
                        dto.getNhiExtendDisposal().getA17()
                    )
                );
        }

        if (result.isValidated() &&
            dto.getNhiExtendDisposal() != null &&
            dto.getNhiExtendDisposal().getDate() != null
        ) {
            LocalDateDuration localDateDuration = new LocalDateDuration();
            localDateDuration.setBegin(
                LocalDate.MIN
                    .withYear(
                        dto.getNhiExtendDisposal().getDate().getYear()
                    )
                    .withMonth(
                        dto.getNhiExtendDisposal().getDate().getMonthValue()
                    )
                    .withDayOfMonth(
                        dto.getNhiExtendDisposal().getDate().getDayOfMonth()
                    )
            );
            localDateDuration.setEnd(
                LocalDate.MAX
                    .withYear(
                        dto.getNhiExtendDisposal().getDate().getYear()
                    )
                    .withMonth(
                        dto.getNhiExtendDisposal().getDate().getMonthValue()
                    )
                    .withDayOfMonth(
                        dto.getNhiExtendDisposal().getDate().getDayOfMonth()
                    )
            );
            NhiExtendTreatmentProcedure match =
                this.findPatientTreatmentProcedureAtCodesAndBetweenDuration(
                    dto.getPatient().getId(),
                    dto.getNhiExtendTreatmentProcedure().getId(),
                    localDateDuration,
                    parsedCodes,
                    dto.getExcludeTreatmentProcedureIds()
                );

            if (match != null) {
                result.validated(
                    false
                )
                    .nhiRuleCheckInfoType(
                        NhiRuleCheckInfoType.WARNING
                    )
                    .message(
                        String.format(
                            NhiRuleCheckFormat.W4_1.getFormat(),
                            DateTimeUtil.transformA71ToDisplay(
                                dto.getNhiExtendTreatmentProcedure().getA71()
                            ),
                            codes.toString(),
                            NhiRuleCheckSourceType.TODAY_OTHER_DISPOSAL.getValue(),
                            dto.getNhiExtendDisposal().getA17()
                        )
                    );
            }
        }

        return result;
    }

    /**
     * 終生一次
     */
    public NhiRuleCheckResultDTO isOnceInWholeLife(
        NhiRuleCheckDTO dto,
        List<String> codes
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
            .validated(true)
            .validateTitle("終生一次");

        List<String> parsedCodes = this.parseNhiCode(codes);

        if (dto.getIncludeNhiCodes() != null &&
            dto.getIncludeNhiCodes().stream()
                .filter(parsedCodes::contains)
                .count() > 1
        ) {
            result
                .validated(
                    false
                )
                .message(
                    String.format(
                        NhiRuleCheckFormat.D2_1.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        NhiRuleCheckSourceType.CURRENT_DISPOSAL.getValue(),
                        DateTimeUtil.transformA71ToDisplay(
                            dto.getNhiExtendDisposal().getA17()
                        )
                    )
                );
        }

        if (result.isValidated()) {
            LocalDateDuration localDateDuration = new LocalDateDuration();
            localDateDuration.setBegin(
                DateTimeUtil.transformROCDateToLocalDate(
                    dto.getNhiExtendTreatmentProcedure().getA71()
                )
            );
            localDateDuration.setEnd(
                DateTimeUtil.transformROCDateToLocalDate(
                    dto.getNhiExtendTreatmentProcedure().getA71()
                )
            );
            NhiExtendTreatmentProcedure netp = this.findPatientTreatmentProcedureAtCodesAndBetweenDuration(
                dto.getPatient().getId(),
                dto.getNhiExtendTreatmentProcedure().getId(),
                localDateDuration,
                parsedCodes,
                dto.getExcludeTreatmentProcedureIds()
            );

            if (netp != null) {
                result
                    .validated(
                        false
                    )
                    .message(
                        String.format(
                            NhiRuleCheckFormat.D2_1.getFormat(),
                            dto.getNhiExtendTreatmentProcedure().getA73(),
                            NhiRuleCheckSourceType.TODAY_OTHER_DISPOSAL.getValue(),
                            DateTimeUtil.transformA71ToDisplay(
                                dto.getNhiExtendDisposal().getA17()
                            )
                        )
                    );
            }

            if (result.isValidated()) {
                NhiMedicalRecord nmr = this.findPatientMediaRecordAtCodesAndBetweenDuration(
                    dto.getPatient().getId(),
                    localDateDuration,
                    parsedCodes
                );

                if (nmr != null) {
                    result
                        .validated(
                            false
                        )
                        .message(
                            String.format(
                                NhiRuleCheckFormat.D2_1.getFormat(),
                                dto.getNhiExtendTreatmentProcedure().getA73(),
                                NhiRuleCheckSourceType.TODAY_OTHER_DISPOSAL.getValue(),
                                DateTimeUtil.transformA71ToDisplay(
                                    dto.getNhiExtendDisposal().getA17()
                                )
                            )
                        );
                }
            }

        }

        if (result.isValidated()) {
            List<NhiExtendTreatmentProcedure> netps = this.findPatientTreatmentProcedureAtCodes(
                dto.getPatient().getId(),
                parsedCodes
            );

            if (netps != null &&
                netps.size() > 0
            ) {
                result
                    .validated(
                        false
                    )
                    .message(
                        String.format(
                            NhiRuleCheckFormat.D2_1.getFormat(),
                            dto.getNhiExtendTreatmentProcedure().getA73(),
                            NhiRuleCheckSourceType.SYSTEM_RECORD.getValue(),
                            DateTimeUtil.transformA71ToDisplay(
                                dto.getNhiExtendDisposal().getA17()
                            )
                        )
                    );
            }
        }

        if (result.isValidated()) {
            List<NhiMedicalRecord> nmrs = this.findPatientMedicalRecordAtCodes(
                dto.getPatient().getId(),
                parsedCodes
            );

            if (nmrs != null &&
                nmrs.size() > 0
            ) {
                result
                    .validated(
                        false
                    )
                    .message(
                        String.format(
                            NhiRuleCheckFormat.D2_1.getFormat(),
                            dto.getNhiExtendTreatmentProcedure().getA73(),
                            NhiRuleCheckSourceType.NHI_CARD_RECORD.getValue(),
                            DateTimeUtil.transformA71ToDisplay(
                                dto.getNhiExtendDisposal().getA17()
                            )
                        )
                    );
            }
        }

        return result;
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
            List<NhiExtendTreatmentProcedureTable> netps =
                disposalRepository.findDoctorOperationForPatientWithOnceWholeLifeLimitation(
                    dto.getNhiExtendDisposal().getDisposal().getId(),
                    code
                );
            if (netps != null &&
                0 < netps.size()
            ) {
                result
                    .validated(false)
                    .message(
                        String.format(
                            NhiRuleCheckFormat.D2_3.getFormat(),
                            dto.getNhiExtendTreatmentProcedure().getA73(),
                            NhiRuleCheckSourceType.SYSTEM_RECORD.getValue(),
                            DateTimeUtil.transformA71ToDisplay(
                                netps.get(0).getA71()
                            )
                        )
                    );
            }
        }

        return result;
    }

    /**
     * 指定的診療項目，在病患過去紀錄中（來自診所系統產生的紀錄），是否已經包含 codes，且未達 指定月份間隔。
     *
     * @param dto      使用 patient.id, nhiExtendTreatmentProcedure.id/.a71
     * @param codes    被限制的健保代碼清單
     * @param duration 指定時間間隔
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO isCodeBetweenDuration(
        @NotNull NhiRuleCheckDTO dto,
        @NotNull List<String> codes,
        LocalDateDuration duration,
        Long month
    ) {
        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        List<String> parsedCodes = this.parseNhiCode(codes);

        // 檢核 nhi extend treatment procedure
        NhiExtendTreatmentProcedure match =
            this.findPatientTreatmentProcedureAtCodesAndBetweenDuration(
                dto.getPatient().getId(),
                dto.getNhiExtendTreatmentProcedure().getId(),
                duration,
                parsedCodes,
                dto.getExcludeTreatmentProcedureIds());

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("指定的診療項目，在病患過去紀錄中（來自診所系統產生的紀錄），是否已經包含 codes，且未達 指定月份間隔。")
            .validated(match == null);

        if (!result.isValidated()) {
            LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getA71());

            String m = "";

            m = String.format(
                NhiRuleCheckFormat.D1_2_2.getFormat(),
                dto.getNhiExtendTreatmentProcedure().getA73(),
                match.getA73(),
                this.classifySourceType(
                    NhiRuleCheckSourceType.SYSTEM_RECORD,
                    matchDate,
                    match.getNhiExtendDisposal().getId(),
                    dto
                ),
                DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                month,
                dto.getNhiExtendTreatmentProcedure().getA73()
            );

            result
                .nhiRuleCheckInfoType(
                    NhiRuleCheckFormat.D1_2_2.getLevel()
                )
                .message(
                    m
                );
        }

        return result;
    }

    /**
     * 不指定時間，曾經有指定治療項目
     * @param dto
     * @param codes
     * @return
     */
    public NhiRuleCheckResultDTO isTreatmentDependOnCode(
        NhiRuleCheckDTO dto,
        List<String> codes
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
            .validateTitle("不指定時間，曾經有指定治療項目")
            .validated(true);

        List<String> parsedCodes = this.parseNhiCode(codes);

        List<NhiExtendTreatmentProcedure> netps =
            this.findPatientTreatmentProcedureAtCodes(
                dto.getPatient().getId(),
                parsedCodes
            );

        if (netps == null ||
            netps.size() < 1
        ) {
            result
                .validated(
                    false
                )
                .message(
                    String.format(
                        NhiRuleCheckFormat.D8_2.getFormat(),
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        codes
                    )
                );
        }

        return result;
    }

    /**
     * 今天時間，曾經有指定治療項目
     * @param dto
     * @param codes
     * @return
     */
    public NhiRuleCheckResultDTO isTreatmentDependOnCodeToday(
        NhiRuleCheckDTO dto,
        List<String> codes
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.WARNING)
            .validateTitle("今天時間，曾經有指定治療項目")
            .validated(true);

        List<String> parsedCodes = this.parseNhiCode(codes);

        if (dto.getIncludeNhiCodes() != null &&
            dto.getIncludeNhiCodes().stream()
            .anyMatch(
                parsedCodes::contains
            )
        ) {
            return result;
        }

        NhiMedicalRecord nmr = this.findPatientMediaRecordAtCodesAndBeforePeriod(
                dto.getPatient().getId(),
                DateTimeUtil.transformROCDateToLocalDate(
                    dto.getNhiExtendDisposal().getA17()
                ),
                parsedCodes,
                Period.ZERO
            );
        if (nmr != null) {
            return result;
        }

        NhiExtendTreatmentProcedure netps =
            this.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
                dto.getPatient().getId(),
                dto.getNhiExtendTreatmentProcedure().getId(),
                DateTimeUtil.transformROCDateToLocalDate(
                    dto.getNhiExtendDisposal().getA17()
                ),
                parsedCodes,
                Period.ZERO,
                dto.getExcludeTreatmentProcedureIds()
            );

        if (netps == null) {
            result
                .validated(
                    false
                )
                .nhiRuleCheckInfoType(
                    NhiRuleCheckFormat.W3_1.getLevel()
                )
                .message(
                    String.format(
                        NhiRuleCheckFormat.W3_1.getFormat(),
                        codes
                    )
                );
        }

        return result;
    }

    /**
     * 今天時間，不得有指定治療項目
     * @param dto
     * @param codes
     * @return
     */
    public NhiRuleCheckResultDTO isNoTreatmentOnCodeToday(
        NhiRuleCheckDTO dto,
        List<String> codes
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.WARNING)
            .validateTitle("今天時間，不得有指定治療項目")
            .validated(true);

        List<String> parsedCodes = this.parseNhiCode(codes);

        if (dto.getIncludeNhiCodes() != null &&
            dto.getIncludeNhiCodes().stream()
                .anyMatch(parsedCodes::contains)
        ) {
            return result
                .validated(
                    false
                )
                .nhiRuleCheckInfoType(
                    NhiRuleCheckFormat.D6_1.getLevel()
                )
                .message(
                    String.format(
                        NhiRuleCheckFormat.D6_1.getFormat(),
                        codes
                    )
                );
        }

        NhiMedicalRecord nmr = this.findPatientMediaRecordAtCodesAndBeforePeriod(
            dto.getPatient().getId(),
            DateTimeUtil.transformROCDateToLocalDate(
                dto.getNhiExtendDisposal().getA17()
            ),
            parsedCodes,
            Period.ZERO
        );
        if (nmr != null) {
            return result
                .validated(
                    false
                )
                .nhiRuleCheckInfoType(
                    NhiRuleCheckFormat.D6_1.getLevel()
                )
                .message(
                    String.format(
                        NhiRuleCheckFormat.D6_1.getFormat(),
                        codes
                    )
                );
        }

        NhiExtendTreatmentProcedure netps =
            this.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
                dto.getPatient().getId(),
                dto.getNhiExtendTreatmentProcedure().getId(),
                DateTimeUtil.transformROCDateToLocalDate(
                    dto.getNhiExtendDisposal().getA17()
                ),
                parsedCodes,
                Period.ZERO,
                dto.getExcludeTreatmentProcedureIds()
            );

        if (netps != null) {
            return result
                .validated(
                    false
                )
                .nhiRuleCheckInfoType(
                    NhiRuleCheckFormat.D6_1.getLevel()
                )
                .message(
                    String.format(
                        NhiRuleCheckFormat.D6_1.getFormat(),
                        codes
                    )
                );
        }

        return result;
    }

    /**
     * 當前處置單包含任意其他處置
     * @param dto
     * @return
     */
    public NhiRuleCheckResultDTO isAnyOtherTreatment(NhiRuleCheckDTO dto) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .nhiRuleCheckInfoType(NhiRuleCheckInfoType.WARNING)
            .validateTitle("當前處置單包含任意其他處置")
            .validated(true);

        if (dto.getIncludeNhiCodes() != null &&
            dto.getIncludeNhiCodes().size() == 1
        ) {
            result.message(
                String.format(
                    NhiRuleCheckFormat.W5_1.getFormat(),
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            );
        }

        return result;
    }
}
