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

import io.dentall.totoro.domain.NhiAccumulatedMedicalRecord;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.NhiAccumulatedMedicalRecordRepository;
import io.dentall.totoro.service.dto.NhiAccumulatedMedicalRecordCriteria;

/**
 * Service for executing complex queries for NhiAccumulatedMedicalRecord entities in the database.
 * The main input is a {@link NhiAccumulatedMedicalRecordCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NhiAccumulatedMedicalRecord} or a {@link Page} of {@link NhiAccumulatedMedicalRecord} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NhiAccumulatedMedicalRecordQueryService extends QueryService<NhiAccumulatedMedicalRecord> {

    private final Logger log = LoggerFactory.getLogger(NhiAccumulatedMedicalRecordQueryService.class);

    private final NhiAccumulatedMedicalRecordRepository nhiAccumulatedMedicalRecordRepository;

    public NhiAccumulatedMedicalRecordQueryService(NhiAccumulatedMedicalRecordRepository nhiAccumulatedMedicalRecordRepository) {
        this.nhiAccumulatedMedicalRecordRepository = nhiAccumulatedMedicalRecordRepository;
    }

    /**
     * Return a {@link List} of {@link NhiAccumulatedMedicalRecord} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NhiAccumulatedMedicalRecord> findByCriteria(NhiAccumulatedMedicalRecordCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NhiAccumulatedMedicalRecord> specification = createSpecification(criteria);
        return nhiAccumulatedMedicalRecordRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NhiAccumulatedMedicalRecord} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NhiAccumulatedMedicalRecord> findByCriteria(NhiAccumulatedMedicalRecordCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NhiAccumulatedMedicalRecord> specification = createSpecification(criteria);
        return nhiAccumulatedMedicalRecordRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NhiAccumulatedMedicalRecordCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NhiAccumulatedMedicalRecord> specification = createSpecification(criteria);
        return nhiAccumulatedMedicalRecordRepository.count(specification);
    }

    /**
     * Function to convert NhiAccumulatedMedicalRecordCriteria to a {@link Specification}
     */
    private Specification<NhiAccumulatedMedicalRecord> createSpecification(NhiAccumulatedMedicalRecordCriteria criteria) {
        Specification<NhiAccumulatedMedicalRecord> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NhiAccumulatedMedicalRecord_.id));
            }
            if (criteria.getMedicalCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMedicalCategory(), NhiAccumulatedMedicalRecord_.medicalCategory));
            }
            if (criteria.getNewbornMedicalTreatmentNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNewbornMedicalTreatmentNote(), NhiAccumulatedMedicalRecord_.newbornMedicalTreatmentNote));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), NhiAccumulatedMedicalRecord_.date));
            }
            if (criteria.getCardFillingNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCardFillingNote(), NhiAccumulatedMedicalRecord_.cardFillingNote));
            }
            if (criteria.getSeqNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSeqNumber(), NhiAccumulatedMedicalRecord_.seqNumber));
            }
            if (criteria.getMedicalInstitutionCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMedicalInstitutionCode(), NhiAccumulatedMedicalRecord_.medicalInstitutionCode));
            }
            if (criteria.getPatientId() != null) {
                specification = specification.and(buildSpecification(criteria.getPatientId(),
                    root -> root.join(NhiAccumulatedMedicalRecord_.patient, JoinType.LEFT).get(Patient_.id)));
            }
        }
        return specification;
    }
}
