package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * A NhiExtendTreatmentProcedure.
 */
@Entity
@Table(name = "nhi_extend_treatment_procedure")
public class NhiExtendTreatmentProcedure extends NhiAbstractMedicalArea<NhiExtendTreatmentProcedure> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @OneToOne
    @MapsId
    @JsonProperty(access = WRITE_ONLY)
    private TreatmentProcedure treatmentProcedure;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ApiModelProperty(hidden = true)
    public TreatmentProcedure getTreatmentProcedure() {
        return treatmentProcedure;
    }

    public NhiExtendTreatmentProcedure treatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedure = treatmentProcedure;
        return this;
    }

    public void setTreatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedure = treatmentProcedure;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure = (NhiExtendTreatmentProcedure) o;
        if (nhiExtendTreatmentProcedure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhiExtendTreatmentProcedure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhiExtendTreatmentProcedure{" +
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
            ", check='" + getCheck() + "'" +
            "}";
    }
}
