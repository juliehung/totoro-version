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

import io.dentall.totoro.domain.FindingType;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.FindingTypeRepository;
import io.dentall.totoro.service.dto.FindingTypeCriteria;

/**
 * Service for executing complex queries for FindingType entities in the database.
 * The main input is a {@link FindingTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FindingType} or a {@link Page} of {@link FindingType} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FindingTypeQueryService extends QueryService<FindingType> {

    private final Logger log = LoggerFactory.getLogger(FindingTypeQueryService.class);

    private final FindingTypeRepository findingTypeRepository;

    public FindingTypeQueryService(FindingTypeRepository findingTypeRepository) {
        this.findingTypeRepository = findingTypeRepository;
    }

    /**
     * Return a {@link List} of {@link FindingType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FindingType> findByCriteria(FindingTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FindingType> specification = createSpecification(criteria);
        return findingTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link FindingType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FindingType> findByCriteria(FindingTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FindingType> specification = createSpecification(criteria);
        return findingTypeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FindingTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FindingType> specification = createSpecification(criteria);
        return findingTypeRepository.count(specification);
    }

    /**
     * Function to convert FindingTypeCriteria to a {@link Specification}
     */
    private Specification<FindingType> createSpecification(FindingTypeCriteria criteria) {
        Specification<FindingType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FindingType_.id));
            }
            if (criteria.getMajor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMajor(), FindingType_.major));
            }
            if (criteria.getMinor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMinor(), FindingType_.minor));
            }
            if (criteria.getDisplay() != null) {
                specification = specification.and(buildSpecification(criteria.getDisplay(), FindingType_.display));
            }
        }
        return specification;
    }
}
