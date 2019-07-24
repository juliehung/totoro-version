package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.dentall.totoro.domain.enumeration.NhiMonthDeclarationType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the NhiMonthDeclarationDetails entity. This class is used in NhiMonthDeclarationDetailsResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /nhi-month-declaration-details?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NhiMonthDeclarationDetailsCriteria implements Serializable {
    /**
     * Class for filtering NhiMonthDeclarationType
     */
    public static class NhiMonthDeclarationTypeFilter extends Filter<NhiMonthDeclarationType> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private NhiMonthDeclarationTypeFilter type;

    private StringFilter way;

    private IntegerFilter caseTotal;

    private IntegerFilter pointTotal;

    private IntegerFilter outPatientPoint;

    private IntegerFilter preventiveCaseTotal;

    private IntegerFilter preventivePointTotal;

    private IntegerFilter generalCaseTotal;

    private IntegerFilter generalPointTotal;

    private IntegerFilter professionalCaseTotal;

    private IntegerFilter professionalPointTotal;

    private IntegerFilter partialCaseTotal;

    private IntegerFilter partialPointTotal;

    private StringFilter file;

    private InstantFilter uploadTime;

    private LongFilter nhiMonthDeclarationId;

    private StringFilter localId;

    private StringFilter nhiId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public NhiMonthDeclarationTypeFilter getType() {
        return type;
    }

    public void setType(NhiMonthDeclarationTypeFilter type) {
        this.type = type;
    }

    public StringFilter getWay() {
        return way;
    }

    public void setWay(StringFilter way) {
        this.way = way;
    }

    public IntegerFilter getCaseTotal() {
        return caseTotal;
    }

    public void setCaseTotal(IntegerFilter caseTotal) {
        this.caseTotal = caseTotal;
    }

    public IntegerFilter getPointTotal() {
        return pointTotal;
    }

    public void setPointTotal(IntegerFilter pointTotal) {
        this.pointTotal = pointTotal;
    }

    public IntegerFilter getOutPatientPoint() {
        return outPatientPoint;
    }

    public void setOutPatientPoint(IntegerFilter outPatientPoint) {
        this.outPatientPoint = outPatientPoint;
    }

    public IntegerFilter getPreventiveCaseTotal() {
        return preventiveCaseTotal;
    }

    public void setPreventiveCaseTotal(IntegerFilter preventiveCaseTotal) {
        this.preventiveCaseTotal = preventiveCaseTotal;
    }

    public IntegerFilter getPreventivePointTotal() {
        return preventivePointTotal;
    }

    public void setPreventivePointTotal(IntegerFilter preventivePointTotal) {
        this.preventivePointTotal = preventivePointTotal;
    }

    public IntegerFilter getGeneralCaseTotal() {
        return generalCaseTotal;
    }

    public void setGeneralCaseTotal(IntegerFilter generalCaseTotal) {
        this.generalCaseTotal = generalCaseTotal;
    }

    public IntegerFilter getGeneralPointTotal() {
        return generalPointTotal;
    }

    public void setGeneralPointTotal(IntegerFilter generalPointTotal) {
        this.generalPointTotal = generalPointTotal;
    }

    public IntegerFilter getProfessionalCaseTotal() {
        return professionalCaseTotal;
    }

    public void setProfessionalCaseTotal(IntegerFilter professionalCaseTotal) {
        this.professionalCaseTotal = professionalCaseTotal;
    }

    public IntegerFilter getProfessionalPointTotal() {
        return professionalPointTotal;
    }

    public void setProfessionalPointTotal(IntegerFilter professionalPointTotal) {
        this.professionalPointTotal = professionalPointTotal;
    }

    public IntegerFilter getPartialCaseTotal() {
        return partialCaseTotal;
    }

    public void setPartialCaseTotal(IntegerFilter partialCaseTotal) {
        this.partialCaseTotal = partialCaseTotal;
    }

    public IntegerFilter getPartialPointTotal() {
        return partialPointTotal;
    }

    public void setPartialPointTotal(IntegerFilter partialPointTotal) {
        this.partialPointTotal = partialPointTotal;
    }

    public StringFilter getFile() {
        return file;
    }

    public void setFile(StringFilter file) {
        this.file = file;
    }

    public InstantFilter getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(InstantFilter uploadTime) {
        this.uploadTime = uploadTime;
    }

    public StringFilter getLocalId() {
        return localId;
    }

    public void setLocalId(StringFilter localId) {
        this.localId = localId;
    }

    public StringFilter getNhiId() {
        return nhiId;
    }

    public void setNhiId(StringFilter nhiId) {
        this.nhiId = nhiId;
    }

    public LongFilter getNhiMonthDeclarationId() {
        return nhiMonthDeclarationId;
    }

    public void setNhiMonthDeclarationId(LongFilter nhiMonthDeclarationId) {
        this.nhiMonthDeclarationId = nhiMonthDeclarationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final NhiMonthDeclarationDetailsCriteria that = (NhiMonthDeclarationDetailsCriteria) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(way, that.way) &&
            Objects.equals(caseTotal, that.caseTotal) &&
            Objects.equals(pointTotal, that.pointTotal) &&
            Objects.equals(outPatientPoint, that.outPatientPoint) &&
            Objects.equals(preventiveCaseTotal, that.preventiveCaseTotal) &&
            Objects.equals(preventivePointTotal, that.preventivePointTotal) &&
            Objects.equals(generalCaseTotal, that.generalCaseTotal) &&
            Objects.equals(generalPointTotal, that.generalPointTotal) &&
            Objects.equals(professionalCaseTotal, that.professionalCaseTotal) &&
            Objects.equals(professionalPointTotal, that.professionalPointTotal) &&
            Objects.equals(partialCaseTotal, that.partialCaseTotal) &&
            Objects.equals(partialPointTotal, that.partialPointTotal) &&
            Objects.equals(file, that.file) &&
            Objects.equals(uploadTime, that.uploadTime) &&
            Objects.equals(localId, that.localId) &&
            Objects.equals(nhiId, that.nhiId) &&
            Objects.equals(nhiMonthDeclarationId, that.nhiMonthDeclarationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            type,
            way,
            caseTotal,
            pointTotal,
            outPatientPoint,
            preventiveCaseTotal,
            preventivePointTotal,
            generalCaseTotal,
            generalPointTotal,
            professionalCaseTotal,
            professionalPointTotal,
            partialCaseTotal,
            partialPointTotal,
            file,
            uploadTime,
            localId,
            nhiId,
            nhiMonthDeclarationId
        );
    }

    @Override
    public String toString() {
        return "NhiMonthDeclarationDetailsCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (way != null ? "way=" + way + ", " : "") +
            (caseTotal != null ? "caseTotal=" + caseTotal + ", " : "") +
            (pointTotal != null ? "pointTotal=" + pointTotal + ", " : "") +
            (outPatientPoint != null ? "outPatientPoint=" + outPatientPoint + ", " : "") +
            (preventiveCaseTotal != null ? "preventiveCaseTotal=" + preventiveCaseTotal + ", " : "") +
            (preventivePointTotal != null ? "preventivePointTotal=" + preventivePointTotal + ", " : "") +
            (generalCaseTotal != null ? "generalCaseTotal=" + generalCaseTotal + ", " : "") +
            (generalPointTotal != null ? "generalPointTotal=" + generalPointTotal + ", " : "") +
            (professionalCaseTotal != null ? "professionalCaseTotal=" + professionalCaseTotal + ", " : "") +
            (professionalPointTotal != null ? "professionalPointTotal=" + professionalPointTotal + ", " : "") +
            (partialCaseTotal != null ? "partialCaseTotal=" + partialCaseTotal + ", " : "") +
            (partialPointTotal != null ? "partialPointTotal=" + partialPointTotal + ", " : "") +
            (file != null ? "file=" + file + ", " : "") +
            (uploadTime != null ? "uploadTime=" + uploadTime + ", " : "") +
            (localId != null ? "localId=" + localId + ", " : "") +
            (nhiId != null ? "nhiId=" + nhiId + ", " : "") +
            (nhiMonthDeclarationId != null ? "nhiMonthDeclarationId=" + nhiMonthDeclarationId + ", " : "") +
            "}";
    }

}
