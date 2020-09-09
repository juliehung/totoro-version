package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckVM;

import java.lang.reflect.InvocationTargetException;

public interface NhiRuleCheckService {

    NhiRuleCheckResultVM dispatch(String code, NhiRuleCheckVM vm) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    // 910***
    NhiRuleCheckResultVM validate91003C(NhiRuleCheckDTO dto);

    NhiRuleCheckResultVM validate91004C(NhiRuleCheckDTO dto);

    NhiRuleCheckResultVM validate91005C(NhiRuleCheckDTO dto);

    NhiRuleCheckResultVM validate91015C(NhiRuleCheckDTO dto);

    NhiRuleCheckResultVM validate91016C(NhiRuleCheckDTO dto);

    NhiRuleCheckResultVM validate91017C(NhiRuleCheckDTO dto);

    NhiRuleCheckResultVM validate91018C(NhiRuleCheckDTO dto);

    // 911***
    NhiRuleCheckResultVM validate91103C(NhiRuleCheckDTO dto);

    NhiRuleCheckResultVM validate91104C(NhiRuleCheckDTO dto);

    // ****8*
    NhiRuleCheckResultVM validate81(NhiRuleCheckDTO dto);

    NhiRuleCheckResultVM validate88(NhiRuleCheckDTO dto);

    NhiRuleCheckResultVM validate87(NhiRuleCheckDTO dto);

    NhiRuleCheckResultVM validate89(NhiRuleCheckDTO dto);
}
