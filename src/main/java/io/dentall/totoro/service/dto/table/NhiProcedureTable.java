package io.dentall.totoro.service.dto.table;

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

    // Relationship
    Long getNhiProcedureType_Id();
    Long getNhiIcd9Cm_Id();
}
