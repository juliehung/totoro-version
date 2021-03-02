package io.dentall.totoro.web.rest.vm;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

public class PlainDisposalInfoVM {

    private Instant arrivalTime;

    private String doctorName;

    private LocalDate patientBirth;

    private Long patientId;

    private String patientName;

    private String targetInfo;

    private String phone;

    private String note;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    private List<Instant> futureAppointment;

    public List<Instant> getFutureAppointment() {
        return futureAppointment;
    }

    public void setFutureAppointment(List<Instant> futureAppointment) {
        this.futureAppointment = futureAppointment;
    }

    public LocalDate getPatientBirth() {
        return patientBirth;
    }

    public void setPatientBirth(LocalDate patientBirth) {
        this.patientBirth = patientBirth;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getTargetInfo() {
        return targetInfo;
    }

    public void setTargetInfo(String targetInfo) {
        this.targetInfo = targetInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
