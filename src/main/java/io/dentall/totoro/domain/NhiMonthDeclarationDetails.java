package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.NhiMonthDeclarationType;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * A NhiMonthDeclarationDetails.
 */
@Entity
@Table(name = "nhi_month_declaration_details")
public class NhiMonthDeclarationDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private NhiMonthDeclarationType type = NhiMonthDeclarationType.SUBMISSION;

    @Column(name = "way")
    private String way;

    @Column(name = "case_total")
    private Integer caseTotal;

    @Column(name = "point_total")
    private Integer pointTotal;

    @Column(name = "out_patient_point")
    private Integer outPatientPoint;

    @Column(name = "preventive_case_total")
    private Integer preventiveCaseTotal;

    @Column(name = "preventive_point_total")
    private Integer preventivePointTotal;

    @Column(name = "general_case_total")
    private Integer generalCaseTotal;

    @Column(name = "general_point_total")
    private Integer generalPointTotal;

    @Column(name = "professional_case_total")
    private Integer professionalCaseTotal;

    @Column(name = "professional_point_total")
    private Integer professionalPointTotal;

    @Column(name = "partial_case_total")
    private Integer partialCaseTotal;

    @Column(name = "partial_point_total")
    private Integer partialPointTotal;

    @Column(name = "jhi_file")
    private String file;

    @Column(name = "upload_time")
    private Instant uploadTime;

    @Column(name = "local_id")
    private String localId;

    @Column(name = "nhi_id")
    private String nhiId;

    @ManyToOne
    @JsonProperty(access = WRITE_ONLY)
    private NhiMonthDeclaration nhiMonthDeclaration;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NhiMonthDeclarationType getType() {
        return type;
    }

    public NhiMonthDeclarationDetails type(NhiMonthDeclarationType type) {
        this.type = type;
        return this;
    }

    public void setType(NhiMonthDeclarationType type) {
        this.type = type;
    }

    public String getWay() {
        return way;
    }

    public NhiMonthDeclarationDetails way(String way) {
        this.way = way;
        return this;
    }

    public void setWay(String way) {
        this.way = way;
    }

    public Integer getCaseTotal() {
        return caseTotal;
    }

    public NhiMonthDeclarationDetails caseTotal(Integer caseTotal) {
        this.caseTotal = caseTotal;
        return this;
    }

    public void setCaseTotal(Integer caseTotal) {
        this.caseTotal = caseTotal;
    }

    public Integer getPointTotal() {
        return pointTotal;
    }

    public NhiMonthDeclarationDetails pointTotal(Integer pointTotal) {
        this.pointTotal = pointTotal;
        return this;
    }

    public void setPointTotal(Integer pointTotal) {
        this.pointTotal = pointTotal;
    }

    public Integer getOutPatientPoint() {
        return outPatientPoint;
    }

    public NhiMonthDeclarationDetails outPatientPoint(Integer outPatientPoint) {
        this.outPatientPoint = outPatientPoint;
        return this;
    }

    public void setOutPatientPoint(Integer outPatientPoint) {
        this.outPatientPoint = outPatientPoint;
    }

    public Integer getPreventiveCaseTotal() {
        return preventiveCaseTotal;
    }

    public NhiMonthDeclarationDetails preventiveCaseTotal(Integer preventiveCaseTotal) {
        this.preventiveCaseTotal = preventiveCaseTotal;
        return this;
    }

    public void setPreventiveCaseTotal(Integer preventiveCaseTotal) {
        this.preventiveCaseTotal = preventiveCaseTotal;
    }

    public Integer getPreventivePointTotal() {
        return preventivePointTotal;
    }

    public NhiMonthDeclarationDetails preventivePointTotal(Integer preventivePointTotal) {
        this.preventivePointTotal = preventivePointTotal;
        return this;
    }

    public void setPreventivePointTotal(Integer preventivePointTotal) {
        this.preventivePointTotal = preventivePointTotal;
    }

    public Integer getGeneralCaseTotal() {
        return generalCaseTotal;
    }

    public NhiMonthDeclarationDetails generalCaseTotal(Integer generalCaseTotal) {
        this.generalCaseTotal = generalCaseTotal;
        return this;
    }

    public void setGeneralCaseTotal(Integer generalCaseTotal) {
        this.generalCaseTotal = generalCaseTotal;
    }

    public Integer getGeneralPointTotal() {
        return generalPointTotal;
    }

    public NhiMonthDeclarationDetails generalPointTotal(Integer generalPointTotal) {
        this.generalPointTotal = generalPointTotal;
        return this;
    }

    public void setGeneralPointTotal(Integer generalPointTotal) {
        this.generalPointTotal = generalPointTotal;
    }

    public Integer getProfessionalCaseTotal() {
        return professionalCaseTotal;
    }

    public NhiMonthDeclarationDetails professionalCaseTotal(Integer professionalCaseTotal) {
        this.professionalCaseTotal = professionalCaseTotal;
        return this;
    }

    public void setProfessionalCaseTotal(Integer professionalCaseTotal) {
        this.professionalCaseTotal = professionalCaseTotal;
    }

    public Integer getProfessionalPointTotal() {
        return professionalPointTotal;
    }

    public NhiMonthDeclarationDetails professionalPointTotal(Integer professionalPointTotal) {
        this.professionalPointTotal = professionalPointTotal;
        return this;
    }

    public void setProfessionalPointTotal(Integer professionalPointTotal) {
        this.professionalPointTotal = professionalPointTotal;
    }

    public Integer getPartialCaseTotal() {
        return partialCaseTotal;
    }

    public NhiMonthDeclarationDetails partialCaseTotal(Integer partialCaseTotal) {
        this.partialCaseTotal = partialCaseTotal;
        return this;
    }

    public void setPartialCaseTotal(Integer partialCaseTotal) {
        this.partialCaseTotal = partialCaseTotal;
    }

    public Integer getPartialPointTotal() {
        return partialPointTotal;
    }

    public NhiMonthDeclarationDetails partialPointTotal(Integer partialPointTotal) {
        this.partialPointTotal = partialPointTotal;
        return this;
    }

    public void setPartialPointTotal(Integer partialPointTotal) {
        this.partialPointTotal = partialPointTotal;
    }

    public String getFile() {
        return file;
    }

    public NhiMonthDeclarationDetails file(String file) {
        this.file = file;
        return this;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public Instant getUploadTime() {
        return uploadTime;
    }

    public NhiMonthDeclarationDetails uploadTime(Instant uploadTime) {
        this.uploadTime = uploadTime;
        return this;
    }

    public void setUploadTime(Instant uploadTime) {
        this.uploadTime = uploadTime;
    }

    public String getLocalId() {
        return localId;
    }

    public NhiMonthDeclarationDetails localId(String localId) {
        this.localId = localId;
        return this;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }

    public String getNhiId() {
        return nhiId;
    }

    public NhiMonthDeclarationDetails nhiId(String nhiId) {
        this.nhiId = nhiId;
        return this;
    }

    public void setNhiId(String nhiId) {
        this.nhiId = nhiId;
    }

    public NhiMonthDeclaration getNhiMonthDeclaration() {
        return nhiMonthDeclaration;
    }

    public NhiMonthDeclarationDetails nhiMonthDeclaration(NhiMonthDeclaration nhiMonthDeclaration) {
        this.nhiMonthDeclaration = nhiMonthDeclaration;
        return this;
    }

    public void setNhiMonthDeclaration(NhiMonthDeclaration nhiMonthDeclaration) {
        this.nhiMonthDeclaration = nhiMonthDeclaration;
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
        NhiMonthDeclarationDetails nhiMonthDeclarationDetails = (NhiMonthDeclarationDetails) o;
        if (nhiMonthDeclarationDetails.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), nhiMonthDeclarationDetails.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "NhiMonthDeclarationDetails{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", way='" + getWay() + "'" +
            ", caseTotal=" + getCaseTotal() +
            ", pointTotal=" + getPointTotal() +
            ", outPatientPoint=" + getOutPatientPoint() +
            ", preventiveCaseTotal=" + getPreventiveCaseTotal() +
            ", preventivePointTotal=" + getPreventivePointTotal() +
            ", generalCaseTotal=" + getGeneralCaseTotal() +
            ", generalPointTotal=" + getGeneralPointTotal() +
            ", professionalCaseTotal=" + getProfessionalCaseTotal() +
            ", professionalPointTotal=" + getProfessionalPointTotal() +
            ", partialCaseTotal=" + getPartialCaseTotal() +
            ", partialPointTotal=" + getPartialPointTotal() +
            ", file='" + getFile() + "'" +
            ", uploadTime='" + getUploadTime() + "'" +
            ", localId='" + getLocalId() + "'" +
            ", nhiId='" + getNhiId() + "'" +
            "}";
    }
}
