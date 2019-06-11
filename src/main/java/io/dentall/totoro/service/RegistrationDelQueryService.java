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

import io.dentall.totoro.domain.RegistrationDel;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.RegistrationDelRepository;
import io.dentall.totoro.service.dto.RegistrationDelCriteria;

/**
 * Service for executing complex queries for RegistrationDel entities in the database.
 * The main input is a {@link RegistrationDelCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link RegistrationDel} or a {@link Page} of {@link RegistrationDel} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class RegistrationDelQueryService extends QueryService<RegistrationDel> {

    private final Logger log = LoggerFactory.getLogger(RegistrationDelQueryService.class);

    private final RegistrationDelRepository registrationDelRepository;

    public RegistrationDelQueryService(RegistrationDelRepository registrationDelRepository) {
        this.registrationDelRepository = registrationDelRepository;
    }

    /**
     * Return a {@link List} of {@link RegistrationDel} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<RegistrationDel> findByCriteria(RegistrationDelCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<RegistrationDel> specification = createSpecification(criteria);
        return registrationDelRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link RegistrationDel} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<RegistrationDel> findByCriteria(RegistrationDelCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<RegistrationDel> specification = createSpecification(criteria);
        return registrationDelRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(RegistrationDelCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<RegistrationDel> specification = createSpecification(criteria);
        return registrationDelRepository.count(specification);
    }

    /**
     * Function to convert RegistrationDelCriteria to a {@link Specification}
     */
    private Specification<RegistrationDel> createSpecification(RegistrationDelCriteria criteria) {
        Specification<RegistrationDel> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), RegistrationDel_.id));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), RegistrationDel_.status));
            }
            if (criteria.getArrivalTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getArrivalTime(), RegistrationDel_.arrivalTime));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), RegistrationDel_.type));
            }
            if (criteria.getOnSite() != null) {
                specification = specification.and(buildSpecification(criteria.getOnSite(), RegistrationDel_.onSite));
            }
            if (criteria.getNoCard() != null) {
                specification = specification.and(buildSpecification(criteria.getNoCard(), RegistrationDel_.noCard));
            }
            if (criteria.getAppointmentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAppointmentId(), RegistrationDel_.appointmentId));
            }
            if (criteria.getAccountingId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAccountingId(), RegistrationDel_.accountingId));
            }
        }
        return specification;
    }
}
