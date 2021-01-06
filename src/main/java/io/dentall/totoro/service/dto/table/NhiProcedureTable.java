package io.dentall.totoro.service.dto.table;

import java.time.Instant;

public interface NhiProcedureTable {
    Long getId();
    String getCode();
    String getName();
    Integer getPoint();
    String getEnglishName();
    Long getDefaultIcd10CmId();
    String getDescription();
    String getExclude();
    String getFdi();
    String getSpecificCode();
    String getChiefComplaint();
    Instant getExpirationTime();

    // Relationship
    Long getNhiProcedureType_Id();
    Long getNhiIcd9Cm_Id();
}
