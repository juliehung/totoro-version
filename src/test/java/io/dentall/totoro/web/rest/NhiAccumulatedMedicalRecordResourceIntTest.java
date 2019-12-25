package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.NhiAccumulatedMedicalRecord;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.NhiAccumulatedMedicalRecordRepository;
import io.dentall.totoro.service.NhiAccumulatedMedicalRecordService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.dto.NhiAccumulatedMedicalRecordCriteria;
import io.dentall.totoro.service.NhiAccumulatedMedicalRecordQueryService;

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
 * Test class for the NhiAccumulatedMedicalRecordResource REST controller.
 *
 * @see NhiAccumulatedMedicalRecordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiAccumulatedMedicalRecordResourceIntTest {

    private static final String DEFAULT_MEDICAL_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAL_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_NEWBORN_MEDICAL_TREATMENT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NEWBORN_MEDICAL_TREATMENT_NOTE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CARD_FILLING_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_CARD_FILLING_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_SEQ_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_SEQ_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_MEDICAL_INSTITUTION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_MEDICAL_INSTITUTION_CODE = "BBBBBBBBBB";

    @Autowired
    private NhiAccumulatedMedicalRecordRepository nhiAccumulatedMedicalRecordRepository;

    @Autowired
    private NhiAccumulatedMedicalRecordService nhiAccumulatedMedicalRecordService;

    @Autowired
    private NhiAccumulatedMedicalRecordQueryService nhiAccumulatedMedicalRecordQueryService;

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

    private MockMvc restNhiAccumulatedMedicalRecordMockMvc;

    private NhiAccumulatedMedicalRecord nhiAccumulatedMedicalRecord;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhiAccumulatedMedicalRecordResource nhiAccumulatedMedicalRecordResource = new NhiAccumulatedMedicalRecordResource(nhiAccumulatedMedicalRecordService, nhiAccumulatedMedicalRecordQueryService);
        this.restNhiAccumulatedMedicalRecordMockMvc = MockMvcBuilders.standaloneSetup(nhiAccumulatedMedicalRecordResource)
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
    public static NhiAccumulatedMedicalRecord createEntity(EntityManager em) {
        NhiAccumulatedMedicalRecord nhiAccumulatedMedicalRecord = new NhiAccumulatedMedicalRecord()
            .medicalCategory(DEFAULT_MEDICAL_CATEGORY)
            .newbornMedicalTreatmentNote(DEFAULT_NEWBORN_MEDICAL_TREATMENT_NOTE)
            .date(DEFAULT_DATE)
            .cardFillingNote(DEFAULT_CARD_FILLING_NOTE)
            .seqNumber(DEFAULT_SEQ_NUMBER)
            .medicalInstitutionCode(DEFAULT_MEDICAL_INSTITUTION_CODE);
        // Add required entity
        Patient patient = PatientResourceIntTest.createEntity(em);
        em.persist(patient);
        em.flush();
        nhiAccumulatedMedicalRecord.setPatient(patient);
        return nhiAccumulatedMedicalRecord;
    }

    @Before
    public void initTest() {
        nhiAccumulatedMedicalRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhiAccumulatedMedicalRecord() throws Exception {
        int databaseSizeBeforeCreate = nhiAccumulatedMedicalRecordRepository.findAll().size();

        // Create the NhiAccumulatedMedicalRecord
        restNhiAccumulatedMedicalRecordMockMvc.perform(post("/api/nhi-accumulated-medical-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiAccumulatedMedicalRecord)))
            .andExpect(status().isCreated());

        // Validate the NhiAccumulatedMedicalRecord in the database
        List<NhiAccumulatedMedicalRecord> nhiAccumulatedMedicalRecordList = nhiAccumulatedMedicalRecordRepository.findAll();
        assertThat(nhiAccumulatedMedicalRecordList).hasSize(databaseSizeBeforeCreate + 1);
        NhiAccumulatedMedicalRecord testNhiAccumulatedMedicalRecord = nhiAccumulatedMedicalRecordList.get(nhiAccumulatedMedicalRecordList.size() - 1);
        assertThat(testNhiAccumulatedMedicalRecord.getMedicalCategory()).isEqualTo(DEFAULT_MEDICAL_CATEGORY);
        assertThat(testNhiAccumulatedMedicalRecord.getNewbornMedicalTreatmentNote()).isEqualTo(DEFAULT_NEWBORN_MEDICAL_TREATMENT_NOTE);
        assertThat(testNhiAccumulatedMedicalRecord.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testNhiAccumulatedMedicalRecord.getCardFillingNote()).isEqualTo(DEFAULT_CARD_FILLING_NOTE);
        assertThat(testNhiAccumulatedMedicalRecord.getSeqNumber()).isEqualTo(DEFAULT_SEQ_NUMBER);
        assertThat(testNhiAccumulatedMedicalRecord.getMedicalInstitutionCode()).isEqualTo(DEFAULT_MEDICAL_INSTITUTION_CODE);
    }

    @Test
    @Transactional
    public void createNhiAccumulatedMedicalRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhiAccumulatedMedicalRecordRepository.findAll().size();

        // Create the NhiAccumulatedMedicalRecord with an existing ID
        nhiAccumulatedMedicalRecord.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhiAccumulatedMedicalRecordMockMvc.perform(post("/api/nhi-accumulated-medical-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiAccumulatedMedicalRecord)))
            .andExpect(status().isBadRequest());

        // Validate the NhiAccumulatedMedicalRecord in the database
        List<NhiAccumulatedMedicalRecord> nhiAccumulatedMedicalRecordList = nhiAccumulatedMedicalRecordRepository.findAll();
        assertThat(nhiAccumulatedMedicalRecordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecords() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList
        restNhiAccumulatedMedicalRecordMockMvc.perform(get("/api/nhi-accumulated-medical-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiAccumulatedMedicalRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].medicalCategory").value(hasItem(DEFAULT_MEDICAL_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].newbornMedicalTreatmentNote").value(hasItem(DEFAULT_NEWBORN_MEDICAL_TREATMENT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].cardFillingNote").value(hasItem(DEFAULT_CARD_FILLING_NOTE.toString())))
            .andExpect(jsonPath("$.[*].seqNumber").value(hasItem(DEFAULT_SEQ_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].medicalInstitutionCode").value(hasItem(DEFAULT_MEDICAL_INSTITUTION_CODE.toString())));
    }

    @Test
    @Transactional
    public void getNhiAccumulatedMedicalRecord() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get the nhiAccumulatedMedicalRecord
        restNhiAccumulatedMedicalRecordMockMvc.perform(get("/api/nhi-accumulated-medical-records/{id}", nhiAccumulatedMedicalRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhiAccumulatedMedicalRecord.getId().intValue()))
            .andExpect(jsonPath("$.medicalCategory").value(DEFAULT_MEDICAL_CATEGORY.toString()))
            .andExpect(jsonPath("$.newbornMedicalTreatmentNote").value(DEFAULT_NEWBORN_MEDICAL_TREATMENT_NOTE.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.cardFillingNote").value(DEFAULT_CARD_FILLING_NOTE.toString()))
            .andExpect(jsonPath("$.seqNumber").value(DEFAULT_SEQ_NUMBER.toString()))
            .andExpect(jsonPath("$.medicalInstitutionCode").value(DEFAULT_MEDICAL_INSTITUTION_CODE.toString()));
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByMedicalCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where medicalCategory equals to DEFAULT_MEDICAL_CATEGORY
        defaultNhiAccumulatedMedicalRecordShouldBeFound("medicalCategory.equals=" + DEFAULT_MEDICAL_CATEGORY);

        // Get all the nhiAccumulatedMedicalRecordList where medicalCategory equals to UPDATED_MEDICAL_CATEGORY
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("medicalCategory.equals=" + UPDATED_MEDICAL_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByMedicalCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where medicalCategory in DEFAULT_MEDICAL_CATEGORY or UPDATED_MEDICAL_CATEGORY
        defaultNhiAccumulatedMedicalRecordShouldBeFound("medicalCategory.in=" + DEFAULT_MEDICAL_CATEGORY + "," + UPDATED_MEDICAL_CATEGORY);

        // Get all the nhiAccumulatedMedicalRecordList where medicalCategory equals to UPDATED_MEDICAL_CATEGORY
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("medicalCategory.in=" + UPDATED_MEDICAL_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByMedicalCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where medicalCategory is not null
        defaultNhiAccumulatedMedicalRecordShouldBeFound("medicalCategory.specified=true");

        // Get all the nhiAccumulatedMedicalRecordList where medicalCategory is null
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("medicalCategory.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByNewbornMedicalTreatmentNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where newbornMedicalTreatmentNote equals to DEFAULT_NEWBORN_MEDICAL_TREATMENT_NOTE
        defaultNhiAccumulatedMedicalRecordShouldBeFound("newbornMedicalTreatmentNote.equals=" + DEFAULT_NEWBORN_MEDICAL_TREATMENT_NOTE);

        // Get all the nhiAccumulatedMedicalRecordList where newbornMedicalTreatmentNote equals to UPDATED_NEWBORN_MEDICAL_TREATMENT_NOTE
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("newbornMedicalTreatmentNote.equals=" + UPDATED_NEWBORN_MEDICAL_TREATMENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByNewbornMedicalTreatmentNoteIsInShouldWork() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where newbornMedicalTreatmentNote in DEFAULT_NEWBORN_MEDICAL_TREATMENT_NOTE or UPDATED_NEWBORN_MEDICAL_TREATMENT_NOTE
        defaultNhiAccumulatedMedicalRecordShouldBeFound("newbornMedicalTreatmentNote.in=" + DEFAULT_NEWBORN_MEDICAL_TREATMENT_NOTE + "," + UPDATED_NEWBORN_MEDICAL_TREATMENT_NOTE);

        // Get all the nhiAccumulatedMedicalRecordList where newbornMedicalTreatmentNote equals to UPDATED_NEWBORN_MEDICAL_TREATMENT_NOTE
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("newbornMedicalTreatmentNote.in=" + UPDATED_NEWBORN_MEDICAL_TREATMENT_NOTE);
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByNewbornMedicalTreatmentNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where newbornMedicalTreatmentNote is not null
        defaultNhiAccumulatedMedicalRecordShouldBeFound("newbornMedicalTreatmentNote.specified=true");

        // Get all the nhiAccumulatedMedicalRecordList where newbornMedicalTreatmentNote is null
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("newbornMedicalTreatmentNote.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where date equals to DEFAULT_DATE
        defaultNhiAccumulatedMedicalRecordShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the nhiAccumulatedMedicalRecordList where date equals to UPDATED_DATE
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where date in DEFAULT_DATE or UPDATED_DATE
        defaultNhiAccumulatedMedicalRecordShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the nhiAccumulatedMedicalRecordList where date equals to UPDATED_DATE
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where date is not null
        defaultNhiAccumulatedMedicalRecordShouldBeFound("date.specified=true");

        // Get all the nhiAccumulatedMedicalRecordList where date is null
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByCardFillingNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where cardFillingNote equals to DEFAULT_CARD_FILLING_NOTE
        defaultNhiAccumulatedMedicalRecordShouldBeFound("cardFillingNote.equals=" + DEFAULT_CARD_FILLING_NOTE);

        // Get all the nhiAccumulatedMedicalRecordList where cardFillingNote equals to UPDATED_CARD_FILLING_NOTE
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("cardFillingNote.equals=" + UPDATED_CARD_FILLING_NOTE);
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByCardFillingNoteIsInShouldWork() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where cardFillingNote in DEFAULT_CARD_FILLING_NOTE or UPDATED_CARD_FILLING_NOTE
        defaultNhiAccumulatedMedicalRecordShouldBeFound("cardFillingNote.in=" + DEFAULT_CARD_FILLING_NOTE + "," + UPDATED_CARD_FILLING_NOTE);

        // Get all the nhiAccumulatedMedicalRecordList where cardFillingNote equals to UPDATED_CARD_FILLING_NOTE
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("cardFillingNote.in=" + UPDATED_CARD_FILLING_NOTE);
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByCardFillingNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where cardFillingNote is not null
        defaultNhiAccumulatedMedicalRecordShouldBeFound("cardFillingNote.specified=true");

        // Get all the nhiAccumulatedMedicalRecordList where cardFillingNote is null
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("cardFillingNote.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsBySeqNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where seqNumber equals to DEFAULT_SEQ_NUMBER
        defaultNhiAccumulatedMedicalRecordShouldBeFound("seqNumber.equals=" + DEFAULT_SEQ_NUMBER);

        // Get all the nhiAccumulatedMedicalRecordList where seqNumber equals to UPDATED_SEQ_NUMBER
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("seqNumber.equals=" + UPDATED_SEQ_NUMBER);
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsBySeqNumberIsInShouldWork() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where seqNumber in DEFAULT_SEQ_NUMBER or UPDATED_SEQ_NUMBER
        defaultNhiAccumulatedMedicalRecordShouldBeFound("seqNumber.in=" + DEFAULT_SEQ_NUMBER + "," + UPDATED_SEQ_NUMBER);

        // Get all the nhiAccumulatedMedicalRecordList where seqNumber equals to UPDATED_SEQ_NUMBER
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("seqNumber.in=" + UPDATED_SEQ_NUMBER);
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsBySeqNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where seqNumber is not null
        defaultNhiAccumulatedMedicalRecordShouldBeFound("seqNumber.specified=true");

        // Get all the nhiAccumulatedMedicalRecordList where seqNumber is null
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("seqNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByMedicalInstitutionCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where medicalInstitutionCode equals to DEFAULT_MEDICAL_INSTITUTION_CODE
        defaultNhiAccumulatedMedicalRecordShouldBeFound("medicalInstitutionCode.equals=" + DEFAULT_MEDICAL_INSTITUTION_CODE);

        // Get all the nhiAccumulatedMedicalRecordList where medicalInstitutionCode equals to UPDATED_MEDICAL_INSTITUTION_CODE
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("medicalInstitutionCode.equals=" + UPDATED_MEDICAL_INSTITUTION_CODE);
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByMedicalInstitutionCodeIsInShouldWork() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where medicalInstitutionCode in DEFAULT_MEDICAL_INSTITUTION_CODE or UPDATED_MEDICAL_INSTITUTION_CODE
        defaultNhiAccumulatedMedicalRecordShouldBeFound("medicalInstitutionCode.in=" + DEFAULT_MEDICAL_INSTITUTION_CODE + "," + UPDATED_MEDICAL_INSTITUTION_CODE);

        // Get all the nhiAccumulatedMedicalRecordList where medicalInstitutionCode equals to UPDATED_MEDICAL_INSTITUTION_CODE
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("medicalInstitutionCode.in=" + UPDATED_MEDICAL_INSTITUTION_CODE);
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByMedicalInstitutionCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);

        // Get all the nhiAccumulatedMedicalRecordList where medicalInstitutionCode is not null
        defaultNhiAccumulatedMedicalRecordShouldBeFound("medicalInstitutionCode.specified=true");

        // Get all the nhiAccumulatedMedicalRecordList where medicalInstitutionCode is null
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("medicalInstitutionCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiAccumulatedMedicalRecordsByPatientIsEqualToSomething() throws Exception {
        // Initialize the database
        Patient patient = PatientResourceIntTest.createEntity(em);
        em.persist(patient);
        em.flush();
        nhiAccumulatedMedicalRecord.setPatient(patient);
        nhiAccumulatedMedicalRecordRepository.saveAndFlush(nhiAccumulatedMedicalRecord);
        Long patientId = patient.getId();

        // Get all the nhiAccumulatedMedicalRecordList where patient equals to patientId
        defaultNhiAccumulatedMedicalRecordShouldBeFound("patientId.equals=" + patientId);

        // Get all the nhiAccumulatedMedicalRecordList where patient equals to patientId + 1
        defaultNhiAccumulatedMedicalRecordShouldNotBeFound("patientId.equals=" + (patientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNhiAccumulatedMedicalRecordShouldBeFound(String filter) throws Exception {
        restNhiAccumulatedMedicalRecordMockMvc.perform(get("/api/nhi-accumulated-medical-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiAccumulatedMedicalRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].medicalCategory").value(hasItem(DEFAULT_MEDICAL_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].newbornMedicalTreatmentNote").value(hasItem(DEFAULT_NEWBORN_MEDICAL_TREATMENT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].cardFillingNote").value(hasItem(DEFAULT_CARD_FILLING_NOTE.toString())))
            .andExpect(jsonPath("$.[*].seqNumber").value(hasItem(DEFAULT_SEQ_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].medicalInstitutionCode").value(hasItem(DEFAULT_MEDICAL_INSTITUTION_CODE.toString())));

        // Check, that the count call also returns 1
        restNhiAccumulatedMedicalRecordMockMvc.perform(get("/api/nhi-accumulated-medical-records/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNhiAccumulatedMedicalRecordShouldNotBeFound(String filter) throws Exception {
        restNhiAccumulatedMedicalRecordMockMvc.perform(get("/api/nhi-accumulated-medical-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNhiAccumulatedMedicalRecordMockMvc.perform(get("/api/nhi-accumulated-medical-records/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNhiAccumulatedMedicalRecord() throws Exception {
        // Get the nhiAccumulatedMedicalRecord
        restNhiAccumulatedMedicalRecordMockMvc.perform(get("/api/nhi-accumulated-medical-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhiAccumulatedMedicalRecord() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordService.save(nhiAccumulatedMedicalRecord);

        int databaseSizeBeforeUpdate = nhiAccumulatedMedicalRecordRepository.findAll().size();

        // Update the nhiAccumulatedMedicalRecord
        NhiAccumulatedMedicalRecord updatedNhiAccumulatedMedicalRecord = nhiAccumulatedMedicalRecordRepository.findById(nhiAccumulatedMedicalRecord.getId()).get();
        // Disconnect from session so that the updates on updatedNhiAccumulatedMedicalRecord are not directly saved in db
        em.detach(updatedNhiAccumulatedMedicalRecord);
        updatedNhiAccumulatedMedicalRecord
            .medicalCategory(UPDATED_MEDICAL_CATEGORY)
            .newbornMedicalTreatmentNote(UPDATED_NEWBORN_MEDICAL_TREATMENT_NOTE)
            .date(UPDATED_DATE)
            .cardFillingNote(UPDATED_CARD_FILLING_NOTE)
            .seqNumber(UPDATED_SEQ_NUMBER)
            .medicalInstitutionCode(UPDATED_MEDICAL_INSTITUTION_CODE);

        restNhiAccumulatedMedicalRecordMockMvc.perform(put("/api/nhi-accumulated-medical-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNhiAccumulatedMedicalRecord)))
            .andExpect(status().isOk());

        // Validate the NhiAccumulatedMedicalRecord in the database
        List<NhiAccumulatedMedicalRecord> nhiAccumulatedMedicalRecordList = nhiAccumulatedMedicalRecordRepository.findAll();
        assertThat(nhiAccumulatedMedicalRecordList).hasSize(databaseSizeBeforeUpdate);
        NhiAccumulatedMedicalRecord testNhiAccumulatedMedicalRecord = nhiAccumulatedMedicalRecordList.get(nhiAccumulatedMedicalRecordList.size() - 1);
        assertThat(testNhiAccumulatedMedicalRecord.getMedicalCategory()).isEqualTo(UPDATED_MEDICAL_CATEGORY);
        assertThat(testNhiAccumulatedMedicalRecord.getNewbornMedicalTreatmentNote()).isEqualTo(UPDATED_NEWBORN_MEDICAL_TREATMENT_NOTE);
        assertThat(testNhiAccumulatedMedicalRecord.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testNhiAccumulatedMedicalRecord.getCardFillingNote()).isEqualTo(UPDATED_CARD_FILLING_NOTE);
        assertThat(testNhiAccumulatedMedicalRecord.getSeqNumber()).isEqualTo(UPDATED_SEQ_NUMBER);
        assertThat(testNhiAccumulatedMedicalRecord.getMedicalInstitutionCode()).isEqualTo(UPDATED_MEDICAL_INSTITUTION_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingNhiAccumulatedMedicalRecord() throws Exception {
        int databaseSizeBeforeUpdate = nhiAccumulatedMedicalRecordRepository.findAll().size();

        // Create the NhiAccumulatedMedicalRecord

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhiAccumulatedMedicalRecordMockMvc.perform(put("/api/nhi-accumulated-medical-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiAccumulatedMedicalRecord)))
            .andExpect(status().isBadRequest());

        // Validate the NhiAccumulatedMedicalRecord in the database
        List<NhiAccumulatedMedicalRecord> nhiAccumulatedMedicalRecordList = nhiAccumulatedMedicalRecordRepository.findAll();
        assertThat(nhiAccumulatedMedicalRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhiAccumulatedMedicalRecord() throws Exception {
        // Initialize the database
        nhiAccumulatedMedicalRecordService.save(nhiAccumulatedMedicalRecord);

        int databaseSizeBeforeDelete = nhiAccumulatedMedicalRecordRepository.findAll().size();

        // Get the nhiAccumulatedMedicalRecord
        restNhiAccumulatedMedicalRecordMockMvc.perform(delete("/api/nhi-accumulated-medical-records/{id}", nhiAccumulatedMedicalRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhiAccumulatedMedicalRecord> nhiAccumulatedMedicalRecordList = nhiAccumulatedMedicalRecordRepository.findAll();
        assertThat(nhiAccumulatedMedicalRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        Patient p = new Patient();
        p.setId(1L);
        TestUtil.equalsVerifier(NhiAccumulatedMedicalRecord.class);
        NhiAccumulatedMedicalRecord nhiAccumulatedMedicalRecord1 = new NhiAccumulatedMedicalRecord();
        nhiAccumulatedMedicalRecord1.setId(1L);
        NhiAccumulatedMedicalRecord nhiAccumulatedMedicalRecord2 = new NhiAccumulatedMedicalRecord();
        nhiAccumulatedMedicalRecord1.setPatient(p);
        nhiAccumulatedMedicalRecord2.setId(nhiAccumulatedMedicalRecord1.getId());
        nhiAccumulatedMedicalRecord2.setPatient(p);
        assertThat(nhiAccumulatedMedicalRecord1).isEqualTo(nhiAccumulatedMedicalRecord2);
        nhiAccumulatedMedicalRecord2.setMedicalCategory("string");
        assertThat(nhiAccumulatedMedicalRecord1).isNotEqualTo(nhiAccumulatedMedicalRecord2);
        nhiAccumulatedMedicalRecord1.setId(null);
        assertThat(nhiAccumulatedMedicalRecord1).isNotEqualTo(nhiAccumulatedMedicalRecord2);
    }
}
