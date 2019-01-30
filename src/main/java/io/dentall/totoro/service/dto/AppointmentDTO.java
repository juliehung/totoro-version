package io.dentall.totoro.service.dto;

import io.dentall.totoro.domain.Appointment;
import io.dentall.totoro.domain.enumeration.AppointmentStatus;

import javax.validation.constraints.Null;

/**
 * A DTO representing a appointment
 */
public class AppointmentDTO extends Appointment {

    @Null(groups = NullGroup.class)
    private AppointmentStatus status;
}
