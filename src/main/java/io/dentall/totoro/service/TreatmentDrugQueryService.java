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

import io.dentall.totoro.domain.TreatmentDrug;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.TreatmentDrugRepository;
import io.dentall.totoro.service.dto.TreatmentDrugCriteria;

/**
 * Service for executing complex queries for TreatmentDrug entities in the database.
 * The main input is a {@link TreatmentDrugCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TreatmentDrug} or a {@link Page} of {@link TreatmentDrug} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TreatmentDrugQueryService extends QueryService<TreatmentDrug> {

    private final Logger log = LoggerFactory.getLogger(TreatmentDrugQueryService.class);

    private final TreatmentDrugRepository treatmentDrugRepository;

    public TreatmentDrugQueryService(TreatmentDrugRepository treatmentDrugRepository) {
        this.treatmentDrugRepository = treatmentDrugRepository;
    }

    /**
     * Return a {@link List} of {@link TreatmentDrug} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TreatmentDrug> findByCriteria(TreatmentDrugCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TreatmentDrug> specification = createSpecification(criteria);
        return treatmentDrugRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TreatmentDrug} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TreatmentDrug> findByCriteria(TreatmentDrugCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TreatmentDrug> specification = createSpecification(criteria);
        return treatmentDrugRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TreatmentDrugCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TreatmentDrug> specification = createSpecification(criteria);
        return treatmentDrugRepository.count(specification);
    }

    /**
     * Function to convert TreatmentDrugCriteria to a {@link Specification}
     */
    private Specification<TreatmentDrug> createSpecification(TreatmentDrugCriteria criteria) {
        Specification<TreatmentDrug> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TreatmentDrug_.id));
            }
            if (criteria.getDay() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDay(), TreatmentDrug_.day));
            }
            if (criteria.getPrescriptionId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrescriptionId(),
                    root -> root.join(TreatmentDrug_.prescription, JoinType.LEFT).get(Prescription_.id)));
            }
            if (criteria.getDrugId() != null) {
                specification = specification.and(buildSpecification(criteria.getDrugId(),
                    root -> root.join(TreatmentDrug_.drug, JoinType.LEFT).get(Drug_.id)));
            }
        }
        return specification;
    }
}
