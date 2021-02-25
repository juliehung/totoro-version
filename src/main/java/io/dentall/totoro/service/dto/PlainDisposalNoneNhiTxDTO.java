package io.dentall.totoro.service.dto;

import java.time.Instant;

public interface PlainDisposalNoneNhiTxDTO {
    Instant getArrivalTime();

    String getDoctorName();

    Long getPatientId();

    String getPatientName();

    Instant getBirth();

    String getContent();

    String getPhone();

    String getNote();
}
