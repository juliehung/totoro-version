package io.dentall.totoro.service.dto.table;

import io.dentall.totoro.domain.enumeration.NhiDayUploadDetailType;

public interface NhiDayUploadDetailsTable extends AuditingElement {
    Long getId();
    NhiDayUploadDetailType getType();

    // Relationship
    Long getNhiDayUpload_Id();
}
