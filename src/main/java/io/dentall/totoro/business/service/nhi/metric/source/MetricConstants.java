package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.code.NhiCodeHashSet;

import static io.dentall.totoro.business.service.nhi.code.NhiCodeAttribute.*;

public class MetricConstants {

    // 根管
    public static final NhiCodeHashSet CodesByEndo1 = findNhiCode(Endo);

    // 補牙
    public static final NhiCodeHashSet CodesByOd = findNhiCode(Od);

    // 拔牙
    public static final NhiCodeHashSet CodesByExt = findNhiCode(Ext);

    // 洗牙
    public static final NhiCodeHashSet CodesBySc1 = findNhiCode(Sc);

    public static final NhiCodeHashSet CodesByExam1 = findNhiCode(Exam1);

    public static final NhiCodeHashSet CodesByExam2 = findNhiCode(Exam2);

    public static final NhiCodeHashSet CodesByExam3 = findNhiCode(Exam3);

    public static final NhiCodeHashSet CodesByExam4 = findNhiCode(Exam4);

    // 山地離島診察代碼
    public static final NhiCodeHashSet CodesByHideout = findNhiCode(Hideout);

    public static final NhiCodeHashSet calculateByTooth = findNhiCode(ToothBasic);

    public static final long CLINIC_ID = Long.MIN_VALUE;

    public static final MetricSubject CLINIC = new ClinicSubject();

}
