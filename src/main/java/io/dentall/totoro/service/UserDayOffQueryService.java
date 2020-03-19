package io.dentall.totoro.service;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.UserDayOff;
import io.dentall.totoro.repository.UserDayOffRepository;
import io.dentall.totoro.service.dto.UserDayOffCriteria;
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
 * Service for executing complex queries for {@link UserDayOff} entities in the database.
 * The main input is a {@link UserDayOffCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link UserDayOff} or a {@link Page} of {@link UserDayOff} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class UserDayOffQueryService extends QueryService<UserDayOff> {

    private final Logger log = LoggerFactory.getLogger(UserDayOffQueryService.class);

    private final UserDayOffRepository userDayOffRepository;

    public UserDayOffQueryService(UserDayOffRepository userDayOffRepository) {
        this.userDayOffRepository = userDayOffRepository;
    }

    /**
     * Return a {@link List} of {@link UserDayOff} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<UserDayOff> findByCriteria(UserDayOffCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<UserDayOff> specification = createSpecification(criteria);
        return userDayOffRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link UserDayOff} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<UserDayOff> findByCriteria(UserDayOffCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<UserDayOff> specification = createSpecification(criteria);
        return userDayOffRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(UserDayOffCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<UserDayOff> specification = createSpecification(criteria);
        return userDayOffRepository.count(specification);
    }

    /**
     * Function to convert {@link UserDayOffCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<UserDayOff> createSpecification(UserDayOffCriteria criteria) {
        Specification<UserDayOff> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), UserDayOff_.id));
            }
            if (criteria.getUserId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getUserId(), UserDayOff_.userId));
            }
            if (criteria.getFromDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFromDate(), UserDayOff_.fromDate));
            }
            if (criteria.getToDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getToDate(), UserDayOff_.toDate));
            }
        }
        return specification;
    }
}
