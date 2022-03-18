package io.dentall.totoro.business.service.report.dto;

import java.time.LocalDate;

public interface NhiDto extends DisposalDto {

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

    String getExamCode();

    Long getExamPoint();

    Long getProcedureId();

    String getProcedureCode();

    String getProcedureTooth();

    String getProcedureSurface();

    Long getProcedurePoint();

}
