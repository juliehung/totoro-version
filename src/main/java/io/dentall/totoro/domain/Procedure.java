package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Procedure.
 */
@Entity
@Table(name = "procedure")
public class Procedure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JsonIgnoreProperties("")
    private ProcedureType procedureType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @ManyToOne
    @JsonIgnoreProperties(value = {"lastPatients", "firstPatients", "appointments", "treatmentProcedures", "treatmentTasks", "procedures", "treatments", "calendars"}, allowSetters = true)
    private ExtendUser doctor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public Procedure content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getPrice() {
        return price;
    }

    public Procedure price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public ProcedureType getProcedureType() {
        return procedureType;
    }

    public Procedure procedureType(ProcedureType procedureType) {
        this.procedureType = procedureType;
        return this;
    }

    public void setProcedureType(ProcedureType procedureType) {
        this.procedureType = procedureType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public ExtendUser getDoctor() {
        return doctor;
    }

    public Procedure doctor(ExtendUser doctor) {
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
        Procedure procedure = (Procedure) o;
        if (procedure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), procedure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Procedure{" +
            "id=" + getId() +
            ", content='" + getContent() + "'" +
            ", price=" + getPrice() +
            "}";
    }
}
