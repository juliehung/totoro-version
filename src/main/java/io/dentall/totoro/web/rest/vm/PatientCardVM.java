package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.Registration;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;

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

    private Boolean newPatient;

    private Integer registrationStatus;

    private String firstDoctor;

    private String reminder;

    private String note;

    private Instant lastModifiedDate;

    private Instant writeIcTime;

    private String lineId;

    private String fbId;

    private Boolean baseFloor;

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

    public Boolean isNewPatient() {
        return newPatient;
    }

    public void setNewPatient(Boolean newPatient) {
        this.newPatient = newPatient;
    }

    public String getFirstDoctor() {
        return firstDoctor;
    }

    public void setFirstDoctor(ExtendUser firstDoctor) {
        if (firstDoctor != null) {
            this.firstDoctor = firstDoctor.getUser().getLogin();
        }
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
            this.registrationType = null;
            this.registrationStatus = null;
            this.arrivalTime = null;
        } else {
            if (registration.getType() == null) {
                this.registrationType = null;
            } else {
                this.registrationType = registration.getType().getValue();
            }

            if (registration.getStatus() == null) {
                this.registrationStatus = null;
            } else {
                this.registrationStatus = registration.getStatus().getValue();
            }

            this.arrivalTime = registration.getArrivalTime();
        }
    }
}
