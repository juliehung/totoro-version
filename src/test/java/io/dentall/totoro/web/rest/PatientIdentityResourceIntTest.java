package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.PatientIdentity;
import io.dentall.totoro.repository.PatientIdentityRepository;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PatientIdentityResource REST controller.
 *
 * @see PatientIdentityResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class PatientIdentityResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FREE_BURDEN = false;
    private static final Boolean UPDATED_FREE_BURDEN = true;

    @Autowired
    private PatientIdentityRepository patientIdentityRepository;

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

    private MockMvc restPatientIdentityMockMvc;

    private PatientIdentity patientIdentity;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientIdentityResource patientIdentityResource = new PatientIdentityResource(patientIdentityRepository);
        this.restPatientIdentityMockMvc = MockMvcBuilders.standaloneSetup(patientIdentityResource)
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
    public static PatientIdentity createEntity(EntityManager em) {
        PatientIdentity patientIdentity = new PatientIdentity()
            .enable(true);
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .freeBurden(DEFAULT_FREE_BURDEN);
        return patientIdentity;
    }

    @Before
    public void initTest() {
        patientIdentity = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatientIdentity() throws Exception {
        int databaseSizeBeforeCreate = patientIdentityRepository.findAll().size();

        // Create the PatientIdentity
        restPatientIdentityMockMvc.perform(post("/api/patient-identities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientIdentity)))
            .andExpect(status().isCreated());

        // Validate the PatientIdentity in the database
        List<PatientIdentity> patientIdentityList = patientIdentityRepository.findAll();
        assertThat(patientIdentityList).hasSize(databaseSizeBeforeCreate + 1);
        PatientIdentity testPatientIdentity = patientIdentityList.get(patientIdentityList.size() - 1);
        assertThat(testPatientIdentity.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testPatientIdentity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPatientIdentity.isFreeBurden()).isEqualTo(DEFAULT_FREE_BURDEN);
    }

    @Test
    @Transactional
    public void createPatientIdentityWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientIdentityRepository.findAll().size();

        // Create the PatientIdentity with an existing ID
        patientIdentity.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientIdentityMockMvc.perform(post("/api/patient-identities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientIdentity)))
            .andExpect(status().isBadRequest());

        // Validate the PatientIdentity in the database
        List<PatientIdentity> patientIdentityList = patientIdentityRepository.findAll();
        assertThat(patientIdentityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientIdentityRepository.findAll().size();
        // set the field null
        patientIdentity.setCode(null);

        // Create the PatientIdentity, which fails.

        restPatientIdentityMockMvc.perform(post("/api/patient-identities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientIdentity)))
            .andExpect(status().isBadRequest());

        List<PatientIdentity> patientIdentityList = patientIdentityRepository.findAll();
        assertThat(patientIdentityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientIdentityRepository.findAll().size();
        // set the field null
        patientIdentity.setName(null);

        // Create the PatientIdentity, which fails.

        restPatientIdentityMockMvc.perform(post("/api/patient-identities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientIdentity)))
            .andExpect(status().isBadRequest());

        List<PatientIdentity> patientIdentityList = patientIdentityRepository.findAll();
        assertThat(patientIdentityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFreeBurdenIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientIdentityRepository.findAll().size();
        // set the field null
        patientIdentity.setFreeBurden(null);

        // Create the PatientIdentity, which fails.

        restPatientIdentityMockMvc.perform(post("/api/patient-identities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientIdentity)))
            .andExpect(status().isBadRequest());

        List<PatientIdentity> patientIdentityList = patientIdentityRepository.findAll();
        assertThat(patientIdentityList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPatientIdentities() throws Exception {
        // Initialize the database
        patientIdentityRepository.saveAndFlush(patientIdentity);

        // Get all the patientIdentityList
        restPatientIdentityMockMvc.perform(get("/api/patient-identities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientIdentity.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].freeBurden").value(hasItem(DEFAULT_FREE_BURDEN.booleanValue())));
    }

    @Test
    @Transactional
    public void getPatientIdentity() throws Exception {
        // Initialize the database
        patientIdentityRepository.saveAndFlush(patientIdentity);

        // Get the patientIdentity
        restPatientIdentityMockMvc.perform(get("/api/patient-identities/{id}", patientIdentity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patientIdentity.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.freeBurden").value(DEFAULT_FREE_BURDEN.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPatientIdentity() throws Exception {
        // Get the patientIdentity
        restPatientIdentityMockMvc.perform(get("/api/patient-identities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatientIdentity() throws Exception {
        // Initialize the database
        patientIdentityRepository.saveAndFlush(patientIdentity);

        int databaseSizeBeforeUpdate = patientIdentityRepository.findAll().size();

        // Update the patientIdentity
        PatientIdentity updatedPatientIdentity = patientIdentityRepository.findById(patientIdentity.getId()).get();
        // Disconnect from session so that the updates on updatedPatientIdentity are not directly saved in db
        em.detach(updatedPatientIdentity);
        updatedPatientIdentity
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .freeBurden(UPDATED_FREE_BURDEN);

        restPatientIdentityMockMvc.perform(put("/api/patient-identities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatientIdentity)))
            .andExpect(status().isOk());

        // Validate the PatientIdentity in the database
        List<PatientIdentity> patientIdentityList = patientIdentityRepository.findAll();
        assertThat(patientIdentityList).hasSize(databaseSizeBeforeUpdate);
        PatientIdentity testPatientIdentity = patientIdentityList.get(patientIdentityList.size() - 1);
        assertThat(testPatientIdentity.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testPatientIdentity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPatientIdentity.isFreeBurden()).isEqualTo(UPDATED_FREE_BURDEN);
    }

    @Test
    @Transactional
    public void updateNonExistingPatientIdentity() throws Exception {
        int databaseSizeBeforeUpdate = patientIdentityRepository.findAll().size();

        // Create the PatientIdentity

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientIdentityMockMvc.perform(put("/api/patient-identities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patientIdentity)))
            .andExpect(status().isBadRequest());

        // Validate the PatientIdentity in the database
        List<PatientIdentity> patientIdentityList = patientIdentityRepository.findAll();
        assertThat(patientIdentityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePatientIdentity() throws Exception {
        // Initialize the database
        patientIdentityRepository.saveAndFlush(patientIdentity);

        int databaseSizeBeforeDelete = patientIdentityRepository.findAll().size();

        // Get the patientIdentity
        restPatientIdentityMockMvc.perform(delete("/api/patient-identities/{id}", patientIdentity.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PatientIdentity> patientIdentityList = patientIdentityRepository.findAll();
        assertThat(patientIdentityList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PatientIdentity.class);
        PatientIdentity patientIdentity1 = new PatientIdentity();
        patientIdentity1.setId(1L);
        PatientIdentity patientIdentity2 = new PatientIdentity();
        patientIdentity2.setId(patientIdentity1.getId());
        assertThat(patientIdentity1).isEqualTo(patientIdentity2);
        patientIdentity2.setId(2L);
        assertThat(patientIdentity1).isNotEqualTo(patientIdentity2);
        patientIdentity1.setId(null);
        assertThat(patientIdentity1).isNotEqualTo(patientIdentity2);
    }
}
