package io.dentall.totoro.business.service.nhi.util;

public enum CopaymentCode {
    _001("001", "重大傷病", "僅適用於全民健康保險牙醫門診總額特殊醫療服務計畫之適用對 象、化療、放射線治療患者之牙醫醫療服務申報。");

    private String code;

    private String zhName;

    private String notification;

    public String getZhName() {
        return zhName;
    }

    public String getCode() {
        return code;
    }

    public String getNotification() {
        return notification;
    }

    CopaymentCode(String code, String zhName, String notification) {
        this.code = code;
        this.zhName = zhName;
        this.notification = notification;
    }
}
