package io.dentall.totoro.business.service.nhi;

import io.dentall.totoro.business.vm.nhi.NhiRuleCheckVM;

import java.lang.reflect.InvocationTargetException;

public interface NhiRuleCheckService {

    boolean dispatcher(String code, NhiRuleCheckVM vm) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

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
