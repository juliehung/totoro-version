package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.NHIProcedure;
import io.dentall.totoro.repository.NHIProcedureRepository;
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
 * Test class for the NHIProcedureResource REST controller.
 *
 * @see NHIProcedureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NHIProcedureResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_POINT = 1;
    private static final Integer UPDATED_POINT = 2;

    private static final String DEFAULT_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_NAME = "BBBBBBBBBB";

    @Autowired
    private NHIProcedureRepository nhiProcedureRepository;

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

    private MockMvc restNHIProcedureMockMvc;

    private NHIProcedure nhiProcedure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NHIProcedureResource nhiProcedureResource = new NHIProcedureResource(nhiProcedureRepository);
        this.restNHIProcedureMockMvc = MockMvcBuilders.standaloneSetup(nhiProcedureResource)
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
    public static NHIProcedure createEntity(EntityManager em) {
        NHIProcedure nhiProcedure = new NHIProcedure()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .point(DEFAULT_POINT)
            .englishName(DEFAULT_ENGLISH_NAME);
        return nhiProcedure;
    }

    @Before
    public void initTest() {
        nhiProcedure = createEntity(em);
    }

    @Test
    @Transactional
    public void createNHIProcedure() throws Exception {
        int databaseSizeBeforeCreate = nhiProcedureRepository.findAll().size();

        // Create the NHIProcedure
        restNHIProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedure)))
            .andExpect(status().isCreated());

        // Validate the NHIProcedure in the database
        List<NHIProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeCreate + 1);
        NHIProcedure testNHIProcedure = nhiProcedureList.get(nhiProcedureList.size() - 1);
        assertThat(testNHIProcedure.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNHIProcedure.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNHIProcedure.getPoint()).isEqualTo(DEFAULT_POINT);
        assertThat(testNHIProcedure.getEnglishName()).isEqualTo(DEFAULT_ENGLISH_NAME);
    }

    @Test
    @Transactional
    public void createNHIProcedureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhiProcedureRepository.findAll().size();

        // Create the NHIProcedure with an existing ID
        nhiProcedure.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNHIProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedure)))
            .andExpect(status().isBadRequest());

        // Validate the NHIProcedure in the database
        List<NHIProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhiProcedureRepository.findAll().size();
        // set the field null
        nhiProcedure.setCode(null);

        // Create the NHIProcedure, which fails.

        restNHIProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedure)))
            .andExpect(status().isBadRequest());

        List<NHIProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhiProcedureRepository.findAll().size();
        // set the field null
        nhiProcedure.setName(null);

        // Create the NHIProcedure, which fails.

        restNHIProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedure)))
            .andExpect(status().isBadRequest());

        List<NHIProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPointIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhiProcedureRepository.findAll().size();
        // set the field null
        nhiProcedure.setPoint(null);

        // Create the NHIProcedure, which fails.

        restNHIProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedure)))
            .andExpect(status().isBadRequest());

        List<NHIProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNHIProcedures() throws Exception {
        // Initialize the database
        nhiProcedureRepository.saveAndFlush(nhiProcedure);

        // Get all the nhiProcedureList
        restNHIProcedureMockMvc.perform(get("/api/nhi-procedures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiProcedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].point").value(hasItem(DEFAULT_POINT)))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getNHIProcedure() throws Exception {
        // Initialize the database
        nhiProcedureRepository.saveAndFlush(nhiProcedure);

        // Get the nhiProcedure
        restNHIProcedureMockMvc.perform(get("/api/nhi-procedures/{id}", nhiProcedure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhiProcedure.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.point").value(DEFAULT_POINT))
            .andExpect(jsonPath("$.englishName").value(DEFAULT_ENGLISH_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNHIProcedure() throws Exception {
        // Get the nhiProcedure
        restNHIProcedureMockMvc.perform(get("/api/nhi-procedures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNHIProcedure() throws Exception {
        // Initialize the database
        nhiProcedureRepository.saveAndFlush(nhiProcedure);

        int databaseSizeBeforeUpdate = nhiProcedureRepository.findAll().size();

        // Update the nhiProcedure
        NHIProcedure updatedNHIProcedure = nhiProcedureRepository.findById(nhiProcedure.getId()).get();
        // Disconnect from session so that the updates on updatedNHIProcedure are not directly saved in db
        em.detach(updatedNHIProcedure);
        updatedNHIProcedure
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .point(UPDATED_POINT)
            .englishName(UPDATED_ENGLISH_NAME);

        restNHIProcedureMockMvc.perform(put("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNHIProcedure)))
            .andExpect(status().isOk());

        // Validate the NHIProcedure in the database
        List<NHIProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeUpdate);
        NHIProcedure testNHIProcedure = nhiProcedureList.get(nhiProcedureList.size() - 1);
        assertThat(testNHIProcedure.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNHIProcedure.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNHIProcedure.getPoint()).isEqualTo(UPDATED_POINT);
        assertThat(testNHIProcedure.getEnglishName()).isEqualTo(UPDATED_ENGLISH_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingNHIProcedure() throws Exception {
        int databaseSizeBeforeUpdate = nhiProcedureRepository.findAll().size();

        // Create the NHIProcedure

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNHIProcedureMockMvc.perform(put("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedure)))
            .andExpect(status().isBadRequest());

        // Validate the NHIProcedure in the database
        List<NHIProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNHIProcedure() throws Exception {
        // Initialize the database
        nhiProcedureRepository.saveAndFlush(nhiProcedure);

        int databaseSizeBeforeDelete = nhiProcedureRepository.findAll().size();

        // Get the nhiProcedure
        restNHIProcedureMockMvc.perform(delete("/api/nhi-procedures/{id}", nhiProcedure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NHIProcedure> nhiProcedureList = nhiProcedureRepository.findAll();
        assertThat(nhiProcedureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NHIProcedure.class);
        NHIProcedure nhiProcedure1 = new NHIProcedure();
        nhiProcedure1.setId(1L);
        NHIProcedure nhiProcedure2 = new NHIProcedure();
        nhiProcedure2.setId(nhiProcedure1.getId());
        assertThat(nhiProcedure1).isEqualTo(nhiProcedure2);
        nhiProcedure2.setId(2L);
        assertThat(nhiProcedure1).isNotEqualTo(nhiProcedure2);
        nhiProcedure1.setId(null);
        assertThat(nhiProcedure1).isNotEqualTo(nhiProcedure2);
    }
}
