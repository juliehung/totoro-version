package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.enumeration.HomePageCoverSourceTable;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;

import java.io.Serializable;
import java.util.Objects;

/**
 * Criteria class for the HomePageCover entity. This class is used in HomePageCoverResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /home-page-covers?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class HomePageCoverCriteria implements Serializable {

    public static class HomePageCoverSourceTableFilter extends Filter<HomePageCoverSourceTable> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter patientId;

    private HomePageCoverSourceTableFilter sourceTable;

    private LongFilter sourceId;

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

    public HomePageCoverSourceTableFilter getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(HomePageCoverSourceTableFilter sourceTable) {
        this.sourceTable = sourceTable;
    }

    public LongFilter getSourceId() {
        return sourceId;
    }

    public void setSourceId(LongFilter sourceId) {
        this.sourceId = sourceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final HomePageCoverCriteria that = (HomePageCoverCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(patientId, that.patientId) &&
            Objects.equals(sourceTable, that.sourceTable) &&
            Objects.equals(sourceId, that.sourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        patientId,
        sourceTable,
        sourceId
        );
    }

    @Override
    public String toString() {
        return "HomePageCoverCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (patientId != null ? "patientId=" + patientId + ", " : "") +
                (sourceTable != null ? "sourceTable=" + sourceTable + ", " : "") +
                (sourceId != null ? "sourceId=" + sourceId + ", " : "") +
            "}";
    }

}
