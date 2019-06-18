package io.dentall.totoro.service;

import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.Todo;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.domain.enumeration.TodoStatus;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.TodoRepository;
import io.dentall.totoro.service.util.ProblemUtil;
import io.dentall.totoro.service.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Status;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Todo.
 */
@Service
@Transactional
public class TodoService {

    private final Logger log = LoggerFactory.getLogger(TodoService.class);

    private final TodoRepository todoRepository;

    private final RelationshipService relationshipService;

    private final PatientRepository patientRepository;

    public TodoService(
        TodoRepository todoRepository,
        RelationshipService relationshipService,
        PatientRepository patientRepository
    ) {
        this.todoRepository = todoRepository;
        this.relationshipService = relationshipService;
        this.patientRepository = patientRepository;
    }

    /**
     * Save a todo.
     *
     * @param todo the entity to save
     * @return the persisted entity
     */
    public Todo save(Todo todo) {
        log.debug("Request to save Todo : {}", todo);

        Set<TreatmentProcedure> treatmentProcedures = todo.getTreatmentProcedures();
        todo = todoRepository.save(todo.treatmentProcedures(null));
        relationshipService.addRelationshipWithTreatmentProcedures(todo.treatmentProcedures(treatmentProcedures));

        return todo;
    }

    /**
     * Get all the todos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Todo> findAll(Pageable pageable) {
        log.debug("Request to get all Todos");
        return todoRepository.findAll(pageable);
    }


    /**
     * Get one todo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Todo> findOne(Long id) {
        log.debug("Request to get Todo : {}", id);
        return todoRepository.findById(id);
    }

    /**
     * Delete the todo by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Todo : {}", id);

        todoRepository.findById(id).ifPresent(todo -> {
            if (todo.getStatus() != TodoStatus.TEMPORARY) {
                throw new ProblemUtil("A non-temporary todo cannot delete", Status.BAD_REQUEST);
            }

            StreamUtil.asStream(todo.getTreatmentProcedures()).forEach(treatmentProcedure -> treatmentProcedure.setTodo(null));
            relationshipService.deleteTreatmentProcedures(todo.getTreatmentProcedures());

            if (todo.getPatient() != null) {
                Patient patient = todo.getPatient();
                patient.getTodos().remove(todo);
            }

            todoRepository.deleteById(id);
        });
    }

    /**
     * Update the todo.
     *
     * @param updateTodo the update entity
     * @return the entity
     */
    public Todo update(Todo updateTodo) {
        log.debug("Request to update Todo : {}", updateTodo);

        return todoRepository
            .findById(updateTodo.getId())
            .map(todo -> {
                if (updateTodo.getStatus() != null) {
                    todo.setStatus((updateTodo.getStatus()));
                }

                if (updateTodo.getExpectedDate() != null) {
                    todo.setExpectedDate((updateTodo.getExpectedDate()));
                }

                if (updateTodo.getRequiredTreatmentTime() != null) {
                    todo.setRequiredTreatmentTime((updateTodo.getRequiredTreatmentTime()));
                }

                if (updateTodo.getNote() != null) {
                    todo.setNote((updateTodo.getNote()));
                }

                if (updateTodo.getPatient() != null && updateTodo.getPatient().getId() != null) {
                    patientRepository.findById(updateTodo.getPatient().getId()).ifPresent(todo::setPatient);
                }

                if (updateTodo.getTreatmentProcedures() != null) {
                    Set<TreatmentProcedure> originTxPs = todo.getTreatmentProcedures();
                    relationshipService.addRelationshipWithTreatmentProcedures(todo.treatmentProcedures(updateTodo.getTreatmentProcedures()));
                    Set<Long> ids = StreamUtil.asStream(todo.getTreatmentProcedures()).map(TreatmentProcedure::getId).collect(Collectors.toSet());
                    relationshipService.deleteTreatmentProcedures(
                        StreamUtil.asStream(originTxPs)
                            .filter(treatmentProcedure -> !ids.contains(treatmentProcedure.getId()))
                            .map(treatmentProcedure -> treatmentProcedure.todo(null))
                            .filter(RelationshipService.isDeletable)
                            .collect(Collectors.toSet())
                    );
                }

                return todo;
            })
            .get();
    }
}
