package io.dentall.totoro.business.service.nhi.util;

public enum SurfaceConstraint {
    MUST_HAVE_M_D_O(Integer.MAX_VALUE, ".*[MOD]{1,3}.*", "充填牙面部位應包含雙鄰接面(Mesial, M; Distal, D) 及咬合面(Occlusal, O)"),
    MAX_2_SURFACES(2, ".*", "申報面數最高以 2 面為限"),
    MAX_3_SURFACES(3, ".*", "申報面數最高以 3 面為限"),
    MIN_1_SURFACES(1, ".*", "牙面數不可小於 1"),
    MIN_2_SURFACES(2, ".*", "牙面數不可小於 2"),
    MIN_3_SURFACES(3, ".*", "牙面數不可小於 3"),
    EQUAL_4_SURFACES(4, ".*", "牙面面數應為4面"),
    ;

    private int limitNumber;

    private String limitRegex;

    private String errorMessage;

    SurfaceConstraint(int limitNumber, String limitRegex, String errorMessage) {
        this.limitNumber = limitNumber;
        this.limitRegex = limitRegex;
        this.errorMessage = errorMessage;
    }

    public int getLimitNumber() {
        return limitNumber;
    }

    public String getLimitRegex() {
        return limitRegex;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
