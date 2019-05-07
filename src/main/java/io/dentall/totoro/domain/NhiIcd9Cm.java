package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A NhiIcd9Cm.
 */
@Entity
@Table(name = "nhi_icd_9_cm")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NhiIcd9Cm implements Serializable {

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

    @OneToMany(mappedBy = "nhiIcd9Cm", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NhiIcd10Cm> nhiIcd10Cms = new HashSet<>();
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

    public NhiIcd9Cm code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public NhiIcd9Cm name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEnglishName() {
        return englishName;
    }

    public NhiIcd9Cm englishName(String englishName) {
        this.englishName = englishName;
        return this;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Set<NhiIcd10Cm> getNhiIcd10Cms() {
        return nhiIcd10Cms;
    }

    public NhiIcd9Cm nhiIcd10Cms(Set<NhiIcd10Cm> nhiIcd10Cms) {
        this.nhiIcd10Cms = nhiIcd10Cms;
        return this;
    }

    public NhiIcd9Cm addNhiIcd10Cm(NhiIcd10Cm nhiIcd10Cm) {
        this.nhiIcd10Cms.add(nhiIcd10Cm);
        nhiIcd10Cm.setNhiIcd9Cm(this);
        return this;
    }

    public NhiIcd9Cm removeNhiIcd10Cm(NhiIcd10Cm nhiIcd10Cm) {
        this.nhiIcd10Cms.remove(nhiIcd10Cm);
        nhiIcd10Cm.setNhiIcd9Cm(null);
        return this;
    }

    public void setNhiIcd10Cms(Set<NhiIcd10Cm> nhiIcd10Cms) {
        this.nhiIcd10Cms = nhiIcd10Cms;
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
        NhiIcd9Cm nhiIcd9Cm = (NhiIcd9Cm) o;
        if (nhiIcd9Cm.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhiIcd9Cm.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhiIcd9Cm{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", englishName='" + getEnglishName() + "'" +
            "}";
    }
}
