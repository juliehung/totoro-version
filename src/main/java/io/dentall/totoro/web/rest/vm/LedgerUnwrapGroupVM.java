package io.dentall.totoro.web.rest.vm;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.List;

public class LedgerUnwrapGroupVM {
    private Long id;

    private String type;

    private Long patientId;

    private Double amount;

    private String projectCode;

    private String displayName;

    @NotNull
    private Double charge;

    private String note;

    @NotNull
    private String doctor;

    @NotNull
    private Instant date;

    @NotNull
    private Long gid;

    private Boolean includeStampTax;

    private Instant printTime;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

    private List<LedgerReceiptVM> ledgerReceipts;

    public List<LedgerReceiptVM> getLedgerReceipts() {
        return ledgerReceipts;
    }

    public void setLedgerReceipts(List<LedgerReceiptVM> ledgerReceipts) {
        this.ledgerReceipts = ledgerReceipts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Boolean getIncludeStampTax() {
        return includeStampTax;
    }

    public void setIncludeStampTax(Boolean includeStampTax) {
        this.includeStampTax = includeStampTax;
    }

    public Instant getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Instant printTime) {
        this.printTime = printTime;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public Double getAmount() {
        return amount;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
}
