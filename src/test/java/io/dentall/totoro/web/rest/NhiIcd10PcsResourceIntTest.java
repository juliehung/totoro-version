package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.NhiIcd10Pcs;
import io.dentall.totoro.repository.NhiIcd10PcsRepository;
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
 * Test class for the NhiIcd10PcsResource REST controller.
 *
 * @see NhiIcd10PcsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiIcd10PcsResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NHI_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NHI_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ENGLISH_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ENGLISH_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CHINESE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHINESE_NAME = "BBBBBBBBBB";

    @Autowired
    private NhiIcd10PcsRepository nhiIcd10PcsRepository;

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

    private MockMvc restNhiIcd10PcsMockMvc;

    private NhiIcd10Pcs nhiIcd10Pcs;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhiIcd10PcsResource nhiIcd10PcsResource = new NhiIcd10PcsResource(nhiIcd10PcsRepository);
        this.restNhiIcd10PcsMockMvc = MockMvcBuilders.standaloneSetup(nhiIcd10PcsResource)
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
    public static NhiIcd10Pcs createEntity(EntityManager em) {
        NhiIcd10Pcs nhiIcd10Pcs = new NhiIcd10Pcs()
            .code(DEFAULT_CODE)
            .nhiName(DEFAULT_NHI_NAME)
            .englishName(DEFAULT_ENGLISH_NAME)
            .chineseName(DEFAULT_CHINESE_NAME);
        return nhiIcd10Pcs;
    }

    @Before
    public void initTest() {
        nhiIcd10Pcs = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhiIcd10Pcs() throws Exception {
        int databaseSizeBeforeCreate = nhiIcd10PcsRepository.findAll().size();

        // Create the NhiIcd10Pcs
        restNhiIcd10PcsMockMvc.perform(post("/api/nhi-icd-10-pcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiIcd10Pcs)))
            .andExpect(status().isCreated());

        // Validate the NhiIcd10Pcs in the database
        List<NhiIcd10Pcs> nhiIcd10PcsList = nhiIcd10PcsRepository.findAll();
        assertThat(nhiIcd10PcsList).hasSize(databaseSizeBeforeCreate + 1);
        NhiIcd10Pcs testNhiIcd10Pcs = nhiIcd10PcsList.get(nhiIcd10PcsList.size() - 1);
        assertThat(testNhiIcd10Pcs.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testNhiIcd10Pcs.getNhiName()).isEqualTo(DEFAULT_NHI_NAME);
        assertThat(testNhiIcd10Pcs.getEnglishName()).isEqualTo(DEFAULT_ENGLISH_NAME);
        assertThat(testNhiIcd10Pcs.getChineseName()).isEqualTo(DEFAULT_CHINESE_NAME);
    }

    @Test
    @Transactional
    public void createNhiIcd10PcsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhiIcd10PcsRepository.findAll().size();

        // Create the NhiIcd10Pcs with an existing ID
        nhiIcd10Pcs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhiIcd10PcsMockMvc.perform(post("/api/nhi-icd-10-pcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiIcd10Pcs)))
            .andExpect(status().isBadRequest());

        // Validate the NhiIcd10Pcs in the database
        List<NhiIcd10Pcs> nhiIcd10PcsList = nhiIcd10PcsRepository.findAll();
        assertThat(nhiIcd10PcsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhiIcd10PcsRepository.findAll().size();
        // set the field null
        nhiIcd10Pcs.setCode(null);

        // Create the NhiIcd10Pcs, which fails.

        restNhiIcd10PcsMockMvc.perform(post("/api/nhi-icd-10-pcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiIcd10Pcs)))
            .andExpect(status().isBadRequest());

        List<NhiIcd10Pcs> nhiIcd10PcsList = nhiIcd10PcsRepository.findAll();
        assertThat(nhiIcd10PcsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNhiIcd10Pcs() throws Exception {
        // Initialize the database
        nhiIcd10PcsRepository.saveAndFlush(nhiIcd10Pcs);

        // Get all the nhiIcd10PcsList
        restNhiIcd10PcsMockMvc.perform(get("/api/nhi-icd-10-pcs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiIcd10Pcs.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].nhiName").value(hasItem(DEFAULT_NHI_NAME.toString())))
            .andExpect(jsonPath("$.[*].englishName").value(hasItem(DEFAULT_ENGLISH_NAME.toString())))
            .andExpect(jsonPath("$.[*].chineseName").value(hasItem(DEFAULT_CHINESE_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getNhiIcd10Pcs() throws Exception {
        // Initialize the database
        nhiIcd10PcsRepository.saveAndFlush(nhiIcd10Pcs);

        // Get the nhiIcd10Pcs
        restNhiIcd10PcsMockMvc.perform(get("/api/nhi-icd-10-pcs/{id}", nhiIcd10Pcs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhiIcd10Pcs.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.nhiName").value(DEFAULT_NHI_NAME.toString()))
            .andExpect(jsonPath("$.englishName").value(DEFAULT_ENGLISH_NAME.toString()))
            .andExpect(jsonPath("$.chineseName").value(DEFAULT_CHINESE_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNhiIcd10Pcs() throws Exception {
        // Get the nhiIcd10Pcs
        restNhiIcd10PcsMockMvc.perform(get("/api/nhi-icd-10-pcs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhiIcd10Pcs() throws Exception {
        // Initialize the database
        nhiIcd10PcsRepository.saveAndFlush(nhiIcd10Pcs);

        int databaseSizeBeforeUpdate = nhiIcd10PcsRepository.findAll().size();

        // Update the nhiIcd10Pcs
        NhiIcd10Pcs updatedNhiIcd10Pcs = nhiIcd10PcsRepository.findById(nhiIcd10Pcs.getId()).get();
        // Disconnect from session so that the updates on updatedNhiIcd10Pcs are not directly saved in db
        em.detach(updatedNhiIcd10Pcs);
        updatedNhiIcd10Pcs
            .code(UPDATED_CODE)
            .nhiName(UPDATED_NHI_NAME)
            .englishName(UPDATED_ENGLISH_NAME)
            .chineseName(UPDATED_CHINESE_NAME);

        restNhiIcd10PcsMockMvc.perform(put("/api/nhi-icd-10-pcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNhiIcd10Pcs)))
            .andExpect(status().isOk());

        // Validate the NhiIcd10Pcs in the database
        List<NhiIcd10Pcs> nhiIcd10PcsList = nhiIcd10PcsRepository.findAll();
        assertThat(nhiIcd10PcsList).hasSize(databaseSizeBeforeUpdate);
        NhiIcd10Pcs testNhiIcd10Pcs = nhiIcd10PcsList.get(nhiIcd10PcsList.size() - 1);
        assertThat(testNhiIcd10Pcs.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testNhiIcd10Pcs.getNhiName()).isEqualTo(UPDATED_NHI_NAME);
        assertThat(testNhiIcd10Pcs.getEnglishName()).isEqualTo(UPDATED_ENGLISH_NAME);
        assertThat(testNhiIcd10Pcs.getChineseName()).isEqualTo(UPDATED_CHINESE_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingNhiIcd10Pcs() throws Exception {
        int databaseSizeBeforeUpdate = nhiIcd10PcsRepository.findAll().size();

        // Create the NhiIcd10Pcs

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhiIcd10PcsMockMvc.perform(put("/api/nhi-icd-10-pcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiIcd10Pcs)))
            .andExpect(status().isBadRequest());

        // Validate the NhiIcd10Pcs in the database
        List<NhiIcd10Pcs> nhiIcd10PcsList = nhiIcd10PcsRepository.findAll();
        assertThat(nhiIcd10PcsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhiIcd10Pcs() throws Exception {
        // Initialize the database
        nhiIcd10PcsRepository.saveAndFlush(nhiIcd10Pcs);

        int databaseSizeBeforeDelete = nhiIcd10PcsRepository.findAll().size();

        // Get the nhiIcd10Pcs
        restNhiIcd10PcsMockMvc.perform(delete("/api/nhi-icd-10-pcs/{id}", nhiIcd10Pcs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhiIcd10Pcs> nhiIcd10PcsList = nhiIcd10PcsRepository.findAll();
        assertThat(nhiIcd10PcsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhiIcd10Pcs.class);
        NhiIcd10Pcs nhiIcd10Pcs1 = new NhiIcd10Pcs();
        nhiIcd10Pcs1.setId(1L);
        NhiIcd10Pcs nhiIcd10Pcs2 = new NhiIcd10Pcs();
        nhiIcd10Pcs2.setId(nhiIcd10Pcs1.getId());
        assertThat(nhiIcd10Pcs1).isEqualTo(nhiIcd10Pcs2);
        nhiIcd10Pcs2.setId(2L);
        assertThat(nhiIcd10Pcs1).isNotEqualTo(nhiIcd10Pcs2);
        nhiIcd10Pcs1.setId(null);
        assertThat(nhiIcd10Pcs1).isNotEqualTo(nhiIcd10Pcs2);
    }
}
