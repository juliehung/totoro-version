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
import io.github.jhipster.service.filter.InstantFilter;

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

    private InstantFilter completedDate;

    private DoubleFilter price;

    private StringFilter nhiCategory;

    private StringFilter nhiDescription;

    private StringFilter nhiIcd10Cm;

    private LongFilter nhiProcedureId;

    private LongFilter treatmentTaskId;

    private LongFilter procedureId;

    private LongFilter appointmentId;

    private LongFilter toothId;

    private LongFilter todoId;

    private LongFilter disposalId;

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

    public InstantFilter getCompletedDate() {
        return completedDate;
    }

    public void setCompletedDate(InstantFilter completedDate) {
        this.completedDate = completedDate;
    }

    public DoubleFilter getPrice() {
        return price;
    }

    public void setPrice(DoubleFilter price) {
        this.price = price;
    }

    public StringFilter getNhiCategory() {
        return nhiCategory;
    }

    public void setNhiCategory(StringFilter nhiCategory) {
        this.nhiCategory = nhiCategory;
    }

    public StringFilter getNhiDescription() {
        return nhiDescription;
    }

    public void setNhiDescription(StringFilter nhiDescription) {
        this.nhiDescription = nhiDescription;
    }

    public StringFilter getNhiIcd10Cm() {
        return nhiIcd10Cm;
    }

    public void setNhiIcd10Cm(StringFilter nhiIcd10Cm) {
        this.nhiIcd10Cm = nhiIcd10Cm;
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

    public LongFilter getToothId() {
        return toothId;
    }

    public void setToothId(LongFilter toothId) {
        this.toothId = toothId;
    }

    public LongFilter getTodoId() {
        return todoId;
    }

    public void setTodoId(LongFilter todoId) {
        this.todoId = todoId;
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
        final TreatmentProcedureCriteria that = (TreatmentProcedureCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(status, that.status) &&
            Objects.equals(quantity, that.quantity) &&
            Objects.equals(total, that.total) &&
            Objects.equals(note, that.note) &&
            Objects.equals(completedDate, that.completedDate) &&
            Objects.equals(price, that.price) &&
            Objects.equals(nhiCategory, that.nhiCategory) &&
            Objects.equals(nhiDescription, that.nhiDescription) &&
            Objects.equals(nhiIcd10Cm, that.nhiIcd10Cm) &&
            Objects.equals(nhiProcedureId, that.nhiProcedureId) &&
            Objects.equals(treatmentTaskId, that.treatmentTaskId) &&
            Objects.equals(procedureId, that.procedureId) &&
            Objects.equals(appointmentId, that.appointmentId) &&
            Objects.equals(toothId, that.toothId) &&
            Objects.equals(todoId, that.todoId) &&
            Objects.equals(disposalId, that.disposalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        status,
        quantity,
        total,
        note,
        completedDate,
        price,
        nhiCategory,
        nhiDescription,
        nhiIcd10Cm,
        nhiProcedureId,
        treatmentTaskId,
        procedureId,
        appointmentId,
        toothId,
        todoId,
        disposalId
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
                (completedDate != null ? "completedDate=" + completedDate + ", " : "") +
                (price != null ? "price=" + price + ", " : "") +
                (nhiCategory != null ? "nhiCategory=" + nhiCategory + ", " : "") +
                (nhiDescription != null ? "nhiDescription=" + nhiDescription + ", " : "") +
                (nhiIcd10Cm != null ? "nhiIcd10Cm=" + nhiIcd10Cm + ", " : "") +
                (nhiProcedureId != null ? "nhiProcedureId=" + nhiProcedureId + ", " : "") +
                (treatmentTaskId != null ? "treatmentTaskId=" + treatmentTaskId + ", " : "") +
                (procedureId != null ? "procedureId=" + procedureId + ", " : "") +
                (appointmentId != null ? "appointmentId=" + appointmentId + ", " : "") +
                (toothId != null ? "toothId=" + toothId + ", " : "") +
                (todoId != null ? "todoId=" + todoId + ", " : "") +
                (disposalId != null ? "disposalId=" + disposalId + ", " : "") +
            "}";
    }

}
