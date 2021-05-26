package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.TagType;

/**
 * A Tag.
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type", nullable = false)
    private TagType type;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "modifiable", nullable = false)
    private Boolean modifiable;

    @Column(name = "jhi_order")
    private Integer order;

    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Patient> patients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TagType getType() {
        return type;
    }

    public Tag type(TagType type) {
        this.type = type;
        return this;
    }

    public void setType(TagType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Tag name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isModifiable() {
        return modifiable;
    }

    public Tag modifiable(Boolean modifiable) {
        this.modifiable = modifiable;
        return this;
    }

    public void setModifiable(Boolean modifiable) {
        this.modifiable = modifiable;
    }

    public Integer getOrder() {
        return order;
    }

    public Tag order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @ApiModelProperty(hidden = true)
    public Set<Patient> getPatients() {
        return patients;
    }

    public Tag patients(Set<Patient> patients) {
        this.patients = patients;
        return this;
    }

    public Tag addPatient(Patient patient) {
        this.patients.add(patient);
        patient.getTags().add(this);
        return this;
    }

    public Tag removePatient(Patient patient) {
        this.patients.remove(patient);
        patient.getTags().remove(this);
        return this;
    }

    public void setPatients(Set<Patient> patients) {
        this.patients = patients;
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
        Tag tag = (Tag) o;
        if (tag.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "{"
            .concat(id == null ? "" : " \"id\": \"" + id + "\"")
            .concat(type == null ? "" : ", \"type\": \"" + type + "\"")
            .concat(name == null ? "" : ", \"name\": \"" + name + "\"")
            .concat(modifiable == null ? "" : ", \"modifiable\": \"" + modifiable + "\"")
            .concat(order == null ? "" : ", \"order\": \"" + order + "\"")
            .concat(patients == null ? "" : ", \"patients\": \"" + patients + "\"")
            .concat("}");
    }
}
