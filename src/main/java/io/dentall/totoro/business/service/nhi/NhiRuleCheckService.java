package io.dentall.totoro.business.service.nhi;

public interface NhiRuleCheckService {
    // 910***
    boolean validate91003C(NhiRuleCheckDTO dto);
    boolean validate91004C(NhiRuleCheckDTO dto);
    boolean validate91005C(NhiRuleCheckDTO dto);
    boolean validate91015C(NhiRuleCheckDTO dto);
    boolean validate91016C(NhiRuleCheckDTO dto);
    boolean validate91017C(NhiRuleCheckDTO dto);
    boolean validate91018C(NhiRuleCheckDTO dto);
    // 911***
    boolean validate91103C(NhiRuleCheckDTO dto);
    boolean validate91104C(NhiRuleCheckDTO dto);
}
