package io.dentall.totoro.service.dto.table;

import io.dentall.totoro.domain.enumeration.DisposalStatus;

import java.time.Instant;

public interface DisposalTable extends AuditingElement {
    Long getId();
    DisposalStatus getStatus();
    Double getTotal();
    Instant getDateTime();
    Instant getDateTimeEnd();
    String getChiefComplaint();

    // Relationship
    Long getPrescription_Id();
    @Deprecated
    Long getTodo_Id();
    Long getRegistration_Id();

}
