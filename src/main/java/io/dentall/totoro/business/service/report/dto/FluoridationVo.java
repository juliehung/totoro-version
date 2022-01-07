package io.dentall.totoro.business.service.report.dto;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class FluoridationVo implements FutureAppointment {

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

    private LocalDate nextAvailableTreatmentDate;

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

    public LocalDate getNextAvailableTreatmentDate() {
        return nextAvailableTreatmentDate;
    }

    public void setNextAvailableTreatmentDate(LocalDate nextAvailableTreatmentDate) {
        this.nextAvailableTreatmentDate = nextAvailableTreatmentDate;
    }

    public List<FutureAppointmentVo> getFutureAppointmentList() {
        return futureAppointmentList;
    }

    public void setFutureAppointmentList(List<FutureAppointmentVo> futureAppointmentList) {
        this.futureAppointmentList = futureAppointmentList;
    }

}
