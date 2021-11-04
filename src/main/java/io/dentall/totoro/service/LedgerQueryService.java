package io.dentall.totoro.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.criteria.JoinType;

import io.dentall.totoro.repository.LedgerGroupRepository;
import io.dentall.totoro.service.mapper.LedgerGroupMapper;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    private final LedgerGroupRepository ledgerGroupRepository;

    public LedgerQueryService(
        LedgerRepository ledgerRepository,
        LedgerGroupRepository ledgerGroupRepository
    ) {
        this.ledgerRepository = ledgerRepository;
        this.ledgerGroupRepository = ledgerGroupRepository;
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
        page.getSortOr(new Sort(Sort.Direction.DESC, "date"));

        Page<Ledger> ledgers = ledgerRepository.findAll(specification, page);
        return ledgers;
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
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), Ledger_.note));
            }
            if (criteria.getDoctor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDoctor(), Ledger_.doctor));
            }
            if (criteria.getProjectCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getProjectCode(), Ledger_.projectCode));
            }
            if (criteria.getDisplayName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDisplayName(), Ledger_.displayName));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(),
                    root -> root.join(Ledger_.ledgerGroup, JoinType.LEFT).get(LedgerGroup_.type)));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Ledger_.date));
            }
            if (criteria.getPatientId() != null) {
                specification = specification.and(buildSpecification(criteria.getPatientId(),
                    root -> root.join(Ledger_.ledgerGroup, JoinType.LEFT).get(LedgerGroup_.patientId)));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Ledger_.createdDate));
            }
            if (criteria.getCreatedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCreatedBy(), Ledger_.createdBy));
            }
            if (criteria.getLastModifiedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLastModifiedDate(), Ledger_.lastModifiedDate));
            }
            if (criteria.getLastModifiedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLastModifiedBy(), Ledger_.lastModifiedBy));
            }
            if (criteria.getTreatmentPlanId() != null) {
                specification = specification.and(buildSpecification(criteria.getTreatmentPlanId(),
                    root -> root.join(Ledger_.treatmentPlan, JoinType.LEFT).get(TreatmentPlan_.id)));
            }
        }
        return specification;
    }
}
