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
    private NHIUnusalIncidentRepository nHIUnusalIncidentRepository;

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

    private NHIUnusalIncident nHIUnusalIncident;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NHIUnusalIncidentResource nHIUnusalIncidentResource = new NHIUnusalIncidentResource(nHIUnusalIncidentRepository);
        this.restNHIUnusalIncidentMockMvc = MockMvcBuilders.standaloneSetup(nHIUnusalIncidentResource)
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
        NHIUnusalIncident nHIUnusalIncident = new NHIUnusalIncident()
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return nHIUnusalIncident;
    }

    @Before
    public void initTest() {
        nHIUnusalIncident = createEntity(em);
    }

    @Test
    @Transactional
    public void createNHIUnusalIncident() throws Exception {
        int databaseSizeBeforeCreate = nHIUnusalIncidentRepository.findAll().size();

        // Create the NHIUnusalIncident
        restNHIUnusalIncidentMockMvc.perform(post("/api/nhi-unusal-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHIUnusalIncident)))
            .andExpect(status().isCreated());

        // Validate the NHIUnusalIncident in the database
        List<NHIUnusalIncident> nHIUnusalIncidentList = nHIUnusalIncidentRepository.findAll();
        assertThat(nHIUnusalIncidentList).hasSize(databaseSizeBeforeCreate + 1);
        NHIUnusalIncident testNHIUnusalIncident = nHIUnusalIncidentList.get(nHIUnusalIncidentList.size() - 1);
        assertThat(testNHIUnusalIncident.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testNHIUnusalIncident.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createNHIUnusalIncidentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nHIUnusalIncidentRepository.findAll().size();

        // Create the NHIUnusalIncident with an existing ID
        nHIUnusalIncident.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNHIUnusalIncidentMockMvc.perform(post("/api/nhi-unusal-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHIUnusalIncident)))
            .andExpect(status().isBadRequest());

        // Validate the NHIUnusalIncident in the database
        List<NHIUnusalIncident> nHIUnusalIncidentList = nHIUnusalIncidentRepository.findAll();
        assertThat(nHIUnusalIncidentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = nHIUnusalIncidentRepository.findAll().size();
        // set the field null
        nHIUnusalIncident.setStart(null);

        // Create the NHIUnusalIncident, which fails.

        restNHIUnusalIncidentMockMvc.perform(post("/api/nhi-unusal-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHIUnusalIncident)))
            .andExpect(status().isBadRequest());

        List<NHIUnusalIncident> nHIUnusalIncidentList = nHIUnusalIncidentRepository.findAll();
        assertThat(nHIUnusalIncidentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNHIUnusalIncidents() throws Exception {
        // Initialize the database
        nHIUnusalIncidentRepository.saveAndFlush(nHIUnusalIncident);

        // Get all the nHIUnusalIncidentList
        restNHIUnusalIncidentMockMvc.perform(get("/api/nhi-unusal-incidents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nHIUnusalIncident.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }
    
    @Test
    @Transactional
    public void getNHIUnusalIncident() throws Exception {
        // Initialize the database
        nHIUnusalIncidentRepository.saveAndFlush(nHIUnusalIncident);

        // Get the nHIUnusalIncident
        restNHIUnusalIncidentMockMvc.perform(get("/api/nhi-unusal-incidents/{id}", nHIUnusalIncident.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nHIUnusalIncident.getId().intValue()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNHIUnusalIncident() throws Exception {
        // Get the nHIUnusalIncident
        restNHIUnusalIncidentMockMvc.perform(get("/api/nhi-unusal-incidents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNHIUnusalIncident() throws Exception {
        // Initialize the database
        nHIUnusalIncidentRepository.saveAndFlush(nHIUnusalIncident);

        int databaseSizeBeforeUpdate = nHIUnusalIncidentRepository.findAll().size();

        // Update the nHIUnusalIncident
        NHIUnusalIncident updatedNHIUnusalIncident = nHIUnusalIncidentRepository.findById(nHIUnusalIncident.getId()).get();
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
        List<NHIUnusalIncident> nHIUnusalIncidentList = nHIUnusalIncidentRepository.findAll();
        assertThat(nHIUnusalIncidentList).hasSize(databaseSizeBeforeUpdate);
        NHIUnusalIncident testNHIUnusalIncident = nHIUnusalIncidentList.get(nHIUnusalIncidentList.size() - 1);
        assertThat(testNHIUnusalIncident.getStart()).isEqualTo(UPDATED_START);
        assertThat(testNHIUnusalIncident.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingNHIUnusalIncident() throws Exception {
        int databaseSizeBeforeUpdate = nHIUnusalIncidentRepository.findAll().size();

        // Create the NHIUnusalIncident

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNHIUnusalIncidentMockMvc.perform(put("/api/nhi-unusal-incidents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHIUnusalIncident)))
            .andExpect(status().isBadRequest());

        // Validate the NHIUnusalIncident in the database
        List<NHIUnusalIncident> nHIUnusalIncidentList = nHIUnusalIncidentRepository.findAll();
        assertThat(nHIUnusalIncidentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNHIUnusalIncident() throws Exception {
        // Initialize the database
        nHIUnusalIncidentRepository.saveAndFlush(nHIUnusalIncident);

        int databaseSizeBeforeDelete = nHIUnusalIncidentRepository.findAll().size();

        // Get the nHIUnusalIncident
        restNHIUnusalIncidentMockMvc.perform(delete("/api/nhi-unusal-incidents/{id}", nHIUnusalIncident.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NHIUnusalIncident> nHIUnusalIncidentList = nHIUnusalIncidentRepository.findAll();
        assertThat(nHIUnusalIncidentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NHIUnusalIncident.class);
        NHIUnusalIncident nHIUnusalIncident1 = new NHIUnusalIncident();
        nHIUnusalIncident1.setId(1L);
        NHIUnusalIncident nHIUnusalIncident2 = new NHIUnusalIncident();
        nHIUnusalIncident2.setId(nHIUnusalIncident1.getId());
        assertThat(nHIUnusalIncident1).isEqualTo(nHIUnusalIncident2);
        nHIUnusalIncident2.setId(2L);
        assertThat(nHIUnusalIncident1).isNotEqualTo(nHIUnusalIncident2);
        nHIUnusalIncident1.setId(null);
        assertThat(nHIUnusalIncident1).isNotEqualTo(nHIUnusalIncident2);
    }
}
