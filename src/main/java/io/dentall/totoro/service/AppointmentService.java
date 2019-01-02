package io.dentall.totoro.service;

import io.dentall.totoro.domain.Appointment;
import io.dentall.totoro.domain.Registration;
import io.dentall.totoro.repository.AppointmentRepository;
import io.dentall.totoro.repository.ExtendUserRepository;
import io.dentall.totoro.repository.RegistrationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Appointment.
 */
@Service
@Transactional
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

    /**
     * Save a appointment.
     *
     * @param appointment the entity to save
     * @return the persisted entity
     */
    public Appointment save(Appointment appointment) {
        log.debug("Request to save Appointment : {}", appointment);
        return appointmentRepository.save(appointment);
    }

    /**
     * Get all the appointments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Appointment> findAll(Pageable pageable) {
        log.debug("Request to get all Appointments");
        return appointmentRepository.findAll(pageable);
    }


    /**
     * Get one appointment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Appointment> findOne(Long id) {
        log.debug("Request to get Appointment : {}", id);
        return appointmentRepository.findById(id);
    }

    /**
     * Delete the appointment by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Appointment : {}", id);
        appointmentRepository.deleteById(id);
    }

    public List<Appointment> getAppointmentsForPatientCard(Instant start, Instant end) {
        if (start == null && end == null) {
            // default expectedArrivalTime is today
            start = OffsetDateTime.now().toZonedDateTime().with(LocalTime.MIN).toInstant();
            end = OffsetDateTime.now().toZonedDateTime().with(LocalTime.MAX).toInstant();
            log.debug("REST request to get appointment patient cards between {} and {}", start, end);
            return appointmentRepository.findByRegistrationIsNullAndExpectedArrivalTimeBetweenOrderByExpectedArrivalTimeAsc(start, end);
        }

        if (start != null && end != null) {
            log.debug("REST request to get appointment patient cards between {} and {}", start, end);
            return appointmentRepository.findByExpectedArrivalTimeBetweenOrderByExpectedArrivalTimeAsc(start, end);
        }

        return null;
    }

    @Transactional
    public Optional<Appointment> updateAppointment(Appointment updateAppointment) {
        return appointmentRepository.findById(updateAppointment.getId()).map(appointment -> {
            // registration
            if (updateAppointment.getRegistration() != null) {
                log.debug("Update Registration({}) of Appointment(id: {})", updateAppointment.getRegistration(), updateAppointment.getId());
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

            if (updateAppointment.getColorId() != null) {
                appointment.setColorId((updateAppointment.getColorId()));
            }

            if (updateAppointment.isArchived() != null) {
                appointment.setArchived((updateAppointment.isArchived()));
            }

            // doctor
            if (updateAppointment.getDoctor() != null) {
                log.debug("Update Doctor({}) of Appointment(id: {})", updateAppointment.getDoctor(), updateAppointment.getId());
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
