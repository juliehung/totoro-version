package io.dentall.totoro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.SourceType;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * A Esign.
 */
@Entity
@Table(name = "esign")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
public class Esign implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "patient_id", nullable = false)
    private Long patientId;

    
    @Lob
    @Column(name = "jhi_lob")
    private byte[] lob;

    @Column(name = "jhi_lob_content_type")
    private String lobContentType;

    @CreatedDate
    @Column(name = "created_date")
    private Instant createdDate;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type")
    private SourceType sourceType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public Esign patientId(Long patientId) {
        this.patientId = patientId;
        return this;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public byte[] getLob() {
        return lob;
    }

    public Esign lob(byte[] lob) {
        this.lob = lob;
        return this;
    }

    public void setLob(byte[] lob) {
        this.lob = lob;
    }

    public String getLobContentType() {
        return lobContentType;
    }

    public Esign lobContentType(String lobContentType) {
        this.lobContentType = lobContentType;
        return this;
    }

    public void setLobContentType(String lobContentType) {
        this.lobContentType = lobContentType;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Esign createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Esign createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public Esign sourceType(SourceType sourceType) {
        this.sourceType = sourceType;
        return this;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
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
        Esign esign = (Esign) o;
        if (esign.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), esign.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Esign{" +
            "id=" + getId() +
            ", patientId=" + getPatientId() +
            ", lob='" + getLob() + "'" +
            ", lobContentType='" + getLobContentType() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", sourceType='" + getSourceType() + "'" +
            "}";
    }
}
