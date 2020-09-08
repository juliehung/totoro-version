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

    private void assignDtoByPatientId(@NotNull NhiRuleCheckDTO dto, @NotNull Long patientId) {
        dto.setPatient(
            PatientMapper.patientTableToPatient(
                patientRepository.findPatientById(patientId)
                    .orElseThrow(() -> new ResourceNotFoundException("patient with " + patientId))));
    }

    private void assignDtoByNhiExtendTreatmentProcedureId(@NotNull NhiRuleCheckDTO dto, @NotNull Long nhiExtendTreatmentProcedureId) {
        dto.setNhiExtendTreatmentProcedure(
            nhiExtendTreatmentProcedureMapper.nhiExtendTreatmentProcedureTableToNhiExtendTreatmentProcedureTable(
                nhiExtendTreatmentProcedureRepository.findById(nhiExtendTreatmentProcedureId, NhiExtendTreatmentProcedureTable.class)
                    .orElseThrow(() -> new ResourceNotFoundException("patient with " + nhiExtendTreatmentProcedureId))));
    }

    public NhiRuleCheckDTO convertVmToDto(@NotNull String code, @NotNull NhiRuleCheckVM vm) {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();

        if (vm.getPatientId() != null) {
            assignDtoByPatientId(dto, vm.getPatientId());
        }

        if (vm.getTreatmentId() != null) {
            assignDtoByNhiExtendTreatmentProcedureId(dto, vm.getTreatmentId());
        }

        // 產生暫時的 treatment 資料，在後續的檢驗中被檢核所需
        if (vm.getPatientId() != null && vm.getTreatmentId() == null) {
            BadRequestAlertException.ifNull(vm.getTmpTreatmentA74(), "診療項目需輸入 牙位", "診療項目需輸入 牙位 A74", "tmpTreatmentA74");
            BadRequestAlertException.ifNull(vm.getTmpTreatmentA75(), "診療項目需輸入 牙面", "診療項目需輸入 牙面 A75", "tmpTreatmentA75");

            dto.setNhiExtendTreatmentProcedure(
                new NhiExtendTreatmentProcedure()
                    .a71(DateTimeUtil.transformLocalDateToRocDate(Instant.now()))
                    .a73(code)
                    .a74(vm.getTmpTreatmentA74())
                    .a75(vm.getTmpTreatmentA75()));
        }

        return dto;
    }

    public NhiRuleCheckResultDTO equalsOrGreaterThanAge12(@NotNull NhiRuleCheckDTO dto) {

        Period p = Period.between(
            dto.getPatient().getBirth(),
            DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()));

        NhiRuleCheckResultDTO result = new NhiRuleCheckResultDTO()
            .validated(p.getYears() > 12);

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
