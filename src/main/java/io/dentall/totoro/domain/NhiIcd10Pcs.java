package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A NhiIcd10Pcs.
 */
@Entity
@Table(name = "nhi_icd_10_pcs")
public class NhiIcd10Pcs implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "nhi_name")
    private String nhiName;

    @Column(name = "english_name")
    private String englishName;

    @Column(name = "chinese_name")
    private String chineseName;

    @ManyToOne
    @JsonIgnore
    private NhiProcedure nhiProcedure;

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

    public NhiIcd10Pcs code(String code) {
        this.code = code;
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNhiName() {
        return nhiName;
    }

    public NhiIcd10Pcs nhiName(String nhiName) {
        this.nhiName = nhiName;
        return this;
    }

    public void setNhiName(String nhiName) {
        this.nhiName = nhiName;
    }

    public String getEnglishName() {
        return englishName;
    }

    public NhiIcd10Pcs englishName(String englishName) {
        this.englishName = englishName;
        return this;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public NhiIcd10Pcs chineseName(String chineseName) {
        this.chineseName = chineseName;
        return this;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public NhiProcedure getNhiProcedure() {
        return nhiProcedure;
    }

    public NhiIcd10Pcs nhiProcedure(NhiProcedure nhiProcedure) {
        this.nhiProcedure = nhiProcedure;
        return this;
    }

    public void setNhiProcedure(NhiProcedure nhiProcedure) {
        this.nhiProcedure = nhiProcedure;
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
        NhiIcd10Pcs nhiIcd10Pcs = (NhiIcd10Pcs) o;
        if (nhiIcd10Pcs.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhiIcd10Pcs.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhiIcd10Pcs{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", nhiName='" + getNhiName() + "'" +
            ", englishName='" + getEnglishName() + "'" +
            ", chineseName='" + getChineseName() + "'" +
            "}";
    }
}
