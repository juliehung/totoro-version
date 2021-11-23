package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.LedgerReceipt;
import io.dentall.totoro.service.dto.DoctorVM;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class LedgerGroupVM {

    private Long ledgerGroupId;

    private Long ledgerId;

    private Instant ledgerDate;

    private Instant ledgerGroupDate;

    private String displayName;

    private String projectCode;

    private Double amount;

    private Double charge;

    private String type;

    private String note;

    private Boolean includeStampTax;

    private PatientVM patient;

    private DoctorVM ledgerDoctor;

    private DoctorVM ledgerGroupDoctor;

    public Long getLedgerGroupId() {
        return ledgerGroupId;
    }

    public void setLedgerGroupId(Long ledgerGroupId) {
        this.ledgerGroupId = ledgerGroupId;
    }

    public Long getLedgerId() {
        return ledgerId;
    }

    public void setLedgerId(Long ledgerId) {
        this.ledgerId = ledgerId;
    }

    public Instant getLedgerDate() {
        return ledgerDate;
    }

    public void setLedgerDate(Instant ledgerDate) {
        this.ledgerDate = ledgerDate;
    }

    public Instant getLedgerGroupDate() {
        return ledgerGroupDate;
    }

    public void setLedgerGroupDate(Instant ledgerGroupDate) {
        this.ledgerGroupDate = ledgerGroupDate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCharge() {
        return charge;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getIncludeStampTax() {
        return includeStampTax;
    }

    public void setIncludeStampTax(Boolean includeStampTax) {
        this.includeStampTax = includeStampTax;
    }

    public PatientVM getPatient() {
        return patient;
    }

    public void setPatient(PatientVM patient) {
        this.patient = patient;
    }

    public DoctorVM getLedgerDoctor() {
        return ledgerDoctor;
    }

    public void setLedgerDoctor(DoctorVM ledgerDoctor) {
        this.ledgerDoctor = ledgerDoctor;
    }

    public DoctorVM getLedgerGroupDoctor() {
        return ledgerGroupDoctor;
    }

    public void setLedgerGroupDoctor(DoctorVM ledgerGroupDoctor) {
        this.ledgerGroupDoctor = ledgerGroupDoctor;
    }

    public List<LedgerReceipt> getLedgerReceipts() {
        return ledgerReceipts;
    }

    public void setLedgerReceipts(List<LedgerReceipt> ledgerReceipts) {
        this.ledgerReceipts = ledgerReceipts;
    }

    private List<LedgerReceipt> ledgerReceipts = new ArrayList<>();
}
