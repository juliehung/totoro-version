package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.Ledger;
import io.dentall.totoro.domain.TreatmentPlan;
import io.dentall.totoro.repository.LedgerRepository;
import io.dentall.totoro.service.LedgerService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.dto.LedgerCriteria;
import io.dentall.totoro.service.LedgerQueryService;

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
 * Test class for the LedgerResource REST controller.
 *
 * @see LedgerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class LedgerResourceIntTest {

    private static final Double DEFAULT_AMOUNT = 1D;
    private static final Double UPDATED_AMOUNT = 2D;

    private static final Double DEFAULT_CHARGE = 1D;
    private static final Double UPDATED_CHARGE = 2D;

    private static final Double DEFAULT_ARREARS = 1D;
    private static final Double UPDATED_ARREARS = 2D;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_DOCTOR = "AAAAAAAAAA";
    private static final String UPDATED_DOCTOR = "BBBBBBBBBB";

    @Autowired
    private LedgerRepository ledgerRepository;

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private LedgerQueryService ledgerQueryService;

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

    private MockMvc restLedgerMockMvc;

    private Ledger ledger;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LedgerResource ledgerResource = new LedgerResource(ledgerService, ledgerQueryService);
        this.restLedgerMockMvc = MockMvcBuilders.standaloneSetup(ledgerResource)
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
    public static Ledger createEntity(EntityManager em) {
        Ledger ledger = new Ledger()
            .amount(DEFAULT_AMOUNT)
            .charge(DEFAULT_CHARGE)
            .arrears(DEFAULT_ARREARS)
            .note(DEFAULT_NOTE)
            .doctor(DEFAULT_DOCTOR);
        return ledger;
    }

    @Before
    public void initTest() {
        ledger = createEntity(em);
    }

    @Test
    @Transactional
    public void createLedger() throws Exception {
        int databaseSizeBeforeCreate = ledgerRepository.findAll().size();

        // Create the Ledger
        restLedgerMockMvc.perform(post("/api/ledgers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ledger)))
            .andExpect(status().isCreated());

        // Validate the Ledger in the database
        List<Ledger> ledgerList = ledgerRepository.findAll();
        assertThat(ledgerList).hasSize(databaseSizeBeforeCreate + 1);
        Ledger testLedger = ledgerList.get(ledgerList.size() - 1);
        assertThat(testLedger.getAmount()).isEqualTo(DEFAULT_AMOUNT);
        assertThat(testLedger.getCharge()).isEqualTo(DEFAULT_CHARGE);
        assertThat(testLedger.getArrears()).isEqualTo(DEFAULT_ARREARS);
        assertThat(testLedger.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testLedger.getDoctor()).isEqualTo(DEFAULT_DOCTOR);
    }

    @Test
    @Transactional
    public void createLedgerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = ledgerRepository.findAll().size();

        // Create the Ledger with an existing ID
        ledger.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLedgerMockMvc.perform(post("/api/ledgers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ledger)))
            .andExpect(status().isBadRequest());

        // Validate the Ledger in the database
        List<Ledger> ledgerList = ledgerRepository.findAll();
        assertThat(ledgerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = ledgerRepository.findAll().size();
        // set the field null
        ledger.setAmount(null);

        // Create the Ledger, which fails.

        restLedgerMockMvc.perform(post("/api/ledgers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ledger)))
            .andExpect(status().isBadRequest());

        List<Ledger> ledgerList = ledgerRepository.findAll();
        assertThat(ledgerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChargeIsRequired() throws Exception {
        int databaseSizeBeforeTest = ledgerRepository.findAll().size();
        // set the field null
        ledger.setCharge(null);

        // Create the Ledger, which fails.

        restLedgerMockMvc.perform(post("/api/ledgers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ledger)))
            .andExpect(status().isBadRequest());

        List<Ledger> ledgerList = ledgerRepository.findAll();
        assertThat(ledgerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkArrearsIsRequired() throws Exception {
        int databaseSizeBeforeTest = ledgerRepository.findAll().size();
        // set the field null
        ledger.setArrears(null);

        // Create the Ledger, which fails.

        restLedgerMockMvc.perform(post("/api/ledgers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ledger)))
            .andExpect(status().isBadRequest());

        List<Ledger> ledgerList = ledgerRepository.findAll();
        assertThat(ledgerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLedgers() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList
        restLedgerMockMvc.perform(get("/api/ledgers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ledger.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].charge").value(hasItem(DEFAULT_CHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].arrears").value(hasItem(DEFAULT_ARREARS.doubleValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].doctor").value(hasItem(DEFAULT_DOCTOR.toString())));
    }
    
    @Test
    @Transactional
    public void getLedger() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get the ledger
        restLedgerMockMvc.perform(get("/api/ledgers/{id}", ledger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ledger.getId().intValue()))
            .andExpect(jsonPath("$.amount").value(DEFAULT_AMOUNT.doubleValue()))
            .andExpect(jsonPath("$.charge").value(DEFAULT_CHARGE.doubleValue()))
            .andExpect(jsonPath("$.arrears").value(DEFAULT_ARREARS.doubleValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.doctor").value(DEFAULT_DOCTOR.toString()));
    }

    @Test
    @Transactional
    public void getAllLedgersByAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList where amount equals to DEFAULT_AMOUNT
        defaultLedgerShouldBeFound("amount.equals=" + DEFAULT_AMOUNT);

        // Get all the ledgerList where amount equals to UPDATED_AMOUNT
        defaultLedgerShouldNotBeFound("amount.equals=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLedgersByAmountIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList where amount in DEFAULT_AMOUNT or UPDATED_AMOUNT
        defaultLedgerShouldBeFound("amount.in=" + DEFAULT_AMOUNT + "," + UPDATED_AMOUNT);

        // Get all the ledgerList where amount equals to UPDATED_AMOUNT
        defaultLedgerShouldNotBeFound("amount.in=" + UPDATED_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllLedgersByAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList where amount is not null
        defaultLedgerShouldBeFound("amount.specified=true");

        // Get all the ledgerList where amount is null
        defaultLedgerShouldNotBeFound("amount.specified=false");
    }

    @Test
    @Transactional
    public void getAllLedgersByChargeIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList where charge equals to DEFAULT_CHARGE
        defaultLedgerShouldBeFound("charge.equals=" + DEFAULT_CHARGE);

        // Get all the ledgerList where charge equals to UPDATED_CHARGE
        defaultLedgerShouldNotBeFound("charge.equals=" + UPDATED_CHARGE);
    }

    @Test
    @Transactional
    public void getAllLedgersByChargeIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList where charge in DEFAULT_CHARGE or UPDATED_CHARGE
        defaultLedgerShouldBeFound("charge.in=" + DEFAULT_CHARGE + "," + UPDATED_CHARGE);

        // Get all the ledgerList where charge equals to UPDATED_CHARGE
        defaultLedgerShouldNotBeFound("charge.in=" + UPDATED_CHARGE);
    }

    @Test
    @Transactional
    public void getAllLedgersByChargeIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList where charge is not null
        defaultLedgerShouldBeFound("charge.specified=true");

        // Get all the ledgerList where charge is null
        defaultLedgerShouldNotBeFound("charge.specified=false");
    }

    @Test
    @Transactional
    public void getAllLedgersByArrearsIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList where arrears equals to DEFAULT_ARREARS
        defaultLedgerShouldBeFound("arrears.equals=" + DEFAULT_ARREARS);

        // Get all the ledgerList where arrears equals to UPDATED_ARREARS
        defaultLedgerShouldNotBeFound("arrears.equals=" + UPDATED_ARREARS);
    }

    @Test
    @Transactional
    public void getAllLedgersByArrearsIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList where arrears in DEFAULT_ARREARS or UPDATED_ARREARS
        defaultLedgerShouldBeFound("arrears.in=" + DEFAULT_ARREARS + "," + UPDATED_ARREARS);

        // Get all the ledgerList where arrears equals to UPDATED_ARREARS
        defaultLedgerShouldNotBeFound("arrears.in=" + UPDATED_ARREARS);
    }

    @Test
    @Transactional
    public void getAllLedgersByArrearsIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList where arrears is not null
        defaultLedgerShouldBeFound("arrears.specified=true");

        // Get all the ledgerList where arrears is null
        defaultLedgerShouldNotBeFound("arrears.specified=false");
    }

    @Test
    @Transactional
    public void getAllLedgersByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList where note equals to DEFAULT_NOTE
        defaultLedgerShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the ledgerList where note equals to UPDATED_NOTE
        defaultLedgerShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllLedgersByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultLedgerShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the ledgerList where note equals to UPDATED_NOTE
        defaultLedgerShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllLedgersByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList where note is not null
        defaultLedgerShouldBeFound("note.specified=true");

        // Get all the ledgerList where note is null
        defaultLedgerShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    public void getAllLedgersByDoctorIsEqualToSomething() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList where doctor equals to DEFAULT_DOCTOR
        defaultLedgerShouldBeFound("doctor.equals=" + DEFAULT_DOCTOR);

        // Get all the ledgerList where doctor equals to UPDATED_DOCTOR
        defaultLedgerShouldNotBeFound("doctor.equals=" + UPDATED_DOCTOR);
    }

    @Test
    @Transactional
    public void getAllLedgersByDoctorIsInShouldWork() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList where doctor in DEFAULT_DOCTOR or UPDATED_DOCTOR
        defaultLedgerShouldBeFound("doctor.in=" + DEFAULT_DOCTOR + "," + UPDATED_DOCTOR);

        // Get all the ledgerList where doctor equals to UPDATED_DOCTOR
        defaultLedgerShouldNotBeFound("doctor.in=" + UPDATED_DOCTOR);
    }

    @Test
    @Transactional
    public void getAllLedgersByDoctorIsNullOrNotNull() throws Exception {
        // Initialize the database
        ledgerRepository.saveAndFlush(ledger);

        // Get all the ledgerList where doctor is not null
        defaultLedgerShouldBeFound("doctor.specified=true");

        // Get all the ledgerList where doctor is null
        defaultLedgerShouldNotBeFound("doctor.specified=false");
    }

    @Test
    @Transactional
    public void getAllLedgersByTreatmentPlanIsEqualToSomething() throws Exception {
        // Initialize the database
        TreatmentPlan treatmentPlan = TreatmentPlanResourceIntTest.createEntity(em);
        em.persist(treatmentPlan);
        em.flush();
        ledger.setTreatmentPlan(treatmentPlan);
        ledgerRepository.saveAndFlush(ledger);
        Long treatmentPlanId = treatmentPlan.getId();

        // Get all the ledgerList where treatmentPlan equals to treatmentPlanId
        defaultLedgerShouldBeFound("treatmentPlanId.equals=" + treatmentPlanId);

        // Get all the ledgerList where treatmentPlan equals to treatmentPlanId + 1
        defaultLedgerShouldNotBeFound("treatmentPlanId.equals=" + (treatmentPlanId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultLedgerShouldBeFound(String filter) throws Exception {
        restLedgerMockMvc.perform(get("/api/ledgers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ledger.getId().intValue())))
            .andExpect(jsonPath("$.[*].amount").value(hasItem(DEFAULT_AMOUNT.doubleValue())))
            .andExpect(jsonPath("$.[*].charge").value(hasItem(DEFAULT_CHARGE.doubleValue())))
            .andExpect(jsonPath("$.[*].arrears").value(hasItem(DEFAULT_ARREARS.doubleValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].doctor").value(hasItem(DEFAULT_DOCTOR.toString())));

        // Check, that the count call also returns 1
        restLedgerMockMvc.perform(get("/api/ledgers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultLedgerShouldNotBeFound(String filter) throws Exception {
        restLedgerMockMvc.perform(get("/api/ledgers?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLedgerMockMvc.perform(get("/api/ledgers/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLedger() throws Exception {
        // Get the ledger
        restLedgerMockMvc.perform(get("/api/ledgers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLedger() throws Exception {
        // Initialize the database
        ledgerService.save(ledger);

        int databaseSizeBeforeUpdate = ledgerRepository.findAll().size();

        // Update the ledger
        Ledger updatedLedger = ledgerRepository.findById(ledger.getId()).get();
        // Disconnect from session so that the updates on updatedLedger are not directly saved in db
        em.detach(updatedLedger);
        updatedLedger
            .amount(UPDATED_AMOUNT)
            .charge(UPDATED_CHARGE)
            .arrears(UPDATED_ARREARS)
            .note(UPDATED_NOTE)
            .doctor(UPDATED_DOCTOR);

        restLedgerMockMvc.perform(put("/api/ledgers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLedger)))
            .andExpect(status().isOk());

        // Validate the Ledger in the database
        List<Ledger> ledgerList = ledgerRepository.findAll();
        assertThat(ledgerList).hasSize(databaseSizeBeforeUpdate);
        Ledger testLedger = ledgerList.get(ledgerList.size() - 1);
        assertThat(testLedger.getAmount()).isEqualTo(UPDATED_AMOUNT);
        assertThat(testLedger.getCharge()).isEqualTo(UPDATED_CHARGE);
        assertThat(testLedger.getArrears()).isEqualTo(UPDATED_ARREARS);
        assertThat(testLedger.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testLedger.getDoctor()).isEqualTo(UPDATED_DOCTOR);
    }

    @Test
    @Transactional
    public void updateNonExistingLedger() throws Exception {
        int databaseSizeBeforeUpdate = ledgerRepository.findAll().size();

        // Create the Ledger

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLedgerMockMvc.perform(put("/api/ledgers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ledger)))
            .andExpect(status().isBadRequest());

        // Validate the Ledger in the database
        List<Ledger> ledgerList = ledgerRepository.findAll();
        assertThat(ledgerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLedger() throws Exception {
        // Initialize the database
        ledgerService.save(ledger);

        int databaseSizeBeforeDelete = ledgerRepository.findAll().size();

        // Get the ledger
        restLedgerMockMvc.perform(delete("/api/ledgers/{id}", ledger.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Ledger> ledgerList = ledgerRepository.findAll();
        assertThat(ledgerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ledger.class);
        Ledger ledger1 = new Ledger();
        ledger1.setId(1L);
        Ledger ledger2 = new Ledger();
        ledger2.setId(ledger1.getId());
        assertThat(ledger1).isEqualTo(ledger2);
        ledger2.setId(2L);
        assertThat(ledger1).isNotEqualTo(ledger2);
        ledger1.setId(null);
        assertThat(ledger1).isNotEqualTo(ledger2);
    }
}
