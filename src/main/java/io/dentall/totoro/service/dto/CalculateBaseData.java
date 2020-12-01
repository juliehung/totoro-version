package io.dentall.totoro.service.dto;

import java.time.Instant;

public interface CalculateBaseData {
    // Disposal
    Long getDisposalId();

    // Nhi extend disposal
    Instant getDateTime();

    String getExaminationCode();

    Integer getExaminationPoint();

    String getPatientIdentity();

    String getSerialNumber();

    String getCopayment();

    // Nhi procedure
    String getTxCode();

    Integer getTxPoint();

    String getSpecialCode();

    // Appointment
    Long getPatientId();

    Long getDoctorId();

}
