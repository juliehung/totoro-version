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

import io.dentall.totoro.domain.TreatmentPlan;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.TreatmentPlanRepository;
import io.dentall.totoro.service.dto.TreatmentPlanCriteria;

/**
 * Service for executing complex queries for TreatmentPlan entities in the database.
 * The main input is a {@link TreatmentPlanCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TreatmentPlan} or a {@link Page} of {@link TreatmentPlan} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TreatmentPlanQueryService extends QueryService<TreatmentPlan> {

    private final Logger log = LoggerFactory.getLogger(TreatmentPlanQueryService.class);

    private final TreatmentPlanRepository treatmentPlanRepository;

    public TreatmentPlanQueryService(TreatmentPlanRepository treatmentPlanRepository) {
        this.treatmentPlanRepository = treatmentPlanRepository;
    }

    /**
     * Return a {@link List} of {@link TreatmentPlan} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TreatmentPlan> findByCriteria(TreatmentPlanCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TreatmentPlan> specification = createSpecification(criteria);
        return treatmentPlanRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TreatmentPlan} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TreatmentPlan> findByCriteria(TreatmentPlanCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TreatmentPlan> specification = createSpecification(criteria);
        return treatmentPlanRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TreatmentPlanCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TreatmentPlan> specification = createSpecification(criteria);
        return treatmentPlanRepository.count(specification);
    }

    /**
     * Function to convert TreatmentPlanCriteria to a {@link Specification}
     */
    private Specification<TreatmentPlan> createSpecification(TreatmentPlanCriteria criteria) {
        Specification<TreatmentPlan> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TreatmentPlan_.id));
            }
            if (criteria.getActivated() != null) {
                specification = specification.and(buildSpecification(criteria.getActivated(), TreatmentPlan_.activated));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TreatmentPlan_.name));
            }
            if (criteria.getTreatmentTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getTreatmentTaskId(),
                    root -> root.join(TreatmentPlan_.treatmentTasks, JoinType.LEFT).get(TreatmentTask_.id)));
            }
            if (criteria.getTreatmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getTreatmentId(),
                    root -> root.join(TreatmentPlan_.treatment, JoinType.LEFT).get(Treatment_.id)));
            }
        }
        return specification;
    }
}
