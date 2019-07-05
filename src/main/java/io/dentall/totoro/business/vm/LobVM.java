package io.dentall.totoro.business.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LobVM {

    @JsonProperty
    private String contentType;

    @JsonProperty
    private String base64;

    public LobVM(String contentType, String base64) {
        this.contentType = contentType;
        this.base64 = base64;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    @Override
    public String toString() {
        return "LobVM{" +
            "contentType='" + contentType + "'" +
            ", base64=" + base64 + "'" +
            "}";
    }
}
