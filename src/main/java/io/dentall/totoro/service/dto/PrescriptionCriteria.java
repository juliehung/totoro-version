package io.dentall.totoro.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.dentall.totoro.domain.enumeration.PrescriptionStatus;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the Prescription entity. This class is used in PrescriptionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /prescriptions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PrescriptionCriteria implements Serializable {
    /**
     * Class for filtering PrescriptionStatus
     */
    public static class PrescriptionStatusFilter extends Filter<PrescriptionStatus> {
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private BooleanFilter clinicAdministration;

    private BooleanFilter antiInflammatoryDrug;

    private BooleanFilter pain;

    private BooleanFilter takenAll;

    private PrescriptionStatusFilter status;

    private LongFilter treatmentDrugId;

    private LongFilter registrationId;

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public BooleanFilter getClinicAdministration() {
        return clinicAdministration;
    }

    public void setClinicAdministration(BooleanFilter clinicAdministration) {
        this.clinicAdministration = clinicAdministration;
    }

    public BooleanFilter getAntiInflammatoryDrug() {
        return antiInflammatoryDrug;
    }

    public void setAntiInflammatoryDrug(BooleanFilter antiInflammatoryDrug) {
        this.antiInflammatoryDrug = antiInflammatoryDrug;
    }

    public BooleanFilter getPain() {
        return pain;
    }

    public void setPain(BooleanFilter pain) {
        this.pain = pain;
    }

    public BooleanFilter getTakenAll() {
        return takenAll;
    }

    public void setTakenAll(BooleanFilter takenAll) {
        this.takenAll = takenAll;
    }

    public PrescriptionStatusFilter getStatus() {
        return status;
    }

    public void setStatus(PrescriptionStatusFilter status) {
        this.status = status;
    }

    public LongFilter getTreatmentDrugId() {
        return treatmentDrugId;
    }

    public void setTreatmentDrugId(LongFilter treatmentDrugId) {
        this.treatmentDrugId = treatmentDrugId;
    }

    public LongFilter getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(LongFilter registrationId) {
        this.registrationId = registrationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PrescriptionCriteria that = (PrescriptionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(clinicAdministration, that.clinicAdministration) &&
            Objects.equals(antiInflammatoryDrug, that.antiInflammatoryDrug) &&
            Objects.equals(pain, that.pain) &&
            Objects.equals(takenAll, that.takenAll) &&
            Objects.equals(status, that.status) &&
            Objects.equals(treatmentDrugId, that.treatmentDrugId) &&
            Objects.equals(registrationId, that.registrationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        clinicAdministration,
        antiInflammatoryDrug,
        pain,
        takenAll,
        status,
        treatmentDrugId,
        registrationId
        );
    }

    @Override
    public String toString() {
        return "PrescriptionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (clinicAdministration != null ? "clinicAdministration=" + clinicAdministration + ", " : "") +
                (antiInflammatoryDrug != null ? "antiInflammatoryDrug=" + antiInflammatoryDrug + ", " : "") +
                (pain != null ? "pain=" + pain + ", " : "") +
                (takenAll != null ? "takenAll=" + takenAll + ", " : "") +
                (status != null ? "status=" + status + ", " : "") +
                (treatmentDrugId != null ? "treatmentDrugId=" + treatmentDrugId + ", " : "") +
                (registrationId != null ? "registrationId=" + registrationId + ", " : "") +
            "}";
    }

}
