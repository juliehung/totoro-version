package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
public class Ledger implements Serializable {

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

    @Size(max = 5100)
    @Column(name = "note", length = 5100)
    private String note;

    @Column(name = "doctor")
    private String doctor;

    @Column(name = "gid")
    private Long gid;

    @Column(name = "display_name")
    private String displayName;

    @Column(name = "project_code")
    private String projectCode;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "jhi_date")
    private Instant date;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "include_stamp_tax")
    private Boolean includeStampTax;

    @Column(name = "print_time")
    private Instant printTime;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedDate
    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @LastModifiedBy
    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @ManyToOne
    private TreatmentPlan treatmentPlan;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Ledger includeStampTax(Boolean includeStampTax) {
        this.includeStampTax = includeStampTax;
        return this;
    }

    public Ledger printTime(Instant printTime) {
        this.printTime = printTime;
        return this;
    }

    public Instant getPrintTime() {
        return printTime;
    }

    public void setPrintTime(Instant printTime) {
        this.printTime = printTime;
    }

    public Ledger projectCode(String projectCode) {
        this.projectCode = projectCode;
        return this;
    }

    public Boolean getIncludeStampTax() {
        return includeStampTax;
    }

    public void setIncludeStampTax(Boolean includeStampTax) {
        this.includeStampTax = includeStampTax;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
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

    public Long getGid() {
        return gid;
    }

    public Ledger gid(Long gid) {
        this.gid = gid;
        return this;
    }

    public void setGid(Long gid) {
        this.gid = gid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Ledger displayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Ledger createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Ledger createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Ledger lastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
        return this;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public Ledger lastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
        return this;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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
            ", gid=" + getGid() +
            ", displayName='" + getDisplayName() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
