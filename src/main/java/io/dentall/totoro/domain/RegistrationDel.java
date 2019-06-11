package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.RegistrationStatus;

/**
 * A RegistrationDel.
 */
@Entity
@Table(name = "registration_del")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RegistrationDel extends AbstractAuditingEntity implements Serializable {

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

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "on_site")
    private Boolean onSite;

    @Column(name = "no_card")
    private Boolean noCard;

    @Column(name = "appointment_id")
    private Long appointmentId;

    @Column(name = "accounting_id")
    private Long accountingId;

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

    public RegistrationDel status(RegistrationStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(RegistrationStatus status) {
        this.status = status;
    }

    public Instant getArrivalTime() {
        return arrivalTime;
    }

    public RegistrationDel arrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
        return this;
    }

    public void setArrivalTime(Instant arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getType() {
        return type;
    }

    public RegistrationDel type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean isOnSite() {
        return onSite;
    }

    public RegistrationDel onSite(Boolean onSite) {
        this.onSite = onSite;
        return this;
    }

    public void setOnSite(Boolean onSite) {
        this.onSite = onSite;
    }

    public Boolean isNoCard() {
        return noCard;
    }

    public RegistrationDel noCard(Boolean noCard) {
        this.noCard = noCard;
        return this;
    }

    public void setNoCard(Boolean noCard) {
        this.noCard = noCard;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public RegistrationDel appointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
        return this;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getAccountingId() {
        return accountingId;
    }

    public RegistrationDel accountingId(Long accountingId) {
        this.accountingId = accountingId;
        return this;
    }

    public void setAccountingId(Long accountingId) {
        this.accountingId = accountingId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    @JsonIgnore(false)
    @JsonProperty
    public String getCreatedBy() {
        return super.getCreatedBy();
    }

    @Override
    @JsonIgnore(false)
    @JsonProperty
    public Instant getCreatedDate() {
        return super.getCreatedDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RegistrationDel registrationDel = (RegistrationDel) o;
        if (registrationDel.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), registrationDel.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RegistrationDel{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", arrivalTime='" + getArrivalTime() + "'" +
            ", type='" + getType() + "'" +
            ", onSite='" + isOnSite() + "'" +
            ", noCard='" + isNoCard() + "'" +
            ", appointmentId=" + getAppointmentId() +
            ", accountingId=" + getAccountingId() +
            "}";
    }
}
