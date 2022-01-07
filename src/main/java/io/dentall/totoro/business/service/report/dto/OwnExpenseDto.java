package io.dentall.totoro.business.service.report.dto;

import java.time.LocalDate;

public interface OwnExpenseDto extends DisposalDto {

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

    Long getProcedureId();

    String getProcedureName();

    String getProcedureMinorType();

    Long getTreatmentProcedureId();
}
