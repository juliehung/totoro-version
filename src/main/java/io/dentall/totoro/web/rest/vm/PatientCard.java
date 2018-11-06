package io.dentall.totoro.web.rest.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.Registration;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * View Model object for PatientCard.
 */
public class PatientCard implements Serializable {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Gender")
    private int gender;

    @JsonProperty("Pid")
    private String medicalId;

    @JsonProperty("Birthday")
    private LocalDate birthday;

    @JsonProperty("RegistrationTime")
    private ZonedDateTime expectedArrivalTime;

    @JsonProperty("ArrivalTime")
    private ZonedDateTime arrivalTime;

    @JsonProperty("ConsultType")
    private Integer registrationType;

//    @JsonProperty("MedicalOrder")
//    private String medicalOrder;

    @JsonProperty("Subject")
    private String subject;

    @JsonProperty("DominantDoc")
    private String dominantDoctor;

    @JsonProperty("NeedTime")
    private int requiredTreatmentTime;

    @JsonProperty("IsNewPatient")
    private boolean newPatient;

    @JsonProperty("ConsultationStatus")
    private Integer registrationStatus;

    @JsonProperty("FirstDoc")
    private String firstDoctor;

    @JsonProperty("Reminder")
    private String reminder;

    @JsonProperty("EmrLastModifyTime")
    private ZonedDateTime lastModifiedDate;

    @JsonProperty("IcWrittenTime")
    private ZonedDateTime writeIcTime;

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

    public ZonedDateTime getExpectedArrivalTime() {
        return expectedArrivalTime;
    }

    public void setExpectedArrivalTime(ZonedDateTime expectedArrivalTime) {
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

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        if (lastModifiedDate != null) {
            this.lastModifiedDate = lastModifiedDate.atZone(ZoneId.systemDefault());
        }
    }

    public ZonedDateTime getWriteIcTime() {
        return writeIcTime;
    }

    public void setWriteIcTime(ZonedDateTime writeIcTime) {
        this.writeIcTime = writeIcTime;
    }

    public Integer getRegistrationType() {
        return registrationType;
    }

    public Integer getRegistrationStatus() {
        return registrationStatus;
    }

    public ZonedDateTime getArrivalTime() {
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
