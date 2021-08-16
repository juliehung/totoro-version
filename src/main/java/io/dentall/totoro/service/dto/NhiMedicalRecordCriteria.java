package io.dentall.totoro.service.dto;

import io.dentall.totoro.business.service.NhiRuleCheckSourceType;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the NhiMedicalRecord entity. This class is used in NhiMedicalRecordResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /nhi-medical-records?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NhiMedicalRecordCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter date;

    private StringFilter nhiCategory;

    private StringFilter nhiCode;

    private StringFilter part;

    private StringFilter usage;

    private StringFilter total;

    private StringFilter note;

    private StringFilter days;

    private LongFilter nhiExtendPatientId;

    private NhiRuleCheckSourceType ignoreSourceType;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDate() {
        return date;
    }

    public void setDate(StringFilter date) {
        this.date = date;
    }

    public StringFilter getNhiCategory() {
        return nhiCategory;
    }

    public void setNhiCategory(StringFilter nhiCategory) {
        this.nhiCategory = nhiCategory;
    }

    public StringFilter getNhiCode() {
        return nhiCode;
    }

    public void setNhiCode(StringFilter nhiCode) {
        this.nhiCode = nhiCode;
    }

    public StringFilter getPart() {
        return part;
    }

    public void setPart(StringFilter part) {
        this.part = part;
    }

    public StringFilter getUsage() {
        return usage;
    }

    public void setUsage(StringFilter usage) {
        this.usage = usage;
    }

    public StringFilter getTotal() {
        return total;
    }

    public void setTotal(StringFilter total) {
        this.total = total;
    }

    public StringFilter getNote() {
        return note;
    }

    public void setNote(StringFilter note) {
        this.note = note;
    }

    public StringFilter getDays() {
        return days;
    }

    public void setDays(StringFilter days) {
        this.days = days;
    }

    public LongFilter getNhiExtendPatientId() {
        return nhiExtendPatientId;
    }

    public void setNhiExtendPatientId(LongFilter nhiExtendPatientId) {
        this.nhiExtendPatientId = nhiExtendPatientId;
    }

    public NhiRuleCheckSourceType getIgnoreSourceType() {
        return ignoreSourceType;
    }

    public void setIgnoreSourceType(NhiRuleCheckSourceType ignoreSourceType) {
        this.ignoreSourceType = ignoreSourceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NhiMedicalRecordCriteria that = (NhiMedicalRecordCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(nhiCategory, that.nhiCategory) &&
            Objects.equals(nhiCode, that.nhiCode) &&
            Objects.equals(part, that.part) &&
            Objects.equals(usage, that.usage) &&
            Objects.equals(total, that.total) &&
            Objects.equals(note, that.note) &&
            Objects.equals(days, that.days) &&
            Objects.equals(nhiExtendPatientId, that.nhiExtendPatientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        date,
        nhiCategory,
        nhiCode,
        part,
        usage,
        total,
        note,
        days,
        nhiExtendPatientId
        );
    }

    @Override
    public String toString() {
        return "NhiMedicalRecordCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (nhiCategory != null ? "nhiCategory=" + nhiCategory + ", " : "") +
                (nhiCode != null ? "nhiCode=" + nhiCode + ", " : "") +
                (part != null ? "part=" + part + ", " : "") +
                (usage != null ? "usage=" + usage + ", " : "") +
                (total != null ? "total=" + total + ", " : "") +
                (note != null ? "note=" + note + ", " : "") +
                (days != null ? "days=" + days + ", " : "") +
                (nhiExtendPatientId != null ? "nhiExtendPatientId=" + nhiExtendPatientId + ", " : "") +
            "}";
    }

}
