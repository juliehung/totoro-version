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
 * Criteria class for the Tooth entity. This class is used in ToothResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /teeth?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ToothCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter position;

    private StringFilter surface;

    private StringFilter status;

    private LongFilter treatmentProcedureId;

    private LongFilter disposalId;

    private LongFilter patientId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPosition() {
        return position;
    }

    public void setPosition(StringFilter position) {
        this.position = position;
    }

    public StringFilter getSurface() {
        return surface;
    }

    public void setSurface(StringFilter surface) {
        this.surface = surface;
    }

    public StringFilter getStatus() {
        return status;
    }

    public void setStatus(StringFilter status) {
        this.status = status;
    }

    public LongFilter getTreatmentProcedureId() {
        return treatmentProcedureId;
    }

    public void setTreatmentProcedureId(LongFilter treatmentProcedureId) {
        this.treatmentProcedureId = treatmentProcedureId;
    }

    public LongFilter getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(LongFilter disposalId) {
        this.disposalId = disposalId;
    }

    public LongFilter getPatientId() {
        return patientId;
    }

    public void setPatientId(LongFilter patientId) {
        this.patientId = patientId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ToothCriteria that = (ToothCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(position, that.position) &&
            Objects.equals(surface, that.surface) &&
            Objects.equals(status, that.status) &&
            Objects.equals(treatmentProcedureId, that.treatmentProcedureId) &&
            Objects.equals(disposalId, that.disposalId) &&
            Objects.equals(patientId, that.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        position,
        surface,
        status,
        treatmentProcedureId,
        disposalId,
        patientId
        );
    }

    @Override
    public String toString() {
        return "ToothCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (position != null ? "position=" + position + ", " : "") +
                (surface != null ? "surface=" + surface + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (treatmentProcedureId != null ? "treatmentProcedureId=" + treatmentProcedureId + ", " : "") +
                (disposalId != null ? "disposalId=" + disposalId + ", " : "") +
                (patientId != null ? "patientId=" + patientId + ", " : "") +
            "}";
    }

}
