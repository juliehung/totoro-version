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

import io.dentall.totoro.domain.NhiMedicalRecord;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.NhiMedicalRecordRepository;
import io.dentall.totoro.service.dto.NhiMedicalRecordCriteria;

/**
 * Service for executing complex queries for NhiMedicalRecord entities in the database.
 * The main input is a {@link NhiMedicalRecordCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NhiMedicalRecord} or a {@link Page} of {@link NhiMedicalRecord} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NhiMedicalRecordQueryService extends QueryService<NhiMedicalRecord> {

    private final Logger log = LoggerFactory.getLogger(NhiMedicalRecordQueryService.class);

    private final NhiMedicalRecordRepository nhiMedicalRecordRepository;

    public NhiMedicalRecordQueryService(NhiMedicalRecordRepository nhiMedicalRecordRepository) {
        this.nhiMedicalRecordRepository = nhiMedicalRecordRepository;
    }

    /**
     * Return a {@link List} of {@link NhiMedicalRecord} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NhiMedicalRecord> findByCriteria(NhiMedicalRecordCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NhiMedicalRecord> specification = createSpecification(criteria);
        return nhiMedicalRecordRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NhiMedicalRecord} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NhiMedicalRecord> findByCriteria(NhiMedicalRecordCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NhiMedicalRecord> specification = createSpecification(criteria);
        return nhiMedicalRecordRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NhiMedicalRecordCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NhiMedicalRecord> specification = createSpecification(criteria);
        return nhiMedicalRecordRepository.count(specification);
    }

    /**
     * Function to convert NhiMedicalRecordCriteria to a {@link Specification}
     */
    private Specification<NhiMedicalRecord> createSpecification(NhiMedicalRecordCriteria criteria) {
        Specification<NhiMedicalRecord> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NhiMedicalRecord_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDate(), NhiMedicalRecord_.date));
            }
            if (criteria.getNhiCategory() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNhiCategory(), NhiMedicalRecord_.nhiCategory));
            }
            if (criteria.getNhiCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNhiCode(), NhiMedicalRecord_.nhiCode));
            }
            if (criteria.getPart() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPart(), NhiMedicalRecord_.part));
            }
            if (criteria.getUsage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsage(), NhiMedicalRecord_.usage));
            }
            if (criteria.getTotal() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTotal(), NhiMedicalRecord_.total));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), NhiMedicalRecord_.note));
            }
            if (criteria.getDays() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDays(), NhiMedicalRecord_.days));
            }
            if (criteria.getNhiExtendPatientId() != null) {
                specification = specification.and(buildSpecification(criteria.getNhiExtendPatientId(),
                    root -> root.join(NhiMedicalRecord_.nhiExtendPatient, JoinType.LEFT).get(NhiExtendPatient_.id)));
            }
        }
        return specification;
    }
}
