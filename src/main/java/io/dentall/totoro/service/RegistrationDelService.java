package io.dentall.totoro.service;

import io.dentall.totoro.domain.RegistrationDel;
import io.dentall.totoro.repository.RegistrationDelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RegistrationDel.
 */
@Service
@Transactional
public class RegistrationDelService {

    private final Logger log = LoggerFactory.getLogger(RegistrationDelService.class);

    private final RegistrationDelRepository registrationDelRepository;

    public RegistrationDelService(RegistrationDelRepository registrationDelRepository) {
        this.registrationDelRepository = registrationDelRepository;
    }

    /**
     * Save a registrationDel.
     *
     * @param registrationDel the entity to save
     * @return the persisted entity
     */
    public RegistrationDel save(RegistrationDel registrationDel) {
        log.debug("Request to save RegistrationDel : {}", registrationDel);
        return registrationDelRepository.save(registrationDel);
    }

    /**
     * Get all the registrationDels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<RegistrationDel> findAll(Pageable pageable) {
        log.debug("Request to get all RegistrationDels");
        return registrationDelRepository.findAll(pageable);
    }


    /**
     * Get one registrationDel by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<RegistrationDel> findOne(Long id) {
        log.debug("Request to get RegistrationDel : {}", id);
        return registrationDelRepository.findById(id);
    }

    /**
     * Delete the registrationDel by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete RegistrationDel : {}", id);
        registrationDelRepository.deleteById(id);
    }
}
