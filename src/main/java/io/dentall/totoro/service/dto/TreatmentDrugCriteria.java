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
 * Criteria class for the TreatmentDrug entity. This class is used in TreatmentDrugResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /treatment-drugs?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TreatmentDrugCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private IntegerFilter day;

    private StringFilter frequency;

    private StringFilter way;

    private DoubleFilter quantity;

    private LongFilter prescriptionId;

    private LongFilter drugId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public IntegerFilter getDay() {
        return day;
    }

    public void setDay(IntegerFilter day) {
        this.day = day;
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

    public DoubleFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(DoubleFilter quantity) {
        this.quantity = quantity;
    }

    public LongFilter getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(LongFilter prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public LongFilter getDrugId() {
        return drugId;
    }

    public void setDrugId(LongFilter drugId) {
        this.drugId = drugId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TreatmentDrugCriteria that = (TreatmentDrugCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(day, that.day) &&
            Objects.equals(frequency, that.frequency) &&
            Objects.equals(way, that.way) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(prescriptionId, that.prescriptionId) &&
            Objects.equals(drugId, that.drugId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        day,
        frequency,
        way,
        quantity,
        prescriptionId,
        drugId
        );
    }

    @Override
    public String toString() {
        return "TreatmentDrugCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (day != null ? "day=" + day + ", " : "") +
                (frequency != null ? "frequency=" + frequency + ", " : "") +
                (way != null ? "way=" + way + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (prescriptionId != null ? "prescriptionId=" + prescriptionId + ", " : "") +
                (drugId != null ? "drugId=" + drugId + ", " : "") +
            "}";
    }

}