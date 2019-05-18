package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

import io.dentall.totoro.domain.enumeration.TreatmentProcedureStatus;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

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

    @Column(name = "price")
    private Double price;

    @Column(name = "nhi_category")
    private String nhiCategory;

    @Size(max = 5100)
    @Column(name = "nhi_description", length = 5100)
    private String nhiDescription;

    @ManyToOne
    @JsonIgnoreProperties("")
    private NhiProcedure nhiProcedure;

    @ManyToOne
    @JsonProperty(access = WRITE_ONLY)
    private TreatmentTask treatmentTask;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Procedure procedure;

    @ManyToOne
    @JsonProperty(access = WRITE_ONLY)
    private Appointment appointment;

    @OneToMany(mappedBy = "treatmentProcedure", fetch = FetchType.EAGER)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Tooth> teeth = new HashSet<>();

    @ManyToOne
    @JsonProperty(access = WRITE_ONLY)
    private Todo todo;

    @ManyToOne
    @JsonProperty(access = WRITE_ONLY)
    private Disposal disposal;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @OneToOne(mappedBy = "treatmentProcedure", cascade = CascadeType.ALL)
    private NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure;

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

    public Double getPrice() {
        return price;
    }

    public TreatmentProcedure price(Double price) {
        this.price = price;
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getNhiCategory() {
        return nhiCategory;
    }

    public TreatmentProcedure nhiCategory(String nhiCategory) {
        this.nhiCategory = nhiCategory;
        return this;
    }

    public void setNhiCategory(String nhiCategory) {
        this.nhiCategory = nhiCategory;
    }

    public String getNhiDescription() {
        return nhiDescription;
    }

    public TreatmentProcedure nhiDescription(String nhiDescription) {
        this.nhiDescription = nhiDescription;
        return this;
    }

    public void setNhiDescription(String nhiDescription) {
        this.nhiDescription = nhiDescription;
    }

    public NhiProcedure getNhiProcedure() {
        return nhiProcedure;
    }

    public TreatmentProcedure nhiProcedure(NhiProcedure nhiProcedure) {
        this.nhiProcedure = nhiProcedure;
        return this;
    }

    public void setNhiProcedure(NhiProcedure nhiProcedure) {
        this.nhiProcedure = nhiProcedure;
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

    public Todo getTodo() {
        return todo;
    }

    public TreatmentProcedure todo(Todo todo) {
        this.todo = todo;
        return this;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }

    public Disposal getDisposal() {
        return disposal;
    }

    public TreatmentProcedure disposal(Disposal disposal) {
        this.disposal = disposal;
        return this;
    }

    public void setDisposal(Disposal disposal) {
        this.disposal = disposal;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    public NhiExtendTreatmentProcedure getNhiExtendTreatmentProcedure() {
        return nhiExtendTreatmentProcedure;
    }

    public void setNhiExtendTreatmentProcedure(NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        this.nhiExtendTreatmentProcedure = nhiExtendTreatmentProcedure;
    }

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
            ", price=" + getPrice() +
            ", nhiCategory='" + getNhiCategory() + "'" +
            ", nhiDescription='" + getNhiDescription() + "'" +
            "}";
    }
}
