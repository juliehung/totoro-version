package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.UserShift;
import io.dentall.totoro.repository.UserShiftRepository;
import io.dentall.totoro.service.dto.UserShiftCriteria;
import io.github.jhipster.service.QueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for executing complex queries for {@link UserShift} entities in the database.
 * The main input is a {@link UserShiftCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserShift} or a {@link Page} of {@link UserShift} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserShiftQueryService extends QueryService<UserShift> {

    private final Logger log = LoggerFactory.getLogger(UserShiftQueryService.class);

    private final UserShiftRepository userShiftRepository;

    public UserShiftQueryService(UserShiftRepository userShiftRepository) {
        this.userShiftRepository = userShiftRepository;
    }

    /**
     * Return a {@link List} of {@link UserShift} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserShift> findByCriteria(UserShiftCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserShift> specification = createSpecification(criteria);
        return userShiftRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserShift} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserShift> findByCriteria(UserShiftCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserShift> specification = createSpecification(criteria);
        return userShiftRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserShiftCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserShift> specification = createSpecification(criteria);
        return userShiftRepository.count(specification);
    }

    /**
     * Function to convert {@link UserShiftCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserShift> createSpecification(UserShiftCriteria criteria) {
        Specification<UserShift> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserShift_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), UserShift_.userId));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), UserShift_.fromDate));
            }
            if (criteria.getToDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToDate(), UserShift_.toDate));
            }
        }
        return specification;
    }
}
