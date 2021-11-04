package io.dentall.totoro.web.rest.vm;

import javax.validation.constraints.NotNull;
import java.time.Instant;

public class LedgerUnwrapGroupUpdateVM {
    private Long id;

    private Double charge;

    private String note;

    private String doctor;

    private Instant date;

    private Boolean includeStampTax;

    private Instant printTime;

    private Instant createdDate;

    private String createdBy;

    private Instant lastModifiedDate;

    private String lastModifiedBy;

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
}
