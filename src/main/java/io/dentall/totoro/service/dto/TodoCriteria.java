package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.dentall.totoro.domain.enumeration.TodoStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the Todo entity. This class is used in TodoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /todos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TodoCriteria implements Serializable {
    /**
     * Class for filtering TodoStatus
     */
    public static class TodoStatusFilter extends Filter<TodoStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TodoStatusFilter status;

    private LocalDateFilter expectedDate;

    private IntegerFilter requiredTreatmentTime;

    private StringFilter note;

    private LongFilter patientId;

    private LongFilter treatmentProcedureId;

    private LongFilter disposalId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TodoStatusFilter getStatus() {
        return status;
    }

    public void setStatus(TodoStatusFilter status) {
        this.status = status;
    }

    public LocalDateFilter getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(LocalDateFilter expectedDate) {
        this.expectedDate = expectedDate;
    }

    public IntegerFilter getRequiredTreatmentTime() {
        return requiredTreatmentTime;
    }

    public void setRequiredTreatmentTime(IntegerFilter requiredTreatmentTime) {
        this.requiredTreatmentTime = requiredTreatmentTime;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public LongFilter getPatientId() {
        return patientId;
    }

    public void setPatientId(LongFilter patientId) {
        this.patientId = patientId;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TodoCriteria that = (TodoCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(expectedDate, that.expectedDate) &&
            Objects.equals(requiredTreatmentTime, that.requiredTreatmentTime) &&
            Objects.equals(note, that.note) &&
            Objects.equals(patientId, that.patientId) &&
            Objects.equals(treatmentProcedureId, that.treatmentProcedureId) &&
            Objects.equals(disposalId, that.disposalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        status,
        expectedDate,
        requiredTreatmentTime,
        note,
        patientId,
        treatmentProcedureId,
        disposalId
        );
    }

    @Override
    public String toString() {
        return "TodoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (expectedDate != null ? "expectedDate=" + expectedDate + ", " : "") +
                (requiredTreatmentTime != null ? "requiredTreatmentTime=" + requiredTreatmentTime + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (patientId != null ? "patientId=" + patientId + ", " : "") +
                (treatmentProcedureId != null ? "treatmentProcedureId=" + treatmentProcedureId + ", " : "") +
                (disposalId != null ? "disposalId=" + disposalId + ", " : "") +
            "}";
    }

}
