package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.Esign;
import io.dentall.totoro.repository.EsignRepository;
import io.dentall.totoro.service.EsignService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.dto.EsignCriteria;
import io.dentall.totoro.service.EsignQueryService;

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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.dentall.totoro.domain.enumeration.SourceType;
/**
 * Test class for the EsignResource REST controller.
 *
 * @see EsignResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class EsignResourceIntTest {

    private static final Long DEFAULT_PATIENT_ID = 1L;
    private static final Long UPDATED_PATIENT_ID = 2L;

    private static final byte[] DEFAULT_LOB = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOB = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOB_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOB_CONTENT_TYPE = "image/png";

    private static final SourceType DEFAULT_SOURCE_TYPE = SourceType.BY_STRING64;
    private static final SourceType UPDATED_SOURCE_TYPE = SourceType.BY_FILE;

    @Autowired
    private EsignRepository esignRepository;

    @Autowired
    private EsignService esignService;

    @Autowired
    private EsignQueryService esignQueryService;

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

    private MockMvc restEsignMockMvc;

    private Esign esign;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EsignResource esignResource = new EsignResource(esignService, esignQueryService);
        this.restEsignMockMvc = MockMvcBuilders.standaloneSetup(esignResource)
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
    public static Esign createEntity(EntityManager em) {
        Esign esign = new Esign()
            .patientId(DEFAULT_PATIENT_ID)
            .lob(DEFAULT_LOB)
            .lobContentType(DEFAULT_LOB_CONTENT_TYPE)
            .sourceType(DEFAULT_SOURCE_TYPE);
        return esign;
    }

    @Before
    public void initTest() {
        esign = createEntity(em);
    }

    @Test
    @Transactional
    public void createEsign() throws Exception {
        int databaseSizeBeforeCreate = esignRepository.findAll().size();

        // Create the Esign
        restEsignMockMvc.perform(post("/api/esigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esign)))
            .andExpect(status().isCreated());

        // Validate the Esign in the database
        List<Esign> esignList = esignRepository.findAll();
        assertThat(esignList).hasSize(databaseSizeBeforeCreate + 1);
        Esign testEsign = esignList.get(esignList.size() - 1);
        assertThat(testEsign.getPatientId()).isEqualTo(DEFAULT_PATIENT_ID);
        assertThat(testEsign.getLob()).isEqualTo(DEFAULT_LOB);
        assertThat(testEsign.getLobContentType()).isEqualTo(DEFAULT_LOB_CONTENT_TYPE);
        assertThat(testEsign.getSourceType()).isEqualTo(DEFAULT_SOURCE_TYPE);
    }

    @Test
    @Transactional
    public void createEsignWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = esignRepository.findAll().size();

        // Create the Esign with an existing ID
        esign.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEsignMockMvc.perform(post("/api/esigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esign)))
            .andExpect(status().isBadRequest());

        // Validate the Esign in the database
        List<Esign> esignList = esignRepository.findAll();
        assertThat(esignList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkPatientIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = esignRepository.findAll().size();
        // set the field null
        esign.setPatientId(null);

        // Create the Esign, which fails.

        restEsignMockMvc.perform(post("/api/esigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esign)))
            .andExpect(status().isBadRequest());

        List<Esign> esignList = esignRepository.findAll();
        assertThat(esignList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEsigns() throws Exception {
        // Initialize the database
        esignRepository.saveAndFlush(esign);

        // Get all the esignList
        restEsignMockMvc.perform(get("/api/esigns?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(esign.getId().intValue())))
            .andExpect(jsonPath("$.[*].patientId").value(hasItem(DEFAULT_PATIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].lobContentType").value(hasItem(DEFAULT_LOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].lob").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOB))))
            .andExpect(jsonPath("$.[*].sourceType").value(hasItem(DEFAULT_SOURCE_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getEsign() throws Exception {
        // Initialize the database
        esignRepository.saveAndFlush(esign);

        // Get the esign
        restEsignMockMvc.perform(get("/api/esigns/{id}", esign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(esign.getId().intValue()))
            .andExpect(jsonPath("$.patientId").value(DEFAULT_PATIENT_ID.intValue()))
            .andExpect(jsonPath("$.lobContentType").value(DEFAULT_LOB_CONTENT_TYPE))
            .andExpect(jsonPath("$.lob").value(Base64Utils.encodeToString(DEFAULT_LOB)))
            .andExpect(jsonPath("$.sourceType").value(DEFAULT_SOURCE_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllEsignsByPatientIdIsEqualToSomething() throws Exception {
        // Initialize the database
        esignRepository.saveAndFlush(esign);

        // Get all the esignList where patientId equals to DEFAULT_PATIENT_ID
        defaultEsignShouldBeFound("patientId.equals=" + DEFAULT_PATIENT_ID);

        // Get all the esignList where patientId equals to UPDATED_PATIENT_ID
        defaultEsignShouldNotBeFound("patientId.equals=" + UPDATED_PATIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEsignsByPatientIdIsInShouldWork() throws Exception {
        // Initialize the database
        esignRepository.saveAndFlush(esign);

        // Get all the esignList where patientId in DEFAULT_PATIENT_ID or UPDATED_PATIENT_ID
        defaultEsignShouldBeFound("patientId.in=" + DEFAULT_PATIENT_ID + "," + UPDATED_PATIENT_ID);

        // Get all the esignList where patientId equals to UPDATED_PATIENT_ID
        defaultEsignShouldNotBeFound("patientId.in=" + UPDATED_PATIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEsignsByPatientIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        esignRepository.saveAndFlush(esign);

        // Get all the esignList where patientId is not null
        defaultEsignShouldBeFound("patientId.specified=true");

        // Get all the esignList where patientId is null
        defaultEsignShouldNotBeFound("patientId.specified=false");
    }

    @Test
    @Transactional
    public void getAllEsignsByPatientIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        esignRepository.saveAndFlush(esign);

        // Get all the esignList where patientId greater than or equals to DEFAULT_PATIENT_ID
        defaultEsignShouldBeFound("patientId.greaterOrEqualThan=" + DEFAULT_PATIENT_ID);

        // Get all the esignList where patientId greater than or equals to UPDATED_PATIENT_ID
        defaultEsignShouldNotBeFound("patientId.greaterOrEqualThan=" + UPDATED_PATIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEsignsByPatientIdIsLessThanSomething() throws Exception {
        // Initialize the database
        esignRepository.saveAndFlush(esign);

        // Get all the esignList where patientId less than or equals to DEFAULT_PATIENT_ID
        defaultEsignShouldNotBeFound("patientId.lessThan=" + DEFAULT_PATIENT_ID);

        // Get all the esignList where patientId less than or equals to UPDATED_PATIENT_ID
        defaultEsignShouldBeFound("patientId.lessThan=" + UPDATED_PATIENT_ID);
    }

    @Test
    @Transactional
    public void getAllEsignsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        esignRepository.saveAndFlush(esign);

        // Get all the esignList where createdDate is not null
        defaultEsignShouldBeFound("createdDate.specified=true");

        // Get all the esignList where createdDate is null
        defaultEsignShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllEsignsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        esignRepository.saveAndFlush(esign);

        // Get all the esignList where createdBy is not null
        defaultEsignShouldBeFound("createdBy.specified=true");

        // Get all the esignList where createdBy is null
        defaultEsignShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllEsignsBySourceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        esignRepository.saveAndFlush(esign);

        // Get all the esignList where sourceType equals to DEFAULT_SOURCE_TYPE
        defaultEsignShouldBeFound("sourceType.equals=" + DEFAULT_SOURCE_TYPE);

        // Get all the esignList where sourceType equals to UPDATED_SOURCE_TYPE
        defaultEsignShouldNotBeFound("sourceType.equals=" + UPDATED_SOURCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllEsignsBySourceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        esignRepository.saveAndFlush(esign);

        // Get all the esignList where sourceType in DEFAULT_SOURCE_TYPE or UPDATED_SOURCE_TYPE
        defaultEsignShouldBeFound("sourceType.in=" + DEFAULT_SOURCE_TYPE + "," + UPDATED_SOURCE_TYPE);

        // Get all the esignList where sourceType equals to UPDATED_SOURCE_TYPE
        defaultEsignShouldNotBeFound("sourceType.in=" + UPDATED_SOURCE_TYPE);
    }

    @Test
    @Transactional
    public void getAllEsignsBySourceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        esignRepository.saveAndFlush(esign);

        // Get all the esignList where sourceType is not null
        defaultEsignShouldBeFound("sourceType.specified=true");

        // Get all the esignList where sourceType is null
        defaultEsignShouldNotBeFound("sourceType.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEsignShouldBeFound(String filter) throws Exception {
        restEsignMockMvc.perform(get("/api/esigns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(esign.getId().intValue())))
            .andExpect(jsonPath("$.[*].patientId").value(hasItem(DEFAULT_PATIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].lobContentType").value(hasItem(DEFAULT_LOB_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].lob").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOB))))
            .andExpect(jsonPath("$.[*].sourceType").value(hasItem(DEFAULT_SOURCE_TYPE.toString())));

        // Check, that the count call also returns 1
        restEsignMockMvc.perform(get("/api/esigns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEsignShouldNotBeFound(String filter) throws Exception {
        restEsignMockMvc.perform(get("/api/esigns?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEsignMockMvc.perform(get("/api/esigns/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEsign() throws Exception {
        // Get the esign
        restEsignMockMvc.perform(get("/api/esigns/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEsign() throws Exception {
        // Initialize the database
        esignService.save(esign);

        int databaseSizeBeforeUpdate = esignRepository.findAll().size();

        // Update the esign
        Esign updatedEsign = esignRepository.findById(esign.getId()).get();
        // Disconnect from session so that the updates on updatedEsign are not directly saved in db
        em.detach(updatedEsign);
        updatedEsign
            .patientId(UPDATED_PATIENT_ID)
            .lob(UPDATED_LOB)
            .lobContentType(UPDATED_LOB_CONTENT_TYPE)
            .sourceType(UPDATED_SOURCE_TYPE);

        restEsignMockMvc.perform(put("/api/esigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEsign)))
            .andExpect(status().isOk());

        // Validate the Esign in the database
        List<Esign> esignList = esignRepository.findAll();
        assertThat(esignList).hasSize(databaseSizeBeforeUpdate);
        Esign testEsign = esignList.get(esignList.size() - 1);
        assertThat(testEsign.getPatientId()).isEqualTo(UPDATED_PATIENT_ID);
        assertThat(testEsign.getLob()).isEqualTo(UPDATED_LOB);
        assertThat(testEsign.getLobContentType()).isEqualTo(UPDATED_LOB_CONTENT_TYPE);
        assertThat(testEsign.getSourceType()).isEqualTo(UPDATED_SOURCE_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingEsign() throws Exception {
        int databaseSizeBeforeUpdate = esignRepository.findAll().size();

        // Create the Esign

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEsignMockMvc.perform(put("/api/esigns")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(esign)))
            .andExpect(status().isBadRequest());

        // Validate the Esign in the database
        List<Esign> esignList = esignRepository.findAll();
        assertThat(esignList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEsign() throws Exception {
        // Initialize the database
        esignService.save(esign);

        int databaseSizeBeforeDelete = esignRepository.findAll().size();

        // Get the esign
        restEsignMockMvc.perform(delete("/api/esigns/{id}", esign.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Esign> esignList = esignRepository.findAll();
        assertThat(esignList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Esign.class);
        Esign esign1 = new Esign();
        esign1.setId(1L);
        Esign esign2 = new Esign();
        esign2.setId(esign1.getId());
        assertThat(esign1).isEqualTo(esign2);
        esign2.setId(2L);
        assertThat(esign1).isNotEqualTo(esign2);
        esign1.setId(null);
        assertThat(esign1).isNotEqualTo(esign2);
    }
}
