package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A TreatmentProcedure.
 */
@Entity
@Table(name = "treatment_procedure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TreatmentProcedure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "price")
    private Integer price;

    @Column(name = "teeth")
    private String teeth;

    @Column(name = "surfaces")
    private String surfaces;

    @Column(name = "nhi_declared")
    private Boolean nhiDeclared;

    @ManyToOne
    @JsonIgnoreProperties("")
    private NHIProcedure nhiProcedure;

    @ManyToOne
    @JsonIgnoreProperties("treatmentProcedures")
    private TreatmentTask treatmentTask;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @ManyToOne
    @JsonIgnoreProperties({"dominantPatients", "firstPatients", "appointments", "treatmentProcedures", "treatmentTasks"})
    private ExtendUser doctor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public TreatmentProcedure price(Integer price) {
        this.price = price;
        return this;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getTeeth() {
        return teeth;
    }

    public TreatmentProcedure teeth(String teeth) {
        this.teeth = teeth;
        return this;
    }

    public void setTeeth(String teeth) {
        this.teeth = teeth;
    }

    public String getSurfaces() {
        return surfaces;
    }

    public TreatmentProcedure surfaces(String surfaces) {
        this.surfaces = surfaces;
        return this;
    }

    public void setSurfaces(String surfaces) {
        this.surfaces = surfaces;
    }

    public Boolean isNhiDeclared() {
        return nhiDeclared;
    }

    public TreatmentProcedure nhiDeclared(Boolean nhiDeclared) {
        this.nhiDeclared = nhiDeclared;
        return this;
    }

    public void setNhiDeclared(Boolean nhiDeclared) {
        this.nhiDeclared = nhiDeclared;
    }

    public NHIProcedure getNhiProcedure() {
        return nhiProcedure;
    }

    public TreatmentProcedure nhiProcedure(NHIProcedure nHIProcedure) {
        this.nhiProcedure = nHIProcedure;
        return this;
    }

    public void setNhiProcedure(NHIProcedure nHIProcedure) {
        this.nhiProcedure = nHIProcedure;
    }

    public TreatmentTask getTreatmentTask() {
        return treatmentTask;
    }

    public TreatmentProcedure treatmentTask(TreatmentTask treatmentTask) {
        this.treatmentTask = treatmentTask;
        return this;
    }

    public void setTreatmentTask(TreatmentTask treatmentTask) {
        this.treatmentTask = treatmentTask;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public ExtendUser getDoctor() {
        return doctor;
    }

    public TreatmentProcedure doctor(ExtendUser doctor) {
        this.doctor = doctor;
        return this;
    }

    public void setDoctor(ExtendUser doctor) {
        this.doctor = doctor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TreatmentProcedure treatmentProcedure = (TreatmentProcedure) o;
        if (treatmentProcedure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), treatmentProcedure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TreatmentProcedure{" +
            "id=" + getId() +
            ", price=" + getPrice() +
            ", teeth='" + getTeeth() + "'" +
            ", surfaces='" + getSurfaces() + "'" +
            ", nhiDeclared='" + isNhiDeclared() + "'" +
            "}";
    }
}
