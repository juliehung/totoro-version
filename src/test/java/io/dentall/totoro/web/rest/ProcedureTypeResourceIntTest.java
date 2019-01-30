package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.ProcedureType;
import io.dentall.totoro.repository.ProcedureTypeRepository;
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
 * Test class for the ProcedureTypeResource REST controller.
 *
 * @see ProcedureTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class ProcedureTypeResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private ProcedureTypeRepository procedureTypeRepository;

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

    private MockMvc restProcedureTypeMockMvc;

    private ProcedureType procedureType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcedureTypeResource procedureTypeResource = new ProcedureTypeResource(procedureTypeRepository);
        this.restProcedureTypeMockMvc = MockMvcBuilders.standaloneSetup(procedureTypeResource)
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
    public static ProcedureType createEntity(EntityManager em) {
        ProcedureType procedureType = new ProcedureType()
            .name(DEFAULT_NAME);
        return procedureType;
    }

    @Before
    public void initTest() {
        procedureType = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcedureType() throws Exception {
        int databaseSizeBeforeCreate = procedureTypeRepository.findAll().size();

        // Create the ProcedureType
        restProcedureTypeMockMvc.perform(post("/api/procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureType)))
            .andExpect(status().isCreated());

        // Validate the ProcedureType in the database
        List<ProcedureType> procedureTypeList = procedureTypeRepository.findAll();
        assertThat(procedureTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ProcedureType testProcedureType = procedureTypeList.get(procedureTypeList.size() - 1);
        assertThat(testProcedureType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createProcedureTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = procedureTypeRepository.findAll().size();

        // Create the ProcedureType with an existing ID
        procedureType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcedureTypeMockMvc.perform(post("/api/procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureType)))
            .andExpect(status().isBadRequest());

        // Validate the ProcedureType in the database
        List<ProcedureType> procedureTypeList = procedureTypeRepository.findAll();
        assertThat(procedureTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = procedureTypeRepository.findAll().size();
        // set the field null
        procedureType.setName(null);

        // Create the ProcedureType, which fails.

        restProcedureTypeMockMvc.perform(post("/api/procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureType)))
            .andExpect(status().isBadRequest());

        List<ProcedureType> procedureTypeList = procedureTypeRepository.findAll();
        assertThat(procedureTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProcedureTypes() throws Exception {
        // Initialize the database
        procedureTypeRepository.saveAndFlush(procedureType);

        // Get all the procedureTypeList
        restProcedureTypeMockMvc.perform(get("/api/procedure-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedureType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getProcedureType() throws Exception {
        // Initialize the database
        procedureTypeRepository.saveAndFlush(procedureType);

        // Get the procedureType
        restProcedureTypeMockMvc.perform(get("/api/procedure-types/{id}", procedureType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(procedureType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProcedureType() throws Exception {
        // Get the procedureType
        restProcedureTypeMockMvc.perform(get("/api/procedure-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcedureType() throws Exception {
        // Initialize the database
        procedureTypeRepository.saveAndFlush(procedureType);

        int databaseSizeBeforeUpdate = procedureTypeRepository.findAll().size();

        // Update the procedureType
        ProcedureType updatedProcedureType = procedureTypeRepository.findById(procedureType.getId()).get();
        // Disconnect from session so that the updates on updatedProcedureType are not directly saved in db
        em.detach(updatedProcedureType);
        updatedProcedureType
            .name(UPDATED_NAME);

        restProcedureTypeMockMvc.perform(put("/api/procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcedureType)))
            .andExpect(status().isOk());

        // Validate the ProcedureType in the database
        List<ProcedureType> procedureTypeList = procedureTypeRepository.findAll();
        assertThat(procedureTypeList).hasSize(databaseSizeBeforeUpdate);
        ProcedureType testProcedureType = procedureTypeList.get(procedureTypeList.size() - 1);
        assertThat(testProcedureType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingProcedureType() throws Exception {
        int databaseSizeBeforeUpdate = procedureTypeRepository.findAll().size();

        // Create the ProcedureType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcedureTypeMockMvc.perform(put("/api/procedure-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedureType)))
            .andExpect(status().isBadRequest());

        // Validate the ProcedureType in the database
        List<ProcedureType> procedureTypeList = procedureTypeRepository.findAll();
        assertThat(procedureTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcedureType() throws Exception {
        // Initialize the database
        procedureTypeRepository.saveAndFlush(procedureType);

        int databaseSizeBeforeDelete = procedureTypeRepository.findAll().size();

        // Get the procedureType
        restProcedureTypeMockMvc.perform(delete("/api/procedure-types/{id}", procedureType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProcedureType> procedureTypeList = procedureTypeRepository.findAll();
        assertThat(procedureTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcedureType.class);
        ProcedureType procedureType1 = new ProcedureType();
        procedureType1.setId(1L);
        ProcedureType procedureType2 = new ProcedureType();
        procedureType2.setId(procedureType1.getId());
        assertThat(procedureType1).isEqualTo(procedureType2);
        procedureType2.setId(2L);
        assertThat(procedureType1).isNotEqualTo(procedureType2);
        procedureType1.setId(null);
        assertThat(procedureType1).isNotEqualTo(procedureType2);
    }
}
