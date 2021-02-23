package io.dentall.totoro.service.dto;

public class NhiExtendDisposalSerialNumberBatchDTO {
    private Long disposalId;

    private String serialNumber;

    public Long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(Long disposalId) {
        this.disposalId = disposalId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
