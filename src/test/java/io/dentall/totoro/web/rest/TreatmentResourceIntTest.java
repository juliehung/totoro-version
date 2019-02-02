package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.Treatment;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.TreatmentRepository;
import io.dentall.totoro.service.TreatmentPlanService;
import io.dentall.totoro.service.TreatmentService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.dto.TreatmentCriteria;
import io.dentall.totoro.service.TreatmentQueryService;

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
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TreatmentResource REST controller.
 *
 * @see TreatmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class TreatmentResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CHIEF_COMPLAINT = "AAAAAAAAAA";
    private static final String UPDATED_CHIEF_COMPLAINT = "BBBBBBBBBB";

    private static final String DEFAULT_GOAL = "AAAAAAAAAA";
    private static final String UPDATED_GOAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_FINDING = "AAAAAAAAAA";
    private static final String UPDATED_FINDING = "BBBBBBBBBB";

    @Autowired
    private TreatmentRepository treatmentRepository;

    @Autowired
    private TreatmentService treatmentService;

    @Autowired
    private TreatmentQueryService treatmentQueryService;

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
    private TreatmentPlanService treatmentPlanService;

    private MockMvc restTreatmentMockMvc;

    private Treatment treatment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TreatmentResource treatmentResource = new TreatmentResource(treatmentService, treatmentQueryService, treatmentPlanService);
        this.restTreatmentMockMvc = MockMvcBuilders.standaloneSetup(treatmentResource)
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
    public static Treatment createEntity(EntityManager em) {
        Treatment treatment = new Treatment()
            .name(DEFAULT_NAME)
            .chiefComplaint(DEFAULT_CHIEF_COMPLAINT)
            .goal(DEFAULT_GOAL)
            .note(DEFAULT_NOTE)
            .finding(DEFAULT_FINDING);
        return treatment;
    }

    @Before
    public void initTest() {
        treatment = createEntity(em);
    }

    @Test
    @Transactional
    public void createTreatment() throws Exception {
        int databaseSizeBeforeCreate = treatmentRepository.findAll().size();

        // Create the Treatment
        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatment)))
            .andExpect(status().isCreated());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeCreate + 1);
        Treatment testTreatment = treatmentList.get(treatmentList.size() - 1);
        assertThat(testTreatment.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTreatment.getChiefComplaint()).isEqualTo(DEFAULT_CHIEF_COMPLAINT);
        assertThat(testTreatment.getGoal()).isEqualTo(DEFAULT_GOAL);
        assertThat(testTreatment.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testTreatment.getFinding()).isEqualTo(DEFAULT_FINDING);
    }

    @Test
    @Transactional
    public void createTreatmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = treatmentRepository.findAll().size();

        // Create the Treatment with an existing ID
        treatment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatment)))
            .andExpect(status().isBadRequest());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = treatmentRepository.findAll().size();
        // set the field null
        treatment.setName(null);

        // Create the Treatment, which fails.

        restTreatmentMockMvc.perform(post("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatment)))
            .andExpect(status().isBadRequest());

        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTreatments() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList
        restTreatmentMockMvc.perform(get("/api/treatments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].chiefComplaint").value(hasItem(DEFAULT_CHIEF_COMPLAINT.toString())))
            .andExpect(jsonPath("$.[*].goal").value(hasItem(DEFAULT_GOAL.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].finding").value(hasItem(DEFAULT_FINDING.toString())));
    }
    
    @Test
    @Transactional
    public void getTreatment() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", treatment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(treatment.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.chiefComplaint").value(DEFAULT_CHIEF_COMPLAINT.toString()))
            .andExpect(jsonPath("$.goal").value(DEFAULT_GOAL.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.finding").value(DEFAULT_FINDING.toString()));
    }

    @Test
    @Transactional
    public void getAllTreatmentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where name equals to DEFAULT_NAME
        defaultTreatmentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the treatmentList where name equals to UPDATED_NAME
        defaultTreatmentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTreatmentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTreatmentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the treatmentList where name equals to UPDATED_NAME
        defaultTreatmentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTreatmentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where name is not null
        defaultTreatmentShouldBeFound("name.specified=true");

        // Get all the treatmentList where name is null
        defaultTreatmentShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentsByChiefComplaintIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where chiefComplaint equals to DEFAULT_CHIEF_COMPLAINT
        defaultTreatmentShouldBeFound("chiefComplaint.equals=" + DEFAULT_CHIEF_COMPLAINT);

        // Get all the treatmentList where chiefComplaint equals to UPDATED_CHIEF_COMPLAINT
        defaultTreatmentShouldNotBeFound("chiefComplaint.equals=" + UPDATED_CHIEF_COMPLAINT);
    }

    @Test
    @Transactional
    public void getAllTreatmentsByChiefComplaintIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where chiefComplaint in DEFAULT_CHIEF_COMPLAINT or UPDATED_CHIEF_COMPLAINT
        defaultTreatmentShouldBeFound("chiefComplaint.in=" + DEFAULT_CHIEF_COMPLAINT + "," + UPDATED_CHIEF_COMPLAINT);

        // Get all the treatmentList where chiefComplaint equals to UPDATED_CHIEF_COMPLAINT
        defaultTreatmentShouldNotBeFound("chiefComplaint.in=" + UPDATED_CHIEF_COMPLAINT);
    }

    @Test
    @Transactional
    public void getAllTreatmentsByChiefComplaintIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where chiefComplaint is not null
        defaultTreatmentShouldBeFound("chiefComplaint.specified=true");

        // Get all the treatmentList where chiefComplaint is null
        defaultTreatmentShouldNotBeFound("chiefComplaint.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentsByGoalIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where goal equals to DEFAULT_GOAL
        defaultTreatmentShouldBeFound("goal.equals=" + DEFAULT_GOAL);

        // Get all the treatmentList where goal equals to UPDATED_GOAL
        defaultTreatmentShouldNotBeFound("goal.equals=" + UPDATED_GOAL);
    }

    @Test
    @Transactional
    public void getAllTreatmentsByGoalIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where goal in DEFAULT_GOAL or UPDATED_GOAL
        defaultTreatmentShouldBeFound("goal.in=" + DEFAULT_GOAL + "," + UPDATED_GOAL);

        // Get all the treatmentList where goal equals to UPDATED_GOAL
        defaultTreatmentShouldNotBeFound("goal.in=" + UPDATED_GOAL);
    }

    @Test
    @Transactional
    public void getAllTreatmentsByGoalIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where goal is not null
        defaultTreatmentShouldBeFound("goal.specified=true");

        // Get all the treatmentList where goal is null
        defaultTreatmentShouldNotBeFound("goal.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where note equals to DEFAULT_NOTE
        defaultTreatmentShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the treatmentList where note equals to UPDATED_NOTE
        defaultTreatmentShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTreatmentsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultTreatmentShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the treatmentList where note equals to UPDATED_NOTE
        defaultTreatmentShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTreatmentsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where note is not null
        defaultTreatmentShouldBeFound("note.specified=true");

        // Get all the treatmentList where note is null
        defaultTreatmentShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentsByFindingIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where finding equals to DEFAULT_FINDING
        defaultTreatmentShouldBeFound("finding.equals=" + DEFAULT_FINDING);

        // Get all the treatmentList where finding equals to UPDATED_FINDING
        defaultTreatmentShouldNotBeFound("finding.equals=" + UPDATED_FINDING);
    }

    @Test
    @Transactional
    public void getAllTreatmentsByFindingIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where finding in DEFAULT_FINDING or UPDATED_FINDING
        defaultTreatmentShouldBeFound("finding.in=" + DEFAULT_FINDING + "," + UPDATED_FINDING);

        // Get all the treatmentList where finding equals to UPDATED_FINDING
        defaultTreatmentShouldNotBeFound("finding.in=" + UPDATED_FINDING);
    }

    @Test
    @Transactional
    public void getAllTreatmentsByFindingIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentRepository.saveAndFlush(treatment);

        // Get all the treatmentList where finding is not null
        defaultTreatmentShouldBeFound("finding.specified=true");

        // Get all the treatmentList where finding is null
        defaultTreatmentShouldNotBeFound("finding.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentsByPatientIsEqualToSomething() throws Exception {
        // Initialize the database
        Patient patient = PatientResourceIntTest.createEntity(em);
        em.persist(patient);
        em.flush();
        treatment.setPatient(patient);
        treatmentRepository.saveAndFlush(treatment);
        Long patientId = patient.getId();

        // Get all the treatmentList where patient equals to patientId
        defaultTreatmentShouldBeFound("patientId.equals=" + patientId);

        // Get all the treatmentList where patient equals to patientId + 1
        defaultTreatmentShouldNotBeFound("patientId.equals=" + (patientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTreatmentShouldBeFound(String filter) throws Exception {
        restTreatmentMockMvc.perform(get("/api/treatments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatment.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].chiefComplaint").value(hasItem(DEFAULT_CHIEF_COMPLAINT.toString())))
            .andExpect(jsonPath("$.[*].goal").value(hasItem(DEFAULT_GOAL.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].finding").value(hasItem(DEFAULT_FINDING.toString())));

        // Check, that the count call also returns 1
        restTreatmentMockMvc.perform(get("/api/treatments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTreatmentShouldNotBeFound(String filter) throws Exception {
        restTreatmentMockMvc.perform(get("/api/treatments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTreatmentMockMvc.perform(get("/api/treatments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTreatment() throws Exception {
        // Get the treatment
        restTreatmentMockMvc.perform(get("/api/treatments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreatment() throws Exception {
        // Initialize the database
        treatmentService.save(treatment);

        int databaseSizeBeforeUpdate = treatmentRepository.findAll().size();

        // Update the treatment
        Treatment updatedTreatment = treatmentRepository.findById(treatment.getId()).get();
        // Disconnect from session so that the updates on updatedTreatment are not directly saved in db
        em.detach(updatedTreatment);
        updatedTreatment
            .name(UPDATED_NAME)
            .chiefComplaint(UPDATED_CHIEF_COMPLAINT)
            .goal(UPDATED_GOAL)
            .note(UPDATED_NOTE)
            .finding(UPDATED_FINDING);

        restTreatmentMockMvc.perform(put("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTreatment)))
            .andExpect(status().isOk());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeUpdate);
        Treatment testTreatment = treatmentList.get(treatmentList.size() - 1);
        assertThat(testTreatment.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTreatment.getChiefComplaint()).isEqualTo(UPDATED_CHIEF_COMPLAINT);
        assertThat(testTreatment.getGoal()).isEqualTo(UPDATED_GOAL);
        assertThat(testTreatment.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testTreatment.getFinding()).isEqualTo(UPDATED_FINDING);
    }

    @Test
    @Transactional
    public void updateNonExistingTreatment() throws Exception {
        int databaseSizeBeforeUpdate = treatmentRepository.findAll().size();

        // Create the Treatment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTreatmentMockMvc.perform(put("/api/treatments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatment)))
            .andExpect(status().isBadRequest());

        // Validate the Treatment in the database
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTreatment() throws Exception {
        // Initialize the database
        treatmentService.save(treatment);

        int databaseSizeBeforeDelete = treatmentRepository.findAll().size();

        // Get the treatment
        restTreatmentMockMvc.perform(delete("/api/treatments/{id}", treatment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Treatment> treatmentList = treatmentRepository.findAll();
        assertThat(treatmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Treatment.class);
        Treatment treatment1 = new Treatment();
        treatment1.setId(1L);
        Treatment treatment2 = new Treatment();
        treatment2.setId(treatment1.getId());
        assertThat(treatment1).isEqualTo(treatment2);
        treatment2.setId(2L);
        assertThat(treatment1).isNotEqualTo(treatment2);
        treatment1.setId(null);
        assertThat(treatment1).isNotEqualTo(treatment2);
    }
}
