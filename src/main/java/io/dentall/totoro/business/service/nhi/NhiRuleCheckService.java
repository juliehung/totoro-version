package io.dentall.totoro.business.service.nhi;

import java.lang.reflect.InvocationTargetException;

/**
 * 健保代碼 檢核功能，會依照年分可以進行不同的實作 e.g. NhiRuleCheckService20200901Impl
 * @param <A> 前端 vm 傳進來的內容格式
 * @param <B> 後端運算互傳的格式
 * @param <O> 後端運算結果回傳的格式
 */
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
