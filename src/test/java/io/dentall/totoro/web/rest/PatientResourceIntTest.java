package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.*;
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
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.sameInstant;
import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.dentall.totoro.domain.enumeration.Gender;
import io.dentall.totoro.domain.enumeration.Blood;
/**
 * Test class for the PatientResource REST controller.
 *
 * @see PatientResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class PatientResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_NATIONAL_ID = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.OTHER;
    private static final Gender UPDATED_GENDER = Gender.MALE;

    private static final LocalDate DEFAULT_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO = "BBBBBBBBBB";

    private static final Blood DEFAULT_BLOOD = Blood.UNKNOWN;
    private static final Blood UPDATED_BLOOD = Blood.A;

    private static final String DEFAULT_CARD_ID = "AAAAAAAAAA";
    private static final String UPDATED_CARD_ID = "BBBBBBBBBB";

    private static final String DEFAULT_VIP = "AAAAAAAAAA";
    private static final String UPDATED_VIP = "BBBBBBBBBB";

    private static final String DEFAULT_EMERGENCY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMERGENCY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMERGENCY_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_EMERGENCY_PHONE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_DELETE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_DELETE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final LocalDate DEFAULT_SCALING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCALING = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_ALLERGY = false;
    private static final Boolean UPDATED_ALLERGY = true;

    private static final Boolean DEFAULT_INCONVENIENCE = false;
    private static final Boolean UPDATED_INCONVENIENCE = true;

    private static final Boolean DEFAULT_SERIOUS_DISEASE = false;
    private static final Boolean UPDATED_SERIOUS_DISEASE = true;

    private static final String DEFAULT_LINE_ID = "AAAAAAAAAA";
    private static final String UPDATED_LINE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FB_ID = "AAAAAAAAAA";
    private static final String UPDATED_FB_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REMINDER = "AAAAAAAAAA";
    private static final String UPDATED_REMINDER = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_WRITE_IC_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_WRITE_IC_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String CREATED_BY = "system";

    @Autowired
    private PatientRepository patientRepository;

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

    private MockMvc restPatientMockMvc;

    private Patient patient;

    private ExtendUser extendUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientResource patientResource = new PatientResource(patientRepository);
        this.restPatientMockMvc = MockMvcBuilders.standaloneSetup(patientResource)
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
    public static Patient createEntity(EntityManager em) {
        Patient patient = new Patient()
            .name(DEFAULT_NAME)
            .nationalId(DEFAULT_NATIONAL_ID)
            .gender(DEFAULT_GENDER)
            .birth(DEFAULT_BIRTH)
            .phone(DEFAULT_PHONE)
            .medicalId(DEFAULT_MEDICAL_ID)
            .zip(DEFAULT_ZIP)
            .address(DEFAULT_ADDRESS)
            .email(DEFAULT_EMAIL)
            .photo(DEFAULT_PHOTO)
            .blood(DEFAULT_BLOOD)
            .cardId(DEFAULT_CARD_ID)
            .vip(DEFAULT_VIP)
            .introducer(null)
            .emergencyName(DEFAULT_EMERGENCY_NAME)
            .emergencyPhone(DEFAULT_EMERGENCY_PHONE)
            .deleteDate(DEFAULT_DELETE_DATE)
            .scaling(DEFAULT_SCALING)
            .allergy(DEFAULT_ALLERGY)
            .inconvenience(DEFAULT_INCONVENIENCE)
            .seriousDisease(DEFAULT_SERIOUS_DISEASE)
            .lineId(DEFAULT_LINE_ID)
            .fbId(DEFAULT_FB_ID)
            .reminder(DEFAULT_REMINDER)
            .writeIcTime(DEFAULT_WRITE_IC_TIME)
            .createdBy(CREATED_BY);
        return patient;
    }

    @Before
    public void initTest() {
        patient = createEntity(em);

        User user = UserResourceIntTest.createEntity(em);
        userRepository.saveAndFlush(user);

        extendUser = user.getExtendUser();
        patient.setDominantDoctor(extendUser);
        patient.setFirstDoctor(extendUser);
    }

    @Test
    @Transactional
    public void createPatient() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient
        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isCreated());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate + 1);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPatient.getNationalId()).isEqualTo(DEFAULT_NATIONAL_ID);
        assertThat(testPatient.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPatient.getBirth()).isEqualTo(DEFAULT_BIRTH);
        assertThat(testPatient.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPatient.getMedicalId()).isEqualTo(DEFAULT_MEDICAL_ID);
        assertThat(testPatient.getZip()).isEqualTo(DEFAULT_ZIP);
        assertThat(testPatient.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPatient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPatient.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPatient.getBlood()).isEqualTo(DEFAULT_BLOOD);
        assertThat(testPatient.getCardId()).isEqualTo(DEFAULT_CARD_ID);
        assertThat(testPatient.getVip()).isEqualTo(DEFAULT_VIP);
        assertThat(testPatient.getDominantDoctor()).isEqualTo(extendUser);
        assertThat(testPatient.getFirstDoctor()).isEqualTo(extendUser);
        assertThat(testPatient.getIntroducer()).isEqualTo(null);
        assertThat(testPatient.getEmergencyName()).isEqualTo(DEFAULT_EMERGENCY_NAME);
        assertThat(testPatient.getEmergencyPhone()).isEqualTo(DEFAULT_EMERGENCY_PHONE);
        assertThat(testPatient.getDeleteDate()).isEqualTo(DEFAULT_DELETE_DATE);
        assertThat(testPatient.getScaling()).isEqualTo(DEFAULT_SCALING);
        assertThat(testPatient.isAllergy()).isEqualTo(DEFAULT_ALLERGY);
        assertThat(testPatient.isInconvenience()).isEqualTo(DEFAULT_INCONVENIENCE);
        assertThat(testPatient.isSeriousDisease()).isEqualTo(DEFAULT_SERIOUS_DISEASE);
        assertThat(testPatient.getLineId()).isEqualTo(DEFAULT_LINE_ID);
        assertThat(testPatient.getFbId()).isEqualTo(DEFAULT_FB_ID);
        assertThat(testPatient.getReminder()).isEqualTo(DEFAULT_REMINDER);
        assertThat(testPatient.getWriteIcTime()).isEqualTo(DEFAULT_WRITE_IC_TIME);
    }

    @Test
    @Transactional
    public void createPatientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientRepository.findAll().size();

        // Create the Patient with an existing ID
        patient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setName(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNationalIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setNationalId(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setGender(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBirthIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setBirth(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPhoneIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientRepository.findAll().size();
        // set the field null
        patient.setPhone(null);

        // Create the Patient, which fails.

        restPatientMockMvc.perform(post("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPatients() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get all the patientList
        restPatientMockMvc.perform(get("/api/patients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].nationalId").value(hasItem(DEFAULT_NATIONAL_ID)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].birth").value(hasItem(DEFAULT_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].medicalId").value(hasItem(DEFAULT_MEDICAL_ID)))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO)))
            .andExpect(jsonPath("$.[*].blood").value(hasItem(DEFAULT_BLOOD.toString())))
            .andExpect(jsonPath("$.[*].cardId").value(hasItem(DEFAULT_CARD_ID)))
            .andExpect(jsonPath("$.[*].vip").value(hasItem(DEFAULT_VIP)))
            .andExpect(jsonPath("$.[*].emergencyName").value(hasItem(DEFAULT_EMERGENCY_NAME)))
            .andExpect(jsonPath("$.[*].emergencyPhone").value(hasItem(DEFAULT_EMERGENCY_PHONE)))
            .andExpect(jsonPath("$.[*].deleteDate").value(hasItem(sameInstant(DEFAULT_DELETE_DATE))))
            .andExpect(jsonPath("$.[*].scaling").value(hasItem(DEFAULT_SCALING.toString())))
            .andExpect(jsonPath("$.[*].allergy").value(hasItem(DEFAULT_ALLERGY)))
            .andExpect(jsonPath("$.[*].inconvenience").value(hasItem(DEFAULT_INCONVENIENCE)))
            .andExpect(jsonPath("$.[*].seriousDisease").value(hasItem(DEFAULT_SERIOUS_DISEASE)))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID)))
            .andExpect(jsonPath("$.[*].fbId").value(hasItem(DEFAULT_FB_ID)))
            .andExpect(jsonPath("$.[*].reminder").value(hasItem(DEFAULT_REMINDER)))
            .andExpect(jsonPath("$.[*].writeIcTime").value(hasItem(sameInstant(DEFAULT_WRITE_IC_TIME))));
    }
    
    @Test
    @Transactional
    public void getPatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(patient.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.nationalId").value(DEFAULT_NATIONAL_ID))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.birth").value(DEFAULT_BIRTH.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.medicalId").value(DEFAULT_MEDICAL_ID))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO))
            .andExpect(jsonPath("$.blood").value(DEFAULT_BLOOD.toString()))
            .andExpect(jsonPath("$.cardId").value(DEFAULT_CARD_ID))
            .andExpect(jsonPath("$.vip").value(DEFAULT_VIP))
            .andExpect(jsonPath("$.emergencyName").value(DEFAULT_EMERGENCY_NAME))
            .andExpect(jsonPath("$.emergencyPhone").value(DEFAULT_EMERGENCY_PHONE))
            .andExpect(jsonPath("$.deleteDate").value(sameInstant(DEFAULT_DELETE_DATE)))
            .andExpect(jsonPath("$.scaling").value(DEFAULT_SCALING.toString()))
            .andExpect(jsonPath("$.allergy").value(DEFAULT_ALLERGY))
            .andExpect(jsonPath("$.inconvenience").value(DEFAULT_INCONVENIENCE))
            .andExpect(jsonPath("$.seriousDisease").value(DEFAULT_SERIOUS_DISEASE))
            .andExpect(jsonPath("$.lineId").value(DEFAULT_LINE_ID))
            .andExpect(jsonPath("$.fbId").value(DEFAULT_FB_ID))
            .andExpect(jsonPath("$.reminder").value(DEFAULT_REMINDER))
            .andExpect(jsonPath("$.writeIcTime").value(sameInstant(DEFAULT_WRITE_IC_TIME)));
    }

    @Test
    @Transactional
    public void getNonExistingPatient() throws Exception {
        // Get the patient
        restPatientMockMvc.perform(get("/api/patients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatient() throws Exception {
        Patient introducer = createPatientByName("introducer");
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Update the patient
        Patient updatedPatient = patientRepository.findById(patient.getId()).get();
        // Disconnect from session so that the updates on updatedPatient are not directly saved in db
        em.detach(updatedPatient);
        updatedPatient
            .name(UPDATED_NAME)
            .nationalId(UPDATED_NATIONAL_ID)
            .gender(UPDATED_GENDER)
            .birth(UPDATED_BIRTH)
            .phone(UPDATED_PHONE)
            .medicalId(UPDATED_MEDICAL_ID)
            .zip(UPDATED_ZIP)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .photo(UPDATED_PHOTO)
            .blood(UPDATED_BLOOD)
            .cardId(UPDATED_CARD_ID)
            .vip(UPDATED_VIP)
            .dominantDoctor(extendUser)
            .firstDoctor(extendUser)
            .introducer(introducer)
            .emergencyName(UPDATED_EMERGENCY_NAME)
            .emergencyPhone(UPDATED_EMERGENCY_PHONE)
            .deleteDate(UPDATED_DELETE_DATE)
            .scaling(UPDATED_SCALING)
            .allergy(UPDATED_ALLERGY)
            .inconvenience(UPDATED_INCONVENIENCE)
            .seriousDisease(UPDATED_SERIOUS_DISEASE)
            .lineId(UPDATED_LINE_ID)
            .fbId(UPDATED_FB_ID)
            .reminder(UPDATED_REMINDER)
            .writeIcTime(UPDATED_WRITE_IC_TIME);

        restPatientMockMvc.perform(put("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatient)))
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPatient.getNationalId()).isEqualTo(UPDATED_NATIONAL_ID);
        assertThat(testPatient.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatient.getBirth()).isEqualTo(UPDATED_BIRTH);
        assertThat(testPatient.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPatient.getMedicalId()).isEqualTo(UPDATED_MEDICAL_ID);
        assertThat(testPatient.getZip()).isEqualTo(UPDATED_ZIP);
        assertThat(testPatient.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPatient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPatient.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPatient.getBlood()).isEqualTo(UPDATED_BLOOD);
        assertThat(testPatient.getCardId()).isEqualTo(UPDATED_CARD_ID);
        assertThat(testPatient.getVip()).isEqualTo(UPDATED_VIP);
        assertThat(testPatient.getDominantDoctor()).isEqualTo(extendUser);
        assertThat(testPatient.getFirstDoctor()).isEqualTo(extendUser);
        assertThat(testPatient.getIntroducer()).isEqualTo(introducer);
        assertThat(testPatient.getEmergencyName()).isEqualTo(UPDATED_EMERGENCY_NAME);
        assertThat(testPatient.getEmergencyPhone()).isEqualTo(UPDATED_EMERGENCY_PHONE);
        assertThat(testPatient.getDeleteDate()).isEqualTo(UPDATED_DELETE_DATE);
        assertThat(testPatient.getScaling()).isEqualTo(UPDATED_SCALING);
        assertThat(testPatient.isAllergy()).isEqualTo(UPDATED_ALLERGY);
        assertThat(testPatient.isInconvenience()).isEqualTo(UPDATED_INCONVENIENCE);
        assertThat(testPatient.isSeriousDisease()).isEqualTo(UPDATED_SERIOUS_DISEASE);
        assertThat(testPatient.getLineId()).isEqualTo(UPDATED_LINE_ID);
        assertThat(testPatient.getFbId()).isEqualTo(UPDATED_FB_ID);
        assertThat(testPatient.getReminder()).isEqualTo(UPDATED_REMINDER);
        assertThat(testPatient.getWriteIcTime()).isEqualTo(UPDATED_WRITE_IC_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingPatient() throws Exception {
        int databaseSizeBeforeUpdate = patientRepository.findAll().size();

        // Create the Patient

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientMockMvc.perform(put("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(patient)))
            .andExpect(status().isBadRequest());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePatient() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int databaseSizeBeforeDelete = patientRepository.findAll().size();

        // Get the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}", patient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Patient.class);
        Patient patient1 = new Patient();
        patient1.setId(1L);
        Patient patient2 = new Patient();
        patient2.setId(patient1.getId());
        assertThat(patient1).isEqualTo(patient2);
        patient2.setId(2L);
        assertThat(patient1).isNotEqualTo(patient2);
        patient1.setId(null);
        assertThat(patient1).isNotEqualTo(patient2);
    }

    @Test
    @Transactional
    public void getPatientParents() throws Exception {
        Patient parent1 = createPatientByName("parent1");
        Patient parent2 = createPatientByName("parent2");
        patient.getParents().addAll(Arrays.asList(parent1, parent2));

        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the parents
        restPatientMockMvc.perform(get("/api/patients/{id}/parents", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[0].name").value(parent1.getName()))
            .andExpect(jsonPath("$.[1].name").value(parent2.getName()));
    }

    @Test
    @Transactional
    public void createPatientParent() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        Patient parent = createPatientByName("parent");

        // Create a parent of the patient
        restPatientMockMvc.perform(post("/api/patients/{id}/parents/{parent_id}", patient.getId(), parent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(patient.getParents().size()));
    }

    @Test
    @Transactional
    public void deletePatientParent() throws Exception {
        Patient parent = createPatientByName("parent");
        patient.getParents().add(parent);

        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Delete a parent of the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}/parents/{parent_id}", patient.getId(), parent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(patient.getParents().size()));
    }

    @Test
    @Transactional
    public void getPatientChildren() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        Patient child1 = createPatientByName("child1");
        child1.getParents().add(patient);
        Patient child2 = createPatientByName("child2");
        child2.getParents().add(patient);
        patient.getChildren().addAll(Arrays.asList(child1, child2));

        // Initialize the database
        patientRepository.saveAndFlush(child1);
        patientRepository.saveAndFlush(child2);

        // Get the children
        restPatientMockMvc.perform(get("/api/patients/{id}/children", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[0].name").value(child1.getName()))
            .andExpect(jsonPath("$.[1].name").value(child2.getName()));
    }

    @Test
    @Transactional
    public void createPatientChild() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        Patient child = createPatientByName("child");

        // Create a child of the patient
        restPatientMockMvc.perform(post("/api/patients/{id}/children/{child_id}", patient.getId(), child.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(patient.getChildren().size()));
    }

    @Test
    @Transactional
    public void deletePatientChild() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        Patient child = createPatientByName("child");
        child.getParents().add(patient);
        patient.getChildren().add(child);
        patientRepository.saveAndFlush(child);

        int childrenSizeBeforeTest = patientRepository.findById(patient.getId()).get().getChildren().size();

        // Delete a child of the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}/children/{child_id}", patient.getId(), child.getId()))
            .andExpect(status().isOk());

        assertThat(patientRepository.findById(patient.getId()).get().getChildren()).hasSize(childrenSizeBeforeTest - 1);
    }

    private Patient createPatientByName(String name) {
        Patient patient = createEntity(em);
        patient.setName(name);
        patientRepository.saveAndFlush(patient);

        return patient;
    }
}
