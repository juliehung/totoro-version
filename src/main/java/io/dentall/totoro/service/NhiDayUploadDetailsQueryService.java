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

import io.dentall.totoro.domain.NhiDayUploadDetails;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.NhiDayUploadDetailsRepository;
import io.dentall.totoro.service.dto.NhiDayUploadDetailsCriteria;

/**
 * Service for executing complex queries for NhiDayUploadDetails entities in the database.
 * The main input is a {@link NhiDayUploadDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NhiDayUploadDetails} or a {@link Page} of {@link NhiDayUploadDetails} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NhiDayUploadDetailsQueryService extends QueryService<NhiDayUploadDetails> {

    private final Logger log = LoggerFactory.getLogger(NhiDayUploadDetailsQueryService.class);

    private final NhiDayUploadDetailsRepository nhiDayUploadDetailsRepository;

    public NhiDayUploadDetailsQueryService(NhiDayUploadDetailsRepository nhiDayUploadDetailsRepository) {
        this.nhiDayUploadDetailsRepository = nhiDayUploadDetailsRepository;
    }

    /**
     * Return a {@link List} of {@link NhiDayUploadDetails} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NhiDayUploadDetails> findByCriteria(NhiDayUploadDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NhiDayUploadDetails> specification = createSpecification(criteria);
        return nhiDayUploadDetailsRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NhiDayUploadDetails} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NhiDayUploadDetails> findByCriteria(NhiDayUploadDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NhiDayUploadDetails> specification = createSpecification(criteria);
        return nhiDayUploadDetailsRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NhiDayUploadDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NhiDayUploadDetails> specification = createSpecification(criteria);
        return nhiDayUploadDetailsRepository.count(specification);
    }

    /**
     * Function to convert NhiDayUploadDetailsCriteria to a {@link Specification}
     */
    private Specification<NhiDayUploadDetails> createSpecification(NhiDayUploadDetailsCriteria criteria) {
        Specification<NhiDayUploadDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), NhiDayUploadDetails_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), NhiDayUploadDetails_.type));
            }
            if (criteria.getNhiDayUploadId() != null) {
                specification = specification.and(buildSpecification(criteria.getNhiDayUploadId(),
                    root -> root.join(NhiDayUploadDetails_.nhiDayUpload, JoinType.LEFT).get(NhiDayUpload_.id)));
            }
        }
        return specification;
    }
}
