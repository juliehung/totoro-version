package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.RegistrationDel;
import io.dentall.totoro.repository.RegistrationDelRepository;
import io.dentall.totoro.service.RegistrationDelService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.dto.RegistrationDelCriteria;
import io.dentall.totoro.service.RegistrationDelQueryService;

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

import io.dentall.totoro.domain.enumeration.RegistrationStatus;
/**
 * Test class for the RegistrationDelResource REST controller.
 *
 * @see RegistrationDelResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class RegistrationDelResourceIntTest {

    private static final RegistrationStatus DEFAULT_STATUS = RegistrationStatus.PENDING;
    private static final RegistrationStatus UPDATED_STATUS = RegistrationStatus.FINISHED;

    private static final Instant DEFAULT_ARRIVAL_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ARRIVAL_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ON_SITE = false;
    private static final Boolean UPDATED_ON_SITE = true;

    private static final Boolean DEFAULT_NO_CARD = false;
    private static final Boolean UPDATED_NO_CARD = true;

    private static final Long DEFAULT_APPOINTMENT_ID = 1L;
    private static final Long UPDATED_APPOINTMENT_ID = 2L;

    private static final Long DEFAULT_ACCOUNTING_ID = 1L;
    private static final Long UPDATED_ACCOUNTING_ID = 2L;

    @Autowired
    private RegistrationDelRepository registrationDelRepository;

    @Autowired
    private RegistrationDelService registrationDelService;

    @Autowired
    private RegistrationDelQueryService registrationDelQueryService;

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

    private MockMvc restRegistrationDelMockMvc;

    private RegistrationDel registrationDel;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegistrationDelResource registrationDelResource = new RegistrationDelResource(registrationDelService, registrationDelQueryService);
        this.restRegistrationDelMockMvc = MockMvcBuilders.standaloneSetup(registrationDelResource)
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
    public static RegistrationDel createEntity(EntityManager em) {
        RegistrationDel registrationDel = new RegistrationDel()
            .status(DEFAULT_STATUS)
            .arrivalTime(DEFAULT_ARRIVAL_TIME)
            .type(DEFAULT_TYPE)
            .onSite(DEFAULT_ON_SITE)
            .noCard(DEFAULT_NO_CARD)
            .appointmentId(DEFAULT_APPOINTMENT_ID)
            .accountingId(DEFAULT_ACCOUNTING_ID);
        return registrationDel;
    }

    @Before
    public void initTest() {
        registrationDel = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegistrationDel() throws Exception {
        int databaseSizeBeforeCreate = registrationDelRepository.findAll().size();

        // Create the RegistrationDel
        restRegistrationDelMockMvc.perform(post("/api/registration-dels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationDel)))
            .andExpect(status().isCreated());

        // Validate the RegistrationDel in the database
        List<RegistrationDel> registrationDelList = registrationDelRepository.findAll();
        assertThat(registrationDelList).hasSize(databaseSizeBeforeCreate + 1);
        RegistrationDel testRegistrationDel = registrationDelList.get(registrationDelList.size() - 1);
        assertThat(testRegistrationDel.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRegistrationDel.getArrivalTime()).isEqualTo(DEFAULT_ARRIVAL_TIME);
        assertThat(testRegistrationDel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRegistrationDel.isOnSite()).isEqualTo(DEFAULT_ON_SITE);
        assertThat(testRegistrationDel.isNoCard()).isEqualTo(DEFAULT_NO_CARD);
        assertThat(testRegistrationDel.getAppointmentId()).isEqualTo(DEFAULT_APPOINTMENT_ID);
        assertThat(testRegistrationDel.getAccountingId()).isEqualTo(DEFAULT_ACCOUNTING_ID);
    }

    @Test
    @Transactional
    public void createRegistrationDelWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registrationDelRepository.findAll().size();

        // Create the RegistrationDel with an existing ID
        registrationDel.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistrationDelMockMvc.perform(post("/api/registration-dels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationDel)))
            .andExpect(status().isBadRequest());

        // Validate the RegistrationDel in the database
        List<RegistrationDel> registrationDelList = registrationDelRepository.findAll();
        assertThat(registrationDelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = registrationDelRepository.findAll().size();
        // set the field null
        registrationDel.setStatus(null);

        // Create the RegistrationDel, which fails.

        restRegistrationDelMockMvc.perform(post("/api/registration-dels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationDel)))
            .andExpect(status().isBadRequest());

        List<RegistrationDel> registrationDelList = registrationDelRepository.findAll();
        assertThat(registrationDelList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegistrationDels() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList
        restRegistrationDelMockMvc.perform(get("/api/registration-dels?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registrationDel.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].arrivalTime").value(hasItem(DEFAULT_ARRIVAL_TIME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].onSite").value(hasItem(DEFAULT_ON_SITE.booleanValue())))
            .andExpect(jsonPath("$.[*].noCard").value(hasItem(DEFAULT_NO_CARD.booleanValue())))
            .andExpect(jsonPath("$.[*].appointmentId").value(hasItem(DEFAULT_APPOINTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].accountingId").value(hasItem(DEFAULT_ACCOUNTING_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getRegistrationDel() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get the registrationDel
        restRegistrationDelMockMvc.perform(get("/api/registration-dels/{id}", registrationDel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registrationDel.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.arrivalTime").value(DEFAULT_ARRIVAL_TIME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.onSite").value(DEFAULT_ON_SITE.booleanValue()))
            .andExpect(jsonPath("$.noCard").value(DEFAULT_NO_CARD.booleanValue()))
            .andExpect(jsonPath("$.appointmentId").value(DEFAULT_APPOINTMENT_ID.intValue()))
            .andExpect(jsonPath("$.accountingId").value(DEFAULT_ACCOUNTING_ID.intValue()));
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where status equals to DEFAULT_STATUS
        defaultRegistrationDelShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the registrationDelList where status equals to UPDATED_STATUS
        defaultRegistrationDelShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultRegistrationDelShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the registrationDelList where status equals to UPDATED_STATUS
        defaultRegistrationDelShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where status is not null
        defaultRegistrationDelShouldBeFound("status.specified=true");

        // Get all the registrationDelList where status is null
        defaultRegistrationDelShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByArrivalTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where arrivalTime equals to DEFAULT_ARRIVAL_TIME
        defaultRegistrationDelShouldBeFound("arrivalTime.equals=" + DEFAULT_ARRIVAL_TIME);

        // Get all the registrationDelList where arrivalTime equals to UPDATED_ARRIVAL_TIME
        defaultRegistrationDelShouldNotBeFound("arrivalTime.equals=" + UPDATED_ARRIVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByArrivalTimeIsInShouldWork() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where arrivalTime in DEFAULT_ARRIVAL_TIME or UPDATED_ARRIVAL_TIME
        defaultRegistrationDelShouldBeFound("arrivalTime.in=" + DEFAULT_ARRIVAL_TIME + "," + UPDATED_ARRIVAL_TIME);

        // Get all the registrationDelList where arrivalTime equals to UPDATED_ARRIVAL_TIME
        defaultRegistrationDelShouldNotBeFound("arrivalTime.in=" + UPDATED_ARRIVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByArrivalTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where arrivalTime is not null
        defaultRegistrationDelShouldBeFound("arrivalTime.specified=true");

        // Get all the registrationDelList where arrivalTime is null
        defaultRegistrationDelShouldNotBeFound("arrivalTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where type equals to DEFAULT_TYPE
        defaultRegistrationDelShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the registrationDelList where type equals to UPDATED_TYPE
        defaultRegistrationDelShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultRegistrationDelShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the registrationDelList where type equals to UPDATED_TYPE
        defaultRegistrationDelShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where type is not null
        defaultRegistrationDelShouldBeFound("type.specified=true");

        // Get all the registrationDelList where type is null
        defaultRegistrationDelShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByOnSiteIsEqualToSomething() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where onSite equals to DEFAULT_ON_SITE
        defaultRegistrationDelShouldBeFound("onSite.equals=" + DEFAULT_ON_SITE);

        // Get all the registrationDelList where onSite equals to UPDATED_ON_SITE
        defaultRegistrationDelShouldNotBeFound("onSite.equals=" + UPDATED_ON_SITE);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByOnSiteIsInShouldWork() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where onSite in DEFAULT_ON_SITE or UPDATED_ON_SITE
        defaultRegistrationDelShouldBeFound("onSite.in=" + DEFAULT_ON_SITE + "," + UPDATED_ON_SITE);

        // Get all the registrationDelList where onSite equals to UPDATED_ON_SITE
        defaultRegistrationDelShouldNotBeFound("onSite.in=" + UPDATED_ON_SITE);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByOnSiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where onSite is not null
        defaultRegistrationDelShouldBeFound("onSite.specified=true");

        // Get all the registrationDelList where onSite is null
        defaultRegistrationDelShouldNotBeFound("onSite.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByNoCardIsEqualToSomething() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where noCard equals to DEFAULT_NO_CARD
        defaultRegistrationDelShouldBeFound("noCard.equals=" + DEFAULT_NO_CARD);

        // Get all the registrationDelList where noCard equals to UPDATED_NO_CARD
        defaultRegistrationDelShouldNotBeFound("noCard.equals=" + UPDATED_NO_CARD);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByNoCardIsInShouldWork() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where noCard in DEFAULT_NO_CARD or UPDATED_NO_CARD
        defaultRegistrationDelShouldBeFound("noCard.in=" + DEFAULT_NO_CARD + "," + UPDATED_NO_CARD);

        // Get all the registrationDelList where noCard equals to UPDATED_NO_CARD
        defaultRegistrationDelShouldNotBeFound("noCard.in=" + UPDATED_NO_CARD);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByNoCardIsNullOrNotNull() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where noCard is not null
        defaultRegistrationDelShouldBeFound("noCard.specified=true");

        // Get all the registrationDelList where noCard is null
        defaultRegistrationDelShouldNotBeFound("noCard.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByAppointmentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where appointmentId equals to DEFAULT_APPOINTMENT_ID
        defaultRegistrationDelShouldBeFound("appointmentId.equals=" + DEFAULT_APPOINTMENT_ID);

        // Get all the registrationDelList where appointmentId equals to UPDATED_APPOINTMENT_ID
        defaultRegistrationDelShouldNotBeFound("appointmentId.equals=" + UPDATED_APPOINTMENT_ID);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByAppointmentIdIsInShouldWork() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where appointmentId in DEFAULT_APPOINTMENT_ID or UPDATED_APPOINTMENT_ID
        defaultRegistrationDelShouldBeFound("appointmentId.in=" + DEFAULT_APPOINTMENT_ID + "," + UPDATED_APPOINTMENT_ID);

        // Get all the registrationDelList where appointmentId equals to UPDATED_APPOINTMENT_ID
        defaultRegistrationDelShouldNotBeFound("appointmentId.in=" + UPDATED_APPOINTMENT_ID);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByAppointmentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where appointmentId is not null
        defaultRegistrationDelShouldBeFound("appointmentId.specified=true");

        // Get all the registrationDelList where appointmentId is null
        defaultRegistrationDelShouldNotBeFound("appointmentId.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByAppointmentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where appointmentId greater than or equals to DEFAULT_APPOINTMENT_ID
        defaultRegistrationDelShouldBeFound("appointmentId.greaterOrEqualThan=" + DEFAULT_APPOINTMENT_ID);

        // Get all the registrationDelList where appointmentId greater than or equals to UPDATED_APPOINTMENT_ID
        defaultRegistrationDelShouldNotBeFound("appointmentId.greaterOrEqualThan=" + UPDATED_APPOINTMENT_ID);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByAppointmentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where appointmentId less than or equals to DEFAULT_APPOINTMENT_ID
        defaultRegistrationDelShouldNotBeFound("appointmentId.lessThan=" + DEFAULT_APPOINTMENT_ID);

        // Get all the registrationDelList where appointmentId less than or equals to UPDATED_APPOINTMENT_ID
        defaultRegistrationDelShouldBeFound("appointmentId.lessThan=" + UPDATED_APPOINTMENT_ID);
    }


    @Test
    @Transactional
    public void getAllRegistrationDelsByAccountingIdIsEqualToSomething() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where accountingId equals to DEFAULT_ACCOUNTING_ID
        defaultRegistrationDelShouldBeFound("accountingId.equals=" + DEFAULT_ACCOUNTING_ID);

        // Get all the registrationDelList where accountingId equals to UPDATED_ACCOUNTING_ID
        defaultRegistrationDelShouldNotBeFound("accountingId.equals=" + UPDATED_ACCOUNTING_ID);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByAccountingIdIsInShouldWork() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where accountingId in DEFAULT_ACCOUNTING_ID or UPDATED_ACCOUNTING_ID
        defaultRegistrationDelShouldBeFound("accountingId.in=" + DEFAULT_ACCOUNTING_ID + "," + UPDATED_ACCOUNTING_ID);

        // Get all the registrationDelList where accountingId equals to UPDATED_ACCOUNTING_ID
        defaultRegistrationDelShouldNotBeFound("accountingId.in=" + UPDATED_ACCOUNTING_ID);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByAccountingIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where accountingId is not null
        defaultRegistrationDelShouldBeFound("accountingId.specified=true");

        // Get all the registrationDelList where accountingId is null
        defaultRegistrationDelShouldNotBeFound("accountingId.specified=false");
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByAccountingIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where accountingId greater than or equals to DEFAULT_ACCOUNTING_ID
        defaultRegistrationDelShouldBeFound("accountingId.greaterOrEqualThan=" + DEFAULT_ACCOUNTING_ID);

        // Get all the registrationDelList where accountingId greater than or equals to UPDATED_ACCOUNTING_ID
        defaultRegistrationDelShouldNotBeFound("accountingId.greaterOrEqualThan=" + UPDATED_ACCOUNTING_ID);
    }

    @Test
    @Transactional
    public void getAllRegistrationDelsByAccountingIdIsLessThanSomething() throws Exception {
        // Initialize the database
        registrationDelRepository.saveAndFlush(registrationDel);

        // Get all the registrationDelList where accountingId less than or equals to DEFAULT_ACCOUNTING_ID
        defaultRegistrationDelShouldNotBeFound("accountingId.lessThan=" + DEFAULT_ACCOUNTING_ID);

        // Get all the registrationDelList where accountingId less than or equals to UPDATED_ACCOUNTING_ID
        defaultRegistrationDelShouldBeFound("accountingId.lessThan=" + UPDATED_ACCOUNTING_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultRegistrationDelShouldBeFound(String filter) throws Exception {
        restRegistrationDelMockMvc.perform(get("/api/registration-dels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registrationDel.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].arrivalTime").value(hasItem(DEFAULT_ARRIVAL_TIME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].onSite").value(hasItem(DEFAULT_ON_SITE.booleanValue())))
            .andExpect(jsonPath("$.[*].noCard").value(hasItem(DEFAULT_NO_CARD.booleanValue())))
            .andExpect(jsonPath("$.[*].appointmentId").value(hasItem(DEFAULT_APPOINTMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].accountingId").value(hasItem(DEFAULT_ACCOUNTING_ID.intValue())));

        // Check, that the count call also returns 1
        restRegistrationDelMockMvc.perform(get("/api/registration-dels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultRegistrationDelShouldNotBeFound(String filter) throws Exception {
        restRegistrationDelMockMvc.perform(get("/api/registration-dels?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restRegistrationDelMockMvc.perform(get("/api/registration-dels/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingRegistrationDel() throws Exception {
        // Get the registrationDel
        restRegistrationDelMockMvc.perform(get("/api/registration-dels/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegistrationDel() throws Exception {
        // Initialize the database
        registrationDelService.save(registrationDel);

        int databaseSizeBeforeUpdate = registrationDelRepository.findAll().size();

        // Update the registrationDel
        RegistrationDel updatedRegistrationDel = registrationDelRepository.findById(registrationDel.getId()).get();
        // Disconnect from session so that the updates on updatedRegistrationDel are not directly saved in db
        em.detach(updatedRegistrationDel);
        updatedRegistrationDel
            .status(UPDATED_STATUS)
            .arrivalTime(UPDATED_ARRIVAL_TIME)
            .type(UPDATED_TYPE)
            .onSite(UPDATED_ON_SITE)
            .noCard(UPDATED_NO_CARD)
            .appointmentId(UPDATED_APPOINTMENT_ID)
            .accountingId(UPDATED_ACCOUNTING_ID);

        restRegistrationDelMockMvc.perform(put("/api/registration-dels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegistrationDel)))
            .andExpect(status().isOk());

        // Validate the RegistrationDel in the database
        List<RegistrationDel> registrationDelList = registrationDelRepository.findAll();
        assertThat(registrationDelList).hasSize(databaseSizeBeforeUpdate);
        RegistrationDel testRegistrationDel = registrationDelList.get(registrationDelList.size() - 1);
        assertThat(testRegistrationDel.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRegistrationDel.getArrivalTime()).isEqualTo(UPDATED_ARRIVAL_TIME);
        assertThat(testRegistrationDel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRegistrationDel.isOnSite()).isEqualTo(UPDATED_ON_SITE);
        assertThat(testRegistrationDel.isNoCard()).isEqualTo(UPDATED_NO_CARD);
        assertThat(testRegistrationDel.getAppointmentId()).isEqualTo(UPDATED_APPOINTMENT_ID);
        assertThat(testRegistrationDel.getAccountingId()).isEqualTo(UPDATED_ACCOUNTING_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingRegistrationDel() throws Exception {
        int databaseSizeBeforeUpdate = registrationDelRepository.findAll().size();

        // Create the RegistrationDel

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistrationDelMockMvc.perform(put("/api/registration-dels")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registrationDel)))
            .andExpect(status().isBadRequest());

        // Validate the RegistrationDel in the database
        List<RegistrationDel> registrationDelList = registrationDelRepository.findAll();
        assertThat(registrationDelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegistrationDel() throws Exception {
        // Initialize the database
        registrationDelService.save(registrationDel);

        int databaseSizeBeforeDelete = registrationDelRepository.findAll().size();

        // Get the registrationDel
        restRegistrationDelMockMvc.perform(delete("/api/registration-dels/{id}", registrationDel.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RegistrationDel> registrationDelList = registrationDelRepository.findAll();
        assertThat(registrationDelList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegistrationDel.class);
        RegistrationDel registrationDel1 = new RegistrationDel();
        registrationDel1.setId(1L);
        RegistrationDel registrationDel2 = new RegistrationDel();
        registrationDel2.setId(registrationDel1.getId());
        assertThat(registrationDel1).isEqualTo(registrationDel2);
        registrationDel2.setId(2L);
        assertThat(registrationDel1).isNotEqualTo(registrationDel2);
        registrationDel1.setId(null);
        assertThat(registrationDel1).isNotEqualTo(registrationDel2);
    }
}
