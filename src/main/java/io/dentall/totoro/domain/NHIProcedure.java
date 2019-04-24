package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A NHIProcedure.
 */
@Entity
@Table(name = "nhi_procedure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NHIProcedure implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "point", nullable = false)
    private Integer point;

    @Column(name = "english_name")
    private String englishName;

    @ManyToOne
    @JsonIgnoreProperties("")
    private NHIProcedureType nHIProcedureType;

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

    public NHIProcedure code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public NHIProcedure name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoint() {
        return point;
    }

    public NHIProcedure point(Integer point) {
        this.point = point;
        return this;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getEnglishName() {
        return englishName;
    }

    public NHIProcedure englishName(String englishName) {
        this.englishName = englishName;
        return this;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public NHIProcedureType getNHIProcedureType() {
        return nHIProcedureType;
    }

    public NHIProcedure nHIProcedureType(NHIProcedureType nHIProcedureType) {
        this.nHIProcedureType = nHIProcedureType;
        return this;
    }

    public void setNHIProcedureType(NHIProcedureType nHIProcedureType) {
        this.nHIProcedureType = nHIProcedureType;
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
        NHIProcedure nHIProcedure = (NHIProcedure) o;
        if (nHIProcedure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nHIProcedure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NHIProcedure{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", point=" + getPoint() +
            ", englishName='" + getEnglishName() + "'" +
            "}";
    }
}
