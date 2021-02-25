package io.dentall.totoro.service.dto;

import java.time.Instant;

public interface PlainDisposalDrugDTO {
    Instant getArrivalTime();

    String getDoctorName();

    Long getPatientId();

    String getPatientName();

    Instant getBirth();

    String getDrugName();

    String getPhone();

    String getNote();
}
