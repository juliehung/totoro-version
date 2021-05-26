package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Questionnaire.
 */
@Entity
@Table(name = "questionnaire")
public class Questionnaire extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "drug")
    private Boolean drug;

    @Column(name = "drug_name")
    private String drugName;

    @Column(name = "glycemic_ac")
    private Integer glycemicAC;

    @Column(name = "glycemic_pc")
    private Integer glycemicPC;

    @Column(name = "smoke_number_a_day")
    private Integer smokeNumberADay;

    @Column(name = "other_in_treatment")
    private String otherInTreatment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isDrug() {
        return drug;
    }

    public Questionnaire drug(Boolean drug) {
        this.drug = drug;
        return this;
    }

    public void setDrug(Boolean drug) {
        this.drug = drug;
    }

    public String getDrugName() {
        return drugName;
    }

    public Questionnaire drugName(String drugName) {
        this.drugName = drugName;
        return this;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Integer getGlycemicAC() {
        return glycemicAC;
    }

    public Questionnaire glycemicAC(Integer glycemicAC) {
        this.glycemicAC = glycemicAC;
        return this;
    }

    public void setGlycemicAC(Integer glycemicAC) {
        this.glycemicAC = glycemicAC;
    }

    public Integer getGlycemicPC() {
        return glycemicPC;
    }

    public Questionnaire glycemicPC(Integer glycemicPC) {
        this.glycemicPC = glycemicPC;
        return this;
    }

    public void setGlycemicPC(Integer glycemicPC) {
        this.glycemicPC = glycemicPC;
    }

    public Integer getSmokeNumberADay() {
        return smokeNumberADay;
    }

    public Questionnaire smokeNumberADay(Integer smokeNumberADay) {
        this.smokeNumberADay = smokeNumberADay;
        return this;
    }

    public void setSmokeNumberADay(Integer smokeNumberADay) {
        this.smokeNumberADay = smokeNumberADay;
    }

    public String getOtherInTreatment() {
        return otherInTreatment;
    }

    public Questionnaire otherInTreatment(String otherInTreatment) {
        this.otherInTreatment = otherInTreatment;
        return this;
    }

    public void setOtherInTreatment(String otherInTreatment) {
        this.otherInTreatment = otherInTreatment;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    @JsonIgnore(false)
    @JsonProperty
    public Instant getLastModifiedDate() {
        return super.getLastModifiedDate();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Questionnaire questionnaire = (Questionnaire) o;
        if (questionnaire.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), questionnaire.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "{"
            .concat(id == null ? "" : " \"id\": \"" + id + "\"")
            .concat(drug == null ? "" : ", \"drug\": \"" + drug + "\"")
            .concat(drugName == null ? "" : ", \"drugName\": \"" + drugName + "\"")
            .concat(glycemicAC == null ? "" : ", \"glycemicAC\": \"" + glycemicAC + "\"")
            .concat(glycemicPC == null ? "" : ", \"glycemicPC\": \"" + glycemicPC + "\"")
            .concat(smokeNumberADay == null ? "" : ", \"smokeNumberADay\": \"" + smokeNumberADay + "\"")
            .concat(otherInTreatment == null ? "" : ", \"otherInTreatment\": \"" + otherInTreatment + "\"")
            .concat("}");
    }
}
