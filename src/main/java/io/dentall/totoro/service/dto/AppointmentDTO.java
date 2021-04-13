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

    private final String patientDisplayName;

    private final LocalDate birth;

    private final String nationalId;

    private final String phone;

    private final Boolean vipPatient;

    private final Instant expectedArrivalTime;

    private final ExtendUser doctor;

    private final Integer requiredTreatmentTime;

    private final String note;

    private final Boolean microscope;

    @Deprecated
    private final Boolean baseFloor;

    private final Gender gender;

    private final AppointmentStatus status;

    private final Instant registerArrivalTime;

    private final Long id;

    private final Boolean newPatient;

    private final RegistrationStatus registrationStatus;

    private final Instant patientLastModifiedDate;

    private final String patientLastModifiedBy;

    private final String patientMedicalId;

    private final Integer colorId;

    private final Boolean firstVisit;

    private final String patientCustomizedDisease;

    private final String patientCustomizedBloodDisease;

    private final String patientCustomizedAllergy;

    private final String patientCustomizedOther;

    private final Boolean disabled;

    public AppointmentDTO(Long patientId, String patientName, String patientDisplayName, LocalDate birth,
            String nationalId, Gender gender, String phone, Boolean vipPatient, Instant expectedArrivalTime, ExtendUser doctor,
            Integer requiredTreatmentTime, String note, Boolean microscope, Boolean baseFloor, AppointmentStatus status,
            Instant registerArrivalTime, Long id, Boolean newPatient, RegistrationStatus registrationStatus,
            Instant patientLastModifiedDate, String patientLastModifiedBy, String patientMedicalId, Integer colorId,
            Boolean firstVisit, String patientCustomizedDisease, String patientCustomizedBloodDisease, String patientCustomizedAllergy, String patientCustomizedOther,
            Boolean disabled
    ) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.patientDisplayName = patientDisplayName;
        this.disabled = disabled;
        this.birth = birth;
        this.nationalId = nationalId;
        this.phone = phone;
        this.vipPatient = vipPatient;
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
        this.patientMedicalId = patientMedicalId;
        this.colorId = colorId;
        this.firstVisit = firstVisit;
        this.patientCustomizedDisease = patientCustomizedDisease;
        this.patientCustomizedBloodDisease = patientCustomizedBloodDisease;
        this.patientCustomizedAllergy = patientCustomizedAllergy;
        this.patientCustomizedOther = patientCustomizedOther;
    }

    public String getPatientCustomizedDisease() {
        return patientCustomizedDisease;
    }

    public String getPatientCustomizedBloodDisease() {
        return patientCustomizedBloodDisease;
    }

    public String getPatientCustomizedAllergy() {
        return patientCustomizedAllergy;
    }

    public String getPatientCustomizedOther() {
        return patientCustomizedOther;
    }

    public Boolean getDisabled() {
        return disabled;
    }

    public Boolean getMicroscope() {
        return microscope;
    }

    public Boolean getBaseFloor() {
        return baseFloor;
    }

    public Boolean getNewPatient() {
        return newPatient;
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

    public Boolean getVipPatient() {
        return vipPatient;
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

    public String getPatientMedicalId() {
        return patientMedicalId;
    }

    public Gender getGender() {
        return gender;
    }

    public Integer getColorId() {
        return colorId;
    }

    public String getPatientDisplayName() {
        return patientDisplayName;
    }

    public Boolean getFirstVisit() {
        return firstVisit;
    }
}
