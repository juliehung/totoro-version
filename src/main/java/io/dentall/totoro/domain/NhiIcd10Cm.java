package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A NhiIcd10Cm.
 */
@Entity
@Table(name = "nhi_icd_10_cm")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NhiIcd10Cm implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "english_name")
    private String englishName;

    @ManyToOne
    @JsonIgnoreProperties("nhiIcd10Cms")
    private NhiIcd9Cm nhiIcd9Cm;

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

    public NhiIcd10Cm code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public NhiIcd10Cm name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public NhiIcd10Cm englishName(String englishName) {
        this.englishName = englishName;
        return this;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public NhiIcd9Cm getNhiIcd9Cm() {
        return nhiIcd9Cm;
    }

    public NhiIcd10Cm nhiIcd9Cm(NhiIcd9Cm nhiIcd9Cm) {
        this.nhiIcd9Cm = nhiIcd9Cm;
        return this;
    }

    public void setNhiIcd9Cm(NhiIcd9Cm nhiIcd9Cm) {
        this.nhiIcd9Cm = nhiIcd9Cm;
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
        NhiIcd10Cm nhiIcd10Cm = (NhiIcd10Cm) o;
        if (nhiIcd10Cm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhiIcd10Cm.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhiIcd10Cm{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", englishName='" + getEnglishName() + "'" +
            "}";
    }
}
