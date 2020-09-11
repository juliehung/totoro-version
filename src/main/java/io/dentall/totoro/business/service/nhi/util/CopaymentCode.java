package io.dentall.totoro.business.service.nhi.util;

public enum CopaymentCode {
    _001("001", "重大傷病");

    private String code;
    
    private String zhName;

    public String getZhName() {
        return zhName;
    }

    public String getCode() {
        return code;
    }
    
    CopaymentCode(String code, String zhName) {
        this.code = code;
        this.zhName = zhName;
    }
}
