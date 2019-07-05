package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.TreatmentTask;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.domain.TreatmentPlan;
import io.dentall.totoro.repository.TreatmentTaskRepository;
import io.dentall.totoro.service.TreatmentTaskService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.TreatmentTaskQueryService;

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
import java.util.HashSet;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TreatmentTaskResource REST controller.
 *
 * @see TreatmentTaskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class TreatmentTaskResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private TreatmentTaskRepository treatmentTaskRepository;

    @Autowired
    private TreatmentTaskService treatmentTaskService;

    @Autowired
    private TreatmentTaskQueryService treatmentTaskQueryService;

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

    private MockMvc restTreatmentTaskMockMvc;

    private TreatmentTask treatmentTask;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TreatmentTaskResource treatmentTaskResource = new TreatmentTaskResource(treatmentTaskService, treatmentTaskQueryService);
        this.restTreatmentTaskMockMvc = MockMvcBuilders.standaloneSetup(treatmentTaskResource)
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
    public static TreatmentTask createEntity(EntityManager em) {
        TreatmentTask treatmentTask = new TreatmentTask()
            .name(DEFAULT_NAME)
            .note(DEFAULT_NOTE);
        return treatmentTask;
    }

    @Before
    public void initTest() {
        treatmentTask = createEntity(em);
    }

    @Test
    @Transactional
    public void createTreatmentTask() throws Exception {
        int databaseSizeBeforeCreate = treatmentTaskRepository.findAll().size();

        // Create the TreatmentTask
        restTreatmentTaskMockMvc.perform(post("/api/treatment-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentTask)))
            .andExpect(status().isCreated());

        // Validate the TreatmentTask in the database
        List<TreatmentTask> treatmentTaskList = treatmentTaskRepository.findAll();
        assertThat(treatmentTaskList).hasSize(databaseSizeBeforeCreate + 1);
        TreatmentTask testTreatmentTask = treatmentTaskList.get(treatmentTaskList.size() - 1);
        assertThat(testTreatmentTask.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTreatmentTask.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testTreatmentTask.getTreatmentProcedures().size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void createTreatmentTaskWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = treatmentTaskRepository.findAll().size();

        // Create the TreatmentTask with an existing ID
        treatmentTask.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTreatmentTaskMockMvc.perform(post("/api/treatment-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentTask)))
            .andExpect(status().isBadRequest());

        // Validate the TreatmentTask in the database
        List<TreatmentTask> treatmentTaskList = treatmentTaskRepository.findAll();
        assertThat(treatmentTaskList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTreatmentTasks() throws Exception {
        // Initialize the database
        treatmentTaskRepository.saveAndFlush(treatmentTask);

        // Get all the treatmentTaskList
        restTreatmentTaskMockMvc.perform(get("/api/treatment-tasks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatmentTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
    }
    
    @Test
    @Transactional
    public void getTreatmentTask() throws Exception {
        // Initialize the database
        treatmentTaskRepository.saveAndFlush(treatmentTask);

        // Get the treatmentTask
        restTreatmentTaskMockMvc.perform(get("/api/treatment-tasks/{id}", treatmentTask.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(treatmentTask.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
    }

    @Test
    @Transactional
    public void getAllTreatmentTasksByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentTaskRepository.saveAndFlush(treatmentTask);

        // Get all the treatmentTaskList where name equals to DEFAULT_NAME
        defaultTreatmentTaskShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the treatmentTaskList where name equals to UPDATED_NAME
        defaultTreatmentTaskShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTreatmentTasksByNameIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentTaskRepository.saveAndFlush(treatmentTask);

        // Get all the treatmentTaskList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTreatmentTaskShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the treatmentTaskList where name equals to UPDATED_NAME
        defaultTreatmentTaskShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTreatmentTasksByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentTaskRepository.saveAndFlush(treatmentTask);

        // Get all the treatmentTaskList where name is not null
        defaultTreatmentTaskShouldBeFound("name.specified=true");

        // Get all the treatmentTaskList where name is null
        defaultTreatmentTaskShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentTasksByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentTaskRepository.saveAndFlush(treatmentTask);

        // Get all the treatmentTaskList where note equals to DEFAULT_NOTE
        defaultTreatmentTaskShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the treatmentTaskList where note equals to UPDATED_NOTE
        defaultTreatmentTaskShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTreatmentTasksByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentTaskRepository.saveAndFlush(treatmentTask);

        // Get all the treatmentTaskList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultTreatmentTaskShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the treatmentTaskList where note equals to UPDATED_NOTE
        defaultTreatmentTaskShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTreatmentTasksByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentTaskRepository.saveAndFlush(treatmentTask);

        // Get all the treatmentTaskList where note is not null
        defaultTreatmentTaskShouldBeFound("note.specified=true");

        // Get all the treatmentTaskList where note is null
        defaultTreatmentTaskShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentTasksByTreatmentProcedureIsEqualToSomething() throws Exception {
        // Initialize the database
        TreatmentProcedure treatmentProcedure = TreatmentProcedureResourceIntTest.createEntity(em);
        em.persist(treatmentProcedure);
        em.flush();

        if (treatmentTask.getTreatmentProcedures() == null) {
            treatmentTask.setTreatmentProcedures(new HashSet<>());
        }

        treatmentTask.addTreatmentProcedure(treatmentProcedure);
        treatmentTaskRepository.saveAndFlush(treatmentTask);
        Long treatmentProcedureId = treatmentProcedure.getId();

        // Get all the treatmentTaskList where treatmentProcedure equals to treatmentProcedureId
        defaultTreatmentTaskShouldBeFound("treatmentProcedureId.equals=" + treatmentProcedureId);

        // Get all the treatmentTaskList where treatmentProcedure equals to treatmentProcedureId + 1
        defaultTreatmentTaskShouldNotBeFound("treatmentProcedureId.equals=" + (treatmentProcedureId + 1));
    }


    @Test
    @Transactional
    public void getAllTreatmentTasksByTreatmentPlanIsEqualToSomething() throws Exception {
        // Initialize the database
        TreatmentPlan treatmentPlan = TreatmentPlanResourceIntTest.createEntity(em);
        em.persist(treatmentPlan);
        em.flush();
        treatmentTask.setTreatmentPlan(treatmentPlan);
        treatmentTaskRepository.saveAndFlush(treatmentTask);
        Long treatmentPlanId = treatmentPlan.getId();

        // Get all the treatmentTaskList where treatmentPlan equals to treatmentPlanId
        defaultTreatmentTaskShouldBeFound("treatmentPlanId.equals=" + treatmentPlanId);

        // Get all the treatmentTaskList where treatmentPlan equals to treatmentPlanId + 1
        defaultTreatmentTaskShouldNotBeFound("treatmentPlanId.equals=" + (treatmentPlanId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTreatmentTaskShouldBeFound(String filter) throws Exception {
        restTreatmentTaskMockMvc.perform(get("/api/treatment-tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatmentTask.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));

        // Check, that the count call also returns 1
        restTreatmentTaskMockMvc.perform(get("/api/treatment-tasks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTreatmentTaskShouldNotBeFound(String filter) throws Exception {
        restTreatmentTaskMockMvc.perform(get("/api/treatment-tasks?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTreatmentTaskMockMvc.perform(get("/api/treatment-tasks/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTreatmentTask() throws Exception {
        // Get the treatmentTask
        restTreatmentTaskMockMvc.perform(get("/api/treatment-tasks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreatmentTask() throws Exception {
        // Initialize the database
        treatmentTaskService.save(treatmentTask);

        int databaseSizeBeforeUpdate = treatmentTaskRepository.findAll().size();

        // Update the treatmentTask
        TreatmentTask updatedTreatmentTask = treatmentTaskRepository.findById(treatmentTask.getId()).get();
        // Disconnect from session so that the updates on updatedTreatmentTask are not directly saved in db
        em.detach(updatedTreatmentTask);
        updatedTreatmentTask
            .name(UPDATED_NAME)
            .note(UPDATED_NOTE)
            .treatmentProcedures(null);

        restTreatmentTaskMockMvc.perform(put("/api/treatment-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTreatmentTask)))
            .andExpect(status().isOk());

        // Validate the TreatmentTask in the database
        List<TreatmentTask> treatmentTaskList = treatmentTaskRepository.findAll();
        assertThat(treatmentTaskList).hasSize(databaseSizeBeforeUpdate);
        TreatmentTask testTreatmentTask = treatmentTaskList.get(treatmentTaskList.size() - 1);
        assertThat(testTreatmentTask.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTreatmentTask.getNote()).isEqualTo(UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void updateNonExistingTreatmentTask() throws Exception {
        int databaseSizeBeforeUpdate = treatmentTaskRepository.findAll().size();

        // Create the TreatmentTask

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTreatmentTaskMockMvc.perform(put("/api/treatment-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentTask)))
            .andExpect(status().isBadRequest());

        // Validate the TreatmentTask in the database
        List<TreatmentTask> treatmentTaskList = treatmentTaskRepository.findAll();
        assertThat(treatmentTaskList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTreatmentTask() throws Exception {
        // Initialize the database
        treatmentTaskService.save(treatmentTask);

        int databaseSizeBeforeDelete = treatmentTaskRepository.findAll().size();

        // Get the treatmentTask
        restTreatmentTaskMockMvc.perform(delete("/api/treatment-tasks/{id}", treatmentTask.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TreatmentTask> treatmentTaskList = treatmentTaskRepository.findAll();
        assertThat(treatmentTaskList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TreatmentTask.class);
        TreatmentTask treatmentTask1 = new TreatmentTask();
        treatmentTask1.setId(1L);
        TreatmentTask treatmentTask2 = new TreatmentTask();
        treatmentTask2.setId(treatmentTask1.getId());
        assertThat(treatmentTask1).isEqualTo(treatmentTask2);
        treatmentTask2.setId(2L);
        assertThat(treatmentTask1).isNotEqualTo(treatmentTask2);
        treatmentTask1.setId(null);
        assertThat(treatmentTask1).isNotEqualTo(treatmentTask2);
    }
}
