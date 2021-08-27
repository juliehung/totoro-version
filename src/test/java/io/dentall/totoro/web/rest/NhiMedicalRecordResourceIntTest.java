package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.domain.NhiExtendPatient;
import io.dentall.totoro.domain.NhiMedicalRecord;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.NhiMedicalRecordQueryService;
import io.dentall.totoro.service.NhiMedicalRecordService;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NhiMedicalRecordResource REST controller.
 *
 * @see NhiMedicalRecordResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiMedicalRecordResourceIntTest {

    private static final String DEFAULT_DATE = "AAAAAAAAAA";
    private static final String UPDATED_DATE = "BBBBBBBBBB";

    private static final String DEFAULT_NHI_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_NHI_CATEGORY = "BBBBBBBBBB";

    private static final String DEFAULT_NHI_CODE = "AAAAAAAAAA";
    private static final String UPDATED_NHI_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PART = "AAAAAAAAAA";
    private static final String UPDATED_PART = "BBBBBBBBBB";

    private static final String DEFAULT_USAGE = "AAAAAAAAAA";
    private static final String UPDATED_USAGE = "BBBBBBBBBB";

    private static final String DEFAULT_TOTAL = "AAAAAAAAAA";
    private static final String UPDATED_TOTAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_DAYS = "AAAAAAAAAA";
    private static final String UPDATED_DAYS = "BBBBBBBBBB";

    @Autowired
    private NhiMedicalRecordRepository nhiMedicalRecordRepository;

    @Autowired
    private NhiExtendPatientRepository nhiExtendPatientRepository;

    @Autowired
    private NhiMedicalRecordService nhiMedicalRecordService;

    @Autowired
    private NhiMedicalRecordQueryService nhiMedicalRecordQueryService;

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
    private PatientRepository patientRepository;

    private MockMvc restNhiMedicalRecordMockMvc;

    private NhiMedicalRecord nhiMedicalRecord;

    @Autowired
    private NhiTxRepository nhiTxRepository;

    @Autowired
    private NhiMedicineRepository nhiMedicineRepository;

    @Autowired
    private NhiExtendDisposalRepository nhiExtendDisposalRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhiMedicalRecordResource nhiMedicalRecordResource = new NhiMedicalRecordResource(nhiMedicalRecordService, nhiMedicalRecordQueryService, nhiTxRepository, nhiMedicineRepository, nhiExtendDisposalRepository);
        this.restNhiMedicalRecordMockMvc = MockMvcBuilders.standaloneSetup(nhiMedicalRecordResource)
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
    public static NhiMedicalRecord createEntity(EntityManager em) {
        NhiMedicalRecord nhiMedicalRecord = new NhiMedicalRecord()
            .date(DEFAULT_DATE)
            .nhiCategory(DEFAULT_NHI_CATEGORY)
            .nhiCode(DEFAULT_NHI_CODE)
            .part(DEFAULT_PART)
            .usage(DEFAULT_USAGE)
            .total(DEFAULT_TOTAL)
            .note(DEFAULT_NOTE)
            .days(DEFAULT_DAYS);
        return nhiMedicalRecord;
    }

    @Before
    public void initTest() {
        nhiMedicalRecord = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhiMedicalRecord() throws Exception {
        int databaseSizeBeforeCreate = nhiMedicalRecordRepository.findAll().size();

        Patient p = new Patient();
        p.setId(1L);
        p.setPhone("1");
        p.setName("1");
        patientRepository.save(p);

        NhiExtendPatient nep = new NhiExtendPatient();
        nep.setId(1L);
        nep.setPatient(p);
        nhiExtendPatientRepository.save(nep);


        // Create the NhiMedicalRecord
        restNhiMedicalRecordMockMvc.perform(post("/api/nhi-medical-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(
                nhiMedicalRecord
                    .nhiExtendPatient(nep)
                    .date("modify.for.not.duplicate"))))
            .andExpect(status().isCreated());

        // Validate the NhiMedicalRecord in the database
        List<NhiMedicalRecord> nhiMedicalRecordList = nhiMedicalRecordRepository.findAll();
        assertThat(nhiMedicalRecordList).hasSize(databaseSizeBeforeCreate + 1);
        NhiMedicalRecord testNhiMedicalRecord = nhiMedicalRecordList.get(nhiMedicalRecordList.size() - 1);
        assertThat(testNhiMedicalRecord.getDate()).isEqualTo("modify.for.not.duplicate");
        assertThat(testNhiMedicalRecord.getNhiCategory()).isEqualTo(DEFAULT_NHI_CATEGORY);
        assertThat(testNhiMedicalRecord.getNhiCode()).isEqualTo(DEFAULT_NHI_CODE);
        assertThat(testNhiMedicalRecord.getPart()).isEqualTo(DEFAULT_PART);
        assertThat(testNhiMedicalRecord.getUsage()).isEqualTo(DEFAULT_USAGE);
        assertThat(testNhiMedicalRecord.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testNhiMedicalRecord.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testNhiMedicalRecord.getDays()).isEqualTo(DEFAULT_DAYS);
    }

    @Test
    @Transactional
    public void createNhiMedicalRecordWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhiMedicalRecordRepository.findAll().size();

        // Create the NhiMedicalRecord with an existing ID
        nhiMedicalRecord.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhiMedicalRecordMockMvc.perform(post("/api/nhi-medical-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiMedicalRecord)))
            .andExpect(status().isBadRequest());

        // Validate the NhiMedicalRecord in the database
        List<NhiMedicalRecord> nhiMedicalRecordList = nhiMedicalRecordRepository.findAll();
        assertThat(nhiMedicalRecordList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecords() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList
        restNhiMedicalRecordMockMvc.perform(get("/api/nhi-medical-records?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiMedicalRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].nhiCategory").value(hasItem(DEFAULT_NHI_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].nhiCode").value(hasItem(DEFAULT_NHI_CODE.toString())))
            .andExpect(jsonPath("$.[*].part").value(hasItem(DEFAULT_PART.toString())))
            .andExpect(jsonPath("$.[*].usage").value(hasItem(DEFAULT_USAGE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS.toString())));
    }

    @Test
    @Transactional
    public void getNhiMedicalRecord() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get the nhiMedicalRecord
        restNhiMedicalRecordMockMvc.perform(get("/api/nhi-medical-records/{id}", nhiMedicalRecord.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhiMedicalRecord.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.nhiCategory").value(DEFAULT_NHI_CATEGORY.toString()))
            .andExpect(jsonPath("$.nhiCode").value(DEFAULT_NHI_CODE.toString()))
            .andExpect(jsonPath("$.part").value(DEFAULT_PART.toString()))
            .andExpect(jsonPath("$.usage").value(DEFAULT_USAGE.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()))
            .andExpect(jsonPath("$.days").value(DEFAULT_DAYS.toString()));
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where date equals to DEFAULT_DATE
        defaultNhiMedicalRecordShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the nhiMedicalRecordList where date equals to UPDATED_DATE
        defaultNhiMedicalRecordShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where date in DEFAULT_DATE or UPDATED_DATE
        defaultNhiMedicalRecordShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the nhiMedicalRecordList where date equals to UPDATED_DATE
        defaultNhiMedicalRecordShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where date is not null
        defaultNhiMedicalRecordShouldBeFound("date.specified=true");

        // Get all the nhiMedicalRecordList where date is null
        defaultNhiMedicalRecordShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByNhiCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where nhiCategory equals to DEFAULT_NHI_CATEGORY
        defaultNhiMedicalRecordShouldBeFound("nhiCategory.equals=" + DEFAULT_NHI_CATEGORY);

        // Get all the nhiMedicalRecordList where nhiCategory equals to UPDATED_NHI_CATEGORY
        defaultNhiMedicalRecordShouldNotBeFound("nhiCategory.equals=" + UPDATED_NHI_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByNhiCategoryIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where nhiCategory in DEFAULT_NHI_CATEGORY or UPDATED_NHI_CATEGORY
        defaultNhiMedicalRecordShouldBeFound("nhiCategory.in=" + DEFAULT_NHI_CATEGORY + "," + UPDATED_NHI_CATEGORY);

        // Get all the nhiMedicalRecordList where nhiCategory equals to UPDATED_NHI_CATEGORY
        defaultNhiMedicalRecordShouldNotBeFound("nhiCategory.in=" + UPDATED_NHI_CATEGORY);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByNhiCategoryIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where nhiCategory is not null
        defaultNhiMedicalRecordShouldBeFound("nhiCategory.specified=true");

        // Get all the nhiMedicalRecordList where nhiCategory is null
        defaultNhiMedicalRecordShouldNotBeFound("nhiCategory.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByNhiCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where nhiCode equals to DEFAULT_NHI_CODE
        defaultNhiMedicalRecordShouldBeFound("nhiCode.equals=" + DEFAULT_NHI_CODE);

        // Get all the nhiMedicalRecordList where nhiCode equals to UPDATED_NHI_CODE
        defaultNhiMedicalRecordShouldNotBeFound("nhiCode.equals=" + UPDATED_NHI_CODE);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByNhiCodeIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where nhiCode in DEFAULT_NHI_CODE or UPDATED_NHI_CODE
        defaultNhiMedicalRecordShouldBeFound("nhiCode.in=" + DEFAULT_NHI_CODE + "," + UPDATED_NHI_CODE);

        // Get all the nhiMedicalRecordList where nhiCode equals to UPDATED_NHI_CODE
        defaultNhiMedicalRecordShouldNotBeFound("nhiCode.in=" + UPDATED_NHI_CODE);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByNhiCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where nhiCode is not null
        defaultNhiMedicalRecordShouldBeFound("nhiCode.specified=true");

        // Get all the nhiMedicalRecordList where nhiCode is null
        defaultNhiMedicalRecordShouldNotBeFound("nhiCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByPartIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where part equals to DEFAULT_PART
        defaultNhiMedicalRecordShouldBeFound("part.equals=" + DEFAULT_PART);

        // Get all the nhiMedicalRecordList where part equals to UPDATED_PART
        defaultNhiMedicalRecordShouldNotBeFound("part.equals=" + UPDATED_PART);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByPartIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where part in DEFAULT_PART or UPDATED_PART
        defaultNhiMedicalRecordShouldBeFound("part.in=" + DEFAULT_PART + "," + UPDATED_PART);

        // Get all the nhiMedicalRecordList where part equals to UPDATED_PART
        defaultNhiMedicalRecordShouldNotBeFound("part.in=" + UPDATED_PART);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByPartIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where part is not null
        defaultNhiMedicalRecordShouldBeFound("part.specified=true");

        // Get all the nhiMedicalRecordList where part is null
        defaultNhiMedicalRecordShouldNotBeFound("part.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByUsageIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where usage equals to DEFAULT_USAGE
        defaultNhiMedicalRecordShouldBeFound("usage.equals=" + DEFAULT_USAGE);

        // Get all the nhiMedicalRecordList where usage equals to UPDATED_USAGE
        defaultNhiMedicalRecordShouldNotBeFound("usage.equals=" + UPDATED_USAGE);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByUsageIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where usage in DEFAULT_USAGE or UPDATED_USAGE
        defaultNhiMedicalRecordShouldBeFound("usage.in=" + DEFAULT_USAGE + "," + UPDATED_USAGE);

        // Get all the nhiMedicalRecordList where usage equals to UPDATED_USAGE
        defaultNhiMedicalRecordShouldNotBeFound("usage.in=" + UPDATED_USAGE);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByUsageIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where usage is not null
        defaultNhiMedicalRecordShouldBeFound("usage.specified=true");

        // Get all the nhiMedicalRecordList where usage is null
        defaultNhiMedicalRecordShouldNotBeFound("usage.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where total equals to DEFAULT_TOTAL
        defaultNhiMedicalRecordShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the nhiMedicalRecordList where total equals to UPDATED_TOTAL
        defaultNhiMedicalRecordShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultNhiMedicalRecordShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the nhiMedicalRecordList where total equals to UPDATED_TOTAL
        defaultNhiMedicalRecordShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where total is not null
        defaultNhiMedicalRecordShouldBeFound("total.specified=true");

        // Get all the nhiMedicalRecordList where total is null
        defaultNhiMedicalRecordShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where note equals to DEFAULT_NOTE
        defaultNhiMedicalRecordShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the nhiMedicalRecordList where note equals to UPDATED_NOTE
        defaultNhiMedicalRecordShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultNhiMedicalRecordShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the nhiMedicalRecordList where note equals to UPDATED_NOTE
        defaultNhiMedicalRecordShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where note is not null
        defaultNhiMedicalRecordShouldBeFound("note.specified=true");

        // Get all the nhiMedicalRecordList where note is null
        defaultNhiMedicalRecordShouldNotBeFound("note.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where days equals to DEFAULT_DAYS
        defaultNhiMedicalRecordShouldBeFound("days.equals=" + DEFAULT_DAYS);

        // Get all the nhiMedicalRecordList where days equals to UPDATED_DAYS
        defaultNhiMedicalRecordShouldNotBeFound("days.equals=" + UPDATED_DAYS);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByDaysIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where days in DEFAULT_DAYS or UPDATED_DAYS
        defaultNhiMedicalRecordShouldBeFound("days.in=" + DEFAULT_DAYS + "," + UPDATED_DAYS);

        // Get all the nhiMedicalRecordList where days equals to UPDATED_DAYS
        defaultNhiMedicalRecordShouldNotBeFound("days.in=" + UPDATED_DAYS);
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);

        // Get all the nhiMedicalRecordList where days is not null
        defaultNhiMedicalRecordShouldBeFound("days.specified=true");

        // Get all the nhiMedicalRecordList where days is null
        defaultNhiMedicalRecordShouldNotBeFound("days.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMedicalRecordsByNhiExtendPatientIsEqualToSomething() throws Exception {
        // Initialize the database
        NhiExtendPatient nhiExtendPatient = NhiExtendPatientResourceIntTest.createEntity(em);
        nhiExtendPatient.setPatient(patientRepository.save(PatientResourceIntTest.createEntity(em)));
        em.persist(nhiExtendPatient);
        em.flush();
        nhiMedicalRecord.setNhiExtendPatient(nhiExtendPatient);
        nhiMedicalRecordRepository.saveAndFlush(nhiMedicalRecord);
        Long nhiExtendPatientId = nhiExtendPatient.getId();

        // Get all the nhiMedicalRecordList where nhiExtendPatient equals to nhiExtendPatientId
        defaultNhiMedicalRecordShouldBeFound("nhiExtendPatientId.equals=" + nhiExtendPatientId);

        // Get all the nhiMedicalRecordList where nhiExtendPatient equals to nhiExtendPatientId + 1
        defaultNhiMedicalRecordShouldNotBeFound("nhiExtendPatientId.equals=" + (nhiExtendPatientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNhiMedicalRecordShouldBeFound(String filter) throws Exception {
        restNhiMedicalRecordMockMvc.perform(get("/api/nhi-medical-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiMedicalRecord.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].nhiCategory").value(hasItem(DEFAULT_NHI_CATEGORY.toString())))
            .andExpect(jsonPath("$.[*].nhiCode").value(hasItem(DEFAULT_NHI_CODE.toString())))
            .andExpect(jsonPath("$.[*].part").value(hasItem(DEFAULT_PART.toString())))
            .andExpect(jsonPath("$.[*].usage").value(hasItem(DEFAULT_USAGE.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())))
            .andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS.toString())));

        // Check, that the count call also returns 1
        restNhiMedicalRecordMockMvc.perform(get("/api/nhi-medical-records/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNhiMedicalRecordShouldNotBeFound(String filter) throws Exception {
        restNhiMedicalRecordMockMvc.perform(get("/api/nhi-medical-records?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNhiMedicalRecordMockMvc.perform(get("/api/nhi-medical-records/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNhiMedicalRecord() throws Exception {
        // Get the nhiMedicalRecord
        restNhiMedicalRecordMockMvc.perform(get("/api/nhi-medical-records/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhiMedicalRecord() throws Exception {
        // Initialize the database
        nhiMedicalRecordService.save(nhiMedicalRecord);

        int databaseSizeBeforeUpdate = nhiMedicalRecordRepository.findAll().size();

        // Update the nhiMedicalRecord
        NhiMedicalRecord updatedNhiMedicalRecord = nhiMedicalRecordRepository.findById(nhiMedicalRecord.getId()).get();
        // Disconnect from session so that the updates on updatedNhiMedicalRecord are not directly saved in db
        em.detach(updatedNhiMedicalRecord);
        updatedNhiMedicalRecord
            .date(UPDATED_DATE)
            .nhiCategory(UPDATED_NHI_CATEGORY)
            .nhiCode(UPDATED_NHI_CODE)
            .part(UPDATED_PART)
            .usage(UPDATED_USAGE)
            .total(UPDATED_TOTAL)
            .note(UPDATED_NOTE)
            .days(UPDATED_DAYS);

        restNhiMedicalRecordMockMvc.perform(put("/api/nhi-medical-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNhiMedicalRecord)))
            .andExpect(status().isOk());

        // Validate the NhiMedicalRecord in the database
        List<NhiMedicalRecord> nhiMedicalRecordList = nhiMedicalRecordRepository.findAll();
        assertThat(nhiMedicalRecordList).hasSize(databaseSizeBeforeUpdate);
        NhiMedicalRecord testNhiMedicalRecord = nhiMedicalRecordList.get(nhiMedicalRecordList.size() - 1);
        assertThat(testNhiMedicalRecord.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testNhiMedicalRecord.getNhiCategory()).isEqualTo(UPDATED_NHI_CATEGORY);
        assertThat(testNhiMedicalRecord.getNhiCode()).isEqualTo(UPDATED_NHI_CODE);
        assertThat(testNhiMedicalRecord.getPart()).isEqualTo(UPDATED_PART);
        assertThat(testNhiMedicalRecord.getUsage()).isEqualTo(UPDATED_USAGE);
        assertThat(testNhiMedicalRecord.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testNhiMedicalRecord.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testNhiMedicalRecord.getDays()).isEqualTo(UPDATED_DAYS);
    }

    @Test
    @Transactional
    public void updateNonExistingNhiMedicalRecord() throws Exception {
        int databaseSizeBeforeUpdate = nhiMedicalRecordRepository.findAll().size();

        // Create the NhiMedicalRecord

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhiMedicalRecordMockMvc.perform(put("/api/nhi-medical-records")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiMedicalRecord)))
            .andExpect(status().isBadRequest());

        // Validate the NhiMedicalRecord in the database
        List<NhiMedicalRecord> nhiMedicalRecordList = nhiMedicalRecordRepository.findAll();
        assertThat(nhiMedicalRecordList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhiMedicalRecord() throws Exception {
        // Initialize the database
        nhiMedicalRecordService.save(nhiMedicalRecord);

        int databaseSizeBeforeDelete = nhiMedicalRecordRepository.findAll().size();

        // Get the nhiMedicalRecord
        restNhiMedicalRecordMockMvc.perform(delete("/api/nhi-medical-records/{id}", nhiMedicalRecord.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhiMedicalRecord> nhiMedicalRecordList = nhiMedicalRecordRepository.findAll();
        assertThat(nhiMedicalRecordList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
