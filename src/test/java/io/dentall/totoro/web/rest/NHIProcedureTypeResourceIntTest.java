package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.NHIProcedureType;
import io.dentall.totoro.repository.NHIProcedureTypeRepository;
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
 * Test class for the NHIProcedureTypeResource REST controller.
 *
 * @see NHIProcedureTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NHIProcedureTypeResourceIntTest {

    private static final String DEFAULT_MAJOR = "AAAAAAAAAA";
    private static final String UPDATED_MAJOR = "BBBBBBBBBB";

    @Autowired
    private NHIProcedureTypeRepository nhiProcedureTypeRepository;

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

    private MockMvc restNHIProcedureTypeMockMvc;

    private NHIProcedureType nhiProcedureType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NHIProcedureTypeResource nhiProcedureTypeResource = new NHIProcedureTypeResource(nhiProcedureTypeRepository);
        this.restNHIProcedureTypeMockMvc = MockMvcBuilders.standaloneSetup(nhiProcedureTypeResource)
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
    public static NHIProcedureType createEntity(EntityManager em) {
        NHIProcedureType nhiProcedureType = new NHIProcedureType()
            .major(DEFAULT_MAJOR);
        return nhiProcedureType;
    }

    @Before
    public void initTest() {
        nhiProcedureType = createEntity(em);
    }

    @Test
    @Transactional
    public void createNHIProcedureType() throws Exception {
        int databaseSizeBeforeCreate = nhiProcedureTypeRepository.findAll().size();

        // Create the NHIProcedureType
        restNHIProcedureTypeMockMvc.perform(post("/api/nhi-procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedureType)))
            .andExpect(status().isCreated());

        // Validate the NHIProcedureType in the database
        List<NHIProcedureType> nhiProcedureTypeList = nhiProcedureTypeRepository.findAll();
        assertThat(nhiProcedureTypeList).hasSize(databaseSizeBeforeCreate + 1);
        NHIProcedureType testNHIProcedureType = nhiProcedureTypeList.get(nhiProcedureTypeList.size() - 1);
        assertThat(testNHIProcedureType.getMajor()).isEqualTo(DEFAULT_MAJOR);
    }

    @Test
    @Transactional
    public void createNHIProcedureTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhiProcedureTypeRepository.findAll().size();

        // Create the NHIProcedureType with an existing ID
        nhiProcedureType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNHIProcedureTypeMockMvc.perform(post("/api/nhi-procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedureType)))
            .andExpect(status().isBadRequest());

        // Validate the NHIProcedureType in the database
        List<NHIProcedureType> nhiProcedureTypeList = nhiProcedureTypeRepository.findAll();
        assertThat(nhiProcedureTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMajorIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhiProcedureTypeRepository.findAll().size();
        // set the field null
        nhiProcedureType.setMajor(null);

        // Create the NHIProcedureType, which fails.

        restNHIProcedureTypeMockMvc.perform(post("/api/nhi-procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedureType)))
            .andExpect(status().isBadRequest());

        List<NHIProcedureType> nhiProcedureTypeList = nhiProcedureTypeRepository.findAll();
        assertThat(nhiProcedureTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNHIProcedureTypes() throws Exception {
        // Initialize the database
        nhiProcedureTypeRepository.saveAndFlush(nhiProcedureType);

        // Get all the nhiProcedureTypeList
        restNHIProcedureTypeMockMvc.perform(get("/api/nhi-procedure-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiProcedureType.getId().intValue())))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR.toString())));
    }
    
    @Test
    @Transactional
    public void getNHIProcedureType() throws Exception {
        // Initialize the database
        nhiProcedureTypeRepository.saveAndFlush(nhiProcedureType);

        // Get the nhiProcedureType
        restNHIProcedureTypeMockMvc.perform(get("/api/nhi-procedure-types/{id}", nhiProcedureType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhiProcedureType.getId().intValue()))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNHIProcedureType() throws Exception {
        // Get the nhiProcedureType
        restNHIProcedureTypeMockMvc.perform(get("/api/nhi-procedure-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNHIProcedureType() throws Exception {
        // Initialize the database
        nhiProcedureTypeRepository.saveAndFlush(nhiProcedureType);

        int databaseSizeBeforeUpdate = nhiProcedureTypeRepository.findAll().size();

        // Update the nhiProcedureType
        NHIProcedureType updatedNHIProcedureType = nhiProcedureTypeRepository.findById(nhiProcedureType.getId()).get();
        // Disconnect from session so that the updates on updatedNHIProcedureType are not directly saved in db
        em.detach(updatedNHIProcedureType);
        updatedNHIProcedureType
            .major(UPDATED_MAJOR);

        restNHIProcedureTypeMockMvc.perform(put("/api/nhi-procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNHIProcedureType)))
            .andExpect(status().isOk());

        // Validate the NHIProcedureType in the database
        List<NHIProcedureType> nhiProcedureTypeList = nhiProcedureTypeRepository.findAll();
        assertThat(nhiProcedureTypeList).hasSize(databaseSizeBeforeUpdate);
        NHIProcedureType testNHIProcedureType = nhiProcedureTypeList.get(nhiProcedureTypeList.size() - 1);
        assertThat(testNHIProcedureType.getMajor()).isEqualTo(UPDATED_MAJOR);
    }

    @Test
    @Transactional
    public void updateNonExistingNHIProcedureType() throws Exception {
        int databaseSizeBeforeUpdate = nhiProcedureTypeRepository.findAll().size();

        // Create the NHIProcedureType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNHIProcedureTypeMockMvc.perform(put("/api/nhi-procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiProcedureType)))
            .andExpect(status().isBadRequest());

        // Validate the NHIProcedureType in the database
        List<NHIProcedureType> nhiProcedureTypeList = nhiProcedureTypeRepository.findAll();
        assertThat(nhiProcedureTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNHIProcedureType() throws Exception {
        // Initialize the database
        nhiProcedureTypeRepository.saveAndFlush(nhiProcedureType);

        int databaseSizeBeforeDelete = nhiProcedureTypeRepository.findAll().size();

        // Get the nhiProcedureType
        restNHIProcedureTypeMockMvc.perform(delete("/api/nhi-procedure-types/{id}", nhiProcedureType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NHIProcedureType> nhiProcedureTypeList = nhiProcedureTypeRepository.findAll();
        assertThat(nhiProcedureTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NHIProcedureType.class);
        NHIProcedureType nhiProcedureType1 = new NHIProcedureType();
        nhiProcedureType1.setId(1L);
        NHIProcedureType nhiProcedureType2 = new NHIProcedureType();
        nhiProcedureType2.setId(nhiProcedureType1.getId());
        assertThat(nhiProcedureType1).isEqualTo(nhiProcedureType2);
        nhiProcedureType2.setId(2L);
        assertThat(nhiProcedureType1).isNotEqualTo(nhiProcedureType2);
        nhiProcedureType1.setId(null);
        assertThat(nhiProcedureType1).isNotEqualTo(nhiProcedureType2);
    }
}
