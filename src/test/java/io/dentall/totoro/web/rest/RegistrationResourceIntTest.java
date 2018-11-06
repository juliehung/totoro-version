package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.AppointmentRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.RegistrationRepository;
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

import io.dentall.totoro.domain.enumeration.RegistrationStatus;
import io.dentall.totoro.domain.enumeration.RegistrationType;
/**
 * Test class for the RegistrationResource REST controller.
 *
 * @see RegistrationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class RegistrationResourceIntTest {

    private static final RegistrationStatus DEFAULT_STATUS = RegistrationStatus.PENDING;
    private static final RegistrationStatus UPDATED_STATUS = RegistrationStatus.FINISH;

    private static final ZonedDateTime DEFAULT_ARRIVAL_TIME = ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(10).withNano(0);
    private static final ZonedDateTime UPDATED_ARRIVAL_TIME = ZonedDateTime.now(ZoneId.systemDefault()).plusHours(1).plusMinutes(10).withNano(0);

    private static final RegistrationType DEFAULT_TYPE = RegistrationType.OWN_EXPENSE;
    private static final RegistrationType UPDATED_TYPE = RegistrationType.NHI;

    private static final Boolean DEFAULT_ON_SITE = false;
    private static final Boolean UPDATED_ON_SITE = true;

    @Autowired
    private RegistrationRepository registrationRepository;

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

    @Autowired
    private AppointmentRepository appointmentRepository;

    private MockMvc restRegistrationMockMvc;

    private Registration registration;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegistrationResource registrationResource = new RegistrationResource(registrationRepository);
        this.restRegistrationMockMvc = MockMvcBuilders.standaloneSetup(registrationResource)
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
    public static Registration createEntity(EntityManager em) {
        Registration registration = new Registration()
            .status(DEFAULT_STATUS)
            .arrivalTime(DEFAULT_ARRIVAL_TIME)
            .type(DEFAULT_TYPE)
            .onSite(DEFAULT_ON_SITE);
        return registration;
    }

    @Before
    public void initTest() {
        registration = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegistration() throws Exception {
        int databaseSizeBeforeCreate = registrationRepository.findAll().size();

        // Create the Registration
        restRegistrationMockMvc.perform(post("/api/registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registration)))
            .andExpect(status().isCreated());

        // Validate the Registration in the database
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeCreate + 1);
        Registration testRegistration = registrationList.get(registrationList.size() - 1);
        assertThat(testRegistration.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRegistration.getArrivalTime()).isEqualTo(DEFAULT_ARRIVAL_TIME);
        assertThat(testRegistration.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRegistration.isOnSite()).isEqualTo(DEFAULT_ON_SITE);
    }

    @Test
    @Transactional
    public void createRegistrationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = registrationRepository.findAll().size();

        // Create the Registration with an existing ID
        registration.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegistrationMockMvc.perform(post("/api/registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registration)))
            .andExpect(status().isBadRequest());

        // Validate the Registration in the database
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = registrationRepository.findAll().size();
        // set the field null
        registration.setStatus(null);

        // Create the Registration, which fails.

        restRegistrationMockMvc.perform(post("/api/registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registration)))
            .andExpect(status().isBadRequest());

        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRegistrations() throws Exception {
        // Initialize the database
        registrationRepository.saveAndFlush(registration);

        // Get all the registrationList
        restRegistrationMockMvc.perform(get("/api/registrations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(registration.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].arrivalTime").value(hasItem(sameInstant(DEFAULT_ARRIVAL_TIME))))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].onSite").value(hasItem(DEFAULT_ON_SITE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getRegistration() throws Exception {
        // Initialize the database
        registrationRepository.saveAndFlush(registration);

        // Get the registration
        restRegistrationMockMvc.perform(get("/api/registrations/{id}", registration.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(registration.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.arrivalTime").value(sameInstant(DEFAULT_ARRIVAL_TIME)))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.onSite").value(DEFAULT_ON_SITE));
    }

    @Test
    @Transactional
    public void getNonExistingRegistration() throws Exception {
        // Get the registration
        restRegistrationMockMvc.perform(get("/api/registrations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegistration() throws Exception {
        // Initialize the database
        registrationRepository.saveAndFlush(registration);

        int databaseSizeBeforeUpdate = registrationRepository.findAll().size();

        // Update the registration
        Registration updatedRegistration = registrationRepository.findById(registration.getId()).get();
        // Disconnect from session so that the updates on updatedRegistration are not directly saved in db
        em.detach(updatedRegistration);
        updatedRegistration
            .status(UPDATED_STATUS)
            .arrivalTime(UPDATED_ARRIVAL_TIME)
            .type(UPDATED_TYPE)
            .onSite(UPDATED_ON_SITE);

        restRegistrationMockMvc.perform(put("/api/registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegistration)))
            .andExpect(status().isOk());

        // Validate the Registration in the database
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeUpdate);
        Registration testRegistration = registrationList.get(registrationList.size() - 1);
        assertThat(testRegistration.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRegistration.getArrivalTime()).isEqualTo(UPDATED_ARRIVAL_TIME);
        assertThat(testRegistration.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRegistration.isOnSite()).isEqualTo(UPDATED_ON_SITE);
    }

    @Test
    @Transactional
    public void updateNonExistingRegistration() throws Exception {
        int databaseSizeBeforeUpdate = registrationRepository.findAll().size();

        // Create the Registration

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegistrationMockMvc.perform(put("/api/registrations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(registration)))
            .andExpect(status().isBadRequest());

        // Validate the Registration in the database
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegistration() throws Exception {
        // Initialize the database
        registrationRepository.saveAndFlush(registration);

        int databaseSizeBeforeDelete = registrationRepository.findAll().size();

        // Get the registration
        restRegistrationMockMvc.perform(delete("/api/registrations/{id}", registration.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Registration> registrationList = registrationRepository.findAll();
        assertThat(registrationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Registration.class);
        Registration registration1 = new Registration();
        registration1.setId(1L);
        Registration registration2 = new Registration();
        registration2.setId(registration1.getId());
        assertThat(registration1).isEqualTo(registration2);
        registration2.setId(2L);
        assertThat(registration1).isNotEqualTo(registration2);
        registration1.setId(null);
        assertThat(registration1).isNotEqualTo(registration2);
    }

    @Test
    @Transactional
    public void getAllPatientCards() throws Exception {
        // Initialize the database
        registrationRepository.saveAndFlush(registration);
        Patient patient = TestUtil.createPatient(
            UserResourceIntTest.createEntity(em), userRepository, PatientResourceIntTest.createEntity(em), patientRepository);
        Appointment appointment = createAppointment(patient);

        restRegistrationMockMvc.perform(get("/api/registrations/patient-cards?sort=arrivalTime,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[0].Name").value(patient.getName()))
            .andExpect(jsonPath("$.[0].Gender").value(patient.getGender().getValue()))
            .andExpect(jsonPath("$.[0].Pid").value(patient.getMedicalId()))
            .andExpect(jsonPath("$.[0].Birthday").value(patient.getBirth().toString()))
            .andExpect(jsonPath("$.[0].RegistrationTime").value(sameInstant(appointment.getExpectedArrivalTime())))
            .andExpect(jsonPath("$.[0].ArrivalTime").value(sameInstant(DEFAULT_ARRIVAL_TIME)))
            .andExpect(jsonPath("$.[0].ConsultType").value(DEFAULT_TYPE.getValue()))
            .andExpect(jsonPath("$.[0].Subject").value(appointment.getSubject()))
            .andExpect(jsonPath("$.[0].DominantDoc").value(patient.getDominantDoctor().getUser().getLogin()))
            .andExpect(jsonPath("$.[0].NeedTime").value(appointment.getRequiredTreatmentTime()))
            .andExpect(jsonPath("$.[0].IsNewPatient").value(appointment.isNewPatient()))
            .andExpect(jsonPath("$.[0].ConsultationStatus").value(DEFAULT_STATUS.getValue()))
            .andExpect(jsonPath("$.[0].FirstDoc").value(patient.getFirstDoctor().getUser().getLogin()))
            .andExpect(jsonPath("$.[0].Reminder").value(patient.getReminder()))
            .andExpect(jsonPath("$.[0].IcWrittenTime").value(sameInstant(patient.getWriteIcTime())));
    }

    private Appointment createAppointment(Patient patient) {
        Appointment appointment = AppointmentResourceIntTest.createEntity(em);
        appointment.setPatient(patient);
        appointment.setRegistration(registration);
        registration.setAppointment(appointment);
        appointmentRepository.saveAndFlush(appointment);

        return appointment;
    }
}
