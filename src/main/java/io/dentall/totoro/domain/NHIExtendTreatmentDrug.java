package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A NHIExtendTreatmentDrug.
 */
@Entity
@Table(name = "nhi_extend_treatment_drug")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NHIExtendTreatmentDrug extends NHIAbstractMedicalArea<NHIExtendTreatmentDrug> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JsonIgnore
    private TreatmentDrug treatmentDrug;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TreatmentDrug getTreatmentDrug() {
        return treatmentDrug;
    }

    public NHIExtendTreatmentDrug treatmentDrug(TreatmentDrug treatmentDrug) {
        this.treatmentDrug = treatmentDrug;
        return this;
    }

    public void setTreatmentDrug(TreatmentDrug treatmentDrug) {
        this.treatmentDrug = treatmentDrug;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NHIExtendTreatmentDrug extendUser = (NHIExtendTreatmentDrug) o;
        if (extendUser.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), extendUser.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NHIExtendTreatmentDrug{" +
            "id=" + getId() +
            ", a71='" + getA71() + "'" +
            ", a72='" + getA72() + "'" +
            ", a73='" + getA73() + "'" +
            ", a74='" + getA74() + "'" +
            ", a75='" + getA75() + "'" +
            ", a76='" + getA76() + "'" +
            ", a77='" + getA77() + "'" +
            ", a78='" + getA78() + "'" +
            ", a79='" + getA79() + "'" +
            "}";
    }
}
