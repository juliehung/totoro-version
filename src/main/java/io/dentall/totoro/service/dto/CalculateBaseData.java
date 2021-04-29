package io.dentall.totoro.service.dto;

import io.dentall.totoro.business.service.nhi.NhiSpecialCode;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.Instant;

public interface CalculateBaseData {
    // Disposal
    Long getDisposalId();

    Instant getDisposalDate();

    // Nhi extend disposal
    Instant getDateTime();

    String getExaminationCode();

    Integer getExaminationPoint();

    String getPatientIdentity();

    String getSerialNumber();

    String getVisitTotalPoint();

    String getCopayment();

    /**
     * 就醫類別，a.k.a A23
     * @return
     */
    String getNhiCategory();

    // Nhi procedure
    String getTxCode();

    Double getTxPoint();

    @Enumerated(EnumType.STRING)
    NhiSpecialCode getSpecificCode();

    // Appointment
    Long getPatientId();

    Long getDoctorId();

    // Patient
    String getPatientName();
    Boolean getVipPatient();

}
