package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Appointment.
 */
@Service
@Transactional
public class AppointmentService {

    private final Logger log = LoggerFactory.getLogger(AppointmentService.class);

    private final AppointmentRepository appointmentRepository;

    private final ExtendUserRepository extendUserRepository;

    private final PatientService patientService;

    private final RegistrationService registrationService;

    private final RelationshipService relationshipService;

    public AppointmentService(
        AppointmentRepository appointmentRepository,
        ExtendUserRepository extendUserRepository,
        PatientService patientService,
        @Lazy RegistrationService registrationService,
        RelationshipService relationshipService
    ) {
        this.appointmentRepository = appointmentRepository;
        this.extendUserRepository = extendUserRepository;
        this.patientService = patientService;
        this.registrationService = registrationService;
        this.relationshipService = relationshipService;
    }

    /**
     * Save a appointment.
     *
     * @param appointment the entity to save
     * @return the persisted entity
     */
    public Appointment save(Appointment appointment) {
        log.debug("Request to save Appointment : {}", appointment);

        Patient patient = getPatient(appointment);

        Set<TreatmentProcedure> treatmentProcedures = appointment.getTreatmentProcedures();
        appointment = appointmentRepository.save(appointment.treatmentProcedures(null));
        relationshipService.addRelationshipWithTreatmentProcedures(appointment.treatmentProcedures(treatmentProcedures));
        appointment.setPatient(patient);
        patient.getAppointments().add(appointment);
        Registration registration = getRegistration(appointment);
        appointment.setRegistration(registration);

        patientService.setNewPatient(patient);

        return appointment;
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

        appointmentRepository.findById(id).ifPresent(appointment -> {
            if (appointment.getPatient() != null) {
                Patient patient = appointment.getPatient();
                patient.getAppointments().remove(appointment);
            }

            relationshipService.deleteTreatmentProcedures(appointment.getTreatmentProcedures());
        });
        appointmentRepository.deleteById(id);
    }

    public Appointment update(Appointment updateAppointment) {
        return appointmentRepository
            .findById(updateAppointment.getId())
            .map(appointment -> {
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

                if (updateAppointment.isBaseFloor() != null) {
                    appointment.setBaseFloor((updateAppointment.isBaseFloor()));
                }

                if (updateAppointment.getColorId() != null) {
                    appointment.setColorId((updateAppointment.getColorId()));
                }

                if (updateAppointment.isArchived() != null) {
                    appointment.setArchived((updateAppointment.isArchived()));
                }

                if (updateAppointment.isContacted() != null) {
                    appointment.setContacted((updateAppointment.isContacted()));
                }

                // doctor
                if (updateAppointment.getDoctor() != null && updateAppointment.getDoctor().getId() != null) {
                    log.debug("Update Doctor({}) of Appointment(id: {})", updateAppointment.getDoctor(), updateAppointment.getId());
                    extendUserRepository.findById(updateAppointment.getDoctor().getId()).ifPresent(appointment::setDoctor);
                }

                // patient
                if (updateAppointment.getPatient() != null) {
                    log.debug("Update Patient({}) of Appointment(id: {})", updateAppointment.getPatient(), updateAppointment.getId());
                    appointment.setPatient(getPatient(updateAppointment));
                }

                // registration
                if (updateAppointment.getRegistration() != null) {
                    log.debug("Update Registration({}) of Appointment(id: {})", updateAppointment.getRegistration(), updateAppointment.getId());
                    appointment.setRegistration(getRegistration(updateAppointment.patient(appointment.getPatient())));
                }

                // treatmentProcedures
                if (updateAppointment.getTreatmentProcedures() != null) {
                    Set<Long> updateIds = updateAppointment.getTreatmentProcedures().stream().map(TreatmentProcedure::getId).collect(Collectors.toSet());
                    relationshipService.deleteTreatmentProcedures(
                        appointment
                            .getTreatmentProcedures()
                            .stream()
                            .filter(treatmentProcedure -> !updateIds.contains(treatmentProcedure.getId()))
                            .collect(Collectors.toSet())
                    );
                    relationshipService.addRelationshipWithTreatmentProcedures(appointment.treatmentProcedures(updateAppointment.getTreatmentProcedures()));
                }

                patientService.setNewPatient(appointment.getPatient());

                return appointment;
            })
            .get();
    }

    private Patient getPatient(Appointment appointment) {
        Patient patient = appointment.getPatient();
        if (patient != null) {
            patient = patient.getId() == null ? patientService.save(patient) : patientService.update(patient);
        }

        return patient;
    }

    private Registration getRegistration(Appointment appointment) {
        Registration registration = appointment.getRegistration();
        if (registration != null) {
            registration = registration.getId() == null ? registrationService.save(registration, appointment.getPatient().getName()) : registrationService.update(registration);
        }

        return registration;
    }
}
