package io.dentall.totoro.service;

import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.repository.TreatmentProcedureRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing TreatmentProcedure.
 */
@Service
@Transactional
public class TreatmentProcedureService {

    private final Logger log = LoggerFactory.getLogger(TreatmentProcedureService.class);

    private final TreatmentProcedureRepository treatmentProcedureRepository;

    public TreatmentProcedureService(TreatmentProcedureRepository treatmentProcedureRepository) {
        this.treatmentProcedureRepository = treatmentProcedureRepository;
    }

    /**
     * Save a treatmentProcedure.
     *
     * @param treatmentProcedure the entity to save
     * @return the persisted entity
     */
    public TreatmentProcedure save(TreatmentProcedure treatmentProcedure) {
        log.debug("Request to save TreatmentProcedure : {}", treatmentProcedure);
        return treatmentProcedureRepository.save(treatmentProcedure);
    }

    /**
     * Get all the treatmentProcedures.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<TreatmentProcedure> findAll(Pageable pageable) {
        log.debug("Request to get all TreatmentProcedures");
        return treatmentProcedureRepository.findAll(pageable);
    }


    /**
     * Get one treatmentProcedure by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<TreatmentProcedure> findOne(Long id) {
        log.debug("Request to get TreatmentProcedure : {}", id);
        return treatmentProcedureRepository.findById(id);
    }

    /**
     * Delete the treatmentProcedure by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete TreatmentProcedure : {}", id);
        treatmentProcedureRepository.deleteById(id);
    }
}
