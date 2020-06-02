package io.dentall.totoro.service.dto.table;

public interface ToothTable extends AuditingElement {
    Long getId();
    String getPosition();
    String getSurface();
    String getStatus();

    // Relationship
    Long getTreatmentProcedure_Id();
    @Deprecated
    Long getDisposal_Id();
    @Deprecated
    Long getPatient_Id();
}
