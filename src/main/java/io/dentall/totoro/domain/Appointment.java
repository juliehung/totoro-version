package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.AppointmentStatus;

/**
 * A Appointment.
 */
@Entity
@Table(name = "appointment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private AppointmentStatus status;

    @Column(name = "subject")
    private String subject;

    @Column(name = "expected_arrival_time")
    private ZonedDateTime expectedArrivalTime;

    @Column(name = "required_treatment_time")
    private Integer requiredTreatmentTime;

    @Column(name = "pregnancy")
    private Boolean pregnancy;

    @Column(name = "microscope")
    private Boolean microscope;

    @Column(name = "new_patient")
    private Boolean newPatient;

    @ManyToOne
    @JsonIgnoreProperties("appointments")
    private Patient patient;

    @OneToOne    @JoinColumn(unique = true)
    private Registration registration;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public Appointment status(AppointmentStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public Appointment subject(String subject) {
        this.subject = subject;
        return this;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public ZonedDateTime getExpectedArrivalTime() {
        return expectedArrivalTime;
    }

    public Appointment expectedArrivalTime(ZonedDateTime expectedArrivalTime) {
        this.expectedArrivalTime = expectedArrivalTime;
        return this;
    }

    public void setExpectedArrivalTime(ZonedDateTime expectedArrivalTime) {
        this.expectedArrivalTime = expectedArrivalTime;
    }

    public Integer getRequiredTreatmentTime() {
        return requiredTreatmentTime;
    }

    public Appointment requiredTreatmentTime(Integer requiredTreatmentTime) {
        this.requiredTreatmentTime = requiredTreatmentTime;
        return this;
    }

    public void setRequiredTreatmentTime(Integer requiredTreatmentTime) {
        this.requiredTreatmentTime = requiredTreatmentTime;
    }

    public Boolean isPregnancy() {
        return pregnancy;
    }

    public Appointment pregnancy(Boolean pregnancy) {
        this.pregnancy = pregnancy;
        return this;
    }

    public void setPregnancy(Boolean pregnancy) {
        this.pregnancy = pregnancy;
    }

    public Boolean isMicroscope() {
        return microscope;
    }

    public Appointment microscope(Boolean microscope) {
        this.microscope = microscope;
        return this;
    }

    public void setMicroscope(Boolean microscope) {
        this.microscope = microscope;
    }

    public Boolean isNewPatient() {
        return newPatient;
    }

    public Appointment newPatient(Boolean newPatient) {
        this.newPatient = newPatient;
        return this;
    }

    public void setNewPatient(Boolean newPatient) {
        this.newPatient = newPatient;
    }

    public Patient getPatient() {
        return patient;
    }

    public Appointment patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Registration getRegistration() {
        return registration;
    }

    public Appointment registration(Registration registration) {
        this.registration = registration;
        return this;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Appointment appointment = (Appointment) o;
        if (appointment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), appointment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Appointment{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", subject='" + getSubject() + "'" +
            ", expectedArrivalTime='" + getExpectedArrivalTime() + "'" +
            ", requiredTreatmentTime=" + getRequiredTreatmentTime() +
            ", pregnancy='" + isPregnancy() + "'" +
            ", microscope='" + isMicroscope() + "'" +
            ", newPatient='" + isNewPatient() + "'" +
            "}";
    }
}
