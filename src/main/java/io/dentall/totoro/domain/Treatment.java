package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.TreatmentType;

/**
 * A Treatment.
 */
@Entity
@Table(name = "treatment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Treatment extends AbstractDoctorAndAuditingEntity<Treatment> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "chief_complaint")
    private String chiefComplaint;

    @Column(name = "goal")
    private String goal;

    @Size(max = 5100)
    @Column(name = "note", length = 5100)
    private String note;

    @Column(name = "finding")
    private String finding;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private TreatmentType type;

    @ManyToOne
    @JsonIgnoreProperties(value = {"appointments", "treatments", "todos", "teeth", "parents", "spouse1S"}, allowSetters = true)
    private Patient patient;

    @OneToMany(mappedBy = "treatment")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TreatmentPlan> treatmentPlans = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Treatment name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public Treatment chiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
        return this;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public String getGoal() {
        return goal;
    }

    public Treatment goal(String goal) {
        this.goal = goal;
        return this;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getNote() {
        return note;
    }

    public Treatment note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getFinding() {
        return finding;
    }

    public Treatment finding(String finding) {
        this.finding = finding;
        return this;
    }

    public void setFinding(String finding) {
        this.finding = finding;
    }

    public TreatmentType getType() {
        return type;
    }

    public Treatment type(TreatmentType type) {
        this.type = type;
        return this;
    }

    public void setType(TreatmentType type) {
        this.type = type;
    }

    public Patient getPatient() {
        return patient;
    }

    public Treatment patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @ApiModelProperty(hidden = true)
    public Set<TreatmentPlan> getTreatmentPlans() {
        return treatmentPlans;
    }

    public Treatment treatmentPlans(Set<TreatmentPlan> treatmentPlans) {
        this.treatmentPlans = treatmentPlans;
        return this;
    }

    public Treatment addTreatmentPlan(TreatmentPlan treatmentPlan) {
        this.treatmentPlans.add(treatmentPlan);
        treatmentPlan.setTreatment(this);
        return this;
    }

    public Treatment removeTreatmentPlan(TreatmentPlan treatmentPlan) {
        this.treatmentPlans.remove(treatmentPlan);
        treatmentPlan.setTreatment(null);
        return this;
    }

    public void setTreatmentPlans(Set<TreatmentPlan> treatmentPlans) {
        this.treatmentPlans = treatmentPlans;
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
        Treatment treatment = (Treatment) o;
        if (treatment.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), treatment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Treatment{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", chiefComplaint='" + getChiefComplaint() + "'" +
            ", goal='" + getGoal() + "'" +
            ", note='" + getNote() + "'" +
            ", finding='" + getFinding() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
