package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.dentall.totoro.domain.enumeration.NhiDayUploadDetailType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the NhiDayUploadDetails entity. This class is used in NhiDayUploadDetailsResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /nhi-day-upload-details?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NhiDayUploadDetailsCriteria implements Serializable {
    /**
     * Class for filtering NhiDayUploadDetailType
     */
    public static class NhiDayUploadDetailTypeFilter extends Filter<NhiDayUploadDetailType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private NhiDayUploadDetailTypeFilter type;

    private LongFilter nhiDayUploadId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public NhiDayUploadDetailTypeFilter getType() {
        return type;
    }

    public void setType(NhiDayUploadDetailTypeFilter type) {
        this.type = type;
    }

    public LongFilter getNhiDayUploadId() {
        return nhiDayUploadId;
    }

    public void setNhiDayUploadId(LongFilter nhiDayUploadId) {
        this.nhiDayUploadId = nhiDayUploadId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NhiDayUploadDetailsCriteria that = (NhiDayUploadDetailsCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(nhiDayUploadId, that.nhiDayUploadId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        type,
        nhiDayUploadId
        );
    }

    @Override
    public String toString() {
        return "NhiDayUploadDetailsCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (type != null ? "type=" + type + ", " : "") +
                (nhiDayUploadId != null ? "nhiDayUploadId=" + nhiDayUploadId + ", " : "") +
            "}";
    }

}
