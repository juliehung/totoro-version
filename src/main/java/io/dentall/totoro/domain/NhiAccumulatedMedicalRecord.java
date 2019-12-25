package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A NhiAccumulatedMedicalRecord.
 */
@Entity
@Table(name = "nhi_accumulated_medical_record")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NhiAccumulatedMedicalRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "medical_category")
    private String medicalCategory;

    @Column(name = "newborn_medical_treatment_note")
    private String newbornMedicalTreatmentNote;

    @Column(name = "jhi_date")
    private Instant date;

    @Column(name = "card_filling_note")
    private String cardFillingNote;

    @Column(name = "seq_number")
    private String seqNumber;

    @Column(name = "medical_institution_code")
    private String medicalInstitutionCode;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("")
    private Patient patient;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicalCategory() {
        return medicalCategory;
    }

    public NhiAccumulatedMedicalRecord medicalCategory(String medicalCategory) {
        this.medicalCategory = medicalCategory;
        return this;
    }

    public void setMedicalCategory(String medicalCategory) {
        this.medicalCategory = medicalCategory;
    }

    public String getNewbornMedicalTreatmentNote() {
        return newbornMedicalTreatmentNote;
    }

    public NhiAccumulatedMedicalRecord newbornMedicalTreatmentNote(String newbornMedicalTreatmentNote) {
        this.newbornMedicalTreatmentNote = newbornMedicalTreatmentNote;
        return this;
    }

    public void setNewbornMedicalTreatmentNote(String newbornMedicalTreatmentNote) {
        this.newbornMedicalTreatmentNote = newbornMedicalTreatmentNote;
    }

    public Instant getDate() {
        return date;
    }

    public NhiAccumulatedMedicalRecord date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public String getCardFillingNote() {
        return cardFillingNote;
    }

    public NhiAccumulatedMedicalRecord cardFillingNote(String cardFillingNote) {
        this.cardFillingNote = cardFillingNote;
        return this;
    }

    public void setCardFillingNote(String cardFillingNote) {
        this.cardFillingNote = cardFillingNote;
    }

    public String getSeqNumber() {
        return seqNumber;
    }

    public NhiAccumulatedMedicalRecord seqNumber(String seqNumber) {
        this.seqNumber = seqNumber;
        return this;
    }

    public void setSeqNumber(String seqNumber) {
        this.seqNumber = seqNumber;
    }

    public String getMedicalInstitutionCode() {
        return medicalInstitutionCode;
    }

    public NhiAccumulatedMedicalRecord medicalInstitutionCode(String medicalInstitutionCode) {
        this.medicalInstitutionCode = medicalInstitutionCode;
        return this;
    }

    public void setMedicalInstitutionCode(String medicalInstitutionCode) {
        this.medicalInstitutionCode = medicalInstitutionCode;
    }

    public Patient getPatient() {
        return patient;
    }

    public NhiAccumulatedMedicalRecord patient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NhiAccumulatedMedicalRecord that = (NhiAccumulatedMedicalRecord) o;
        if (patient == null || patient.getId() == null) {
            return false;
        }
        return Objects.equals(medicalCategory, that.medicalCategory) &&
            Objects.equals(newbornMedicalTreatmentNote, that.newbornMedicalTreatmentNote) &&
            Objects.equals(date, that.date) &&
            Objects.equals(cardFillingNote, that.cardFillingNote) &&
            Objects.equals(seqNumber, that.seqNumber) &&
            Objects.equals(medicalInstitutionCode, that.medicalInstitutionCode) &&
            patient.getId().equals(that.patient.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, medicalCategory, newbornMedicalTreatmentNote, date, cardFillingNote, seqNumber, medicalInstitutionCode, patient);
    }

    @Override
    public String toString() {
        return "NhiAccumulatedMedicalRecord{" +
            "id=" + getId() +
            ", medicalCategory='" + getMedicalCategory() + "'" +
            ", newbornMedicalTreatmentNote='" + getNewbornMedicalTreatmentNote() + "'" +
            ", date='" + getDate() + "'" +
            ", cardFillingNote='" + getCardFillingNote() + "'" +
            ", seqNumber='" + getSeqNumber() + "'" +
            ", medicalInstitutionCode='" + getMedicalInstitutionCode() + "'" +
            "}";
    }
}
