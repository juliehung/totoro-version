package io.dentall.totoro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.Objects;

/**
 * A NhiDayUpload.
 */
@Entity
@Table(name = "nhi_day_upload")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NhiDayUpload implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private LocalDate date;

    @OneToMany(mappedBy = "nhiDayUpload", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NhiDayUploadDetails> nhiDayUploadDetails = null;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public NhiDayUpload date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<NhiDayUploadDetails> getNhiDayUploadDetails() {
        return nhiDayUploadDetails;
    }

    public NhiDayUpload nhiDayUploadDetails(Set<NhiDayUploadDetails> nhiDayUploadDetails) {
        this.nhiDayUploadDetails = nhiDayUploadDetails;
        return this;
    }

    public void setNhiDayUploadDetails(Set<NhiDayUploadDetails> nhiDayUploadDetails) {
        this.nhiDayUploadDetails = nhiDayUploadDetails;
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
        NhiDayUpload nhiDayUpload = (NhiDayUpload) o;
        if (nhiDayUpload.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhiDayUpload.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhiDayUpload{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
