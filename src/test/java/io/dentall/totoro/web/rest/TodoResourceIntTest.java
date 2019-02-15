package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.Todo;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.TodoRepository;
import io.dentall.totoro.repository.TreatmentProcedureRepository;
import io.dentall.totoro.service.TodoService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.dto.TodoCriteria;
import io.dentall.totoro.service.TodoQueryService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.dentall.totoro.domain.enumeration.TodoStatus;
/**
 * Test class for the TodoResource REST controller.
 *
 * @see TodoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class TodoResourceIntTest {

    private static final TodoStatus DEFAULT_STSTUS = TodoStatus.PENDING;
    private static final TodoStatus UPDATED_STSTUS = TodoStatus.FINISHED;

    private static final LocalDate DEFAULT_EXPECTED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EXPECTED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_REQUIRED_TREATMENT_TIME = 1;
    private static final Integer UPDATED_REQUIRED_TREATMENT_TIME = 2;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoQueryService todoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TreatmentProcedureRepository treatmentProcedureRepository;

    private MockMvc restTodoMockMvc;

    private Todo todo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TodoResource todoResource = new TodoResource(todoService, todoQueryService);
        this.restTodoMockMvc = MockMvcBuilders.standaloneSetup(todoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Todo createEntity(EntityManager em) {
        Todo todo = new Todo()
            .ststus(DEFAULT_STSTUS)
            .expectedDate(DEFAULT_EXPECTED_DATE)
            .requiredTreatmentTime(DEFAULT_REQUIRED_TREATMENT_TIME)
            .note(DEFAULT_NOTE);
        return todo;
    }

    @Before
    public void initTest() {
        todo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTodo() throws Exception {
        int databaseSizeBeforeCreate = todoRepository.findAll().size();

        Patient patient = patientRepository.save(PatientResourceIntTest.createEntity(em));
        todo.setPatient(patient);
        TreatmentProcedure treatmentProcedure1 = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        todo.getTreatmentProcedures().add(treatmentProcedure1);
        TreatmentProcedure treatmentProcedure2 = treatmentProcedureRepository.save(TreatmentProcedureResourceIntTest.createEntity(em));
        todo.getTreatmentProcedures().add(treatmentProcedure2);

        // Create the Todo
        restTodoMockMvc.perform(post("/api/todos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(todo)))
            .andExpect(status().isCreated());

        // Validate the Todo in the database
        List<Todo> todoList = todoRepository.findAll();
        assertThat(todoList).hasSize(databaseSizeBeforeCreate + 1);
        Todo testTodo = todoList.get(todoList.size() - 1);
        assertThat(testTodo.getStstus()).isEqualTo(DEFAULT_STSTUS);
        assertThat(testTodo.getExpectedDate()).isEqualTo(DEFAULT_EXPECTED_DATE);
        assertThat(testTodo.getRequiredTreatmentTime()).isEqualTo(DEFAULT_REQUIRED_TREATMENT_TIME);
        assertThat(testTodo.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testTodo.getPatient().getId()).isEqualTo(patient.getId());
        assertThat(testTodo.getTreatmentProcedures().size()).isEqualTo(2);
    }

    @Test
    @Transactional
    public void createTodoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = todoRepository.findAll().size();

        // Create the Todo with an existing ID
        todo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTodoMockMvc.perform(post("/api/todos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(todo)))
            .andExpect(status().isBadRequest());

        // Validate the Todo in the database
        List<Todo> todoList = todoRepository.findAll();
        assertThat(todoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTodos() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList
        restTodoMockMvc.perform(get("/api/todos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(todo.getId().intValue())))
            .andExpect(jsonPath("$.[*].ststus").value(hasItem(DEFAULT_STSTUS.toString())))
            .andExpect(jsonPath("$.[*].expectedDate").value(hasItem(DEFAULT_EXPECTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].requiredTreatmentTime").value(hasItem(DEFAULT_REQUIRED_TREATMENT_TIME)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getTodo() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get the todo
        restTodoMockMvc.perform(get("/api/todos/{id}", todo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(todo.getId().intValue()))
            .andExpect(jsonPath("$.ststus").value(DEFAULT_STSTUS.toString()))
            .andExpect(jsonPath("$.expectedDate").value(DEFAULT_EXPECTED_DATE.toString()))
            .andExpect(jsonPath("$.requiredTreatmentTime").value(DEFAULT_REQUIRED_TREATMENT_TIME))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getAllTodosByStstusIsEqualToSomething() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where ststus equals to DEFAULT_STSTUS
        defaultTodoShouldBeFound("ststus.equals=" + DEFAULT_STSTUS);

        // Get all the todoList where ststus equals to UPDATED_STSTUS
        defaultTodoShouldNotBeFound("ststus.equals=" + UPDATED_STSTUS);
    }

    @Test
    @Transactional
    public void getAllTodosByStstusIsInShouldWork() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where ststus in DEFAULT_STSTUS or UPDATED_STSTUS
        defaultTodoShouldBeFound("ststus.in=" + DEFAULT_STSTUS + "," + UPDATED_STSTUS);

        // Get all the todoList where ststus equals to UPDATED_STSTUS
        defaultTodoShouldNotBeFound("ststus.in=" + UPDATED_STSTUS);
    }

    @Test
    @Transactional
    public void getAllTodosByStstusIsNullOrNotNull() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where ststus is not null
        defaultTodoShouldBeFound("ststus.specified=true");

        // Get all the todoList where ststus is null
        defaultTodoShouldNotBeFound("ststus.specified=false");
    }

    @Test
    @Transactional
    public void getAllTodosByExpectedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where expectedDate equals to DEFAULT_EXPECTED_DATE
        defaultTodoShouldBeFound("expectedDate.equals=" + DEFAULT_EXPECTED_DATE);

        // Get all the todoList where expectedDate equals to UPDATED_EXPECTED_DATE
        defaultTodoShouldNotBeFound("expectedDate.equals=" + UPDATED_EXPECTED_DATE);
    }

    @Test
    @Transactional
    public void getAllTodosByExpectedDateIsInShouldWork() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where expectedDate in DEFAULT_EXPECTED_DATE or UPDATED_EXPECTED_DATE
        defaultTodoShouldBeFound("expectedDate.in=" + DEFAULT_EXPECTED_DATE + "," + UPDATED_EXPECTED_DATE);

        // Get all the todoList where expectedDate equals to UPDATED_EXPECTED_DATE
        defaultTodoShouldNotBeFound("expectedDate.in=" + UPDATED_EXPECTED_DATE);
    }

    @Test
    @Transactional
    public void getAllTodosByExpectedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where expectedDate is not null
        defaultTodoShouldBeFound("expectedDate.specified=true");

        // Get all the todoList where expectedDate is null
        defaultTodoShouldNotBeFound("expectedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTodosByExpectedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where expectedDate greater than or equals to DEFAULT_EXPECTED_DATE
        defaultTodoShouldBeFound("expectedDate.greaterOrEqualThan=" + DEFAULT_EXPECTED_DATE);

        // Get all the todoList where expectedDate greater than or equals to UPDATED_EXPECTED_DATE
        defaultTodoShouldNotBeFound("expectedDate.greaterOrEqualThan=" + UPDATED_EXPECTED_DATE);
    }

    @Test
    @Transactional
    public void getAllTodosByExpectedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where expectedDate less than or equals to DEFAULT_EXPECTED_DATE
        defaultTodoShouldNotBeFound("expectedDate.lessThan=" + DEFAULT_EXPECTED_DATE);

        // Get all the todoList where expectedDate less than or equals to UPDATED_EXPECTED_DATE
        defaultTodoShouldBeFound("expectedDate.lessThan=" + UPDATED_EXPECTED_DATE);
    }


    @Test
    @Transactional
    public void getAllTodosByRequiredTreatmentTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where requiredTreatmentTime equals to DEFAULT_REQUIRED_TREATMENT_TIME
        defaultTodoShouldBeFound("requiredTreatmentTime.equals=" + DEFAULT_REQUIRED_TREATMENT_TIME);

        // Get all the todoList where requiredTreatmentTime equals to UPDATED_REQUIRED_TREATMENT_TIME
        defaultTodoShouldNotBeFound("requiredTreatmentTime.equals=" + UPDATED_REQUIRED_TREATMENT_TIME);
    }

    @Test
    @Transactional
    public void getAllTodosByRequiredTreatmentTimeIsInShouldWork() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where requiredTreatmentTime in DEFAULT_REQUIRED_TREATMENT_TIME or UPDATED_REQUIRED_TREATMENT_TIME
        defaultTodoShouldBeFound("requiredTreatmentTime.in=" + DEFAULT_REQUIRED_TREATMENT_TIME + "," + UPDATED_REQUIRED_TREATMENT_TIME);

        // Get all the todoList where requiredTreatmentTime equals to UPDATED_REQUIRED_TREATMENT_TIME
        defaultTodoShouldNotBeFound("requiredTreatmentTime.in=" + UPDATED_REQUIRED_TREATMENT_TIME);
    }

    @Test
    @Transactional
    public void getAllTodosByRequiredTreatmentTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where requiredTreatmentTime is not null
        defaultTodoShouldBeFound("requiredTreatmentTime.specified=true");

        // Get all the todoList where requiredTreatmentTime is null
        defaultTodoShouldNotBeFound("requiredTreatmentTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllTodosByRequiredTreatmentTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where requiredTreatmentTime greater than or equals to DEFAULT_REQUIRED_TREATMENT_TIME
        defaultTodoShouldBeFound("requiredTreatmentTime.greaterOrEqualThan=" + DEFAULT_REQUIRED_TREATMENT_TIME);

        // Get all the todoList where requiredTreatmentTime greater than or equals to UPDATED_REQUIRED_TREATMENT_TIME
        defaultTodoShouldNotBeFound("requiredTreatmentTime.greaterOrEqualThan=" + UPDATED_REQUIRED_TREATMENT_TIME);
    }

    @Test
    @Transactional
    public void getAllTodosByRequiredTreatmentTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where requiredTreatmentTime less than or equals to DEFAULT_REQUIRED_TREATMENT_TIME
        defaultTodoShouldNotBeFound("requiredTreatmentTime.lessThan=" + DEFAULT_REQUIRED_TREATMENT_TIME);

        // Get all the todoList where requiredTreatmentTime less than or equals to UPDATED_REQUIRED_TREATMENT_TIME
        defaultTodoShouldBeFound("requiredTreatmentTime.lessThan=" + UPDATED_REQUIRED_TREATMENT_TIME);
    }


    @Test
    @Transactional
    public void getAllTodosByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where note equals to DEFAULT_NOTE
        defaultTodoShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the todoList where note equals to UPDATED_NOTE
        defaultTodoShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTodosByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultTodoShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the todoList where note equals to UPDATED_NOTE
        defaultTodoShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTodosByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        todoRepository.saveAndFlush(todo);

        // Get all the todoList where note is not null
        defaultTodoShouldBeFound("note.specified=true");

        // Get all the todoList where note is null
        defaultTodoShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    public void getAllTodosByPatientIsEqualToSomething() throws Exception {
        // Initialize the database
        Patient patient = PatientResourceIntTest.createEntity(em);
        em.persist(patient);
        em.flush();
        todo.setPatient(patient);
        todoRepository.saveAndFlush(todo);
        Long patientId = patient.getId();

        // Get all the todoList where patient equals to patientId
        defaultTodoShouldBeFound("patientId.equals=" + patientId);

        // Get all the todoList where patient equals to patientId + 1
        defaultTodoShouldNotBeFound("patientId.equals=" + (patientId + 1));
    }


    @Test
    @Transactional
    public void getAllTodosByTreatmentProcedureIsEqualToSomething() throws Exception {
        // Initialize the database
        TreatmentProcedure treatmentProcedure = TreatmentProcedureResourceIntTest.createEntity(em);
        em.persist(treatmentProcedure);
        em.flush();
        todo.addTreatmentProcedure(treatmentProcedure);
        todoRepository.saveAndFlush(todo);
        Long treatmentProcedureId = treatmentProcedure.getId();

        // Get all the todoList where treatmentProcedure equals to treatmentProcedureId
        defaultTodoShouldBeFound("treatmentProcedureId.equals=" + treatmentProcedureId);

        // Get all the todoList where treatmentProcedure equals to treatmentProcedureId + 1
        defaultTodoShouldNotBeFound("treatmentProcedureId.equals=" + (treatmentProcedureId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTodoShouldBeFound(String filter) throws Exception {
        restTodoMockMvc.perform(get("/api/todos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(todo.getId().intValue())))
            .andExpect(jsonPath("$.[*].ststus").value(hasItem(DEFAULT_STSTUS.toString())))
            .andExpect(jsonPath("$.[*].expectedDate").value(hasItem(DEFAULT_EXPECTED_DATE.toString())))
            .andExpect(jsonPath("$.[*].requiredTreatmentTime").value(hasItem(DEFAULT_REQUIRED_TREATMENT_TIME)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));

        // Check, that the count call also returns 1
        restTodoMockMvc.perform(get("/api/todos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTodoShouldNotBeFound(String filter) throws Exception {
        restTodoMockMvc.perform(get("/api/todos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTodoMockMvc.perform(get("/api/todos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTodo() throws Exception {
        // Get the todo
        restTodoMockMvc.perform(get("/api/todos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTodo() throws Exception {
        // Initialize the database
        todoService.save(todo);

        int databaseSizeBeforeUpdate = todoRepository.findAll().size();

        // Update the todo
        Todo updatedTodo = todoRepository.findById(todo.getId()).get();
        // Disconnect from session so that the updates on updatedTodo are not directly saved in db
        em.detach(updatedTodo);
        updatedTodo
            .ststus(UPDATED_STSTUS)
            .expectedDate(UPDATED_EXPECTED_DATE)
            .requiredTreatmentTime(UPDATED_REQUIRED_TREATMENT_TIME)
            .note(UPDATED_NOTE);

        restTodoMockMvc.perform(put("/api/todos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTodo)))
            .andExpect(status().isOk());

        // Validate the Todo in the database
        List<Todo> todoList = todoRepository.findAll();
        assertThat(todoList).hasSize(databaseSizeBeforeUpdate);
        Todo testTodo = todoList.get(todoList.size() - 1);
        assertThat(testTodo.getStstus()).isEqualTo(UPDATED_STSTUS);
        assertThat(testTodo.getExpectedDate()).isEqualTo(UPDATED_EXPECTED_DATE);
        assertThat(testTodo.getRequiredTreatmentTime()).isEqualTo(UPDATED_REQUIRED_TREATMENT_TIME);
        assertThat(testTodo.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingTodo() throws Exception {
        int databaseSizeBeforeUpdate = todoRepository.findAll().size();

        // Create the Todo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTodoMockMvc.perform(put("/api/todos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(todo)))
            .andExpect(status().isBadRequest());

        // Validate the Todo in the database
        List<Todo> todoList = todoRepository.findAll();
        assertThat(todoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTodo() throws Exception {
        // Initialize the database
        todoService.save(todo);

        int databaseSizeBeforeDelete = todoRepository.findAll().size();

        // Get the todo
        restTodoMockMvc.perform(delete("/api/todos/{id}", todo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Todo> todoList = todoRepository.findAll();
        assertThat(todoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Todo.class);
        Todo todo1 = new Todo();
        todo1.setId(1L);
        Todo todo2 = new Todo();
        todo2.setId(todo1.getId());
        assertThat(todo1).isEqualTo(todo2);
        todo2.setId(2L);
        assertThat(todo1).isNotEqualTo(todo2);
        todo1.setId(null);
        assertThat(todo1).isNotEqualTo(todo2);
    }
}
