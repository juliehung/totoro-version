package io.dentall.totoro.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Drug.
 */
@Entity
@Table(name = "drug")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Drug implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "chinese_name")
    private String chineseName;

    @Column(name = "jhi_type")
    private String type;

    @Column(name = "valid_date")
    private LocalDate validDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "unit")
    private String unit;

    @Column(name = "price")
    private Double price;

    @Column(name = "quantity")
    private Double quantity;

    @Column(name = "frequency")
    private String frequency;

    @Column(name = "way")
    private String way;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Drug name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChineseName() {
        return chineseName;
    }

    public Drug chineseName(String chineseName) {
        this.chineseName = chineseName;
        return this;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getType() {
        return type;
    }

    public Drug type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDate getValidDate() {
        return validDate;
    }

    public Drug validDate(LocalDate validDate) {
        this.validDate = validDate;
        return this;
    }

    public void setValidDate(LocalDate validDate) {
        this.validDate = validDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Drug endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getUnit() {
        return unit;
    }

    public Drug unit(String unit) {
        this.unit = unit;
        return this;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Double getPrice() {
        return price;
    }

    public Drug price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getQuantity() {
        return quantity;
    }

    public Drug quantity(Double quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getFrequency() {
        return frequency;
    }

    public Drug frequency(String frequency) {
        this.frequency = frequency;
        return this;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getWay() {
        return way;
    }

    public Drug way(String way) {
        this.way = way;
        return this;
    }

    public void setWay(String way) {
        this.way = way;
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
        Drug drug = (Drug) o;
        if (drug.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), drug.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Drug{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", chineseName='" + getChineseName() + "'" +
            ", type='" + getType() + "'" +
            ", validDate='" + getValidDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", unit='" + getUnit() + "'" +
            ", price=" + getPrice() +
            ", quantity=" + getQuantity() +
            ", frequency='" + getFrequency() + "'" +
            ", way='" + getWay() + "'" +
            "}";
    }
}
