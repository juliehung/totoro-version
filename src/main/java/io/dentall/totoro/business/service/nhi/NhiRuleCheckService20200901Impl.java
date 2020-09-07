package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class NhiRuleCheckService20200901Impl implements NhiRuleCheckService {

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheckService20200901Impl(NhiRuleCheckUtil nhiRuleCheckUtil) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    @Override
    public boolean validate91003C(NhiRuleCheckDTO dto) {
        return nhiRuleCheckUtil.equalsOrGreaterThanAge12(NhiRuleCheckUtil.calculatePatientAgeAtTreatmentDate(dto.getPatient(), dto.getNhiExtendTreatmentProcedure())) &&
            !nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91003C"}.clone()),
                NhiRuleCheckUtil.nhiHalfYear);
    }

    @Override
    public boolean validate91004C(NhiRuleCheckDTO dto) {
        return nhiRuleCheckUtil.equalsOrGreaterThanAge12(NhiRuleCheckUtil.calculatePatientAgeAtTreatmentDate(dto.getPatient(), dto.getNhiExtendTreatmentProcedure())) &&
            !nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91004C"}.clone()),
                NhiRuleCheckUtil.nhiHalfYear);
    }

    @Override
    public boolean validate91005C(NhiRuleCheckDTO dto) {
        return nhiRuleCheckUtil.equalsOrGreaterThanAge12(NhiRuleCheckUtil.calculatePatientAgeAtTreatmentDate(dto.getPatient(), dto.getNhiExtendTreatmentProcedure())) &&
            !nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91005"}.clone()),
                NhiRuleCheckUtil.nhiMonthAndHalf);
    }

    @Override
    public boolean validate91015C(NhiRuleCheckDTO dto) {
        return nhiRuleCheckUtil.equalsOrGreaterThanAge12(NhiRuleCheckUtil.calculatePatientAgeAtTreatmentDate(dto.getPatient(), dto.getNhiExtendTreatmentProcedure())) &&
            !nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91015C"}.clone()),
                NhiRuleCheckUtil.nhiMonthAndHalf);
    }

    @Override
    public boolean validate91016C(NhiRuleCheckDTO dto) {
        return nhiRuleCheckUtil.equalsOrGreaterThanAge12(NhiRuleCheckUtil.calculatePatientAgeAtTreatmentDate(dto.getPatient(), dto.getNhiExtendTreatmentProcedure())) &&
            !nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91016C"}.clone()),
                NhiRuleCheckUtil.nhiMonthAndHalf);
    }

    @Override
    public boolean validate91017C(NhiRuleCheckDTO dto) {
        return nhiRuleCheckUtil.equalsOrGreaterThanAge12(NhiRuleCheckUtil.calculatePatientAgeAtTreatmentDate(dto.getPatient(), dto.getNhiExtendTreatmentProcedure())) &&
            !nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91017C"}.clone()),
                NhiRuleCheckUtil.nhiMonthAndHalf);
    }

    @Override
    public boolean validate91018C(NhiRuleCheckDTO dto) {
        return nhiRuleCheckUtil.equalsOrGreaterThanAge12(NhiRuleCheckUtil.calculatePatientAgeAtTreatmentDate(dto.getPatient(), dto.getNhiExtendTreatmentProcedure())) &&
            !nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91018C"}.clone()),
                NhiRuleCheckUtil.nhiMonthAndHalf);
    }

    @Override
    public boolean validate91103C(NhiRuleCheckDTO dto) {
        return nhiRuleCheckUtil.equalsOrGreaterThanAge12(NhiRuleCheckUtil.calculatePatientAgeAtTreatmentDate(dto.getPatient(), dto.getNhiExtendTreatmentProcedure())) &&
            !nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91103C"}.clone()),
                NhiRuleCheckUtil.nhiMonthAndHalf);
    }

    @Override
    public boolean validate91104C(NhiRuleCheckDTO dto) {
        return nhiRuleCheckUtil.equalsOrGreaterThanAge12(NhiRuleCheckUtil.calculatePatientAgeAtTreatmentDate(dto.getPatient(), dto.getNhiExtendTreatmentProcedure())) &&
            !nhiRuleCheckUtil.hasCodeBeforeDate(dto,
                Arrays.asList(new String[]{"91104C"}.clone()),
                NhiRuleCheckUtil.nhiMonthAndHalf);
    }
}
