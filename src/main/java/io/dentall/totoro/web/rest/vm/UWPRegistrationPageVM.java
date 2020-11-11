package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.enumeration.Gender;
import io.dentall.totoro.domain.enumeration.RegistrationStatus;

import java.time.Instant;
import java.time.LocalDate;

public interface UWPRegistrationPageVM {

    // patient
    Long getPatientId();
    String getPatientName();
    String getPatientDisplayName();
    LocalDate getPatientBirth();
    String getPatientMedicalId();
    Gender getPatientGender();
    String getPatientNationalId();

    // disposal
    Long getDisposalId();

    // apointment
    Long getAppointmentId();
    String getAppointmentNote();
    Instant getAppointmentExpectedArrivalTime();
    Boolean getAppointmentMicroscope();
    Boolean getAppointmentBaseFloor();

    // registration
    Long getRegistrationId();
    Instant getRegistrationArrivalTime();
    RegistrationStatus getRegistrationStatus();
    String getRegistrationAbnormalCode();
    String getRegistrationType();
    Boolean getRegistrationNoCard();

    // User
    String getUserFirstName();
    String getUserLastName();

    // nhi extend disposal
    String getNhiExtendDisposalA17();
    String getNhiExtendDisposalA18();
    String getNhiExtendDisposalA23();
    String getNhiExtendDisposalA54();

    // accounting
    Instant getAccountingTransactionTime();

    // other needed
    String getPatientTags();
    Long getProcedureCounter();
    Long getNhiProcedureCounter();
}
