package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * A NhiExtendPatient.
 */
@Entity
@Table(name = "nhi_extend_patient")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class NhiExtendPatient extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "card_annotation")
    private String cardAnnotation;

    @Column(name = "card_valid_date")
    private String cardValidDate;

    @Column(name = "card_issue_date")
    private String cardIssueDate;

    @Column(name = "nhi_identity")
    private String nhiIdentity;

    @Column(name = "available_times")
    private Integer availableTimes;

    @Column(name = "scaling")
    private String scaling;

    @Column(name = "fluoride")
    private String fluoride;

    @Column(name = "perio")
    private String perio;

    @Column(name = "lifetime", columnDefinition = "jsonb")
    @Type(type = "jsonb")
    private Map<String, Object> lifetime;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JsonProperty(access = WRITE_ONLY)
    private Patient patient;

    @OneToMany(mappedBy = "nhiExtendPatient", fetch = FetchType.EAGER)
    private Set<NhiMedicalRecord> nhiMedicalRecords = null;

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

    public String getCardValidDate() {
        return cardValidDate;
    }

    public NhiExtendPatient cardValidDate(String cardValidDate) {
        this.cardValidDate = cardValidDate;
        return this;
    }

    public void setCardValidDate(String cardValidDate) {
        this.cardValidDate = cardValidDate;
    }

    public String getCardIssueDate() {
        return cardIssueDate;
    }

    public NhiExtendPatient cardIssueDate(String cardIssueDate) {
        this.cardIssueDate = cardIssueDate;
        return this;
    }

    public void setCardIssueDate(String cardIssueDate) {
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

    public String getScaling() {
        return scaling;
    }

    public NhiExtendPatient scaling(String scaling) {
        this.scaling = scaling;
        return this;
    }

    public void setScaling(String scaling) {
        this.scaling = scaling;
    }

    public String getFluoride() {
        return fluoride;
    }

    public NhiExtendPatient fluoride(String fluoride) {
        this.fluoride = fluoride;
        return this;
    }

    public void setFluoride(String fluoride) {
        this.fluoride = fluoride;
    }

    public String getPerio() {
        return perio;
    }

    public NhiExtendPatient perio(String perio) {
        this.perio = perio;
        return this;
    }

    public void setPerio(String perio) {
        this.perio = perio;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @ApiModelProperty(hidden = true)
    public Set<NhiMedicalRecord> getNhiMedicalRecords() {
        return nhiMedicalRecords;
    }

    public NhiExtendPatient nhiMedicalRecords(Set<NhiMedicalRecord> nhiMedicalRecords) {
        this.nhiMedicalRecords = nhiMedicalRecords;
        return this;
    }

    public NhiExtendPatient addNhiMedicalRecord(NhiMedicalRecord nhiMedicalRecord) {
        this.nhiMedicalRecords.add(nhiMedicalRecord);
        nhiMedicalRecord.setNhiExtendPatient(this);
        return this;
    }

    public NhiExtendPatient removeNhiMedicalRecord(NhiMedicalRecord nhiMedicalRecord) {
        this.nhiMedicalRecords.remove(nhiMedicalRecord);
        nhiMedicalRecord.setNhiExtendPatient(null);
        return this;
    }

    public void setNhiMedicalRecords(Set<NhiMedicalRecord> nhiMedicalRecords) {
        this.nhiMedicalRecords = nhiMedicalRecords;
    }

    public Map<String, Object> getLifetime() {
        return lifetime;
    }

    public NhiExtendPatient lifetime(Map<String, Object> lifetime) {
        this.lifetime = lifetime;
        return this;
    }

    public void setLifetime(Map<String, Object> lifetime) {
        this.lifetime = lifetime;
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
            ", scaling='" + getScaling() + "'" +
            ", fluoride='" + getFluoride() + "'" +
            ", perio='" + getPerio() + "'" +
            "}";
    }
}
