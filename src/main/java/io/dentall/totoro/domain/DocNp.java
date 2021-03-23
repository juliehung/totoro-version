package io.dentall.totoro.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Map;
import java.util.Objects;

/**
 * A DocNp.
 */
@Entity
@Table(name = "doc_np")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@EntityListeners(AuditingEntityListener.class)
public class DocNp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Type(type = "jsonb")
    @Column(name = "patient")
    private Map<String, Object> patient;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "esign_id")
    private Long esignId;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, Object> getPatient() {
        return patient;
    }

    public DocNp patient(Map<String, Object> patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Map<String, Object> patient) {
        this.patient = patient;
    }

    public Long getPatientId() {
        return patientId;
    }

    public DocNp patientId(Long patientId) {
        this.patientId = patientId;
        return this;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public Long getEsignId() {
        return esignId;
    }

    public DocNp esignId(Long esignId) {
        this.esignId = esignId;
        return this;
    }

    public void setEsignId(Long esignId) {
        this.esignId = esignId;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public DocNp createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public DocNp createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
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
        DocNp docNp = (DocNp) o;
        if (docNp.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), docNp.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DocNp{" +
            "id=" + getId() +
            ", patient='" + getPatient() + "'" +
            ", patientId=" + getPatientId() +
            ", esignId=" + getEsignId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }

}
