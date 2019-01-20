package io.dentall.totoro.service;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import io.dentall.totoro.service.util.FilterUtil;
import io.github.jhipster.service.filter.LocalDateFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.dentall.totoro.domain.CalendarSetting;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.CalendarSettingRepository;
import io.dentall.totoro.service.dto.CalendarSettingCriteria;

/**
 * Service for executing complex queries for CalendarSetting entities in the database.
 * The main input is a {@link CalendarSettingCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CalendarSetting} or a {@link Page} of {@link CalendarSetting} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CalendarSettingQueryService extends QueryService<CalendarSetting> {

    private final Logger log = LoggerFactory.getLogger(CalendarSettingQueryService.class);

    private final CalendarSettingRepository calendarSettingRepository;

    public CalendarSettingQueryService(CalendarSettingRepository calendarSettingRepository) {
        this.calendarSettingRepository = calendarSettingRepository;
    }

    /**
     * Return a {@link List} of {@link CalendarSetting} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CalendarSetting> findByCriteria(CalendarSettingCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CalendarSetting> specification = createSpecification(criteria);
        return calendarSettingRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link CalendarSetting} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CalendarSetting> findByCriteria(CalendarSettingCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CalendarSetting> specification = createSpecification(criteria);
        return calendarSettingRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CalendarSettingCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CalendarSetting> specification = createSpecification(criteria);
        return calendarSettingRepository.count(specification);
    }

    /**
     * Function to convert CalendarSettingCriteria to a {@link Specification}
     */
    private Specification<CalendarSetting> createSpecification(CalendarSettingCriteria criteria) {
        Specification<CalendarSetting> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CalendarSetting_.id));
            }
            if (criteria.getStartTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getStartTime(), CalendarSetting_.startTime));
            }
            if (criteria.getEndTime() != null) {
                specification = specification.and(buildStringSpecification(criteria.getEndTime(), CalendarSetting_.endTime));
            }
            if (criteria.getTimeInterval() != null) {
                specification = specification.and(buildSpecification(criteria.getTimeInterval(), CalendarSetting_.timeInterval));
            }
            if (criteria.getWeekday() != null) {
                specification = specification.and(buildSpecification(criteria.getWeekday(), CalendarSetting_.weekday));
            }

            if (criteria.getStartDate() != null && criteria.getEndDate() != null) {
                LocalDateFilter startDateFilter = criteria.getStartDate();
                LocalDateFilter endDateFilter = criteria.getEndDate();
                if (startDateFilter.getEquals() != null && endDateFilter.getEquals() != null) {
                    Specification<CalendarSetting> endDateIsNull = Specification.where(null);
                    // setting_endDate is null
                    endDateIsNull = endDateIsNull.and((root, query, builder) -> builder.isNull(root.get(CalendarSetting_.endDate)));

                    specification = specification
                        // setting_startDate <= endDate
                        .and((root, query, builder) -> builder.lessThanOrEqualTo(root.get(CalendarSetting_.startDate), endDateFilter.getEquals()))
                        .and(endDateIsNull
                            // startDate <= setting_endDate
                            .or((root, query, builder) -> builder.greaterThanOrEqualTo(root.get(CalendarSetting_.endDate), startDateFilter.getEquals()))
                        );
                } else {
                    specification = specification
                        .and(buildRangeSpecification(criteria.getStartDate(), CalendarSetting_.startDate))
                        .and(buildRangeSpecification(criteria.getEndDate(), CalendarSetting_.endDate));
                }
            } else {
                if (criteria.getStartDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getStartDate(), CalendarSetting_.startDate));
                }

                if (criteria.getEndDate() != null) {
                    specification = specification.and(buildRangeSpecification(criteria.getEndDate(), CalendarSetting_.endDate));
                }
            }
        }
        return specification;
    }
}
