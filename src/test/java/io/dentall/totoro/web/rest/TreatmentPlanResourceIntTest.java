package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.TreatmentPlan;
import io.dentall.totoro.domain.TreatmentTask;
import io.dentall.totoro.domain.Treatment;
import io.dentall.totoro.repository.TreatmentPlanRepository;
import io.dentall.totoro.service.TreatmentPlanService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.TreatmentPlanQueryService;

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
 * Test class for the TreatmentPlanResource REST controller.
 *
 * @see TreatmentPlanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class TreatmentPlanResourceIntTest {

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private TreatmentPlanRepository treatmentPlanRepository;

    @Autowired
    private TreatmentPlanService treatmentPlanService;

    @Autowired
    private TreatmentPlanQueryService treatmentPlanQueryService;

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

    private MockMvc restTreatmentPlanMockMvc;

    private TreatmentPlan treatmentPlan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TreatmentPlanResource treatmentPlanResource = new TreatmentPlanResource(treatmentPlanService, treatmentPlanQueryService);
        this.restTreatmentPlanMockMvc = MockMvcBuilders.standaloneSetup(treatmentPlanResource)
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
    public static TreatmentPlan createEntity(EntityManager em) {
        TreatmentPlan treatmentPlan = new TreatmentPlan()
            .activated(DEFAULT_ACTIVATED)
            .name(DEFAULT_NAME);
        return treatmentPlan;
    }

    @Before
    public void initTest() {
        treatmentPlan = createEntity(em);
    }

    @Test
    @Transactional
    public void createTreatmentPlan() throws Exception {
        int databaseSizeBeforeCreate = treatmentPlanRepository.findAll().size();

        // Create the TreatmentPlan
        restTreatmentPlanMockMvc.perform(post("/api/treatment-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentPlan)))
            .andExpect(status().isCreated());

        // Validate the TreatmentPlan in the database
        List<TreatmentPlan> treatmentPlanList = treatmentPlanRepository.findAll();
        assertThat(treatmentPlanList).hasSize(databaseSizeBeforeCreate + 1);
        TreatmentPlan testTreatmentPlan = treatmentPlanList.get(treatmentPlanList.size() - 1);
        assertThat(testTreatmentPlan.isActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testTreatmentPlan.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createTreatmentPlanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = treatmentPlanRepository.findAll().size();

        // Create the TreatmentPlan with an existing ID
        treatmentPlan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTreatmentPlanMockMvc.perform(post("/api/treatment-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentPlan)))
            .andExpect(status().isBadRequest());

        // Validate the TreatmentPlan in the database
        List<TreatmentPlan> treatmentPlanList = treatmentPlanRepository.findAll();
        assertThat(treatmentPlanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkActivatedIsRequired() throws Exception {
        int databaseSizeBeforeTest = treatmentPlanRepository.findAll().size();
        // set the field null
        treatmentPlan.setActivated(null);

        // Create the TreatmentPlan, which fails.

        restTreatmentPlanMockMvc.perform(post("/api/treatment-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentPlan)))
            .andExpect(status().isBadRequest());

        List<TreatmentPlan> treatmentPlanList = treatmentPlanRepository.findAll();
        assertThat(treatmentPlanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTreatmentPlans() throws Exception {
        // Initialize the database
        treatmentPlanRepository.saveAndFlush(treatmentPlan);

        // Get all the treatmentPlanList
        restTreatmentPlanMockMvc.perform(get("/api/treatment-plans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatmentPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getTreatmentPlan() throws Exception {
        // Initialize the database
        treatmentPlanRepository.saveAndFlush(treatmentPlan);

        // Get the treatmentPlan
        restTreatmentPlanMockMvc.perform(get("/api/treatment-plans/{id}", treatmentPlan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(treatmentPlan.getId().intValue()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getAllTreatmentPlansByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentPlanRepository.saveAndFlush(treatmentPlan);

        // Get all the treatmentPlanList where activated equals to DEFAULT_ACTIVATED
        defaultTreatmentPlanShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the treatmentPlanList where activated equals to UPDATED_ACTIVATED
        defaultTreatmentPlanShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllTreatmentPlansByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentPlanRepository.saveAndFlush(treatmentPlan);

        // Get all the treatmentPlanList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultTreatmentPlanShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the treatmentPlanList where activated equals to UPDATED_ACTIVATED
        defaultTreatmentPlanShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    public void getAllTreatmentPlansByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentPlanRepository.saveAndFlush(treatmentPlan);

        // Get all the treatmentPlanList where activated is not null
        defaultTreatmentPlanShouldBeFound("activated.specified=true");

        // Get all the treatmentPlanList where activated is null
        defaultTreatmentPlanShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentPlansByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentPlanRepository.saveAndFlush(treatmentPlan);

        // Get all the treatmentPlanList where name equals to DEFAULT_NAME
        defaultTreatmentPlanShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the treatmentPlanList where name equals to UPDATED_NAME
        defaultTreatmentPlanShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTreatmentPlansByNameIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentPlanRepository.saveAndFlush(treatmentPlan);

        // Get all the treatmentPlanList where name in DEFAULT_NAME or UPDATED_NAME
        defaultTreatmentPlanShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the treatmentPlanList where name equals to UPDATED_NAME
        defaultTreatmentPlanShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllTreatmentPlansByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentPlanRepository.saveAndFlush(treatmentPlan);

        // Get all the treatmentPlanList where name is not null
        defaultTreatmentPlanShouldBeFound("name.specified=true");

        // Get all the treatmentPlanList where name is null
        defaultTreatmentPlanShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentPlansByTreatmentTaskIsEqualToSomething() throws Exception {
        // Initialize the database
        TreatmentTask treatmentTask = TreatmentTaskResourceIntTest.createEntity(em);
        em.persist(treatmentTask);
        em.flush();
        treatmentPlan.addTreatmentTask(treatmentTask);
        treatmentPlanRepository.saveAndFlush(treatmentPlan);
        Long treatmentTaskId = treatmentTask.getId();

        // Get all the treatmentPlanList where treatmentTask equals to treatmentTaskId
        defaultTreatmentPlanShouldBeFound("treatmentTaskId.equals=" + treatmentTaskId);

        // Get all the treatmentPlanList where treatmentTask equals to treatmentTaskId + 1
        defaultTreatmentPlanShouldNotBeFound("treatmentTaskId.equals=" + (treatmentTaskId + 1));
    }


    @Test
    @Transactional
    public void getAllTreatmentPlansByTreatmentIsEqualToSomething() throws Exception {
        // Initialize the database
        Treatment treatment = TreatmentResourceIntTest.createEntity(em);
        em.persist(treatment);
        em.flush();
        treatmentPlan.setTreatment(treatment);
        treatmentPlanRepository.saveAndFlush(treatmentPlan);
        Long treatmentId = treatment.getId();

        // Get all the treatmentPlanList where treatment equals to treatmentId
        defaultTreatmentPlanShouldBeFound("treatmentId.equals=" + treatmentId);

        // Get all the treatmentPlanList where treatment equals to treatmentId + 1
        defaultTreatmentPlanShouldNotBeFound("treatmentId.equals=" + (treatmentId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTreatmentPlanShouldBeFound(String filter) throws Exception {
        restTreatmentPlanMockMvc.perform(get("/api/treatment-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatmentPlan.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));

        // Check, that the count call also returns 1
        restTreatmentPlanMockMvc.perform(get("/api/treatment-plans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTreatmentPlanShouldNotBeFound(String filter) throws Exception {
        restTreatmentPlanMockMvc.perform(get("/api/treatment-plans?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTreatmentPlanMockMvc.perform(get("/api/treatment-plans/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTreatmentPlan() throws Exception {
        // Get the treatmentPlan
        restTreatmentPlanMockMvc.perform(get("/api/treatment-plans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreatmentPlan() throws Exception {
        // Initialize the database
        treatmentPlanService.save(treatmentPlan);

        int databaseSizeBeforeUpdate = treatmentPlanRepository.findAll().size();

        // Update the treatmentPlan
        TreatmentPlan updatedTreatmentPlan = treatmentPlanRepository.findById(treatmentPlan.getId()).get();
        // Disconnect from session so that the updates on updatedTreatmentPlan are not directly saved in db
        em.detach(updatedTreatmentPlan);
        updatedTreatmentPlan
            .activated(UPDATED_ACTIVATED)
            .name(UPDATED_NAME);

        restTreatmentPlanMockMvc.perform(put("/api/treatment-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTreatmentPlan)))
            .andExpect(status().isOk());

        // Validate the TreatmentPlan in the database
        List<TreatmentPlan> treatmentPlanList = treatmentPlanRepository.findAll();
        assertThat(treatmentPlanList).hasSize(databaseSizeBeforeUpdate);
        TreatmentPlan testTreatmentPlan = treatmentPlanList.get(treatmentPlanList.size() - 1);
        assertThat(testTreatmentPlan.isActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testTreatmentPlan.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingTreatmentPlan() throws Exception {
        int databaseSizeBeforeUpdate = treatmentPlanRepository.findAll().size();

        // Create the TreatmentPlan

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTreatmentPlanMockMvc.perform(put("/api/treatment-plans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentPlan)))
            .andExpect(status().isBadRequest());

        // Validate the TreatmentPlan in the database
        List<TreatmentPlan> treatmentPlanList = treatmentPlanRepository.findAll();
        assertThat(treatmentPlanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTreatmentPlan() throws Exception {
        // Initialize the database
        treatmentPlanService.save(treatmentPlan);

        int databaseSizeBeforeDelete = treatmentPlanRepository.findAll().size();

        // Get the treatmentPlan
        restTreatmentPlanMockMvc.perform(delete("/api/treatment-plans/{id}", treatmentPlan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TreatmentPlan> treatmentPlanList = treatmentPlanRepository.findAll();
        assertThat(treatmentPlanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TreatmentPlan.class);
        TreatmentPlan treatmentPlan1 = new TreatmentPlan();
        treatmentPlan1.setId(1L);
        TreatmentPlan treatmentPlan2 = new TreatmentPlan();
        treatmentPlan2.setId(treatmentPlan1.getId());
        assertThat(treatmentPlan1).isEqualTo(treatmentPlan2);
        treatmentPlan2.setId(2L);
        assertThat(treatmentPlan1).isNotEqualTo(treatmentPlan2);
        treatmentPlan1.setId(null);
        assertThat(treatmentPlan1).isNotEqualTo(treatmentPlan2);
    }
}
