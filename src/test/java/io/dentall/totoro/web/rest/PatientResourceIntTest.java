package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.domain.enumeration.TagName;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.QuestionnaireRepository;
import io.dentall.totoro.repository.TagRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.PatientService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
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

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final Gender DEFAULT_GENDER = Gender.OTHER;
    private static final Gender UPDATED_GENDER = Gender.MALE;

    private static final LocalDate DEFAULT_BIRTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_BIRTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_NATIONAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_NATIONAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAL_ID = "BBBBBBBBBB";

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

    private static final Instant DEFAULT_DELETE_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DELETE_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final LocalDate DEFAULT_SCALING = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SCALING = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LINE_ID = "AAAAAAAAAA";
    private static final String UPDATED_LINE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_FB_ID = "AAAAAAAAAA";
    private static final String UPDATED_FB_ID = "BBBBBBBBBB";

    private static final String DEFAULT_REMINDER = "AAAAAAAAAA";
    private static final String UPDATED_REMINDER = "BBBBBBBBBB";

    private static final Instant DEFAULT_WRITE_IC_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_WRITE_IC_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_BURDEN_COST = 1;
    private static final Integer UPDATED_BURDEN_COST = 2;

    @Autowired
    private PatientRepository patientRepository;

    @Mock
    private PatientRepository patientRepositoryMock;

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
    private TagRepository tagRepository;

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private PatientService patientService;

    private MockMvc restPatientMockMvc;

    private Patient patient;

    private ExtendUser extendUser;

    private Tag tag;

    private Questionnaire questionnaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientResource patientResource = new PatientResource(patientRepository, tagRepository, patientService);
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
            .phone(DEFAULT_PHONE)
            .gender(DEFAULT_GENDER)
            .birth(DEFAULT_BIRTH)
            .nationalId(DEFAULT_NATIONAL_ID)
            .medicalId(DEFAULT_MEDICAL_ID)
            .address(DEFAULT_ADDRESS)
            .email(DEFAULT_EMAIL)
            .photo(DEFAULT_PHOTO)
            .blood(DEFAULT_BLOOD)
            .cardId(DEFAULT_CARD_ID)
            .vip(DEFAULT_VIP)
            .emergencyName(DEFAULT_EMERGENCY_NAME)
            .emergencyPhone(DEFAULT_EMERGENCY_PHONE)
            .deleteDate(DEFAULT_DELETE_DATE)
            .scaling(DEFAULT_SCALING)
            .lineId(DEFAULT_LINE_ID)
            .fbId(DEFAULT_FB_ID)
            .reminder(DEFAULT_REMINDER)
            .writeIcTime(DEFAULT_WRITE_IC_TIME)
            .burdenCost(DEFAULT_BURDEN_COST);
        return patient;
    }

    @Before
    public void initTest() {
        User user = userRepository.save(UserResourceIntTest.createEntity(em));
        extendUser = user.getExtendUser();

        patient = createEntity(em);
        patient.setDominantDoctor(extendUser);
        patient.setFirstDoctor(extendUser);

        tag = TagResourceIntTest.createEntity(em);
        patient.addTag(tagRepository.save(tag));

        questionnaire = QuestionnaireResourceIntTest.createEntity(em);
        patient.setQuestionnaire(questionnaire);

        patientService.setTagsByQuestionnaire(patient.getTags(), patient.getQuestionnaire());
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
        assertThat(testPatient.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testPatient.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testPatient.getBirth()).isEqualTo(DEFAULT_BIRTH);
        assertThat(testPatient.getNationalId()).isEqualTo(DEFAULT_NATIONAL_ID);
        assertThat(testPatient.getMedicalId()).isEqualTo(DEFAULT_MEDICAL_ID);
        assertThat(testPatient.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testPatient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testPatient.getPhoto()).isEqualTo(DEFAULT_PHOTO);
        assertThat(testPatient.getBlood()).isEqualTo(DEFAULT_BLOOD);
        assertThat(testPatient.getCardId()).isEqualTo(DEFAULT_CARD_ID);
        assertThat(testPatient.getVip()).isEqualTo(DEFAULT_VIP);
        assertThat(testPatient.getEmergencyName()).isEqualTo(DEFAULT_EMERGENCY_NAME);
        assertThat(testPatient.getEmergencyPhone()).isEqualTo(DEFAULT_EMERGENCY_PHONE);
        assertThat(testPatient.getDeleteDate()).isEqualTo(DEFAULT_DELETE_DATE);
        assertThat(testPatient.getScaling()).isEqualTo(DEFAULT_SCALING);
        assertThat(testPatient.getLineId()).isEqualTo(DEFAULT_LINE_ID);
        assertThat(testPatient.getFbId()).isEqualTo(DEFAULT_FB_ID);
        assertThat(testPatient.getReminder()).isEqualTo(DEFAULT_REMINDER);
        assertThat(testPatient.getWriteIcTime()).isEqualTo(DEFAULT_WRITE_IC_TIME);
        assertThat(testPatient.getBurdenCost()).isEqualTo(DEFAULT_BURDEN_COST);
        assertThat(testPatient.getDominantDoctor()).isEqualTo(extendUser);
        assertThat(testPatient.getFirstDoctor()).isEqualTo(extendUser);
        assertThat(testPatient.getIntroducer()).isEqualTo(null);
        assertThat(testPatient.getTags()).contains(tag);
        assertThat(testPatient.getQuestionnaire().getHepatitisType()).isEqualTo(patient.getQuestionnaire().getHepatitisType());

        tagRepository.findById(TagName.Hypertension.getValue()).ifPresent(tag -> assertThat(testPatient.getTags()).contains(tag));
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
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].birth").value(hasItem(DEFAULT_BIRTH.toString())))
            .andExpect(jsonPath("$.[*].nationalId").value(hasItem(DEFAULT_NATIONAL_ID.toString())))
            .andExpect(jsonPath("$.[*].medicalId").value(hasItem(DEFAULT_MEDICAL_ID.toString())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].photo").value(hasItem(DEFAULT_PHOTO.toString())))
            .andExpect(jsonPath("$.[*].blood").value(hasItem(DEFAULT_BLOOD.toString())))
            .andExpect(jsonPath("$.[*].cardId").value(hasItem(DEFAULT_CARD_ID.toString())))
            .andExpect(jsonPath("$.[*].vip").value(hasItem(DEFAULT_VIP.toString())))
            .andExpect(jsonPath("$.[*].emergencyName").value(hasItem(DEFAULT_EMERGENCY_NAME.toString())))
            .andExpect(jsonPath("$.[*].emergencyPhone").value(hasItem(DEFAULT_EMERGENCY_PHONE.toString())))
            .andExpect(jsonPath("$.[*].deleteDate").value(hasItem(DEFAULT_DELETE_DATE.toString())))
            .andExpect(jsonPath("$.[*].scaling").value(hasItem(DEFAULT_SCALING.toString())))
            .andExpect(jsonPath("$.[*].lineId").value(hasItem(DEFAULT_LINE_ID.toString())))
            .andExpect(jsonPath("$.[*].fbId").value(hasItem(DEFAULT_FB_ID.toString())))
            .andExpect(jsonPath("$.[*].reminder").value(hasItem(DEFAULT_REMINDER.toString())))
            .andExpect(jsonPath("$.[*].writeIcTime").value(hasItem(DEFAULT_WRITE_IC_TIME.toString())))
            .andExpect(jsonPath("$.[*].burdenCost").value(hasItem(DEFAULT_BURDEN_COST)))
            .andExpect(jsonPath("$.[*].questionnaire.id").value(hasItem(questionnaire.getId().intValue())));
    }
    
    public void getAllPatientsWithEagerRelationshipsIsEnabled() throws Exception {
        PatientResource patientResource = new PatientResource(patientRepositoryMock, tagRepository, patientService);
        when(patientRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restPatientMockMvc = MockMvcBuilders.standaloneSetup(patientResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restPatientMockMvc.perform(get("/api/patients?eagerload=true"))
        .andExpect(status().isOk());

        verify(patientRepositoryMock, times(1)).findAllWithEagerRelationships(any());
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
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.birth").value(DEFAULT_BIRTH.toString()))
            .andExpect(jsonPath("$.nationalId").value(DEFAULT_NATIONAL_ID.toString()))
            .andExpect(jsonPath("$.medicalId").value(DEFAULT_MEDICAL_ID.toString()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.photo").value(DEFAULT_PHOTO.toString()))
            .andExpect(jsonPath("$.blood").value(DEFAULT_BLOOD.toString()))
            .andExpect(jsonPath("$.cardId").value(DEFAULT_CARD_ID.toString()))
            .andExpect(jsonPath("$.vip").value(DEFAULT_VIP.toString()))
            .andExpect(jsonPath("$.emergencyName").value(DEFAULT_EMERGENCY_NAME.toString()))
            .andExpect(jsonPath("$.emergencyPhone").value(DEFAULT_EMERGENCY_PHONE.toString()))
            .andExpect(jsonPath("$.deleteDate").value(DEFAULT_DELETE_DATE.toString()))
            .andExpect(jsonPath("$.scaling").value(DEFAULT_SCALING.toString()))
            .andExpect(jsonPath("$.lineId").value(DEFAULT_LINE_ID.toString()))
            .andExpect(jsonPath("$.fbId").value(DEFAULT_FB_ID.toString()))
            .andExpect(jsonPath("$.reminder").value(DEFAULT_REMINDER.toString()))
            .andExpect(jsonPath("$.writeIcTime").value(DEFAULT_WRITE_IC_TIME.toString()))
            .andExpect(jsonPath("$.burdenCost").value(DEFAULT_BURDEN_COST));
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

        // Update the questionnaire
        Questionnaire updatedQuestionnaire = questionnaireRepository.findById(patient.getQuestionnaire().getId()).get();

        // Disconnect from session so that the updates on updatedPatient are not directly saved in db
        em.detach(updatedPatient);
        em.detach(updatedQuestionnaire);

        String updateOther = "other";
        String nonUpdateOtherInTreatment = updatedQuestionnaire.getOtherInTreatment();
        updatedQuestionnaire = updatedQuestionnaire
            .other(updateOther)
            .otherInTreatment(null);

        updatedPatient
            .name(UPDATED_NAME)
            .phone(UPDATED_PHONE)
            .gender(UPDATED_GENDER)
            .birth(UPDATED_BIRTH)
            .nationalId(UPDATED_NATIONAL_ID)
            .medicalId(UPDATED_MEDICAL_ID)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .photo(UPDATED_PHOTO)
            .blood(UPDATED_BLOOD)
            .cardId(UPDATED_CARD_ID)
            .vip(UPDATED_VIP)
            .emergencyName(UPDATED_EMERGENCY_NAME)
            .emergencyPhone(UPDATED_EMERGENCY_PHONE)
            .deleteDate(UPDATED_DELETE_DATE)
            .scaling(UPDATED_SCALING)
            .lineId(UPDATED_LINE_ID)
            .fbId(UPDATED_FB_ID)
            .reminder(UPDATED_REMINDER)
            .writeIcTime(UPDATED_WRITE_IC_TIME)
            .burdenCost(UPDATED_BURDEN_COST)
            .introducer(introducer)
            .questionnaire(updatedQuestionnaire);

        restPatientMockMvc.perform(put("/api/patients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatient)))
            .andExpect(status().isOk());

        // Validate the Patient in the database
        List<Patient> patientList = patientRepository.findAll();
        assertThat(patientList).hasSize(databaseSizeBeforeUpdate);
        Patient testPatient = patientList.get(patientList.size() - 1);
        assertThat(testPatient.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPatient.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testPatient.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testPatient.getBirth()).isEqualTo(UPDATED_BIRTH);
        assertThat(testPatient.getNationalId()).isEqualTo(UPDATED_NATIONAL_ID);
        assertThat(testPatient.getMedicalId()).isEqualTo(UPDATED_MEDICAL_ID);
        assertThat(testPatient.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testPatient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testPatient.getPhoto()).isEqualTo(UPDATED_PHOTO);
        assertThat(testPatient.getBlood()).isEqualTo(UPDATED_BLOOD);
        assertThat(testPatient.getCardId()).isEqualTo(UPDATED_CARD_ID);
        assertThat(testPatient.getVip()).isEqualTo(UPDATED_VIP);
        assertThat(testPatient.getEmergencyName()).isEqualTo(UPDATED_EMERGENCY_NAME);
        assertThat(testPatient.getEmergencyPhone()).isEqualTo(UPDATED_EMERGENCY_PHONE);
        assertThat(testPatient.getDeleteDate()).isEqualTo(UPDATED_DELETE_DATE);
        assertThat(testPatient.getScaling()).isEqualTo(UPDATED_SCALING);
        assertThat(testPatient.getLineId()).isEqualTo(UPDATED_LINE_ID);
        assertThat(testPatient.getFbId()).isEqualTo(UPDATED_FB_ID);
        assertThat(testPatient.getReminder()).isEqualTo(UPDATED_REMINDER);
        assertThat(testPatient.getWriteIcTime()).isEqualTo(UPDATED_WRITE_IC_TIME);
        assertThat(testPatient.getBurdenCost()).isEqualTo(UPDATED_BURDEN_COST);
        assertThat(testPatient.getIntroducer()).isEqualTo(introducer);
        assertThat(testPatient.getQuestionnaire().getOther()).isEqualTo(updateOther);
        assertThat(testPatient.getQuestionnaire().getOtherInTreatment()).isEqualTo(nonUpdateOtherInTreatment);
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
        patient.addParent(parent1);
        Patient parent2 = createPatientByName("parent2");
        patient.addParent(parent2);

        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the parents
        restPatientMockMvc.perform(get("/api/patients/{id}/parents", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].name").value(hasItem(parent2.getName())));
    }

    @Test
    @Transactional
    public void createPatientParent() throws Exception {
        Patient parent = createPatientByName("parent");

        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int parentsSizeBeforeTest = patientRepository.findById(patient.getId()).get().getParents().size();

        // Create a parent of the patient
        restPatientMockMvc.perform(post("/api/patients/{id}/parents/{parent_id}", patient.getId(), parent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(parentsSizeBeforeTest + 1));
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
            .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @Transactional
    public void getPatientChildren() throws Exception {
        Patient child1 = createPatientByName("child1");
        patient.addChild(child1);
        Patient child2 = createPatientByName("child2");
        patient.addChild(child2);

        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the children
        restPatientMockMvc.perform(get("/api/patients/{id}/children", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @Transactional
    public void createPatientChild() throws Exception {
        Patient child = createPatientByName("child");

        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Create a child of the patient
        restPatientMockMvc.perform(post("/api/patients/{id}/children/{child_id}", patient.getId(), child.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[0].name").value(child.getName()));
    }

    @Test
    @Transactional
    public void deletePatientChild() throws Exception {
        Patient child = createPatientByName("child");
        patient.addChild(child);

        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int childrenSizeBeforeTest = patientRepository.findById(patient.getId()).get().getChildren().size();

        // Delete a child of the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}/children/{child_id}", patient.getId(), child.getId()))
            .andExpect(status().isOk());

        assertThat(patientRepository.findById(patient.getId()).get().getChildren()).hasSize(childrenSizeBeforeTest - 1);
    }

    @Test
    @Transactional
    public void getPatientSpouse1S() throws Exception {
        Patient andy = createPatientByName("andy");
        patient.addSpouse1(andy);
        Patient john = createPatientByName("john");
        patient.addSpouse1(john);

        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the spouse1S
        restPatientMockMvc.perform(get("/api/patients/{id}/spouse1S", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].name").value(hasItem(andy.getName())));
    }

    @Test
    @Transactional
    public void createPatientSpouse1() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        Patient spouse1 = createPatientByName("spouse1");

        // Create a spouse1 of the patient
        restPatientMockMvc.perform(post("/api/patients/{id}/spouse1S/{spouse1_id}", patient.getId(), spouse1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @Transactional
    public void deletePatientSpouse1() throws Exception {
        Patient spouse1 = createPatientByName("spouse1");
        patient.addSpouse1(spouse1);

        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Delete a spouse1 of the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}/spouse1S/{spouse1_id}", patient.getId(), spouse1.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @Transactional
    public void getPatientSpouse2S() throws Exception {
        Patient andy = createPatientByName("andy");
        patient.addSpouse2(andy);

        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the spouse2S
        restPatientMockMvc.perform(get("/api/patients/{id}/spouse2S", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[0].name").value(andy.getName()));
    }

    @Test
    @Transactional
    public void createPatientSpouse2() throws Exception {
        Patient spouse2 = createPatientByName("spouse2");

        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Create a spouse2 of the patient
        restPatientMockMvc.perform(post("/api/patients/{id}/spouse2S/{spouse2_id}", patient.getId(), spouse2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @Transactional
    public void deletePatientSpouse2() throws Exception {
        Patient spouse2 = createPatientByName("spouse2");
        patient.addSpouse2(spouse2);

        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Delete a spouse1 of the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}/spouse2S/{spouse2_id}", patient.getId(), spouse2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    @Transactional
    public void getPatientTag() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get the tag
        restPatientMockMvc.perform(get("/api/patients/{id}/tags", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].name").value(hasItem(tag.getName())));
    }

    @Test
    @Transactional
    public void createPatientTag() throws Exception {
        // Initialize the database
        patient.getTags().remove(tag);
        patientRepository.saveAndFlush(patient);

        int size = patient.getTags().size();

        // Create a tag of the patient
        restPatientMockMvc.perform(post("/api/patients/{id}/tags/{tag_id}", patient.getId(), tag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(size + 1));
    }

    @Test
    @Transactional
    public void deletePatientTag() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        int size = patient.getTags().size();

        // Delete a tag of the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}/tags/{tag_id}", patient.getId(), tag.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(size - 1));
    }

    @Test
    @Transactional
    public void getPatientsByQueryNameContaining() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get patients by name
        restPatientMockMvc.perform(get("/api/patients?search={keyword}&sort=id,desc", patient.getName().substring(2, 5)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].name").value(hasItem(patient.getName())));
    }

    @Test
    @Transactional
    public void getPatientsByQueryBirthContaining() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // Get patients by birth
        restPatientMockMvc.perform(get("/api/patients?search={keyword}&sort=id,desc", patient.getBirth().format(formatter).substring(2, 5)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].birth").value(hasItem(patient.getBirth().toString())));
    }

    @Test
    @Transactional
    public void getPatientsByQueryBirthContainingAndIsDeleted() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // Get patients by birth and deleted
        restPatientMockMvc.perform(get("/api/patients?search={keyword}&isDeleted={isDeleted}&sort=id,desc", patient.getBirth().format(formatter).substring(2, 5), true))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].deleteDate").value(hasItem(patient.getDeleteDate().toString())));
    }

    @Test
    @Transactional
    public void getPatientsByQueryIsNotDeleted() throws Exception {
        patient.setDeleteDate(null);
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Get patients by not deleted
        restPatientMockMvc.perform(get("/api/patients?isDeleted={isDeleted}&sort=id,desc", false))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patient.getId().intValue())));
    }

    @Test
    @Transactional
    public void createPatientTags() throws Exception {
        Tag tag1 = TagResourceIntTest.createEntity(em);
        tag1.setName("tag1");
        tagRepository.save(tag1);

        Tag tag2 = TagResourceIntTest.createEntity(em);
        tag1.setName("tag2");
        tagRepository.save(tag2);

        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Create a tag of the patient
        restPatientMockMvc.perform(
            post("/api/patients/{id}/tags", patient.getId())
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(Arrays.asList(tag1, tag2)))
        )

            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].name").value(hasItem(tag1.getName())));
    }

    @Test
    @Transactional
    public void createDeleteTags() throws Exception {
        // Initialize the database
        patientRepository.saveAndFlush(patient);

        // Delete tags of the patient
        restPatientMockMvc.perform(delete("/api/patients/{id}/tags", patient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.length()").value(0));
    }

    private Patient createPatientByName(String name) {
        Patient patient = createEntity(em);
        patient.setName(name);

        return patientRepository.save(patient);
    }
}
