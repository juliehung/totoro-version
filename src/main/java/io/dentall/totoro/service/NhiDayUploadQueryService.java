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

import io.dentall.totoro.domain.NhiDayUpload;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.NhiDayUploadRepository;
import io.dentall.totoro.service.dto.NhiDayUploadCriteria;

/**
 * Service for executing complex queries for NhiDayUpload entities in the database.
 * The main input is a {@link NhiDayUploadCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NhiDayUpload} or a {@link Page} of {@link NhiDayUpload} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NhiDayUploadQueryService extends QueryService<NhiDayUpload> {

    private final Logger log = LoggerFactory.getLogger(NhiDayUploadQueryService.class);

    private final NhiDayUploadRepository nhiDayUploadRepository;

    public NhiDayUploadQueryService(NhiDayUploadRepository nhiDayUploadRepository) {
        this.nhiDayUploadRepository = nhiDayUploadRepository;
    }

    /**
     * Return a {@link List} of {@link NhiDayUpload} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NhiDayUpload> findByCriteria(NhiDayUploadCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NhiDayUpload> specification = createSpecification(criteria);
        return nhiDayUploadRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NhiDayUpload} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NhiDayUpload> findByCriteria(NhiDayUploadCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NhiDayUpload> specification = createSpecification(criteria);
        return nhiDayUploadRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NhiDayUploadCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NhiDayUpload> specification = createSpecification(criteria);
        return nhiDayUploadRepository.count(specification);
    }

    /**
     * Function to convert NhiDayUploadCriteria to a {@link Specification}
     */
    private Specification<NhiDayUpload> createSpecification(NhiDayUploadCriteria criteria) {
        Specification<NhiDayUpload> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NhiDayUpload_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), NhiDayUpload_.date));
            }
            if (criteria.getNhiDayUploadDetailsId() != null) {
                specification = specification.and(buildSpecification(criteria.getNhiDayUploadDetailsId(),
                    root -> root.join(NhiDayUpload_.nhiDayUploadDetails, JoinType.LEFT).get(NhiDayUploadDetails_.id)));
            }
        }
        return specification;
    }
}
