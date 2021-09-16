package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.TreatmentProcedureStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "treatment_procedure_del")
public class TreatmentProcedureDel extends AbstractDoctorAndAuditingEntity<TreatmentProcedureDel> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TreatmentProcedureStatus status;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total")
    private Double total;

    @Size(max = 5100)
    @Column(name = "note", length = 5100)
    private String note;

    @Column(name = "completed_date")
    private Instant completedDate;

    @Column(name = "price")
    private Double price;

    @Column(name = "nhi_category")
    private String nhiCategory;

    @Size(max = 5100)
    @Column(name = "nhi_description", length = 5100)
    private String nhiDescription;

    @Column(name = "nhi_icd_10_cm")
    private String nhiIcd10Cm;

    @Column(name = "nhi_procedure_id")
    private Long nhiProcedureId;

    @Column(name = "treatment_task_id")
    private Long treatmentTaskId;

    @Column(name = "procedure_id")
    private Long procedureId;

    @Column(name = "appointment_id")
    private Long appointmentId;

    @Column(name = "disposal_id")
    private Long disposalId;

    @Column(name = "ic_card_eject")
    private Boolean icCardEject = false;

    public Boolean getIcCardEject() {
        return icCardEject;
    }

    public void setIcCardEject(Boolean icCardEject) {
        this.icCardEject = icCardEject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TreatmentProcedureStatus getStatus() {
        return status;
    }

    public TreatmentProcedureDel status(TreatmentProcedureStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TreatmentProcedureStatus status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public TreatmentProcedureDel quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotal() {
        return total;
    }

    public TreatmentProcedureDel total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public TreatmentProcedureDel note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getCompletedDate() {
        return completedDate;
    }

    public TreatmentProcedureDel completedDate(Instant completedDate) {
        this.completedDate = completedDate;
        return this;
    }

    public void setCompletedDate(Instant completedDate) {
        this.completedDate = completedDate;
    }

    public Double getPrice() {
        return price;
    }

    public TreatmentProcedureDel price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNhiCategory() {
        return nhiCategory;
    }

    public TreatmentProcedureDel nhiCategory(String nhiCategory) {
        this.nhiCategory = nhiCategory;
        return this;
    }

    public void setNhiCategory(String nhiCategory) {
        this.nhiCategory = nhiCategory;
    }

    public String getNhiDescription() {
        return nhiDescription;
    }

    public TreatmentProcedureDel nhiDescription(String nhiDescription) {
        this.nhiDescription = nhiDescription;
        return this;
    }

    public void setNhiDescription(String nhiDescription) {
        this.nhiDescription = nhiDescription;
    }

    public String getNhiIcd10Cm() {
        return nhiIcd10Cm;
    }

    public TreatmentProcedureDel nhiIcd10Cm(String nhiIcd10Cm) {
        this.nhiIcd10Cm = nhiIcd10Cm;
        return this;
    }

    public void setNhiIcd10Cm(String nhiIcd10Cm) {
        this.nhiIcd10Cm = nhiIcd10Cm;
    }

    public Long getNhiProcedureId() {
        return nhiProcedureId;
    }

    public void setNhiProcedureId(Long nhiProcedureId) {
        this.nhiProcedureId = nhiProcedureId;
    }

    public Long getTreatmentTaskId() {
        return treatmentTaskId;
    }

    public void setTreatmentTaskId(Long treatmentTaskId) {
        this.treatmentTaskId = treatmentTaskId;
    }

    public Long getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(Long procedureId) {
        this.procedureId = procedureId;
    }

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getDisposalId() {
        return disposalId;
    }

    public void setDisposalId(Long disposalId) {
        this.disposalId = disposalId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TreatmentProcedureDel treatmentProcedure = (TreatmentProcedureDel) o;
        if (treatmentProcedure.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), treatmentProcedure.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        String s = "TreatmentProcedureDel{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", quantity=" + getQuantity() +
            ", total=" + getTotal() +
            ", note='" + getNote() + "'" +
            ", completedDate='" + getCompletedDate() + "'" +
            ", price=" + getPrice() +
            ", nhiCategory='" + getNhiCategory() + "'" +
            ", nhiDescription='" + getNhiDescription() + "'" +
            ", nhiIcd10Cm='" + getNhiIcd10Cm() + "'" +
            ", disposalId='" + getDisposalId() + "'" +
            "}";

        return s;
    }
}
