package io.dentall.totoro.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dentall.totoro.domain.enumeration.NhiExtendDisposalUploadStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;
import java.time.LocalDate;

public interface MonthDisposalDTO {

    Long getDisposalId();

    Instant getDisposalDateTime();

    @JsonProperty(value = "id")
    Long getNhiExtendDisposalId();

    String getA11();

    String getA12();

    String getA13();

    String getA14();

    String getA15();

    String getA16();

    String getA17();

    String getA18();

    String getA19();

    String getA22();

    String getA23();

    String getA25();

    String getA26();

    String getA27();

    String getA31();

    String getA32();

    String getA41();

    String getA42();

    String getA43();

    String getA44();

    String getA54();

    @Enumerated(EnumType.STRING)
    NhiExtendDisposalUploadStatus getUploadStatus();

    String getExaminationCode();

    Integer getExaminationPoint();

    String getPatientIdentity();

    String getSerialNumber();

    Long getPatientId();

    String getCategory();

    LocalDate getReplenishmentDate();

    Boolean getCheckedMonthDeclaration();

    Boolean getCheckedAuditing();

    String getPatientName();

    Boolean getVipPatient();

    Long getDependedDisposalId();
}
