package io.dentall.totoro.business.service.nhi.util;

public enum ToothConstraint {
    SPECIFIC_TOOTH("^[1-4][1-8]$|^[5-8][1-5]$", "牙位限填: 11-18,21-28,31-38,41-48,51- 5,61-65,71-75,81-85"),
    GENERAL_TOOTH("^[1-4][1-8]$|^[5-8][1-5]$|^[1-9]9$", "牙位限填: 11-19,21-29,31-39,41-49,51-55,61-65,71-75,81-85,99"),
    FRONT_TOOTH("^[1-8][1-3]$|^[1-9]9$", "牙位限填: 11-13,21-23,31-33,41-43,51-53,61-63,71-73,81-83,19,29,39,49,59,69,79,89,99"),
    BACK_TOOTH("^[1-4][4-8]$|^[5-8][4-5]$|^[1-9]9$", "牙位限填: 14-18,24-28,34-38,44-48,54-55,64-65,74-75,84-85,19,29,39,49,59,69,79,89,99"),
    PERMANENT_TOOTH("^[1-4][1-9]$|99", "牙位限填: 11-19,21-29,31-39,41-49,99"),
    PERMANENT_FRONT_TOOTH("^[1-4][1-3]$|^[1-4]9$|^99$", "牙位限填: 11-13,21-23,31-33,41-43,19,29,39,49,99"),
    PERMANENT_BACK_TOOTH("^[1-4][4-9]$|^99$", "牙位限填: 14-19,24-29,34-39,44-49,99"),
    PARTIAL_ZONE("UB|LB|UR|UL|LR|LL", "牙位限填: UB, LB, UR, UL, LR, LL"),
    FOUR_PHASE_ZONE("UR|UL|LR|LL", "牙位限填: UR, UL, LR, LL"),
    DECIDUOUS_TOOTH("^[5-8][1-5]$", "牙位限填: 51-55, 61-65, 71-75, 81-85"),
    FULL_ZONE("FM", "牙位限填: FM"),
    VALIDATED_ALL("^[1-4][1-8]$|^[5-8][1-5]$|^[1-9]9$|UR|UL|LR|LL|UA|LA|FM", "牙位限填: 11-19,21-29,31-39,41-49,51-55,61-65,71-75,81-85,99,UR,UL,LR,LL,UA,LA,FM"),
    ONLY_16("16", "牙位限填: 16"),
    ONLY_26("26", "牙位限填: 26"),
    ONLY_36("36", "牙位限填: 36"),
    ONLY_46("46", "牙位限填: 46"),
    ;

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
