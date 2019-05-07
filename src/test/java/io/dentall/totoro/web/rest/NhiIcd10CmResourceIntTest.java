package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.NhiIcd10Cm;
import io.dentall.totoro.repository.NhiIcd10CmRepository;
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
 * Test class for the NhiIcd10CmResource REST controller.
 *
 * @see NhiIcd10CmResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiIcd10CmResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_NAME = "BBBBBBBBBB";

    @Autowired
    private NhiIcd10CmRepository nhiIcd10CmRepository;

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

    private MockMvc restNhiIcd10CmMockMvc;

    private NhiIcd10Cm nhiIcd10Cm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhiIcd10CmResource nhiIcd10CmResource = new NhiIcd10CmResource(nhiIcd10CmRepository);
        this.restNhiIcd10CmMockMvc = MockMvcBuilders.standaloneSetup(nhiIcd10CmResource)
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
    public static NhiIcd10Cm createEntity(EntityManager em) {
        NhiIcd10Cm nhiIcd10Cm = new NhiIcd10Cm()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .englishName(DEFAULT_ENGLISH_NAME);
        return nhiIcd10Cm;
    }

    @Before
    public void initTest() {
        nhiIcd10Cm = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhiIcd10Cm() throws Exception {
        int databaseSizeBeforeCreate = nhiIcd10CmRepository.findAll().size();

        // Create the NhiIcd10Cm
        restNhiIcd10CmMockMvc.perform(post("/api/nhi-icd-10-cms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiIcd10Cm)))
            .andExpect(status().isCreated());

        // Validate the NhiIcd10Cm in the database
        List<NhiIcd10Cm> nhiIcd10CmList = nhiIcd10CmRepository.findAll();
        assertThat(nhiIcd10CmList).hasSize(databaseSizeBeforeCreate + 1);
        NhiIcd10Cm testNhiIcd10Cm = nhiIcd10CmList.get(nhiIcd10CmList.size() - 1);
        assertThat(testNhiIcd10Cm.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNhiIcd10Cm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNhiIcd10Cm.getEnglishName()).isEqualTo(DEFAULT_ENGLISH_NAME);
    }

    @Test
    @Transactional
    public void createNhiIcd10CmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhiIcd10CmRepository.findAll().size();

        // Create the NhiIcd10Cm with an existing ID
        nhiIcd10Cm.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhiIcd10CmMockMvc.perform(post("/api/nhi-icd-10-cms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiIcd10Cm)))
            .andExpect(status().isBadRequest());

        // Validate the NhiIcd10Cm in the database
        List<NhiIcd10Cm> nhiIcd10CmList = nhiIcd10CmRepository.findAll();
        assertThat(nhiIcd10CmList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhiIcd10CmRepository.findAll().size();
        // set the field null
        nhiIcd10Cm.setCode(null);

        // Create the NhiIcd10Cm, which fails.

        restNhiIcd10CmMockMvc.perform(post("/api/nhi-icd-10-cms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiIcd10Cm)))
            .andExpect(status().isBadRequest());

        List<NhiIcd10Cm> nhiIcd10CmList = nhiIcd10CmRepository.findAll();
        assertThat(nhiIcd10CmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNhiIcd10Cms() throws Exception {
        // Initialize the database
        nhiIcd10CmRepository.saveAndFlush(nhiIcd10Cm);

        // Get all the nhiIcd10CmList
        restNhiIcd10CmMockMvc.perform(get("/api/nhi-icd-10-cms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiIcd10Cm.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getNhiIcd10Cm() throws Exception {
        // Initialize the database
        nhiIcd10CmRepository.saveAndFlush(nhiIcd10Cm);

        // Get the nhiIcd10Cm
        restNhiIcd10CmMockMvc.perform(get("/api/nhi-icd-10-cms/{id}", nhiIcd10Cm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhiIcd10Cm.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.englishName").value(DEFAULT_ENGLISH_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNhiIcd10Cm() throws Exception {
        // Get the nhiIcd10Cm
        restNhiIcd10CmMockMvc.perform(get("/api/nhi-icd-10-cms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhiIcd10Cm() throws Exception {
        // Initialize the database
        nhiIcd10CmRepository.saveAndFlush(nhiIcd10Cm);

        int databaseSizeBeforeUpdate = nhiIcd10CmRepository.findAll().size();

        // Update the nhiIcd10Cm
        NhiIcd10Cm updatedNhiIcd10Cm = nhiIcd10CmRepository.findById(nhiIcd10Cm.getId()).get();
        // Disconnect from session so that the updates on updatedNhiIcd10Cm are not directly saved in db
        em.detach(updatedNhiIcd10Cm);
        updatedNhiIcd10Cm
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .englishName(UPDATED_ENGLISH_NAME);

        restNhiIcd10CmMockMvc.perform(put("/api/nhi-icd-10-cms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNhiIcd10Cm)))
            .andExpect(status().isOk());

        // Validate the NhiIcd10Cm in the database
        List<NhiIcd10Cm> nhiIcd10CmList = nhiIcd10CmRepository.findAll();
        assertThat(nhiIcd10CmList).hasSize(databaseSizeBeforeUpdate);
        NhiIcd10Cm testNhiIcd10Cm = nhiIcd10CmList.get(nhiIcd10CmList.size() - 1);
        assertThat(testNhiIcd10Cm.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNhiIcd10Cm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNhiIcd10Cm.getEnglishName()).isEqualTo(UPDATED_ENGLISH_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingNhiIcd10Cm() throws Exception {
        int databaseSizeBeforeUpdate = nhiIcd10CmRepository.findAll().size();

        // Create the NhiIcd10Cm

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhiIcd10CmMockMvc.perform(put("/api/nhi-icd-10-cms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiIcd10Cm)))
            .andExpect(status().isBadRequest());

        // Validate the NhiIcd10Cm in the database
        List<NhiIcd10Cm> nhiIcd10CmList = nhiIcd10CmRepository.findAll();
        assertThat(nhiIcd10CmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhiIcd10Cm() throws Exception {
        // Initialize the database
        nhiIcd10CmRepository.saveAndFlush(nhiIcd10Cm);

        int databaseSizeBeforeDelete = nhiIcd10CmRepository.findAll().size();

        // Get the nhiIcd10Cm
        restNhiIcd10CmMockMvc.perform(delete("/api/nhi-icd-10-cms/{id}", nhiIcd10Cm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhiIcd10Cm> nhiIcd10CmList = nhiIcd10CmRepository.findAll();
        assertThat(nhiIcd10CmList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhiIcd10Cm.class);
        NhiIcd10Cm nhiIcd10Cm1 = new NhiIcd10Cm();
        nhiIcd10Cm1.setId(1L);
        NhiIcd10Cm nhiIcd10Cm2 = new NhiIcd10Cm();
        nhiIcd10Cm2.setId(nhiIcd10Cm1.getId());
        assertThat(nhiIcd10Cm1).isEqualTo(nhiIcd10Cm2);
        nhiIcd10Cm2.setId(2L);
        assertThat(nhiIcd10Cm1).isNotEqualTo(nhiIcd10Cm2);
        nhiIcd10Cm1.setId(null);
        assertThat(nhiIcd10Cm1).isNotEqualTo(nhiIcd10Cm2);
    }
}
