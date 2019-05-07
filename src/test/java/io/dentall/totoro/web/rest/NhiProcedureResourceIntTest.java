package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.NhiProcedure;
import io.dentall.totoro.repository.NhiProcedureRepository;
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
 * Test class for the NhiProcedureResource REST controller.
 *
 * @see NhiProcedureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiProcedureResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_POINT = 1;
    private static final Integer UPDATED_POINT = 2;

    private static final String DEFAULT_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_NAME = "BBBBBBBBBB";

    private static final Long DEFAULT_DEFAULT_ICD_10_CM_ID = 1L;
    private static final Long UPDATED_DEFAULT_ICD_10_CM_ID = 2L;

    @Autowired
    private NhiProcedureRepository nhiProcedureRepository;

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

    private MockMvc restNhiProcedureMockMvc;

    private NhiProcedure nhiProcedure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhiProcedureResource nhiProcedureResource = new NhiProcedureResource(nhiProcedureRepository);
        this.restNhiProcedureMockMvc = MockMvcBuilders.standaloneSetup(nhiProcedureResource)
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
    public static NhiProcedure createEntity(EntityManager em) {
        NhiProcedure nhiProcedure = new NhiProcedure()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .point(DEFAULT_POINT)
            .englishName(DEFAULT_ENGLISH_NAME)
            .defaultIcd10CmId(DEFAULT_DEFAULT_ICD_10_CM_ID);
        return nhiProcedure;
    }

    @Before
    public void initTest() {
        nhiProcedure = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhiProcedure() throws Exception {
        int databaseSizeBeforeCreate = nhiProcedureRepository.findAll().size();

        // Create the NhiProcedure
        restNhiProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedure)))
            .andExpect(status().isCreated());

        // Validate the NhiProcedure in the database
        List<NhiProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeCreate + 1);
        NhiProcedure testNhiProcedure = nhiProcedureList.get(nhiProcedureList.size() - 1);
        assertThat(testNhiProcedure.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNhiProcedure.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNhiProcedure.getPoint()).isEqualTo(DEFAULT_POINT);
        assertThat(testNhiProcedure.getEnglishName()).isEqualTo(DEFAULT_ENGLISH_NAME);
        assertThat(testNhiProcedure.getDefaultIcd10CmId()).isEqualTo(DEFAULT_DEFAULT_ICD_10_CM_ID);
    }

    @Test
    @Transactional
    public void createNhiProcedureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhiProcedureRepository.findAll().size();

        // Create the NhiProcedure with an existing ID
        nhiProcedure.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhiProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedure)))
            .andExpect(status().isBadRequest());

        // Validate the NhiProcedure in the database
        List<NhiProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhiProcedureRepository.findAll().size();
        // set the field null
        nhiProcedure.setCode(null);

        // Create the NhiProcedure, which fails.

        restNhiProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedure)))
            .andExpect(status().isBadRequest());

        List<NhiProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhiProcedureRepository.findAll().size();
        // set the field null
        nhiProcedure.setName(null);

        // Create the NhiProcedure, which fails.

        restNhiProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedure)))
            .andExpect(status().isBadRequest());

        List<NhiProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPointIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhiProcedureRepository.findAll().size();
        // set the field null
        nhiProcedure.setPoint(null);

        // Create the NhiProcedure, which fails.

        restNhiProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedure)))
            .andExpect(status().isBadRequest());

        List<NhiProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNhiProcedures() throws Exception {
        // Initialize the database
        nhiProcedureRepository.saveAndFlush(nhiProcedure);

        // Get all the nhiProcedureList
        restNhiProcedureMockMvc.perform(get("/api/nhi-procedures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiProcedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].point").value(hasItem(DEFAULT_POINT)))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME.toString())))
            .andExpect(jsonPath("$.[*].defaultIcd10CmId").value(hasItem(DEFAULT_DEFAULT_ICD_10_CM_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getNhiProcedure() throws Exception {
        // Initialize the database
        nhiProcedureRepository.saveAndFlush(nhiProcedure);

        // Get the nhiProcedure
        restNhiProcedureMockMvc.perform(get("/api/nhi-procedures/{id}", nhiProcedure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhiProcedure.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.point").value(DEFAULT_POINT))
            .andExpect(jsonPath("$.englishName").value(DEFAULT_ENGLISH_NAME.toString()))
            .andExpect(jsonPath("$.defaultIcd10CmId").value(DEFAULT_DEFAULT_ICD_10_CM_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingNhiProcedure() throws Exception {
        // Get the nhiProcedure
        restNhiProcedureMockMvc.perform(get("/api/nhi-procedures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhiProcedure() throws Exception {
        // Initialize the database
        nhiProcedureRepository.saveAndFlush(nhiProcedure);

        int databaseSizeBeforeUpdate = nhiProcedureRepository.findAll().size();

        // Update the nhiProcedure
        NhiProcedure updatedNhiProcedure = nhiProcedureRepository.findById(nhiProcedure.getId()).get();
        // Disconnect from session so that the updates on updatedNhiProcedure are not directly saved in db
        em.detach(updatedNhiProcedure);
        updatedNhiProcedure
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .point(UPDATED_POINT)
            .englishName(UPDATED_ENGLISH_NAME)
            .defaultIcd10CmId(UPDATED_DEFAULT_ICD_10_CM_ID);

        restNhiProcedureMockMvc.perform(put("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNhiProcedure)))
            .andExpect(status().isOk());

        // Validate the NhiProcedure in the database
        List<NhiProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeUpdate);
        NhiProcedure testNhiProcedure = nhiProcedureList.get(nhiProcedureList.size() - 1);
        assertThat(testNhiProcedure.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNhiProcedure.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNhiProcedure.getPoint()).isEqualTo(UPDATED_POINT);
        assertThat(testNhiProcedure.getEnglishName()).isEqualTo(UPDATED_ENGLISH_NAME);
        assertThat(testNhiProcedure.getDefaultIcd10CmId()).isEqualTo(UPDATED_DEFAULT_ICD_10_CM_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingNhiProcedure() throws Exception {
        int databaseSizeBeforeUpdate = nhiProcedureRepository.findAll().size();

        // Create the NhiProcedure

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhiProcedureMockMvc.perform(put("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedure)))
            .andExpect(status().isBadRequest());

        // Validate the NhiProcedure in the database
        List<NhiProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhiProcedure() throws Exception {
        // Initialize the database
        nhiProcedureRepository.saveAndFlush(nhiProcedure);

        int databaseSizeBeforeDelete = nhiProcedureRepository.findAll().size();

        // Get the nhiProcedure
        restNhiProcedureMockMvc.perform(delete("/api/nhi-procedures/{id}", nhiProcedure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhiProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhiProcedure.class);
        NhiProcedure nhiProcedure1 = new NhiProcedure();
        nhiProcedure1.setId(1L);
        NhiProcedure nhiProcedure2 = new NhiProcedure();
        nhiProcedure2.setId(nhiProcedure1.getId());
        assertThat(nhiProcedure1).isEqualTo(nhiProcedure2);
        nhiProcedure2.setId(2L);
        assertThat(nhiProcedure1).isNotEqualTo(nhiProcedure2);
        nhiProcedure1.setId(null);
        assertThat(nhiProcedure1).isNotEqualTo(nhiProcedure2);
    }
}
