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

import io.dentall.totoro.domain.Calendar;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.CalendarRepository;
import io.dentall.totoro.service.dto.CalendarCriteria;

/**
 * Service for executing complex queries for Calendar entities in the database.
 * The main input is a {@link CalendarCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Calendar} or a {@link Page} of {@link Calendar} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CalendarQueryService extends QueryService<Calendar> {

    private final Logger log = LoggerFactory.getLogger(CalendarQueryService.class);

    private final CalendarRepository calendarRepository;

    public CalendarQueryService(CalendarRepository calendarRepository) {
        this.calendarRepository = calendarRepository;
    }

    /**
     * Return a {@link List} of {@link Calendar} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Calendar> findByCriteria(CalendarCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Calendar> specification = createSpecification(criteria);
        return calendarRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Calendar} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Calendar> findByCriteria(CalendarCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Calendar> specification = createSpecification(criteria);
        return calendarRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CalendarCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Calendar> specification = createSpecification(criteria);
        return calendarRepository.count(specification);
    }

    /**
     * Function to convert CalendarCriteria to a {@link Specification}
     */
    private Specification<Calendar> createSpecification(CalendarCriteria criteria) {
        Specification<Calendar> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Calendar_.id));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), Calendar_.date));
            }
            if (criteria.getTimeInterval() != null) {
                specification = specification.and(buildSpecification(criteria.getTimeInterval(), Calendar_.timeInterval));
            }
            if (criteria.getTimeType() != null) {
                specification = specification.and(buildSpecification(criteria.getTimeType(), Calendar_.timeType));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartTime(), Calendar_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndTime(), Calendar_.endTime));
            }
        }
        return specification;
    }
}
