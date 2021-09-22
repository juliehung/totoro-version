package io.dentall.totoro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * A TreatmentDrugDel.
 */
@Entity
@Table(name = "treatment_drug_del")
public class TreatmentDrugDel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(name = "day")
    private Integer day;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "way")
    private String way;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "prescription_id")
    private Long prescriptionId;

    @Column(name = "drug_id")
    private Long drugId;

    @Column(name = "total_amount")
    private Double totalAmount;

    @Column(name = "ic_card_eject")
    private Boolean icCardEject = false;

    public Boolean getIcCardEject() {
        return icCardEject;
    }

    public void setIcCardEject(Boolean icCardEject) {
        this.icCardEject = icCardEject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getWay() {
        return way;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public Long getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Long prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Long getDrugId() {
        return drugId;
    }

    public void setDrugId(Long drugId) {
        this.drugId = drugId;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
