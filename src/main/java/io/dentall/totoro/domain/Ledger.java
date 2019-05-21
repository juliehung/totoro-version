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

/**
 * A Ledger.
 */
@Entity
@Table(name = "ledger")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Ledger extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "amount", nullable = false)
    private Double amount;

    @NotNull
    @Column(name = "charge", nullable = false)
    private Double charge;

    @NotNull
    @Column(name = "arrears", nullable = false)
    private Double arrears;

    @Column(name = "note")
    private String note;

    @Column(name = "doctor")
    private String doctor;

    @ManyToOne
    private TreatmentPlan treatmentPlan;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public Ledger amount(Double amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getCharge() {
        return charge;
    }

    public Ledger charge(Double charge) {
        this.charge = charge;
        return this;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public Double getArrears() {
        return arrears;
    }

    public Ledger arrears(Double arrears) {
        this.arrears = arrears;
        return this;
    }

    public void setArrears(Double arrears) {
        this.arrears = arrears;
    }

    public String getNote() {
        return note;
    }

    public Ledger note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDoctor() {
        return doctor;
    }

    public Ledger doctor(String doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public TreatmentPlan getTreatmentPlan() {
        return treatmentPlan;
    }

    public Ledger treatmentPlan(TreatmentPlan treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
        return this;
    }

    public void setTreatmentPlan(TreatmentPlan treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
        Ledger ledger = (Ledger) o;
        if (ledger.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), ledger.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Ledger{" +
            "id=" + getId() +
            ", amount=" + getAmount() +
            ", charge=" + getCharge() +
            ", arrears=" + getArrears() +
            ", note='" + getNote() + "'" +
            ", doctor='" + getDoctor() + "'" +
            "}";
    }
}
