package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.ExtendUser;

import java.time.Instant;

public class AppointmentDTO {

    private final Instant expectedArrivalTime;

    private final Integer requiredTreatmentTime;

    private final Long patientId;

    private final String patientName;

    private final ExtendUser doctor;

    public AppointmentDTO(
        Instant expectedArrivalTime,
        Integer requiredTreatmentTime,
        Long patientId,
        String patientName,
        ExtendUser doctor
    ) {
        this.expectedArrivalTime = expectedArrivalTime;
        this.requiredTreatmentTime = requiredTreatmentTime;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctor = doctor;
    }

    public ExtendUser getDoctor() {
        return doctor;
    }

    public Instant getExpectedArrivalTime() {
        return expectedArrivalTime;
    }

    public Integer getRequiredTreatmentTime() {
        return requiredTreatmentTime;
    }

    public Long getPatientId() {
        return patientId;
    }



    public String getPatientName() {
        return patientName;
    }

}
