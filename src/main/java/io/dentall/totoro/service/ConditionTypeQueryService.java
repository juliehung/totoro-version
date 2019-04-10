package io.dentall.totoro.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.dentall.totoro.domain.ConditionType;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.ConditionTypeRepository;
import io.dentall.totoro.service.dto.ConditionTypeCriteria;

/**
 * Service for executing complex queries for ConditionType entities in the database.
 * The main input is a {@link ConditionTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ConditionType} or a {@link Page} of {@link ConditionType} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ConditionTypeQueryService extends QueryService<ConditionType> {

    private final Logger log = LoggerFactory.getLogger(ConditionTypeQueryService.class);

    private final ConditionTypeRepository conditionTypeRepository;

    public ConditionTypeQueryService(ConditionTypeRepository conditionTypeRepository) {
        this.conditionTypeRepository = conditionTypeRepository;
    }

    /**
     * Return a {@link List} of {@link ConditionType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ConditionType> findByCriteria(ConditionTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ConditionType> specification = createSpecification(criteria);
        return conditionTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ConditionType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ConditionType> findByCriteria(ConditionTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ConditionType> specification = createSpecification(criteria);
        return conditionTypeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ConditionTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ConditionType> specification = createSpecification(criteria);
        return conditionTypeRepository.count(specification);
    }

    /**
     * Function to convert ConditionTypeCriteria to a {@link Specification}
     */
    private Specification<ConditionType> createSpecification(ConditionTypeCriteria criteria) {
        Specification<ConditionType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ConditionType_.id));
            }
            if (criteria.getMajor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMajor(), ConditionType_.major));
            }
            if (criteria.getMinor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMinor(), ConditionType_.minor));
            }
            if (criteria.getDisplay() != null) {
                specification = specification.and(buildSpecification(criteria.getDisplay(), ConditionType_.display));
            }
        }
        return specification;
    }
}
