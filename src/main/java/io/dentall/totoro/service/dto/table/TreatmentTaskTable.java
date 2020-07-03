package io.dentall.totoro.service.dto.table;

public interface TreatmentTaskTable extends AuditingElement {

    Long getId();
    String getName();
    String getNote();

    // Relationship
    Long getTreatmentPlan_Id();
}
