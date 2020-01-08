package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.InstantFilter;

/**
 * Criteria class for the NhiAccumulatedMedicalRecord entity. This class is used in NhiAccumulatedMedicalRecordResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /nhi-accumulated-medical-records?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class NhiAccumulatedMedicalRecordCriteria implements Serializable {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter medicalCategory;

    private StringFilter newbornMedicalTreatmentNote;

    private StringFilter date;

    private StringFilter cardFillingNote;

    private StringFilter seqNumber;

    private StringFilter medicalInstitutionCode;

    private LongFilter patientId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getMedicalCategory() {
        return medicalCategory;
    }

    public void setMedicalCategory(StringFilter medicalCategory) {
        this.medicalCategory = medicalCategory;
    }

    public StringFilter getNewbornMedicalTreatmentNote() {
        return newbornMedicalTreatmentNote;
    }

    public void setNewbornMedicalTreatmentNote(StringFilter newbornMedicalTreatmentNote) {
        this.newbornMedicalTreatmentNote = newbornMedicalTreatmentNote;
    }

    public StringFilter getDate() {
        return date;
    }

    public void setDate(StringFilter date) {
        this.date = date;
    }

    public StringFilter getCardFillingNote() {
        return cardFillingNote;
    }

    public void setCardFillingNote(StringFilter cardFillingNote) {
        this.cardFillingNote = cardFillingNote;
    }

    public StringFilter getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(StringFilter seqNumber) {
        this.seqNumber = seqNumber;
    }

    public StringFilter getMedicalInstitutionCode() {
        return medicalInstitutionCode;
    }

    public void setMedicalInstitutionCode(StringFilter medicalInstitutionCode) {
        this.medicalInstitutionCode = medicalInstitutionCode;
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
        final NhiAccumulatedMedicalRecordCriteria that = (NhiAccumulatedMedicalRecordCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(medicalCategory, that.medicalCategory) &&
            Objects.equals(newbornMedicalTreatmentNote, that.newbornMedicalTreatmentNote) &&
            Objects.equals(date, that.date) &&
            Objects.equals(cardFillingNote, that.cardFillingNote) &&
            Objects.equals(seqNumber, that.seqNumber) &&
            Objects.equals(medicalInstitutionCode, that.medicalInstitutionCode) &&
            Objects.equals(patientId, that.patientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        medicalCategory,
        newbornMedicalTreatmentNote,
        date,
        cardFillingNote,
        seqNumber,
        medicalInstitutionCode,
        patientId
        );
    }

    @Override
    public String toString() {
        return "NhiAccumulatedMedicalRecordCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (medicalCategory != null ? "medicalCategory=" + medicalCategory + ", " : "") +
                (newbornMedicalTreatmentNote != null ? "newbornMedicalTreatmentNote=" + newbornMedicalTreatmentNote + ", " : "") +
                (date != null ? "date=" + date + ", " : "") +
                (cardFillingNote != null ? "cardFillingNote=" + cardFillingNote + ", " : "") +
                (seqNumber != null ? "seqNumber=" + seqNumber + ", " : "") +
                (medicalInstitutionCode != null ? "medicalInstitutionCode=" + medicalInstitutionCode + ", " : "") +
                (patientId != null ? "patientId=" + patientId + ", " : "") +
            "}";
    }

}
