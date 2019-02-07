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

import io.dentall.totoro.domain.ProcedureType;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.ProcedureTypeRepository;
import io.dentall.totoro.service.dto.ProcedureTypeCriteria;

/**
 * Service for executing complex queries for ProcedureType entities in the database.
 * The main input is a {@link ProcedureTypeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ProcedureType} or a {@link Page} of {@link ProcedureType} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProcedureTypeQueryService extends QueryService<ProcedureType> {

    private final Logger log = LoggerFactory.getLogger(ProcedureTypeQueryService.class);

    private final ProcedureTypeRepository procedureTypeRepository;

    public ProcedureTypeQueryService(ProcedureTypeRepository procedureTypeRepository) {
        this.procedureTypeRepository = procedureTypeRepository;
    }

    /**
     * Return a {@link List} of {@link ProcedureType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ProcedureType> findByCriteria(ProcedureTypeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ProcedureType> specification = createSpecification(criteria);
        return procedureTypeRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link ProcedureType} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ProcedureType> findByCriteria(ProcedureTypeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ProcedureType> specification = createSpecification(criteria);
        return procedureTypeRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProcedureTypeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ProcedureType> specification = createSpecification(criteria);
        return procedureTypeRepository.count(specification);
    }

    /**
     * Function to convert ProcedureTypeCriteria to a {@link Specification}
     */
    private Specification<ProcedureType> createSpecification(ProcedureTypeCriteria criteria) {
        Specification<ProcedureType> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ProcedureType_.id));
            }
            if (criteria.getMajor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMajor(), ProcedureType_.major));
            }
            if (criteria.getMinor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMinor(), ProcedureType_.minor));
            }
            if (criteria.getDisplay() != null) {
                specification = specification.and(buildSpecification(criteria.getDisplay(), ProcedureType_.display));
            }
        }
        return specification;
    }
}
