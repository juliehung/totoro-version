package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.TreatmentTask;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.TreatmentTaskRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;

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

import javax.persistence.EntityManager;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.dentall.totoro.domain.enumeration.TreatmentTaskStatus;
/**
 * Test class for the TreatmentTaskResource REST controller.
 *
 * @see TreatmentTaskResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class TreatmentTaskResourceIntTest {

    private static final TreatmentTaskStatus DEFAULT_STATUS = TreatmentTaskStatus.PLANNED;
    private static final TreatmentTaskStatus UPDATED_STATUS = TreatmentTaskStatus.IN_PROGRESS;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_TEETH = "AAAAAAAAAA";
    private static final String UPDATED_TEETH = "BBBBBBBBBB";

    private static final String DEFAULT_SURFACES = "AAAAAAAAAA";
    private static final String UPDATED_SURFACES = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    @Autowired
    private TreatmentTaskRepository treatmentTaskRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    private MockMvc restTreatmentTaskMockMvc;

    private TreatmentTask treatmentTask;

    private ExtendUser extendUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TreatmentTaskResource treatmentTaskResource = new TreatmentTaskResource(treatmentTaskRepository);
        this.restTreatmentTaskMockMvc = MockMvcBuilders.standaloneSetup(treatmentTaskResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TreatmentTask createEntity(EntityManager em) {
        TreatmentTask treatmentTask = new TreatmentTask()
            .status(DEFAULT_STATUS)
            .description(DEFAULT_DESCRIPTION)
            .teeth(DEFAULT_TEETH)
            .surfaces(DEFAULT_SURFACES)
            .note(DEFAULT_NOTE);
        return treatmentTask;
    }

    @Before
    public void initTest() {
        User user = userRepository.save(UserResourceIntTest.createEntity(em));
        extendUser = user.getExtendUser();

        treatmentTask = createEntity(em);
        treatmentTask.setDoctor(extendUser);
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
        assertThat(testTreatmentTask.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTreatmentTask.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTreatmentTask.getTeeth()).isEqualTo(DEFAULT_TEETH);
        assertThat(testTreatmentTask.getSurfaces()).isEqualTo(DEFAULT_SURFACES);
        assertThat(testTreatmentTask.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testTreatmentTask.getDoctor()).isEqualTo(extendUser);
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
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].teeth").value(hasItem(DEFAULT_TEETH.toString())))
            .andExpect(jsonPath("$.[*].surfaces").value(hasItem(DEFAULT_SURFACES.toString())))
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
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.teeth").value(DEFAULT_TEETH.toString()))
            .andExpect(jsonPath("$.surfaces").value(DEFAULT_SURFACES.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
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
        treatmentTaskRepository.saveAndFlush(treatmentTask);

        int databaseSizeBeforeUpdate = treatmentTaskRepository.findAll().size();

        // Update the treatmentTask
        TreatmentTask updatedTreatmentTask = treatmentTaskRepository.findById(treatmentTask.getId()).get();
        // Disconnect from session so that the updates on updatedTreatmentTask are not directly saved in db
        em.detach(updatedTreatmentTask);
        updatedTreatmentTask
            .status(UPDATED_STATUS)
            .description(UPDATED_DESCRIPTION)
            .teeth(UPDATED_TEETH)
            .surfaces(UPDATED_SURFACES)
            .note(UPDATED_NOTE);

        restTreatmentTaskMockMvc.perform(put("/api/treatment-tasks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTreatmentTask)))
            .andExpect(status().isOk());

        // Validate the TreatmentTask in the database
        List<TreatmentTask> treatmentTaskList = treatmentTaskRepository.findAll();
        assertThat(treatmentTaskList).hasSize(databaseSizeBeforeUpdate);
        TreatmentTask testTreatmentTask = treatmentTaskList.get(treatmentTaskList.size() - 1);
        assertThat(testTreatmentTask.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTreatmentTask.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTreatmentTask.getTeeth()).isEqualTo(UPDATED_TEETH);
        assertThat(testTreatmentTask.getSurfaces()).isEqualTo(UPDATED_SURFACES);
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
        treatmentTaskRepository.saveAndFlush(treatmentTask);

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
