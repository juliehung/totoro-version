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

import io.dentall.totoro.domain.Esign;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.EsignRepository;
import io.dentall.totoro.service.dto.EsignCriteria;

/**
 * Service for executing complex queries for Esign entities in the database.
 * The main input is a {@link EsignCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Esign} or a {@link Page} of {@link Esign} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EsignQueryService extends QueryService<Esign> {

    private final Logger log = LoggerFactory.getLogger(EsignQueryService.class);

    private final EsignRepository esignRepository;

    public EsignQueryService(EsignRepository esignRepository) {
        this.esignRepository = esignRepository;
    }

    /**
     * Return a {@link List} of {@link Esign} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Esign> findByCriteria(EsignCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Esign> specification = createSpecification(criteria);
        return esignRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Esign} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Esign> findByCriteria(EsignCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Esign> specification = createSpecification(criteria);
        return esignRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EsignCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Esign> specification = createSpecification(criteria);
        return esignRepository.count(specification);
    }

    /**
     * Function to convert EsignCriteria to a {@link Specification}
     */
    private Specification<Esign> createSpecification(EsignCriteria criteria) {
        Specification<Esign> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Esign_.id));
            }
            if (criteria.getPatientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPatientId(), Esign_.patientId));
            }
            if (criteria.getCreateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreateTime(), Esign_.createTime));
            }
            if (criteria.getUpdateTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUpdateTime(), Esign_.updateTime));
            }
            if (criteria.getSourceType() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceType(), Esign_.sourceType));
            }
        }
        return specification;
    }
}
