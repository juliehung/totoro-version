package io.dentall.totoro.business.service.nhi.util;

public enum ToothConstraint {
    GENERAL_TOOTH("^[1-4][1-8]$|^[5-8][1-5]$|^[1-9]9$", "可填範圍: 11-19,21-29,31-39,41-49,51- 55,61-65,71-75,81-85,99"),
    FRONT_TOOTH("^[1-8][1-3]$|^[1-9]9$", "可填範圍: 11-14,21-24,31-34,41-44,51-54,61-64,71-74,81-84,19,29,39,49,59,69,79,89,99"),
    PERMANENT_TOOTH("^[1-4][1-9]$|99", "可填範圍: 11-19,21-29,31-39,41-49,99");

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
