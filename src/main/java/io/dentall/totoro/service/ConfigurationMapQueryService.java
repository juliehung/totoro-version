package io.dentall.totoro.service;

import io.dentall.totoro.domain.ConfigurationMap;
import io.dentall.totoro.domain.ConfigurationMap_;
import io.dentall.totoro.repository.ConfigurationMapRepository;
import io.dentall.totoro.service.dto.ConfigurationMapCriteria;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for {@link ConfigurationMap} entities in the database.
 * The main input is a {@link ConfigurationMapCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConfigurationMap} or a {@link Page} of {@link ConfigurationMap} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConfigurationMapQueryService extends QueryService<ConfigurationMap> {

    private final Logger log = LoggerFactory.getLogger(ConfigurationMapQueryService.class);

    private final ConfigurationMapRepository configurationMapRepository;

    public ConfigurationMapQueryService(ConfigurationMapRepository configurationMapRepository) {
        this.configurationMapRepository = configurationMapRepository;
    }

    /**
     * Return a {@link List} of {@link ConfigurationMap} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConfigurationMap> findByCriteria(ConfigurationMapCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ConfigurationMap> specification = createSpecification(criteria);
        return configurationMapRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ConfigurationMap} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConfigurationMap> findByCriteria(ConfigurationMapCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ConfigurationMap> specification = createSpecification(criteria);
        return configurationMapRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConfigurationMapCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ConfigurationMap> specification = createSpecification(criteria);
        return configurationMapRepository.count(specification);
    }

    /**
     * Function to convert {@link ConfigurationMapCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<ConfigurationMap> createSpecification(ConfigurationMapCriteria criteria) {
        Specification<ConfigurationMap> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), ConfigurationMap_.id));
            }
            if (criteria.getConfigKey() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConfigKey(), ConfigurationMap_.configKey));
            }
            if (criteria.getConfigValue() != null) {
                specification = specification.and(buildStringSpecification(criteria.getConfigValue(), ConfigurationMap_.configValue));
            }
        }
        return specification;
    }
}
