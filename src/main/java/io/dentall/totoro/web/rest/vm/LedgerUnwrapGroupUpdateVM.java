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

}
