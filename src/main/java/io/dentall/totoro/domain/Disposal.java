package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.enumeration.DisposalRevisitInterval;
import io.dentall.totoro.domain.enumeration.DisposalStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.Set;

/**
 * A Disposal.
 */
@Entity
@Table(name = "disposal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@AttributeOverride(name="createdBy", column=@Column(name="createdBy"))
public class Disposal extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private DisposalStatus status;

    @Column(name = "total")
    private Double total;

    @Column(name = "date_time")
    private Instant dateTime;

    @Column(name = "chief_complaint")
    private String chiefComplaint;

    @Column(name = "revisit_content")
    private String revisitContent;

    @Enumerated(EnumType.STRING)
    @Column(name = "revisit_interval")
    private DisposalRevisitInterval revisitInterval;

    @Column(name = "revisit_treatment_time")
    private Integer revisitTreatmentTime;

    @Column(name = "revisit_comment")
    private String revisitComment;

    @Column(name = "revisit_will_not_happen")
    private Boolean revisitWillNotHappen;

    @OneToMany(mappedBy = "disposal", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TreatmentProcedure> treatmentProcedures = null;

    @OneToOne
    @JoinColumn(unique = true)
    private Prescription prescription;

    @OneToOne
    @JoinColumn(unique = true)
    private Todo todo;

    @OneToOne    @JoinColumn(unique = true)
    private Registration registration;

    @OneToMany(mappedBy = "disposal", fetch = FetchType.LAZY)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tooth> teeth = null;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    // In fact we use it like one-to-one, add logic for make it fit one-to-one
    @OneToMany(mappedBy = "disposal", fetch = FetchType.EAGER)
    private Set<NhiExtendDisposal> nhiExtendDisposals = null;

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50)
    @JsonIgnore
    private String createdBy;

    @Column(name = "date_time_end")
    private Instant dateTimeEnd;

    public Disposal revisitWillNotHappen(Boolean revisitWillNotHappen) {
        this.revisitWillNotHappen = revisitWillNotHappen;
        return this;
    }

    public Boolean getRevisitWillNotHappen() {
        return revisitWillNotHappen;
    }

    public void setRevisitWillNotHappen(Boolean revisitWillNotHappen) {
        this.revisitWillNotHappen = revisitWillNotHappen;
    }

    public Disposal revisitContent(String revisitContent) {
        this.revisitContent = revisitContent;
        return this;
    }

    public Disposal revisitInterval(DisposalRevisitInterval revisitInterval) {
        this.revisitInterval = revisitInterval;
        return this;
    }

    public Disposal revisitTreatmentTime(Integer revisitTreatmentTime) {
        this.revisitTreatmentTime = revisitTreatmentTime;
        return this;
    }

    public Disposal revisitComment(String revisitComment) {
        this.revisitComment = revisitComment;
        return this;
    }

    public Disposal dateTimeEnd(Instant dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
        return this;
    }

    public Instant getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(Instant dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DisposalStatus getStatus() {
        return status;
    }

    public Disposal status(DisposalStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(DisposalStatus status) {
        this.status = status;
    }

    public Double getTotal() {
        return total;
    }

    public Disposal total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Instant getDateTime() {
        return dateTime;
    }

    public Disposal dateTime(Instant dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    public void setDateTime(Instant dateTime) {
        this.dateTime = dateTime;
    }

    public String getChiefComplaint() {
        return chiefComplaint;
    }

    public Disposal chiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
        return this;
    }

    public void setChiefComplaint(String chiefComplaint) {
        this.chiefComplaint = chiefComplaint;
    }

    public Set<TreatmentProcedure> getTreatmentProcedures() {
        return treatmentProcedures;
    }

    public Disposal treatmentProcedures(Set<TreatmentProcedure> treatmentProcedures) {
        this.treatmentProcedures = treatmentProcedures;
        return this;
    }

    public Disposal addTreatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedures.add(treatmentProcedure);
        treatmentProcedure.setDisposal(this);
        return this;
    }

    public Disposal removeTreatmentProcedure(TreatmentProcedure treatmentProcedure) {
        this.treatmentProcedures.remove(treatmentProcedure);
        treatmentProcedure.setDisposal(null);
        return this;
    }

    public void setTreatmentProcedures(Set<TreatmentProcedure> treatmentProcedures) {
        this.treatmentProcedures = treatmentProcedures;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public Disposal prescription(Prescription prescription) {
        this.prescription = prescription;
        return this;
    }

    public void setPrescription(Prescription prescription) {
        this.prescription = prescription;
    }

    public Todo getTodo() {
        return todo;
    }

    public Disposal todo(Todo todo) {
        this.todo = todo;
        return this;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public Registration getRegistration() {
        return registration;
    }

    public Disposal registration(Registration registration) {
        this.registration = registration;
        return this;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Set<Tooth> getTeeth() {
        return teeth;
    }

    public Disposal teeth(Set<Tooth> teeth) {
        this.teeth = teeth;
        return this;
    }

    public Disposal addTooth(Tooth tooth) {
        this.teeth.add(tooth);
        tooth.setDisposal(this);
        return this;
    }

    public Disposal removeTooth(Tooth tooth) {
        this.teeth.remove(tooth);
        tooth.setDisposal(null);
        return this;
    }

    public void setTeeth(Set<Tooth> teeth) {
        this.teeth = teeth;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public Set<NhiExtendDisposal> getNhiExtendDisposals() {
        return nhiExtendDisposals;
    }

    public Disposal nhiExtendDisposals(Set<NhiExtendDisposal> nhiExtendDisposals) {
        this.nhiExtendDisposals = nhiExtendDisposals;
        return this;
    }

    public Disposal addNhiExtendDisposal(NhiExtendDisposal nhiExtendDisposal) {
        this.nhiExtendDisposals.add(nhiExtendDisposal);
        nhiExtendDisposal.setDisposal(this);
        return this;
    }

    public Disposal removeNhiExtendDisposal(NhiExtendDisposal nhiExtendDisposal) {
        this.nhiExtendDisposals.remove(nhiExtendDisposal);
        nhiExtendDisposal.setDisposal(null);
        return this;
    }

    public void setNhiExtendDisposals(Set<NhiExtendDisposal> nhiExtendDisposals) {
        this.nhiExtendDisposals = nhiExtendDisposals;
    }

    @Override
    @JsonIgnore(false)
    @JsonProperty
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    @JsonIgnore(false)
    @JsonProperty
    public Instant getCreatedDate() {
        return super.getCreatedDate();
    }

    @Override
    @JsonIgnore(false)
    @JsonProperty
    public String getLastModifiedBy() {
        return super.getLastModifiedBy();
    }

    @Override
    @JsonIgnore(false)
    @JsonProperty
    public Instant getLastModifiedDate() {
        return super.getLastModifiedDate();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRevisitContent() {
        return revisitContent;
    }

    public void setRevisitContent(String revisitContent) {
        this.revisitContent = revisitContent;
    }

    public DisposalRevisitInterval getRevisitInterval() {
        return revisitInterval;
    }

    public void setRevisitInterval(DisposalRevisitInterval revisitInterval) {
        this.revisitInterval = revisitInterval;
    }

    public Integer getRevisitTreatmentTime() {
        return revisitTreatmentTime;
    }

    public void setRevisitTreatmentTime(Integer revisitTreatmentTime) {
        this.revisitTreatmentTime = revisitTreatmentTime;
    }

    public String getRevisitComment() {
        return revisitComment;
    }

    public void setRevisitComment(String revisitComment) {
        this.revisitComment = revisitComment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Disposal disposal = (Disposal) o;
        if (disposal.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), disposal.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Disposal{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", total=" + getTotal() +
            ", dateTime='" + getDateTime() + "'" +
            ", chiefComplaint='" + getChiefComplaint() + "'" +
            ", revisitContent='" + getRevisitContent() + "'" +
            ", revisitInterval='" + getRevisitInterval() + "'" +
            ", revisitTreatmentTime='" + getRevisitTreatmentTime() + "'" +
            ", revisitComment='" + getRevisitComment() + "'" +
            "}";
    }
}
