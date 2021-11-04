package io.dentall.totoro.service.dto.table;

import io.dentall.totoro.domain.enumeration.PrescriptionMode;
import io.dentall.totoro.domain.enumeration.TreatmentProcedureStatus;

import java.time.Instant;

public interface TreatmentProcedureTable extends AuditingElement {
    Long getId();
    TreatmentProcedureStatus getStatus();
    Integer getQuantity();
    Double getTotal();
    String getNote();
    Instant getCompletedDate();
    Double getPrice();
    String getNhiCategory();
    String getNhiDescription();
    String getNhiIcd10Cm();
    String getProxiedInspectionHospitalCode();
    PrescriptionMode getMode();

    // Relationship
    Long getNhiExtendTreatmentProcedure_Id();
    Long getNhiProcedure_Id();
    Long getTreatmentTask_Id();
    Long getProcedure_Id();
    Long getAppointment_Id();
    Long getDisposal_Id();
    Long getDoctor_Id();
}
