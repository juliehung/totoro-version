package io.dentall.totoro.business.vm.nhi;

import java.time.LocalDate;

public interface NhiMetricBaseVM {

    Long getPatientId();

    LocalDate getPatientBirth();

    Long getDisposalId();

    // jhi_date
    LocalDate getDisposalDate();

    // A18
    String getCardNumber();

    // A19
    String getCardReplenishment();

    // A23
    String getNhiCategory();

    // A32
    String getPartialBurden();

    // replenishment date
    LocalDate getCardReplenishmentDisposalDate();

    String getExamCode();

    String getExamPoint();

    String getPatientIdentity();

    String getSerialNumber();

    String getTreatmentProcedureCode();

    String getTreatmentProcedureTooth();

    String getTreatmentProcedureSurface();

    Long getTreatmentProcedureTotal();

    // 特定治療代碼
    String getTreatmentProcedureSpecificCode();
}