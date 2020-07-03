package io.dentall.totoro.service.dto.table;

public interface TreatmentPlanTable extends AuditingElement {

    Long getId();
    Boolean getActivated();
    String getName();

    // Relationship
    Long getTreatment_Id();
}
