package io.dentall.totoro.business.service.nhi;

public class NhiRuleCheckResultDTO {

    private boolean validated;

    private String validateTitle;

    private String message;

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
