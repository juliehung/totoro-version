package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.TreatmentDrug;
import io.dentall.totoro.domain.Prescription;
import io.dentall.totoro.domain.Drug;
import io.dentall.totoro.repository.TreatmentDrugRepository;
import io.dentall.totoro.service.TreatmentDrugService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.dto.TreatmentDrugCriteria;
import io.dentall.totoro.service.TreatmentDrugQueryService;

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
 * Test class for the TreatmentDrugResource REST controller.
 *
 * @see TreatmentDrugResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class TreatmentDrugResourceIntTest {

    private static final Integer DEFAULT_DAY = 1;
    private static final Integer UPDATED_DAY = 2;

    @Autowired
    private TreatmentDrugRepository treatmentDrugRepository;

    @Autowired
    private TreatmentDrugService treatmentDrugService;

    @Autowired
    private TreatmentDrugQueryService treatmentDrugQueryService;

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

    private MockMvc restTreatmentDrugMockMvc;

    private TreatmentDrug treatmentDrug;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TreatmentDrugResource treatmentDrugResource = new TreatmentDrugResource(treatmentDrugService, treatmentDrugQueryService);
        this.restTreatmentDrugMockMvc = MockMvcBuilders.standaloneSetup(treatmentDrugResource)
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
    public static TreatmentDrug createEntity(EntityManager em) {
        TreatmentDrug treatmentDrug = new TreatmentDrug()
            .day(DEFAULT_DAY);
        return treatmentDrug;
    }

    @Before
    public void initTest() {
        treatmentDrug = createEntity(em);
    }

    @Test
    @Transactional
    public void createTreatmentDrug() throws Exception {
        int databaseSizeBeforeCreate = treatmentDrugRepository.findAll().size();

        // Create the TreatmentDrug
        restTreatmentDrugMockMvc.perform(post("/api/treatment-drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDrug)))
            .andExpect(status().isCreated());

        // Validate the TreatmentDrug in the database
        List<TreatmentDrug> treatmentDrugList = treatmentDrugRepository.findAll();
        assertThat(treatmentDrugList).hasSize(databaseSizeBeforeCreate + 1);
        TreatmentDrug testTreatmentDrug = treatmentDrugList.get(treatmentDrugList.size() - 1);
        assertThat(testTreatmentDrug.getDay()).isEqualTo(DEFAULT_DAY);
    }

    @Test
    @Transactional
    public void createTreatmentDrugWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = treatmentDrugRepository.findAll().size();

        // Create the TreatmentDrug with an existing ID
        treatmentDrug.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTreatmentDrugMockMvc.perform(post("/api/treatment-drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDrug)))
            .andExpect(status().isBadRequest());

        // Validate the TreatmentDrug in the database
        List<TreatmentDrug> treatmentDrugList = treatmentDrugRepository.findAll();
        assertThat(treatmentDrugList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTreatmentDrugs() throws Exception {
        // Initialize the database
        treatmentDrugRepository.saveAndFlush(treatmentDrug);

        // Get all the treatmentDrugList
        restTreatmentDrugMockMvc.perform(get("/api/treatment-drugs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatmentDrug.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)));
    }
    
    @Test
    @Transactional
    public void getTreatmentDrug() throws Exception {
        // Initialize the database
        treatmentDrugRepository.saveAndFlush(treatmentDrug);

        // Get the treatmentDrug
        restTreatmentDrugMockMvc.perform(get("/api/treatment-drugs/{id}", treatmentDrug.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(treatmentDrug.getId().intValue()))
            .andExpect(jsonPath("$.day").value(DEFAULT_DAY));
    }

    @Test
    @Transactional
    public void getAllTreatmentDrugsByDayIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentDrugRepository.saveAndFlush(treatmentDrug);

        // Get all the treatmentDrugList where day equals to DEFAULT_DAY
        defaultTreatmentDrugShouldBeFound("day.equals=" + DEFAULT_DAY);

        // Get all the treatmentDrugList where day equals to UPDATED_DAY
        defaultTreatmentDrugShouldNotBeFound("day.equals=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    public void getAllTreatmentDrugsByDayIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentDrugRepository.saveAndFlush(treatmentDrug);

        // Get all the treatmentDrugList where day in DEFAULT_DAY or UPDATED_DAY
        defaultTreatmentDrugShouldBeFound("day.in=" + DEFAULT_DAY + "," + UPDATED_DAY);

        // Get all the treatmentDrugList where day equals to UPDATED_DAY
        defaultTreatmentDrugShouldNotBeFound("day.in=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    public void getAllTreatmentDrugsByDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentDrugRepository.saveAndFlush(treatmentDrug);

        // Get all the treatmentDrugList where day is not null
        defaultTreatmentDrugShouldBeFound("day.specified=true");

        // Get all the treatmentDrugList where day is null
        defaultTreatmentDrugShouldNotBeFound("day.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentDrugsByDayIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        treatmentDrugRepository.saveAndFlush(treatmentDrug);

        // Get all the treatmentDrugList where day greater than or equals to DEFAULT_DAY
        defaultTreatmentDrugShouldBeFound("day.greaterOrEqualThan=" + DEFAULT_DAY);

        // Get all the treatmentDrugList where day greater than or equals to UPDATED_DAY
        defaultTreatmentDrugShouldNotBeFound("day.greaterOrEqualThan=" + UPDATED_DAY);
    }

    @Test
    @Transactional
    public void getAllTreatmentDrugsByDayIsLessThanSomething() throws Exception {
        // Initialize the database
        treatmentDrugRepository.saveAndFlush(treatmentDrug);

        // Get all the treatmentDrugList where day less than or equals to DEFAULT_DAY
        defaultTreatmentDrugShouldNotBeFound("day.lessThan=" + DEFAULT_DAY);

        // Get all the treatmentDrugList where day less than or equals to UPDATED_DAY
        defaultTreatmentDrugShouldBeFound("day.lessThan=" + UPDATED_DAY);
    }


    @Test
    @Transactional
    public void getAllTreatmentDrugsByPrescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        Prescription prescription = PrescriptionResourceIntTest.createEntity(em);
        em.persist(prescription);
        em.flush();
        treatmentDrug.setPrescription(prescription);
        treatmentDrugRepository.saveAndFlush(treatmentDrug);
        Long prescriptionId = prescription.getId();

        // Get all the treatmentDrugList where prescription equals to prescriptionId
        defaultTreatmentDrugShouldBeFound("prescriptionId.equals=" + prescriptionId);

        // Get all the treatmentDrugList where prescription equals to prescriptionId + 1
        defaultTreatmentDrugShouldNotBeFound("prescriptionId.equals=" + (prescriptionId + 1));
    }


    @Test
    @Transactional
    public void getAllTreatmentDrugsByDrugIsEqualToSomething() throws Exception {
        // Initialize the database
        Drug drug = DrugResourceIntTest.createEntity(em);
        em.persist(drug);
        em.flush();
        treatmentDrug.setDrug(drug);
        treatmentDrugRepository.saveAndFlush(treatmentDrug);
        Long drugId = drug.getId();

        // Get all the treatmentDrugList where drug equals to drugId
        defaultTreatmentDrugShouldBeFound("drugId.equals=" + drugId);

        // Get all the treatmentDrugList where drug equals to drugId + 1
        defaultTreatmentDrugShouldNotBeFound("drugId.equals=" + (drugId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTreatmentDrugShouldBeFound(String filter) throws Exception {
        restTreatmentDrugMockMvc.perform(get("/api/treatment-drugs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatmentDrug.getId().intValue())))
            .andExpect(jsonPath("$.[*].day").value(hasItem(DEFAULT_DAY)));

        // Check, that the count call also returns 1
        restTreatmentDrugMockMvc.perform(get("/api/treatment-drugs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTreatmentDrugShouldNotBeFound(String filter) throws Exception {
        restTreatmentDrugMockMvc.perform(get("/api/treatment-drugs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTreatmentDrugMockMvc.perform(get("/api/treatment-drugs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTreatmentDrug() throws Exception {
        // Get the treatmentDrug
        restTreatmentDrugMockMvc.perform(get("/api/treatment-drugs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreatmentDrug() throws Exception {
        // Initialize the database
        treatmentDrugService.save(treatmentDrug);

        int databaseSizeBeforeUpdate = treatmentDrugRepository.findAll().size();

        // Update the treatmentDrug
        TreatmentDrug updatedTreatmentDrug = treatmentDrugRepository.findById(treatmentDrug.getId()).get();
        // Disconnect from session so that the updates on updatedTreatmentDrug are not directly saved in db
        em.detach(updatedTreatmentDrug);
        updatedTreatmentDrug
            .day(UPDATED_DAY);

        restTreatmentDrugMockMvc.perform(put("/api/treatment-drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTreatmentDrug)))
            .andExpect(status().isOk());

        // Validate the TreatmentDrug in the database
        List<TreatmentDrug> treatmentDrugList = treatmentDrugRepository.findAll();
        assertThat(treatmentDrugList).hasSize(databaseSizeBeforeUpdate);
        TreatmentDrug testTreatmentDrug = treatmentDrugList.get(treatmentDrugList.size() - 1);
        assertThat(testTreatmentDrug.getDay()).isEqualTo(UPDATED_DAY);
    }

    @Test
    @Transactional
    public void updateNonExistingTreatmentDrug() throws Exception {
        int databaseSizeBeforeUpdate = treatmentDrugRepository.findAll().size();

        // Create the TreatmentDrug

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTreatmentDrugMockMvc.perform(put("/api/treatment-drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentDrug)))
            .andExpect(status().isBadRequest());

        // Validate the TreatmentDrug in the database
        List<TreatmentDrug> treatmentDrugList = treatmentDrugRepository.findAll();
        assertThat(treatmentDrugList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTreatmentDrug() throws Exception {
        // Initialize the database
        treatmentDrugService.save(treatmentDrug);

        int databaseSizeBeforeDelete = treatmentDrugRepository.findAll().size();

        // Get the treatmentDrug
        restTreatmentDrugMockMvc.perform(delete("/api/treatment-drugs/{id}", treatmentDrug.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TreatmentDrug> treatmentDrugList = treatmentDrugRepository.findAll();
        assertThat(treatmentDrugList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TreatmentDrug.class);
        TreatmentDrug treatmentDrug1 = new TreatmentDrug();
        treatmentDrug1.setId(1L);
        TreatmentDrug treatmentDrug2 = new TreatmentDrug();
        treatmentDrug2.setId(treatmentDrug1.getId());
        assertThat(treatmentDrug1).isEqualTo(treatmentDrug2);
        treatmentDrug2.setId(2L);
        assertThat(treatmentDrug1).isNotEqualTo(treatmentDrug2);
        treatmentDrug1.setId(null);
        assertThat(treatmentDrug1).isNotEqualTo(treatmentDrug2);
    }
}
