package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.RegistrationStatus;

import io.dentall.totoro.domain.enumeration.RegistrationType;

/**
 * A Registration.
 */
@Entity
@Table(name = "registration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private RegistrationStatus status;

    @Column(name = "arrival_time")
    private Instant arrivalTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private RegistrationType type;

    @Column(name = "on_site")
    private Boolean onSite;

    @OneToOne(mappedBy = "registration")
    @JsonIgnore
    private Appointment appointment;

    @OneToOne    @JoinColumn(unique = true)
    private Accounting accounting;

    @OneToMany(mappedBy = "registration")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TreatmentProcedure> treatmentProcedures = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RegistrationStatus getStatus() {
        return status;
    }

    public Registration status(RegistrationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public Registration arrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    public void setArrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public RegistrationType getType() {
        return type;
    }

    public Registration type(RegistrationType type) {
        this.type = type;
        return this;
    }

    public void setType(RegistrationType type) {
        this.type = type;
    }

    public Boolean isOnSite() {
        return onSite;
    }

    public Registration onSite(Boolean onSite) {
        this.onSite = onSite;
        return this;
    }

    public void setOnSite(Boolean onSite) {
        this.onSite = onSite;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public Registration appointment(Appointment appointment) {
        this.appointment = appointment;
        return this;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Accounting getAccounting() {
        return accounting;
    }

    public Registration accounting(Accounting accounting) {
        this.accounting = accounting;
        return this;
    }

    public void setAccounting(Accounting accounting) {
        this.accounting = accounting;
    }

    public Set<TreatmentProcedure> getTreatmentProcedures() {
        return treatmentProcedures;
    }

    public Registration treatmentProcedures(Set<TreatmentProcedure> treatmentProcedures) {
        this.treatmentProcedures = treatmentProcedures;
        return this;
    }

    public Registration addTreatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedures.add(treatmentProcedure);
        treatmentProcedure.setRegistration(this);
        return this;
    }

    public Registration removeTreatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedures.remove(treatmentProcedure);
        treatmentProcedure.setRegistration(null);
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
        Registration registration = (Registration) o;
        if (registration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), registration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Registration{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", arrivalTime='" + getArrivalTime() + "'" +
            ", type='" + getType() + "'" +
            ", onSite='" + isOnSite() + "'" +
            "}";
    }
}
