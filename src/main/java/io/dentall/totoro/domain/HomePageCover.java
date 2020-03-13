package io.dentall.totoro.domain;


import io.dentall.totoro.domain.enumeration.HomePageCoverSourceTable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A HomePageCover.
 */
@Entity
@Table(name = "home_page_cover")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class HomePageCover implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "patient_id")
    private Long patientId;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_table")
    private HomePageCoverSourceTable sourceTable;

    @Column(name = "source_id")
    private Long sourceId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getPatientId() {
        return patientId;
    }

    public HomePageCover patientId(Long patientId) {
        this.patientId = patientId;
        return this;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public HomePageCover sourceTable(HomePageCoverSourceTable sourceTable) {
        this.sourceTable = sourceTable;
        return this;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public HomePageCoverSourceTable getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(HomePageCoverSourceTable sourceTable) {
        this.sourceTable = sourceTable;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public HomePageCover sourceId(Long sourceId) {
        this.sourceId = sourceId;
        return this;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
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
        HomePageCover homePageCover = (HomePageCover) o;
        if (homePageCover.getPatientId() == null || getPatientId() == null) {
            return false;
        }
        return Objects.equals(getPatientId(), homePageCover.getPatientId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPatientId());
    }

    @Override
    public String toString() {
        return "HomePageCover{" +
            "id=" + getPatientId() +
            ", patientId=" + getPatientId() +
            ", sourceTable='" + getSourceTable() + "'" +
            ", sourceId=" + getSourceId() +
            "}";
    }
}
