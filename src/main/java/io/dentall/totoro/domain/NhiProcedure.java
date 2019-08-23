package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
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

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "point")
    private Integer point;

    @Column(name = "english_name")
    private String englishName;

    @Column(name = "default_icd_10_cm_id")
    private Long defaultIcd10CmId;

    @Size(max = 5100)
    @Column(name = "description", length = 5100)
    private String description;

    @Size(max = 510)
    @Column(name = "exclude", length = 510)
    private String exclude;

    @Column(name = "fdi")
    private String fdi;

    @Column(name = "specific_code")
    private String specificCode;

    @Column(name = "chief_complaint")
    private String chiefComplaint;

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

    public String getDescription() {
        return description;
    }

    public NhiProcedure description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExclude() {
        return exclude;
    }

    public NhiProcedure exclude(String exclude) {
        this.exclude = exclude;
        return this;
    }

    public void setExclude(String exclude) {
        this.exclude = exclude;
    }

    public String getFdi() {
        return fdi;
    }

    public NhiProcedure fdi(String fdi) {
        this.fdi = fdi;
        return this;
    }

    public void setFdi(String fdi) {
        this.fdi = fdi;
    }

    public String getSpecificCode() {
        return specificCode;
    }

    public NhiProcedure specificCode(String specificCode) {
        this.specificCode = specificCode;
        return this;
    }

    public void setSpecificCode(String specificCode) {
        this.specificCode = specificCode;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public NhiProcedure chiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
        return this;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
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

    @ApiModelProperty(hidden = true)
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
            ", description='" + getDescription() + "'" +
            ", exclude='" + getExclude() + "'" +
            ", fdi='" + getFdi() + "'" +
            ", specificCode='" + getSpecificCode() + "'" +
            ", chiefComplaint='" + getChiefComplaint() + "'" +
            "}";
    }
}
