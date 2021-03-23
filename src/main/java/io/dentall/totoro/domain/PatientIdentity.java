package io.dentall.totoro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A PatientIdentity.
 */
@Entity
@Table(name = "patient_identity")
public class PatientIdentity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "enabled")
    private boolean enabled;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "free_burden", nullable = false)
    private Boolean freeBurden;

    public PatientIdentity enable(boolean ennable) {
        this.enabled = ennable;
        return this;
    }

    public Boolean getFreeBurden() {
        return freeBurden;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public PatientIdentity code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public PatientIdentity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isFreeBurden() {
        return freeBurden;
    }

    public PatientIdentity freeBurden(Boolean freeBurden) {
        this.freeBurden = freeBurden;
        return this;
    }

    public void setFreeBurden(Boolean freeBurden) {
        this.freeBurden = freeBurden;
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
        PatientIdentity patientIdentity = (PatientIdentity) o;
        if (patientIdentity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), patientIdentity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PatientIdentity{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", freeBurden='" + isFreeBurden() + "'" +
            "}";
    }
}
