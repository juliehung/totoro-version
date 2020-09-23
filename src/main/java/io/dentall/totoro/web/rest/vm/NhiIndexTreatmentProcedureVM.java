package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.service.dto.table.AppointmentTable;

public interface NhiIndexTreatmentProcedureVM {

    // Patient id
    Long getPid();

    // User id a.k.a doctor's id
    Long getDid();

    // User first name a.k.a doctor's name
    String getDname();

    Long getDisposalId();

    Long getTreatmentProcedureId();

    String getPatientIdentity();

    String getSerialNumber();

    String getExaminationCode();

    Integer getExaminationPoint();

    // Nhi extend disposal
    String getA11();
    String getA12();
    String getA13();
    String getA14();
    String getA15();
    String getA16();
    String getA17();
    String getA18();
    String getA19();
    String getA22();
    String getA23();
    String getA25();
    String getA26();
    String getA27();
    String getA31();
    String getA32();
    String getA41();
    String getA42();
    String getA43();
    String getA44();
    String getA54();

    // Nhi treatment procedure
    String getA71();
    String getA72();
    String getA73();
    String getA74();
    String getA75();
    String getA76();
    String getA77();
    String getA78();
    String getA79();

}
