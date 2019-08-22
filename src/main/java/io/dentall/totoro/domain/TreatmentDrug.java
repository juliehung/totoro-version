package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * A TreatmentDrug.
 */
@Entity
@Table(name = "treatment_drug")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TreatmentDrug implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "day")
    private Integer day;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "way")
    private String way;

    @Column(name = "quantity")
    private Double quantity;

    @ManyToOne
    @JsonProperty(access = WRITE_ONLY)
    private Prescription prescription;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Drug drug;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @OneToOne(mappedBy = "treatmentDrug", cascade = CascadeType.ALL)
    private NhiExtendTreatmentDrug nhiExtendTreatmentDrug;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public TreatmentDrug day(Integer day) {
        this.day = day;
        return this;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getFrequency() {
        return frequency;
    }

    public TreatmentDrug frequency(String frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getWay() {
        return way;
    }

    public TreatmentDrug way(String way) {
        this.way = way;
        return this;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public Double getQuantity() {
        return quantity;
    }

    public TreatmentDrug quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public TreatmentDrug prescription(Prescription prescription) {
        this.prescription = prescription;
        return this;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Drug getDrug() {
        return drug;
    }

    public TreatmentDrug drug(Drug drug) {
        this.drug = drug;
        return this;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @ApiModelProperty(hidden = true)
    public NhiExtendTreatmentDrug getNhiExtendTreatmentDrug() {
        return nhiExtendTreatmentDrug;
    }

    public TreatmentDrug nhiExtendTreatmentDrug(NhiExtendTreatmentDrug nhiExtendTreatmentDrug) {
        this.nhiExtendTreatmentDrug = nhiExtendTreatmentDrug;
        return this;
    }

    public void setNhiExtendTreatmentDrug(NhiExtendTreatmentDrug nhiExtendTreatmentDrug) {
        this.nhiExtendTreatmentDrug = nhiExtendTreatmentDrug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TreatmentDrug treatmentDrug = (TreatmentDrug) o;
        if (treatmentDrug.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), treatmentDrug.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TreatmentDrug{" +
            "id=" + getId() +
            ", day=" + getDay() +
            ", frequency='" + getFrequency() + "'" +
            ", way='" + getWay() + "'" +
            ", quantity=" + getQuantity() +
            "}";
    }
}
