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

import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.DisposalRepository;
import io.dentall.totoro.service.dto.DisposalCriteria;

/**
 * Service for executing complex queries for Disposal entities in the database.
 * The main input is a {@link DisposalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Disposal} or a {@link Page} of {@link Disposal} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DisposalQueryService extends QueryService<Disposal> {

    private final Logger log = LoggerFactory.getLogger(DisposalQueryService.class);

    private final DisposalRepository disposalRepository;

    public DisposalQueryService(DisposalRepository disposalRepository) {
        this.disposalRepository = disposalRepository;
    }

    /**
     * Return a {@link List} of {@link Disposal} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Disposal> findByCriteria(DisposalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Disposal> specification = createSpecification(criteria);
        return disposalRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Disposal} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Disposal> findByCriteria(DisposalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Disposal> specification = createSpecification(criteria);
        return disposalRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DisposalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Disposal> specification = createSpecification(criteria);
        return disposalRepository.count(specification);
    }

    /**
     * Function to convert DisposalCriteria to a {@link Specification}
     */
    private Specification<Disposal> createSpecification(DisposalCriteria criteria) {
        Specification<Disposal> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Disposal_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Disposal_.status));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTotal(), Disposal_.total));
            }
            if (criteria.getDateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDateTime(), Disposal_.dateTime));
            }
            if (criteria.getTreatmentProcedureId() != null) {
                specification = specification.and(buildSpecification(criteria.getTreatmentProcedureId(),
                    root -> root.join(Disposal_.treatmentProcedures, JoinType.LEFT).get(TreatmentProcedure_.id)));
            }
            if (criteria.getPrescriptionId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrescriptionId(),
                    root -> root.join(Disposal_.prescription, JoinType.LEFT).get(Prescription_.id)));
            }
            if (criteria.getTodoId() != null) {
                specification = specification.and(buildSpecification(criteria.getTodoId(),
                    root -> root.join(Disposal_.todo, JoinType.LEFT).get(Todo_.id)));
            }
            if (criteria.getRegistrationId() != null) {
                specification = specification.and(buildSpecification(criteria.getRegistrationId(),
                    root -> root.join(Disposal_.registration, JoinType.LEFT).get(Registration_.id)));
            }

            if (criteria.getPatientId() != null) {
                specification = specification.and(buildSpecification(criteria.getPatientId(),
                    root -> root
                        .join(Disposal_.registration, JoinType.LEFT)
                        .join(Registration_.appointment, JoinType.LEFT)
                        .join(Appointment_.patient, JoinType.LEFT).get(Patient_.id)
                ));
            }

            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Disposal_.createdDate));
            }

            if (criteria.getToothId() != null) {
                specification = specification.and(buildSpecification(criteria.getToothId(),
                    root -> root.join(Disposal_.teeth, JoinType.LEFT).get(Tooth_.id)));
            }
        }
        return specification;
    }
}
