package io.dentall.totoro.business.dto;

public class EsignDTO {

    private Long patientId;

    private String contentType;

    private String base64;

    public EsignDTO() { }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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
        return "EsignDTO{" +
            "patientId='" + patientId + "'" +
            ", contentType='" + contentType + "'" +
            ", base64=" + base64 + "'" +
            "}";
    }
}
