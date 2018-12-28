package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.AppointmentStatus;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

/**
 * View Model object for PatientCardVM.
 */
public class PatientCardVM implements Serializable {

    private String name;

    private int gender;

    private String medicalId;

    private LocalDate birthday;

    private Instant expectedArrivalTime;

    private Instant arrivalTime;

    private Integer registrationType;

//    @JsonProperty("MedicalOrder")
//    private String medicalOrder;

    private String subject;

    private String dominantDoctor;

    private int requiredTreatmentTime;

    private boolean newPatient = false;

    private Integer registrationStatus;

    private String firstDoctor;

    private String patientNote;

    private String patientClinicNote;

    private String appointmentNote;

    private Instant lastModifiedDate;

    private Instant writeIcTime;

    private String lineId;

    private String fbId;

    private Boolean baseFloor;

    private Boolean microscope;

    private Set<Tag> tags = new HashSet<>();

    private Integer colorId;

    private User doctor;

    private String createdBy;

    private Instant createdDate;

    private AvatarVM avatar;

    private boolean alreadyRegistered = false;

    private boolean isCancel = false;

    private boolean notArrived = false;

    public PatientCardVM(Patient patient, Appointment appointment, Registration registration) {
        // patient
        setName(patient.getName());
        setGender(patient.getGender().getValue());
        setMedicalId(patient.getMedicalId());
        setBirthday(patient.getBirth());
        setDominantDoctor(patient.getDominantDoctor());
        setFirstDoctor(patient.getFirstDoctor());
        setPatientNote(patient.getNote());
        setPatientClinicNote(patient.getClinicNote());
        setLastModifiedDate(patient.getLastModifiedDate());
        setWriteIcTime(patient.getWriteIcTime());
        setLineId(patient.getLineId());
        setFbId(patient.getFbId());
        setTags(patient.getTags());
        if (patient.getAvatar() != null) {
            setAvatar(new AvatarVM(patient));
        }

        // appointment
        setExpectedArrivalTime(appointment.getExpectedArrivalTime());
        setSubject(appointment.getSubject());
        setAppointmentNote(appointment.getNote());
        setRequiredTreatmentTime(appointment.getRequiredTreatmentTime());
        setNewPatient(appointment.isNewPatient());
        setBaseFloor(appointment.isBaseFloor());
        setMicroscope(appointment.isMicroscope());
        setColorId(appointment.getColorId());
        setAppointmentStatus(appointment.getStatus());
        setDoctor(appointment.getDoctor().getUser());
        setCreatedBy(appointment.getCreatedBy());
        setCreatedDate(appointment.getCreatedDate());

        // registration
        setRegistration(registration);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getMedicalId() {
        return medicalId;
    }

    public void setMedicalId(String medicalId) {
        this.medicalId = medicalId;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Instant getExpectedArrivalTime() {
        return expectedArrivalTime;
    }

    public void setExpectedArrivalTime(Instant expectedArrivalTime) {
        this.expectedArrivalTime = expectedArrivalTime;
    }

//    public String getMedicalOrder() {
//        return medicalOrder;
//    }
//
//    public void setMedicalOrder(String medicalOrder) {
//        this.medicalOrder = medicalOrder;
//    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDominantDoctor() {
        return dominantDoctor;
    }

    public void setDominantDoctor(ExtendUser dominantDoctor) {
        if (dominantDoctor != null) {
            this.dominantDoctor = dominantDoctor.getUser().getLogin();
        }
    }

    public int getRequiredTreatmentTime() {
        return requiredTreatmentTime;
    }

    public void setRequiredTreatmentTime(int requiredTreatmentTime) {
        this.requiredTreatmentTime = requiredTreatmentTime;
    }

    public boolean isNewPatient() {
        return newPatient;
    }

    public void setNewPatient(Boolean newPatient) {
        if (newPatient != null && newPatient) {
            this.newPatient = newPatient;
        }
    }

    public String getFirstDoctor() {
        return firstDoctor;
    }

    public void setFirstDoctor(ExtendUser firstDoctor) {
        if (firstDoctor != null) {
            this.firstDoctor = firstDoctor.getUser().getLogin();
        }
    }

    public String getPatientNote() {
        return patientNote;
    }

    public void setPatientNote(String patientNote) {
        this.patientNote = patientNote;
    }

    public String getPatientClinicNote() {
        return patientClinicNote;
    }

    public void setPatientClinicNote(String patientClinicNote) {
        this.patientClinicNote = patientClinicNote;
    }

    public String getAppointmentNote() {
        return appointmentNote;
    }

    public void setAppointmentNote(String appointmentNote) {
        this.appointmentNote = appointmentNote;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        if (lastModifiedDate != null) {
            this.lastModifiedDate = lastModifiedDate;
        }
    }

    public Instant getWriteIcTime() {
        return writeIcTime;
    }

    public void setWriteIcTime(Instant writeIcTime) {
        this.writeIcTime = writeIcTime;
    }

    public String getLineId() {
        return lineId;
    }

    public void setLineId(String lineId) {
        this.lineId = lineId;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public Boolean isBaseFloor() {
        return baseFloor;
    }

    public void setBaseFloor(Boolean baseFloor) {
        this.baseFloor = baseFloor;
    }

    public Boolean isMicroscope() {
        return microscope;
    }

    public void setMicroscope(Boolean microscope) {
        this.microscope = microscope;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Integer getColorId() {
        return colorId;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public AvatarVM getAvatar() {
        return avatar;
    }

    public void setAvatar(AvatarVM avatar) {
        this.avatar = avatar;
    }

    public Integer getRegistrationType() {
        return registrationType;
    }

    public Integer getRegistrationStatus() {
        return registrationStatus;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public void setRegistration(Registration registration) {
        if (registration == null) {
            registrationType = null;
            registrationStatus = null;
            arrivalTime = null;
            notArrived = true;
        } else {
            if (registration.getType() == null) {
                registrationType = null;
            } else {
                registrationType = registration.getType().getValue();
            }

            if (registration.getStatus() == null) {
                registrationStatus = null;
            } else {
                registrationStatus = registration.getStatus().getValue();
            }

            arrivalTime = registration.getArrivalTime();
            alreadyRegistered = true;
        }
    }

    public void setAppointmentStatus(AppointmentStatus status) {
        if (status == AppointmentStatus.CANCEL) {
            isCancel = true;
        }
    }
}
