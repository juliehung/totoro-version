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

/**
 * Criteria class for the DocNp entity. This class is used in DocNpResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /doc-nps?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DocNpCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter patient;

    private LongFilter patientId;

    private LongFilter esignId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPatient() {
        return patient;
    }

    public void setPatient(StringFilter patient) {
        this.patient = patient;
    }

    public LongFilter getPatientId() {
        return patientId;
    }

    public void setPatientId(LongFilter patientId) {
        this.patientId = patientId;
    }

    public LongFilter getEsignId() {
        return esignId;
    }

    public void setEsignId(LongFilter esignId) {
        this.esignId = esignId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DocNpCriteria that = (DocNpCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(patient, that.patient) &&
            Objects.equals(patientId, that.patientId) &&
            Objects.equals(esignId, that.esignId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        patient,
        patientId,
        esignId
        );
    }

    @Override
    public String toString() {
        return "DocNpCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (patient != null ? "patient=" + patient + ", " : "") +
                (patientId != null ? "patientId=" + patientId + ", " : "") +
                (esignId != null ? "esignId=" + esignId + ", " : "") +
            "}";
    }

}
