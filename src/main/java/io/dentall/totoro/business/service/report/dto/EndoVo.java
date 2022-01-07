package io.dentall.totoro.business.service.report.dto;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class EndoVo implements SubsequentDisposal, FutureAppointment {

    private Long disposalId;

    private LocalDate disposalDate;

    private String cardNumber;

    private Long doctorId;

    private String doctorName;

    private Long patientId;

    private String patientName;

    private LocalDate patientBirth;

    private Period patientAge;

    private String patientPhone;

    private String patientNote;

    private Long procedureId;

    private String procedureCode;

    private String procedureTooth;

    private List<NhiVo> subsequentNhiList;

    private List<OwnExpenseVo> subsequentOwnExpenseList;

    private List<DrugVo> subsequentDrugList;

    private List<FutureAppointmentVo> futureAppointmentList;

    public Long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(Long disposalId) {
        this.disposalId = disposalId;
    }

    public LocalDate getDisposalDate() {
        return disposalDate;
    }

    public void setDisposalDate(LocalDate disposalDate) {
        this.disposalDate = disposalDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public LocalDate getPatientBirth() {
        return patientBirth;
    }

    public void setPatientBirth(LocalDate patientBirth) {
        this.patientBirth = patientBirth;
    }

    public Period getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Period patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientNote() {
        return patientNote;
    }

    public void setPatientNote(String patientNote) {
        this.patientNote = patientNote;
    }

    public Long getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Long procedureId) {
        this.procedureId = procedureId;
    }

    public String getProcedureCode() {
        return procedureCode;
    }

    public void setProcedureCode(String procedureCode) {
        this.procedureCode = procedureCode;
    }

    public String getProcedureTooth() {
        return procedureTooth;
    }

    public void setProcedureTooth(String procedureTooth) {
        this.procedureTooth = procedureTooth;
    }

    public List<NhiVo> getSubsequentNhiList() {
        return subsequentNhiList;
    }

    public void setSubsequentNhiList(List<NhiVo> subsequentNhiList) {
        this.subsequentNhiList = subsequentNhiList;
    }

    public List<OwnExpenseVo> getSubsequentOwnExpenseList() {
        return subsequentOwnExpenseList;
    }

    public void setSubsequentOwnExpenseList(List<OwnExpenseVo> subsequentOwnExpenseList) {
        this.subsequentOwnExpenseList = subsequentOwnExpenseList;
    }

    public List<DrugVo> getSubsequentDrugList() {
        return subsequentDrugList;
    }

    public void setSubsequentDrugList(List<DrugVo> subsequentDrugList) {
        this.subsequentDrugList = subsequentDrugList;
    }

    public List<FutureAppointmentVo> getFutureAppointmentList() {
        return futureAppointmentList;
    }

    public void setFutureAppointmentList(List<FutureAppointmentVo> futureAppointmentList) {
        this.futureAppointmentList = futureAppointmentList;
    }

}
