package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.DisposalStatus;
import org.springframework.data.annotation.CreatedBy;

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

    @OneToMany(mappedBy = "disposal", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TreatmentProcedure> treatmentProcedures = new HashSet<>();

    @OneToOne
    @JoinColumn(unique = true)
    private Prescription prescription;

    @OneToOne
    @JoinColumn(unique = true)
    private Todo todo;

    @OneToOne    @JoinColumn(unique = true)
    private Registration registration;

    @OneToMany(mappedBy = "disposal")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tooth> teeth = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @OneToOne(mappedBy = "disposal", cascade = CascadeType.ALL)
    private ExtendDisposalNHI extendDisposalNHI;

    @CreatedBy
    @Column(name = "created_by", nullable = false, length = 50)
    @JsonIgnore
    private String createdBy;

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

    public ExtendDisposalNHI getExtendDisposalNHI() {
        return extendDisposalNHI;
    }

    public void setExtendDisposalNHI(ExtendDisposalNHI extendDisposalNHI) {
        this.extendDisposalNHI = extendDisposalNHI;
    }

    @Override
    @JsonIgnore(false)
    @JsonProperty
    public String getCreatedBy() {
        return createdBy;
    }

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
            "}";
    }
}
