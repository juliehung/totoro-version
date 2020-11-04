package io.dentall.totoro.business.service.nhi.util;

public enum ToothConstraint {
    GENERAL_TOOTH("^[1-4][1-8]$|^[5-8][1-5]$|^[1-9]9$", "請填入正確牙位後，再次點擊檢查代碼\n可填範圍: 11-19,21-29,31-39,41-49,51- 55,61-65,71-75,81-85,99"),
    FRONT_TOOTH("^[1-8][1-3]$|^[1-9]9$", "請填入正確牙位後，再次點擊檢查代碼\n可填範圍: 11-13,21-23,31-33,41-43,51-53,61-63,71-73,81-83,19,29,39,49,59,69,79,89,99"),
    BACK_TOOTH("^[1-4][4-8]$|^[5-8][4-5]$|^[1-9]9$", "請填入正確牙位後，再次點擊檢查代碼\n可填範圍: 14-18,24-28,34-38,44-48,54-55,64-65,74-75,84-85,19,29,39,49,59,69,79,89,99"),
    PERMANENT_TOOTH("^[1-4][1-9]$|99", "請填入正確牙位後，再次點擊檢查代碼\n可填範圍: 11-19,21-29,31-39,41-49,99");

    private String message;

    private String regex;

    ToothConstraint(String regex, String message) {
        this.message = message;
        this.regex = regex;
    }

    public String getMessage() {
        return this.message;
    }

    public String getRegex() {
        return this.regex;
    }
}
