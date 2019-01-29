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

import io.dentall.totoro.domain.Ledger;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.LedgerRepository;
import io.dentall.totoro.service.dto.LedgerCriteria;

/**
 * Service for executing complex queries for Ledger entities in the database.
 * The main input is a {@link LedgerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Ledger} or a {@link Page} of {@link Ledger} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class LedgerQueryService extends QueryService<Ledger> {

    private final Logger log = LoggerFactory.getLogger(LedgerQueryService.class);

    private final LedgerRepository ledgerRepository;

    public LedgerQueryService(LedgerRepository ledgerRepository) {
        this.ledgerRepository = ledgerRepository;
    }

    /**
     * Return a {@link List} of {@link Ledger} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Ledger> findByCriteria(LedgerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Ledger> specification = createSpecification(criteria);
        return ledgerRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Ledger} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Ledger> findByCriteria(LedgerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Ledger> specification = createSpecification(criteria);
        return ledgerRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(LedgerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Ledger> specification = createSpecification(criteria);
        return ledgerRepository.count(specification);
    }

    /**
     * Function to convert LedgerCriteria to a {@link Specification}
     */
    private Specification<Ledger> createSpecification(LedgerCriteria criteria) {
        Specification<Ledger> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Ledger_.id));
            }
            if (criteria.getAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAmount(), Ledger_.amount));
            }
            if (criteria.getCharge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCharge(), Ledger_.charge));
            }
            if (criteria.getArrears() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getArrears(), Ledger_.arrears));
            }
        }
        return specification;
    }
}
