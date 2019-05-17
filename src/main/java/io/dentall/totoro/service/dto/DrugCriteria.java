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

    private StringFilter unit;

    private DoubleFilter price;

    private DoubleFilter quantity;

    private StringFilter frequency;

    private StringFilter way;

    private StringFilter nhiCode;

    private StringFilter warning;

    private IntegerFilter days;

    private IntegerFilter order;

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

    public StringFilter getNhiCode() {
        return nhiCode;
    }

    public void setNhiCode(StringFilter nhiCode) {
        this.nhiCode = nhiCode;
    }

    public StringFilter getWarning() {
        return warning;
    }

    public void setWarning(StringFilter warning) {
        this.warning = warning;
    }

    public IntegerFilter getDays() {
        return days;
    }

    public void setDays(IntegerFilter days) {
        this.days = days;
    }

    public IntegerFilter getOrder() {
        return order;
    }

    public void setOrder(IntegerFilter order) {
        this.order = order;
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
            Objects.equals(unit, that.unit) &&
            Objects.equals(price, that.price) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(frequency, that.frequency) &&
            Objects.equals(way, that.way) &&
            Objects.equals(nhiCode, that.nhiCode) &&
            Objects.equals(warning, that.warning) &&
            Objects.equals(days, that.days) &&
            Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        chineseName,
        unit,
        price,
        quantity,
        frequency,
        way,
        nhiCode,
        warning,
        days,
        order
        );
    }

    @Override
    public String toString() {
        return "DrugCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (chineseName != null ? "chineseName=" + chineseName + ", " : "") +
                (unit != null ? "unit=" + unit + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (frequency != null ? "frequency=" + frequency + ", " : "") +
                (way != null ? "way=" + way + ", " : "") +
                (nhiCode != null ? "nhiCode=" + nhiCode + ", " : "") +
                (warning != null ? "warning=" + warning + ", " : "") +
                (days != null ? "days=" + days + ", " : "") +
                (order != null ? "order=" + order + ", " : "") +
            "}";
    }

}
