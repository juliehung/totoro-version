package io.dentall.totoro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.Hepatitis;

import io.dentall.totoro.domain.enumeration.Month;

/**
 * A Questionnaire.
 */
@Entity
@Table(name = "questionnaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Questionnaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "hypertension")
    private String hypertension;

    @Column(name = "heart_diseases")
    private String heartDiseases;

    @Column(name = "kidney_diseases")
    private String kidneyDiseases;

    @Column(name = "blood_diseases")
    private String bloodDiseases;

    @Column(name = "liver_diseases")
    private String liverDiseases;

    @Enumerated(EnumType.STRING)
    @Column(name = "hepatitis_type")
    private Hepatitis hepatitisType;

    @Column(name = "gastrointestinal_diseases")
    private String gastrointestinalDiseases;

    @Column(name = "receiving_medication")
    private String receivingMedication;

    @Column(name = "any_allergy_sensitivity")
    private String anyAllergySensitivity;

    @Column(name = "glycemic_ac")
    private Integer glycemicAC;

    @Column(name = "glycemic_pc")
    private Integer glycemicPC;

    @Column(name = "smoke_number_a_day")
    private Integer smokeNumberADay;

    @Column(name = "production_year")
    private Integer productionYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "production_month")
    private Month productionMonth;

    @Column(name = "other")
    private String other;

    @Column(name = "difficult_extraction_or_continuous_bleeding")
    private Boolean difficultExtractionOrContinuousBleeding;

    @Column(name = "nausea_or_dizziness")
    private Boolean nauseaOrDizziness;

    @Column(name = "adverse_reactions_to_anesthetic_injections")
    private Boolean adverseReactionsToAnestheticInjections;

    @Column(name = "other_in_treatment")
    private String otherInTreatment;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHypertension() {
        return hypertension;
    }

    public Questionnaire hypertension(String hypertension) {
        this.hypertension = hypertension;
        return this;
    }

    public void setHypertension(String hypertension) {
        this.hypertension = hypertension;
    }

    public String getHeartDiseases() {
        return heartDiseases;
    }

    public Questionnaire heartDiseases(String heartDiseases) {
        this.heartDiseases = heartDiseases;
        return this;
    }

    public void setHeartDiseases(String heartDiseases) {
        this.heartDiseases = heartDiseases;
    }

    public String getKidneyDiseases() {
        return kidneyDiseases;
    }

    public Questionnaire kidneyDiseases(String kidneyDiseases) {
        this.kidneyDiseases = kidneyDiseases;
        return this;
    }

    public void setKidneyDiseases(String kidneyDiseases) {
        this.kidneyDiseases = kidneyDiseases;
    }

    public String getBloodDiseases() {
        return bloodDiseases;
    }

    public Questionnaire bloodDiseases(String bloodDiseases) {
        this.bloodDiseases = bloodDiseases;
        return this;
    }

    public void setBloodDiseases(String bloodDiseases) {
        this.bloodDiseases = bloodDiseases;
    }

    public String getLiverDiseases() {
        return liverDiseases;
    }

    public Questionnaire liverDiseases(String liverDiseases) {
        this.liverDiseases = liverDiseases;
        return this;
    }

    public void setLiverDiseases(String liverDiseases) {
        this.liverDiseases = liverDiseases;
    }

    public Hepatitis getHepatitisType() {
        return hepatitisType;
    }

    public Questionnaire hepatitisType(Hepatitis hepatitisType) {
        this.hepatitisType = hepatitisType;
        return this;
    }

    public void setHepatitisType(Hepatitis hepatitisType) {
        this.hepatitisType = hepatitisType;
    }

    public String getGastrointestinalDiseases() {
        return gastrointestinalDiseases;
    }

    public Questionnaire gastrointestinalDiseases(String gastrointestinalDiseases) {
        this.gastrointestinalDiseases = gastrointestinalDiseases;
        return this;
    }

    public void setGastrointestinalDiseases(String gastrointestinalDiseases) {
        this.gastrointestinalDiseases = gastrointestinalDiseases;
    }

    public String getReceivingMedication() {
        return receivingMedication;
    }

    public Questionnaire receivingMedication(String receivingMedication) {
        this.receivingMedication = receivingMedication;
        return this;
    }

    public void setReceivingMedication(String receivingMedication) {
        this.receivingMedication = receivingMedication;
    }

    public String getAnyAllergySensitivity() {
        return anyAllergySensitivity;
    }

    public Questionnaire anyAllergySensitivity(String anyAllergySensitivity) {
        this.anyAllergySensitivity = anyAllergySensitivity;
        return this;
    }

    public void setAnyAllergySensitivity(String anyAllergySensitivity) {
        this.anyAllergySensitivity = anyAllergySensitivity;
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

    public Integer getProductionYear() {
        return productionYear;
    }

    public Questionnaire productionYear(Integer productionYear) {
        this.productionYear = productionYear;
        return this;
    }

    public void setProductionYear(Integer productionYear) {
        this.productionYear = productionYear;
    }

    public Month getProductionMonth() {
        return productionMonth;
    }

    public Questionnaire productionMonth(Month productionMonth) {
        this.productionMonth = productionMonth;
        return this;
    }

    public void setProductionMonth(Month productionMonth) {
        this.productionMonth = productionMonth;
    }

    public String getOther() {
        return other;
    }

    public Questionnaire other(String other) {
        this.other = other;
        return this;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public Boolean isDifficultExtractionOrContinuousBleeding() {
        return difficultExtractionOrContinuousBleeding;
    }

    public Questionnaire difficultExtractionOrContinuousBleeding(Boolean difficultExtractionOrContinuousBleeding) {
        this.difficultExtractionOrContinuousBleeding = difficultExtractionOrContinuousBleeding;
        return this;
    }

    public void setDifficultExtractionOrContinuousBleeding(Boolean difficultExtractionOrContinuousBleeding) {
        this.difficultExtractionOrContinuousBleeding = difficultExtractionOrContinuousBleeding;
    }

    public Boolean isNauseaOrDizziness() {
        return nauseaOrDizziness;
    }

    public Questionnaire nauseaOrDizziness(Boolean nauseaOrDizziness) {
        this.nauseaOrDizziness = nauseaOrDizziness;
        return this;
    }

    public void setNauseaOrDizziness(Boolean nauseaOrDizziness) {
        this.nauseaOrDizziness = nauseaOrDizziness;
    }

    public Boolean isAdverseReactionsToAnestheticInjections() {
        return adverseReactionsToAnestheticInjections;
    }

    public Questionnaire adverseReactionsToAnestheticInjections(Boolean adverseReactionsToAnestheticInjections) {
        this.adverseReactionsToAnestheticInjections = adverseReactionsToAnestheticInjections;
        return this;
    }

    public void setAdverseReactionsToAnestheticInjections(Boolean adverseReactionsToAnestheticInjections) {
        this.adverseReactionsToAnestheticInjections = adverseReactionsToAnestheticInjections;
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
        return "Questionnaire{" +
            "id=" + getId() +
            ", hypertension='" + getHypertension() + "'" +
            ", heartDiseases='" + getHeartDiseases() + "'" +
            ", kidneyDiseases='" + getKidneyDiseases() + "'" +
            ", bloodDiseases='" + getBloodDiseases() + "'" +
            ", liverDiseases='" + getLiverDiseases() + "'" +
            ", hepatitisType='" + getHepatitisType() + "'" +
            ", gastrointestinalDiseases='" + getGastrointestinalDiseases() + "'" +
            ", receivingMedication='" + getReceivingMedication() + "'" +
            ", anyAllergySensitivity='" + getAnyAllergySensitivity() + "'" +
            ", glycemicAC=" + getGlycemicAC() +
            ", glycemicPC=" + getGlycemicPC() +
            ", smokeNumberADay=" + getSmokeNumberADay() +
            ", productionYear=" + getProductionYear() +
            ", productionMonth='" + getProductionMonth() + "'" +
            ", other='" + getOther() + "'" +
            ", difficultExtractionOrContinuousBleeding='" + isDifficultExtractionOrContinuousBleeding() + "'" +
            ", nauseaOrDizziness='" + isNauseaOrDizziness() + "'" +
            ", adverseReactionsToAnestheticInjections='" + isAdverseReactionsToAnestheticInjections() + "'" +
            ", otherInTreatment='" + getOtherInTreatment() + "'" +
            "}";
    }
}
