package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.RegistrationStatus;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.util.ProblemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;

/**
 * Service class for managing registrations.
 */
@Service
@Transactional
public class RegistrationService {

    private final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    private final RegistrationRepository registrationRepository;

    private final AccountingService accountingService;

    private final BroadcastService broadcastService;

    private final RegistrationDelRepository registrationDelRepository;

    private final PatientService patientService;

    private final AppointmentService appointmentService;

    public RegistrationService(
        RegistrationRepository registrationRepository,
        AccountingService accountingService,
        BroadcastService broadcastService,
        RegistrationDelRepository registrationDelRepository,
        PatientService patientService,
        AppointmentService appointmentService
    ) {
        this.registrationRepository = registrationRepository;
        this.accountingService = accountingService;
        this.broadcastService = broadcastService;
        this.registrationDelRepository = registrationDelRepository;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    /**
     * Save a registration.
     *
     * @param registration the entity to save
     * @return the persisted entity
     */
    public Registration save(Registration registration) {
        log.debug("Request to save Registration : {}", registration);

        Accounting accounting = getAccounting(registration);
        registration = registrationRepository.save(registration.accounting(null));
        registration.setAccounting(accounting);

        return registration;
    }

    /**
     * Save a registration and broadcast patient status.
     *
     * @param registration the entity to save
     * @param patientName the name of patient
     * @return the persisted entity
     */
    public Registration save(Registration registration, String patientName) {
        registration = save(registration);
        broadcastService.broadcastRegistrationStatus(patientName, registration.getStatus());

        return registration;
    }

    /**
     * Update the registration.
     *
     * @param updateRegistration the update entity
     */
    public Registration update(Registration updateRegistration) {
        log.debug("Request to update Registration : {}", updateRegistration);

        return registrationRepository
            .findById(updateRegistration.getId())
            .map(registration -> {
                if (updateRegistration.getStatus() != null) {
                    registration.setStatus(updateRegistration.getStatus());

                    if (registration.getAppointment() != null) {
                        if (registration.getAppointment().getPatient() != null) {
                            broadcastService.broadcastRegistrationStatus(registration.getAppointment().getPatient().getName(), registration.getStatus());
                        }
                    }
                }

                if (updateRegistration.getArrivalTime() != null) {
                    registration.setArrivalTime(updateRegistration.getArrivalTime());
                }

                if (updateRegistration.getType() != null) {
                    registration.setType(updateRegistration.getType());
                }

                if (updateRegistration.isOnSite() != null) {
                    registration.setOnSite(updateRegistration.isOnSite());
                }

                if (updateRegistration.isNoCard() != null) {
                    registration.setNoCard(updateRegistration.isNoCard());
                }

                if (updateRegistration.getAccounting() != null) {
                    registration.setAccounting(getAccounting(updateRegistration));
                }

                return registration;
            })
            .get();
    }

    /**
     * Delete the registration and save registrationDel
     *
     * @param id the id of the registration to delete
     */
    public void delete(Long id) {
        log.debug("Request to delete Registration({})", id);

        registrationRepository
            .findById(id)
            .ifPresent(registration ->  {
                if (registration.getDisposal() != null) {
                    throw new ProblemUtil("A registration which has disposal cannot delete", Status.UNPROCESSABLE_ENTITY);
                }

                if (registration.getStatus() == RegistrationStatus.FINISHED) {
                    throw new ProblemUtil("A registration which was finished cannot delete", Status.UNPROCESSABLE_ENTITY);
                }

                Appointment appointment = registration.getAppointment();
                appointment.setRegistration(null);
                Accounting accounting = registration.getAccounting();

                registrationDelRepository.save(new RegistrationDel()
                    .status(registration.getStatus())
                    .arrivalTime(registration.getArrivalTime())
                    .type(registration.getType())
                    .onSite(registration.isOnSite())
                    .noCard(registration.isNoCard())
                    .appointmentId(appointment.getId())
                    .accountingId(accounting == null ? null : accounting.registration(null).getId())
                );
                registrationRepository.deleteById(id);

                // On-site registration
                if (appointment.getExpectedArrivalTime().equals(registration.getArrivalTime())) {
                    appointmentService.delete(appointment.getId());
                }

                patientService.setNewPatient(appointment.getPatient());
            });
    }

    private Accounting getAccounting(Registration registration) {
        Accounting accounting = registration.getAccounting();
        if (accounting != null) {
            accounting = accounting.getId() == null ? accountingService.save(accounting) : accountingService.update(accounting);
        }

        return accounting;
    }
}
