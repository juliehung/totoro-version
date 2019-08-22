package io.dentall.totoro.domain;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Set;
import java.util.Objects;

/**
 * A NhiMonthDeclaration.
 */
@Entity
@Table(name = "nhi_month_declaration")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NhiMonthDeclaration implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "jhi_year_month", nullable = false)
    private String yearMonth;

    @Column(name = "institution")
    private String institution;

    @OneToMany(mappedBy = "nhiMonthDeclaration", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<NhiMonthDeclarationDetails> nhiMonthDeclarationDetails = null;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public NhiMonthDeclaration yearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
        return this;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getInstitution() {
        return institution;
    }

    public NhiMonthDeclaration institution(String institution) {
        this.institution = institution;
        return this;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @ApiModelProperty(hidden = true)
    public Set<NhiMonthDeclarationDetails> getNhiMonthDeclarationDetails() {
        return nhiMonthDeclarationDetails;
    }

    public NhiMonthDeclaration nhiMonthDeclarationDetails(Set<NhiMonthDeclarationDetails> nhiMonthDeclarationDetails) {
        this.nhiMonthDeclarationDetails = nhiMonthDeclarationDetails;
        return this;
    }

    public NhiMonthDeclaration addNhiMonthDeclarationDetails(NhiMonthDeclarationDetails nhiMonthDeclarationDetails) {
        this.nhiMonthDeclarationDetails.add(nhiMonthDeclarationDetails);
        nhiMonthDeclarationDetails.setNhiMonthDeclaration(this);
        return this;
    }

    public NhiMonthDeclaration removeNhiMonthDeclarationDetails(NhiMonthDeclarationDetails nhiMonthDeclarationDetails) {
        this.nhiMonthDeclarationDetails.remove(nhiMonthDeclarationDetails);
        nhiMonthDeclarationDetails.setNhiMonthDeclaration(null);
        return this;
    }

    public void setNhiMonthDeclarationDetails(Set<NhiMonthDeclarationDetails> nhiMonthDeclarationDetails) {
        this.nhiMonthDeclarationDetails = nhiMonthDeclarationDetails;
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
        NhiMonthDeclaration nhiMonthDeclaration = (NhiMonthDeclaration) o;
        if (nhiMonthDeclaration.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhiMonthDeclaration.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhiMonthDeclaration{" +
            "id=" + getId() +
            ", yearMonth='" + getYearMonth() + "'" +
            ", institution='" + getInstitution() + "'" +
            "}";
    }
}
