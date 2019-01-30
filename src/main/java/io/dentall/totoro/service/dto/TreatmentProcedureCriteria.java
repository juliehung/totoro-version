package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.dentall.totoro.domain.enumeration.TreatmentProcedureStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the TreatmentProcedure entity. This class is used in TreatmentProcedureResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /treatment-procedures?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TreatmentProcedureCriteria implements Serializable {
    /**
     * Class for filtering TreatmentProcedureStatus
     */
    public static class TreatmentProcedureStatusFilter extends Filter<TreatmentProcedureStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TreatmentProcedureStatusFilter status;

    private IntegerFilter quantity;

    private DoubleFilter total;

    private StringFilter note;

    private LongFilter nhiProcedureId;

    private LongFilter treatmentTaskId;

    private LongFilter procedureId;

    private LongFilter appointmentId;

    private LongFilter registrationId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TreatmentProcedureStatusFilter getStatus() {
        return status;
    }

    public void setStatus(TreatmentProcedureStatusFilter status) {
        this.status = status;
    }

    public IntegerFilter getQuantity() {
        return quantity;
    }

    public void setQuantity(IntegerFilter quantity) {
        this.quantity = quantity;
    }

    public DoubleFilter getTotal() {
        return total;
    }

    public void setTotal(DoubleFilter total) {
        this.total = total;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public LongFilter getNhiProcedureId() {
        return nhiProcedureId;
    }

    public void setNhiProcedureId(LongFilter nhiProcedureId) {
        this.nhiProcedureId = nhiProcedureId;
    }

    public LongFilter getTreatmentTaskId() {
        return treatmentTaskId;
    }

    public void setTreatmentTaskId(LongFilter treatmentTaskId) {
        this.treatmentTaskId = treatmentTaskId;
    }

    public LongFilter getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(LongFilter procedureId) {
        this.procedureId = procedureId;
    }

    public LongFilter getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(LongFilter appointmentId) {
        this.appointmentId = appointmentId;
    }

    public LongFilter getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(LongFilter registrationId) {
        this.registrationId = registrationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TreatmentProcedureCriteria that = (TreatmentProcedureCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(total, that.total) &&
            Objects.equals(note, that.note) &&
            Objects.equals(nhiProcedureId, that.nhiProcedureId) &&
            Objects.equals(treatmentTaskId, that.treatmentTaskId) &&
            Objects.equals(procedureId, that.procedureId) &&
            Objects.equals(appointmentId, that.appointmentId) &&
            Objects.equals(registrationId, that.registrationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        status,
        quantity,
        total,
        note,
        nhiProcedureId,
        treatmentTaskId,
        procedureId,
        appointmentId,
        registrationId
        );
    }

    @Override
    public String toString() {
        return "TreatmentProcedureCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (quantity != null ? "quantity=" + quantity + ", " : "") +
                (total != null ? "total=" + total + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (nhiProcedureId != null ? "nhiProcedureId=" + nhiProcedureId + ", " : "") +
                (treatmentTaskId != null ? "treatmentTaskId=" + treatmentTaskId + ", " : "") +
                (procedureId != null ? "procedureId=" + procedureId + ", " : "") +
                (appointmentId != null ? "appointmentId=" + appointmentId + ", " : "") +
                (registrationId != null ? "registrationId=" + registrationId + ", " : "") +
            "}";
    }

}
