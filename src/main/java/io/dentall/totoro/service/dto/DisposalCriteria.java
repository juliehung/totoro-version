package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.enumeration.DisposalStatus;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the Disposal entity. This class is used in DisposalResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /disposals?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DisposalCriteria implements Serializable {
    /**
     * Class for filtering DisposalStatus
     */
    public static class DisposalStatusFilter extends Filter<DisposalStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DisposalStatusFilter status;

    private DoubleFilter total;

    private LongFilter treatmentProcedureId;

    private LongFilter prescriptionId;

    private LongFilter todoId;

    private InstantFilter dateTime;

    private InstantFilter dateTimeEnd;

    private LongFilter registrationId;

    private LongFilter patientId;

    private InstantFilter createdDate;

    private LongFilter toothId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DisposalStatusFilter getStatus() {
        return status;
    }

    public void setStatus(DisposalStatusFilter status) {
        this.status = status;
    }

    public DoubleFilter getTotal() {
        return total;
    }

    public void setTotal(DoubleFilter total) {
        this.total = total;
    }

    public InstantFilter getDateTime() {
        return dateTime;
    }

    public void setDateTime(InstantFilter dateTime) {
        this.dateTime = dateTime;
    }

    public LongFilter getTreatmentProcedureId() {
        return treatmentProcedureId;
    }

    public void setTreatmentProcedureId(LongFilter treatmentProcedureId) {
        this.treatmentProcedureId = treatmentProcedureId;
    }

    public LongFilter getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(LongFilter prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public LongFilter getTodoId() {
        return todoId;
    }

    public void setTodoId(LongFilter todoId) {
        this.todoId = todoId;
    }

    public LongFilter getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(LongFilter registrationId) {
        this.registrationId = registrationId;
    }

    public LongFilter getPatientId() {
        return patientId;
    }

    public void setPatientId(LongFilter patientId) {
        this.patientId = patientId;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public LongFilter getToothId() {
        return toothId;
    }

    public void setToothId(LongFilter toothId) {
        this.toothId = toothId;
    }

    public InstantFilter getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(InstantFilter dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DisposalCriteria that = (DisposalCriteria) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(total, that.total) &&
            Objects.equals(dateTime, that.dateTime) &&
            Objects.equals(treatmentProcedureId, that.treatmentProcedureId) &&
            Objects.equals(prescriptionId, that.prescriptionId) &&
            Objects.equals(todoId, that.todoId) &&
            Objects.equals(registrationId, that.registrationId) &&
            Objects.equals(patientId, that.patientId) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(toothId, that.toothId) &&
            Objects.equals(dateTimeEnd, that.dateTimeEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            status,
            total,
            dateTime,
            treatmentProcedureId,
            prescriptionId,
            todoId,
            registrationId,
            patientId,
            createdDate,
            toothId,
            dateTimeEnd
        );
    }

    @Override
    public String toString() {
        return "DisposalCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (status != null ? "status=" + status + ", " : "") +
            (total != null ? "total=" + total + ", " : "") +
            (dateTime != null ? "dateTime=" + dateTime + ", " : "") +
            (treatmentProcedureId != null ? "treatmentProcedureId=" + treatmentProcedureId + ", " : "") +
            (prescriptionId != null ? "prescriptionId=" + prescriptionId + ", " : "") +
            (todoId != null ? "todoId=" + todoId + ", " : "") +
            (registrationId != null ? "registrationId=" + registrationId + ", " : "") +
            (patientId != null ? "patientId=" + patientId + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (toothId != null ? "toothId=" + toothId + ", " : "") +
            (dateTimeEnd != null ? "dateTimeEnd=" + dateTimeEnd + ", " : "") +
            "}";
    }

    public boolean isOnlyPatientId() {
        return this.patientId != null &&
            this.id == null &&
            this.status == null &&
            this.total == null &&
            this.dateTime == null &&
            this.treatmentProcedureId == null &&
            this.prescriptionId == null &&
            this.todoId == null &&
            this.registrationId == null &&
            this.createdDate == null &&
            this.toothId == null &&
            this.dateTimeEnd == null;
    }
}