package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AvatarVM implements Serializable {

    @JsonProperty
    private String contentType;

    @JsonProperty
    private String base64;

    public AvatarVM(String contentType, String base64) {
        this.contentType = contentType;
        this.base64 = base64;
    }
}
