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

import io.dentall.totoro.domain.NhiMonthDeclarationDetails;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.NhiMonthDeclarationDetailsRepository;
import io.dentall.totoro.service.dto.NhiMonthDeclarationDetailsCriteria;

/**
 * Service for executing complex queries for NhiMonthDeclarationDetails entities in the database.
 * The main input is a {@link NhiMonthDeclarationDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NhiMonthDeclarationDetails} or a {@link Page} of {@link NhiMonthDeclarationDetails} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NhiMonthDeclarationDetailsQueryService extends QueryService<NhiMonthDeclarationDetails> {

    private final Logger log = LoggerFactory.getLogger(NhiMonthDeclarationDetailsQueryService.class);

    private final NhiMonthDeclarationDetailsRepository nhiMonthDeclarationDetailsRepository;

    public NhiMonthDeclarationDetailsQueryService(NhiMonthDeclarationDetailsRepository nhiMonthDeclarationDetailsRepository) {
        this.nhiMonthDeclarationDetailsRepository = nhiMonthDeclarationDetailsRepository;
    }

    /**
     * Return a {@link List} of {@link NhiMonthDeclarationDetails} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NhiMonthDeclarationDetails> findByCriteria(NhiMonthDeclarationDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NhiMonthDeclarationDetails> specification = createSpecification(criteria);
        return nhiMonthDeclarationDetailsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NhiMonthDeclarationDetails} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NhiMonthDeclarationDetails> findByCriteria(NhiMonthDeclarationDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NhiMonthDeclarationDetails> specification = createSpecification(criteria);
        return nhiMonthDeclarationDetailsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NhiMonthDeclarationDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NhiMonthDeclarationDetails> specification = createSpecification(criteria);
        return nhiMonthDeclarationDetailsRepository.count(specification);
    }

    /**
     * Function to convert NhiMonthDeclarationDetailsCriteria to a {@link Specification}
     */
    private Specification<NhiMonthDeclarationDetails> createSpecification(NhiMonthDeclarationDetailsCriteria criteria) {
        Specification<NhiMonthDeclarationDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NhiMonthDeclarationDetails_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), NhiMonthDeclarationDetails_.type));
            }
            if (criteria.getWay() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWay(), NhiMonthDeclarationDetails_.way));
            }
            if (criteria.getCaseTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCaseTotal(), NhiMonthDeclarationDetails_.caseTotal));
            }
            if (criteria.getPointTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPointTotal(), NhiMonthDeclarationDetails_.pointTotal));
            }
            if (criteria.getOutPatientPoint() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOutPatientPoint(), NhiMonthDeclarationDetails_.outPatientPoint));
            }
            if (criteria.getPreventiveCaseTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPreventiveCaseTotal(), NhiMonthDeclarationDetails_.preventiveCaseTotal));
            }
            if (criteria.getPreventivePointTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPreventivePointTotal(), NhiMonthDeclarationDetails_.preventivePointTotal));
            }
            if (criteria.getGeneralCaseTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGeneralCaseTotal(), NhiMonthDeclarationDetails_.generalCaseTotal));
            }
            if (criteria.getGeneralPointTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGeneralPointTotal(), NhiMonthDeclarationDetails_.generalPointTotal));
            }
            if (criteria.getProfessionalCaseTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProfessionalCaseTotal(), NhiMonthDeclarationDetails_.professionalCaseTotal));
            }
            if (criteria.getProfessionalPointTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProfessionalPointTotal(), NhiMonthDeclarationDetails_.professionalPointTotal));
            }
            if (criteria.getPartialCaseTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPartialCaseTotal(), NhiMonthDeclarationDetails_.partialCaseTotal));
            }
            if (criteria.getPartialPointTotal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPartialPointTotal(), NhiMonthDeclarationDetails_.partialPointTotal));
            }
            if (criteria.getFile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFile(), NhiMonthDeclarationDetails_.file));
            }
            if (criteria.getUploadTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUploadTime(), NhiMonthDeclarationDetails_.uploadTime));
            }
            if (criteria.getLocalId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocalId(), NhiMonthDeclarationDetails_.localId));
            }
            if (criteria.getNhiId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNhiId(), NhiMonthDeclarationDetails_.nhiId));
            }
            if (criteria.getNhiMonthDeclarationId() != null) {
                specification = specification.and(buildSpecification(criteria.getNhiMonthDeclarationId(),
                    root -> root.join(NhiMonthDeclarationDetails_.nhiMonthDeclaration, JoinType.LEFT).get(NhiMonthDeclaration_.id)));
            }
        }
        return specification;
    }
}
