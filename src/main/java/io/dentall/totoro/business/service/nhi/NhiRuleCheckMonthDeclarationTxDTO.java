package io.dentall.totoro.business.service.nhi;

import java.time.Instant;

public interface NhiRuleCheckMonthDeclarationTxDTO {

    Long getDisposalId();

    // a17 or a54
    String getDisposalTime();

    Instant getDisplayDisposalTime();

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
