package io.dentall.totoro.service;

import io.dentall.totoro.domain.FindingType;
import io.dentall.totoro.repository.FindingTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing FindingType.
 */
@Service
@Transactional
public class FindingTypeService {

    private final Logger log = LoggerFactory.getLogger(FindingTypeService.class);

    private final FindingTypeRepository findingTypeRepository;

    public FindingTypeService(FindingTypeRepository findingTypeRepository) {
        this.findingTypeRepository = findingTypeRepository;
    }

    /**
     * Save a findingType.
     *
     * @param findingType the entity to save
     * @return the persisted entity
     */
    public FindingType save(FindingType findingType) {
        log.debug("Request to save FindingType : {}", findingType);
        return findingTypeRepository.save(findingType);
    }

    /**
     * Get all the findingTypes.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<FindingType> findAll() {
        log.debug("Request to get all FindingTypes");
        return findingTypeRepository.findAll();
    }


    /**
     * Get one findingType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<FindingType> findOne(Long id) {
        log.debug("Request to get FindingType : {}", id);
        return findingTypeRepository.findById(id);
    }

    /**
     * Delete the findingType by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete FindingType : {}", id);
        findingTypeRepository.deleteById(id);
    }
}
