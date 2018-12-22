package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service class for managing appointments.
 */
@Service
public class AppointmentService {

    private final Logger log = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;

    private final RegistrationRepository registrationRepository;

    private final ExtendUserRepository extendUserRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, RegistrationRepository registrationRepository, ExtendUserRepository extendUserRepository) {
        this.appointmentRepository = appointmentRepository;
        this.registrationRepository = registrationRepository;
        this.extendUserRepository = extendUserRepository;
    }

    @Transactional
    public Optional<Appointment> updateAppointment(Appointment updateAppointment) {
        return appointmentRepository.findById(updateAppointment.getId()).map(appointment -> {
            // registration
            if (updateAppointment.getRegistration() != null) {
                log.debug("Update registration({}) of Appointment(id: {})", updateAppointment.getRegistration(), updateAppointment.getId());
                Registration registration = appointment.getRegistration();
                Registration updateRegistration = updateAppointment.getRegistration();
                appointment.setRegistration(registration == null ? registrationRepository.save(updateRegistration) : updateRegistration(registration, updateRegistration));
            }

            if (updateAppointment.getStatus() != null) {
                appointment.setStatus((updateAppointment.getStatus()));
            }

            if (updateAppointment.getSubject() != null) {
                appointment.setSubject((updateAppointment.getSubject()));
            }

            if (updateAppointment.getNote() != null) {
                appointment.setNote((updateAppointment.getNote()));
            }

            if (updateAppointment.getExpectedArrivalTime() != null) {
                appointment.setExpectedArrivalTime((updateAppointment.getExpectedArrivalTime()));
            }

            if (updateAppointment.getRequiredTreatmentTime() != null) {
                appointment.setRequiredTreatmentTime((updateAppointment.getRequiredTreatmentTime()));
            }

            if (updateAppointment.isMicroscope() != null) {
                appointment.setMicroscope((updateAppointment.isMicroscope()));
            }

            if (updateAppointment.isNewPatient() != null) {
                appointment.setNewPatient((updateAppointment.isNewPatient()));
            }

            if (updateAppointment.isBaseFloor() != null) {
                appointment.setBaseFloor((updateAppointment.isBaseFloor()));
            }

            if (updateAppointment.isArchived() != null) {
                appointment.setArchived((updateAppointment.isArchived()));
            }

            // doctor
            if (updateAppointment.getDoctor() != null) {
                log.debug("Update doctor({}) of Appointment(id: {})", updateAppointment.getDoctor(), updateAppointment.getId());
                appointment.setDoctor(extendUserRepository.findById(updateAppointment.getDoctor().getId()).orElse(null));
            }

            return appointment;
        });
    }

    private Registration updateRegistration(Registration registration, Registration updateRegistration) {
        if (updateRegistration.getStatus() != null) {
            registration.setStatus(updateRegistration.getStatus());
        }

        if (updateRegistration.getArrivalTime() != null) {
            registration.setArrivalTime(updateRegistration.getArrivalTime());
        }

        if (updateRegistration.getType() != null) {
            registration.setType(updateRegistration.getType());
        }

        if (updateRegistration.getType() != null) {
            registration.setType(updateRegistration.getType());
        }

        if (updateRegistration.isOnSite() != null) {
            registration.setOnSite(updateRegistration.isOnSite());
        }

        return registration;
    }
}
