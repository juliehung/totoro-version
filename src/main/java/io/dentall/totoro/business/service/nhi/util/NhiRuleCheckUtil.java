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
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.errors.ResourceNotFoundException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NhiRuleCheckUtil {

    public static final Period nhiMonth = Period.ofDays(30);

    public static final Period nhiMonthAndHalf = Period.ofDays(90);

    public static final Period nhiHalfYear = Period.ofDays(180);

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
     * @return
     * @throws BadRequestAlertException
     */
    public NhiRuleCheckDTO convertVmToDto(@NotNull String code, @NotNull NhiRuleCheckVM vm) throws BadRequestAlertException {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();

        if (vm.getPatientId() != null) {
            assignDtoByPatientId(dto, vm.getPatientId());
        }

        if (vm.getTreatmentId() != null) {
            assignDtoByNhiExtendTreatmentProcedureId(dto, code, vm.getTreatmentId(), vm.getPatientId());
        }

        // 產生暫時的 treatment 資料，在後續的檢驗中被檢核所需
        if (vm.getPatientId() != null && vm.getTreatmentId() == null) {
            if (StringUtils.isBlank(vm.getTmpTreatmentA74())) {
                throw new BadRequestAlertException("輸入內容不得為空", "a74 牙齒面為空", "tmpTreatmentA74");
            }
            if (StringUtils.isBlank(vm.getTmpTreatmentA75())) {
                throw new BadRequestAlertException("輸入內容不得為空", "a75 牙齒面為空", "tmpTreatmentA75");
            }

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
     * @return
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
                    dto.getNhiExtendTreatmentProcedure().getA71()
                )
            );
        }

        return result;
    }

    /**
     * 指定的診療項目，在病患過去紀錄中，是否已經包含 codes，且未達間隔 limitDays
     * @param dto 使用 patient.id, nhiExtendTreatmentProcedure.id/.a71
     * @param codes 被限制的健保代碼清單
     * @param limitDays 間隔時間
     * @return
     */
    public NhiRuleCheckResultDTO hasCodeBeforeDate(@NotNull NhiRuleCheckDTO dto, @NotNull List<String> codes, @NotNull Period limitDays) {

        LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());
        List<NhiExtendTreatmentProcedure> matchedNhiExtendTreatmentProcedure = new ArrayList<>();

        boolean hasCodeBeforeDate = nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73In(
                dto.getPatient().getId(),
                codes)
                .stream()
                .filter(Objects::nonNull)
                .filter(netp -> StringUtils.isNotBlank(netp.getA71()) && netp.getTreatmentProcedure_Id() != null)
                .filter(netp -> !netp.getTreatmentProcedure_Id().equals(dto.getNhiExtendTreatmentProcedure().getId()))
                .filter(netp -> Long.parseLong(dto.getNhiExtendTreatmentProcedure().getA71()) > Long.parseLong(netp.getA71()))
                .anyMatch(netpt -> {
                    LocalDate pastTxDate = DateTimeUtil.transformROCDateToLocalDate(netpt.getA71());
                    if (pastTxDate.plus(limitDays).isAfter(currentTxDate)) {
                        matchedNhiExtendTreatmentProcedure.add(
                            nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(netpt));
                        return true;
                    } else {
                        return false;
                    }
                 });

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validated(!hasCodeBeforeDate);

        if (!result.isValidated()) {
            NhiExtendTreatmentProcedure match = matchedNhiExtendTreatmentProcedure.get(0);
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

}
