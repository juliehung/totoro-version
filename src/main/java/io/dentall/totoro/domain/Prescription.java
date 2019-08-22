package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Set;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.PrescriptionStatus;

import io.dentall.totoro.domain.enumeration.PrescriptionMode;

/**
 * A Prescription.
 */
@Entity
@Table(name = "prescription")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Prescription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "clinic_administration")
    private Boolean clinicAdministration;

    @Column(name = "anti_inflammatory_drug")
    private Boolean antiInflammatoryDrug;

    @Column(name = "pain")
    private Boolean pain;

    @Column(name = "taken_all")
    private Boolean takenAll;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PrescriptionStatus status;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_mode", nullable = false)
    private PrescriptionMode mode;

    @OneToMany(mappedBy = "prescription", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TreatmentDrug> treatmentDrugs = null;
    @OneToOne(mappedBy = "prescription")
    @JsonIgnore
    private Disposal disposal;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean isClinicAdministration() {
        return clinicAdministration;
    }

    public Prescription clinicAdministration(Boolean clinicAdministration) {
        this.clinicAdministration = clinicAdministration;
        return this;
    }

    public void setClinicAdministration(Boolean clinicAdministration) {
        this.clinicAdministration = clinicAdministration;
    }

    public Boolean isAntiInflammatoryDrug() {
        return antiInflammatoryDrug;
    }

    public Prescription antiInflammatoryDrug(Boolean antiInflammatoryDrug) {
        this.antiInflammatoryDrug = antiInflammatoryDrug;
        return this;
    }

    public void setAntiInflammatoryDrug(Boolean antiInflammatoryDrug) {
        this.antiInflammatoryDrug = antiInflammatoryDrug;
    }

    public Boolean isPain() {
        return pain;
    }

    public Prescription pain(Boolean pain) {
        this.pain = pain;
        return this;
    }

    public void setPain(Boolean pain) {
        this.pain = pain;
    }

    public Boolean isTakenAll() {
        return takenAll;
    }

    public Prescription takenAll(Boolean takenAll) {
        this.takenAll = takenAll;
        return this;
    }

    public void setTakenAll(Boolean takenAll) {
        this.takenAll = takenAll;
    }

    public PrescriptionStatus getStatus() {
        return status;
    }

    public Prescription status(PrescriptionStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PrescriptionStatus status) {
        this.status = status;
    }

    public PrescriptionMode getMode() {
        return mode;
    }

    public Prescription mode(PrescriptionMode mode) {
        this.mode = mode;
        return this;
    }

    public void setMode(PrescriptionMode mode) {
        this.mode = mode;
    }

    @ApiModelProperty(hidden = true)
    public Set<TreatmentDrug> getTreatmentDrugs() {
        return treatmentDrugs;
    }

    public Prescription treatmentDrugs(Set<TreatmentDrug> treatmentDrugs) {
        this.treatmentDrugs = treatmentDrugs;
        return this;
    }

    public Prescription addTreatmentDrug(TreatmentDrug treatmentDrug) {
        this.treatmentDrugs.add(treatmentDrug);
        treatmentDrug.setPrescription(this);
        return this;
    }

    public Prescription removeTreatmentDrug(TreatmentDrug treatmentDrug) {
        this.treatmentDrugs.remove(treatmentDrug);
        treatmentDrug.setPrescription(null);
        return this;
    }

    public void setTreatmentDrugs(Set<TreatmentDrug> treatmentDrugs) {
        this.treatmentDrugs = treatmentDrugs;
    }

    @ApiModelProperty(hidden = true)
    public Disposal getDisposal() {
        return disposal;
    }

    public Prescription disposal(Disposal disposal) {
        this.disposal = disposal;
        return this;
    }

    public void setDisposal(Disposal disposal) {
        this.disposal = disposal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Prescription prescription = (Prescription) o;
        if (prescription.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), prescription.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Prescription{" +
            "id=" + getId() +
            ", clinicAdministration='" + isClinicAdministration() + "'" +
            ", antiInflammatoryDrug='" + isAntiInflammatoryDrug() + "'" +
            ", pain='" + isPain() + "'" +
            ", takenAll='" + isTakenAll() + "'" +
            ", status='" + getStatus() + "'" +
            ", mode='" + getMode() + "'" +
            "}";
    }
}
