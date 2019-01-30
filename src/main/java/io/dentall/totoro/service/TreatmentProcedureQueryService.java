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

import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.TreatmentProcedureRepository;
import io.dentall.totoro.service.dto.TreatmentProcedureCriteria;

/**
 * Service for executing complex queries for TreatmentProcedure entities in the database.
 * The main input is a {@link TreatmentProcedureCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TreatmentProcedure} or a {@link Page} of {@link TreatmentProcedure} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TreatmentProcedureQueryService extends QueryService<TreatmentProcedure> {

    private final Logger log = LoggerFactory.getLogger(TreatmentProcedureQueryService.class);

    private final TreatmentProcedureRepository treatmentProcedureRepository;

    public TreatmentProcedureQueryService(TreatmentProcedureRepository treatmentProcedureRepository) {
        this.treatmentProcedureRepository = treatmentProcedureRepository;
    }

    /**
     * Return a {@link List} of {@link TreatmentProcedure} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TreatmentProcedure> findByCriteria(TreatmentProcedureCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TreatmentProcedure> specification = createSpecification(criteria);
        return treatmentProcedureRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TreatmentProcedure} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TreatmentProcedure> findByCriteria(TreatmentProcedureCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TreatmentProcedure> specification = createSpecification(criteria);
        return treatmentProcedureRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TreatmentProcedureCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TreatmentProcedure> specification = createSpecification(criteria);
        return treatmentProcedureRepository.count(specification);
    }

    /**
     * Function to convert TreatmentProcedureCriteria to a {@link Specification}
     */
    private Specification<TreatmentProcedure> createSpecification(TreatmentProcedureCriteria criteria) {
        Specification<TreatmentProcedure> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TreatmentProcedure_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), TreatmentProcedure_.status));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), TreatmentProcedure_.quantity));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), TreatmentProcedure_.total));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), TreatmentProcedure_.note));
            }
            if (criteria.getNhiProcedureId() != null) {
                specification = specification.and(buildSpecification(criteria.getNhiProcedureId(),
                    root -> root.join(TreatmentProcedure_.nhiProcedure, JoinType.LEFT).get(NHIProcedure_.id)));
            }
            if (criteria.getTreatmentTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getTreatmentTaskId(),
                    root -> root.join(TreatmentProcedure_.treatmentTask, JoinType.LEFT).get(TreatmentTask_.id)));
            }
            if (criteria.getProcedureId() != null) {
                specification = specification.and(buildSpecification(criteria.getProcedureId(),
                    root -> root.join(TreatmentProcedure_.procedure, JoinType.LEFT).get(Procedure_.id)));
            }
            if (criteria.getAppointmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getAppointmentId(),
                    root -> root.join(TreatmentProcedure_.appointment, JoinType.LEFT).get(Appointment_.id)));
            }
            if (criteria.getRegistrationId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegistrationId(),
                    root -> root.join(TreatmentProcedure_.registration, JoinType.LEFT).get(Registration_.id)));
            }
        }
        return specification;
    }
}
