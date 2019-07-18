package io.dentall.totoro.business.vm;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public class LobVM {

    @JsonProperty
    private String contentType;

    @JsonProperty
    private String base64;

    private Instant createdDate;

    private Long id;

    public LobVM() { }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public LobVM createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getContentType() {
        return contentType;
    }

    public LobVM contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getBase64() {
        return base64;
    }

    public LobVM base64(String base64) {
        this.base64 = base64;
        return this;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }

    public Long getId() {
        return id;
    }

    public LobVM id(Long id) {
        this.id = id;
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LobVM{" +
            "contentType='" + contentType + "'" +
            ", base64=" + base64 + "'" +
            ", createdDate=" + createdDate + "'" +
            ", id=" + id + "'" +
            "}";
    }
}
