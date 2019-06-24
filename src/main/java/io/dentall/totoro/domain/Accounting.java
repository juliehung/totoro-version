package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Accounting.
 */
@Entity
@Table(name = "accounting")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Accounting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "registration_fee", nullable = false)
    private Double registrationFee;

    @Column(name = "partial_burden")
    private Double partialBurden;

    @Column(name = "deposit")
    private Double deposit;

    @Column(name = "own_expense")
    private Double ownExpense;

    @Column(name = "other")
    private Double other;

    @Column(name = "patient_identity")
    private String patientIdentity;

    @Column(name = "discount_reason")
    private String discountReason;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "withdrawal")
    private Double withdrawal;

    @Column(name = "transaction_time")
    private Instant transactionTime;

    @Column(name = "staff")
    private String staff;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Hospital hospital;

    @OneToOne(mappedBy = "accounting")
    @JsonIgnore
    private Registration registration;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getRegistrationFee() {
        return registrationFee;
    }

    public Accounting registrationFee(Double registrationFee) {
        this.registrationFee = registrationFee;
        return this;
    }

    public void setRegistrationFee(Double registrationFee) {
        this.registrationFee = registrationFee;
    }

    public Double getPartialBurden() {
        return partialBurden;
    }

    public Accounting partialBurden(Double partialBurden) {
        this.partialBurden = partialBurden;
        return this;
    }

    public void setPartialBurden(Double partialBurden) {
        this.partialBurden = partialBurden;
    }

    public Double getDeposit() {
        return deposit;
    }

    public Accounting deposit(Double deposit) {
        this.deposit = deposit;
        return this;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public Double getOwnExpense() {
        return ownExpense;
    }

    public Accounting ownExpense(Double ownExpense) {
        this.ownExpense = ownExpense;
        return this;
    }

    public void setOwnExpense(Double ownExpense) {
        this.ownExpense = ownExpense;
    }

    public Double getOther() {
        return other;
    }

    public Accounting other(Double other) {
        this.other = other;
        return this;
    }

    public void setOther(Double other) {
        this.other = other;
    }

    public String getPatientIdentity() {
        return patientIdentity;
    }

    public Accounting patientIdentity(String patientIdentity) {
        this.patientIdentity = patientIdentity;
        return this;
    }

    public void setPatientIdentity(String patientIdentity) {
        this.patientIdentity = patientIdentity;
    }

    public String getDiscountReason() {
        return discountReason;
    }

    public Accounting discountReason(String discountReason) {
        this.discountReason = discountReason;
        return this;
    }

    public void setDiscountReason(String discountReason) {
        this.discountReason = discountReason;
    }

    public Double getDiscount() {
        return discount;
    }

    public Accounting discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getWithdrawal() {
        return withdrawal;
    }

    public Accounting withdrawal(Double withdrawal) {
        this.withdrawal = withdrawal;
        return this;
    }

    public void setWithdrawal(Double withdrawal) {
        this.withdrawal = withdrawal;
    }

    public Instant getTransactionTime() {
        return transactionTime;
    }

    public Accounting transactionTime(Instant transactionTime) {
        this.transactionTime = transactionTime;
        return this;
    }

    public void setTransactionTime(Instant transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getStaff() {
        return staff;
    }

    public Accounting staff(String staff) {
        this.staff = staff;
        return this;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public Accounting hospital(Hospital hospital) {
        this.hospital = hospital;
        return this;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Registration getRegistration() {
        return registration;
    }

    public Accounting registration(Registration registration) {
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
        Accounting accounting = (Accounting) o;
        if (accounting.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), accounting.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Accounting{" +
            "id=" + getId() +
            ", registrationFee=" + getRegistrationFee() +
            ", partialBurden=" + getPartialBurden() +
            ", deposit=" + getDeposit() +
            ", ownExpense=" + getOwnExpense() +
            ", other=" + getOther() +
            ", patientIdentity='" + getPatientIdentity() + "'" +
            ", discountReason='" + getDiscountReason() + "'" +
            ", discount=" + getDiscount() +
            ", withdrawal=" + getWithdrawal() +
            ", transactionTime='" + getTransactionTime() + "'" +
            ", staff='" + getStaff() + "'" +
            "}";
    }
}
