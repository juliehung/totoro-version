package io.dentall.totoro.business.service.nhi.util;

public enum ToothConstraint {
    // Tooth
    SPECIFIC_TOOTH("^[1-4][1-8]$|^[5-8][1-5]$", "牙位限填: 11-18,21-28,31-38,41-48,51- 5,61-65,71-75,81-85"),
    GENERAL_TOOTH("^[1-4][1-8]$|^[5-8][1-5]$|^[1-4]9$|^99$", "牙位限填: 11-19,21-29,31-39,41-49,51-55,61-65,71-75,81-85,99"),
    GENERAL_TOOTH_EXCLUDE_WISDOM_TOOTH("^[1-4][1-7]$|^[5-8][1-5]$|^[1-4]9$|^99$", "牙位限填: 11-17,21-27,31-37,41-47,51-55,61-65,71-75,81-85,19,29,39,49,99"),
    GENERAL_TOOTH_AND_FM("^[1-4][1-8]$|^[5-8][1-5]$|^[1-4]9$|^99$|^FM$", "牙位限填: 11-19,21-29,31-39,41-49,51-55,61-65,71-75,81-85,99,FM"),
    FRONT_TOOTH("^[1-8][1-3]$|^[1-4]9$|^99$", "牙位限填: 11-13,21-23,31-33,41-43,51-53,61-63,71-73,81-83,19,29,39,49,99"),
    BACK_TOOTH("^[1-4][4-8]$|^[5-8][4-5]$|^[1-4]9$|^99$", "牙位限填: 14-18,24-28,34-38,44-48,54-55,64-65,74-75,84-85,19,29,39,49,99"),
    BACK_TOOTH_EXCLUDE_WISDOM_TOOTH("^[1-4][4-7]$|^[5-8][4-5]$|^[1-4]9$|^99$", "牙位限填: 14-17,24-27,34-37,44-47,54-55,64-65,74-75,84-85,19,29,39,49,99"),
    PERMANENT_TOOTH("^[1-4][1-9]$|99", "牙位限填: 11-19,21-29,31-39,41-49,99"),
    PERMANENT_FRONT_TOOTH("^[1-4][1-3]$|^[1-4]9$|^99$", "牙位限填: 11-13,21-23,31-33,41-43,19,29,39,49,99"),
    PERMANENT_BACK_TOOTH("^[1-4][4-9]$|^99$", "牙位限填: 14-19,24-29,34-39,44-49,99"),
    PERMANENT_PREMOLAR_TOOTH("^[1-4][4-5]$|^[1-4]9$|^99$", "牙位限填: 14-15,24-25,34-35,44-45,19,29,39,49,99"),
    PERMANENT_MOLAR_TOOTH("^[1-4][6-9]$|^99$", "牙位限填: 16-19,26-29,36-39,46-49,99"),
    DECIDUOUS_TOOTH("^[5-8][1-5]$|^99$", "牙位限填: 51-55,61-65,71-75,81-85,99"),
    DECIDUOUS_TOOTH_AND_PERMANENT_WEIRD_TOOTH("^[5-8][1-5]$|19|29|39|49|^99$", "牙位限填: 51-55,61-65,71-75,81-85,19,29,39,49,99"),

    // Zone
    ALL_ZONE("UR|UL|LR|LL|UA|LA|FM|UB|LB", "牙位限填: UA,LA,FM,UR,UL,LL,LR,UB,LB"),
    PARTIAL_ZONE("UR|UL|LR|LL|UA|LA", "牙位限填: UR,UL,LR,LL,UA,LA"),
    PARTIAL_ZONE_AND_99("UR|UL|LR|LL|UA|LA|99", "牙位限填: UR,UL,LR,LL,UA,LA,99"),
    FOUR_PHASE_ZONE("UR|UL|LR|LL", "牙位限填: UR,UL,LR,LL"),
    FULL_ZONE("FM", "牙位限填: FM"),

    // All validated
    VALIDATED_ALL("^[1-4][1-8]$|^[5-8][1-5]$|^[1-9]9$|UR|UL|LR|LL|UA|LA|FM", "牙位限填: 11-19,21-29,31-39,41-49,51-55,61-65,71-75,81-85,99,UR,UL,LR,LL,UA,LA,FM"),
    VALIDATED_ALL_EXCLUDE_FM("^[1-4][1-8]$|^[5-8][1-5]$|^[1-9]9$|UR|UL|LR|LL|UA|LA", "牙位限填: 11-19,21-29,31-39,41-49,51-55,61-65,71-75,81-85,99,UR,UL,LR,LL,UA,LA"),

    // IC81
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
