package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Service class for managing registrations.
 */
@Service
@Transactional
public class RegistrationService {

    private final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    private final RegistrationRepository registrationRepository;

    private final AccountingService accountingService;

    @PersistenceContext
    private EntityManager entityManager;

    public RegistrationService(RegistrationRepository registrationRepository, AccountingService accountingService) {
        this.registrationRepository = registrationRepository;
        this.accountingService = accountingService;
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
                    registration.setStatus((updateRegistration.getStatus()));
                }

                if (updateRegistration.getArrivalTime() != null) {
                    registration.setArrivalTime((updateRegistration.getArrivalTime()));
                }

                if (updateRegistration.getType() != null) {
                    registration.setType((updateRegistration.getType()));
                }

                if (updateRegistration.isOnSite() != null) {
                    registration.setOnSite((updateRegistration.isOnSite()));
                }

                if (updateRegistration.getAccounting() != null) {
                    registration.setAccounting(getAccounting(updateRegistration));
                }

                return registration;
            })
            .get();
    }

    /**
     * Set the registration id with -1 * appointment id.
     *
     * @param registration the -id entity
     */
    public void setNegativeRegistrationId(Registration registration) {
        log.debug("Request to set -id Registration: {}", registration);

        Long deleteId = registration.getId();
        Accounting accounting = registration.getAccounting();

        registrationRepository.save(registration.accounting(null));
        entityManager.flush();

        entityManager.detach(registration);
        registration.setId(registration.getAppointment().getId() * -1L);
        registration.setAppointment(null);
        registration.setAccounting(accounting);
        entityManager.persist(registration);

        registrationRepository.findById(deleteId).ifPresent(registrationRepository::delete);
    }

    private Accounting getAccounting(Registration registration) {
        Accounting accounting = registration.getAccounting();
        if (accounting != null) {
            accounting = accounting.getId() == null ? accountingService.save(accounting) : accountingService.update(accounting);
        }

        return accounting;
    }
}
