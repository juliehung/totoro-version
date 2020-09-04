package io.dentall.totoro.business.service.nhi.util;

import io.dentall.totoro.business.service.nhi.NhiRuleCheckDTO;
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

    private final NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository;

    public NhiRuleCheckUtil(NhiExtendTreatmentProcedureRepository nhiExtendTreatmentProcedureRepository) {
        this.nhiExtendTreatmentProcedureRepository = nhiExtendTreatmentProcedureRepository;
    }

    public static int calculatePatientAge(NhiRuleCheckDTO dto) {
        Integer age;
        if (dto != null &&
            dto.getPatient() != null &&
            dto.getPatient().getBirth() != null &&
            dto.getNhiExtendTreatmentProcedure() != null &&
            StringUtils.isNotBlank(dto.getNhiExtendTreatmentProcedure().getA71())
        ) {
            age = Period.between(dto.getPatient().getBirth(),
                    DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71()))
                .getYears();

        } else {
            throw new IllegalArgumentException("");
        }

        return age;
    }

    public boolean equalsOrGreaterThanAge12(int age) {
       return age > 12;
    }

    public boolean hasCodeBeforeDate(NhiRuleCheckDTO dto, @NotNull List<String> codes, @NotNull Period limitDays) {
        if (dto != null &&
            dto.getPatient() != null &&
            dto.getPatient().getId() != null &&
            codes.size() > 0
        ) {
            LocalDate currentTxDate = DateTimeUtil.transformROCDateToLocalDate(dto.getNhiExtendTreatmentProcedure().getA71());

            return nhiExtendTreatmentProcedureRepository.findAllByTreatmentProcedure_Disposal_Registration_Appointment_Patient_IdAndA73In(dto.getPatient().getId()
                , codes).stream()
                .filter(Objects::nonNull)
                .anyMatch(netpt -> {
                    LocalDate pastTxDate = DateTimeUtil.transformROCDateToLocalDate(netpt.getA71());
                    return pastTxDate.plus(limitDays).isAfter(currentTxDate);
                });
        } else {
            throw new IllegalArgumentException("");
        }

    }
}
