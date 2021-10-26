package io.dentall.totoro.business.service.nhi.code;

import static java.util.stream.Collectors.toCollection;

public enum NhiCodeAttribute {
    ToothBasic, // 以牙齒數為計算基礎
    Endo, // 根管
    Od,  // 補牙
    Ext, // 拔牙
    Sc, // 洗牙
    Exam1, // 一般牙科門診診察費(不含Xray)
    Exam2, // 一般牙科門診診察費(Xray)
    Exam3, // 符合牙醫門診加強感染管制實施方案之牙科門診診察費(不含Xray)
    Exam4, // 符合牙醫門診加強感染管制實施方案之牙科門診診察費(Xray)
    Hideout // 山地離島診察代碼
    ;

    public static NhiCodeHashSet findNhiCode(NhiCodeAttribute attribute) {
        return NhiCode.CODES.stream()
            .filter(code -> code.contain(attribute))
            .collect(toCollection(NhiCodeHashSet::new));
    }

}
