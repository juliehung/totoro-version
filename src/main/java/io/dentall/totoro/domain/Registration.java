package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.enumeration.RegistrationStatus;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Registration.
 */
@Entity
@Table(name = "registration")
public class Registration extends AbstractAuditingEntity implements Serializable {

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

    @OneToOne(mappedBy = "registration", fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = {"registration", "treatmentProcedures"}, allowSetters = true)
    private Appointment appointment;

    @OneToOne
    @JoinColumn(unique = true)
    private Accounting accounting;

    @OneToOne(mappedBy = "registration")
    @JsonIgnoreProperties(value = "registration", allowSetters = true)
    private Disposal disposal;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @Column(name = "abnormal_code")
    private String abnormalCode;

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

    public String getType() {
        return type;
    }

    public Registration type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
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

    public Boolean isNoCard() {
        return noCard;
    }

    public Registration noCard(Boolean noCard) {
        this.noCard = noCard;
        return this;
    }

    public void setNoCard(Boolean noCard) {
        this.noCard = noCard;
    }

    @ApiModelProperty(hidden = true)
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

    @ApiModelProperty(hidden = true)
    public Disposal getDisposal() {
        return disposal;
    }

    public Registration disposal(Disposal disposal) {
        this.disposal = disposal;
        return this;
    }

    public void setDisposal(Disposal disposal) {
        this.disposal = disposal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public String getAbnormalCode() {
        return abnormalCode;
    }

    public Registration abnormalCode(String abnormalCode) {
        this.abnormalCode = abnormalCode;
        return this;
    }

    public void setAbnormalCode(String abnormalCode) {
        this.abnormalCode = abnormalCode;
    }

    @Override
    @JsonIgnore(false)
    @JsonProperty
    public Instant getLastModifiedDate() {
        return super.getLastModifiedDate();
    }

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
            ", noCard='" + isNoCard() + "'" +
            ", abnormalCode='" + getAbnormalCode() + "'" +
            "}";
    }
}
