package io.dentall.totoro.service.dto;

import javax.validation.constraints.NotBlank;

/**
 * A DTO representing a SmsSend.
 */
public class SmsSendDTO {

    @NotBlank
    private String clinic;

    @NotBlank
    private String phone;

    private String content;

    public SmsSendDTO() {
        // Empty constructor needed for Jackson.
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SmsSendDTO{" +
            "clinic='" + clinic + '\'' +
            ", phone='" + phone + '\'' +
            ", content='" + content + '\'' +
            "}";
    }
}
