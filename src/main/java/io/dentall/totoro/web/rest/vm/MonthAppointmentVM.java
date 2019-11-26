package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.enumeration.AppointmentStatus;
import io.dentall.totoro.domain.enumeration.Gender;
import io.dentall.totoro.domain.enumeration.RegistrationStatus;
import io.dentall.totoro.service.dto.AppointmentDTO;

import java.time.Instant;
import java.time.LocalDate;

public class MonthAppointmentVM {

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

    private final Instant lastModifiedDate;

    private final String lastModifiedBy;

    public MonthAppointmentVM(AppointmentDTO appointmentDTO) {
        this.patientId = appointmentDTO.getPatientId();
        this.patientName = appointmentDTO.getPatientName();
        this.birth = appointmentDTO.getBirth();
        this.nationalId = appointmentDTO.getNationalId();
        this.phone = appointmentDTO.getPhone();
        this.expectedArrivalTime = appointmentDTO.getExpectedArrivalTime();
        this.doctor = appointmentDTO.getDoctor();
        this.requiredTreatmentTime = appointmentDTO.getRequiredTreatmentTime();
        this.note = appointmentDTO.getNote();
        this.microscope = appointmentDTO.isMicroscope();
        this.baseFloor = appointmentDTO.isBaseFloor();
        this.gender = appointmentDTO.getGender();
        this.status = appointmentDTO.getStatus();
        this.registerArrivalTime = appointmentDTO.getRegisterArrivalTime();
        this.id = appointmentDTO.getId();
        this.newPatient = appointmentDTO.isNewPatient();
        this.registrationStatus = appointmentDTO.getRegistrationStatus();
        this.lastModifiedDate = appointmentDTO.getLastModifiedDate();
        this.lastModifiedBy = appointmentDTO.getLastModifiedBy();
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public Instant getRegisterArrivalTime() {
        return registerArrivalTime;
    }

    public Gender getGender() {
        return gender;
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

    public Long getId() {
        return id;
    }

    public boolean isNewPatient() {
        return newPatient;
    }

    public RegistrationStatus getRegistrationStatus() {
        return registrationStatus;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }
}
