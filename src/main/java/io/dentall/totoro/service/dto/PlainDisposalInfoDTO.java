package io.dentall.totoro.service.dto;

import java.time.Instant;

public interface PlainDisposalInfoDTO {
    Instant getArrivalTime();
    String getDoctorName();
    Long getPatientId();
    String getPatientName();
    String getNote();
    String getPhone();
    String getTargetInfo();
}
