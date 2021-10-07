package io.dentall.totoro.business.service.nhi.metric.source;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public class MetricConstants {

    // 根管
    public static final List<String> CodesByEndo1 = unmodifiableList(asList(
        "90001C", "90002C", "90003C", "90016C", "90018C", "90019C", "90020C"
    ));

    // 補牙
    public static final List<String> CodesByOd = unmodifiableList(asList(
        "89001C", "89002C", "89003C", "89004C", "89005C", "89008C", "89009C", "89010C", "89011C", "89012C"
    ));

    // 拔牙
    public static final List<String> CodesByExt = unmodifiableList(asList(
        "92013C", "92014C", "92015C", "92016C", "92063C", "92055C"
    ));

    // 洗牙
    public static final List<String> CodesBySc1 = unmodifiableList(asList(
        "91003C", "91004C"
    ));

    public static final List<String> CodesByExam1 = unmodifiableList(asList(
        "00121C", "00122C", "00123C", "00124C", "00125C", "00126C", "00128C", "00129C", "00130C", "00133C", "00134C", "00301C", "00302C", "00303C", "00304C"
    ));

    public static final List<String> CodesByExam2 = unmodifiableList(asList(
        "01271C", "01272C", "01273C"
    ));

    public static final List<String> CodesByExam3 = unmodifiableList(asList(
        "00305C", "00306C", "00307C", "00308C", "00309C", "00310C", "00311C", "00312C", "00313C", "00314C"
    ));

    public static final List<String> CodesByExam4 = unmodifiableList(asList(
        "00315C", "00316C", "00317C"
    ));

    // 山地離島診察代碼
    public static final List<String> CodesByHideout = unmodifiableList(asList(
        "00125C", "00126C", "00309C", "00310C"
    ));

    public static final long CLINIC_ID = Long.MIN_VALUE;

    public static final MetricSubject CLINIC = new ClinicSubject();

}
