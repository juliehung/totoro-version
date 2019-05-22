package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.TodoStatus;

/**
 * A Todo.
 */
@Entity
@Table(name = "todo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Todo extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TodoStatus status;

    @Column(name = "expected_date")
    private LocalDate expectedDate;

    @Column(name = "required_treatment_time")
    private Integer requiredTreatmentTime;

    @Size(max = 5100)
    @Column(name = "note", length = 5100)
    private String note;

    @ManyToOne
    @JsonIgnoreProperties(value = {"appointments", "treatments", "todos", "teeth", "parents", "spouse1S"}, allowSetters = true)
    private Patient patient;

    @OneToMany(mappedBy = "todo", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TreatmentProcedure> treatmentProcedures = new HashSet<>();

    @OneToOne(mappedBy = "todo")
    @JsonIgnore
    private Disposal disposal;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TodoStatus getStatus() {
        return status;
    }

    public Todo status(TodoStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TodoStatus status) {
        this.status = status;
    }

    public LocalDate getExpectedDate() {
        return expectedDate;
    }

    public Todo expectedDate(LocalDate expectedDate) {
        this.expectedDate = expectedDate;
        return this;
    }

    public void setExpectedDate(LocalDate expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Integer getRequiredTreatmentTime() {
        return requiredTreatmentTime;
    }

    public Todo requiredTreatmentTime(Integer requiredTreatmentTime) {
        this.requiredTreatmentTime = requiredTreatmentTime;
        return this;
    }

    public void setRequiredTreatmentTime(Integer requiredTreatmentTime) {
        this.requiredTreatmentTime = requiredTreatmentTime;
    }

    public String getNote() {
        return note;
    }

    public Todo note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Patient getPatient() {
        return patient;
    }

    public Todo patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Set<TreatmentProcedure> getTreatmentProcedures() {
        return treatmentProcedures;
    }

    public Todo treatmentProcedures(Set<TreatmentProcedure> treatmentProcedures) {
        this.treatmentProcedures = treatmentProcedures;
        return this;
    }

    public Todo addTreatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedures.add(treatmentProcedure);
        treatmentProcedure.setTodo(this);
        return this;
    }

    public Todo removeTreatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedures.remove(treatmentProcedure);
        treatmentProcedure.setTodo(null);
        return this;
    }

    public void setTreatmentProcedures(Set<TreatmentProcedure> treatmentProcedures) {
        this.treatmentProcedures = treatmentProcedures;
    }

    public Disposal getDisposal() {
        return disposal;
    }

    public Todo disposal(Disposal disposal) {
        this.disposal = disposal;
        return this;
    }

    public void setDisposal(Disposal disposal) {
        this.disposal = disposal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Todo todo = (Todo) o;
        if (todo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), todo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Todo{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", expectedDate='" + getExpectedDate() + "'" +
            ", requiredTreatmentTime=" + getRequiredTreatmentTime() +
            ", note='" + getNote() + "'" +
            "}";
    }
}
