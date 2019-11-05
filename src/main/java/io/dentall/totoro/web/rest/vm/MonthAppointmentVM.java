package io.dentall.totoro.web.rest.vm;

import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.service.dto.AppointmentDTO;

import java.time.Instant;

public class MonthAppointmentVM {

    private final Instant expectedArrivalTime;

    private final Integer requiredTreatmentTime;

    private final Long patientId;

    private final String patientName;

    private final ExtendUser doctor;

    public MonthAppointmentVM(AppointmentDTO appointmentDTO) {
        this.expectedArrivalTime = appointmentDTO.getExpectedArrivalTime();
        this.requiredTreatmentTime = appointmentDTO.getRequiredTreatmentTime();
        this.patientId = appointmentDTO.getPatientId();
        this.patientName = appointmentDTO.getPatientName();
        this.doctor = appointmentDTO.getDoctor();
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

    public ExtendUser getDoctor() {
        return doctor;
    }
}
