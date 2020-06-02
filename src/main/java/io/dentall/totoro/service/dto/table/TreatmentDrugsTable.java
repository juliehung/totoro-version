package io.dentall.totoro.service.dto.table;

public interface TreatmentDrugsTable {
    Long getId();
    Integer getDay();
    String getFrequency();
    String getWay();
    Double getQuantity();

    // Relationship
    Long getPrescription_Id();
    Long getDrug_Id();
}
