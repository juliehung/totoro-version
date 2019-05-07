package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.NhiIcd9Cm;
import io.dentall.totoro.repository.NhiIcd9CmRepository;
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
 * Test class for the NhiIcd9CmResource REST controller.
 *
 * @see NhiIcd9CmResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiIcd9CmResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_NAME = "BBBBBBBBBB";

    @Autowired
    private NhiIcd9CmRepository nhiIcd9CmRepository;

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

    private MockMvc restNhiIcd9CmMockMvc;

    private NhiIcd9Cm nhiIcd9Cm;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhiIcd9CmResource nhiIcd9CmResource = new NhiIcd9CmResource(nhiIcd9CmRepository);
        this.restNhiIcd9CmMockMvc = MockMvcBuilders.standaloneSetup(nhiIcd9CmResource)
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
    public static NhiIcd9Cm createEntity(EntityManager em) {
        NhiIcd9Cm nhiIcd9Cm = new NhiIcd9Cm()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .englishName(DEFAULT_ENGLISH_NAME);
        return nhiIcd9Cm;
    }

    @Before
    public void initTest() {
        nhiIcd9Cm = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhiIcd9Cm() throws Exception {
        int databaseSizeBeforeCreate = nhiIcd9CmRepository.findAll().size();

        // Create the NhiIcd9Cm
        restNhiIcd9CmMockMvc.perform(post("/api/nhi-icd-9-cms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiIcd9Cm)))
            .andExpect(status().isCreated());

        // Validate the NhiIcd9Cm in the database
        List<NhiIcd9Cm> nhiIcd9CmList = nhiIcd9CmRepository.findAll();
        assertThat(nhiIcd9CmList).hasSize(databaseSizeBeforeCreate + 1);
        NhiIcd9Cm testNhiIcd9Cm = nhiIcd9CmList.get(nhiIcd9CmList.size() - 1);
        assertThat(testNhiIcd9Cm.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNhiIcd9Cm.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNhiIcd9Cm.getEnglishName()).isEqualTo(DEFAULT_ENGLISH_NAME);
    }

    @Test
    @Transactional
    public void createNhiIcd9CmWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhiIcd9CmRepository.findAll().size();

        // Create the NhiIcd9Cm with an existing ID
        nhiIcd9Cm.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhiIcd9CmMockMvc.perform(post("/api/nhi-icd-9-cms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiIcd9Cm)))
            .andExpect(status().isBadRequest());

        // Validate the NhiIcd9Cm in the database
        List<NhiIcd9Cm> nhiIcd9CmList = nhiIcd9CmRepository.findAll();
        assertThat(nhiIcd9CmList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhiIcd9CmRepository.findAll().size();
        // set the field null
        nhiIcd9Cm.setCode(null);

        // Create the NhiIcd9Cm, which fails.

        restNhiIcd9CmMockMvc.perform(post("/api/nhi-icd-9-cms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiIcd9Cm)))
            .andExpect(status().isBadRequest());

        List<NhiIcd9Cm> nhiIcd9CmList = nhiIcd9CmRepository.findAll();
        assertThat(nhiIcd9CmList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNhiIcd9Cms() throws Exception {
        // Initialize the database
        nhiIcd9CmRepository.saveAndFlush(nhiIcd9Cm);

        // Get all the nhiIcd9CmList
        restNhiIcd9CmMockMvc.perform(get("/api/nhi-icd-9-cms?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiIcd9Cm.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getNhiIcd9Cm() throws Exception {
        // Initialize the database
        nhiIcd9CmRepository.saveAndFlush(nhiIcd9Cm);

        // Get the nhiIcd9Cm
        restNhiIcd9CmMockMvc.perform(get("/api/nhi-icd-9-cms/{id}", nhiIcd9Cm.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhiIcd9Cm.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.englishName").value(DEFAULT_ENGLISH_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNhiIcd9Cm() throws Exception {
        // Get the nhiIcd9Cm
        restNhiIcd9CmMockMvc.perform(get("/api/nhi-icd-9-cms/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhiIcd9Cm() throws Exception {
        // Initialize the database
        nhiIcd9CmRepository.saveAndFlush(nhiIcd9Cm);

        int databaseSizeBeforeUpdate = nhiIcd9CmRepository.findAll().size();

        // Update the nhiIcd9Cm
        NhiIcd9Cm updatedNhiIcd9Cm = nhiIcd9CmRepository.findById(nhiIcd9Cm.getId()).get();
        // Disconnect from session so that the updates on updatedNhiIcd9Cm are not directly saved in db
        em.detach(updatedNhiIcd9Cm);
        updatedNhiIcd9Cm
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .englishName(UPDATED_ENGLISH_NAME);

        restNhiIcd9CmMockMvc.perform(put("/api/nhi-icd-9-cms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNhiIcd9Cm)))
            .andExpect(status().isOk());

        // Validate the NhiIcd9Cm in the database
        List<NhiIcd9Cm> nhiIcd9CmList = nhiIcd9CmRepository.findAll();
        assertThat(nhiIcd9CmList).hasSize(databaseSizeBeforeUpdate);
        NhiIcd9Cm testNhiIcd9Cm = nhiIcd9CmList.get(nhiIcd9CmList.size() - 1);
        assertThat(testNhiIcd9Cm.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNhiIcd9Cm.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNhiIcd9Cm.getEnglishName()).isEqualTo(UPDATED_ENGLISH_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingNhiIcd9Cm() throws Exception {
        int databaseSizeBeforeUpdate = nhiIcd9CmRepository.findAll().size();

        // Create the NhiIcd9Cm

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhiIcd9CmMockMvc.perform(put("/api/nhi-icd-9-cms")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiIcd9Cm)))
            .andExpect(status().isBadRequest());

        // Validate the NhiIcd9Cm in the database
        List<NhiIcd9Cm> nhiIcd9CmList = nhiIcd9CmRepository.findAll();
        assertThat(nhiIcd9CmList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhiIcd9Cm() throws Exception {
        // Initialize the database
        nhiIcd9CmRepository.saveAndFlush(nhiIcd9Cm);

        int databaseSizeBeforeDelete = nhiIcd9CmRepository.findAll().size();

        // Get the nhiIcd9Cm
        restNhiIcd9CmMockMvc.perform(delete("/api/nhi-icd-9-cms/{id}", nhiIcd9Cm.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhiIcd9Cm> nhiIcd9CmList = nhiIcd9CmRepository.findAll();
        assertThat(nhiIcd9CmList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhiIcd9Cm.class);
        NhiIcd9Cm nhiIcd9Cm1 = new NhiIcd9Cm();
        nhiIcd9Cm1.setId(1L);
        NhiIcd9Cm nhiIcd9Cm2 = new NhiIcd9Cm();
        nhiIcd9Cm2.setId(nhiIcd9Cm1.getId());
        assertThat(nhiIcd9Cm1).isEqualTo(nhiIcd9Cm2);
        nhiIcd9Cm2.setId(2L);
        assertThat(nhiIcd9Cm1).isNotEqualTo(nhiIcd9Cm2);
        nhiIcd9Cm1.setId(null);
        assertThat(nhiIcd9Cm1).isNotEqualTo(nhiIcd9Cm2);
    }
}
