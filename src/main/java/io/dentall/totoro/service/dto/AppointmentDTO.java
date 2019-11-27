package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.enumeration.AppointmentStatus;
import io.dentall.totoro.domain.enumeration.Gender;
import io.dentall.totoro.domain.enumeration.RegistrationStatus;

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

    private final Long id;

    private final boolean newPatient;

    private final RegistrationStatus registrationStatus;

    private final Instant patientLastModifiedDate;

    private final String patientLastModifiedBy;

    public AppointmentDTO(Long patientId, String patientName, LocalDate birth, String nationalId, Gender gender, String phone, Instant expectedArrivalTime, ExtendUser doctor, Integer requiredTreatmentTime, String note, boolean microscope, boolean baseFloor, AppointmentStatus status, Instant registerArrivalTime, Long id, boolean newPatient, RegistrationStatus registrationStatus, Instant patientLastModifiedDate, String patientLastModifiedBy) {
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
        this.id = id;
        this.newPatient = newPatient;
        this.registrationStatus = registrationStatus;
        this.patientLastModifiedDate = patientLastModifiedDate;
        this.patientLastModifiedBy = patientLastModifiedBy;
    }

    public Long getId() {
        return id;
    }

    public Instant getPatientLastModifiedDate() {
        return patientLastModifiedDate;
    }

    public String getPatientLastModifiedBy() {
        return patientLastModifiedBy;
    }

    public boolean isNewPatient() {
        return newPatient;
    }

    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
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
