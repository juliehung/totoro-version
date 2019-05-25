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
import java.util.Set;

import io.dentall.totoro.domain.enumeration.NhiDayUploadDetailType;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * A NhiDayUploadDetails.
 */
@Entity
@Table(name = "nhi_day_upload_details")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NhiDayUploadDetails extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private NhiDayUploadDetailType type;

    @ManyToOne
    @JsonProperty(access = WRITE_ONLY)
    private NhiDayUpload nhiDayUpload;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "nhi_day_upload_details_nhi_extend_disposal",
        joinColumns = @JoinColumn(name = "nhi_day_upload_details_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "nhi_extend_disposals_id", referencedColumnName = "id"))
    private Set<NhiExtendDisposal> nhiExtendDisposals = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NhiDayUploadDetailType getType() {
        return type;
    }

    public NhiDayUploadDetails type(NhiDayUploadDetailType type) {
        this.type = type;
        return this;
    }

    public void setType(NhiDayUploadDetailType type) {
        this.type = type;
    }

    public NhiDayUpload getNhiDayUpload() {
        return nhiDayUpload;
    }

    public NhiDayUploadDetails nhiDayUpload(NhiDayUpload nhiDayUpload) {
        this.nhiDayUpload = nhiDayUpload;
        return this;
    }

    public void setNhiDayUpload(NhiDayUpload nhiDayUpload) {
        this.nhiDayUpload = nhiDayUpload;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Set<NhiExtendDisposal> getNhiExtendDisposals() {
        return nhiExtendDisposals;
    }

    public NhiDayUploadDetails nhiExtendDisposals(Set<NhiExtendDisposal> nhiExtendDisposals) {
        this.nhiExtendDisposals = nhiExtendDisposals;
        return this;
    }

    public void setNhiExtendDisposals(Set<NhiExtendDisposal> nhiExtendDisposals) {
        this.nhiExtendDisposals = nhiExtendDisposals;
    }

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
    @JsonIgnore(false)
    @JsonProperty
    public String getLastModifiedBy() {
        return super.getLastModifiedBy();
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
        NhiDayUploadDetails nhiDayUploadDetails = (NhiDayUploadDetails) o;
        if (nhiDayUploadDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhiDayUploadDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhiDayUploadDetails{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
