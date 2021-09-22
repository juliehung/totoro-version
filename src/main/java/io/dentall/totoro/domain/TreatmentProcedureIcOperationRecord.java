package io.dentall.totoro.domain;

import io.dentall.totoro.domain.enumeration.TreatmentProcedureIcOperationType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "treatment_procedure_ic_operation_record")
public class TreatmentProcedureIcOperationRecord extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(
        name = "treatmentProcedureIcOperationRecord",
        sequenceName = "treatment_procedure_ic_operation_record_seq",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "treatmentProcedureIcOperationRecord"
    )
    private Long id;

    @Column(name = "treatment_procedure_id")
    private Long treatmentProcedureId;

    @Column(name = "operation")
    @Enumerated(EnumType.STRING)
    @NotNull
    private TreatmentProcedureIcOperationType operation;

    @Column(name = "error_code")
    @NotNull
    private String errorCode;

    @Column(name = "error_message")
    @NotNull
    private String errorMessage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TreatmentProcedureIcOperationType getOperation() {
        return operation;
    }

    public void setOperation(TreatmentProcedureIcOperationType operation) {
        this.operation = operation;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Long getTreatmentProcedureId() {
        return treatmentProcedureId;
    }

    public void setTreatmentProcedureId(Long treatmentProcedureId) {
        this.treatmentProcedureId = treatmentProcedureId;
    }
}
