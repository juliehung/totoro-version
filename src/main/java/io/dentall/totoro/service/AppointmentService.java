package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.TreatmentType;
import io.dentall.totoro.repository.*;
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
import java.util.Set;

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

    private final AccountingRepository accountingRepository;

    private final PatientRepository patientRepository;

    private final HospitalRepository hospitalRepository;

    private final TreatmentProcedureRepository treatmentProcedureRepository;

    private final TreatmentService treatmentService;

    public AppointmentService(
        AppointmentRepository appointmentRepository,
        RegistrationRepository registrationRepository,
        ExtendUserRepository extendUserRepository,
        AccountingRepository accountingRepository,
        PatientRepository patientRepository,
        HospitalRepository hospitalRepository,
        TreatmentProcedureRepository treatmentProcedureRepository,
        TreatmentService treatmentService
    ) {
        this.appointmentRepository = appointmentRepository;
        this.registrationRepository = registrationRepository;
        this.extendUserRepository = extendUserRepository;
        this.accountingRepository = accountingRepository;
        this.patientRepository = patientRepository;
        this.hospitalRepository = hospitalRepository;
        this.treatmentProcedureRepository = treatmentProcedureRepository;
        this.treatmentService = treatmentService;
    }

    /**
     * Save a appointment.
     *
     * @param appointment the entity to save
     * @return the persisted entity
     */
    public Appointment save(Appointment appointment) {
        log.debug("Request to save Appointment : {}", appointment);

        // patient
        if (appointment.getPatient() != null) {
            Patient patient = appointment.getPatient();
            if (patient.getId() == null) {
                log.debug("Save Patient({})", patient);
                appointment.setPatient(patientRepository.save(patient));
                treatmentService.save(new Treatment().name("General Treatment").type(TreatmentType.GENERAL).patient(patient));
            }
        }

        // registration
        if (appointment.getRegistration() != null) {
            Registration registration = appointment.getRegistration();
            if (registration.getId() == null) {
                log.debug("Save Registration({})", registration);
                appointment.setRegistration(registrationRepository.save(registration));
            }

            // accounting
            if (registration.getAccounting() != null) {
                Accounting accounting = registration.getAccounting();
                if (accounting.getId() == null) {
                    log.debug("Save Accounting({})", accounting);
                    registration.setAccounting(accountingRepository.save(accounting));
                }
            }
        }

        // treatmentProcedures
        if (appointment.getTreatmentProcedures() != null) {
            setAppointmentOfTreatmentProcedure(appointment.getTreatmentProcedures(), appointment);
        }

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

                if (registration == null) {
                    if (updateRegistration.getAccounting() != null) {
                        log.debug("Save Accounting({})", updateRegistration.getAccounting());
                        updateRegistration.accounting(accountingRepository.save(updateRegistration.getAccounting()));
                    }

                    appointment.setRegistration(registrationRepository.save(updateRegistration));
                } else {
                    appointment.setRegistration(updateRegistration(registration, updateRegistration));
                }
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
            if (updateAppointment.getDoctor() != null && updateAppointment.getDoctor().getId() != null) {
                log.debug("Update Doctor({}) of Appointment(id: {})", updateAppointment.getDoctor(), updateAppointment.getId());
                appointment.setDoctor(extendUserRepository.findById(updateAppointment.getDoctor().getId()).orElse(null));
            }

            // treatmentProcedures
            if (updateAppointment.getTreatmentProcedures() != null) {
                // clear
                setAppointmentOfTreatmentProcedure(appointment.getTreatmentProcedures(), null);
                appointment.getTreatmentProcedures().clear();

                setAppointmentOfTreatmentProcedure(updateAppointment.getTreatmentProcedures(), appointment);
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

        if (updateRegistration.getAccounting() != null) {
            log.debug("Update Accounting({}) of Registration(id: {})", updateRegistration.getAccounting(), updateRegistration.getId());
            Accounting accounting = registration.getAccounting();
            Accounting updateAccounting = updateRegistration.getAccounting();
            registration.setAccounting(accounting == null ? accountingRepository.save(updateAccounting) : updateAccounting(accounting, updateAccounting));
        }

        return registration;
    }

    private Accounting updateAccounting(Accounting accounting, Accounting updateAccounting) {
        if (updateAccounting.getRegistrationFee() != null) {
            accounting.setRegistrationFee(updateAccounting.getRegistrationFee());
        }

        if (updateAccounting.getPartialBurden() != null) {
            accounting.setPartialBurden(updateAccounting.getPartialBurden());
        }

        if (updateAccounting.getBurdenCost() != null) {
            accounting.setBurdenCost(updateAccounting.getBurdenCost());
        }

        if (updateAccounting.getDeposit() != null) {
            accounting.setDeposit(updateAccounting.getDeposit());
        }

        if (updateAccounting.getOwnExpense() != null) {
            accounting.setOwnExpense(updateAccounting.getOwnExpense());
        }

        if (updateAccounting.getOther() != null) {
            accounting.setOther(updateAccounting.getOther());
        }

        if (updateAccounting.getPatientIdentity() != null) {
            accounting.setPatientIdentity(updateAccounting.getPatientIdentity());
        }

        if (updateAccounting.getDiscountReason() != null) {
            accounting.setDiscountReason(updateAccounting.getDiscountReason());
        }

        if (updateAccounting.getDiscount() != null) {
            accounting.setDiscount(updateAccounting.getDiscount());
        }

        // hospital
        if (updateAccounting.getHospital() != null && updateAccounting.getHospital().getId() != null) {
            log.debug("Update Hospital({}) of Accounting(id: {})", updateAccounting.getHospital(), updateAccounting.getId());
            accounting.setHospital(hospitalRepository.findById(updateAccounting.getHospital().getId()).orElse(null));
        }

        return accounting;
    }

    private void setAppointmentOfTreatmentProcedure(Set<TreatmentProcedure> treatmentProcedures, Appointment appointment) {
        treatmentProcedures.stream().map(TreatmentProcedure::getId).forEach(id ->
            treatmentProcedureRepository.findById(id).ifPresent(treatmentProcedure -> {
                treatmentProcedure.setAppointment(appointment);
                if (appointment != null) {
                    appointment.getTreatmentProcedures().add(treatmentProcedure);
                }
            })
        );
    }
}
