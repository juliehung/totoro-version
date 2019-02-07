package io.dentall.totoro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A FindingType.
 */
@Entity
@Table(name = "finding_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class FindingType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "major", nullable = false)
    private String major;

    @Column(name = "minor")
    private String minor;

    @NotNull
    @Column(name = "display", nullable = false)
    private Boolean display;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMajor() {
        return major;
    }

    public FindingType major(String major) {
        this.major = major;
        return this;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public FindingType minor(String minor) {
        this.minor = minor;
        return this;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public Boolean isDisplay() {
        return display;
    }

    public FindingType display(Boolean display) {
        this.display = display;
        return this;
    }

    public void setDisplay(Boolean display) {
        this.display = display;
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
        FindingType findingType = (FindingType) o;
        if (findingType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), findingType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FindingType{" +
            "id=" + getId() +
            ", major='" + getMajor() + "'" +
            ", minor='" + getMinor() + "'" +
            ", display='" + isDisplay() + "'" +
            "}";
    }
}
