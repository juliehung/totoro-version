package io.dentall.totoro.service;

import io.dentall.totoro.domain.Tooth;
import io.dentall.totoro.repository.ToothRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Tooth.
 */
@Service
@Transactional
public class ToothService {

    private final Logger log = LoggerFactory.getLogger(ToothService.class);

    private final ToothRepository toothRepository;

    public ToothService(ToothRepository toothRepository) {
        this.toothRepository = toothRepository;
    }

    /**
     * Save a tooth.
     *
     * @param tooth the entity to save
     * @return the persisted entity
     */
    public Tooth save(Tooth tooth) {
        log.debug("Request to save Tooth : {}", tooth);
        return toothRepository.save(tooth);
    }

    /**
     * Get all the teeth.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Tooth> findAll(Pageable pageable) {
        log.debug("Request to get all Teeth");
        return toothRepository.findAll(pageable);
    }


    /**
     * Get one tooth by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Tooth> findOne(Long id) {
        log.debug("Request to get Tooth : {}", id);
        return toothRepository.findById(id);
    }

    /**
     * Delete the tooth by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Tooth : {}", id);
        toothRepository.deleteById(id);
    }
}
