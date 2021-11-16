package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.Patient;

import java.time.Instant;
import java.util.List;

public class LedgerVM {

    private Long id;

    private String type;

    private Long patientId;

    private Double amount;

    private String projectCode;

    private String displayName;

    private Double charge;

    private String note;

    private Instant date;

    private Long gid;

    private Boolean includeStampTax;

    private Instant printTime;

    private List<LedgerReceiptVM> ledgerReceipts;

    Patient patient;

    @Deprecated
    String doctor;

    Long doctorId;

    public LedgerVM() {

    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getGid() {
        return gid;
    }

    public void setGid(Long gid) {
        this.gid = gid;
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

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
