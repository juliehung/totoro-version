package io.dentall.totoro.service.dto;

import java.time.Instant;
import java.time.LocalDate;

public interface PlainDisposalInfoDTO {
    Instant getArrivalTime();
    String getDoctorName();
    Long getPatientId();
    String getPatientName();
    String getNote();
    String getPhone();
    String getTargetInfo();
    LocalDate getPatientBirth();
}
