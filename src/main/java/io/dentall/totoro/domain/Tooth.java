package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Tooth.
 */
@Entity
@Table(name = "tooth")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Tooth extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "position", nullable = false)
    private String position;

    @Column(name = "jhi_before")
    private String before;

    @Column(name = "planned")
    private String planned;

    @Column(name = "jhi_after")
    private String after;

    @ManyToOne
    @JsonIgnoreProperties("teeth")
    private TreatmentProcedure treatmentProcedure;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public Tooth position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getBefore() {
        return before;
    }

    public Tooth before(String before) {
        this.before = before;
        return this;
    }

    public void setBefore(String before) {
        this.before = before;
    }

    public String getPlanned() {
        return planned;
    }

    public Tooth planned(String planned) {
        this.planned = planned;
        return this;
    }

    public void setPlanned(String planned) {
        this.planned = planned;
    }

    public String getAfter() {
        return after;
    }

    public Tooth after(String after) {
        this.after = after;
        return this;
    }

    public void setAfter(String after) {
        this.after = after;
    }

    public TreatmentProcedure getTreatmentProcedure() {
        return treatmentProcedure;
    }

    public Tooth treatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedure = treatmentProcedure;
        return this;
    }

    public void setTreatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedure = treatmentProcedure;
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
        Tooth tooth = (Tooth) o;
        if (tooth.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tooth.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Tooth{" +
            "id=" + getId() +
            ", position='" + getPosition() + "'" +
            ", before='" + getBefore() + "'" +
            ", planned='" + getPlanned() + "'" +
            ", after='" + getAfter() + "'" +
            "}";
    }
}
