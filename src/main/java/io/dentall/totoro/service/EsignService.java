package io.dentall.totoro.service;

import io.dentall.totoro.domain.Esign;
import io.dentall.totoro.repository.EsignRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Esign.
 */
@Service
@Transactional
public class EsignService {

    private final Logger log = LoggerFactory.getLogger(EsignService.class);

    private final EsignRepository esignRepository;

    public EsignService(EsignRepository esignRepository) {
        this.esignRepository = esignRepository;
    }

    /**
     * Save a esign.
     *
     * @param esign the entity to save
     * @return the persisted entity
     */
    public Esign save(Esign esign) {
        log.debug("Request to save Esign : {}", esign);
        return esignRepository.save(esign);
    }

    /**
     * Get all the esigns.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Esign> findAll() {
        log.debug("Request to get all Esigns");
        return esignRepository.findAll();
    }


    /**
     * Get one esign by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Esign> findOne(Long id) {
        log.debug("Request to get Esign : {}", id);
        return esignRepository.findById(id);
    }

    /**
     * Delete the esign by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Esign : {}", id);
        esignRepository.deleteById(id);
    }
}
