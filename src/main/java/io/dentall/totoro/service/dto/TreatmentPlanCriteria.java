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
 * Criteria class for the TreatmentPlan entity. This class is used in TreatmentPlanResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /treatment-plans?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TreatmentPlanCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter activated;

    private StringFilter name;

    private LongFilter treatmentTaskId;

    private LongFilter treatmentId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getActivated() {
        return activated;
    }

    public void setActivated(BooleanFilter activated) {
        this.activated = activated;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public LongFilter getTreatmentTaskId() {
        return treatmentTaskId;
    }

    public void setTreatmentTaskId(LongFilter treatmentTaskId) {
        this.treatmentTaskId = treatmentTaskId;
    }

    public LongFilter getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(LongFilter treatmentId) {
        this.treatmentId = treatmentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TreatmentPlanCriteria that = (TreatmentPlanCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(activated, that.activated) &&
            Objects.equals(name, that.name) &&
            Objects.equals(treatmentTaskId, that.treatmentTaskId) &&
            Objects.equals(treatmentId, that.treatmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        activated,
        name,
        treatmentTaskId,
        treatmentId
        );
    }

    @Override
    public String toString() {
        return "TreatmentPlanCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (activated != null ? "activated=" + activated + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (treatmentTaskId != null ? "treatmentTaskId=" + treatmentTaskId + ", " : "") +
                (treatmentId != null ? "treatmentId=" + treatmentId + ", " : "") +
            "}";
    }

}
