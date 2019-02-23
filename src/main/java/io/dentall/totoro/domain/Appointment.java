package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import io.dentall.totoro.domain.enumeration.AppointmentStatus;

/**
 * A Appointment.
 */
@Entity
@Table(name = "appointment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Appointment extends AbstractDoctorAndAuditingEntity<Appointment> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AppointmentStatus status;

    @Column(name = "subject")
    private String subject;

    @Column(name = "note")
    private String note;

    @Column(name = "expected_arrival_time")
    private Instant expectedArrivalTime;

    @Column(name = "required_treatment_time")
    private Integer requiredTreatmentTime;

    @Column(name = "microscope")
    private Boolean microscope;

    @Column(name = "new_patient")
    private Boolean newPatient;

    @Column(name = "base_floor")
    private Boolean baseFloor;

    @Column(name = "color_id")
    private Integer colorId;

    @Column(name = "archived")
    private Boolean archived;

    @ManyToOne
    @JsonIgnoreProperties("appointments")
    private Patient patient;

    @OneToOne    @JoinColumn(unique = true)
    private Registration registration;

    @OneToMany(mappedBy = "appointment", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TreatmentProcedure> treatmentProcedures = new HashSet<>();
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

    public String getNote() {
        return note;
    }

    public Appointment note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getExpectedArrivalTime() {
        return expectedArrivalTime;
    }

    public Appointment expectedArrivalTime(Instant expectedArrivalTime) {
        this.expectedArrivalTime = expectedArrivalTime;
        return this;
    }

    public void setExpectedArrivalTime(Instant expectedArrivalTime) {
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

    public Boolean isBaseFloor() {
        return baseFloor;
    }

    public Appointment baseFloor(Boolean baseFloor) {
        this.baseFloor = baseFloor;
        return this;
    }

    public void setBaseFloor(Boolean baseFloor) {
        this.baseFloor = baseFloor;
    }

    public Integer getColorId() {
        return colorId;
    }

    public Appointment colorId(Integer colorId) {
        this.colorId = colorId;
        return this;
    }

    public void setColorId(Integer colorId) {
        this.colorId = colorId;
    }

    public Boolean isArchived() {
        return archived;
    }

    public Appointment archived(Boolean archived) {
        this.archived = archived;
        return this;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
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

    public Set<TreatmentProcedure> getTreatmentProcedures() {
        return treatmentProcedures;
    }

    public Appointment treatmentProcedures(Set<TreatmentProcedure> treatmentProcedures) {
        this.treatmentProcedures = treatmentProcedures;
        return this;
    }

    public Appointment addTreatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedures.add(treatmentProcedure);
        treatmentProcedure.setAppointment(this);
        return this;
    }

    public Appointment removeTreatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedures.remove(treatmentProcedure);
        treatmentProcedure.setAppointment(null);
        return this;
    }

    public void setTreatmentProcedures(Set<TreatmentProcedure> treatmentProcedures) {
        this.treatmentProcedures = treatmentProcedures;
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
            ", note='" + getNote() + "'" +
            ", expectedArrivalTime='" + getExpectedArrivalTime() + "'" +
            ", requiredTreatmentTime=" + getRequiredTreatmentTime() +
            ", microscope='" + isMicroscope() + "'" +
            ", newPatient='" + isNewPatient() + "'" +
            ", baseFloor='" + isBaseFloor() + "'" +
            ", colorId=" + getColorId() +
            ", archived='" + isArchived() + "'" +
            "}";
    }
}
