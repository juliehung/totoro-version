package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.NHIUnusalIncident;
import io.dentall.totoro.repository.NHIUnusalIncidentRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NHIUnusalIncidentResource REST controller.
 *
 * @see NHIUnusalIncidentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NHIUnusalIncidentResourceIntTest {

    private static final Instant DEFAULT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private NHIUnusalIncidentRepository nhiUnusalIncidentRepository;

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

    private MockMvc restNHIUnusalIncidentMockMvc;

    private NHIUnusalIncident nhiUnusalIncident;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NHIUnusalIncidentResource nhiUnusalIncidentResource = new NHIUnusalIncidentResource(nhiUnusalIncidentRepository);
        this.restNHIUnusalIncidentMockMvc = MockMvcBuilders.standaloneSetup(nhiUnusalIncidentResource)
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
    public static NHIUnusalIncident createEntity(EntityManager em) {
        NHIUnusalIncident nhiUnusalIncident = new NHIUnusalIncident()
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return nhiUnusalIncident;
    }

    @Before
    public void initTest() {
        nhiUnusalIncident = createEntity(em);
    }

    @Test
    @Transactional
    public void createNHIUnusalIncident() throws Exception {
        int databaseSizeBeforeCreate = nhiUnusalIncidentRepository.findAll().size();

        // Create the NHIUnusalIncident
        restNHIUnusalIncidentMockMvc.perform(post("/api/nhi-unusal-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiUnusalIncident)))
            .andExpect(status().isCreated());

        // Validate the NHIUnusalIncident in the database
        List<NHIUnusalIncident> nhiUnusalIncidentList = nhiUnusalIncidentRepository.findAll();
        assertThat(nhiUnusalIncidentList).hasSize(databaseSizeBeforeCreate + 1);
        NHIUnusalIncident testNHIUnusalIncident = nhiUnusalIncidentList.get(nhiUnusalIncidentList.size() - 1);
        assertThat(testNHIUnusalIncident.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testNHIUnusalIncident.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createNHIUnusalIncidentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhiUnusalIncidentRepository.findAll().size();

        // Create the NHIUnusalIncident with an existing ID
        nhiUnusalIncident.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNHIUnusalIncidentMockMvc.perform(post("/api/nhi-unusal-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiUnusalIncident)))
            .andExpect(status().isBadRequest());

        // Validate the NHIUnusalIncident in the database
        List<NHIUnusalIncident> nhiUnusalIncidentList = nhiUnusalIncidentRepository.findAll();
        assertThat(nhiUnusalIncidentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhiUnusalIncidentRepository.findAll().size();
        // set the field null
        nhiUnusalIncident.setStart(null);

        // Create the NHIUnusalIncident, which fails.

        restNHIUnusalIncidentMockMvc.perform(post("/api/nhi-unusal-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiUnusalIncident)))
            .andExpect(status().isBadRequest());

        List<NHIUnusalIncident> nhiUnusalIncidentList = nhiUnusalIncidentRepository.findAll();
        assertThat(nhiUnusalIncidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNHIUnusalIncidents() throws Exception {
        // Initialize the database
        nhiUnusalIncidentRepository.saveAndFlush(nhiUnusalIncident);

        // Get all the nhiUnusalIncidentList
        restNHIUnusalIncidentMockMvc.perform(get("/api/nhi-unusal-incidents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiUnusalIncident.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }
    
    @Test
    @Transactional
    public void getNHIUnusalIncident() throws Exception {
        // Initialize the database
        nhiUnusalIncidentRepository.saveAndFlush(nhiUnusalIncident);

        // Get the nhiUnusalIncident
        restNHIUnusalIncidentMockMvc.perform(get("/api/nhi-unusal-incidents/{id}", nhiUnusalIncident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhiUnusalIncident.getId().intValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNHIUnusalIncident() throws Exception {
        // Get the nhiUnusalIncident
        restNHIUnusalIncidentMockMvc.perform(get("/api/nhi-unusal-incidents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNHIUnusalIncident() throws Exception {
        // Initialize the database
        nhiUnusalIncidentRepository.saveAndFlush(nhiUnusalIncident);

        int databaseSizeBeforeUpdate = nhiUnusalIncidentRepository.findAll().size();

        // Update the nhiUnusalIncident
        NHIUnusalIncident updatedNHIUnusalIncident = nhiUnusalIncidentRepository.findById(nhiUnusalIncident.getId()).get();
        // Disconnect from session so that the updates on updatedNHIUnusalIncident are not directly saved in db
        em.detach(updatedNHIUnusalIncident);
        updatedNHIUnusalIncident
            .start(UPDATED_START)
            .end(UPDATED_END);

        restNHIUnusalIncidentMockMvc.perform(put("/api/nhi-unusal-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNHIUnusalIncident)))
            .andExpect(status().isOk());

        // Validate the NHIUnusalIncident in the database
        List<NHIUnusalIncident> nhiUnusalIncidentList = nhiUnusalIncidentRepository.findAll();
        assertThat(nhiUnusalIncidentList).hasSize(databaseSizeBeforeUpdate);
        NHIUnusalIncident testNHIUnusalIncident = nhiUnusalIncidentList.get(nhiUnusalIncidentList.size() - 1);
        assertThat(testNHIUnusalIncident.getStart()).isEqualTo(UPDATED_START);
        assertThat(testNHIUnusalIncident.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingNHIUnusalIncident() throws Exception {
        int databaseSizeBeforeUpdate = nhiUnusalIncidentRepository.findAll().size();

        // Create the NHIUnusalIncident

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNHIUnusalIncidentMockMvc.perform(put("/api/nhi-unusal-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiUnusalIncident)))
            .andExpect(status().isBadRequest());

        // Validate the NHIUnusalIncident in the database
        List<NHIUnusalIncident> nhiUnusalIncidentList = nhiUnusalIncidentRepository.findAll();
        assertThat(nhiUnusalIncidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNHIUnusalIncident() throws Exception {
        // Initialize the database
        nhiUnusalIncidentRepository.saveAndFlush(nhiUnusalIncident);

        int databaseSizeBeforeDelete = nhiUnusalIncidentRepository.findAll().size();

        // Get the nhiUnusalIncident
        restNHIUnusalIncidentMockMvc.perform(delete("/api/nhi-unusal-incidents/{id}", nhiUnusalIncident.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NHIUnusalIncident> nhiUnusalIncidentList = nhiUnusalIncidentRepository.findAll();
        assertThat(nhiUnusalIncidentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NHIUnusalIncident.class);
        NHIUnusalIncident nhiUnusalIncident1 = new NHIUnusalIncident();
        nhiUnusalIncident1.setId(1L);
        NHIUnusalIncident nhiUnusalIncident2 = new NHIUnusalIncident();
        nhiUnusalIncident2.setId(nhiUnusalIncident1.getId());
        assertThat(nhiUnusalIncident1).isEqualTo(nhiUnusalIncident2);
        nhiUnusalIncident2.setId(2L);
        assertThat(nhiUnusalIncident1).isNotEqualTo(nhiUnusalIncident2);
        nhiUnusalIncident1.setId(null);
        assertThat(nhiUnusalIncident1).isNotEqualTo(nhiUnusalIncident2);
    }
}
