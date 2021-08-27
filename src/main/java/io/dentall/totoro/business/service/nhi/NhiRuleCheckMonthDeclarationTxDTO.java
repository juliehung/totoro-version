package io.dentall.totoro.business.service.nhi;

public interface NhiRuleCheckMonthDeclarationTxDTO {

    Long getDisposalId();

    // a17 or a54
    String getDisposalTime();

    // a23
    String getNhiCategory();

    Long getDoctorId();

    String getDoctorName();

    Long getPatientId();

    String getPatientName();

    Long getTreatmentProcedureId();

    String getNhiCode();

    String getTeeth();

    String getSurface();

    String getNhiTxName();
}
