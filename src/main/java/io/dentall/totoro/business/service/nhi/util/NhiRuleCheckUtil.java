package io.dentall.totoro.business.service.nhi.util;

import io.dentall.totoro.business.service.nhi.NhiRuleCheckDTO;
import io.dentall.totoro.business.service.nhi.NhiRuleCheckResultDTO;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckVM;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.Patient;
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
import java.time.LocalDate;
import java.time.Period;
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

    public NhiRuleCheckResultVM addResultToVm(@NotNull NhiRuleCheckResultDTO dto, @NotNull NhiRuleCheckResultVM vm) {

        vm.getCheckHistory().add(dto);

        vm.setValidated(
            vm.isValidated() && dto.isValidated()
        );

        if (StringUtils.isNotBlank(dto.getMessage())) {
            vm.getMessages().add(dto.getMessage());
        }

        return vm;
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

    public NhiRuleCheckDTO convertVmToDto(@NotNull NhiRuleCheckVM vm) {
        NhiRuleCheckDTO dto = new NhiRuleCheckDTO();

        if (vm.getPatientId() != null) {
            assignDtoByPatientId(dto, vm.getPatientId());
        }

        if (vm.getTreatmentId() != null) {
            assignDtoByNhiExtendTreatmentProcedureId(dto, vm.getTreatmentId());
        }

        return dto;
    }

    public static int calculatePatientAgeAtTreatmentDate(@NotNull Patient patient, @NotNull NhiExtendTreatmentProcedure targetTreatmentProcedure) {
        Integer age;
        if (patient.getBirth() != null &&
            StringUtils.isNotBlank(targetTreatmentProcedure.getA71())
        ) {
            age = Period.between(patient.getBirth(),
                DateTimeUtil.transformROCDateToLocalDate(targetTreatmentProcedure.getA71()))
                .getYears();
        } else {
            throw new IllegalArgumentException("");
        }

        return age;
    }

    public boolean equalsOrGreaterThanAge12(@NotNull int age) {
        return age > 12;
    }

    public boolean hasCodeBeforeDate(@NotNull NhiRuleCheckDTO dto, @NotNull List<String> codes, @NotNull Period limitDays) {
        if (dto != null &&
            dto.getPatient() != null &&
            dto.getPatient().getId() != null &&
            codes.size() > 0
        ) {
            LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

            return nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73In(dto.getPatient().getId(),
                codes).stream()
                .filter(Objects::nonNull)
                .filter(netp -> StringUtils.isNotBlank(netp.getA71()) && netp.getTreatmentProcedure_Id() != null)
                .filter(netp -> !netp.getTreatmentProcedure_Id().equals(dto.getNhiExtendTreatmentProcedure().getId()))
                .filter(netp -> Long.parseLong(dto.getNhiExtendTreatmentProcedure().getA71()) > Long.parseLong(netp.getA71()))
                .anyMatch(netpt -> {
                    LocalDate pastTxDate = DateTimeUtil.transformROCDateToLocalDate(netpt.getA71());
                    return pastTxDate.plus(limitDays).isAfter(currentTxDate);
                });
        } else {
            throw new IllegalArgumentException("");
        }

    }
}
