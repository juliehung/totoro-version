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

import io.dentall.totoro.domain.Tooth;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.ToothRepository;
import io.dentall.totoro.service.dto.ToothCriteria;

/**
 * Service for executing complex queries for Tooth entities in the database.
 * The main input is a {@link ToothCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Tooth} or a {@link Page} of {@link Tooth} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ToothQueryService extends QueryService<Tooth> {

    private final Logger log = LoggerFactory.getLogger(ToothQueryService.class);

    private final ToothRepository toothRepository;

    public ToothQueryService(ToothRepository toothRepository) {
        this.toothRepository = toothRepository;
    }

    /**
     * Return a {@link List} of {@link Tooth} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Tooth> findByCriteria(ToothCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tooth> specification = createSpecification(criteria);
        return toothRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Tooth} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Tooth> findByCriteria(ToothCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tooth> specification = createSpecification(criteria);
        return toothRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ToothCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tooth> specification = createSpecification(criteria);
        return toothRepository.count(specification);
    }

    /**
     * Function to convert ToothCriteria to a {@link Specification}
     */
    private Specification<Tooth> createSpecification(ToothCriteria criteria) {
        Specification<Tooth> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Tooth_.id));
            }
            if (criteria.getPosition() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPosition(), Tooth_.position));
            }
            if (criteria.getBefore() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBefore(), Tooth_.before));
            }
            if (criteria.getPlanned() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPlanned(), Tooth_.planned));
            }
            if (criteria.getAfter() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAfter(), Tooth_.after));
            }
            if (criteria.getTreatmentTaskId() != null) {
                specification = specification.and(buildSpecification(criteria.getTreatmentTaskId(),
                    root -> root.join(Tooth_.treatmentTask, JoinType.LEFT).get(TreatmentTask_.id)));
            }
            if (criteria.getTreatmentProcedureId() != null) {
                specification = specification.and(buildSpecification(criteria.getTreatmentProcedureId(),
                    root -> root.join(Tooth_.treatmentProcedure, JoinType.LEFT).get(TreatmentProcedure_.id)));
            }
        }
        return specification;
    }
}
