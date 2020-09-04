package io.dentall.totoro.business.service.nhi.util;

import io.dentall.totoro.business.service.nhi.NhiRuleCheckDTO;
import io.dentall.totoro.service.util.DateTimeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Period;

@Service
public class NhiRuleCheckUtil {
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
}
