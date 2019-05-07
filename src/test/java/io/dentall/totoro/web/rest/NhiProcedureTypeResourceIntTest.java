package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.NhiProcedureType;
import io.dentall.totoro.repository.NhiProcedureTypeRepository;
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
 * Test class for the NhiProcedureTypeResource REST controller.
 *
 * @see NhiProcedureTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiProcedureTypeResourceIntTest {

    private static final String DEFAULT_MAJOR = "AAAAAAAAAA";
    private static final String UPDATED_MAJOR = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private NhiProcedureTypeRepository nhiProcedureTypeRepository;

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

    private MockMvc restNhiProcedureTypeMockMvc;

    private NhiProcedureType nhiProcedureType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhiProcedureTypeResource nhiProcedureTypeResource = new NhiProcedureTypeResource(nhiProcedureTypeRepository);
        this.restNhiProcedureTypeMockMvc = MockMvcBuilders.standaloneSetup(nhiProcedureTypeResource)
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
    public static NhiProcedureType createEntity(EntityManager em) {
        NhiProcedureType nhiProcedureType = new NhiProcedureType()
            .major(DEFAULT_MAJOR)
            .name(DEFAULT_NAME);
        return nhiProcedureType;
    }

    @Before
    public void initTest() {
        nhiProcedureType = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhiProcedureType() throws Exception {
        int databaseSizeBeforeCreate = nhiProcedureTypeRepository.findAll().size();

        // Create the NhiProcedureType
        restNhiProcedureTypeMockMvc.perform(post("/api/nhi-procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedureType)))
            .andExpect(status().isCreated());

        // Validate the NhiProcedureType in the database
        List<NhiProcedureType> nhiProcedureTypeList = nhiProcedureTypeRepository.findAll();
        assertThat(nhiProcedureTypeList).hasSize(databaseSizeBeforeCreate + 1);
        NhiProcedureType testNhiProcedureType = nhiProcedureTypeList.get(nhiProcedureTypeList.size() - 1);
        assertThat(testNhiProcedureType.getMajor()).isEqualTo(DEFAULT_MAJOR);
        assertThat(testNhiProcedureType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createNhiProcedureTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhiProcedureTypeRepository.findAll().size();

        // Create the NhiProcedureType with an existing ID
        nhiProcedureType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhiProcedureTypeMockMvc.perform(post("/api/nhi-procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedureType)))
            .andExpect(status().isBadRequest());

        // Validate the NhiProcedureType in the database
        List<NhiProcedureType> nhiProcedureTypeList = nhiProcedureTypeRepository.findAll();
        assertThat(nhiProcedureTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMajorIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhiProcedureTypeRepository.findAll().size();
        // set the field null
        nhiProcedureType.setMajor(null);

        // Create the NhiProcedureType, which fails.

        restNhiProcedureTypeMockMvc.perform(post("/api/nhi-procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedureType)))
            .andExpect(status().isBadRequest());

        List<NhiProcedureType> nhiProcedureTypeList = nhiProcedureTypeRepository.findAll();
        assertThat(nhiProcedureTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNhiProcedureTypes() throws Exception {
        // Initialize the database
        nhiProcedureTypeRepository.saveAndFlush(nhiProcedureType);

        // Get all the nhiProcedureTypeList
        restNhiProcedureTypeMockMvc.perform(get("/api/nhi-procedure-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiProcedureType.getId().intValue())))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getNhiProcedureType() throws Exception {
        // Initialize the database
        nhiProcedureTypeRepository.saveAndFlush(nhiProcedureType);

        // Get the nhiProcedureType
        restNhiProcedureTypeMockMvc.perform(get("/api/nhi-procedure-types/{id}", nhiProcedureType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhiProcedureType.getId().intValue()))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNhiProcedureType() throws Exception {
        // Get the nhiProcedureType
        restNhiProcedureTypeMockMvc.perform(get("/api/nhi-procedure-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhiProcedureType() throws Exception {
        // Initialize the database
        nhiProcedureTypeRepository.saveAndFlush(nhiProcedureType);

        int databaseSizeBeforeUpdate = nhiProcedureTypeRepository.findAll().size();

        // Update the nhiProcedureType
        NhiProcedureType updatedNhiProcedureType = nhiProcedureTypeRepository.findById(nhiProcedureType.getId()).get();
        // Disconnect from session so that the updates on updatedNhiProcedureType are not directly saved in db
        em.detach(updatedNhiProcedureType);
        updatedNhiProcedureType
            .major(UPDATED_MAJOR)
            .name(UPDATED_NAME);

        restNhiProcedureTypeMockMvc.perform(put("/api/nhi-procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNhiProcedureType)))
            .andExpect(status().isOk());

        // Validate the NhiProcedureType in the database
        List<NhiProcedureType> nhiProcedureTypeList = nhiProcedureTypeRepository.findAll();
        assertThat(nhiProcedureTypeList).hasSize(databaseSizeBeforeUpdate);
        NhiProcedureType testNhiProcedureType = nhiProcedureTypeList.get(nhiProcedureTypeList.size() - 1);
        assertThat(testNhiProcedureType.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testNhiProcedureType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingNhiProcedureType() throws Exception {
        int databaseSizeBeforeUpdate = nhiProcedureTypeRepository.findAll().size();

        // Create the NhiProcedureType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhiProcedureTypeMockMvc.perform(put("/api/nhi-procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedureType)))
            .andExpect(status().isBadRequest());

        // Validate the NhiProcedureType in the database
        List<NhiProcedureType> nhiProcedureTypeList = nhiProcedureTypeRepository.findAll();
        assertThat(nhiProcedureTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhiProcedureType() throws Exception {
        // Initialize the database
        nhiProcedureTypeRepository.saveAndFlush(nhiProcedureType);

        int databaseSizeBeforeDelete = nhiProcedureTypeRepository.findAll().size();

        // Get the nhiProcedureType
        restNhiProcedureTypeMockMvc.perform(delete("/api/nhi-procedure-types/{id}", nhiProcedureType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhiProcedureType> nhiProcedureTypeList = nhiProcedureTypeRepository.findAll();
        assertThat(nhiProcedureTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhiProcedureType.class);
        NhiProcedureType nhiProcedureType1 = new NhiProcedureType();
        nhiProcedureType1.setId(1L);
        NhiProcedureType nhiProcedureType2 = new NhiProcedureType();
        nhiProcedureType2.setId(nhiProcedureType1.getId());
        assertThat(nhiProcedureType1).isEqualTo(nhiProcedureType2);
        nhiProcedureType2.setId(2L);
        assertThat(nhiProcedureType1).isNotEqualTo(nhiProcedureType2);
        nhiProcedureType1.setId(null);
        assertThat(nhiProcedureType1).isNotEqualTo(nhiProcedureType2);
    }
}
