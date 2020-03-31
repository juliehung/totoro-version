package io.dentall.totoro.service;

import io.dentall.totoro.domain.ConfigurationMap;
import io.dentall.totoro.repository.ConfigurationMapRepository;
import io.dentall.totoro.service.dto.ConfigurationMapCriteria;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.vm.ConfigurationVM;
import io.github.jhipster.service.filter.StringFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Service Implementation for managing {@link ConfigurationMap}.
 */
@Service
@Transactional
public class ConfigurationMapService {
    private static final String ENTITY_NAME = "CONFIGURATION_MAP";

    private final Logger log = LoggerFactory.getLogger(ConfigurationMapService.class);

    private final ConfigurationMapRepository configurationMapRepository;

    private final ConfigurationMapQueryService configurationMapQueryService;

    public ConfigurationMapService(ConfigurationMapRepository configurationMapRepository, ConfigurationMapQueryService configurationMapQueryService) {
        this.configurationMapRepository = configurationMapRepository;
        this.configurationMapQueryService = configurationMapQueryService;
    }

    // Factory
    private ConfigurationMapCriteria createCriteriaByConfigKeyEqualsTo(String value) {
        ConfigurationMapCriteria cc = new ConfigurationMapCriteria();
        StringFilter sf = new StringFilter();
        sf.setEquals(value);
        cc.setConfigKey(sf);

        return cc;
    }

    // Validate ConfigurationVM
    private Consumer<ConfigurationVM> validateConfigurationsSize = (ConfigurationVM configurationVM) -> {
        if (configurationVM == null ||
            configurationVM.getConfigurations() == null ||
            configurationVM.getConfigurations().size() < 1
        ) {
            throw new BadRequestAlertException("Must put available value here. e.g. {configurations: [configKey: string, configValue: string]}", ENTITY_NAME, "no.params");
        }
    };

    private Consumer<ConfigurationVM> validateConfigurationKey = (ConfigurationVM configurationVM) -> {
        if (configurationVM.getConfigurations().stream()
            .anyMatch(config -> StringUtils.isBlank(config.getConfigKey()))
        ) {
            throw new BadRequestAlertException("The configKey must not be empty/blank", ENTITY_NAME, "bad.params");
        }
    };

    // Validate ConfigurationMap
    private Consumer<ConfigurationMap> validateConfigKeyNotBlank = (ConfigurationMap configurationMap) -> {
        if (StringUtils.isBlank(configurationMap.getConfigKey())) {
            throw new BadRequestAlertException("The configKey must not be blank", ENTITY_NAME, "bad.params");
        }
    };

    private ConfigurationVM createReturnVM() {
        ConfigurationVM returnVm = new ConfigurationVM();
        List<ConfigurationMap> cmList = new ArrayList<>();
        returnVm.setConfigurations(cmList);

        return returnVm;
    }

    public ConfigurationVM saveAll(ConfigurationVM configurationVM) {
        ConfigurationVM returnVm = this.createReturnVM();

        validateConfigurationsSize
            .andThen(validateConfigurationKey)
            .accept(configurationVM);

        configurationVM.getConfigurations().forEach(configuration -> {
            ConfigurationMap o = this.save(configuration);
            returnVm.getConfigurations().add(o);
        });

        return returnVm;
    }

    public ConfigurationVM updateAll(ConfigurationVM configurationVM) {
        List<ConfigurationMap> validatedList = new ArrayList<>();
        ConfigurationVM returnVm = this.createReturnVM();
        validateConfigurationsSize.accept(configurationVM);

        for (ConfigurationMap updateObject : configurationVM.getConfigurations()) {
            validateConfigKeyNotBlank.accept(updateObject);
            ConfigurationMapCriteria cc = createCriteriaByConfigKeyEqualsTo(updateObject.getConfigKey());
            Optional<ConfigurationMap> optionalConfig = configurationMapQueryService.findByCriteria(cc).stream().findFirst();

            if (!optionalConfig.isPresent()) {
                throw new BadRequestAlertException("Can not found any by configKey: " + updateObject.getConfigKey(), ENTITY_NAME, "not.found");
            }

            updateObject.setId(optionalConfig.get().getId());
            validatedList.add(updateObject);
        }

        validatedList.forEach(updateValue -> {
            this.save(updateValue);
            returnVm.getConfigurations().add(updateValue);
        });

        return returnVm;
    }

    public void deleteAll(ConfigurationVM configurationVM) {
        List<Long> validatedList = new ArrayList<>();
        validateConfigurationsSize.accept(configurationVM);

        for (ConfigurationMap o : configurationVM.getConfigurations()) {
            validateConfigKeyNotBlank.accept(o);
            ConfigurationMapCriteria cc = createCriteriaByConfigKeyEqualsTo(o.getConfigKey());
            Optional<ConfigurationMap> optionalConfig = configurationMapQueryService.findByCriteria(cc).stream().findFirst();

            if (!optionalConfig.isPresent()) {
                throw new BadRequestAlertException("Can not found any by configKey: " + o.getConfigKey(), ENTITY_NAME, "not.found");
            }

            validatedList.add(optionalConfig.get().getId());
        }

        validatedList.forEach(this::delete);
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
