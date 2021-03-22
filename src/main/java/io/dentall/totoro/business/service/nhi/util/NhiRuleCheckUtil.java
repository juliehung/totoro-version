package io.dentall.totoro.business.service.nhi.util;

import io.dentall.totoro.business.service.NhiRuleCheckInfoType;
import io.dentall.totoro.business.service.NhiRuleCheckSourceType;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckDTO;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckResultDTO;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckVM;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.NhiExtendDisposal;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.NhiMedicalRecord;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.dto.table.DisposalTable;
import io.dentall.totoro.service.dto.table.NhiExtendDisposalTable;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentProcedureTable;
import io.dentall.totoro.service.mapper.DisposalMapper;
import io.dentall.totoro.service.mapper.NhiExtendDisposalMapper;
import io.dentall.totoro.service.mapper.NhiExtendTreatmentProcedureMapper;
import io.dentall.totoro.service.mapper.PatientMapper;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.web.rest.errors.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
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

    private final NhiExtendDisposalRepository nhiExtendDisposalRepository;

    private final NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    private final PatientRepository patientRepository;

    private final NhiMedicalRecordRepository nhiMedicalRecordRepository;

    private final NhiExtendDisposalMapper nhiExtendDisposalMapper;

    private final NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper;

    private final DisposalRepository disposalRepository;

    public NhiRuleCheckUtil(
        NhiExtendDisposalRepository nhiExtendDisposalRepository,
        NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository,
        PatientRepository patientRepository,
        NhiMedicalRecordRepository nhiMedicalRecordRepository,
        NhiExtendDisposalMapper nhiExtendDisposalMapper,
        NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper,
        DisposalRepository disposalRepository
    ) {
        this.nhiExtendDisposalRepository = nhiExtendDisposalRepository;
        this.nhiExtendTreatmentProcedureRepository = nhiExtendTreatmentProcedureRepository;
        this.patientRepository = patientRepository;
        this.nhiMedicalRecordRepository = nhiMedicalRecordRepository;
        this.nhiExtendDisposalMapper = nhiExtendDisposalMapper;
        this.nhiExtendTreatmentProcedureMapper = nhiExtendTreatmentProcedureMapper;
        this.disposalRepository = disposalRepository;
    }

    /**
     * 單顆牙齒是否為 乳牙（字串兩碼且為 51-59, 61-69, 71-79, 81-89）
     *
     * @param singleToothPosition 單一牙位
     * @return boolean 是否為乳牙
     */
    private boolean isDeciduousTeeth(String singleToothPosition) {
        if (singleToothPosition.length() != 2) {
            return false;
        }

        if (!singleToothPosition.matches("^[5|6|7|8][1-5]$")) {
            return false;
        }

        return true;
    }

    /**
     * 單顆牙齒是否為 恆牙（字串兩碼且為 11-19, 21-29, 31-39, 41-49）
     *
     * @param singleToothPosition 單一牙位
     * @return boolean 是否為恆牙
     */
    private boolean isPermanentTeeth(String singleToothPosition) {
        if (singleToothPosition.length() != 2) {
            return false;
        }

        if (!singleToothPosition.matches("^[1|2|3|4][1-9]$")) {
            return false;
        }

        return true;
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
     * 作用是轉化 vm 取得到的值，檢核、查詢對應 Nhi Disposal, Nhi Treatment Procedure, Patient 資料，以利後續功能使用。
     * 此 method 使用情境有二
     * 1. 診療項目 尚未被產生，需要預先進行確認，所需資料會用到 patientId, a71, a73, a74, a75，a71, a73
     * 這邊會自動計算帶入；a73 則是打 api 時就會認定想驗證的目標在 api path。
     * 2. 診療項目 已被產生，需帶入 patientId, treatmentProcedureId(treatmentProcedureId === nhiExtendTreatmentProcedureId),
     * a74, a75，若檢查結果不同則以新帶入的欄位為檢驗項目
     * 並查詢取得對應資料。
     *
     * @param code 來源於 api path，及其預計想檢核的目標
     * @param vm   來自於前端的輸入
     * @return 後續檢核統一 `輸入` 的介面
     */
    public NhiRuleCheckDTO convertVmToDto(@NotNull String code, @NotNull NhiRuleCheckVM vm) {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();

        // 若有指定排除的 treatment procedure id，在後續的 query result 將會排除所列項目。（應用於前端刪除項目但尚未改動到資料）
        dto.setExcludeTreatmentProcedureIds(vm.getExcludeTreatmentProcedureIds());
        // assign 指定同處置底下的 nhi code，以利後續規則使用
        dto.setIncludeNhiCodes(vm.getIncludeNhiCodes());
        // assign 轉診註記，以利後續規則使用
        dto.setReferral(vm.isReferral());

        if (vm.getPatientId() != null) {
            assignDtoByPatientId(dto, vm.getPatientId());
        }

        if (vm.getTreatmentProcedureId() != null) {
            assignDtoByNhiExtendTreatmentProcedureId(dto, code, vm.getTreatmentProcedureId(), vm.getPatientId());
            assignDtoByNhiExtendDisposalId(dto, dto.getNhiExtendTreatmentProcedure().getId());

            // 若有給定 a74 且 與查詢結果不同，則將 其改為 前端傳入之牙位
            if (
                StringUtils.isNotBlank(vm.getA74()) &&
                !dto.getNhiExtendTreatmentProcedure().getA74().equals(vm.getA74())
            ) {
                dto.getNhiExtendTreatmentProcedure().setA74(vm.getA74());
            }
            // 若有給定 a75 且 與查詢結果不同，則將 其改為 前端傳入之牙位
            if (
                StringUtils.isNotBlank(vm.getA75()) &&
                !dto.getNhiExtendTreatmentProcedure().getA75().equals(vm.getA75())
            ) {
                dto.getNhiExtendTreatmentProcedure().setA75(vm.getA75());
            }

            // 將自身加入 exclude list
            if (dto.getExcludeTreatmentProcedureIds() == null) {
                List<Long> ids = new ArrayList<>();
                ids.add(vm.getTreatmentProcedureId());
                dto.setExcludeTreatmentProcedureIds(ids);
            } else {
                dto.getExcludeTreatmentProcedureIds().add(vm.getTreatmentProcedureId());
            }
        }

        // 產生暫時的 treatment 資料，在後續的檢驗中被檢核所需
        if (vm.getPatientId() != null && vm.getTreatmentProcedureId() == null) {
            dto.setNhiExtendTreatmentProcedure(
                new NhiExtendTreatmentProcedure()
                    .a71(StringUtils.isNotBlank(vm.getA71()) ? vm.getA71() : DateTimeUtil.transformLocalDateToRocDate(Instant.now()))
                    .a73(code)
                    .a74(vm.getA74())
                    .a75(vm.getA75()));
        }

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
                        "%s 須在病患年滿 12 歲，方能申報",
                        dto.getNhiExtendTreatmentProcedure().getA73()
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
                        "%s 須在病患未滿 12 歲，方能申報",
                        dto.getNhiExtendTreatmentProcedure().getA73()
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
            .validateTitle("病患是否在診療當下年紀大於等於 6 歲")
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
                        "%s 須在病患未滿 6 歲，方能申報",
                        dto.getNhiExtendTreatmentProcedure().getA73()
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
                    matchedNhiExtendTreatmentProcedure.add(
                        nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(netpt));
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

    /**
     * 指定的診療項目，在病患過去紀錄中（來自診所系統產生的紀錄），是否已經包含 codes，且未達間隔 limitDays。
     *
     * @param dto       使用 patient.id, nhiExtendTreatmentProcedure.id/.a71
     * @param codes     被限制的健保代碼清單
     * @param limitDays 間隔時間
     * @return 後續檢核統一 `回傳` 的介面
     */
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
                        "建議 %s 後再行申報，近一次處置為系統中 %s",
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.plusDays(limitDays.getDays()).atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET))
                    )
                );
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
        int maxTimes
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
            LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.get(0).getA71());

            result
                .validated(false)
                .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                .message(
                    String.format(
                        "建議 %s 再行申報，近一次處置為系統中 %s",
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.plusDays(limitDays.getDays()).atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET))
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
        Set<ToothUtil.ToothPhase> currentPhaseSet = ToothUtil.markAsPhase(teeth);

        // 檢核 nhi extend treatment procedure
        NhiExtendTreatmentProcedure match =
            this.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
                dto.getPatient().getId(),
                dto.getNhiExtendTreatmentProcedure().getId(),
                currentTxDate,
                codes,
                limitDays,
                dto.getExcludeTreatmentProcedureIds());

        if (match != null) {
            Set<ToothUtil.ToothPhase> matchPhaseSet = ToothUtil.markAsPhase(
                ToothUtil.splitA74(match.getA74()));

            if (currentPhaseSet.stream().anyMatch(matchPhaseSet::contains)) {
                LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getA71());

                result
                    .validated(false)
                    .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                    .message(
                        String.format(
                            "建議 %s 再行申報，近一次處置為系統中 %s",
                            DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.plusDays(limitDays.getDays()).atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                            DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET))
                        )
                    );
            }
        }

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
        Set<ToothUtil.ToothPhase> currentPhaseSet = ToothUtil.markAsPhase(teeth);

        // 檢核 nhi extend treatment procedure
        NhiMedicalRecord match = this.findPatientMediaRecordAtCodesAndBeforePeriod(
            dto.getPatient().getId(),
            currentTxDate,
            codes,
            limitDays);

        if (match != null) {
            Set<ToothUtil.ToothPhase> matchPhaseSet = ToothUtil.markAsPhase(
                ToothUtil.splitA74(match.getPart()));

            if (currentPhaseSet.stream().anyMatch(matchPhaseSet::contains)) {
                LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getPart());

                result
                    .validated(false)
                    .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                    .message(
                        String.format(
                            "建議次月再行申報，近一次處置為系統中 %s",
                            DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.plusDays(limitDays.getDays()).atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                            DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET))
                        )
                    );
            }

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
                        "建議次月再行申報，近一次處置為健保IC卡中 %s",
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.plusDays(limitDays.getDays()).atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET))
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
                        "建議 %s 後再行申報，近一次處置為健保IC卡中 %s",
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.plusDays(limitDays.getDays()).atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                        DateTimeUtil.transformLocalDateToRocDateForDisplay(matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET))
                    )
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
     * @param dto 根據 predicate 的不同許用不同的內容
     * @param message 應回傳訊息
     * @param predicates 條件式
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO addNotificationWithClause(
        NhiRuleCheckDTO dto,
        String message,
        Predicate<NhiRuleCheckDTO>...predicates
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
            .validateTitle("限制牙面在 isAllLimitedSurface 以下");

        switch (sc) {
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
                .message(ToothUtil.getToothConstraintsFailureMessage(tc));
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
        Period permanentToothLimitDays
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("病患 牙齒 是否有 健保代碼 於某時間前已被申報過")
            .validated(true);

        List<String> teeth = ToothUtil.splitA74(dto.getNhiExtendTreatmentProcedure().getA74());

        teeth.stream()
            .forEach(tooth -> {
                Period selectedLimitDay = isDeciduousTeeth(tooth) ? deciduousToothLimitDays : permanentToothLimitDays;
                NhiExtendTreatmentProcedure match = this.findPatientTreatmentProcedureAtCodesAndBeforePeriodAndTooth(
                    dto.getPatient().getId(),
                    dto.getNhiExtendTreatmentProcedure().getId(),
                    DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()),
                    codes,
                    selectedLimitDay,
                    tooth,
                    dto.getExcludeTreatmentProcedureIds()
                );

                if (match != null) {
                    LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getA71());
                    result
                        .validated(false)
                        .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                        .message(
                            String.format(
                                "建議 %s 後再行申報，近一次處置為系統中 %s",
                                DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                    matchDate.plusDays(selectedLimitDay.getDays()).atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                                DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                    matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET))
                            )
                        );
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
        Period permanentToothLimitDays
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validateTitle("病患 牙齒 是否有 健保代碼 於某時間前已被申報過 in IC card")
            .validated(true);

        List<String> teeth = ToothUtil.splitA74(dto.getNhiExtendTreatmentProcedure().getA74());

        teeth.stream()
            .forEach(tooth -> {
                Period selectedLimitDay = isDeciduousTeeth(tooth) ? deciduousToothLimitDays : permanentToothLimitDays;
                NhiMedicalRecord match = this.findPatientMediaRecordAtCodesAndBeforePeriod(
                    dto.getPatient().getId(),
                    DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()),
                    codes,
                    selectedLimitDay
                );

                if (match == null) {
                    return;
                } else if (
                    match != null &&
                        ToothUtil.splitA74(match.getPart()).contains(tooth)
                ) {
                    LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getPart());
                    result
                        .validated(false)
                        .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                        .message(
                            String.format(
                                "建議 %s 後再行申報，近一次處置為健保IC卡中 %s",
                                DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                    matchDate.plusDays(selectedLimitDay.getDays()).atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET)),
                                DateTimeUtil.transformLocalDateToRocDateForDisplay(
                                    matchDate.atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET))
                            )
                        );
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
        Optional<NhiExtendTreatmentProcedureTable> optionalNhiExtendTreatmentProcedureTable = netpts.stream()
            .filter(netpt -> dto.getNhiExtendTreatmentProcedure() != null &&
                dto.getNhiExtendTreatmentProcedure().getId() != null &&
                !dto.getNhiExtendTreatmentProcedure().getId().equals(netpt.getTreatmentProcedure_Id())
            )
            .filter(netpt -> {
                LocalDate historyDate = null;
                if (netpt != null &&
                    netpt.getA71() != null
                ) {
                    historyDate = DateTimeUtil.transformROCDateToLocalDate(netpt.getA71());
                }
                return historyDate != null && historyDate.isEqual(currentDate) || historyDate != null && historyDate.isBefore(currentDate);
            })
            .findFirst();

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
     * 檢查同一處置單，是否沒有健保定義 必須 包含的診療
     *
     * @param dto 使用 includeNhiCodes
     * @return 後續檢核統一 `回傳` 的介面
     */
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
                .size() != 1
        ) {
            result.validated(false)
                .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                .message(
                String.format(
                    "必須且僅能與 %s 其一，同時申報",
                    mustIncludeCodes.toString()
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
            Optional<NhiExtendTreatmentProcedureTable> optionalNetpt = nhiExtendTreatmentProcedureRepository
                .findTop1ByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndTreatmentProcedure_Disposal_DateTimeBetween(
                    dto.getPatient().getId(),
                    currentDate,
                    currentDate.plus(limitDays)
                );

            if (optionalNetpt.isPresent()) {
                result.validated(false)
                    .nhiRuleCheckInfoType(NhiRuleCheckInfoType.DANGER)
                    .message(
                        String.format(
                            "建議 %s 後再行申報，近一次處置為系統中 %s",
                            currentDate.plus(limitDays),
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
            String ym1st = DateTimeUtil.transformLocalDateToRocDate(Instant.from(currentDate)).substring(0, 5).concat("%");
            String ym2nd = DateTimeUtil.transformLocalDateToRocDate(Instant.from(currentDate.minusYears(1))).substring(0, 5).concat("%");
            String ym3th = DateTimeUtil.transformLocalDateToRocDate(Instant.from(currentDate.minusYears(2))).substring(0, 5).concat("%");

            Optional<NhiMedicalRecord> optionalNmr = nhiMedicalRecordRepository.findTop1ByNhiExtendPatient_Patient_IdAndDateLikeOrDateLikeOrDateLike(
                dto.getPatient().getId(),
                ym1st,
                ym2nd,
                ym3th
            );

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
}
