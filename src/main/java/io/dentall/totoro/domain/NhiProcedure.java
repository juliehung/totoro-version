package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A NhiProcedure.
 */
@Entity
@Table(name = "nhi_procedure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NhiProcedure implements Serializable {

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

    @Column(name = "default_icd_10_cm_id")
    private Long defaultIcd10CmId;

    @ManyToOne
    @JsonIgnoreProperties("")
    private NhiProcedureType nhiProcedureType;

    @ManyToOne
    @JsonIgnoreProperties("")
    private NhiIcd9Cm nhiIcd9Cm;

    @OneToMany(mappedBy = "nhiProcedure", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NhiIcd10Pcs> nhiIcd10Pcs = new HashSet<>();
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

    public NhiProcedure code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public NhiProcedure name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoint() {
        return point;
    }

    public NhiProcedure point(Integer point) {
        this.point = point;
        return this;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }

    public String getEnglishName() {
        return englishName;
    }

    public NhiProcedure englishName(String englishName) {
        this.englishName = englishName;
        return this;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Long getDefaultIcd10CmId() {
        return defaultIcd10CmId;
    }

    public NhiProcedure defaultIcd10CmId(Long defaultIcd10CmId) {
        this.defaultIcd10CmId = defaultIcd10CmId;
        return this;
    }

    public void setDefaultIcd10CmId(Long defaultIcd10CmId) {
        this.defaultIcd10CmId = defaultIcd10CmId;
    }

    public NhiProcedureType getNhiProcedureType() {
        return nhiProcedureType;
    }

    public NhiProcedure nhiProcedureType(NhiProcedureType nhiProcedureType) {
        this.nhiProcedureType = nhiProcedureType;
        return this;
    }

    public void setNhiProcedureType(NhiProcedureType nhiProcedureType) {
        this.nhiProcedureType = nhiProcedureType;
    }

    public NhiIcd9Cm getNhiIcd9Cm() {
        return nhiIcd9Cm;
    }

    public NhiProcedure nhiIcd9Cm(NhiIcd9Cm nhiIcd9Cm) {
        this.nhiIcd9Cm = nhiIcd9Cm;
        return this;
    }

    public void setNhiIcd9Cm(NhiIcd9Cm nhiIcd9Cm) {
        this.nhiIcd9Cm = nhiIcd9Cm;
    }

    public Set<NhiIcd10Pcs> getNhiIcd10Pcs() {
        return nhiIcd10Pcs;
    }

    public NhiProcedure nhiIcd10Pcs(Set<NhiIcd10Pcs> nhiIcd10Pcs) {
        this.nhiIcd10Pcs = nhiIcd10Pcs;
        return this;
    }

    public NhiProcedure addNhiIcd10Pcs(NhiIcd10Pcs nhiIcd10Pcs) {
        this.nhiIcd10Pcs.add(nhiIcd10Pcs);
        nhiIcd10Pcs.setNhiProcedure(this);
        return this;
    }

    public NhiProcedure removeNhiIcd10Pcs(NhiIcd10Pcs nhiIcd10Pcs) {
        this.nhiIcd10Pcs.remove(nhiIcd10Pcs);
        nhiIcd10Pcs.setNhiProcedure(null);
        return this;
    }

    public void setNhiIcd10Pcs(Set<NhiIcd10Pcs> nhiIcd10Pcs) {
        this.nhiIcd10Pcs = nhiIcd10Pcs;
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
        NhiProcedure nhiProcedure = (NhiProcedure) o;
        if (nhiProcedure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhiProcedure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhiProcedure{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", point=" + getPoint() +
            ", englishName='" + getEnglishName() + "'" +
            ", defaultIcd10CmId=" + getDefaultIcd10CmId() +
            "}";
    }
}
