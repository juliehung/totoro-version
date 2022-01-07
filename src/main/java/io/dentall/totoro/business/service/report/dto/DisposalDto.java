package io.dentall.totoro.business.service.report.dto;

import java.time.Instant;
import java.time.LocalDate;

public interface DisposalDto {

    Long getDisposalId();

    LocalDate getDisposalDate();

    String getCardNumber();

    Long getDoctorId();

    String getDoctorName();

    Long getPatientId();

    String getPatientName();

    LocalDate getPatientBirth();

    String getPatientPhone();

    String getPatientNote();

    Instant getRegistrationDate();

}
