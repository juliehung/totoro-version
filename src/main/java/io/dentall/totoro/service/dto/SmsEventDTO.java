package io.dentall.totoro.service.dto;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * A DTO representing a SmsEvent.
 */
public class SmsEventDTO {

    private String id;

    private String clinic;

    private String title;

    private String vendor;

    private List<Sms> sms;

    private String status;

    private String createdBy;

    private Instant createdDate;

    private String modifiedBy;

    private Instant modifiedDate;

    private Map<String, Object> metadata;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public List<Sms> getSms() {
        return sms;
    }

    public void setSms(List<Sms> sms) {
        this.sms = sms;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Instant modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    @Override
    public String toString() {
        return "SmsEventDTO{" +
            "id='" + id + '\'' +
            ", clinic='" + clinic + '\'' +
            ", title='" + title + '\'' +
            ", vendor='" + vendor + '\'' +
            ", sms='" + sms + '\'' +
            ", status='" + status + '\'' +
            ", createdBy='" + createdBy + '\'' +
            ", createdDate='" + createdDate + '\'' +
            ", modifiedBy='" + modifiedBy + '\'' +
            ", modifiedDate='" + modifiedDate + '\'' +
            ", metadata=" + metadata +
            "}";
    }

    public static class Sms {

        @NotBlank
        private String phone;

        private String content;

        private Map<String, Object> metadata;

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

        public Map<String, Object> getMetadata() {
            return metadata;
        }

        public void setMetadata(Map<String, Object> metadata) {
            this.metadata = metadata;
        }

        @Override
        public String toString() {
            return "SmsEventDTO.Sms{" +
                "phone='" + phone + '\'' +
                ", content='" + content + '\'' +
                ", metadata=" + metadata +
                "}";
        }
    }
}
