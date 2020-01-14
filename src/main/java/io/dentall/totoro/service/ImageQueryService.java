package io.dentall.totoro.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.dentall.totoro.domain.Image;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.ImageRepository;
import io.dentall.totoro.service.dto.ImageCriteria;

import javax.persistence.criteria.JoinType;

/**
 * Service for executing complex queries for Image entities in the database.
 * The main input is a {@link ImageCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Image} or a {@link Page} of {@link Image} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ImageQueryService extends QueryService<Image> {

    private final Logger log = LoggerFactory.getLogger(ImageQueryService.class);

    private final ImageRepository imageRepository;

    public ImageQueryService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    /**
     * Return a {@link List} of {@link Image} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Image> findByCriteria(ImageCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Image> specification = createSpecification(criteria);
        return imageRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Image} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Image> findByCriteria(ImageCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Image> specification = createSpecification(criteria);
        return imageRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ImageCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Image> specification = createSpecification(criteria);
        return imageRepository.count(specification);
    }

    /**
     * Function to convert ImageCriteria to a {@link Specification}
     */
    private Specification<Image> createSpecification(ImageCriteria criteria) {
        Specification<Image> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Image_.id));
            }
            if (criteria.getFilePath() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFilePath(), Image_.filePath));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), Image_.fileName));
            }
            if (criteria.getFetchUrl() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFetchUrl(), Image_.fetchUrl));
            }

            if (criteria.getGroupId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGroupId(), Image_.groupId));
            }

            if (criteria.getSize() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSize(), Image_.size));
            }

            if (criteria.getPatientId() != null) {
                specification = specification.and(buildSpecification(criteria.getPatientId(),
                    root -> root.join(Image_.patient, JoinType.LEFT).get(Patient_.id)));
            }
        }
        return specification;
    }
}
