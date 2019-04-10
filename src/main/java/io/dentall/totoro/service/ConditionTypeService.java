package io.dentall.totoro.service;

import io.dentall.totoro.domain.ConditionType;
import io.dentall.totoro.repository.ConditionTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing ConditionType.
 */
@Service
@Transactional
public class ConditionTypeService {

    private final Logger log = LoggerFactory.getLogger(ConditionTypeService.class);

    private final ConditionTypeRepository conditionTypeRepository;

    public ConditionTypeService(ConditionTypeRepository conditionTypeRepository) {
        this.conditionTypeRepository = conditionTypeRepository;
    }

    /**
     * Save a conditionType.
     *
     * @param conditionType the entity to save
     * @return the persisted entity
     */
    public ConditionType save(ConditionType conditionType) {
        log.debug("Request to save ConditionType : {}", conditionType);
        return conditionTypeRepository.save(conditionType);
    }

    /**
     * Get all the conditionTypes.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ConditionType> findAll() {
        log.debug("Request to get all ConditionTypes");
        return conditionTypeRepository.findAll();
    }


    /**
     * Get one conditionType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ConditionType> findOne(Long id) {
        log.debug("Request to get ConditionType : {}", id);
        return conditionTypeRepository.findById(id);
    }

    /**
     * Delete the conditionType by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete ConditionType : {}", id);
        conditionTypeRepository.deleteById(id);
    }
}
