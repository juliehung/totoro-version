package io.dentall.totoro.service;

import io.dentall.totoro.domain.ConfigurationMap;
import io.dentall.totoro.repository.ConfigurationMapRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link ConfigurationMap}.
 */
@Service
@Transactional
public class ConfigurationMapService {

    private final Logger log = LoggerFactory.getLogger(ConfigurationMapService.class);

    private final ConfigurationMapRepository configurationMapRepository;

    public ConfigurationMapService(ConfigurationMapRepository configurationMapRepository) {
        this.configurationMapRepository = configurationMapRepository;
    }

    /**
     * Save a configurationMap.
     *
     * @param configurationMap the entity to save.
     * @return the persisted entity.
     */
    public ConfigurationMap save(ConfigurationMap configurationMap) {
        log.debug("Request to save ConfigurationMap : {}", configurationMap);
        return configurationMapRepository.save(configurationMap);
    }

    /**
     * Get all the configurationMaps.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<ConfigurationMap> findAll() {
        log.debug("Request to get all ConfigurationMaps");
        return configurationMapRepository.findAll();
    }

    /**
     * Get one configurationMap by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ConfigurationMap> findOne(Long id) {
        log.debug("Request to get ConfigurationMap : {}", id);
        return configurationMapRepository.findById(id);
    }

    /**
     * Delete the configurationMap by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ConfigurationMap : {}", id);
        configurationMapRepository.deleteById(id);
    }
}
