package io.dentall.totoro.service.dto.table;

import io.dentall.totoro.domain.enumeration.TreatmentType;

public interface TreatmentTable extends AuditingElement {

    Long getId();
    String getName();
    String getChiefComplaint();
    String getGoal();
    String getNote();
    String getFinding();
    TreatmentType getType();

    // Relationship
    Long getPatient_Id();
}
