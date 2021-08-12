package io.dentall.totoro.business.service.nhi.metric.util;

public class NhiHelper {

    private NhiHelper() {
    }

    public static long purgePoint(String code, long examPoint) {
        if ("00305".equals(code) || "00306".equals(code)) {
            return 230L;
        } else if ("00307".equals(code) || "00308".equals(code)) {
            return 120L;
        } else if ("00309".equals(code) || "00310".equals(code)) {
            return 260L;
        } else if ("00311".equals(code)) {
            return 520L;
        } else if ("00312".equals(code)) {
            return 420L;
        } else if ("00313".equals(code)) {
            return 320L;
        } else if ("00314".equals(code)) {
            return 320L;
        } else if ("00315".equals(code)) {
            return 600L;
        } else if ("00316".equals(code)) {
            return 600L;
        } else if ("00317".equals(code)) {
            return 600L;
        }
        return examPoint;
    }
}
