package io.dentall.totoro.business.service.report.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class OwnExpenseVo implements OwnExpenseDto, FutureAppointment, SubsequentDisposal {

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

    private String procedureName;

    private String procedureTooth;

    private String procedureSurface;

    private String procedureMinorType;

    private Long treatmentProcedureId;

    private List<FutureAppointmentVo> futureAppointmentList;

    private Instant registrationDate;

    private List<NhiVo> subsequentNhiList;

    private List<OwnExpenseVo> subsequentOwnExpenseList;

    private List<DrugVo> subsequentDrugList;

    @Override
    public Long getDisposalId() {
        return disposalId;
    }

    @Override
    public LocalDate getDisposalDate() {
        return disposalDate;
    }

    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public Long getDoctorId() {
        return doctorId;
    }

    @Override
    public String getDoctorName() {
        return doctorName;
    }

    @Override
    public Long getPatientId() {
        return patientId;
    }

    @Override
    public String getPatientName() {
        return patientName;
    }

    @Override
    public LocalDate getPatientBirth() {
        return patientBirth;
    }

    @Override
    public String getPatientPhone() {
        return patientPhone;
    }

    @Override
    public String getPatientNote() {
        return patientNote;
    }

    @Override
    public Long getProcedureId() {
        return procedureId;
    }

    @Override
    public String getProcedureName() {
        return procedureName;
    }

    @Override
    public String getProcedureMinorType() {
        return procedureMinorType;
    }

    @Override
    public Long getTreatmentProcedureId() {
        return treatmentProcedureId;
    }

    public void setDisposalId(Long disposalId) {
        this.disposalId = disposalId;
    }

    public void setDisposalDate(LocalDate disposalDate) {
        this.disposalDate = disposalDate;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setPatientBirth(LocalDate patientBirth) {
        this.patientBirth = patientBirth;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public void setPatientNote(String patientNote) {
        this.patientNote = patientNote;
    }

    public void setProcedureId(Long procedureId) {
        this.procedureId = procedureId;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public void setProcedureMinorType(String procedureMinorType) {
        this.procedureMinorType = procedureMinorType;
    }

    public Period getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Period patientAge) {
        this.patientAge = patientAge;
    }

    public String getProcedureTooth() {
        return procedureTooth;
    }

    public void setProcedureTooth(String procedureTooth) {
        this.procedureTooth = procedureTooth;
    }

    public String getProcedureSurface() {
        return procedureSurface;
    }

    public void setProcedureSurface(String procedureSurface) {
        this.procedureSurface = procedureSurface;
    }

    public List<FutureAppointmentVo> getFutureAppointmentList() {
        return futureAppointmentList;
    }

    public void setFutureAppointmentList(List<FutureAppointmentVo> futureAppointmentList) {
        this.futureAppointmentList = futureAppointmentList;
    }

    public void setTreatmentProcedureId(Long treatmentProcedureId) {
        this.treatmentProcedureId = treatmentProcedureId;
    }

    @Override
    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<NhiVo> getSubsequentNhiList() {
        return subsequentNhiList;
    }

    public List<OwnExpenseVo> getSubsequentOwnExpenseList() {
        return subsequentOwnExpenseList;
    }

    public List<DrugVo> getSubsequentDrugList() {
        return subsequentDrugList;
    }

    @Override
    public void setSubsequentNhiList(List<NhiVo> subsequentNhiList) {
        this.subsequentNhiList = subsequentNhiList;
    }

    @Override
    public void setSubsequentOwnExpenseList(List<OwnExpenseVo> subsequentOwnExpenseList) {
        this.subsequentOwnExpenseList = subsequentOwnExpenseList;
    }

    @Override
    public void setSubsequentDrugList(List<DrugVo> subsequentDrugList) {
        this.subsequentDrugList = subsequentDrugList;
    }
}
