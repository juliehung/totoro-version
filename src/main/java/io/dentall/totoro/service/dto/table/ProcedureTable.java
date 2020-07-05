package io.dentall.totoro.service.dto.table;

public interface ProcedureTable {
    Long getId();
    String getContent();
    Double getPrice();

    // relationship
    Long getProcedureType_Id();
    Long getDoctor_Id();
}
