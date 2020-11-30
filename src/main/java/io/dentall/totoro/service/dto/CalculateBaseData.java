package io.dentall.totoro.service.dto;

import java.time.Instant;

public interface CalculateBaseData {
    Long getDisposalId();

    Instant getDateTime();

    String getExaminationCode();

    Integer getExaminationPoint();

    String getPatientIdentity();

    String getSerialNumber();

    String getCode();

    Long getPatientId();

    Long getDoctorId();

}
