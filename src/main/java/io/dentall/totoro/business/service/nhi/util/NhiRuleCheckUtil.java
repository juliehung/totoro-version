package io.dentall.totoro.business.service.nhi.util;

import io.dentall.totoro.business.service.nhi.NhiRuleCheckDTO;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckResultDTO;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckVM;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.repository.NhiExtendTreatmentProcedureRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.service.dto.table.NhiExtendTreatmentProcedureTable;
import io.dentall.totoro.service.mapper.NhiExtendTreatmentProcedureMapper;
import io.dentall.totoro.service.mapper.PatientMapper;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.web.rest.errors.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Service
public class NhiRuleCheckUtil {

    // 申報時常用說明
    public static final String DESC_MUST_FULFILL_SURFACE = "應於病歷詳列充填牙面部位";

    private final NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    private final PatientRepository patientRepository;

    private final NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper;

    public NhiRuleCheckUtil(
        NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository,
        PatientRepository patientRepository,
        NhiExtendTreatmentProcedureMapper nhiExtendTreatmentProcedureMapper
    ) {
        this.nhiExtendTreatmentProcedureRepository = nhiExtendTreatmentProcedureRepository;
        this.patientRepository = patientRepository;
        this.nhiExtendTreatmentProcedureMapper = nhiExtendTreatmentProcedureMapper;
    }

    /**
     * 單顆牙齒是否為 乳牙（字串兩碼且為 51-59, 61-69, 71-79, 81-89）
     * @param singleToothPosition 單一牙位
     * @return boolean 是否為乳牙
     */
    private boolean isDeciduousTeeth(String singleToothPosition) {
        if (singleToothPosition.length() != 2) {
            return false;
        }

        if (!singleToothPosition.matches("^[5|6|7|8][1-9]$")) {
            return false;
        }

        return true;
    }

    /**
     * 單顆牙齒是否為 恆牙（字串兩碼且為 11-19, 21-29, 31-39, 41-49）
     * @param singleToothPosition
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
     * <number-low><single-alphabet>~<number-high><single-alphabet> 這種格式，且 <single-alphabet> 需相同，且 <single-alphabet> 相同
     * e.g.
     * 91001C~91020C 會被展開成 91001C, 91002C, 91003C, ..., 91020C
     *
     * @param nhiCodes 健保代碼，或健保代碼區間
     * @return string list of 健保代碼，健保代碼區間也會被切割產生單一健保代碼
     */
    private List<String> parseNhiCode(List<String> nhiCodes) {
        List<String> result = new ArrayList<>();

        nhiCodes.stream()
            .filter(Objects::nonNull)
            .forEach(code -> {
                String[] tildeCodes = code.split("([0-9]*)([A-Z]?)~([0-9]*)([A-Z]?)");
                try {
                    int numberLow = Integer.parseInt(tildeCodes[0]);
                    int numberHigh = Integer.parseInt(tildeCodes[2]);
                    String alpha = tildeCodes[1];

                    if (tildeCodes.length == 4 &&
                        numberLow < numberHigh &&
                        alpha.equals(tildeCodes[3])
                    ) {
                        for (int i = numberLow; i <= numberHigh; i++) {
                            result.add(String.valueOf(i).concat(alpha));
                        }
                    } else {
                        result.add(code);
                    }

                } catch (NumberFormatException e) {
                    // do nothing
                }
            });

        return result;
    }

    /**
     * 用來把數個後端檢核結果總結，並以前端所需格式輸出
     * @param dto 後端檢驗後的結果
     * @param vm 前端檢驗後的結果
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
     * @param dto dto.patient 將會被 assign db data
     * @param patientId 病患 id for query db
     */
    private void assignDtoByPatientId(@NotNull NhiRuleCheckDTO dto, @NotNull Long patientId) {
        dto.setPatient(
            PatientMapper.patientTableToPatient(
                patientRepository.findPatientById(patientId)
                    .orElseThrow(() -> new ResourceNotFoundException("patient with " + patientId))));
    }

    /**
     * 檢核輸入 code(a73), patient, 是否存在且關係匹，並將取得資料塞入 dto 以利後續使用配，或 response as not found
     * @param dto dto.nhiExtendTreatmentProcedure 將會被 assign db data
     * @param code a.k.a a73 健保代碼
     * @param nhiExtendTreatmentProcedureId 診療 id
     * @param patientId 病患 id
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
     * 作用是轉化 vm 取得到的值，檢核、查詢對應 Nhi Treatment Procedure、Patient 資料，以利後續功能使用。
     * 此 method 使用情境有二
     * 1. 診療項目 尚未被產生，需要預先進行確認，所需資料會用到 patientId, a71, a73, a74, a75，a71, a73 不用給定是因為，a71 必然為今日，
     * 這邊會自動計算帶入；a73 則是打 api 時就會認定想驗證的目標在 api path。
     * 2. 診療項目 已被產生，需帶入 patientId, treatmentProcedureId(treatmentProcedureId === nhiExtendTreatmentProcedureId)，
     * 並查詢取得對應資料。
     *
     * @param code 來源於 api path，及其預計想檢核的目標
     * @param vm 來自於前端的輸入
     * @return 後續檢核統一 `輸入` 的介面
     */
    public NhiRuleCheckDTO convertVmToDto(@NotNull String code, @NotNull NhiRuleCheckVM vm) {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();

        if (vm.getPatientId() != null) {
            assignDtoByPatientId(dto, vm.getPatientId());
        }

        if (vm.getTreatmentId() != null) {
            assignDtoByNhiExtendTreatmentProcedureId(dto, code, vm.getTreatmentId(), vm.getPatientId());
        }

        // 產生暫時的 treatment 資料，在後續的檢驗中被檢核所需
        if (vm.getPatientId() != null && vm.getTreatmentId() == null) {
            dto.setNhiExtendTreatmentProcedure(
                new NhiExtendTreatmentProcedure()
                    .a71(DateTimeUtil.transformLocalDateToRocDate(Instant.now()))
                    .a73(code)
                    .a74(vm.getTmpTreatmentA74())
                    .a75(vm.getTmpTreatmentA75()));
        }

        return dto;
    }

    /**
     * 病患 是否在 診療 當下年紀 >= 12 歲
     * @param dto 使用 patient.birth, nhiExtendTreatmentProcedure.A71
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO equalsOrGreaterThanAge12(@NotNull NhiRuleCheckDTO dto) {

        Period p = Period.between(
            dto.getPatient().getBirth(),
            DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()));

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validated(p.getYears() >= 12);

        if (!result.isValidated()) {
            result.setMessage(
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
     * @param dto 使用 patient.birth, nhiExtendTreatmentProcedure.A71
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO lessThanAge12(@NotNull NhiRuleCheckDTO dto) {

        Period p = Period.between(
            dto.getPatient().getBirth(),
            DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()));

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validated(p.getYears() < 12);

        if (!result.isValidated()) {
            result.setMessage(
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
     * @param dto 使用 patient.birth, nhiExtendTreatmentProcedure.A71
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO lessThanAge6(@NotNull NhiRuleCheckDTO dto) {

        Period p = Period.between(
            dto.getPatient().getBirth(),
            DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()));

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validated(p.getYears() < 6);

        if (!result.isValidated()) {
            result.setMessage(
                String.format(
                    "%s 須在病患未滿 6 歲，方能申報",
                    dto.getNhiExtendTreatmentProcedure().getA73()
                )
            );
        }

        return result;
    }

    private NhiExtendTreatmentProcedure findPatientTreatmentProcedureAtCodesAndBeforePeriod(
        Long patientId,
        Long treatmentProcedureId,
        LocalDate currentTreatmentProcedureDate,
        @NotNull List<String> codes,
        @NotNull Period limitDays
    ) {
        List<NhiExtendTreatmentProcedure> matchedNhiExtendTreatmentProcedure = new ArrayList<>();

        nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73In(
            patientId,
            codes)
            .stream()
            .filter(Objects::nonNull)
            .filter(netp -> StringUtils.isNotBlank(netp.getA71()) && netp.getTreatmentProcedure_Id() != null)
            .filter(netp -> !netp.getTreatmentProcedure_Id().equals(treatmentProcedureId))
            .filter(netp -> currentTreatmentProcedureDate.isAfter(DateTimeUtil.transformROCDateToLocalDate(netp.getA71())))
            .anyMatch(netpt -> {
                LocalDate pastTxDate = DateTimeUtil.transformROCDateToLocalDate(netpt.getA71());
                if (pastTxDate.plus(limitDays).isAfter(currentTreatmentProcedureDate)) {
                    matchedNhiExtendTreatmentProcedure.add(
                        nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(netpt));
                    return true;
                } else {
                    return false;
                }
            });

        return matchedNhiExtendTreatmentProcedure.size() > 0 ?matchedNhiExtendTreatmentProcedure.get(0) :null ;
    }

    /**
     * 指定的診療項目，在病患過去紀錄中，是否已經包含 codes，且未達間隔 limitDays
     * @param dto 使用 patient.id, nhiExtendTreatmentProcedure.id/.a71
     * @param codes 被限制的健保代碼清單
     * @param limitDays 間隔時間
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO hasCodeBeforeDate(@NotNull NhiRuleCheckDTO dto, @NotNull List<String> codes, @NotNull Period limitDays) {

        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

        NhiExtendTreatmentProcedure match =
            findPatientTreatmentProcedureAtCodesAndBeforePeriod(
                dto.getPatient().getId(),
                dto.getNhiExtendTreatmentProcedure().getId(),
                currentTxDate,
                codes,
                limitDays);

        boolean hasCodeBeforeDate = match == null;

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validated(!hasCodeBeforeDate);

        if (!result.isValidated()) {
            LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getA71());

            result.setMessage(
                String.format(
                    "%s 不可與 %s 在 %d 天內再次申報，上次申報 %s (%s, %d 天前)",
                    dto.getNhiExtendTreatmentProcedure().getA73(),
                    codes.toString(),
                    limitDays.getDays(),
                    match.getA73(),
                    matchDate,
                    Duration.between(matchDate.atStartOfDay(), currentTxDate.atStartOfDay()).toDays()
                )
            );
        }

        return result;
    }

    /**
     * 回訊息作為提醒用，檢核狀況算審核通過
     * @param message 作為提醒用訊息
     * @return 後續檢核統一 `回傳` 的介面
     */
    public NhiRuleCheckResultDTO generalNotification(String message) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validated(true);

        return result;
    }

    /**
     * 限制牙面在 limitNumberOfSurface 以下
     * @param dto 使用 nhiExtendTreatmentProcedure.a75
     * @param limitNumberOfSurface 申報最高牙面數
     * @return
     */
    public NhiRuleCheckResultDTO hasLimitation(NhiRuleCheckDTO dto, Integer limitNumberOfSurface) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validated(dto.getNhiExtendTreatmentProcedure().getA75().length() <= limitNumberOfSurface);

        if (!result.isValidated()) {
            result.setMessage(
                "申報面數最高以 "
                .concat(limitNumberOfSurface.toString())
                .concat(" 面為限，或是著排除是否前後有空格。"));
        }

        return result;
    }

    /**
     * 病患 牙齒 是否有 健保代碼 於某時間前已被申報過
     * @param dto 使用 nhiExtendTreatmentProcedure.id/a71/a73/a74, patient.id,
     * @param codes 被限制的健保代碼清單
     * @param deciduousToothLimitDays 為乳牙時，所需時間間隔
     * @param permanentToothLimitDays 為恆牙時，弱需時間間隔
     * @return
     */
    public NhiRuleCheckResultDTO hasPatientToothAtCodesBeforePeriod(
        NhiRuleCheckDTO dto,
        List<String> codes,
        Period deciduousToothLimitDays,
        Period permanentToothLimitDays
    ) {
        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validated(true);

        Stack<Period> usedPeriod = new Stack<>();
        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());
        String[] teeth = dto.getNhiExtendTreatmentProcedure().getA74().split("(?<=\\G..)");

        Arrays.stream(teeth)
            .map(tooth -> {

                if (isDeciduousTeeth(tooth)) {
                    usedPeriod.push(deciduousToothLimitDays);
                }

                if (isPermanentTeeth(tooth)) {
                    usedPeriod.push(permanentToothLimitDays);
                }

                if (usedPeriod.empty()) {
                    return null;
                }

                return this.findPatientTreatmentProcedureAtCodesAndBeforePeriod(
                    dto.getPatient().getId(),
                    dto.getNhiExtendTreatmentProcedure().getId(),
                    DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()),
                    codes,
                    usedPeriod.peek()
                    );

            })
            .filter(Objects::nonNull)
            .findFirst()
            .ifPresent(match -> {
                LocalDate matchDate = DateTimeUtil.transformROCDateToLocalDate(match.getA71());
                result.setValidated(false);
                result.setMessage(
                    String.format(
                        "%s 不可與 %s 在 %d 天內再次申報，上次申報 %s (%s, %d 天前)",
                        dto.getNhiExtendTreatmentProcedure().getA73(),
                        codes.toString(),
                        usedPeriod.peek(),
                        match.getA73(),
                        matchDate,
                        Duration.between(matchDate.atStartOfDay(), currentTxDate.atStartOfDay()).toDays()
                    )
                );
            });

        return result;
    }

}
