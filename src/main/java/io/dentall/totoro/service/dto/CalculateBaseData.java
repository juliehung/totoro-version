package io.dentall.totoro.service.dto;

import java.time.Instant;

public interface CalculateBaseData {
    // Disposal
    Long getDisposalId();

    Instant getDisposalDate();

    // Nhi extend disposal
    Instant getDateTime();

    String getExaminationCode();

    Integer getExaminationPoint();

    String getPatientIdentity();

    String getSerialNumber();

    String getVisitTotalPoint();

    String getCopayment();

    // Nhi procedure
    String getTxCode();

    Double getTxPoint();

    String getSpecificCode();

    // Appointment
    Long getPatientId();

    Long getDoctorId();

    // Patient
    String getPatientName();
    Boolean getVipPatient();

}
