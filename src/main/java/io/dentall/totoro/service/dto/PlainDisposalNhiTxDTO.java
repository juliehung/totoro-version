package io.dentall.totoro.service.dto;

import java.time.Instant;

public interface PlainDisposalNhiTxDTO {
    Instant getArrivalTime();

    String getDoctorName();

    Long getPatientId();

    String getPatientName();

    Instant getBirth();

    String getCode();

    String getPhone();

    String getNote();
}
