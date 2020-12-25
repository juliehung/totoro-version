package io.dentall.totoro.business.service.nhi;

import java.lang.reflect.InvocationTargetException;

/**
 * 健保代碼 檢核功能，會依照年分可以進行不同的實作 e.g. NhiRuleCheckService20200901Impl。在實作時，你可能會遇到多個檢查一樣的狀況，
 * 但為了方便管理、除錯以及彈性，務必不要把相同的檢核，提出來作為 utils, method, etc,. 等方式使用，保持這裡面項目的簡單。
 *
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

    // 890***
    O validate89001C(B dto);

    O validate89002C(B dto);

    O validate89003C(B dto);

    O validate89004C(B dto);

    O validate89005C(B dto);

    O validate89008C(B dto);

    O validate89009C(B dto);

    O validate89010C(B dto);

    O validate89011C(B dto);

    O validate89012C(B dto);

    O validate89013C(B dto);

    O validate89014C(B dto);

    O validate89015C(B dto);

    // 891***
    O validate89101C(B dto);

    O validate89102C(B dto);

    O validate89103C(B dto);

    O validate89104C(B dto);

    O validate89105C(B dto);

    O validate89108C(B dto);

    O validate89109C(B dto);

    O validate89110C(B dto);

    O validate89111C(B dto);

    O validate89112C(B dto);

    O validate89113C(B dto);

    O validate89114C(B dto);

    O validate89115C(B dto);

}
