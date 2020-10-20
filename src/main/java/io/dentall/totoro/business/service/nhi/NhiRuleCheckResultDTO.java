package io.dentall.totoro.business.service.nhi;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.business.service.NhiRuleCheckInfoType;
import io.dentall.totoro.business.service.NhiRuleCheckSourceType;

public class NhiRuleCheckResultDTO {

    private boolean validated;

    @JsonProperty("infoType")
    private NhiRuleCheckInfoType nhiRuleCheckInfoType;

    @JsonProperty("sourceType")
    private NhiRuleCheckSourceType nhiRuleCheckSourceType;

    private String validateTitle;

    private String message;

    public NhiRuleCheckResultDTO nhiRuleCheckInfoType(NhiRuleCheckInfoType nhiRuleCheckInfoType) {
        this.nhiRuleCheckInfoType = nhiRuleCheckInfoType;
        return this;
    }

    public NhiRuleCheckResultDTO nhiRuleCheckSourceType(NhiRuleCheckSourceType nhiRuleCheckSourceType) {
        this.nhiRuleCheckSourceType = nhiRuleCheckSourceType;
        return this;
    }

    public NhiRuleCheckResultDTO validateTitle(String validateTitle) {
        this.validateTitle = validateTitle;
        return this;
    }

    public NhiRuleCheckResultDTO validated(boolean validated) {
        this.validated = validated;
        return this;
    }

    public NhiRuleCheckResultDTO message(String message) {
        this.message = message;
        return this;
    }

    public NhiRuleCheckInfoType getNhiRuleCheckInfoType() {
        return nhiRuleCheckInfoType;
    }

    public void setNhiRuleCheckInfoType(NhiRuleCheckInfoType nhiRuleCheckInfoType) {
        this.nhiRuleCheckInfoType = nhiRuleCheckInfoType;
    }

    public NhiRuleCheckSourceType getNhiRuleCheckSourceType() {
        return nhiRuleCheckSourceType;
    }

    public void setNhiRuleCheckSourceType(NhiRuleCheckSourceType nhiRuleCheckSourceType) {
        this.nhiRuleCheckSourceType = nhiRuleCheckSourceType;
    }

    public String getValidateTitle() {
        return validateTitle;
    }

    public void setValidateTitle(String validateTitle) {
        this.validateTitle = validateTitle;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
