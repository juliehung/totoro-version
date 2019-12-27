package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.domain.*;
import io.dentall.totoro.message.AppointmentSender;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.AppointmentService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.AppointmentQueryService;

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
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.dentall.totoro.domain.enumeration.AppointmentStatus;
/**
 * Test class for the AppointmentResource REST controller.
 *
 * @see AppointmentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class AppointmentResourceIntTest {

    private static final AppointmentStatus DEFAULT_STATUS = AppointmentStatus.CONFIRMED;
    private static final AppointmentStatus UPDATED_STATUS = AppointmentStatus.CANCEL;

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Instant DEFAULT_EXPECTED_ARRIVAL_TIME = OffsetDateTime.now().withHour(22).withNano(0).toInstant();
    private static final Instant UPDATED_EXPECTED_ARRIVAL_TIME = OffsetDateTime.now().withHour(22).plusHours(1).withNano(0).toInstant();

    private static final Integer DEFAULT_REQUIRED_TREATMENT_TIME = 1;
    private static final Integer UPDATED_REQUIRED_TREATMENT_TIME = 2;

    private static final Boolean DEFAULT_MICROSCOPE = false;
    private static final Boolean UPDATED_MICROSCOPE = true;

    private static final Boolean DEFAULT_BASE_FLOOR = false;
    private static final Boolean UPDATED_BASE_FLOOR = true;

    private static final Integer DEFAULT_COLOR_ID = 1;
    private static final Integer UPDATED_COLOR_ID = 2;

    private static final Boolean DEFAULT_ARCHIVED = false;
    private static final Boolean UPDATED_ARCHIVED = true;

    private static final Boolean DEFAULT_CONTACTED = false;
    private static final Boolean UPDATED_CONTACTED = true;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentQueryService appointmentQueryService;

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

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentSender appointmentMessageSender;

    private MockMvc restAppointmentMockMvc;

    private Appointment appointment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppointmentResource appointmentResource = new AppointmentResource(appointmentService, appointmentQueryService, appointmentMessageSender);
        this.restAppointmentMockMvc = MockMvcBuilders.standaloneSetup(appointmentResource)
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
    public static Appointment createEntity(EntityManager em) {
        Appointment appointment = new Appointment()
            .status(DEFAULT_STATUS)
            .subject(DEFAULT_SUBJECT)
            .note(DEFAULT_NOTE)
            .expectedArrivalTime(DEFAULT_EXPECTED_ARRIVAL_TIME)
            .requiredTreatmentTime(DEFAULT_REQUIRED_TREATMENT_TIME)
            .microscope(DEFAULT_MICROSCOPE)
            .baseFloor(DEFAULT_BASE_FLOOR)
            .colorId(DEFAULT_COLOR_ID)
            .archived(DEFAULT_ARCHIVED)
            .contacted(DEFAULT_CONTACTED);
        return appointment;
    }

    @Before
    public void initTest() {
        appointment = createEntity(em);
        appointment.setDoctor(userRepository.save(UserResourceIntTest.createEntity(em)).getExtendUser());
    }

    @Test
    @Transactional
    public void createAppointment() throws Exception {
        int databaseSizeBeforeCreate = appointmentRepository.findAll().size();

        Registration registration = RegistrationResourceIntTest.createEntity(em).accounting(AccountingResourceIntTest.createEntity(em));
        registration.setId(null);
        appointment.setRegistration(registration);

        TreatmentProcedure treatmentProcedure = TreatmentProcedureResourceIntTest.createEntity(em);
        if (appointment.getTreatmentProcedures() == null) {
            appointment.setTreatmentProcedures(new HashSet<>());
        }

        appointment.getTreatmentProcedures().add(treatmentProcedure);

        appointment.setPatient(patientRepository.save(PatientResourceIntTest.createEntity(em)));

        // Create the Appointment
        restAppointmentMockMvc.perform(post("/api/appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointment)))
            .andExpect(status().isCreated());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeCreate + 1);
        Appointment testAppointment = appointmentList.get(appointmentList.size() - 1);
        assertThat(testAppointment.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testAppointment.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testAppointment.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testAppointment.getExpectedArrivalTime()).isEqualTo(DEFAULT_EXPECTED_ARRIVAL_TIME);
        assertThat(testAppointment.getRequiredTreatmentTime()).isEqualTo(DEFAULT_REQUIRED_TREATMENT_TIME);
        assertThat(testAppointment.isMicroscope()).isEqualTo(DEFAULT_MICROSCOPE);
        assertThat(testAppointment.isBaseFloor()).isEqualTo(DEFAULT_BASE_FLOOR);
        assertThat(testAppointment.getColorId()).isEqualTo(DEFAULT_COLOR_ID);
        assertThat(testAppointment.isArchived()).isEqualTo(DEFAULT_ARCHIVED);
        assertThat(testAppointment.isContacted()).isEqualTo(DEFAULT_CONTACTED);
        assertThat(testAppointment.getDoctor()).isEqualTo(appointment.getDoctor());
        assertThat(testAppointment.getRegistration().getType()).isEqualTo(appointment.getRegistration().getType());
        assertThat(testAppointment.getRegistration().getAccounting().getOwnExpense()).isEqualTo(appointment.getRegistration().getAccounting().getOwnExpense());
        assertThat(testAppointment.getTreatmentProcedures().iterator().next().getAppointment().getId()).isEqualTo(testAppointment.getId());
    }

    @Test
    @Transactional
    public void createAppointmentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = appointmentRepository.findAll().size();

        // Create the Appointment with an existing ID
        appointment.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAppointmentMockMvc.perform(post("/api/appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointment)))
            .andExpect(status().isBadRequest());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAppointments() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList
        restAppointmentMockMvc.perform(get("/api/appointments?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appointment.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].expectedArrivalTime").value(hasItem(DEFAULT_EXPECTED_ARRIVAL_TIME.toString())))
            .andExpect(jsonPath("$.[*].requiredTreatmentTime").value(hasItem(DEFAULT_REQUIRED_TREATMENT_TIME)))
            .andExpect(jsonPath("$.[*].microscope").value(hasItem(DEFAULT_MICROSCOPE.booleanValue())))
            .andExpect(jsonPath("$.[*].baseFloor").value(hasItem(DEFAULT_BASE_FLOOR.booleanValue())))
            .andExpect(jsonPath("$.[*].colorId").value(hasItem(DEFAULT_COLOR_ID)))
            .andExpect(jsonPath("$.[*].archived").value(hasItem(DEFAULT_ARCHIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].contacted").value(hasItem(DEFAULT_CONTACTED.booleanValue())));
    }

    @Test
    @Transactional
    public void getAppointment() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get the appointment
        restAppointmentMockMvc.perform(get("/api/appointments/{id}", appointment.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(appointment.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.expectedArrivalTime").value(DEFAULT_EXPECTED_ARRIVAL_TIME.toString()))
            .andExpect(jsonPath("$.requiredTreatmentTime").value(DEFAULT_REQUIRED_TREATMENT_TIME))
            .andExpect(jsonPath("$.microscope").value(DEFAULT_MICROSCOPE.booleanValue()))
            .andExpect(jsonPath("$.baseFloor").value(DEFAULT_BASE_FLOOR.booleanValue()))
            .andExpect(jsonPath("$.colorId").value(DEFAULT_COLOR_ID))
            .andExpect(jsonPath("$.archived").value(DEFAULT_ARCHIVED.booleanValue()))
            .andExpect(jsonPath("$.contacted").value(DEFAULT_CONTACTED.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllAppointmentsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where status equals to DEFAULT_STATUS
        defaultAppointmentShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the appointmentList where status equals to UPDATED_STATUS
        defaultAppointmentShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultAppointmentShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the appointmentList where status equals to UPDATED_STATUS
        defaultAppointmentShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where status is not null
        defaultAppointmentShouldBeFound("status.specified=true");

        // Get all the appointmentList where status is null
        defaultAppointmentShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppointmentsBySubjectIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where subject equals to DEFAULT_SUBJECT
        defaultAppointmentShouldBeFound("subject.equals=" + DEFAULT_SUBJECT);

        // Get all the appointmentList where subject equals to UPDATED_SUBJECT
        defaultAppointmentShouldNotBeFound("subject.equals=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllAppointmentsBySubjectIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where subject in DEFAULT_SUBJECT or UPDATED_SUBJECT
        defaultAppointmentShouldBeFound("subject.in=" + DEFAULT_SUBJECT + "," + UPDATED_SUBJECT);

        // Get all the appointmentList where subject equals to UPDATED_SUBJECT
        defaultAppointmentShouldNotBeFound("subject.in=" + UPDATED_SUBJECT);
    }

    @Test
    @Transactional
    public void getAllAppointmentsBySubjectIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where subject is not null
        defaultAppointmentShouldBeFound("subject.specified=true");

        // Get all the appointmentList where subject is null
        defaultAppointmentShouldNotBeFound("subject.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppointmentsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where note equals to DEFAULT_NOTE
        defaultAppointmentShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the appointmentList where note equals to UPDATED_NOTE
        defaultAppointmentShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultAppointmentShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the appointmentList where note equals to UPDATED_NOTE
        defaultAppointmentShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where note is not null
        defaultAppointmentShouldBeFound("note.specified=true");

        // Get all the appointmentList where note is null
        defaultAppointmentShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppointmentsByExpectedArrivalTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where expectedArrivalTime equals to DEFAULT_EXPECTED_ARRIVAL_TIME
        defaultAppointmentShouldBeFound("expectedArrivalTime.equals=" + DEFAULT_EXPECTED_ARRIVAL_TIME);

        // Get all the appointmentList where expectedArrivalTime equals to UPDATED_EXPECTED_ARRIVAL_TIME
        defaultAppointmentShouldNotBeFound("expectedArrivalTime.equals=" + UPDATED_EXPECTED_ARRIVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByExpectedArrivalTimeIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where expectedArrivalTime in DEFAULT_EXPECTED_ARRIVAL_TIME or UPDATED_EXPECTED_ARRIVAL_TIME
        defaultAppointmentShouldBeFound("expectedArrivalTime.in=" + DEFAULT_EXPECTED_ARRIVAL_TIME + "," + UPDATED_EXPECTED_ARRIVAL_TIME);

        // Get all the appointmentList where expectedArrivalTime equals to UPDATED_EXPECTED_ARRIVAL_TIME
        defaultAppointmentShouldNotBeFound("expectedArrivalTime.in=" + UPDATED_EXPECTED_ARRIVAL_TIME);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByExpectedArrivalTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where expectedArrivalTime is not null
        defaultAppointmentShouldBeFound("expectedArrivalTime.specified=true");

        // Get all the appointmentList where expectedArrivalTime is null
        defaultAppointmentShouldNotBeFound("expectedArrivalTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppointmentsByRequiredTreatmentTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where requiredTreatmentTime equals to DEFAULT_REQUIRED_TREATMENT_TIME
        defaultAppointmentShouldBeFound("requiredTreatmentTime.equals=" + DEFAULT_REQUIRED_TREATMENT_TIME);

        // Get all the appointmentList where requiredTreatmentTime equals to UPDATED_REQUIRED_TREATMENT_TIME
        defaultAppointmentShouldNotBeFound("requiredTreatmentTime.equals=" + UPDATED_REQUIRED_TREATMENT_TIME);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByRequiredTreatmentTimeIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where requiredTreatmentTime in DEFAULT_REQUIRED_TREATMENT_TIME or UPDATED_REQUIRED_TREATMENT_TIME
        defaultAppointmentShouldBeFound("requiredTreatmentTime.in=" + DEFAULT_REQUIRED_TREATMENT_TIME + "," + UPDATED_REQUIRED_TREATMENT_TIME);

        // Get all the appointmentList where requiredTreatmentTime equals to UPDATED_REQUIRED_TREATMENT_TIME
        defaultAppointmentShouldNotBeFound("requiredTreatmentTime.in=" + UPDATED_REQUIRED_TREATMENT_TIME);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByRequiredTreatmentTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where requiredTreatmentTime is not null
        defaultAppointmentShouldBeFound("requiredTreatmentTime.specified=true");

        // Get all the appointmentList where requiredTreatmentTime is null
        defaultAppointmentShouldNotBeFound("requiredTreatmentTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppointmentsByRequiredTreatmentTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where requiredTreatmentTime greater than or equals to DEFAULT_REQUIRED_TREATMENT_TIME
        defaultAppointmentShouldBeFound("requiredTreatmentTime.greaterOrEqualThan=" + DEFAULT_REQUIRED_TREATMENT_TIME);

        // Get all the appointmentList where requiredTreatmentTime greater than or equals to UPDATED_REQUIRED_TREATMENT_TIME
        defaultAppointmentShouldNotBeFound("requiredTreatmentTime.greaterOrEqualThan=" + UPDATED_REQUIRED_TREATMENT_TIME);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByRequiredTreatmentTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where requiredTreatmentTime less than or equals to DEFAULT_REQUIRED_TREATMENT_TIME
        defaultAppointmentShouldNotBeFound("requiredTreatmentTime.lessThan=" + DEFAULT_REQUIRED_TREATMENT_TIME);

        // Get all the appointmentList where requiredTreatmentTime less than or equals to UPDATED_REQUIRED_TREATMENT_TIME
        defaultAppointmentShouldBeFound("requiredTreatmentTime.lessThan=" + UPDATED_REQUIRED_TREATMENT_TIME);
    }


    @Test
    @Transactional
    public void getAllAppointmentsByMicroscopeIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where microscope equals to DEFAULT_MICROSCOPE
        defaultAppointmentShouldBeFound("microscope.equals=" + DEFAULT_MICROSCOPE);

        // Get all the appointmentList where microscope equals to UPDATED_MICROSCOPE
        defaultAppointmentShouldNotBeFound("microscope.equals=" + UPDATED_MICROSCOPE);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByMicroscopeIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where microscope in DEFAULT_MICROSCOPE or UPDATED_MICROSCOPE
        defaultAppointmentShouldBeFound("microscope.in=" + DEFAULT_MICROSCOPE + "," + UPDATED_MICROSCOPE);

        // Get all the appointmentList where microscope equals to UPDATED_MICROSCOPE
        defaultAppointmentShouldNotBeFound("microscope.in=" + UPDATED_MICROSCOPE);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByMicroscopeIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where microscope is not null
        defaultAppointmentShouldBeFound("microscope.specified=true");

        // Get all the appointmentList where microscope is null
        defaultAppointmentShouldNotBeFound("microscope.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppointmentsByBaseFloorIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where baseFloor equals to DEFAULT_BASE_FLOOR
        defaultAppointmentShouldBeFound("baseFloor.equals=" + DEFAULT_BASE_FLOOR);

        // Get all the appointmentList where baseFloor equals to UPDATED_BASE_FLOOR
        defaultAppointmentShouldNotBeFound("baseFloor.equals=" + UPDATED_BASE_FLOOR);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByBaseFloorIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where baseFloor in DEFAULT_BASE_FLOOR or UPDATED_BASE_FLOOR
        defaultAppointmentShouldBeFound("baseFloor.in=" + DEFAULT_BASE_FLOOR + "," + UPDATED_BASE_FLOOR);

        // Get all the appointmentList where baseFloor equals to UPDATED_BASE_FLOOR
        defaultAppointmentShouldNotBeFound("baseFloor.in=" + UPDATED_BASE_FLOOR);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByBaseFloorIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where baseFloor is not null
        defaultAppointmentShouldBeFound("baseFloor.specified=true");

        // Get all the appointmentList where baseFloor is null
        defaultAppointmentShouldNotBeFound("baseFloor.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppointmentsByColorIdIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where colorId equals to DEFAULT_COLOR_ID
        defaultAppointmentShouldBeFound("colorId.equals=" + DEFAULT_COLOR_ID);

        // Get all the appointmentList where colorId equals to UPDATED_COLOR_ID
        defaultAppointmentShouldNotBeFound("colorId.equals=" + UPDATED_COLOR_ID);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByColorIdIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where colorId in DEFAULT_COLOR_ID or UPDATED_COLOR_ID
        defaultAppointmentShouldBeFound("colorId.in=" + DEFAULT_COLOR_ID + "," + UPDATED_COLOR_ID);

        // Get all the appointmentList where colorId equals to UPDATED_COLOR_ID
        defaultAppointmentShouldNotBeFound("colorId.in=" + UPDATED_COLOR_ID);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByColorIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where colorId is not null
        defaultAppointmentShouldBeFound("colorId.specified=true");

        // Get all the appointmentList where colorId is null
        defaultAppointmentShouldNotBeFound("colorId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppointmentsByColorIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where colorId greater than or equals to DEFAULT_COLOR_ID
        defaultAppointmentShouldBeFound("colorId.greaterOrEqualThan=" + DEFAULT_COLOR_ID);

        // Get all the appointmentList where colorId greater than or equals to UPDATED_COLOR_ID
        defaultAppointmentShouldNotBeFound("colorId.greaterOrEqualThan=" + UPDATED_COLOR_ID);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByColorIdIsLessThanSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where colorId less than or equals to DEFAULT_COLOR_ID
        defaultAppointmentShouldNotBeFound("colorId.lessThan=" + DEFAULT_COLOR_ID);

        // Get all the appointmentList where colorId less than or equals to UPDATED_COLOR_ID
        defaultAppointmentShouldBeFound("colorId.lessThan=" + UPDATED_COLOR_ID);
    }


    @Test
    @Transactional
    public void getAllAppointmentsByArchivedIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where archived equals to DEFAULT_ARCHIVED
        defaultAppointmentShouldBeFound("archived.equals=" + DEFAULT_ARCHIVED);

        // Get all the appointmentList where archived equals to UPDATED_ARCHIVED
        defaultAppointmentShouldNotBeFound("archived.equals=" + UPDATED_ARCHIVED);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByArchivedIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where archived in DEFAULT_ARCHIVED or UPDATED_ARCHIVED
        defaultAppointmentShouldBeFound("archived.in=" + DEFAULT_ARCHIVED + "," + UPDATED_ARCHIVED);

        // Get all the appointmentList where archived equals to UPDATED_ARCHIVED
        defaultAppointmentShouldNotBeFound("archived.in=" + UPDATED_ARCHIVED);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByArchivedIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where archived is not null
        defaultAppointmentShouldBeFound("archived.specified=true");

        // Get all the appointmentList where archived is null
        defaultAppointmentShouldNotBeFound("archived.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppointmentsByContactedIsEqualToSomething() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where contacted equals to DEFAULT_CONTACTED
        defaultAppointmentShouldBeFound("contacted.equals=" + DEFAULT_CONTACTED);

        // Get all the appointmentList where contacted equals to UPDATED_CONTACTED
        defaultAppointmentShouldNotBeFound("contacted.equals=" + UPDATED_CONTACTED);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByContactedIsInShouldWork() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where contacted in DEFAULT_CONTACTED or UPDATED_CONTACTED
        defaultAppointmentShouldBeFound("contacted.in=" + DEFAULT_CONTACTED + "," + UPDATED_CONTACTED);

        // Get all the appointmentList where contacted equals to UPDATED_CONTACTED
        defaultAppointmentShouldNotBeFound("contacted.in=" + UPDATED_CONTACTED);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByContactedIsNullOrNotNull() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where contacted is not null
        defaultAppointmentShouldBeFound("contacted.specified=true");

        // Get all the appointmentList where contacted is null
        defaultAppointmentShouldNotBeFound("contacted.specified=false");
    }

    @Test
    @Transactional
    public void getAllAppointmentsByPatientIsEqualToSomething() throws Exception {
        // Initialize the database
        Patient patient = PatientResourceIntTest.createEntity(em);
        em.persist(patient);
        em.flush();
        appointment.setPatient(patient);
        appointmentRepository.saveAndFlush(appointment);
        Long patientId = patient.getId();

        // Get all the appointmentList where patient equals to patientId
        defaultAppointmentShouldBeFound("patientId.equals=" + patientId);

        // Get all the appointmentList where patient equals to patientId + 1
        defaultAppointmentShouldNotBeFound("patientId.equals=" + (patientId + 1));
    }


    @Test
    @Transactional
    public void getAllAppointmentsByRegistrationIsEqualToSomething() throws Exception {
        // Initialize the database
        Registration registration = RegistrationResourceIntTest.createEntity(em);
        em.persist(registration);
        em.flush();
        appointment.setRegistration(registration);
        appointmentRepository.saveAndFlush(appointment);
        Long registrationId = registration.getId();

        // Get all the appointmentList where registration equals to registrationId
        defaultAppointmentShouldBeFound("registrationId.equals=" + registrationId);

        // Get all the appointmentList where registration equals to registrationId + 1
        defaultAppointmentShouldNotBeFound("registrationId.equals=" + (registrationId + 1));
    }

    @Test
    @Transactional
    public void getAllAppointmentsByTreatmentProcedureIsEqualToSomething() throws Exception {
        // Initialize the database
        TreatmentProcedure treatmentProcedure = TreatmentProcedureResourceIntTest.createEntity(em);
        em.persist(treatmentProcedure);
        em.flush();

        if (appointment.getTreatmentProcedures() == null) {
            appointment.setTreatmentProcedures(new HashSet<>());
        }

        appointment.addTreatmentProcedure(treatmentProcedure);
        appointmentRepository.saveAndFlush(appointment);
        Long treatmentProcedureId = treatmentProcedure.getId();

        // Get all the appointmentList where treatmentProcedure equals to treatmentProcedureId
        defaultAppointmentShouldBeFound("treatmentProcedureId.equals=" + treatmentProcedureId);

        // Get all the appointmentList where treatmentProcedure equals to treatmentProcedureId + 1
        defaultAppointmentShouldNotBeFound("treatmentProcedureId.equals=" + (treatmentProcedureId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAppointmentShouldBeFound(String filter) throws Exception {
        restAppointmentMockMvc.perform(get("/api/appointments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(appointment.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].expectedArrivalTime").value(hasItem(DEFAULT_EXPECTED_ARRIVAL_TIME.toString())))
            .andExpect(jsonPath("$.[*].requiredTreatmentTime").value(hasItem(DEFAULT_REQUIRED_TREATMENT_TIME)))
            .andExpect(jsonPath("$.[*].microscope").value(hasItem(DEFAULT_MICROSCOPE.booleanValue())))
            .andExpect(jsonPath("$.[*].baseFloor").value(hasItem(DEFAULT_BASE_FLOOR.booleanValue())))
            .andExpect(jsonPath("$.[*].colorId").value(hasItem(DEFAULT_COLOR_ID)))
            .andExpect(jsonPath("$.[*].archived").value(hasItem(DEFAULT_ARCHIVED.booleanValue())))
            .andExpect(jsonPath("$.[*].contacted").value(hasItem(DEFAULT_CONTACTED.booleanValue())));

        // Check, that the count call also returns 1
        restAppointmentMockMvc.perform(get("/api/appointments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAppointmentShouldNotBeFound(String filter) throws Exception {
        restAppointmentMockMvc.perform(get("/api/appointments?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAppointmentMockMvc.perform(get("/api/appointments/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAppointment() throws Exception {
        // Get the appointment
        restAppointmentMockMvc.perform(get("/api/appointments/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAppointment() throws Exception {
        appointment.setPatient(patientRepository.save(PatientResourceIntTest.createEntity(em)));

        // Initialize the database
        appointmentService.save(appointment);

        int databaseSizeBeforeUpdate = appointmentRepository.findAll().size();

        // Update the appointment
        Appointment updatedAppointment = appointmentRepository.findById(appointment.getId()).get();
        // Disconnect from session so that the updates on updatedAppointment are not directly saved in db
        em.detach(updatedAppointment);

        User updateUser = userRepository.save(UserResourceIntTest.createEntity(em));
        Registration registration = RegistrationResourceIntTest.createEntity(em);
        registration.setId(null);
        Accounting accounting = AccountingResourceIntTest.createEntity(em);
        registration.setAccounting(accounting);
        updatedAppointment
            .status(UPDATED_STATUS)
            .subject(UPDATED_SUBJECT)
            .note(UPDATED_NOTE)
            .expectedArrivalTime(UPDATED_EXPECTED_ARRIVAL_TIME)
            .requiredTreatmentTime(UPDATED_REQUIRED_TREATMENT_TIME)
            .microscope(UPDATED_MICROSCOPE)
            .baseFloor(null) // test unchanged with null
            .colorId(UPDATED_COLOR_ID)
            .archived(UPDATED_ARCHIVED)
            .contacted(UPDATED_CONTACTED)
            .registration(registration)
            .doctor(updateUser.getExtendUser());

        restAppointmentMockMvc.perform(put("/api/appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAppointment)))
            .andExpect(status().isOk());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeUpdate);
        Appointment testAppointment = appointmentList.get(appointmentList.size() - 1);
        assertThat(testAppointment.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testAppointment.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testAppointment.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testAppointment.getExpectedArrivalTime()).isEqualTo(UPDATED_EXPECTED_ARRIVAL_TIME);
        assertThat(testAppointment.getRequiredTreatmentTime()).isEqualTo(UPDATED_REQUIRED_TREATMENT_TIME);
        assertThat(testAppointment.isMicroscope()).isEqualTo(UPDATED_MICROSCOPE);
        assertThat(testAppointment.isBaseFloor()).isEqualTo(DEFAULT_BASE_FLOOR);
        assertThat(testAppointment.getColorId()).isEqualTo(UPDATED_COLOR_ID);
        assertThat(testAppointment.isArchived()).isEqualTo(UPDATED_ARCHIVED);
        assertThat(testAppointment.isContacted()).isEqualTo(UPDATED_CONTACTED);
        assertThat(testAppointment.getRegistration().getStatus()).isEqualTo(registration.getStatus());
        assertThat(testAppointment.getDoctor().getId()).isEqualTo(updateUser.getId());
        assertThat(testAppointment.getRegistration().getAccounting().getOwnExpense()).isEqualTo(accounting.getOwnExpense());
    }

    @Test
    @Transactional
    public void updateNonExistingAppointment() throws Exception {
        int databaseSizeBeforeUpdate = appointmentRepository.findAll().size();

        // Create the Appointment

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAppointmentMockMvc.perform(put("/api/appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointment)))
            .andExpect(status().isBadRequest());

        // Validate the Appointment in the database
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAppointment() throws Exception {
        Patient patient = TestUtil.createPatient(em, userRepository, tagRepository, patientRepository);

        // Initialize the database
        appointmentService.save(appointment.patient(patient));

        int databaseSizeBeforeDelete = appointmentRepository.findAll().size();

        // Get the appointment
        restAppointmentMockMvc.perform(delete("/api/appointments/{id}", appointment.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appointment.class);
        Appointment appointment1 = new Appointment();
        appointment1.setId(1L);
        Appointment appointment2 = new Appointment();
        appointment2.setId(appointment1.getId());
        assertThat(appointment1).isEqualTo(appointment2);
        appointment2.setId(2L);
        assertThat(appointment1).isNotEqualTo(appointment2);
        appointment1.setId(null);
        assertThat(appointment1).isNotEqualTo(appointment2);
    }

    @Test
    @Transactional
    public void getAllAppointmentsByRegistrationIsNullAndPatientId() throws Exception {
        Patient patient = TestUtil.createPatient(em, userRepository, tagRepository, patientRepository);
        patient.addAppointment(appointment);

        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where registration is null and patient equals to
        defaultAppointmentShouldBeFound("registrationId.specified=false&patientId.equals=" + patient.getId());
    }

    @Test
    @Transactional
    public void getAllAppointmentsByRegistrationType() throws Exception {
        Registration registration = RegistrationResourceIntTest.createEntity(em);
        em.persist(registration);
        em.flush();
        appointment.setRegistration(registration);

        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where registration type equals to
        defaultAppointmentShouldBeFound("registrationType.equals=" + registration.getType());
    }

    @Test
    @Transactional
    public void getAllAppointmentsByDoctorId() throws Exception {
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        // Get all the appointmentList where doctor equals to
        defaultAppointmentShouldBeFound("doctorId.equals=" + appointment.getDoctor().getId());
    }
}
