package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.AppointmentRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.UserRepository;
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

import javax.persistence.EntityManager;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.sameInstant;
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

    private static final AppointmentStatus DEFAULT_STATUS = AppointmentStatus.TO_BE_CONFIRMED;
    private static final AppointmentStatus UPDATED_STATUS = AppointmentStatus.CONFIRMED;

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EXPECTED_ARRIVAL_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime UPDATED_EXPECTED_ARRIVAL_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_REQUIRED_TREATMENT_TIME = 1;
    private static final Integer UPDATED_REQUIRED_TREATMENT_TIME = 2;

    private static final Boolean DEFAULT_PREGNANCY = false;
    private static final Boolean UPDATED_PREGNANCY = true;

    private static final Boolean DEFAULT_MICROSCOPE = false;
    private static final Boolean UPDATED_MICROSCOPE = true;

    private static final Boolean DEFAULT_NEW_PATIENT = false;
    private static final Boolean UPDATED_NEW_PATIENT = true;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    private MockMvc restAppointmentMockMvc;

    private Appointment appointment;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppointmentResource appointmentResource = new AppointmentResource(appointmentRepository);
        this.restAppointmentMockMvc = MockMvcBuilders.standaloneSetup(appointmentResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
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
            .expectedArrivalTime(DEFAULT_EXPECTED_ARRIVAL_TIME)
            .requiredTreatmentTime(DEFAULT_REQUIRED_TREATMENT_TIME)
            .pregnancy(DEFAULT_PREGNANCY)
            .microscope(DEFAULT_MICROSCOPE)
            .newPatient(DEFAULT_NEW_PATIENT);
        return appointment;
    }

    @Before
    public void initTest() {
        appointment = createEntity(em);
    }

    @Test
    @Transactional
    public void createAppointment() throws Exception {
        int databaseSizeBeforeCreate = appointmentRepository.findAll().size();

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
        assertThat(testAppointment.getExpectedArrivalTime()).isEqualTo(DEFAULT_EXPECTED_ARRIVAL_TIME);
        assertThat(testAppointment.getRequiredTreatmentTime()).isEqualTo(DEFAULT_REQUIRED_TREATMENT_TIME);
        assertThat(testAppointment.isPregnancy()).isEqualTo(DEFAULT_PREGNANCY);
        assertThat(testAppointment.isMicroscope()).isEqualTo(DEFAULT_MICROSCOPE);
        assertThat(testAppointment.isNewPatient()).isEqualTo(DEFAULT_NEW_PATIENT);
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
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = appointmentRepository.findAll().size();
        // set the field null
        appointment.setStatus(null);

        // Create the Appointment, which fails.

        restAppointmentMockMvc.perform(post("/api/appointments")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(appointment)))
            .andExpect(status().isBadRequest());

        List<Appointment> appointmentList = appointmentRepository.findAll();
        assertThat(appointmentList).hasSize(databaseSizeBeforeTest);
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
            .andExpect(jsonPath("$.[*].expectedArrivalTime").value(hasItem(sameInstant(DEFAULT_EXPECTED_ARRIVAL_TIME))))
            .andExpect(jsonPath("$.[*].requiredTreatmentTime").value(hasItem(DEFAULT_REQUIRED_TREATMENT_TIME)))
            .andExpect(jsonPath("$.[*].pregnancy").value(hasItem(DEFAULT_PREGNANCY.booleanValue())))
            .andExpect(jsonPath("$.[*].microscope").value(hasItem(DEFAULT_MICROSCOPE.booleanValue())))
            .andExpect(jsonPath("$.[*].newPatient").value(hasItem(DEFAULT_NEW_PATIENT.booleanValue())));
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
            .andExpect(jsonPath("$.expectedArrivalTime").value(sameInstant(DEFAULT_EXPECTED_ARRIVAL_TIME)))
            .andExpect(jsonPath("$.requiredTreatmentTime").value(DEFAULT_REQUIRED_TREATMENT_TIME))
            .andExpect(jsonPath("$.pregnancy").value(DEFAULT_PREGNANCY.booleanValue()))
            .andExpect(jsonPath("$.microscope").value(DEFAULT_MICROSCOPE.booleanValue()))
            .andExpect(jsonPath("$.newPatient").value(DEFAULT_NEW_PATIENT.booleanValue()));
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
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

        int databaseSizeBeforeUpdate = appointmentRepository.findAll().size();

        // Update the appointment
        Appointment updatedAppointment = appointmentRepository.findById(appointment.getId()).get();
        // Disconnect from session so that the updates on updatedAppointment are not directly saved in db
        em.detach(updatedAppointment);
        updatedAppointment
            .status(UPDATED_STATUS)
            .subject(UPDATED_SUBJECT)
            .expectedArrivalTime(UPDATED_EXPECTED_ARRIVAL_TIME)
            .requiredTreatmentTime(UPDATED_REQUIRED_TREATMENT_TIME)
            .pregnancy(UPDATED_PREGNANCY)
            .microscope(UPDATED_MICROSCOPE)
            .newPatient(UPDATED_NEW_PATIENT);

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
        assertThat(testAppointment.getExpectedArrivalTime()).isEqualTo(UPDATED_EXPECTED_ARRIVAL_TIME);
        assertThat(testAppointment.getRequiredTreatmentTime()).isEqualTo(UPDATED_REQUIRED_TREATMENT_TIME);
        assertThat(testAppointment.isPregnancy()).isEqualTo(UPDATED_PREGNANCY);
        assertThat(testAppointment.isMicroscope()).isEqualTo(UPDATED_MICROSCOPE);
        assertThat(testAppointment.isNewPatient()).isEqualTo(UPDATED_NEW_PATIENT);
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
        // Initialize the database
        appointmentRepository.saveAndFlush(appointment);

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
    public void getAllPatientCards() throws Exception {
        // Initialize the database
        Patient patient = createPatient();
        appointment.setPatient(patient);
        patient.addAppointment(appointment);
        appointmentRepository.saveAndFlush(appointment);

        restAppointmentMockMvc.perform(get("/api/appointments/patient-cards?sort=expectedArrivalTime,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[0].Name").value(patient.getName()))
            .andExpect(jsonPath("$.[0].Gender").value(patient.getGender().getValue()))
            .andExpect(jsonPath("$.[0].Pid").value(patient.getMedicalId()))
            .andExpect(jsonPath("$.[0].Birthday").value(patient.getBirth().toString()))
            .andExpect(jsonPath("$.[0].RegistrationTime").value(sameInstant(DEFAULT_EXPECTED_ARRIVAL_TIME)))
            .andExpect(jsonPath("$.[0].ArrivalTime").doesNotExist())
            .andExpect(jsonPath("$.[0].ConsultType").doesNotExist())
            .andExpect(jsonPath("$.[0].Subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.[0].DominantDoc").value(patient.getDominantDoctor().getUser().getLogin()))
            .andExpect(jsonPath("$.[0].NeedTime").value(DEFAULT_REQUIRED_TREATMENT_TIME))
            .andExpect(jsonPath("$.[0].IsNewPatient").value(DEFAULT_NEW_PATIENT.toString()))
            .andExpect(jsonPath("$.[0].ConsultationStatus").doesNotExist())
            .andExpect(jsonPath("$.[0].FirstDoc").value(patient.getFirstDoctor().getUser().getLogin()))
            .andExpect(jsonPath("$.[0].Reminder").value(patient.getReminder()))
            .andExpect(jsonPath("$.[0].EmrLastModifyTime").value(sameInstant(patient.getLastModifiedTime())))
            .andExpect(jsonPath("$.[0].IcWrittenTime").value(sameInstant(patient.getWriteIcTime())));
    }

    private Patient createPatient() {
        User user = UserResourceIntTest.createEntity(em);
        userRepository.saveAndFlush(user);

        ExtendUser extendUser = user.getExtendUser();
        Patient patient = PatientResourceIntTest.createEntity(em);
        patient.setDominantDoctor(extendUser);
        patient.setFirstDoctor(extendUser);
        patient.setUpdateUser(extendUser);
        patientRepository.saveAndFlush(patient);

        return patient;
    }
}
