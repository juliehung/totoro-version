package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.Tag;
import io.dentall.totoro.domain.enumeration.AppointmentStatus;
import io.dentall.totoro.domain.enumeration.Gender;
import io.dentall.totoro.domain.enumeration.RegistrationStatus;
import io.dentall.totoro.service.dto.AppointmentDTO;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public class MonthAppointmentVM {

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

    private final Boolean patientDisabled;

    private final Gender gender;

    private final AppointmentStatus status;

    private final Instant registerArrivalTime;

    private final Long id;

    private final Boolean newPatient;

    private final RegistrationStatus registrationStatus;

    private final Instant patientLastModifiedDate;

    private final String patientLastModifiedBy;

    private final String medicalId;

    private final Integer colorId;

    private Set<Tag> tags;

    private final Boolean firstVisit;

    public MonthAppointmentVM(AppointmentDTO appointmentDTO) {
        this.patientId = appointmentDTO.getPatientId();
        this.patientName = appointmentDTO.getPatientName();
        this.patientDisplayName = appointmentDTO.getPatientDisplayName();
        this.patientDisabled = appointmentDTO.getPatientDisabled();
        this.birth = appointmentDTO.getBirth();
        this.nationalId = appointmentDTO.getNationalId();
        this.phone = appointmentDTO.getPhone();
        this.vipPatient = appointmentDTO.getVipPatient();
        this.expectedArrivalTime = appointmentDTO.getExpectedArrivalTime();
        this.doctor = appointmentDTO.getDoctor();
        this.requiredTreatmentTime = appointmentDTO.getRequiredTreatmentTime();
        this.note = appointmentDTO.getNote();
        this.microscope = appointmentDTO.getMicroscope();
        this.baseFloor = appointmentDTO.getBaseFloor();
        this.gender = appointmentDTO.getGender();
        this.status = appointmentDTO.getStatus();
        this.registerArrivalTime = appointmentDTO.getRegisterArrivalTime();
        this.id = appointmentDTO.getId();
        this.newPatient = appointmentDTO.getNewPatient();
        this.registrationStatus = appointmentDTO.getRegistrationStatus();
        this.patientLastModifiedDate = appointmentDTO.getPatientLastModifiedDate();
        this.patientLastModifiedBy = appointmentDTO.getPatientLastModifiedBy();
        this.medicalId = appointmentDTO.getPatientMedicalId();
        this.colorId = appointmentDTO.getColorId();
        this.firstVisit = appointmentDTO.getFirstVisit();
    }

    public Integer getColorId() {
        return colorId;
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

    public Boolean getMicroscope() {
        return microscope;
    }

    public Boolean getBaseFloor() {
        return baseFloor;
    }

    public Gender getGender() {
        return gender;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public Instant getRegisterArrivalTime() {
        return registerArrivalTime;
    }

    public Long getId() {
        return id;
    }

    public Boolean getNewPatient() {
        return newPatient;
    }

    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public Instant getPatientLastModifiedDate() {
        return patientLastModifiedDate;
    }

    public String getPatientLastModifiedBy() {
        return patientLastModifiedBy;
    }

    public String getMedicalId() {
        return medicalId;
    }

    public String getPatientDisplayName() {
        return patientDisplayName;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Boolean getFirstVisit() {
        return firstVisit;
    }
}
