package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.domain.NHICategory;
import io.dentall.totoro.domain.NHIProcedure;
import io.dentall.totoro.repository.NHICategoryRepository;
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

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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

    private static final LocalDate DEFAULT_START = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_NAME = "BBBBBBBBBB";

    @Autowired
    private NHIProcedureRepository nHIProcedureRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private NHICategoryRepository nHICategoryRepository;

    private MockMvc restNHIProcedureMockMvc;

    private NHIProcedure nHIProcedure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NHIProcedureResource nHIProcedureResource = new NHIProcedureResource(nHIProcedureRepository, nHICategoryRepository);
        this.restNHIProcedureMockMvc = MockMvcBuilders.standaloneSetup(nHIProcedureResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NHIProcedure createEntity(EntityManager em) {
        NHIProcedure nHIProcedure = new NHIProcedure()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .point(DEFAULT_POINT)
            .start(DEFAULT_START)
            .end(DEFAULT_END)
            .englishName(DEFAULT_ENGLISH_NAME);
        return nHIProcedure;
    }

    @Before
    public void initTest() {
        nHIProcedure = createEntity(em);
    }

    @Test
    @Transactional
    public void createNHIProcedure() throws Exception {
        int databaseSizeBeforeCreate = nHIProcedureRepository.findAll().size();

        // Create the NHIProcedure
        restNHIProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHIProcedure)))
            .andExpect(status().isCreated());

        // Validate the NHIProcedure in the database
        List<NHIProcedure> nHIProcedureList = nHIProcedureRepository.findAll();
        assertThat(nHIProcedureList).hasSize(databaseSizeBeforeCreate + 1);
        NHIProcedure testNHIProcedure = nHIProcedureList.get(nHIProcedureList.size() - 1);
        assertThat(testNHIProcedure.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNHIProcedure.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNHIProcedure.getPoint()).isEqualTo(DEFAULT_POINT);
        assertThat(testNHIProcedure.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testNHIProcedure.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testNHIProcedure.getEnglishName()).isEqualTo(DEFAULT_ENGLISH_NAME);
    }

    @Test
    @Transactional
    public void createNHIProcedureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nHIProcedureRepository.findAll().size();

        // Create the NHIProcedure with an existing ID
        nHIProcedure.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNHIProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHIProcedure)))
            .andExpect(status().isBadRequest());

        // Validate the NHIProcedure in the database
        List<NHIProcedure> nHIProcedureList = nHIProcedureRepository.findAll();
        assertThat(nHIProcedureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nHIProcedureRepository.findAll().size();
        // set the field null
        nHIProcedure.setCode(null);

        // Create the NHIProcedure, which fails.

        restNHIProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHIProcedure)))
            .andExpect(status().isBadRequest());

        List<NHIProcedure> nHIProcedureList = nHIProcedureRepository.findAll();
        assertThat(nHIProcedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = nHIProcedureRepository.findAll().size();
        // set the field null
        nHIProcedure.setName(null);

        // Create the NHIProcedure, which fails.

        restNHIProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHIProcedure)))
            .andExpect(status().isBadRequest());

        List<NHIProcedure> nHIProcedureList = nHIProcedureRepository.findAll();
        assertThat(nHIProcedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPointIsRequired() throws Exception {
        int databaseSizeBeforeTest = nHIProcedureRepository.findAll().size();
        // set the field null
        nHIProcedure.setPoint(null);

        // Create the NHIProcedure, which fails.

        restNHIProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHIProcedure)))
            .andExpect(status().isBadRequest());

        List<NHIProcedure> nHIProcedureList = nHIProcedureRepository.findAll();
        assertThat(nHIProcedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = nHIProcedureRepository.findAll().size();
        // set the field null
        nHIProcedure.setStart(null);

        // Create the NHIProcedure, which fails.

        restNHIProcedureMockMvc.perform(post("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHIProcedure)))
            .andExpect(status().isBadRequest());

        List<NHIProcedure> nHIProcedureList = nHIProcedureRepository.findAll();
        assertThat(nHIProcedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNHIProcedures() throws Exception {
        // Initialize the database
        nHIProcedureRepository.saveAndFlush(nHIProcedure);

        // Get all the nHIProcedureList
        restNHIProcedureMockMvc.perform(get("/api/nhi-procedures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nHIProcedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].point").value(hasItem(DEFAULT_POINT)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getNHIProcedure() throws Exception {
        // Initialize the database
        nHIProcedureRepository.saveAndFlush(nHIProcedure);

        // Get the nHIProcedure
        restNHIProcedureMockMvc.perform(get("/api/nhi-procedures/{id}", nHIProcedure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nHIProcedure.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.point").value(DEFAULT_POINT))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.englishName").value(DEFAULT_ENGLISH_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNHIProcedure() throws Exception {
        // Get the nHIProcedure
        restNHIProcedureMockMvc.perform(get("/api/nhi-procedures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNHIProcedure() throws Exception {
        // Initialize the database
        nHIProcedureRepository.saveAndFlush(nHIProcedure);

        int databaseSizeBeforeUpdate = nHIProcedureRepository.findAll().size();

        // Update the nHIProcedure
        NHIProcedure updatedNHIProcedure = nHIProcedureRepository.findById(nHIProcedure.getId()).get();
        // Disconnect from session so that the updates on updatedNHIProcedure are not directly saved in db
        em.detach(updatedNHIProcedure);
        updatedNHIProcedure
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .point(UPDATED_POINT)
            .start(UPDATED_START)
            .end(UPDATED_END)
            .englishName(UPDATED_ENGLISH_NAME);

        restNHIProcedureMockMvc.perform(put("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNHIProcedure)))
            .andExpect(status().isOk());

        // Validate the NHIProcedure in the database
        List<NHIProcedure> nHIProcedureList = nHIProcedureRepository.findAll();
        assertThat(nHIProcedureList).hasSize(databaseSizeBeforeUpdate);
        NHIProcedure testNHIProcedure = nHIProcedureList.get(nHIProcedureList.size() - 1);
        assertThat(testNHIProcedure.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNHIProcedure.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNHIProcedure.getPoint()).isEqualTo(UPDATED_POINT);
        assertThat(testNHIProcedure.getStart()).isEqualTo(UPDATED_START);
        assertThat(testNHIProcedure.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testNHIProcedure.getEnglishName()).isEqualTo(UPDATED_ENGLISH_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingNHIProcedure() throws Exception {
        int databaseSizeBeforeUpdate = nHIProcedureRepository.findAll().size();

        // Create the NHIProcedure

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNHIProcedureMockMvc.perform(put("/api/nhi-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHIProcedure)))
            .andExpect(status().isBadRequest());

        // Validate the NHIProcedure in the database
        List<NHIProcedure> nHIProcedureList = nHIProcedureRepository.findAll();
        assertThat(nHIProcedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNHIProcedure() throws Exception {
        // Initialize the database
        nHIProcedureRepository.saveAndFlush(nHIProcedure);

        int databaseSizeBeforeDelete = nHIProcedureRepository.findAll().size();

        // Get the nHIProcedure
        restNHIProcedureMockMvc.perform(delete("/api/nhi-procedures/{id}", nHIProcedure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NHIProcedure> nHIProcedureList = nHIProcedureRepository.findAll();
        assertThat(nHIProcedureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NHIProcedure.class);
        NHIProcedure nHIProcedure1 = new NHIProcedure();
        nHIProcedure1.setId(1L);
        NHIProcedure nHIProcedure2 = new NHIProcedure();
        nHIProcedure2.setId(nHIProcedure1.getId());
        assertThat(nHIProcedure1).isEqualTo(nHIProcedure2);
        nHIProcedure2.setId(2L);
        assertThat(nHIProcedure1).isNotEqualTo(nHIProcedure2);
        nHIProcedure1.setId(null);
        assertThat(nHIProcedure1).isNotEqualTo(nHIProcedure2);
    }

    @Test
    @Transactional
    public void getAllNHIProceduresByCategories() throws Exception {
        NHICategory category1 = NHICategoryResourceIntTest.createEntity(em);
        nHICategoryRepository.save(category1.name("category1").codes(nHIProcedure.getCode()));
        NHICategory category2 = NHICategoryResourceIntTest.createEntity(em);
        nHICategoryRepository.save(category2.name("category2").codes(nHIProcedure.getCode()));
        nHIProcedure.setEnd(null);

        // Initialize the database
        nHIProcedureRepository.saveAndFlush(nHIProcedure);

        // Get all the nHIProcedureList by categories
        Object jsonNull = null;
        restNHIProcedureMockMvc.perform(get("/api/nhi-procedures?categories={category1},{category2}", category1.getName().toUpperCase(), category2.getName()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nHIProcedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].point").value(hasItem(DEFAULT_POINT)))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(jsonNull)))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME.toString())));
    }
}
