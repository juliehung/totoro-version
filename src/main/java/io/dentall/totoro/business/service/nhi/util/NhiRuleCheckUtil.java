package io.dentall.totoro.business.service.nhi.util;

import io.dentall.totoro.business.service.nhi.NhiRuleCheckDTO;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.NhiExtendTreatmentProcedureRepository;
import io.dentall.totoro.service.util.DateTimeUtil;
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

    public NhiRuleCheckUtil(NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository) {
        this.nhiExtendTreatmentProcedureRepository = nhiExtendTreatmentProcedureRepository;
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

    public boolean hasCodeBeforeDate(NhiRuleCheckDTO dto, @NotNull List<String> codes, @NotNull Period limitDays) {
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
                .filter(netp -> Integer.parseInt(dto.getNhiExtendTreatmentProcedure().getA71()) > Integer.parseInt(netp.getA71()))
                .anyMatch(netpt -> {
                    LocalDate pastTxDate = DateTimeUtil.transformROCDateToLocalDate(netpt.getA71());
                    return pastTxDate.plus(limitDays).isAfter(currentTxDate);
                });
        } else {
            throw new IllegalArgumentException("");
        }

    }
}
