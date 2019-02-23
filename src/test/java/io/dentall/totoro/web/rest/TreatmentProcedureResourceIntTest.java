package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.domain.NHIProcedure;
import io.dentall.totoro.domain.TreatmentTask;
import io.dentall.totoro.domain.Procedure;
import io.dentall.totoro.domain.Appointment;
import io.dentall.totoro.domain.Registration;
import io.dentall.totoro.domain.Tooth;
import io.dentall.totoro.domain.Todo;
import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.repository.TreatmentProcedureRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.TreatmentProcedureService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.TreatmentProcedureQueryService;

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

import io.dentall.totoro.domain.enumeration.TreatmentProcedureStatus;
/**
 * Test class for the TreatmentProcedureResource REST controller.
 *
 * @see TreatmentProcedureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class TreatmentProcedureResourceIntTest {

    private static final TreatmentProcedureStatus DEFAULT_STATUS = TreatmentProcedureStatus.PLANNED;
    private static final TreatmentProcedureStatus UPDATED_STATUS = TreatmentProcedureStatus.IN_PROGRESS;

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Instant DEFAULT_COMPLETED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_COMPLETED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    @Autowired
    private TreatmentProcedureRepository treatmentProcedureRepository;

    @Autowired
    private TreatmentProcedureService treatmentProcedureService;

    @Autowired
    private TreatmentProcedureQueryService treatmentProcedureQueryService;

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

    @Autowired
    private UserRepository userRepository;

    private MockMvc restTreatmentProcedureMockMvc;

    private TreatmentProcedure treatmentProcedure;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TreatmentProcedureResource treatmentProcedureResource = new TreatmentProcedureResource(treatmentProcedureService, treatmentProcedureQueryService);
        this.restTreatmentProcedureMockMvc = MockMvcBuilders.standaloneSetup(treatmentProcedureResource)
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
    public static TreatmentProcedure createEntity(EntityManager em) {
        TreatmentProcedure treatmentProcedure = new TreatmentProcedure()
            .status(DEFAULT_STATUS)
            .quantity(DEFAULT_QUANTITY)
            .total(DEFAULT_TOTAL)
            .note(DEFAULT_NOTE)
            .completedDate(DEFAULT_COMPLETED_DATE)
            .price(DEFAULT_PRICE);
        return treatmentProcedure;
    }

    @Before
    public void initTest() {
        treatmentProcedure = createEntity(em);
    }

    @Test
    @Transactional
    public void createTreatmentProcedure() throws Exception {
        int databaseSizeBeforeCreate = treatmentProcedureRepository.findAll().size();

        // Create the TreatmentProcedure
        restTreatmentProcedureMockMvc.perform(post("/api/treatment-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentProcedure)))
            .andExpect(status().isCreated());

        // Validate the TreatmentProcedure in the database
        List<TreatmentProcedure> treatmentProcedureList = treatmentProcedureRepository.findAll();
        assertThat(treatmentProcedureList).hasSize(databaseSizeBeforeCreate + 1);
        TreatmentProcedure testTreatmentProcedure = treatmentProcedureList.get(treatmentProcedureList.size() - 1);
        assertThat(testTreatmentProcedure.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testTreatmentProcedure.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testTreatmentProcedure.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testTreatmentProcedure.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testTreatmentProcedure.getCompletedDate()).isEqualTo(DEFAULT_COMPLETED_DATE);
        assertThat(testTreatmentProcedure.getPrice()).isEqualTo(DEFAULT_PRICE);
    }

    @Test
    @Transactional
    public void createTreatmentProcedureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = treatmentProcedureRepository.findAll().size();

        // Create the TreatmentProcedure with an existing ID
        treatmentProcedure.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTreatmentProcedureMockMvc.perform(post("/api/treatment-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentProcedure)))
            .andExpect(status().isBadRequest());

        // Validate the TreatmentProcedure in the database
        List<TreatmentProcedure> treatmentProcedureList = treatmentProcedureRepository.findAll();
        assertThat(treatmentProcedureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = treatmentProcedureRepository.findAll().size();
        // set the field null
        treatmentProcedure.setStatus(null);

        // Create the TreatmentProcedure, which fails.

        restTreatmentProcedureMockMvc.perform(post("/api/treatment-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentProcedure)))
            .andExpect(status().isBadRequest());

        List<TreatmentProcedure> treatmentProcedureList = treatmentProcedureRepository.findAll();
        assertThat(treatmentProcedureList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTreatmentProcedures() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList
        restTreatmentProcedureMockMvc.perform(get("/api/treatment-procedures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatmentProcedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].completedDate").value(hasItem(DEFAULT_COMPLETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getTreatmentProcedure() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get the treatmentProcedure
        restTreatmentProcedureMockMvc.perform(get("/api/treatment-procedures/{id}", treatmentProcedure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(treatmentProcedure.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.completedDate").value(DEFAULT_COMPLETED_DATE.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where status equals to DEFAULT_STATUS
        defaultTreatmentProcedureShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the treatmentProcedureList where status equals to UPDATED_STATUS
        defaultTreatmentProcedureShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultTreatmentProcedureShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the treatmentProcedureList where status equals to UPDATED_STATUS
        defaultTreatmentProcedureShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where status is not null
        defaultTreatmentProcedureShouldBeFound("status.specified=true");

        // Get all the treatmentProcedureList where status is null
        defaultTreatmentProcedureShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where quantity equals to DEFAULT_QUANTITY
        defaultTreatmentProcedureShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the treatmentProcedureList where quantity equals to UPDATED_QUANTITY
        defaultTreatmentProcedureShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultTreatmentProcedureShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the treatmentProcedureList where quantity equals to UPDATED_QUANTITY
        defaultTreatmentProcedureShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where quantity is not null
        defaultTreatmentProcedureShouldBeFound("quantity.specified=true");

        // Get all the treatmentProcedureList where quantity is null
        defaultTreatmentProcedureShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByQuantityIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where quantity greater than or equals to DEFAULT_QUANTITY
        defaultTreatmentProcedureShouldBeFound("quantity.greaterOrEqualThan=" + DEFAULT_QUANTITY);

        // Get all the treatmentProcedureList where quantity greater than or equals to UPDATED_QUANTITY
        defaultTreatmentProcedureShouldNotBeFound("quantity.greaterOrEqualThan=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByQuantityIsLessThanSomething() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where quantity less than or equals to DEFAULT_QUANTITY
        defaultTreatmentProcedureShouldNotBeFound("quantity.lessThan=" + DEFAULT_QUANTITY);

        // Get all the treatmentProcedureList where quantity less than or equals to UPDATED_QUANTITY
        defaultTreatmentProcedureShouldBeFound("quantity.lessThan=" + UPDATED_QUANTITY);
    }


    @Test
    @Transactional
    public void getAllTreatmentProceduresByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where total equals to DEFAULT_TOTAL
        defaultTreatmentProcedureShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the treatmentProcedureList where total equals to UPDATED_TOTAL
        defaultTreatmentProcedureShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultTreatmentProcedureShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the treatmentProcedureList where total equals to UPDATED_TOTAL
        defaultTreatmentProcedureShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where total is not null
        defaultTreatmentProcedureShouldBeFound("total.specified=true");

        // Get all the treatmentProcedureList where total is null
        defaultTreatmentProcedureShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where note equals to DEFAULT_NOTE
        defaultTreatmentProcedureShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the treatmentProcedureList where note equals to UPDATED_NOTE
        defaultTreatmentProcedureShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultTreatmentProcedureShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the treatmentProcedureList where note equals to UPDATED_NOTE
        defaultTreatmentProcedureShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where note is not null
        defaultTreatmentProcedureShouldBeFound("note.specified=true");

        // Get all the treatmentProcedureList where note is null
        defaultTreatmentProcedureShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByCompletedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where completedDate equals to DEFAULT_COMPLETED_DATE
        defaultTreatmentProcedureShouldBeFound("completedDate.equals=" + DEFAULT_COMPLETED_DATE);

        // Get all the treatmentProcedureList where completedDate equals to UPDATED_COMPLETED_DATE
        defaultTreatmentProcedureShouldNotBeFound("completedDate.equals=" + UPDATED_COMPLETED_DATE);
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByCompletedDateIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where completedDate in DEFAULT_COMPLETED_DATE or UPDATED_COMPLETED_DATE
        defaultTreatmentProcedureShouldBeFound("completedDate.in=" + DEFAULT_COMPLETED_DATE + "," + UPDATED_COMPLETED_DATE);

        // Get all the treatmentProcedureList where completedDate equals to UPDATED_COMPLETED_DATE
        defaultTreatmentProcedureShouldNotBeFound("completedDate.in=" + UPDATED_COMPLETED_DATE);
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByCompletedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where completedDate is not null
        defaultTreatmentProcedureShouldBeFound("completedDate.specified=true");

        // Get all the treatmentProcedureList where completedDate is null
        defaultTreatmentProcedureShouldNotBeFound("completedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where price equals to DEFAULT_PRICE
        defaultTreatmentProcedureShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the treatmentProcedureList where price equals to UPDATED_PRICE
        defaultTreatmentProcedureShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultTreatmentProcedureShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the treatmentProcedureList where price equals to UPDATED_PRICE
        defaultTreatmentProcedureShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList where price is not null
        defaultTreatmentProcedureShouldBeFound("price.specified=true");

        // Get all the treatmentProcedureList where price is null
        defaultTreatmentProcedureShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllTreatmentProceduresByNhiProcedureIsEqualToSomething() throws Exception {
        // Initialize the database
        NHIProcedure nhiProcedure = NHIProcedureResourceIntTest.createEntity(em);
        em.persist(nhiProcedure);
        em.flush();
        treatmentProcedure.setNhiProcedure(nhiProcedure);
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);
        Long nhiProcedureId = nhiProcedure.getId();

        // Get all the treatmentProcedureList where nhiProcedure equals to nhiProcedureId
        defaultTreatmentProcedureShouldBeFound("nhiProcedureId.equals=" + nhiProcedureId);

        // Get all the treatmentProcedureList where nhiProcedure equals to nhiProcedureId + 1
        defaultTreatmentProcedureShouldNotBeFound("nhiProcedureId.equals=" + (nhiProcedureId + 1));
    }


    @Test
    @Transactional
    public void getAllTreatmentProceduresByTreatmentTaskIsEqualToSomething() throws Exception {
        // Initialize the database
        TreatmentTask treatmentTask = TreatmentTaskResourceIntTest.createEntity(em);
        em.persist(treatmentTask);
        em.flush();
        treatmentProcedure.setTreatmentTask(treatmentTask);
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);
        Long treatmentTaskId = treatmentTask.getId();

        // Get all the treatmentProcedureList where treatmentTask equals to treatmentTaskId
        defaultTreatmentProcedureShouldBeFound("treatmentTaskId.equals=" + treatmentTaskId);

        // Get all the treatmentProcedureList where treatmentTask equals to treatmentTaskId + 1
        defaultTreatmentProcedureShouldNotBeFound("treatmentTaskId.equals=" + (treatmentTaskId + 1));
    }


    @Test
    @Transactional
    public void getAllTreatmentProceduresByProcedureIsEqualToSomething() throws Exception {
        // Initialize the database
        Procedure procedure = ProcedureResourceIntTest.createEntity(em);
        em.persist(procedure);
        em.flush();
        treatmentProcedure.setProcedure(procedure);
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);
        Long procedureId = procedure.getId();

        // Get all the treatmentProcedureList where procedure equals to procedureId
        defaultTreatmentProcedureShouldBeFound("procedureId.equals=" + procedureId);

        // Get all the treatmentProcedureList where procedure equals to procedureId + 1
        defaultTreatmentProcedureShouldNotBeFound("procedureId.equals=" + (procedureId + 1));
    }


    @Test
    @Transactional
    public void getAllTreatmentProceduresByAppointmentIsEqualToSomething() throws Exception {
        // Initialize the database
        Appointment appointment = AppointmentResourceIntTest.createEntity(em);
        appointment.setDoctor(userRepository.save(UserResourceIntTest.createEntity(em)).getExtendUser());
        em.persist(appointment);
        em.flush();
        treatmentProcedure.setAppointment(appointment);
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);
        Long appointmentId = appointment.getId();

        // Get all the treatmentProcedureList where appointment equals to appointmentId
        defaultTreatmentProcedureShouldBeFound("appointmentId.equals=" + appointmentId);

        // Get all the treatmentProcedureList where appointment equals to appointmentId + 1
        defaultTreatmentProcedureShouldNotBeFound("appointmentId.equals=" + (appointmentId + 1));
    }


    @Test
    @Transactional
    public void getAllTreatmentProceduresByRegistrationIsEqualToSomething() throws Exception {
        // Initialize the database
        Registration registration = RegistrationResourceIntTest.createEntity(em);
        em.persist(registration);
        em.flush();
        treatmentProcedure.setRegistration(registration);
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);
        Long registrationId = registration.getId();

        // Get all the treatmentProcedureList where registration equals to registrationId
        defaultTreatmentProcedureShouldBeFound("registrationId.equals=" + registrationId);

        // Get all the treatmentProcedureList where registration equals to registrationId + 1
        defaultTreatmentProcedureShouldNotBeFound("registrationId.equals=" + (registrationId + 1));
    }


    @Test
    @Transactional
    public void getAllTreatmentProceduresByToothIsEqualToSomething() throws Exception {
        // Initialize the database
        Tooth tooth = ToothResourceIntTest.createEntity(em);
        em.persist(tooth);
        em.flush();
        treatmentProcedure.addTooth(tooth);
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);
        Long toothId = tooth.getId();

        // Get all the treatmentProcedureList where tooth equals to toothId
        defaultTreatmentProcedureShouldBeFound("toothId.equals=" + toothId);

        // Get all the treatmentProcedureList where tooth equals to toothId + 1
        defaultTreatmentProcedureShouldNotBeFound("toothId.equals=" + (toothId + 1));
    }


    @Test
    @Transactional
    public void getAllTreatmentProceduresByTodoIsEqualToSomething() throws Exception {
        // Initialize the database
        Todo todo = TodoResourceIntTest.createEntity(em);
        em.persist(todo);
        em.flush();
        treatmentProcedure.setTodo(todo);
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);
        Long todoId = todo.getId();

        // Get all the treatmentProcedureList where todo equals to todoId
        defaultTreatmentProcedureShouldBeFound("todoId.equals=" + todoId);

        // Get all the treatmentProcedureList where todo equals to todoId + 1
        defaultTreatmentProcedureShouldNotBeFound("todoId.equals=" + (todoId + 1));
    }


    @Test
    @Transactional
    public void getAllTreatmentProceduresByDisposalIsEqualToSomething() throws Exception {
        // Initialize the database
        Disposal disposal = DisposalResourceIntTest.createEntity(em);
        em.persist(disposal);
        em.flush();
        treatmentProcedure.setDisposal(disposal);
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);
        Long disposalId = disposal.getId();

        // Get all the treatmentProcedureList where disposal equals to disposalId
        defaultTreatmentProcedureShouldBeFound("disposalId.equals=" + disposalId);

        // Get all the treatmentProcedureList where disposal equals to disposalId + 1
        defaultTreatmentProcedureShouldNotBeFound("disposalId.equals=" + (disposalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTreatmentProcedureShouldBeFound(String filter) throws Exception {
        restTreatmentProcedureMockMvc.perform(get("/api/treatment-procedures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatmentProcedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].completedDate").value(hasItem(DEFAULT_COMPLETED_DATE.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())));

        // Check, that the count call also returns 1
        restTreatmentProcedureMockMvc.perform(get("/api/treatment-procedures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTreatmentProcedureShouldNotBeFound(String filter) throws Exception {
        restTreatmentProcedureMockMvc.perform(get("/api/treatment-procedures?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTreatmentProcedureMockMvc.perform(get("/api/treatment-procedures/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTreatmentProcedure() throws Exception {
        // Get the treatmentProcedure
        restTreatmentProcedureMockMvc.perform(get("/api/treatment-procedures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreatmentProcedure() throws Exception {
        // Initialize the database
        treatmentProcedureService.save(treatmentProcedure);

        int databaseSizeBeforeUpdate = treatmentProcedureRepository.findAll().size();

        // Update the treatmentProcedure
        TreatmentProcedure updatedTreatmentProcedure = treatmentProcedureRepository.findById(treatmentProcedure.getId()).get();
        // Disconnect from session so that the updates on updatedTreatmentProcedure are not directly saved in db
        em.detach(updatedTreatmentProcedure);
        updatedTreatmentProcedure
            .status(UPDATED_STATUS)
            .quantity(UPDATED_QUANTITY)
            .total(UPDATED_TOTAL)
            .note(UPDATED_NOTE)
            .completedDate(UPDATED_COMPLETED_DATE)
            .price(UPDATED_PRICE);

        restTreatmentProcedureMockMvc.perform(put("/api/treatment-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTreatmentProcedure)))
            .andExpect(status().isOk());

        // Validate the TreatmentProcedure in the database
        List<TreatmentProcedure> treatmentProcedureList = treatmentProcedureRepository.findAll();
        assertThat(treatmentProcedureList).hasSize(databaseSizeBeforeUpdate);
        TreatmentProcedure testTreatmentProcedure = treatmentProcedureList.get(treatmentProcedureList.size() - 1);
        assertThat(testTreatmentProcedure.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testTreatmentProcedure.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testTreatmentProcedure.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testTreatmentProcedure.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testTreatmentProcedure.getCompletedDate()).isEqualTo(UPDATED_COMPLETED_DATE);
        assertThat(testTreatmentProcedure.getPrice()).isEqualTo(UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void updateNonExistingTreatmentProcedure() throws Exception {
        int databaseSizeBeforeUpdate = treatmentProcedureRepository.findAll().size();

        // Create the TreatmentProcedure

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTreatmentProcedureMockMvc.perform(put("/api/treatment-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentProcedure)))
            .andExpect(status().isBadRequest());

        // Validate the TreatmentProcedure in the database
        List<TreatmentProcedure> treatmentProcedureList = treatmentProcedureRepository.findAll();
        assertThat(treatmentProcedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTreatmentProcedure() throws Exception {
        // Initialize the database
        treatmentProcedureService.save(treatmentProcedure);

        int databaseSizeBeforeDelete = treatmentProcedureRepository.findAll().size();

        // Get the treatmentProcedure
        restTreatmentProcedureMockMvc.perform(delete("/api/treatment-procedures/{id}", treatmentProcedure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TreatmentProcedure> treatmentProcedureList = treatmentProcedureRepository.findAll();
        assertThat(treatmentProcedureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TreatmentProcedure.class);
        TreatmentProcedure treatmentProcedure1 = new TreatmentProcedure();
        treatmentProcedure1.setId(1L);
        TreatmentProcedure treatmentProcedure2 = new TreatmentProcedure();
        treatmentProcedure2.setId(treatmentProcedure1.getId());
        assertThat(treatmentProcedure1).isEqualTo(treatmentProcedure2);
        treatmentProcedure2.setId(2L);
        assertThat(treatmentProcedure1).isNotEqualTo(treatmentProcedure2);
        treatmentProcedure1.setId(null);
        assertThat(treatmentProcedure1).isNotEqualTo(treatmentProcedure2);
    }
}
