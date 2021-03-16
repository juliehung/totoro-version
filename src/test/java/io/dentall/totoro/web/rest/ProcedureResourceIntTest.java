package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.Procedure;
import io.dentall.totoro.domain.ProcedureType;
import io.dentall.totoro.repository.ProcedureRepository;
import io.dentall.totoro.service.ProcedureService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.ProcedureQueryService;

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
 * Test class for the ProcedureResource REST controller.
 *
 * @see ProcedureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class ProcedureResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    @Autowired
    private ProcedureRepository procedureRepository;

    @Autowired
    private ProcedureService procedureService;

    @Autowired
    private ProcedureQueryService procedureQueryService;

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

    private MockMvc restProcedureMockMvc;

    private Procedure procedure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcedureResource procedureResource = new ProcedureResource(procedureService, procedureQueryService, procedureRepository);
        this.restProcedureMockMvc = MockMvcBuilders.standaloneSetup(procedureResource)
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
    public static Procedure createEntity(EntityManager em) {
        Procedure procedure = new Procedure()
            .content(DEFAULT_CONTENT)
            .price(DEFAULT_PRICE);
        return procedure;
    }

    @Before
    public void initTest() {
        procedure = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcedure() throws Exception {
        int databaseSizeBeforeCreate = procedureRepository.findAll().size();

        // Create the Procedure
        restProcedureMockMvc.perform(post("/api/procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedure)))
            .andExpect(status().isCreated());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeCreate + 1);
        Procedure testProcedure = procedureList.get(procedureList.size() - 1);
        assertThat(testProcedure.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testProcedure.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createProcedureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = procedureRepository.findAll().size();

        // Create the Procedure with an existing ID
        procedure.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcedureMockMvc.perform(post("/api/procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedure)))
            .andExpect(status().isBadRequest());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = procedureRepository.findAll().size();
        // set the field null
        procedure.setContent(null);

        // Create the Procedure, which fails.

        restProcedureMockMvc.perform(post("/api/procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedure)))
            .andExpect(status().isBadRequest());

        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProcedures() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList
        restProcedureMockMvc.perform(get("/api/procedures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }

    @Test
    @Transactional
    public void getProcedure() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get the procedure
        restProcedureMockMvc.perform(get("/api/procedures/{id}", procedure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(procedure.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllProceduresByContentIsEqualToSomething() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where content equals to DEFAULT_CONTENT
        defaultProcedureShouldBeFound("content.equals=" + DEFAULT_CONTENT);

        // Get all the procedureList where content equals to UPDATED_CONTENT
        defaultProcedureShouldNotBeFound("content.equals=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllProceduresByContentIsInShouldWork() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where content in DEFAULT_CONTENT or UPDATED_CONTENT
        defaultProcedureShouldBeFound("content.in=" + DEFAULT_CONTENT + "," + UPDATED_CONTENT);

        // Get all the procedureList where content equals to UPDATED_CONTENT
        defaultProcedureShouldNotBeFound("content.in=" + UPDATED_CONTENT);
    }

    @Test
    @Transactional
    public void getAllProceduresByContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where content is not null
        defaultProcedureShouldBeFound("content.specified=true");

        // Get all the procedureList where content is null
        defaultProcedureShouldNotBeFound("content.specified=false");
    }

    @Test
    @Transactional
    public void getAllProceduresByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where price equals to DEFAULT_PRICE
        defaultProcedureShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the procedureList where price equals to UPDATED_PRICE
        defaultProcedureShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProceduresByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultProcedureShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the procedureList where price equals to UPDATED_PRICE
        defaultProcedureShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllProceduresByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        procedureRepository.saveAndFlush(procedure);

        // Get all the procedureList where price is not null
        defaultProcedureShouldBeFound("price.specified=true");

        // Get all the procedureList where price is null
        defaultProcedureShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllProceduresByProcedureTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        ProcedureType procedureType = ProcedureTypeResourceIntTest.createEntity(em);
        em.persist(procedureType);
        em.flush();
        procedure.setProcedureType(procedureType);
        procedureRepository.saveAndFlush(procedure);
        Long procedureTypeId = procedureType.getId();

        // Get all the procedureList where procedureType equals to procedureTypeId
        defaultProcedureShouldBeFound("procedureTypeId.equals=" + procedureTypeId);

        // Get all the procedureList where procedureType equals to procedureTypeId + 1
        defaultProcedureShouldNotBeFound("procedureTypeId.equals=" + (procedureTypeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultProcedureShouldBeFound(String filter) throws Exception {
        restProcedureMockMvc.perform(get("/api/procedures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));

        // Check, that the count call also returns 1
        restProcedureMockMvc.perform(get("/api/procedures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultProcedureShouldNotBeFound(String filter) throws Exception {
        restProcedureMockMvc.perform(get("/api/procedures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProcedureMockMvc.perform(get("/api/procedures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingProcedure() throws Exception {
        // Get the procedure
        restProcedureMockMvc.perform(get("/api/procedures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcedure() throws Exception {
        // Initialize the database
        procedureService.save(procedure);

        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();

        // Update the procedure
        Procedure updatedProcedure = procedureRepository.findById(procedure.getId()).get();
        // Disconnect from session so that the updates on updatedProcedure are not directly saved in db
        em.detach(updatedProcedure);
        updatedProcedure
            .content(UPDATED_CONTENT)
            .price(UPDATED_PRICE);

        restProcedureMockMvc.perform(put("/api/procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProcedure)))
            .andExpect(status().isOk());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
        Procedure testProcedure = procedureList.get(procedureList.size() - 1);
        assertThat(testProcedure.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProcedure.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingProcedure() throws Exception {
        int databaseSizeBeforeUpdate = procedureRepository.findAll().size();

        // Create the Procedure

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcedureMockMvc.perform(put("/api/procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procedure)))
            .andExpect(status().isBadRequest());

        // Validate the Procedure in the database
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProcedure() throws Exception {
        // Initialize the database
        procedureService.save(procedure);

        int databaseSizeBeforeDelete = procedureRepository.findAll().size();

        // Get the procedure
        restProcedureMockMvc.perform(delete("/api/procedures/{id}", procedure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Procedure> procedureList = procedureRepository.findAll();
        assertThat(procedureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Procedure.class);
        Procedure procedure1 = new Procedure();
        procedure1.setId(1L);
        Procedure procedure2 = new Procedure();
        procedure2.setId(procedure1.getId());
        assertThat(procedure1).isEqualTo(procedure2);
        procedure2.setId(2L);
        assertThat(procedure1).isNotEqualTo(procedure2);
        procedure1.setId(null);
        assertThat(procedure1).isNotEqualTo(procedure2);
    }
}
