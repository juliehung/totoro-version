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
        return treatmentRepository.findAll(specification);
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
        return treatmentRepository.findAll(specification, page);
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
            if (criteria.getPatientId() != null) {
                specification = specification.and(buildSpecification(criteria.getPatientId(),
                    root -> root.join(Treatment_.patient, JoinType.LEFT).get(Patient_.id)));
            }
        }
        return specification;
    }
}
