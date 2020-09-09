package io.dentall.totoro.business.service.nhi;

import java.lang.reflect.InvocationTargetException;

public interface NhiRuleCheckService<A, B, O> {

    O dispatch(String code, A vm) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    // 910***
    O validate91003C(B dto);

    O validate91004C(B dto);

    O validate91005C(B dto);

    O validate91015C(B dto);

    O validate91016C(B dto);

    O validate91017C(B dto);

    O validate91018C(B dto);

    // 911***
    O validate91103C(B dto);

    O validate91104C(B dto);

    // ****8*
    O validate81(B dto);

    O validate88(B dto);

    O validate87(B dto);

    O validate89(B dto);
}
