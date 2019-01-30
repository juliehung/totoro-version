package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.Tooth;
import io.dentall.totoro.domain.TreatmentTask;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.repository.ToothRepository;
import io.dentall.totoro.service.ToothService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.ToothQueryService;

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
 * Test class for the ToothResource REST controller.
 *
 * @see ToothResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class ToothResourceIntTest {

    private static final String DEFAULT_POSITION = "AAAAAAAAAA";
    private static final String UPDATED_POSITION = "BBBBBBBBBB";

    private static final String DEFAULT_BEFORE = "AAAAAAAAAA";
    private static final String UPDATED_BEFORE = "BBBBBBBBBB";

    private static final String DEFAULT_PLANNED = "AAAAAAAAAA";
    private static final String UPDATED_PLANNED = "BBBBBBBBBB";

    private static final String DEFAULT_AFTER = "AAAAAAAAAA";
    private static final String UPDATED_AFTER = "BBBBBBBBBB";

    @Autowired
    private ToothRepository toothRepository;

    @Autowired
    private ToothService toothService;

    @Autowired
    private ToothQueryService toothQueryService;

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

    private MockMvc restToothMockMvc;

    private Tooth tooth;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ToothResource toothResource = new ToothResource(toothService, toothQueryService);
        this.restToothMockMvc = MockMvcBuilders.standaloneSetup(toothResource)
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
    public static Tooth createEntity(EntityManager em) {
        Tooth tooth = new Tooth()
            .position(DEFAULT_POSITION)
            .before(DEFAULT_BEFORE)
            .planned(DEFAULT_PLANNED)
            .after(DEFAULT_AFTER);
        return tooth;
    }

    @Before
    public void initTest() {
        tooth = createEntity(em);
    }

    @Test
    @Transactional
    public void createTooth() throws Exception {
        int databaseSizeBeforeCreate = toothRepository.findAll().size();

        // Create the Tooth
        restToothMockMvc.perform(post("/api/teeth")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tooth)))
            .andExpect(status().isCreated());

        // Validate the Tooth in the database
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeCreate + 1);
        Tooth testTooth = toothList.get(toothList.size() - 1);
        assertThat(testTooth.getPosition()).isEqualTo(DEFAULT_POSITION);
        assertThat(testTooth.getBefore()).isEqualTo(DEFAULT_BEFORE);
        assertThat(testTooth.getPlanned()).isEqualTo(DEFAULT_PLANNED);
        assertThat(testTooth.getAfter()).isEqualTo(DEFAULT_AFTER);
    }

    @Test
    @Transactional
    public void createToothWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = toothRepository.findAll().size();

        // Create the Tooth with an existing ID
        tooth.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restToothMockMvc.perform(post("/api/teeth")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tooth)))
            .andExpect(status().isBadRequest());

        // Validate the Tooth in the database
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPositionIsRequired() throws Exception {
        int databaseSizeBeforeTest = toothRepository.findAll().size();
        // set the field null
        tooth.setPosition(null);

        // Create the Tooth, which fails.

        restToothMockMvc.perform(post("/api/teeth")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tooth)))
            .andExpect(status().isBadRequest());

        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTeeth() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get all the toothList
        restToothMockMvc.perform(get("/api/teeth?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tooth.getId().intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].before").value(hasItem(DEFAULT_BEFORE.toString())))
            .andExpect(jsonPath("$.[*].planned").value(hasItem(DEFAULT_PLANNED.toString())))
            .andExpect(jsonPath("$.[*].after").value(hasItem(DEFAULT_AFTER.toString())));
    }
    
    @Test
    @Transactional
    public void getTooth() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get the tooth
        restToothMockMvc.perform(get("/api/teeth/{id}", tooth.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tooth.getId().intValue()))
            .andExpect(jsonPath("$.position").value(DEFAULT_POSITION.toString()))
            .andExpect(jsonPath("$.before").value(DEFAULT_BEFORE.toString()))
            .andExpect(jsonPath("$.planned").value(DEFAULT_PLANNED.toString()))
            .andExpect(jsonPath("$.after").value(DEFAULT_AFTER.toString()));
    }

    @Test
    @Transactional
    public void getAllTeethByPositionIsEqualToSomething() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get all the toothList where position equals to DEFAULT_POSITION
        defaultToothShouldBeFound("position.equals=" + DEFAULT_POSITION);

        // Get all the toothList where position equals to UPDATED_POSITION
        defaultToothShouldNotBeFound("position.equals=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllTeethByPositionIsInShouldWork() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get all the toothList where position in DEFAULT_POSITION or UPDATED_POSITION
        defaultToothShouldBeFound("position.in=" + DEFAULT_POSITION + "," + UPDATED_POSITION);

        // Get all the toothList where position equals to UPDATED_POSITION
        defaultToothShouldNotBeFound("position.in=" + UPDATED_POSITION);
    }

    @Test
    @Transactional
    public void getAllTeethByPositionIsNullOrNotNull() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get all the toothList where position is not null
        defaultToothShouldBeFound("position.specified=true");

        // Get all the toothList where position is null
        defaultToothShouldNotBeFound("position.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeethByBeforeIsEqualToSomething() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get all the toothList where before equals to DEFAULT_BEFORE
        defaultToothShouldBeFound("before.equals=" + DEFAULT_BEFORE);

        // Get all the toothList where before equals to UPDATED_BEFORE
        defaultToothShouldNotBeFound("before.equals=" + UPDATED_BEFORE);
    }

    @Test
    @Transactional
    public void getAllTeethByBeforeIsInShouldWork() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get all the toothList where before in DEFAULT_BEFORE or UPDATED_BEFORE
        defaultToothShouldBeFound("before.in=" + DEFAULT_BEFORE + "," + UPDATED_BEFORE);

        // Get all the toothList where before equals to UPDATED_BEFORE
        defaultToothShouldNotBeFound("before.in=" + UPDATED_BEFORE);
    }

    @Test
    @Transactional
    public void getAllTeethByBeforeIsNullOrNotNull() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get all the toothList where before is not null
        defaultToothShouldBeFound("before.specified=true");

        // Get all the toothList where before is null
        defaultToothShouldNotBeFound("before.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeethByPlannedIsEqualToSomething() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get all the toothList where planned equals to DEFAULT_PLANNED
        defaultToothShouldBeFound("planned.equals=" + DEFAULT_PLANNED);

        // Get all the toothList where planned equals to UPDATED_PLANNED
        defaultToothShouldNotBeFound("planned.equals=" + UPDATED_PLANNED);
    }

    @Test
    @Transactional
    public void getAllTeethByPlannedIsInShouldWork() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get all the toothList where planned in DEFAULT_PLANNED or UPDATED_PLANNED
        defaultToothShouldBeFound("planned.in=" + DEFAULT_PLANNED + "," + UPDATED_PLANNED);

        // Get all the toothList where planned equals to UPDATED_PLANNED
        defaultToothShouldNotBeFound("planned.in=" + UPDATED_PLANNED);
    }

    @Test
    @Transactional
    public void getAllTeethByPlannedIsNullOrNotNull() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get all the toothList where planned is not null
        defaultToothShouldBeFound("planned.specified=true");

        // Get all the toothList where planned is null
        defaultToothShouldNotBeFound("planned.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeethByAfterIsEqualToSomething() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get all the toothList where after equals to DEFAULT_AFTER
        defaultToothShouldBeFound("after.equals=" + DEFAULT_AFTER);

        // Get all the toothList where after equals to UPDATED_AFTER
        defaultToothShouldNotBeFound("after.equals=" + UPDATED_AFTER);
    }

    @Test
    @Transactional
    public void getAllTeethByAfterIsInShouldWork() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get all the toothList where after in DEFAULT_AFTER or UPDATED_AFTER
        defaultToothShouldBeFound("after.in=" + DEFAULT_AFTER + "," + UPDATED_AFTER);

        // Get all the toothList where after equals to UPDATED_AFTER
        defaultToothShouldNotBeFound("after.in=" + UPDATED_AFTER);
    }

    @Test
    @Transactional
    public void getAllTeethByAfterIsNullOrNotNull() throws Exception {
        // Initialize the database
        toothRepository.saveAndFlush(tooth);

        // Get all the toothList where after is not null
        defaultToothShouldBeFound("after.specified=true");

        // Get all the toothList where after is null
        defaultToothShouldNotBeFound("after.specified=false");
    }

    @Test
    @Transactional
    public void getAllTeethByTreatmentTaskIsEqualToSomething() throws Exception {
        // Initialize the database
        TreatmentTask treatmentTask = TreatmentTaskResourceIntTest.createEntity(em);
        em.persist(treatmentTask);
        em.flush();
        tooth.setTreatmentTask(treatmentTask);
        toothRepository.saveAndFlush(tooth);
        Long treatmentTaskId = treatmentTask.getId();

        // Get all the toothList where treatmentTask equals to treatmentTaskId
        defaultToothShouldBeFound("treatmentTaskId.equals=" + treatmentTaskId);

        // Get all the toothList where treatmentTask equals to treatmentTaskId + 1
        defaultToothShouldNotBeFound("treatmentTaskId.equals=" + (treatmentTaskId + 1));
    }


    @Test
    @Transactional
    public void getAllTeethByTreatmentProcedureIsEqualToSomething() throws Exception {
        // Initialize the database
        TreatmentProcedure treatmentProcedure = TreatmentProcedureResourceIntTest.createEntity(em);
        em.persist(treatmentProcedure);
        em.flush();
        tooth.setTreatmentProcedure(treatmentProcedure);
        toothRepository.saveAndFlush(tooth);
        Long treatmentProcedureId = treatmentProcedure.getId();

        // Get all the toothList where treatmentProcedure equals to treatmentProcedureId
        defaultToothShouldBeFound("treatmentProcedureId.equals=" + treatmentProcedureId);

        // Get all the toothList where treatmentProcedure equals to treatmentProcedureId + 1
        defaultToothShouldNotBeFound("treatmentProcedureId.equals=" + (treatmentProcedureId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultToothShouldBeFound(String filter) throws Exception {
        restToothMockMvc.perform(get("/api/teeth?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tooth.getId().intValue())))
            .andExpect(jsonPath("$.[*].position").value(hasItem(DEFAULT_POSITION.toString())))
            .andExpect(jsonPath("$.[*].before").value(hasItem(DEFAULT_BEFORE.toString())))
            .andExpect(jsonPath("$.[*].planned").value(hasItem(DEFAULT_PLANNED.toString())))
            .andExpect(jsonPath("$.[*].after").value(hasItem(DEFAULT_AFTER.toString())));

        // Check, that the count call also returns 1
        restToothMockMvc.perform(get("/api/teeth/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultToothShouldNotBeFound(String filter) throws Exception {
        restToothMockMvc.perform(get("/api/teeth?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restToothMockMvc.perform(get("/api/teeth/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTooth() throws Exception {
        // Get the tooth
        restToothMockMvc.perform(get("/api/teeth/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTooth() throws Exception {
        // Initialize the database
        toothService.save(tooth);

        int databaseSizeBeforeUpdate = toothRepository.findAll().size();

        // Update the tooth
        Tooth updatedTooth = toothRepository.findById(tooth.getId()).get();
        // Disconnect from session so that the updates on updatedTooth are not directly saved in db
        em.detach(updatedTooth);
        updatedTooth
            .position(UPDATED_POSITION)
            .before(UPDATED_BEFORE)
            .planned(UPDATED_PLANNED)
            .after(UPDATED_AFTER);

        restToothMockMvc.perform(put("/api/teeth")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTooth)))
            .andExpect(status().isOk());

        // Validate the Tooth in the database
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeUpdate);
        Tooth testTooth = toothList.get(toothList.size() - 1);
        assertThat(testTooth.getPosition()).isEqualTo(UPDATED_POSITION);
        assertThat(testTooth.getBefore()).isEqualTo(UPDATED_BEFORE);
        assertThat(testTooth.getPlanned()).isEqualTo(UPDATED_PLANNED);
        assertThat(testTooth.getAfter()).isEqualTo(UPDATED_AFTER);
    }

    @Test
    @Transactional
    public void updateNonExistingTooth() throws Exception {
        int databaseSizeBeforeUpdate = toothRepository.findAll().size();

        // Create the Tooth

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restToothMockMvc.perform(put("/api/teeth")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tooth)))
            .andExpect(status().isBadRequest());

        // Validate the Tooth in the database
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTooth() throws Exception {
        // Initialize the database
        toothService.save(tooth);

        int databaseSizeBeforeDelete = toothRepository.findAll().size();

        // Get the tooth
        restToothMockMvc.perform(delete("/api/teeth/{id}", tooth.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tooth> toothList = toothRepository.findAll();
        assertThat(toothList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tooth.class);
        Tooth tooth1 = new Tooth();
        tooth1.setId(1L);
        Tooth tooth2 = new Tooth();
        tooth2.setId(tooth1.getId());
        assertThat(tooth1).isEqualTo(tooth2);
        tooth2.setId(2L);
        assertThat(tooth1).isNotEqualTo(tooth2);
        tooth1.setId(null);
        assertThat(tooth1).isNotEqualTo(tooth2);
    }
}
