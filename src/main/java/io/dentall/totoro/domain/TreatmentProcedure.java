package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.dentall.totoro.domain.enumeration.TreatmentProcedureStatus;

/**
 * A TreatmentProcedure.
 */
@Entity
@Table(name = "treatment_procedure")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TreatmentProcedure extends AbstractDoctorAndAuditingEntity<TreatmentProcedure> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TreatmentProcedureStatus status;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "total")
    private Double total;

    @Column(name = "note")
    private String note;

    @Column(name = "completed_date")
    private Instant completedDate;

    @ManyToOne
    @JsonIgnoreProperties("")
    private NHIProcedure nhiProcedure;

    @ManyToOne
    @JsonIgnoreProperties("treatmentProcedures")
    private TreatmentTask treatmentTask;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Procedure procedure;

    @ManyToOne
    @JsonIgnoreProperties("treatmentProcedures")
    private Appointment appointment;

    @ManyToOne
    @JsonIgnoreProperties("treatmentProcedures")
    private Registration registration;

    @OneToMany(mappedBy = "treatmentProcedure", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tooth> teeth = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TreatmentProcedureStatus getStatus() {
        return status;
    }

    public TreatmentProcedure status(TreatmentProcedureStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(TreatmentProcedureStatus status) {
        this.status = status;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public TreatmentProcedure quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotal() {
        return total;
    }

    public TreatmentProcedure total(Double total) {
        this.total = total;
        return this;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getNote() {
        return note;
    }

    public TreatmentProcedure note(String note) {
        this.note = note;
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Instant getCompletedDate() {
        return completedDate;
    }

    public TreatmentProcedure completedDate(Instant completedDate) {
        this.completedDate = completedDate;
        return this;
    }

    public void setCompletedDate(Instant completedDate) {
        this.completedDate = completedDate;
    }

    public NHIProcedure getNhiProcedure() {
        return nhiProcedure;
    }

    public TreatmentProcedure nhiProcedure(NHIProcedure nHIProcedure) {
        this.nhiProcedure = nHIProcedure;
        return this;
    }

    public void setNhiProcedure(NHIProcedure nHIProcedure) {
        this.nhiProcedure = nHIProcedure;
    }

    public TreatmentTask getTreatmentTask() {
        return treatmentTask;
    }

    public TreatmentProcedure treatmentTask(TreatmentTask treatmentTask) {
        this.treatmentTask = treatmentTask;
        return this;
    }

    public void setTreatmentTask(TreatmentTask treatmentTask) {
        this.treatmentTask = treatmentTask;
    }

    public Procedure getProcedure() {
        return procedure;
    }

    public TreatmentProcedure procedure(Procedure procedure) {
        this.procedure = procedure;
        return this;
    }

    public void setProcedure(Procedure procedure) {
        this.procedure = procedure;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public TreatmentProcedure appointment(Appointment appointment) {
        this.appointment = appointment;
        return this;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Registration getRegistration() {
        return registration;
    }

    public TreatmentProcedure registration(Registration registration) {
        this.registration = registration;
        return this;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    public Set<Tooth> getTeeth() {
        return teeth;
    }

    public TreatmentProcedure teeth(Set<Tooth> teeth) {
        this.teeth = teeth;
        return this;
    }

    public TreatmentProcedure addTooth(Tooth tooth) {
        this.teeth.add(tooth);
        tooth.setTreatmentProcedure(this);
        return this;
    }

    public TreatmentProcedure removeTooth(Tooth tooth) {
        this.teeth.remove(tooth);
        tooth.setTreatmentProcedure(null);
        return this;
    }

    public void setTeeth(Set<Tooth> teeth) {
        this.teeth = teeth;
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
        TreatmentProcedure treatmentProcedure = (TreatmentProcedure) o;
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
        return "TreatmentProcedure{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            ", quantity=" + getQuantity() +
            ", total=" + getTotal() +
            ", note='" + getNote() + "'" +
            ", completedDate='" + getCompletedDate() + "'" +
            "}";
    }
}
