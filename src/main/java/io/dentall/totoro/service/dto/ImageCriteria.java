package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;

import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Image entity. This class is used in ImageResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /images?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ImageCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter filePath;

    private StringFilter fileName;

    private StringFilter fetchUrl;

    private LongFilter groupId;

    private StringFilter size;

    private LongFilter patientId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getFilePath() {
        return filePath;
    }

    public void setFilePath(StringFilter filePath) {
        this.filePath = filePath;
    }

    public StringFilter getFileName() {
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public StringFilter getFetchUrl() {
        return fetchUrl;
    }

    public void setFetchUrl(StringFilter fetchUrl) {
        this.fetchUrl = fetchUrl;
    }

    public LongFilter getGroupId() {
        return groupId;
    }

    public void setGroupId(LongFilter groupId) {
        this.groupId = groupId;
    }

    public StringFilter getSize() {
        return size;
    }

    public void setSize(StringFilter size) {
        this.size = size;
    }

    public LongFilter getPatientId() {
        return patientId;
    }

    public void setPatientId(LongFilter patientId) {
        this.patientId = patientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ImageCriteria that = (ImageCriteria) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(filePath, that.filePath) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(fetchUrl, that.fetchUrl) &&
            Objects.equals(groupId, that.groupId) &&
            Objects.equals(size, that.size) &&
            Objects.equals(patientId, that.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            filePath,
            fileName,
            fetchUrl,
            groupId,
            size,
            patientId
        );
    }

    @Override
    public String toString() {
        return "ImageCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (filePath != null ? "filePath=" + filePath + ", " : "") +
            (fileName != null ? "fileName=" + fileName + ", " : "") +
            (fetchUrl != null ? "fetchUrl=" + fetchUrl + ", " : "") +
            (groupId != null ? "groupId=" + groupId + ", " : "") +
            (size != null ? "size=" + size + ", " : "") +
            (patientId != null ? "patientId=" + patientId + ", " : "") +
            "}";
    }

}
