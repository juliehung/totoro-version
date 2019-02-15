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

import io.dentall.totoro.domain.Todo;
import io.dentall.totoro.domain.*; // for static metamodels
import io.dentall.totoro.repository.TodoRepository;
import io.dentall.totoro.service.dto.TodoCriteria;

/**
 * Service for executing complex queries for Todo entities in the database.
 * The main input is a {@link TodoCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Todo} or a {@link Page} of {@link Todo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TodoQueryService extends QueryService<Todo> {

    private final Logger log = LoggerFactory.getLogger(TodoQueryService.class);

    private final TodoRepository todoRepository;

    public TodoQueryService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /**
     * Return a {@link List} of {@link Todo} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Todo> findByCriteria(TodoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Todo> specification = createSpecification(criteria);
        return todoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Todo} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Todo> findByCriteria(TodoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Todo> specification = createSpecification(criteria);
        return todoRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TodoCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Todo> specification = createSpecification(criteria);
        return todoRepository.count(specification);
    }

    /**
     * Function to convert TodoCriteria to a {@link Specification}
     */
    private Specification<Todo> createSpecification(TodoCriteria criteria) {
        Specification<Todo> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Todo_.id));
            }
            if (criteria.getStstus() != null) {
                specification = specification.and(buildSpecification(criteria.getStstus(), Todo_.ststus));
            }
            if (criteria.getExpectedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getExpectedDate(), Todo_.expectedDate));
            }
            if (criteria.getRequiredTreatmentTime() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequiredTreatmentTime(), Todo_.requiredTreatmentTime));
            }
            if (criteria.getNote() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNote(), Todo_.note));
            }
            if (criteria.getPatientId() != null) {
                specification = specification.and(buildSpecification(criteria.getPatientId(),
                    root -> root.join(Todo_.patient, JoinType.LEFT).get(Patient_.id)));
            }
            if (criteria.getTreatmentProcedureId() != null) {
                specification = specification.and(buildSpecification(criteria.getTreatmentProcedureId(),
                    root -> root.join(Todo_.treatmentProcedures, JoinType.LEFT).get(TreatmentProcedure_.id)));
            }
        }
        return specification;
    }
}
