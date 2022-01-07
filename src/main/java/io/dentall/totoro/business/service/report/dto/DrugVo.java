package io.dentall.totoro.business.service.report.dto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class DrugVo implements DrugDto, FutureAppointment {

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

    private Instant registrationDate;

    private Long drugId;

    private String drugName;

    private String drugNhiCode;

    private String drugDay;

    private String drugWay;

    private String drugFrequency;

    private List<FutureAppointmentVo> futureAppointmentList;

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
    public Long getDrugId() {
        return drugId;
    }

    @Override
    public String getDrugName() {
        return drugName;
    }

    @Override
    public String getDrugNhiCode() {
        return drugNhiCode;
    }

    @Override
    public String getDrugDay() {
        return drugDay;
    }

    @Override
    public String getDrugWay() {
        return drugWay;
    }

    @Override
    public String getDrugFrequency() {
        return drugFrequency;
    }

    @Override
    public Instant getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Instant registrationDate) {
        this.registrationDate = registrationDate;
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

    public Period getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Period patientAge) {
        this.patientAge = patientAge;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public void setPatientNote(String patientNote) {
        this.patientNote = patientNote;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public void setDrugNhiCode(String drugNhiCode) {
        this.drugNhiCode = drugNhiCode;
    }

    public void setDrugDay(String drugDay) {
        this.drugDay = drugDay;
    }

    public void setDrugWay(String drugWay) {
        this.drugWay = drugWay;
    }

    public void setDrugFrequency(String drugFrequency) {
        this.drugFrequency = drugFrequency;
    }

    public List<FutureAppointmentVo> getFutureAppointmentList() {
        return futureAppointmentList;
    }

    public void setFutureAppointmentList(List<FutureAppointmentVo> futureAppointmentList) {
        this.futureAppointmentList = futureAppointmentList;
    }
}
