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

import io.dentall.totoro.domain.TreatmentTask;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.TreatmentTaskRepository;
import io.dentall.totoro.service.dto.TreatmentTaskCriteria;

/**
 * Service for executing complex queries for TreatmentTask entities in the database.
 * The main input is a {@link TreatmentTaskCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TreatmentTask} or a {@link Page} of {@link TreatmentTask} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TreatmentTaskQueryService extends QueryService<TreatmentTask> {

    private final Logger log = LoggerFactory.getLogger(TreatmentTaskQueryService.class);

    private final TreatmentTaskRepository treatmentTaskRepository;

    public TreatmentTaskQueryService(TreatmentTaskRepository treatmentTaskRepository) {
        this.treatmentTaskRepository = treatmentTaskRepository;
    }

    /**
     * Return a {@link List} of {@link TreatmentTask} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TreatmentTask> findByCriteria(TreatmentTaskCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TreatmentTask> specification = createSpecification(criteria);
        return treatmentTaskRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TreatmentTask} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TreatmentTask> findByCriteria(TreatmentTaskCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TreatmentTask> specification = createSpecification(criteria);
        return treatmentTaskRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TreatmentTaskCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TreatmentTask> specification = createSpecification(criteria);
        return treatmentTaskRepository.count(specification);
    }

    /**
     * Function to convert TreatmentTaskCriteria to a {@link Specification}
     */
    private Specification<TreatmentTask> createSpecification(TreatmentTaskCriteria criteria) {
        Specification<TreatmentTask> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TreatmentTask_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), TreatmentTask_.name));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), TreatmentTask_.note));
            }
            if (criteria.getTreatmentProcedureId() != null) {
                specification = specification.and(buildSpecification(criteria.getTreatmentProcedureId(),
                    root -> root.join(TreatmentTask_.treatmentProcedures, JoinType.LEFT).get(TreatmentProcedure_.id)));
            }
            if (criteria.getTreatmentPlanId() != null) {
                specification = specification.and(buildSpecification(criteria.getTreatmentPlanId(),
                    root -> root.join(TreatmentTask_.treatmentPlan, JoinType.LEFT).get(TreatmentPlan_.id)));
            }
        }
        return specification;
    }
}
