package io.dentall.totoro.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.enumeration.TreatmentProcedureStatus;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

/**
 * A TreatmentProcedure.
 */
@Entity
@Table(name = "treatment_procedure")
@AttributeOverride(name="createdDate", column=@Column(name="createdDate"))
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

    // 代檢醫事機構代碼
    @Column(name = "proxied_inspection_hospital_code")
    private String proxiedInspectionHospitalCode;

    @ManyToOne
    @JsonIgnoreProperties("")
    private NhiProcedure nhiProcedure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonProperty(access = WRITE_ONLY)
    private TreatmentTask treatmentTask;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Procedure procedure;

    @ManyToOne
    @JsonProperty(access = WRITE_ONLY)
    private Appointment appointment;

    @OneToMany(mappedBy = "treatmentProcedure", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Tooth> teeth = new HashSet<>();

    @ManyToMany(mappedBy = "treatmentProcedures")
    @JsonIgnore
    private Set<Todo> todos = null;

    @ManyToOne
    @JsonProperty(access = WRITE_ONLY)
    private Disposal disposal;
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove

    @OneToOne(mappedBy = "treatmentProcedure", cascade = CascadeType.ALL)
    private NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure;

    @CreatedDate
    @Column(name = "created_date", nullable = false)
    @JsonIgnore
    private Instant createdDate;

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

    public String getProxiedInspectionHospitalCode() {
        return proxiedInspectionHospitalCode;
    }

    public void setProxiedInspectionHospitalCode(String proxiedInspectionHospitalCode) {
        this.proxiedInspectionHospitalCode = proxiedInspectionHospitalCode;
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

    public String getNhiIcd10Cm() {
        return nhiIcd10Cm;
    }

    public TreatmentProcedure nhiIcd10Cm(String nhiIcd10Cm) {
        this.nhiIcd10Cm = nhiIcd10Cm;
        return this;
    }

    public void setNhiIcd10Cm(String nhiIcd10Cm) {
        this.nhiIcd10Cm = nhiIcd10Cm;
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

    public Set<Todo> getTodos() {
        return todos;
    }

    public TreatmentProcedure todos(Set<Todo> todos) {
        this.todos = todos;
        return this;
    }

    public TreatmentProcedure addTodo(Todo todo) {
        this.todos.add(todo);
        todo.getTreatmentProcedures().add(this);
        return this;
    }

    public TreatmentProcedure removeTodo(Todo todo) {
        this.todos.remove(todo);
        todo.getTreatmentProcedures().remove(this);
        return this;
    }

    public void setTodos(Set<Todo> todos) {
        this.todos = todos;
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

    public TreatmentProcedure nhiExtendTreatmentProcedure(NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        this.nhiExtendTreatmentProcedure = nhiExtendTreatmentProcedure;
        return this;
    }

    public void setNhiExtendTreatmentProcedure(NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) {
        this.nhiExtendTreatmentProcedure = nhiExtendTreatmentProcedure;
    }

    @Override
    public Instant getCreatedDate() {
        return createdDate;
    }

    @Override
    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
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
        String s = "TreatmentProcedure{" +
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
            "}";

        if (this.getNhiProcedure() != null) {
            s = s + this.getNhiProcedure().toString();
        }

        if (this.getNhiExtendTreatmentProcedure() != null) {
            s = s + this.getNhiExtendTreatmentProcedure().toString();
        }

        return s;
    }
}
