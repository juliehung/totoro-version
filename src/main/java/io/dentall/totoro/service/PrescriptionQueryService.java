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

import io.dentall.totoro.domain.Prescription;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.PrescriptionRepository;
import io.dentall.totoro.service.dto.PrescriptionCriteria;

/**
 * Service for executing complex queries for Prescription entities in the database.
 * The main input is a {@link PrescriptionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Prescription} or a {@link Page} of {@link Prescription} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PrescriptionQueryService extends QueryService<Prescription> {

    private final Logger log = LoggerFactory.getLogger(PrescriptionQueryService.class);

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionQueryService(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    /**
     * Return a {@link List} of {@link Prescription} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Prescription> findByCriteria(PrescriptionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Prescription> specification = createSpecification(criteria);
        return prescriptionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Prescription} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Prescription> findByCriteria(PrescriptionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Prescription> specification = createSpecification(criteria);
        return prescriptionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PrescriptionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Prescription> specification = createSpecification(criteria);
        return prescriptionRepository.count(specification);
    }

    /**
     * Function to convert PrescriptionCriteria to a {@link Specification}
     */
    private Specification<Prescription> createSpecification(PrescriptionCriteria criteria) {
        Specification<Prescription> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Prescription_.id));
            }
            if (criteria.getClinicAdministration() != null) {
                specification = specification.and(buildSpecification(criteria.getClinicAdministration(), Prescription_.clinicAdministration));
            }
            if (criteria.getAntiInflammatoryDrug() != null) {
                specification = specification.and(buildSpecification(criteria.getAntiInflammatoryDrug(), Prescription_.antiInflammatoryDrug));
            }
            if (criteria.getPain() != null) {
                specification = specification.and(buildSpecification(criteria.getPain(), Prescription_.pain));
            }
            if (criteria.getTakenAll() != null) {
                specification = specification.and(buildSpecification(criteria.getTakenAll(), Prescription_.takenAll));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), Prescription_.status));
            }
            if (criteria.getMode() != null) {
                specification = specification.and(buildSpecification(criteria.getMode(), Prescription_.mode));
            }
            if (criteria.getTreatmentDrugId() != null) {
                specification = specification.and(buildSpecification(criteria.getTreatmentDrugId(),
                    root -> root.join(Prescription_.treatmentDrugs, JoinType.LEFT).get(TreatmentDrug_.id)));
            }
            if (criteria.getDisposalId() != null) {
                specification = specification.and(buildSpecification(criteria.getDisposalId(),
                    root -> root.join(Prescription_.disposal, JoinType.LEFT).get(Disposal_.id)));
            }
        }
        return specification;
    }
}
