package io.dentall.totoro.service;

import io.dentall.totoro.domain.ProcedureType;
import io.dentall.totoro.repository.ProcedureTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ProcedureType.
 */
@Service
@Transactional
public class ProcedureTypeService {

    private final Logger log = LoggerFactory.getLogger(ProcedureTypeService.class);

    private final ProcedureTypeRepository procedureTypeRepository;

    public ProcedureTypeService(ProcedureTypeRepository procedureTypeRepository) {
        this.procedureTypeRepository = procedureTypeRepository;
    }

    /**
     * Save a procedureType.
     *
     * @param procedureType the entity to save
     * @return the persisted entity
     */
    public ProcedureType save(ProcedureType procedureType) {
        log.debug("Request to save ProcedureType : {}", procedureType);
        return procedureTypeRepository.save(procedureType);
    }

    /**
     * Get all the procedureTypes.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ProcedureType> findAll() {
        log.debug("Request to get all ProcedureTypes");
        return procedureTypeRepository.findAll();
    }


    /**
     * Get one procedureType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ProcedureType> findOne(Long id) {
        log.debug("Request to get ProcedureType : {}", id);
        return procedureTypeRepository.findById(id);
    }

    /**
     * Delete the procedureType by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ProcedureType : {}", id);
        procedureTypeRepository.deleteById(id);
    }
}
