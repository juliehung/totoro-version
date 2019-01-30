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

import io.dentall.totoro.domain.Procedure;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.ProcedureRepository;
import io.dentall.totoro.service.dto.ProcedureCriteria;

/**
 * Service for executing complex queries for Procedure entities in the database.
 * The main input is a {@link ProcedureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Procedure} or a {@link Page} of {@link Procedure} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ProcedureQueryService extends QueryService<Procedure> {

    private final Logger log = LoggerFactory.getLogger(ProcedureQueryService.class);

    private final ProcedureRepository procedureRepository;

    public ProcedureQueryService(ProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }

    /**
     * Return a {@link List} of {@link Procedure} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Procedure> findByCriteria(ProcedureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Procedure> specification = createSpecification(criteria);
        return procedureRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Procedure} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Procedure> findByCriteria(ProcedureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Procedure> specification = createSpecification(criteria);
        return procedureRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ProcedureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Procedure> specification = createSpecification(criteria);
        return procedureRepository.count(specification);
    }

    /**
     * Function to convert ProcedureCriteria to a {@link Specification}
     */
    private Specification<Procedure> createSpecification(ProcedureCriteria criteria) {
        Specification<Procedure> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Procedure_.id));
            }
            if (criteria.getContent() != null) {
                specification = specification.and(buildStringSpecification(criteria.getContent(), Procedure_.content));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Procedure_.price));
            }
            if (criteria.getProcedureTypeId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcedureTypeId(),
                    root -> root.join(Procedure_.procedureType, JoinType.LEFT).get(ProcedureType_.id)));
            }
        }
        return specification;
    }
}
