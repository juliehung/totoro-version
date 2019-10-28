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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the NhiDayUpload entity. This class is used in NhiDayUploadResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /nhi-day-uploads?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NhiDayUploadCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter date;

    private LongFilter nhiDayUploadDetailsId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public LongFilter getNhiDayUploadDetailsId() {
        return nhiDayUploadDetailsId;
    }

    public void setNhiDayUploadDetailsId(LongFilter nhiDayUploadDetailsId) {
        this.nhiDayUploadDetailsId = nhiDayUploadDetailsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NhiDayUploadCriteria that = (NhiDayUploadCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(date, that.date) &&
            Objects.equals(nhiDayUploadDetailsId, that.nhiDayUploadDetailsId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        date,
        nhiDayUploadDetailsId
        );
    }

    @Override
    public String toString() {
        return "NhiDayUploadCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (nhiDayUploadDetailsId != null ? "nhiDayUploadDetailsId=" + nhiDayUploadDetailsId + ", " : "") +
            "}";
    }

}
