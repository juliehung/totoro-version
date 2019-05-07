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

import io.dentall.totoro.domain.Drug;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.DrugRepository;
import io.dentall.totoro.service.dto.DrugCriteria;

/**
 * Service for executing complex queries for Drug entities in the database.
 * The main input is a {@link DrugCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Drug} or a {@link Page} of {@link Drug} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DrugQueryService extends QueryService<Drug> {

    private final Logger log = LoggerFactory.getLogger(DrugQueryService.class);

    private final DrugRepository drugRepository;

    public DrugQueryService(DrugRepository drugRepository) {
        this.drugRepository = drugRepository;
    }

    /**
     * Return a {@link List} of {@link Drug} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Drug> findByCriteria(DrugCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Drug> specification = createSpecification(criteria);
        return drugRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Drug} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Drug> findByCriteria(DrugCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Drug> specification = createSpecification(criteria);
        return drugRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DrugCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Drug> specification = createSpecification(criteria);
        return drugRepository.count(specification);
    }

    /**
     * Function to convert DrugCriteria to a {@link Specification}
     */
    private Specification<Drug> createSpecification(DrugCriteria criteria) {
        Specification<Drug> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Drug_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Drug_.name));
            }
            if (criteria.getChineseName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getChineseName(), Drug_.chineseName));
            }
            if (criteria.getUnit() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUnit(), Drug_.unit));
            }
            if (criteria.getPrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPrice(), Drug_.price));
            }
            if (criteria.getQuantity() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getQuantity(), Drug_.quantity));
            }
            if (criteria.getFrequency() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFrequency(), Drug_.frequency));
            }
            if (criteria.getWay() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWay(), Drug_.way));
            }
            if (criteria.getNhiCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNhiCode(), Drug_.nhiCode));
            }
            if (criteria.getWarning() != null) {
                specification = specification.and(buildStringSpecification(criteria.getWarning(), Drug_.warning));
            }
        }
        return specification;
    }
}
