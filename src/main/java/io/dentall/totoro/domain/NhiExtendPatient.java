package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * A NhiExtendPatient.
 */
@Entity
@Table(name = "nhi_extend_patient")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NhiExtendPatient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JsonProperty(access = WRITE_ONLY)
    private Patient patient;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_annotation")
    private String cardAnnotation;

    @Column(name = "card_valid_date")
    private LocalDate cardValidDate;

    @Column(name = "card_issue_date")
    private LocalDate cardIssueDate;

    @Column(name = "nhi_identity")
    private String nhiIdentity;

    @Column(name = "available_times")
    private Integer availableTimes;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public NhiExtendPatient patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public NhiExtendPatient cardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
        return this;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardAnnotation() {
        return cardAnnotation;
    }

    public NhiExtendPatient cardAnnotation(String cardAnnotation) {
        this.cardAnnotation = cardAnnotation;
        return this;
    }

    public void setCardAnnotation(String cardAnnotation) {
        this.cardAnnotation = cardAnnotation;
    }

    public LocalDate getCardValidDate() {
        return cardValidDate;
    }

    public NhiExtendPatient cardValidDate(LocalDate cardValidDate) {
        this.cardValidDate = cardValidDate;
        return this;
    }

    public void setCardValidDate(LocalDate cardValidDate) {
        this.cardValidDate = cardValidDate;
    }

    public LocalDate getCardIssueDate() {
        return cardIssueDate;
    }

    public NhiExtendPatient cardIssueDate(LocalDate cardIssueDate) {
        this.cardIssueDate = cardIssueDate;
        return this;
    }

    public void setCardIssueDate(LocalDate cardIssueDate) {
        this.cardIssueDate = cardIssueDate;
    }

    public String getNhiIdentity() {
        return nhiIdentity;
    }

    public NhiExtendPatient nhiIdentity(String nhiIdentity) {
        this.nhiIdentity = nhiIdentity;
        return this;
    }

    public void setNhiIdentity(String nhiIdentity) {
        this.nhiIdentity = nhiIdentity;
    }

    public Integer getAvailableTimes() {
        return availableTimes;
    }

    public NhiExtendPatient availableTimes(Integer availableTimes) {
        this.availableTimes = availableTimes;
        return this;
    }

    public void setAvailableTimes(Integer availableTimes) {
        this.availableTimes = availableTimes;
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
        NhiExtendPatient nhiExtendPatient = (NhiExtendPatient) o;
        if (nhiExtendPatient.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhiExtendPatient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhiExtendPatient{" +
            "id=" + getId() +
            ", cardNumber='" + getCardNumber() + "'" +
            ", cardAnnotation='" + getCardAnnotation() + "'" +
            ", cardValidDate='" + getCardValidDate() + "'" +
            ", cardIssueDate='" + getCardIssueDate() + "'" +
            ", nhiIdentity='" + getNhiIdentity() + "'" +
            ", availableTimes=" + getAvailableTimes() +
            "}";
    }
}
