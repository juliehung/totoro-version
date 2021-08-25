package io.dentall.totoro.business.vm.nhi;

import io.dentall.totoro.business.service.nhi.NhiSpecialCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

public interface NhiMetricRawVM {

    Long getPatientId();

    String getPatientName();

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

    Long getNhiOriginPoint();

    // 特定治療代碼
    @Enumerated(EnumType.STRING)
    NhiSpecialCode getTreatmentProcedureSpecificCode();

    Long getDoctorId();

    String getDoctorName();
}
