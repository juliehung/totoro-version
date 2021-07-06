package io.dentall.totoro.business.service;

public enum NhiRuleCheckSourceType {
    SYSTEM_RECORD("系統"),
    NHI_CARD_RECORD("IC"),
    TODAY_OTHER_DISPOSAL("今日其他處置單"),
    CURRENT_DISPOSAL("該處置單");

    private String value;

    NhiRuleCheckSourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
