package io.dentall.totoro.business.service.report.dto;

import java.time.LocalDate;

public interface DrugDto extends DisposalDto {

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

    Long getDrugId();

    String getDrugName();

    String getDrugNhiCode();

    String getDrugDay();

    String getDrugWay();

    String getDrugFrequency();
}
