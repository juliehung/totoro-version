package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A Drug.
 */
@Entity
@Table(name = "drug")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Drug extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * 2021-03-16: 前端新增藥品時，並不會帶入此項
     */
    @Deprecated
    @Column(name = "chinese_name")
    private String chineseName;

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

    @Column(name = "nhi_code")
    private String nhiCode;

    @Column(name = "warning")
    private String warning;

    @Column(name = "days")
    private Integer days;

    @Column(name = "jhi_order")
    private Integer order;
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

    public String getNhiCode() {
        return nhiCode;
    }

    public Drug nhiCode(String nhiCode) {
        this.nhiCode = nhiCode;
        return this;
    }

    public void setNhiCode(String nhiCode) {
        this.nhiCode = nhiCode;
    }

    public String getWarning() {
        return warning;
    }

    public Drug warning(String warning) {
        this.warning = warning;
        return this;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public Integer getDays() {
        return days;
    }

    public Drug days(Integer days) {
        this.days = days;
        return this;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getOrder() {
        return order;
    }

    public Drug order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    @JsonIgnore(false)
    @JsonProperty
    public Instant getCreatedDate() {
        return super.getCreatedDate();
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
            ", unit='" + getUnit() + "'" +
            ", price=" + getPrice() +
            ", quantity=" + getQuantity() +
            ", frequency='" + getFrequency() + "'" +
            ", way='" + getWay() + "'" +
            ", nhiCode='" + getNhiCode() + "'" +
            ", warning='" + getWarning() + "'" +
            ", days=" + getDays() +
            ", order=" + getOrder() +
            "}";
    }
}
