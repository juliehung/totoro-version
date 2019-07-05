package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.dentall.totoro.domain.enumeration.SourceType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the Esign entity. This class is used in EsignResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /esigns?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EsignCriteria implements Serializable {
    /**
     * Class for filtering SourceType
     */
    public static class SourceTypeFilter extends Filter<SourceType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter patientId;

    private InstantFilter createTime;

    private InstantFilter updateTime;

    private SourceTypeFilter sourceType;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getPatientId() {
        return patientId;
    }

    public void setPatientId(LongFilter patientId) {
        this.patientId = patientId;
    }

    public InstantFilter getCreateTime() {
        return createTime;
    }

    public void setCreateTime(InstantFilter createTime) {
        this.createTime = createTime;
    }

    public InstantFilter getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(InstantFilter updateTime) {
        this.updateTime = updateTime;
    }

    public SourceTypeFilter getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceTypeFilter sourceType) {
        this.sourceType = sourceType;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EsignCriteria that = (EsignCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(patientId, that.patientId) &&
            Objects.equals(createTime, that.createTime) &&
            Objects.equals(updateTime, that.updateTime) &&
            Objects.equals(sourceType, that.sourceType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        patientId,
        createTime,
        updateTime,
        sourceType
        );
    }

    @Override
    public String toString() {
        return "EsignCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (patientId != null ? "patientId=" + patientId + ", " : "") +
                (createTime != null ? "createTime=" + createTime + ", " : "") +
                (updateTime != null ? "updateTime=" + updateTime + ", " : "") +
                (sourceType != null ? "sourceType=" + sourceType + ", " : "") +
            "}";
    }

}
