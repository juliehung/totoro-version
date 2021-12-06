package io.dentall.totoro.service.dto;

import io.github.jhipster.service.filter.*;

import java.io.Serializable;
import java.util.Objects;

public class PatientCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter questionnaireId;

    private BooleanFilter newPatient;

    private InstantFilter deleteDate;

    private InstantFilter createdDate;

    private StringFilter search;

    public LongFilter getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(LongFilter questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public BooleanFilter getNewPatient() {
        return newPatient;
    }

    public void setNewPatient(BooleanFilter newPatient) {
        this.newPatient = newPatient;
    }

    public InstantFilter getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(InstantFilter deleteDate) {
        this.deleteDate = deleteDate;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public StringFilter getSearch() {
        return search;
    }

    public void setSearch(StringFilter search) {
        this.search = search;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PatientCriteria that = (PatientCriteria) o;
        return Objects.equals(questionnaireId, that.questionnaireId) &&
            Objects.equals(newPatient, that.newPatient) &&
            Objects.equals(deleteDate, that.deleteDate) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(search, that.search);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            questionnaireId,
            newPatient,
            deleteDate,
            createdDate,
            search
        );
    }

    @Override
    public String toString() {
        return "PatientCriteria{" +
            (questionnaireId != null ? "questionnaireId=" + questionnaireId + ", " : "") +
            (newPatient != null ? "newPatient=" + newPatient + ", " : "") +
            (deleteDate != null ? "deleteDate=" + deleteDate + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (search != null ? "search=" + search + ", " : "") +
            "}";
    }

}