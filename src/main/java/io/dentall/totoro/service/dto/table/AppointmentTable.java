package io.dentall.totoro.service.dto.table;

import io.dentall.totoro.domain.enumeration.AppointmentStatus;

import java.time.Instant;

public interface AppointmentTable extends AuditingElement {
    Long getId();
    AppointmentStatus getStatus();
    String getSubject();
    String getNote();
    Instant getExpectedArrivalTime();
    Integer getRequiredTreatmentTime();
    Boolean getMicroscope();
    Boolean getBaseFloor();
    Integer getColorId();
    Boolean getArchived();
    Boolean getContacted();

    //Relationship
    Long getPatient_Id();
    Long getRegistration_Id();
    Long getDoctorUser_Id();
}
