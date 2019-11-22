package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.enumeration.AppointmentStatus;
import io.dentall.totoro.domain.enumeration.Gender;

import java.time.Instant;
import java.time.LocalDate;

public class AppointmentDTO {

    private final Long patientId;

    private final String patientName;

    private final LocalDate birth;

    private final String nationalId;

    private final String phone;

    private final Instant expectedArrivalTime;

    private final ExtendUser doctor;

    private final Integer requiredTreatmentTime;

    private final String note;

    private final boolean microscope;

    private final boolean baseFloor;

    private final Gender gender;

    private final AppointmentStatus status;

    private final Instant registerArrivalTime;

    public AppointmentDTO(Long patientId, String patientName, LocalDate birth, String nationalId, Gender gender, String phone, Instant expectedArrivalTime, ExtendUser doctor, Integer requiredTreatmentTime, String note, boolean microscope, boolean baseFloor, AppointmentStatus status, Instant registerArrivalTime) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.birth = birth;
        this.nationalId = nationalId;
        this.phone = phone;
        this.expectedArrivalTime = expectedArrivalTime;
        this.doctor = doctor;
        this.requiredTreatmentTime = requiredTreatmentTime;
        this.note = note;
        this.microscope = microscope;
        this.baseFloor = baseFloor;
        this.gender = gender;
        this.status = status;
        this.registerArrivalTime = registerArrivalTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public Instant getRegisterArrivalTime() {
        return registerArrivalTime;
    }

    public Long getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public String getNationalId() {
        return nationalId;
    }

    public String getPhone() {
        return phone;
    }

    public Instant getExpectedArrivalTime() {
        return expectedArrivalTime;
    }

    public ExtendUser getDoctor() {
        return doctor;
    }

    public Integer getRequiredTreatmentTime() {
        return requiredTreatmentTime;
    }

    public String getNote() {
        return note;
    }

    public boolean isMicroscope() {
        return microscope;
    }

    public boolean isBaseFloor() {
        return baseFloor;
    }

    public Gender getGender() {
        return gender;
    }
}
