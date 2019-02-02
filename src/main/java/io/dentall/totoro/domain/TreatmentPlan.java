package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A TreatmentPlan.
 */
@Entity
@Table(name = "treatment_plan")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TreatmentPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "activated", nullable = false)
    private Boolean activated;

    @OneToMany(mappedBy = "treatmentPlan")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TreatmentTask> treatmentTasks = new HashSet<>();
    @ManyToOne
    @JsonIgnoreProperties("treatmentPlans")
    private Treatment treatment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isActivated() {
        return activated;
    }

    public TreatmentPlan activated(Boolean activated) {
        this.activated = activated;
        return this;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Set<TreatmentTask> getTreatmentTasks() {
        return treatmentTasks;
    }

    public TreatmentPlan treatmentTasks(Set<TreatmentTask> treatmentTasks) {
        this.treatmentTasks = treatmentTasks;
        return this;
    }

    public TreatmentPlan addTreatmentTask(TreatmentTask treatmentTask) {
        this.treatmentTasks.add(treatmentTask);
        treatmentTask.setTreatmentPlan(this);
        return this;
    }

    public TreatmentPlan removeTreatmentTask(TreatmentTask treatmentTask) {
        this.treatmentTasks.remove(treatmentTask);
        treatmentTask.setTreatmentPlan(null);
        return this;
    }

    public void setTreatmentTasks(Set<TreatmentTask> treatmentTasks) {
        this.treatmentTasks = treatmentTasks;
    }

    public Treatment getTreatment() {
        return treatment;
    }

    public TreatmentPlan treatment(Treatment treatment) {
        this.treatment = treatment;
        return this;
    }

    public void setTreatment(Treatment treatment) {
        this.treatment = treatment;
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
        TreatmentPlan treatmentPlan = (TreatmentPlan) o;
        if (treatmentPlan.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), treatmentPlan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TreatmentPlan{" +
            "id=" + getId() +
            ", activated='" + isActivated() + "'" +
            "}";
    }
}
