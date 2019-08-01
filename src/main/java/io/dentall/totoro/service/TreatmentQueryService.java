package io.dentall.totoro.service;

import java.time.Instant;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.criteria.*;

import io.dentall.totoro.domain.enumeration.TreatmentProcedureStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.dentall.totoro.domain.Treatment;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.TreatmentRepository;
import io.dentall.totoro.service.dto.TreatmentCriteria;

/**
 * Service for executing complex queries for Treatment entities in the database.
 * The main input is a {@link TreatmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Treatment} or a {@link Page} of {@link Treatment} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TreatmentQueryService extends QueryService<Treatment> {

    private final Logger log = LoggerFactory.getLogger(TreatmentQueryService.class);

    private final TreatmentRepository treatmentRepository;

    public TreatmentQueryService(TreatmentRepository treatmentRepository) {
        this.treatmentRepository = treatmentRepository;
    }

    /**
     * Return a {@link List} of {@link Treatment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Treatment> findByCriteria(TreatmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Treatment> specification = createSpecification(criteria);
        return treatmentRepository.findAll(specification)
            .stream()
            .map(treatment -> mapper.apply(treatment, criteria))
            .collect(Collectors.toList());
    }

    /**
     * Return a {@link Page} of {@link Treatment} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Treatment> findByCriteria(TreatmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Treatment> specification = createSpecification(criteria);
        return treatmentRepository.findAll(specification, page)
            .map(treatment -> mapper.apply(treatment, criteria));
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TreatmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Treatment> specification = createSpecification(criteria);
        return treatmentRepository.count(specification);
    }

    /**
     * Function to convert TreatmentCriteria to a {@link Specification}
     */
    private Specification<Treatment> createSpecification(TreatmentCriteria criteria) {
        Specification<Treatment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Treatment_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Treatment_.name));
            }
            if (criteria.getChiefComplaint() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChiefComplaint(), Treatment_.chiefComplaint));
            }
            if (criteria.getGoal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getGoal(), Treatment_.goal));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), Treatment_.note));
            }
            if (criteria.getFinding() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFinding(), Treatment_.finding));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), Treatment_.type));
            }
            if (criteria.getPatientId() != null) {
                specification = specification.and(buildSpecification(criteria.getPatientId(),
                    root -> root.join(Treatment_.patient, JoinType.LEFT).get(Patient_.id)));
            }
            if (criteria.getTreatmentPlanId() != null) {
                specification = specification.and(buildSpecification(criteria.getTreatmentPlanId(),
                    root -> root.join(Treatment_.treatmentPlans, JoinType.LEFT).get(TreatmentPlan_.id)));
            }
        }
        return specification;
    }

    private BiFunction<Treatment, TreatmentCriteria, Treatment> mapper = (treatment, criteria) -> {
        if (criteria.getEagerload() || criteria.getPatientId() != null || criteria.getIgnoreTodo() || criteria.getTpCompleted()) {
            return fetchEagerRelationships(treatment, criteria);
        }

        return treatment;
    };

    private Treatment fetchEagerRelationships(Treatment treatment, TreatmentCriteria criteria) {
        Predicate<TreatmentProcedure> ignoreTodo = criteria.getIgnoreTodo() ? treatmentProcedure -> (treatmentProcedure.getTodos() == null || treatmentProcedure.getTodos().size() == 0) : treatmentProcedure -> true;
        Predicate<TreatmentProcedure> tpCompleted = criteria.getTpCompleted() ? treatmentProcedure -> treatmentProcedure.getStatus() == TreatmentProcedureStatus.COMPLETED : treatmentProcedure -> true;

        return treatment.treatmentPlans(treatment.getTreatmentPlans()
            .stream()
            .map(treatmentPlan -> treatmentPlan.treatmentTasks(
                treatmentPlan.getTreatmentTasks()
                    .stream()
                    .map(treatmentTask ->
                        treatmentTask.treatmentProcedures(
                            getTreatmentProcedures(
                                treatmentTask.getTreatmentProcedures()
                                    .stream()
                                    .filter(ignoreTodo.and(tpCompleted))
                                    .collect(Collectors.toSet())
                            )
                        )
                    )
                    .collect(Collectors.toSet())
                )
            )
            .collect(Collectors.toSet())
        );
    }

    private Set<TreatmentProcedure> getTreatmentProcedures(Set<TreatmentProcedure> treatmentProcedures) {
        Set<Long> ids = getTreatmentProcedureIdsByCompletedDate(treatmentProcedures);

        return treatmentProcedures
            .stream()
            .filter(treatmentProcedure -> {
                if (treatmentProcedure.getCompletedDate() == null) {
                    return true;
                } else {
                    return ids.contains(treatmentProcedure.getId());
                }
            })
            .collect(Collectors.toSet());
    }

    private Set<Long> getTreatmentProcedureIdsByCompletedDate(Set<TreatmentProcedure> treatmentProcedures) {
        Map<Instant, TreatmentProcedure> map = treatmentProcedures
            .stream()
            .filter(treatmentProcedure -> treatmentProcedure.getCompletedDate() != null)
            .collect(
                Collectors.toMap(
                    TreatmentProcedure::getCompletedDate,
                    Function.identity(),
                    (txP1, txP2) -> {
                        if (txP2.getTodos() == null || txP2.getTodos().size() == 0) {
                            return txP2;
                        }

                        return txP1;
                    },
                    HashMap::new
                )
            );

        return map.values().stream().map(TreatmentProcedure::getId).collect(Collectors.toSet());
    }
}
