package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;

public class NhiRuleCheck20200901Impl implements NhiRuleCheck {

    private final NhiRuleCheckUtil nhiRuleCheckUtil;

    public NhiRuleCheck20200901Impl(NhiRuleCheckUtil nhiRuleCheckUtil) {
        this.nhiRuleCheckUtil = nhiRuleCheckUtil;
    }

    @Override
    public boolean validate91003C(NhiRuleCheckDTO dto) {
        return nhiRuleCheckUtil.equalsOrGreaterThanAge12(NhiRuleCheckUtil.calculatePatientAge(dto))
            ;
    }

    @Override
    public boolean validate91004C(NhiRuleCheckDTO dto) {
        return false;
    }

    @Override
    public boolean validate91005C(NhiRuleCheckDTO dto) {
        return false;
    }

    @Override
    public boolean validate91015C(NhiRuleCheckDTO dto) {
        return false;
    }

    @Override
    public boolean validate91016C(NhiRuleCheckDTO dto) {
        return false;
    }

    @Override
    public boolean validate91017C(NhiRuleCheckDTO dto) {
        return false;
    }

    @Override
    public boolean validate91018C(NhiRuleCheckDTO dto) {
        return false;
    }

    @Override
    public boolean validate91103C(NhiRuleCheckDTO dto) {
        return false;
    }

    @Override
    public boolean validate91104C(NhiRuleCheckDTO dto) {
        return false;
    }
}
