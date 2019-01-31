package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

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

    @Column(name = "note")
    private String note;

    @Column(name = "finding")
    private String finding;

    @ManyToOne
    @JsonIgnoreProperties("treatments")
    private Patient patient;

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
            "}";
    }
}
