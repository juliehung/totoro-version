package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.NhiMonthDeclaration;
import io.dentall.totoro.repository.NhiMonthDeclarationRepository;
import io.dentall.totoro.service.NhiMonthDeclarationService;
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
 * Test class for the NhiMonthDeclarationResource REST controller.
 *
 * @see NhiMonthDeclarationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiMonthDeclarationResourceIntTest {

    private static final String DEFAULT_YEAR_MONTH = "AAAAAAAAAA";
    private static final String UPDATED_YEAR_MONTH = "BBBBBBBBBB";

    private static final String DEFAULT_INSTITUTION = "AAAAAAAAAA";
    private static final String UPDATED_INSTITUTION = "BBBBBBBBBB";

    @Autowired
    private NhiMonthDeclarationRepository nhiMonthDeclarationRepository;

    @Autowired
    private NhiMonthDeclarationService nhiMonthDeclarationService;

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

    private MockMvc restNhiMonthDeclarationMockMvc;

    private NhiMonthDeclaration nhiMonthDeclaration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhiMonthDeclarationResource nhiMonthDeclarationResource = new NhiMonthDeclarationResource(nhiMonthDeclarationService);
        this.restNhiMonthDeclarationMockMvc = MockMvcBuilders.standaloneSetup(nhiMonthDeclarationResource)
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
    public static NhiMonthDeclaration createEntity(EntityManager em) {
        NhiMonthDeclaration nhiMonthDeclaration = new NhiMonthDeclaration()
            .yearMonth(DEFAULT_YEAR_MONTH)
            .institution(DEFAULT_INSTITUTION);
        return nhiMonthDeclaration;
    }

    @Before
    public void initTest() {
        nhiMonthDeclaration = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhiMonthDeclaration() throws Exception {
        int databaseSizeBeforeCreate = nhiMonthDeclarationRepository.findAll().size();

        // Create the NhiMonthDeclaration
        restNhiMonthDeclarationMockMvc.perform(post("/api/nhi-month-declarations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiMonthDeclaration)))
            .andExpect(status().isCreated());

        // Validate the NhiMonthDeclaration in the database
        List<NhiMonthDeclaration> nhiMonthDeclarationList = nhiMonthDeclarationRepository.findAll();
        assertThat(nhiMonthDeclarationList).hasSize(databaseSizeBeforeCreate + 1);
        NhiMonthDeclaration testNhiMonthDeclaration = nhiMonthDeclarationList.get(nhiMonthDeclarationList.size() - 1);
        assertThat(testNhiMonthDeclaration.getYearMonth()).isEqualTo(DEFAULT_YEAR_MONTH);
        assertThat(testNhiMonthDeclaration.getInstitution()).isEqualTo(DEFAULT_INSTITUTION);
    }

    @Test
    @Transactional
    public void createNhiMonthDeclarationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhiMonthDeclarationRepository.findAll().size();

        // Create the NhiMonthDeclaration with an existing ID
        nhiMonthDeclaration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhiMonthDeclarationMockMvc.perform(post("/api/nhi-month-declarations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiMonthDeclaration)))
            .andExpect(status().isBadRequest());

        // Validate the NhiMonthDeclaration in the database
        List<NhiMonthDeclaration> nhiMonthDeclarationList = nhiMonthDeclarationRepository.findAll();
        assertThat(nhiMonthDeclarationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkYearMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhiMonthDeclarationRepository.findAll().size();
        // set the field null
        nhiMonthDeclaration.setYearMonth(null);

        // Create the NhiMonthDeclaration, which fails.

        restNhiMonthDeclarationMockMvc.perform(post("/api/nhi-month-declarations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiMonthDeclaration)))
            .andExpect(status().isBadRequest());

        List<NhiMonthDeclaration> nhiMonthDeclarationList = nhiMonthDeclarationRepository.findAll();
        assertThat(nhiMonthDeclarationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarations() throws Exception {
        // Initialize the database
        nhiMonthDeclarationRepository.saveAndFlush(nhiMonthDeclaration);

        // Get all the nhiMonthDeclarationList
        restNhiMonthDeclarationMockMvc.perform(get("/api/nhi-month-declarations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiMonthDeclaration.getId().intValue())))
            .andExpect(jsonPath("$.[*].yearMonth").value(hasItem(DEFAULT_YEAR_MONTH.toString())))
            .andExpect(jsonPath("$.[*].institution").value(hasItem(DEFAULT_INSTITUTION.toString())));
    }
    
    @Test
    @Transactional
    public void getNhiMonthDeclaration() throws Exception {
        // Initialize the database
        nhiMonthDeclarationRepository.saveAndFlush(nhiMonthDeclaration);

        // Get the nhiMonthDeclaration
        restNhiMonthDeclarationMockMvc.perform(get("/api/nhi-month-declarations/{id}", nhiMonthDeclaration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhiMonthDeclaration.getId().intValue()))
            .andExpect(jsonPath("$.yearMonth").value(DEFAULT_YEAR_MONTH.toString()))
            .andExpect(jsonPath("$.institution").value(DEFAULT_INSTITUTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNhiMonthDeclaration() throws Exception {
        // Get the nhiMonthDeclaration
        restNhiMonthDeclarationMockMvc.perform(get("/api/nhi-month-declarations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhiMonthDeclaration() throws Exception {
        // Initialize the database
        nhiMonthDeclarationService.save(nhiMonthDeclaration);

        int databaseSizeBeforeUpdate = nhiMonthDeclarationRepository.findAll().size();

        // Update the nhiMonthDeclaration
        NhiMonthDeclaration updatedNhiMonthDeclaration = nhiMonthDeclarationRepository.findById(nhiMonthDeclaration.getId()).get();
        // Disconnect from session so that the updates on updatedNhiMonthDeclaration are not directly saved in db
        em.detach(updatedNhiMonthDeclaration);
        updatedNhiMonthDeclaration
            .yearMonth(UPDATED_YEAR_MONTH)
            .institution(UPDATED_INSTITUTION);

        restNhiMonthDeclarationMockMvc.perform(put("/api/nhi-month-declarations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNhiMonthDeclaration)))
            .andExpect(status().isOk());

        // Validate the NhiMonthDeclaration in the database
        List<NhiMonthDeclaration> nhiMonthDeclarationList = nhiMonthDeclarationRepository.findAll();
        assertThat(nhiMonthDeclarationList).hasSize(databaseSizeBeforeUpdate);
        NhiMonthDeclaration testNhiMonthDeclaration = nhiMonthDeclarationList.get(nhiMonthDeclarationList.size() - 1);
        assertThat(testNhiMonthDeclaration.getYearMonth()).isEqualTo(UPDATED_YEAR_MONTH);
        assertThat(testNhiMonthDeclaration.getInstitution()).isEqualTo(UPDATED_INSTITUTION);
    }

    @Test
    @Transactional
    public void updateNonExistingNhiMonthDeclaration() throws Exception {
        int databaseSizeBeforeUpdate = nhiMonthDeclarationRepository.findAll().size();

        // Create the NhiMonthDeclaration

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhiMonthDeclarationMockMvc.perform(put("/api/nhi-month-declarations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiMonthDeclaration)))
            .andExpect(status().isBadRequest());

        // Validate the NhiMonthDeclaration in the database
        List<NhiMonthDeclaration> nhiMonthDeclarationList = nhiMonthDeclarationRepository.findAll();
        assertThat(nhiMonthDeclarationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhiMonthDeclaration() throws Exception {
        // Initialize the database
        nhiMonthDeclarationService.save(nhiMonthDeclaration);

        int databaseSizeBeforeDelete = nhiMonthDeclarationRepository.findAll().size();

        // Get the nhiMonthDeclaration
        restNhiMonthDeclarationMockMvc.perform(delete("/api/nhi-month-declarations/{id}", nhiMonthDeclaration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhiMonthDeclaration> nhiMonthDeclarationList = nhiMonthDeclarationRepository.findAll();
        assertThat(nhiMonthDeclarationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhiMonthDeclaration.class);
        NhiMonthDeclaration nhiMonthDeclaration1 = new NhiMonthDeclaration();
        nhiMonthDeclaration1.setId(1L);
        NhiMonthDeclaration nhiMonthDeclaration2 = new NhiMonthDeclaration();
        nhiMonthDeclaration2.setId(nhiMonthDeclaration1.getId());
        assertThat(nhiMonthDeclaration1).isEqualTo(nhiMonthDeclaration2);
        nhiMonthDeclaration2.setId(2L);
        assertThat(nhiMonthDeclaration1).isNotEqualTo(nhiMonthDeclaration2);
        nhiMonthDeclaration1.setId(null);
        assertThat(nhiMonthDeclaration1).isNotEqualTo(nhiMonthDeclaration2);
    }
}
