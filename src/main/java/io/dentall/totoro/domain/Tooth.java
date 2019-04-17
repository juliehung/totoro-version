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

    @Column(name = "surface")
    private String surface;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JsonIgnoreProperties(value = "teeth", allowSetters = true)
    private TreatmentProcedure treatmentProcedure;

    @ManyToOne
    @JsonIgnoreProperties(value = "teeth", allowSetters = true)
    private Disposal disposal;

    @ManyToOne
    @JsonIgnoreProperties(value = {"appointments", "treatments", "todos", "teeth"}, allowSetters = true)
    private Patient patient;

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

    public String getSurface() {
        return surface;
    }

    public Tooth surface(String surface) {
        this.surface = surface;
        return this;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getStatus() {
        return status;
    }

    public Tooth status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Disposal getDisposal() {
        return disposal;
    }

    public Tooth disposal(Disposal disposal) {
        this.disposal = disposal;
        return this;
    }

    public void setDisposal(Disposal disposal) {
        this.disposal = disposal;
    }

    public Patient getPatient() {
        return patient;
    }

    public Tooth patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
            ", surface='" + getSurface() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
