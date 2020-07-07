package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.dto.table.AppointmentTable;
import io.dentall.totoro.service.dto.table.RegistrationTable;
import io.dentall.totoro.service.mapper.AppointmentMapper;
import io.dentall.totoro.service.mapper.PatientMapper;
import io.dentall.totoro.service.mapper.RegistrationMapper;
import io.dentall.totoro.service.util.MapperUtil;
import io.dentall.totoro.service.util.StreamUtil;
import io.dentall.totoro.web.rest.vm.MonthAppointmentVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

    private final DisposalRepository disposalRepository;

    private final RegistrationRepository registrationRepository;

    private final PatientRepository patientRepository;

    public AppointmentService(
        AppointmentRepository appointmentRepository,
        ExtendUserRepository extendUserRepository,
        PatientService patientService,
        @Lazy RegistrationService registrationService,
        RelationshipService relationshipService,
        DisposalRepository disposalRepository,
        RegistrationRepository registrationRepository,
        PatientRepository patientRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.extendUserRepository = extendUserRepository;
        this.patientService = patientService;
        this.registrationService = registrationService;
        this.relationshipService = relationshipService;
        this.disposalRepository = disposalRepository;
        this.registrationRepository = registrationRepository;
        this.patientRepository = patientRepository;
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
            StreamUtil.asStream(appointment.getTreatmentProcedures()).forEach(treatmentProcedure -> treatmentProcedure.setAppointment(null));
            relationshipService.deleteTreatmentProcedures(appointment.getTreatmentProcedures());

            if (appointment.getPatient() != null) {
                Patient patient = appointment.getPatient();
                patient.getAppointments().remove(appointment);
            }

            appointmentRepository.deleteById(id);
        });
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
                        StreamUtil.asStream(appointment.getTreatmentProcedures())
                            .filter(treatmentProcedure -> !updateIds.contains(treatmentProcedure.getId()))
                            .map(treatmentProcedure -> treatmentProcedure.appointment(null))
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

    public List<MonthAppointmentVM> findAppointmentBetween(
        Instant beginDate,
        Instant endDate
    ) {
        return
            appointmentRepository.findMonthAppointment(
                beginDate,
                endDate
            ).stream()
                .map(appointmentDTO -> {
                    MonthAppointmentVM monthAppointmentVM = new MonthAppointmentVM(appointmentDTO);
                    return monthAppointmentVM;
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<Appointment> getAppointmentProjectionByPatientId(Long id, Pageable page) {
        Page<AppointmentTable> appointmentTables = appointmentRepository.findByPatient_Id(id, page);
        List<Appointment> appointments = appointmentTables
            .stream()
            .map(AppointmentMapper::appointmentTableToAppointment)
            .map(appointment -> {
                Registration registration = appointment.getRegistration();
                if (registration != null && registration.getId() != null) {
                    setDisposal(registration);
                    registrationRepository.findById(registration.getId(), AppointmentRegistration.class)
                        .ifPresent(appointmentRegistration -> registration.setArrivalTime(appointmentRegistration.getArrivalTime()));
                }

                return appointment;
            })
            .collect(Collectors.toList());

        return new PageImpl<>(appointments, page, appointmentTables.getTotalElements());
    }

    @Transactional(readOnly = true)
    public List<Appointment> getAppointmentProjectionBetween(Instant start, Instant end) {
        return appointmentRepository.findByExpectedArrivalTimeBetweenOrderByExpectedArrivalTimeAsc(start, end)
            .stream()
            .map(AppointmentMapper::appointmentTableToAppointment)
            .map(appointment -> {
                Patient patient = appointment.getPatient();
                if (patient != null && patient.getId() != null) {
                    patientRepository.findPatientById(patient.getId())
                        .ifPresent(patientTable -> appointment.setPatient(PatientMapper.patientTableToPatient(patientTable)));
                }

                Registration reg = appointment.getRegistration();
                if (reg != null && reg.getId() != null) {
                    registrationRepository.findById(reg.getId(), RegistrationTable.class)
                        .ifPresent(registrationTable -> {
                            Registration registration = RegistrationMapper.registrationTableToRegistration(registrationTable);
                            setDisposal(registration);
                            appointment.setRegistration(registration);
                        });
                }

                ExtendUser extendUser = appointment.getDoctor();
                if (extendUser != null && extendUser.getId() != null) {
                    extendUserRepository
                        .findById(extendUser.getId(), AppointmentDoctor.class)
                        .ifPresent(appointmentDoctor -> {
                            User user = new User();
                            user.setId(appointmentDoctor.getUser_Id());
                            user.setFirstName(appointmentDoctor.getUser_FirstName());
                            MapperUtil.setNullAuditing(user);

                            extendUser.setUser(user);
                        });
                }

                return appointment;
            })
            .collect(Collectors.toList());
    }

    private void setDisposal(Registration registration) {
        disposalRepository
            .findByRegistration_Id(registration.getId())
            .ifPresent(appointmentRegistrationDisposal -> {
                Disposal disposal = new Disposal();
                disposal.setId(appointmentRegistrationDisposal.getId());
                MapperUtil.setNullAuditing(disposal);

                registration.setDisposal(disposal);
            });
    }

    public interface AppointmentRegistrationDisposal {

        Long getId();
    }

    public interface AppointmentRegistration {

        Instant getArrivalTime();
    }

    public interface AppointmentDoctor {

        Long getId();

        Long getUser_Id();

        String getUser_FirstName();
    }
}
