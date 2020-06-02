package io.dentall.totoro.service.dto.table;

import java.time.Instant;

public interface AuditingElement {
    String getCreatedBy();
    String getLastModifiedBy();
    Instant getCreatedDate();
    Instant getLastModifiedDate();
}
