package io.dentall.totoro.service;

import io.dentall.totoro.domain.Todo;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.repository.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Todo.
 */
@Service
@Transactional
public class TodoService {

    private final Logger log = LoggerFactory.getLogger(TodoService.class);

    private final TodoRepository todoRepository;

    private final TreatmentProcedureService treatmentProcedureService;

    public TodoService(TodoRepository todoRepository, TreatmentProcedureService treatmentProcedureService) {
        this.todoRepository = todoRepository;
        this.treatmentProcedureService = treatmentProcedureService;
    }

    /**
     * Save a todo.
     *
     * @param todo the entity to save
     * @return the persisted entity
     */
    public Todo save(Todo todo) {
        log.debug("Request to save Todo : {}", todo);

        return todoRepository.save(todo).treatmentProcedures(
            todo
                .getTreatmentProcedures()
                .stream()
                .map(TreatmentProcedure::getId)
                .map(treatmentProcedureService::findOne)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(treatmentProcedure -> treatmentProcedure.todo(todo))
                .collect(Collectors.toSet())
        );
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
        todoRepository.deleteById(id);
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
                if (updateTodo.getStstus() != null) {
                    todo.setStstus((updateTodo.getStstus()));
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

                return todo;
            })
            .get();
    }
}
