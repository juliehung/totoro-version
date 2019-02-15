package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Drug entity. This class is used in DrugResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /drugs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DrugCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter name;

    private StringFilter chineseName;

    private StringFilter type;

    private LocalDateFilter validDate;

    private LocalDateFilter endDate;

    private StringFilter unit;

    private DoubleFilter price;

    private DoubleFilter quantity;

    private StringFilter frequency;

    private StringFilter way;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getChineseName() {
        return chineseName;
    }

    public void setChineseName(StringFilter chineseName) {
        this.chineseName = chineseName;
    }

    public StringFilter getType() {
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public LocalDateFilter getValidDate() {
        return validDate;
    }

    public void setValidDate(LocalDateFilter validDate) {
        this.validDate = validDate;
    }

    public LocalDateFilter getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateFilter endDate) {
        this.endDate = endDate;
    }

    public StringFilter getUnit() {
        return unit;
    }

    public void setUnit(StringFilter unit) {
        this.unit = unit;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }

    public DoubleFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(DoubleFilter quantity) {
        this.quantity = quantity;
    }

    public StringFilter getFrequency() {
        return frequency;
    }

    public void setFrequency(StringFilter frequency) {
        this.frequency = frequency;
    }

    public StringFilter getWay() {
        return way;
    }

    public void setWay(StringFilter way) {
        this.way = way;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DrugCriteria that = (DrugCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(chineseName, that.chineseName) &&
            Objects.equals(type, that.type) &&
            Objects.equals(validDate, that.validDate) &&
            Objects.equals(endDate, that.endDate) &&
            Objects.equals(unit, that.unit) &&
            Objects.equals(price, that.price) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(frequency, that.frequency) &&
            Objects.equals(way, that.way);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        chineseName,
        type,
        validDate,
        endDate,
        unit,
        price,
        quantity,
        frequency,
        way
        );
    }

    @Override
    public String toString() {
        return "DrugCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (chineseName != null ? "chineseName=" + chineseName + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (validDate != null ? "validDate=" + validDate + ", " : "") +
                (endDate != null ? "endDate=" + endDate + ", " : "") +
                (unit != null ? "unit=" + unit + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (frequency != null ? "frequency=" + frequency + ", " : "") +
                (way != null ? "way=" + way + ", " : "") +
            "}";
    }

}
