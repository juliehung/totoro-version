package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.NhiExtendPatient;
import io.dentall.totoro.repository.NhiExtendPatientRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.service.NhiExtendPatientService;
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
 * Test class for the NhiExtendPatientResource REST controller.
 *
 * @see NhiExtendPatientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiExtendPatientResourceIntTest {

    private static final String DEFAULT_CARD_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CARD_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_ANNOTATION = "AAAAAAAAAA";
    private static final String UPDATED_CARD_ANNOTATION = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_VALID_DATE = "AAAAAAAAAA";
    private static final String UPDATED_CARD_VALID_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_CARD_ISSUE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_CARD_ISSUE_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_NHI_IDENTITY = "AAAAAAAAAA";
    private static final String UPDATED_NHI_IDENTITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_AVAILABLE_TIMES = 1;
    private static final Integer UPDATED_AVAILABLE_TIMES = 2;

    private static final String DEFAULT_SCALING = "AAAAAAAAAA";
    private static final String UPDATED_SCALING = "BBBBBBBBBB";

    private static final String DEFAULT_FLUORIDE = "AAAAAAAAAA";
    private static final String UPDATED_FLUORIDE = "BBBBBBBBBB";

    private static final String DEFAULT_PERIO = "AAAAAAAAAA";
    private static final String UPDATED_PERIO = "BBBBBBBBBB";

    @Autowired
    private NhiExtendPatientRepository nhiExtendPatientRepository;

    @Autowired
    private NhiExtendPatientService nhiExtendPatientService;

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

    private MockMvc restNhiExtendPatientMockMvc;

    private NhiExtendPatient nhiExtendPatient;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhiExtendPatientResource nhiExtendPatientResource = new NhiExtendPatientResource(nhiExtendPatientService);
        this.restNhiExtendPatientMockMvc = MockMvcBuilders.standaloneSetup(nhiExtendPatientResource)
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
    public static NhiExtendPatient createEntity(EntityManager em) {
        NhiExtendPatient nhiExtendPatient = new NhiExtendPatient()
            .cardNumber(DEFAULT_CARD_NUMBER)
            .cardAnnotation(DEFAULT_CARD_ANNOTATION)
            .cardValidDate(DEFAULT_CARD_VALID_DATE)
            .cardIssueDate(DEFAULT_CARD_ISSUE_DATE)
            .nhiIdentity(DEFAULT_NHI_IDENTITY)
            .availableTimes(DEFAULT_AVAILABLE_TIMES)
            .scaling(DEFAULT_SCALING)
            .fluoride(DEFAULT_FLUORIDE)
            .perio(DEFAULT_PERIO);
        return nhiExtendPatient;
    }

    @Before
    public void initTest() {
        nhiExtendPatient = createEntity(em);
        nhiExtendPatient.setPatient(patientRepository.save(PatientResourceIntTest.createEntity(em)));
    }

    @Test
    @Transactional
    public void createNhiExtendPatient() throws Exception {
        int databaseSizeBeforeCreate = nhiExtendPatientRepository.findAll().size();

        // Create the NhiExtendPatient
        restNhiExtendPatientMockMvc.perform(post("/api/nhi-extend-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiExtendPatient)))
            .andExpect(status().isCreated());

        // Validate the NhiExtendPatient in the database
        List<NhiExtendPatient> nhiExtendPatientList = nhiExtendPatientRepository.findAll();
        assertThat(nhiExtendPatientList).hasSize(databaseSizeBeforeCreate + 1);
        NhiExtendPatient testNhiExtendPatient = nhiExtendPatientList.get(nhiExtendPatientList.size() - 1);
        assertThat(testNhiExtendPatient.getCardNumber()).isEqualTo(DEFAULT_CARD_NUMBER);
        assertThat(testNhiExtendPatient.getCardAnnotation()).isEqualTo(DEFAULT_CARD_ANNOTATION);
        assertThat(testNhiExtendPatient.getCardValidDate()).isEqualTo(DEFAULT_CARD_VALID_DATE);
        assertThat(testNhiExtendPatient.getCardIssueDate()).isEqualTo(DEFAULT_CARD_ISSUE_DATE);
        assertThat(testNhiExtendPatient.getNhiIdentity()).isEqualTo(DEFAULT_NHI_IDENTITY);
        assertThat(testNhiExtendPatient.getAvailableTimes()).isEqualTo(DEFAULT_AVAILABLE_TIMES);
        assertThat(testNhiExtendPatient.getScaling()).isEqualTo(DEFAULT_SCALING);
        assertThat(testNhiExtendPatient.getFluoride()).isEqualTo(DEFAULT_FLUORIDE);
        assertThat(testNhiExtendPatient.getPerio()).isEqualTo(DEFAULT_PERIO);
    }

    @Test
    @Transactional
    public void createNhiExtendPatientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhiExtendPatientRepository.findAll().size();

        // Create the NhiExtendPatient with an existing ID
        nhiExtendPatient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhiExtendPatientMockMvc.perform(post("/api/nhi-extend-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiExtendPatient)))
            .andExpect(status().isBadRequest());

        // Validate the NhiExtendPatient in the database
        List<NhiExtendPatient> nhiExtendPatientList = nhiExtendPatientRepository.findAll();
        assertThat(nhiExtendPatientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNhiExtendPatients() throws Exception {
        // Initialize the database
        nhiExtendPatientRepository.saveAndFlush(nhiExtendPatient);

        // Get all the nhiExtendPatientList
        restNhiExtendPatientMockMvc.perform(get("/api/nhi-extend-patients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiExtendPatient.getId().intValue())))
            .andExpect(jsonPath("$.[*].cardNumber").value(hasItem(DEFAULT_CARD_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].cardAnnotation").value(hasItem(DEFAULT_CARD_ANNOTATION.toString())))
            .andExpect(jsonPath("$.[*].cardValidDate").value(hasItem(DEFAULT_CARD_VALID_DATE.toString())))
            .andExpect(jsonPath("$.[*].cardIssueDate").value(hasItem(DEFAULT_CARD_ISSUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].nhiIdentity").value(hasItem(DEFAULT_NHI_IDENTITY.toString())))
            .andExpect(jsonPath("$.[*].availableTimes").value(hasItem(DEFAULT_AVAILABLE_TIMES)))
            .andExpect(jsonPath("$.[*].scaling").value(hasItem(DEFAULT_SCALING.toString())))
            .andExpect(jsonPath("$.[*].fluoride").value(hasItem(DEFAULT_FLUORIDE.toString())))
            .andExpect(jsonPath("$.[*].perio").value(hasItem(DEFAULT_PERIO.toString())));
    }
    
    @Test
    @Transactional
    public void getNhiExtendPatient() throws Exception {
        // Initialize the database
        nhiExtendPatientRepository.saveAndFlush(nhiExtendPatient);

        // Get the nhiExtendPatient
        restNhiExtendPatientMockMvc.perform(get("/api/nhi-extend-patients/{id}", nhiExtendPatient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhiExtendPatient.getId().intValue()))
            .andExpect(jsonPath("$.cardNumber").value(DEFAULT_CARD_NUMBER.toString()))
            .andExpect(jsonPath("$.cardAnnotation").value(DEFAULT_CARD_ANNOTATION.toString()))
            .andExpect(jsonPath("$.cardValidDate").value(DEFAULT_CARD_VALID_DATE.toString()))
            .andExpect(jsonPath("$.cardIssueDate").value(DEFAULT_CARD_ISSUE_DATE.toString()))
            .andExpect(jsonPath("$.nhiIdentity").value(DEFAULT_NHI_IDENTITY.toString()))
            .andExpect(jsonPath("$.availableTimes").value(DEFAULT_AVAILABLE_TIMES))
            .andExpect(jsonPath("$.scaling").value(DEFAULT_SCALING.toString()))
            .andExpect(jsonPath("$.fluoride").value(DEFAULT_FLUORIDE.toString()))
            .andExpect(jsonPath("$.perio").value(DEFAULT_PERIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNhiExtendPatient() throws Exception {
        // Get the nhiExtendPatient
        restNhiExtendPatientMockMvc.perform(get("/api/nhi-extend-patients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhiExtendPatient() throws Exception {
        // Initialize the database
        nhiExtendPatientService.save(nhiExtendPatient);

        int databaseSizeBeforeUpdate = nhiExtendPatientRepository.findAll().size();

        // Update the nhiExtendPatient
        NhiExtendPatient updatedNhiExtendPatient = nhiExtendPatientRepository.findById(nhiExtendPatient.getId()).get();
        // Disconnect from session so that the updates on updatedNhiExtendPatient are not directly saved in db
        em.detach(updatedNhiExtendPatient);
        updatedNhiExtendPatient
            .cardNumber(UPDATED_CARD_NUMBER)
            .cardAnnotation(UPDATED_CARD_ANNOTATION)
            .cardValidDate(UPDATED_CARD_VALID_DATE)
            .cardIssueDate(UPDATED_CARD_ISSUE_DATE)
            .nhiIdentity(UPDATED_NHI_IDENTITY)
            .availableTimes(UPDATED_AVAILABLE_TIMES)
            .scaling(UPDATED_SCALING)
            .fluoride(UPDATED_FLUORIDE)
            .perio(UPDATED_PERIO);

        restNhiExtendPatientMockMvc.perform(put("/api/nhi-extend-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNhiExtendPatient)))
            .andExpect(status().isOk());

        // Validate the NhiExtendPatient in the database
        List<NhiExtendPatient> nhiExtendPatientList = nhiExtendPatientRepository.findAll();
        assertThat(nhiExtendPatientList).hasSize(databaseSizeBeforeUpdate);
        NhiExtendPatient testNhiExtendPatient = nhiExtendPatientList.get(nhiExtendPatientList.size() - 1);
        assertThat(testNhiExtendPatient.getCardNumber()).isEqualTo(UPDATED_CARD_NUMBER);
        assertThat(testNhiExtendPatient.getCardAnnotation()).isEqualTo(UPDATED_CARD_ANNOTATION);
        assertThat(testNhiExtendPatient.getCardValidDate()).isEqualTo(UPDATED_CARD_VALID_DATE);
        assertThat(testNhiExtendPatient.getCardIssueDate()).isEqualTo(UPDATED_CARD_ISSUE_DATE);
        assertThat(testNhiExtendPatient.getNhiIdentity()).isEqualTo(UPDATED_NHI_IDENTITY);
        assertThat(testNhiExtendPatient.getAvailableTimes()).isEqualTo(UPDATED_AVAILABLE_TIMES);
        assertThat(testNhiExtendPatient.getScaling()).isEqualTo(UPDATED_SCALING);
        assertThat(testNhiExtendPatient.getFluoride()).isEqualTo(UPDATED_FLUORIDE);
        assertThat(testNhiExtendPatient.getPerio()).isEqualTo(UPDATED_PERIO);
    }

    @Test
    @Transactional
    public void updateNonExistingNhiExtendPatient() throws Exception {
        int databaseSizeBeforeUpdate = nhiExtendPatientRepository.findAll().size();

        // Create the NhiExtendPatient

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhiExtendPatientMockMvc.perform(put("/api/nhi-extend-patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiExtendPatient)))
            .andExpect(status().isBadRequest());

        // Validate the NhiExtendPatient in the database
        List<NhiExtendPatient> nhiExtendPatientList = nhiExtendPatientRepository.findAll();
        assertThat(nhiExtendPatientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhiExtendPatient() throws Exception {
        // Initialize the database
        nhiExtendPatientService.save(nhiExtendPatient);

        int databaseSizeBeforeDelete = nhiExtendPatientRepository.findAll().size();

        // Get the nhiExtendPatient
        restNhiExtendPatientMockMvc.perform(delete("/api/nhi-extend-patients/{id}", nhiExtendPatient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhiExtendPatient> nhiExtendPatientList = nhiExtendPatientRepository.findAll();
        assertThat(nhiExtendPatientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhiExtendPatient.class);
        NhiExtendPatient nhiExtendPatient1 = new NhiExtendPatient();
        nhiExtendPatient1.setId(1L);
        NhiExtendPatient nhiExtendPatient2 = new NhiExtendPatient();
        nhiExtendPatient2.setId(nhiExtendPatient1.getId());
        assertThat(nhiExtendPatient1).isEqualTo(nhiExtendPatient2);
        nhiExtendPatient2.setId(2L);
        assertThat(nhiExtendPatient1).isNotEqualTo(nhiExtendPatient2);
        nhiExtendPatient1.setId(null);
        assertThat(nhiExtendPatient1).isNotEqualTo(nhiExtendPatient2);
    }
}
