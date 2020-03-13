package io.dentall.totoro.service;

import io.dentall.totoro.domain.HomePageCover;
import io.dentall.totoro.domain.HomePageCover_;
import io.dentall.totoro.repository.HomePageCoverRepository;
import io.dentall.totoro.service.dto.HomePageCoverCriteria;
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
 * Service for executing complex queries for HomePageCover entities in the database.
 * The main input is a {@link HomePageCoverCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link HomePageCover} or a {@link Page} of {@link HomePageCover} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class HomePageCoverQueryService extends QueryService<HomePageCover> {

    private final Logger log = LoggerFactory.getLogger(HomePageCoverQueryService.class);

    private final HomePageCoverRepository homePageCoverRepository;

    public HomePageCoverQueryService(HomePageCoverRepository homePageCoverRepository) {
        this.homePageCoverRepository = homePageCoverRepository;
    }

    /**
     * Return a {@link List} of {@link HomePageCover} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<HomePageCover> findByCriteria(HomePageCoverCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<HomePageCover> specification = createSpecification(criteria);
        return homePageCoverRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link HomePageCover} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<HomePageCover> findByCriteria(HomePageCoverCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<HomePageCover> specification = createSpecification(criteria);
        return homePageCoverRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(HomePageCoverCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<HomePageCover> specification = createSpecification(criteria);
        return homePageCoverRepository.count(specification);
    }

    /**
     * Function to convert HomePageCoverCriteria to a {@link Specification}
     */
    private Specification<HomePageCover> createSpecification(HomePageCoverCriteria criteria) {
        Specification<HomePageCover> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getPatientId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPatientId(), HomePageCover_.patientId));
            }
            if (criteria.getSourceTable() != null) {
                specification = specification.and(buildSpecification(criteria.getSourceTable(), HomePageCover_.sourceTable));
            }
            if (criteria.getSourceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSourceId(), HomePageCover_.sourceId));
            }
        }
        return specification;
    }
}
