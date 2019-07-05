package io.dentall.totoro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.SourceType;

/**
 * A Esign.
 */
@Entity
@Table(name = "esign")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
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

    @Column(name = "create_time")
    private Instant createTime;

    @Column(name = "update_time")
    private Instant updateTime;

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

    public Instant getCreateTime() {
        return createTime;
    }

    public Esign createTime(Instant createTime) {
        this.createTime = createTime;
        return this;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getUpdateTime() {
        return updateTime;
    }

    public Esign updateTime(Instant updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
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
            ", createTime='" + getCreateTime() + "'" +
            ", updateTime='" + getUpdateTime() + "'" +
            ", sourceType='" + getSourceType() + "'" +
            "}";
    }
}
