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
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the Ledger entity. This class is used in LedgerResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /ledgers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LedgerCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private DoubleFilter amount;

    private DoubleFilter charge;

    private DoubleFilter arrears;

    private StringFilter note;

    private StringFilter doctor;

    private LongFilter gid;

    private StringFilter displayName;

    private InstantFilter createdDate;

    private StringFilter createdBy;

    private InstantFilter lastModifiedDate;

    private StringFilter lastModifiedBy;

    private LongFilter treatmentPlanId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public DoubleFilter getAmount() {
        return amount;
    }

    public void setAmount(DoubleFilter amount) {
        this.amount = amount;
    }

    public DoubleFilter getCharge() {
        return charge;
    }

    public void setCharge(DoubleFilter charge) {
        this.charge = charge;
    }

    public DoubleFilter getArrears() {
        return arrears;
    }

    public void setArrears(DoubleFilter arrears) {
        this.arrears = arrears;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public StringFilter getDoctor() {
        return doctor;
    }

    public void setDoctor(StringFilter doctor) {
        this.doctor = doctor;
    }

    public LongFilter getGid() {
        return gid;
    }

    public void setGid(LongFilter gid) {
        this.gid = gid;
    }

    public StringFilter getDisplayName() {
        return displayName;
    }

    public void setDisplayName(StringFilter displayName) {
        this.displayName = displayName;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(InstantFilter lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public StringFilter getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(StringFilter lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LongFilter getTreatmentPlanId() {
        return treatmentPlanId;
    }

    public void setTreatmentPlanId(LongFilter treatmentPlanId) {
        this.treatmentPlanId = treatmentPlanId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LedgerCriteria that = (LedgerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(amount, that.amount) &&
            Objects.equals(charge, that.charge) &&
            Objects.equals(arrears, that.arrears) &&
            Objects.equals(note, that.note) &&
            Objects.equals(doctor, that.doctor) &&
            Objects.equals(gid, that.gid) &&
            Objects.equals(displayName, that.displayName) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(lastModifiedDate, that.lastModifiedDate) &&
            Objects.equals(lastModifiedBy, that.lastModifiedBy) &&
            Objects.equals(treatmentPlanId, that.treatmentPlanId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        amount,
        charge,
        arrears,
        note,
        doctor,
        gid,
        displayName,
        createdDate,
        createdBy,
        lastModifiedDate,
        lastModifiedBy,
        treatmentPlanId
        );
    }

    @Override
    public String toString() {
        return "LedgerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (amount != null ? "amount=" + amount + ", " : "") +
                (charge != null ? "charge=" + charge + ", " : "") +
                (arrears != null ? "arrears=" + arrears + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (doctor != null ? "doctor=" + doctor + ", " : "") +
                (gid != null ? "gid=" + gid + ", " : "") +
                (displayName != null ? "displayName=" + displayName + ", " : "") +
                (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
                (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
                (lastModifiedDate != null ? "lastModifiedDate=" + lastModifiedDate + ", " : "") +
                (lastModifiedBy != null ? "lastModifiedBy=" + lastModifiedBy + ", " : "") +
                (treatmentPlanId != null ? "treatmentPlanId=" + treatmentPlanId + ", " : "") +
            "}";
    }

}
