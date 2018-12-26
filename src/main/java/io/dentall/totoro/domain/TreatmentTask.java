package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.TreatmentTaskStatus;

/**
 * A TreatmentTask.
 */
@Entity
@Table(name = "treatment_task")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TreatmentTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TreatmentTaskStatus status;

    @Column(name = "description")
    private String description;

    @Column(name = "teeth")
    private String teeth;

    @Column(name = "surfaces")
    private String surfaces;

    @Column(name = "note")
    private String note;

    @OneToMany(mappedBy = "treatmentTask")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TreatmentProcedure> treatmentProcedures = new HashSet<>();
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

    public TreatmentTaskStatus getStatus() {
        return status;
    }

    public TreatmentTask status(TreatmentTaskStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TreatmentTaskStatus status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public TreatmentTask description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTeeth() {
        return teeth;
    }

    public TreatmentTask teeth(String teeth) {
        this.teeth = teeth;
        return this;
    }

    public void setTeeth(String teeth) {
        this.teeth = teeth;
    }

    public String getSurfaces() {
        return surfaces;
    }

    public TreatmentTask surfaces(String surfaces) {
        this.surfaces = surfaces;
        return this;
    }

    public void setSurfaces(String surfaces) {
        this.surfaces = surfaces;
    }

    public String getNote() {
        return note;
    }

    public TreatmentTask note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<TreatmentProcedure> getTreatmentProcedures() {
        return treatmentProcedures;
    }

    public TreatmentTask treatmentProcedures(Set<TreatmentProcedure> treatmentProcedures) {
        this.treatmentProcedures = treatmentProcedures;
        return this;
    }

    public TreatmentTask addTreatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedures.add(treatmentProcedure);
        treatmentProcedure.setTreatmentTask(this);
        return this;
    }

    public TreatmentTask removeTreatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedures.remove(treatmentProcedure);
        treatmentProcedure.setTreatmentTask(null);
        return this;
    }

    public void setTreatmentProcedures(Set<TreatmentProcedure> treatmentProcedures) {
        this.treatmentProcedures = treatmentProcedures;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public ExtendUser getDoctor() {
        return doctor;
    }

    public TreatmentTask doctor(ExtendUser doctor) {
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
        TreatmentTask treatmentTask = (TreatmentTask) o;
        if (treatmentTask.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), treatmentTask.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TreatmentTask{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", description='" + getDescription() + "'" +
            ", teeth='" + getTeeth() + "'" +
            ", surfaces='" + getSurfaces() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
