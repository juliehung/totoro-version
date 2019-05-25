package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.NhiDayUploadDetails;
import io.dentall.totoro.domain.NhiDayUpload;
import io.dentall.totoro.repository.NhiDayUploadDetailsRepository;
import io.dentall.totoro.service.NhiDayUploadDetailsService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.dto.NhiDayUploadDetailsCriteria;
import io.dentall.totoro.service.NhiDayUploadDetailsQueryService;

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

import io.dentall.totoro.domain.enumeration.NhiDayUploadDetailType;
/**
 * Test class for the NhiDayUploadDetailsResource REST controller.
 *
 * @see NhiDayUploadDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiDayUploadDetailsResourceIntTest {

    private static final NhiDayUploadDetailType DEFAULT_TYPE = NhiDayUploadDetailType.NORMAL;
    private static final NhiDayUploadDetailType UPDATED_TYPE = NhiDayUploadDetailType.CORRECTION;

    @Autowired
    private NhiDayUploadDetailsRepository nhiDayUploadDetailsRepository;

    @Autowired
    private NhiDayUploadDetailsService nhiDayUploadDetailsService;

    @Autowired
    private NhiDayUploadDetailsQueryService nhiDayUploadDetailsQueryService;

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

    private MockMvc restNhiDayUploadDetailsMockMvc;

    private NhiDayUploadDetails nhiDayUploadDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhiDayUploadDetailsResource nhiDayUploadDetailsResource = new NhiDayUploadDetailsResource(nhiDayUploadDetailsService, nhiDayUploadDetailsQueryService);
        this.restNhiDayUploadDetailsMockMvc = MockMvcBuilders.standaloneSetup(nhiDayUploadDetailsResource)
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
    public static NhiDayUploadDetails createEntity(EntityManager em) {
        NhiDayUploadDetails nhiDayUploadDetails = new NhiDayUploadDetails()
            .type(DEFAULT_TYPE);
        return nhiDayUploadDetails;
    }

    @Before
    public void initTest() {
        nhiDayUploadDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhiDayUploadDetails() throws Exception {
        int databaseSizeBeforeCreate = nhiDayUploadDetailsRepository.findAll().size();

        // Create the NhiDayUploadDetails
        restNhiDayUploadDetailsMockMvc.perform(post("/api/nhi-day-upload-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiDayUploadDetails)))
            .andExpect(status().isCreated());

        // Validate the NhiDayUploadDetails in the database
        List<NhiDayUploadDetails> nhiDayUploadDetailsList = nhiDayUploadDetailsRepository.findAll();
        assertThat(nhiDayUploadDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        NhiDayUploadDetails testNhiDayUploadDetails = nhiDayUploadDetailsList.get(nhiDayUploadDetailsList.size() - 1);
        assertThat(testNhiDayUploadDetails.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    public void createNhiDayUploadDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhiDayUploadDetailsRepository.findAll().size();

        // Create the NhiDayUploadDetails with an existing ID
        nhiDayUploadDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhiDayUploadDetailsMockMvc.perform(post("/api/nhi-day-upload-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiDayUploadDetails)))
            .andExpect(status().isBadRequest());

        // Validate the NhiDayUploadDetails in the database
        List<NhiDayUploadDetails> nhiDayUploadDetailsList = nhiDayUploadDetailsRepository.findAll();
        assertThat(nhiDayUploadDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhiDayUploadDetailsRepository.findAll().size();
        // set the field null
        nhiDayUploadDetails.setType(null);

        // Create the NhiDayUploadDetails, which fails.

        restNhiDayUploadDetailsMockMvc.perform(post("/api/nhi-day-upload-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiDayUploadDetails)))
            .andExpect(status().isBadRequest());

        List<NhiDayUploadDetails> nhiDayUploadDetailsList = nhiDayUploadDetailsRepository.findAll();
        assertThat(nhiDayUploadDetailsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNhiDayUploadDetails() throws Exception {
        // Initialize the database
        nhiDayUploadDetailsRepository.saveAndFlush(nhiDayUploadDetails);

        // Get all the nhiDayUploadDetailsList
        restNhiDayUploadDetailsMockMvc.perform(get("/api/nhi-day-upload-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiDayUploadDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getNhiDayUploadDetails() throws Exception {
        // Initialize the database
        nhiDayUploadDetailsRepository.saveAndFlush(nhiDayUploadDetails);

        // Get the nhiDayUploadDetails
        restNhiDayUploadDetailsMockMvc.perform(get("/api/nhi-day-upload-details/{id}", nhiDayUploadDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhiDayUploadDetails.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getAllNhiDayUploadDetailsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiDayUploadDetailsRepository.saveAndFlush(nhiDayUploadDetails);

        // Get all the nhiDayUploadDetailsList where type equals to DEFAULT_TYPE
        defaultNhiDayUploadDetailsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the nhiDayUploadDetailsList where type equals to UPDATED_TYPE
        defaultNhiDayUploadDetailsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllNhiDayUploadDetailsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        nhiDayUploadDetailsRepository.saveAndFlush(nhiDayUploadDetails);

        // Get all the nhiDayUploadDetailsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultNhiDayUploadDetailsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the nhiDayUploadDetailsList where type equals to UPDATED_TYPE
        defaultNhiDayUploadDetailsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllNhiDayUploadDetailsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiDayUploadDetailsRepository.saveAndFlush(nhiDayUploadDetails);

        // Get all the nhiDayUploadDetailsList where type is not null
        defaultNhiDayUploadDetailsShouldBeFound("type.specified=true");

        // Get all the nhiDayUploadDetailsList where type is null
        defaultNhiDayUploadDetailsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiDayUploadDetailsByNhiDayUploadIsEqualToSomething() throws Exception {
        // Initialize the database
        NhiDayUpload nhiDayUpload = NhiDayUploadResourceIntTest.createEntity(em);
        em.persist(nhiDayUpload);
        em.flush();
        nhiDayUploadDetails.setNhiDayUpload(nhiDayUpload);
        nhiDayUploadDetailsRepository.saveAndFlush(nhiDayUploadDetails);
        Long nhiDayUploadId = nhiDayUpload.getId();

        // Get all the nhiDayUploadDetailsList where nhiDayUpload equals to nhiDayUploadId
        defaultNhiDayUploadDetailsShouldBeFound("nhiDayUploadId.equals=" + nhiDayUploadId);

        // Get all the nhiDayUploadDetailsList where nhiDayUpload equals to nhiDayUploadId + 1
        defaultNhiDayUploadDetailsShouldNotBeFound("nhiDayUploadId.equals=" + (nhiDayUploadId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNhiDayUploadDetailsShouldBeFound(String filter) throws Exception {
        restNhiDayUploadDetailsMockMvc.perform(get("/api/nhi-day-upload-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiDayUploadDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));

        // Check, that the count call also returns 1
        restNhiDayUploadDetailsMockMvc.perform(get("/api/nhi-day-upload-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNhiDayUploadDetailsShouldNotBeFound(String filter) throws Exception {
        restNhiDayUploadDetailsMockMvc.perform(get("/api/nhi-day-upload-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNhiDayUploadDetailsMockMvc.perform(get("/api/nhi-day-upload-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNhiDayUploadDetails() throws Exception {
        // Get the nhiDayUploadDetails
        restNhiDayUploadDetailsMockMvc.perform(get("/api/nhi-day-upload-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhiDayUploadDetails() throws Exception {
        // Initialize the database
        nhiDayUploadDetailsService.save(nhiDayUploadDetails);

        int databaseSizeBeforeUpdate = nhiDayUploadDetailsRepository.findAll().size();

        // Update the nhiDayUploadDetails
        NhiDayUploadDetails updatedNhiDayUploadDetails = nhiDayUploadDetailsRepository.findById(nhiDayUploadDetails.getId()).get();
        // Disconnect from session so that the updates on updatedNhiDayUploadDetails are not directly saved in db
        em.detach(updatedNhiDayUploadDetails);
        updatedNhiDayUploadDetails
            .type(UPDATED_TYPE);

        restNhiDayUploadDetailsMockMvc.perform(put("/api/nhi-day-upload-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNhiDayUploadDetails)))
            .andExpect(status().isOk());

        // Validate the NhiDayUploadDetails in the database
        List<NhiDayUploadDetails> nhiDayUploadDetailsList = nhiDayUploadDetailsRepository.findAll();
        assertThat(nhiDayUploadDetailsList).hasSize(databaseSizeBeforeUpdate);
        NhiDayUploadDetails testNhiDayUploadDetails = nhiDayUploadDetailsList.get(nhiDayUploadDetailsList.size() - 1);
        assertThat(testNhiDayUploadDetails.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingNhiDayUploadDetails() throws Exception {
        int databaseSizeBeforeUpdate = nhiDayUploadDetailsRepository.findAll().size();

        // Create the NhiDayUploadDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhiDayUploadDetailsMockMvc.perform(put("/api/nhi-day-upload-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiDayUploadDetails)))
            .andExpect(status().isBadRequest());

        // Validate the NhiDayUploadDetails in the database
        List<NhiDayUploadDetails> nhiDayUploadDetailsList = nhiDayUploadDetailsRepository.findAll();
        assertThat(nhiDayUploadDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhiDayUploadDetails() throws Exception {
        // Initialize the database
        nhiDayUploadDetailsService.save(nhiDayUploadDetails);

        int databaseSizeBeforeDelete = nhiDayUploadDetailsRepository.findAll().size();

        // Get the nhiDayUploadDetails
        restNhiDayUploadDetailsMockMvc.perform(delete("/api/nhi-day-upload-details/{id}", nhiDayUploadDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhiDayUploadDetails> nhiDayUploadDetailsList = nhiDayUploadDetailsRepository.findAll();
        assertThat(nhiDayUploadDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhiDayUploadDetails.class);
        NhiDayUploadDetails nhiDayUploadDetails1 = new NhiDayUploadDetails();
        nhiDayUploadDetails1.setId(1L);
        NhiDayUploadDetails nhiDayUploadDetails2 = new NhiDayUploadDetails();
        nhiDayUploadDetails2.setId(nhiDayUploadDetails1.getId());
        assertThat(nhiDayUploadDetails1).isEqualTo(nhiDayUploadDetails2);
        nhiDayUploadDetails2.setId(2L);
        assertThat(nhiDayUploadDetails1).isNotEqualTo(nhiDayUploadDetails2);
        nhiDayUploadDetails1.setId(null);
        assertThat(nhiDayUploadDetails1).isNotEqualTo(nhiDayUploadDetails2);
    }
}
