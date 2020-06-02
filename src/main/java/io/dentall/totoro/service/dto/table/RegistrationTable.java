package io.dentall.totoro.service.dto.table;

import io.dentall.totoro.domain.enumeration.RegistrationStatus;

import java.time.Instant;

public interface RegistrationTable extends AuditingElement {
    Long getId();
    RegistrationStatus getStatus();
    Instant getArrivalTime();
    String getType();
    Boolean getOnSite();
    Boolean getNoCard();
    String getAbnormalCode();

    // Relationship
    Long getAccounting_Id();
}
