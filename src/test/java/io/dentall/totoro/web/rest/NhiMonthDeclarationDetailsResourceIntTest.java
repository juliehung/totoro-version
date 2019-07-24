package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.NhiMonthDeclarationDetails;
import io.dentall.totoro.domain.NhiMonthDeclaration;
import io.dentall.totoro.repository.NhiMonthDeclarationDetailsRepository;
import io.dentall.totoro.service.NhiMonthDeclarationDetailsService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.dto.NhiMonthDeclarationDetailsCriteria;
import io.dentall.totoro.service.NhiMonthDeclarationDetailsQueryService;

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

import io.dentall.totoro.domain.enumeration.NhiMonthDeclarationType;
/**
 * Test class for the NhiMonthDeclarationDetailsResource REST controller.
 *
 * @see NhiMonthDeclarationDetailsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiMonthDeclarationDetailsResourceIntTest {

    private static final NhiMonthDeclarationType DEFAULT_TYPE = NhiMonthDeclarationType.SUBMISSION;
    private static final NhiMonthDeclarationType UPDATED_TYPE = NhiMonthDeclarationType.SUPPLEMENT;

    private static final String DEFAULT_WAY = "AAAAAAAAAA";
    private static final String UPDATED_WAY = "BBBBBBBBBB";

    private static final Integer DEFAULT_CASE_TOTAL = 1;
    private static final Integer UPDATED_CASE_TOTAL = 2;

    private static final Integer DEFAULT_POINT_TOTAL = 1;
    private static final Integer UPDATED_POINT_TOTAL = 2;

    private static final Integer DEFAULT_OUT_PATIENT_POINT = 1;
    private static final Integer UPDATED_OUT_PATIENT_POINT = 2;

    private static final Integer DEFAULT_PREVENTIVE_CASE_TOTAL = 1;
    private static final Integer UPDATED_PREVENTIVE_CASE_TOTAL = 2;

    private static final Integer DEFAULT_PREVENTIVE_POINT_TOTAL = 1;
    private static final Integer UPDATED_PREVENTIVE_POINT_TOTAL = 2;

    private static final Integer DEFAULT_GENERAL_CASE_TOTAL = 1;
    private static final Integer UPDATED_GENERAL_CASE_TOTAL = 2;

    private static final Integer DEFAULT_GENERAL_POINT_TOTAL = 1;
    private static final Integer UPDATED_GENERAL_POINT_TOTAL = 2;

    private static final Integer DEFAULT_PROFESSIONAL_CASE_TOTAL = 1;
    private static final Integer UPDATED_PROFESSIONAL_CASE_TOTAL = 2;

    private static final Integer DEFAULT_PROFESSIONAL_POINT_TOTAL = 1;
    private static final Integer UPDATED_PROFESSIONAL_POINT_TOTAL = 2;

    private static final Integer DEFAULT_PARTIAL_CASE_TOTAL = 1;
    private static final Integer UPDATED_PARTIAL_CASE_TOTAL = 2;

    private static final Integer DEFAULT_PARTIAL_POINT_TOTAL = 1;
    private static final Integer UPDATED_PARTIAL_POINT_TOTAL = 2;

    private static final String DEFAULT_FILE = "AAAAAAAAAA";
    private static final String UPDATED_FILE = "BBBBBBBBBB";

    private static final Instant DEFAULT_UPLOAD_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPLOAD_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOCAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_LOCAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_NHI_ID = "AAAAAAAAAA";
    private static final String UPDATED_NHI_ID = "BBBBBBBBBB";

    @Autowired
    private NhiMonthDeclarationDetailsRepository nhiMonthDeclarationDetailsRepository;

    @Autowired
    private NhiMonthDeclarationDetailsService nhiMonthDeclarationDetailsService;

    @Autowired
    private NhiMonthDeclarationDetailsQueryService nhiMonthDeclarationDetailsQueryService;

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

    private MockMvc restNhiMonthDeclarationDetailsMockMvc;

    private NhiMonthDeclarationDetails nhiMonthDeclarationDetails;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhiMonthDeclarationDetailsResource nhiMonthDeclarationDetailsResource = new NhiMonthDeclarationDetailsResource(nhiMonthDeclarationDetailsService, nhiMonthDeclarationDetailsQueryService);
        this.restNhiMonthDeclarationDetailsMockMvc = MockMvcBuilders.standaloneSetup(nhiMonthDeclarationDetailsResource)
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
    public static NhiMonthDeclarationDetails createEntity(EntityManager em) {
        NhiMonthDeclarationDetails nhiMonthDeclarationDetails = new NhiMonthDeclarationDetails()
            .type(DEFAULT_TYPE)
            .way(DEFAULT_WAY)
            .caseTotal(DEFAULT_CASE_TOTAL)
            .pointTotal(DEFAULT_POINT_TOTAL)
            .outPatientPoint(DEFAULT_OUT_PATIENT_POINT)
            .preventiveCaseTotal(DEFAULT_PREVENTIVE_CASE_TOTAL)
            .preventivePointTotal(DEFAULT_PREVENTIVE_POINT_TOTAL)
            .generalCaseTotal(DEFAULT_GENERAL_CASE_TOTAL)
            .generalPointTotal(DEFAULT_GENERAL_POINT_TOTAL)
            .professionalCaseTotal(DEFAULT_PROFESSIONAL_CASE_TOTAL)
            .professionalPointTotal(DEFAULT_PROFESSIONAL_POINT_TOTAL)
            .partialCaseTotal(DEFAULT_PARTIAL_CASE_TOTAL)
            .partialPointTotal(DEFAULT_PARTIAL_POINT_TOTAL)
            .file(DEFAULT_FILE)
            .uploadTime(DEFAULT_UPLOAD_TIME)
            .localId(DEFAULT_LOCAL_ID)
            .nhiId(DEFAULT_NHI_ID);
        return nhiMonthDeclarationDetails;
    }

    @Before
    public void initTest() {
        nhiMonthDeclarationDetails = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhiMonthDeclarationDetails() throws Exception {
        int databaseSizeBeforeCreate = nhiMonthDeclarationDetailsRepository.findAll().size();

        // Create the NhiMonthDeclarationDetails
        restNhiMonthDeclarationDetailsMockMvc.perform(post("/api/nhi-month-declaration-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiMonthDeclarationDetails)))
            .andExpect(status().isCreated());

        // Validate the NhiMonthDeclarationDetails in the database
        List<NhiMonthDeclarationDetails> nhiMonthDeclarationDetailsList = nhiMonthDeclarationDetailsRepository.findAll();
        assertThat(nhiMonthDeclarationDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        NhiMonthDeclarationDetails testNhiMonthDeclarationDetails = nhiMonthDeclarationDetailsList.get(nhiMonthDeclarationDetailsList.size() - 1);
        assertThat(testNhiMonthDeclarationDetails.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testNhiMonthDeclarationDetails.getWay()).isEqualTo(DEFAULT_WAY);
        assertThat(testNhiMonthDeclarationDetails.getCaseTotal()).isEqualTo(DEFAULT_CASE_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getPointTotal()).isEqualTo(DEFAULT_POINT_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getOutPatientPoint()).isEqualTo(DEFAULT_OUT_PATIENT_POINT);
        assertThat(testNhiMonthDeclarationDetails.getPreventiveCaseTotal()).isEqualTo(DEFAULT_PREVENTIVE_CASE_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getPreventivePointTotal()).isEqualTo(DEFAULT_PREVENTIVE_POINT_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getGeneralCaseTotal()).isEqualTo(DEFAULT_GENERAL_CASE_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getGeneralPointTotal()).isEqualTo(DEFAULT_GENERAL_POINT_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getProfessionalCaseTotal()).isEqualTo(DEFAULT_PROFESSIONAL_CASE_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getProfessionalPointTotal()).isEqualTo(DEFAULT_PROFESSIONAL_POINT_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getPartialCaseTotal()).isEqualTo(DEFAULT_PARTIAL_CASE_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getPartialPointTotal()).isEqualTo(DEFAULT_PARTIAL_POINT_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getFile()).isEqualTo(DEFAULT_FILE);
        assertThat(testNhiMonthDeclarationDetails.getUploadTime()).isEqualTo(DEFAULT_UPLOAD_TIME);
        assertThat(testNhiMonthDeclarationDetails.getLocalId()).isEqualTo(DEFAULT_LOCAL_ID);
        assertThat(testNhiMonthDeclarationDetails.getNhiId()).isEqualTo(DEFAULT_NHI_ID);
    }

    @Test
    @Transactional
    public void createNhiMonthDeclarationDetailsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhiMonthDeclarationDetailsRepository.findAll().size();

        // Create the NhiMonthDeclarationDetails with an existing ID
        nhiMonthDeclarationDetails.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhiMonthDeclarationDetailsMockMvc.perform(post("/api/nhi-month-declaration-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiMonthDeclarationDetails)))
            .andExpect(status().isBadRequest());

        // Validate the NhiMonthDeclarationDetails in the database
        List<NhiMonthDeclarationDetails> nhiMonthDeclarationDetailsList = nhiMonthDeclarationDetailsRepository.findAll();
        assertThat(nhiMonthDeclarationDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetails() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList
        restNhiMonthDeclarationDetailsMockMvc.perform(get("/api/nhi-month-declaration-details?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiMonthDeclarationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].way").value(hasItem(DEFAULT_WAY.toString())))
            .andExpect(jsonPath("$.[*].caseTotal").value(hasItem(DEFAULT_CASE_TOTAL)))
            .andExpect(jsonPath("$.[*].pointTotal").value(hasItem(DEFAULT_POINT_TOTAL)))
            .andExpect(jsonPath("$.[*].outPatientPoint").value(hasItem(DEFAULT_OUT_PATIENT_POINT)))
            .andExpect(jsonPath("$.[*].preventiveCaseTotal").value(hasItem(DEFAULT_PREVENTIVE_CASE_TOTAL)))
            .andExpect(jsonPath("$.[*].preventivePointTotal").value(hasItem(DEFAULT_PREVENTIVE_POINT_TOTAL)))
            .andExpect(jsonPath("$.[*].generalCaseTotal").value(hasItem(DEFAULT_GENERAL_CASE_TOTAL)))
            .andExpect(jsonPath("$.[*].generalPointTotal").value(hasItem(DEFAULT_GENERAL_POINT_TOTAL)))
            .andExpect(jsonPath("$.[*].professionalCaseTotal").value(hasItem(DEFAULT_PROFESSIONAL_CASE_TOTAL)))
            .andExpect(jsonPath("$.[*].professionalPointTotal").value(hasItem(DEFAULT_PROFESSIONAL_POINT_TOTAL)))
            .andExpect(jsonPath("$.[*].partialCaseTotal").value(hasItem(DEFAULT_PARTIAL_CASE_TOTAL)))
            .andExpect(jsonPath("$.[*].partialPointTotal").value(hasItem(DEFAULT_PARTIAL_POINT_TOTAL)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(DEFAULT_FILE.toString())))
            .andExpect(jsonPath("$.[*].uploadTime").value(hasItem(DEFAULT_UPLOAD_TIME.toString())))
            .andExpect(jsonPath("$.[*].localId").value(hasItem(DEFAULT_LOCAL_ID.toString())))
            .andExpect(jsonPath("$.[*].nhiId").value(hasItem(DEFAULT_NHI_ID.toString())));
    }
    
    @Test
    @Transactional
    public void getNhiMonthDeclarationDetails() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get the nhiMonthDeclarationDetails
        restNhiMonthDeclarationDetailsMockMvc.perform(get("/api/nhi-month-declaration-details/{id}", nhiMonthDeclarationDetails.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhiMonthDeclarationDetails.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.way").value(DEFAULT_WAY.toString()))
            .andExpect(jsonPath("$.caseTotal").value(DEFAULT_CASE_TOTAL))
            .andExpect(jsonPath("$.pointTotal").value(DEFAULT_POINT_TOTAL))
            .andExpect(jsonPath("$.outPatientPoint").value(DEFAULT_OUT_PATIENT_POINT))
            .andExpect(jsonPath("$.preventiveCaseTotal").value(DEFAULT_PREVENTIVE_CASE_TOTAL))
            .andExpect(jsonPath("$.preventivePointTotal").value(DEFAULT_PREVENTIVE_POINT_TOTAL))
            .andExpect(jsonPath("$.generalCaseTotal").value(DEFAULT_GENERAL_CASE_TOTAL))
            .andExpect(jsonPath("$.generalPointTotal").value(DEFAULT_GENERAL_POINT_TOTAL))
            .andExpect(jsonPath("$.professionalCaseTotal").value(DEFAULT_PROFESSIONAL_CASE_TOTAL))
            .andExpect(jsonPath("$.professionalPointTotal").value(DEFAULT_PROFESSIONAL_POINT_TOTAL))
            .andExpect(jsonPath("$.partialCaseTotal").value(DEFAULT_PARTIAL_CASE_TOTAL))
            .andExpect(jsonPath("$.partialPointTotal").value(DEFAULT_PARTIAL_POINT_TOTAL))
            .andExpect(jsonPath("$.file").value(DEFAULT_FILE.toString()))
            .andExpect(jsonPath("$.uploadTime").value(DEFAULT_UPLOAD_TIME.toString()))
            .andExpect(jsonPath("$.localId").value(DEFAULT_LOCAL_ID.toString()))
            .andExpect(jsonPath("$.nhiId").value(DEFAULT_NHI_ID.toString()));
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where type equals to DEFAULT_TYPE
        defaultNhiMonthDeclarationDetailsShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the nhiMonthDeclarationDetailsList where type equals to UPDATED_TYPE
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultNhiMonthDeclarationDetailsShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the nhiMonthDeclarationDetailsList where type equals to UPDATED_TYPE
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where type is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("type.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where type is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByWayIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where way equals to DEFAULT_WAY
        defaultNhiMonthDeclarationDetailsShouldBeFound("way.equals=" + DEFAULT_WAY);

        // Get all the nhiMonthDeclarationDetailsList where way equals to UPDATED_WAY
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("way.equals=" + UPDATED_WAY);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByWayIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where way in DEFAULT_WAY or UPDATED_WAY
        defaultNhiMonthDeclarationDetailsShouldBeFound("way.in=" + DEFAULT_WAY + "," + UPDATED_WAY);

        // Get all the nhiMonthDeclarationDetailsList where way equals to UPDATED_WAY
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("way.in=" + UPDATED_WAY);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByWayIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where way is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("way.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where way is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("way.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByCaseTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where caseTotal equals to DEFAULT_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("caseTotal.equals=" + DEFAULT_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where caseTotal equals to UPDATED_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("caseTotal.equals=" + UPDATED_CASE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByCaseTotalIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where caseTotal in DEFAULT_CASE_TOTAL or UPDATED_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("caseTotal.in=" + DEFAULT_CASE_TOTAL + "," + UPDATED_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where caseTotal equals to UPDATED_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("caseTotal.in=" + UPDATED_CASE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByCaseTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where caseTotal is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("caseTotal.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where caseTotal is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("caseTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByCaseTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where caseTotal greater than or equals to DEFAULT_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("caseTotal.greaterOrEqualThan=" + DEFAULT_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where caseTotal greater than or equals to UPDATED_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("caseTotal.greaterOrEqualThan=" + UPDATED_CASE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByCaseTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where caseTotal less than or equals to DEFAULT_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("caseTotal.lessThan=" + DEFAULT_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where caseTotal less than or equals to UPDATED_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("caseTotal.lessThan=" + UPDATED_CASE_TOTAL);
    }


    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPointTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where pointTotal equals to DEFAULT_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("pointTotal.equals=" + DEFAULT_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where pointTotal equals to UPDATED_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("pointTotal.equals=" + UPDATED_POINT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPointTotalIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where pointTotal in DEFAULT_POINT_TOTAL or UPDATED_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("pointTotal.in=" + DEFAULT_POINT_TOTAL + "," + UPDATED_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where pointTotal equals to UPDATED_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("pointTotal.in=" + UPDATED_POINT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPointTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where pointTotal is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("pointTotal.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where pointTotal is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("pointTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPointTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where pointTotal greater than or equals to DEFAULT_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("pointTotal.greaterOrEqualThan=" + DEFAULT_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where pointTotal greater than or equals to UPDATED_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("pointTotal.greaterOrEqualThan=" + UPDATED_POINT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPointTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where pointTotal less than or equals to DEFAULT_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("pointTotal.lessThan=" + DEFAULT_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where pointTotal less than or equals to UPDATED_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("pointTotal.lessThan=" + UPDATED_POINT_TOTAL);
    }


    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByOutPatientPointIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where outPatientPoint equals to DEFAULT_OUT_PATIENT_POINT
        defaultNhiMonthDeclarationDetailsShouldBeFound("outPatientPoint.equals=" + DEFAULT_OUT_PATIENT_POINT);

        // Get all the nhiMonthDeclarationDetailsList where outPatientPoint equals to UPDATED_OUT_PATIENT_POINT
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("outPatientPoint.equals=" + UPDATED_OUT_PATIENT_POINT);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByOutPatientPointIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where outPatientPoint in DEFAULT_OUT_PATIENT_POINT or UPDATED_OUT_PATIENT_POINT
        defaultNhiMonthDeclarationDetailsShouldBeFound("outPatientPoint.in=" + DEFAULT_OUT_PATIENT_POINT + "," + UPDATED_OUT_PATIENT_POINT);

        // Get all the nhiMonthDeclarationDetailsList where outPatientPoint equals to UPDATED_OUT_PATIENT_POINT
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("outPatientPoint.in=" + UPDATED_OUT_PATIENT_POINT);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByOutPatientPointIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where outPatientPoint is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("outPatientPoint.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where outPatientPoint is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("outPatientPoint.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByOutPatientPointIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where outPatientPoint greater than or equals to DEFAULT_OUT_PATIENT_POINT
        defaultNhiMonthDeclarationDetailsShouldBeFound("outPatientPoint.greaterOrEqualThan=" + DEFAULT_OUT_PATIENT_POINT);

        // Get all the nhiMonthDeclarationDetailsList where outPatientPoint greater than or equals to UPDATED_OUT_PATIENT_POINT
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("outPatientPoint.greaterOrEqualThan=" + UPDATED_OUT_PATIENT_POINT);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByOutPatientPointIsLessThanSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where outPatientPoint less than or equals to DEFAULT_OUT_PATIENT_POINT
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("outPatientPoint.lessThan=" + DEFAULT_OUT_PATIENT_POINT);

        // Get all the nhiMonthDeclarationDetailsList where outPatientPoint less than or equals to UPDATED_OUT_PATIENT_POINT
        defaultNhiMonthDeclarationDetailsShouldBeFound("outPatientPoint.lessThan=" + UPDATED_OUT_PATIENT_POINT);
    }


    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPreventiveCaseTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where preventiveCaseTotal equals to DEFAULT_PREVENTIVE_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("preventiveCaseTotal.equals=" + DEFAULT_PREVENTIVE_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where preventiveCaseTotal equals to UPDATED_PREVENTIVE_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("preventiveCaseTotal.equals=" + UPDATED_PREVENTIVE_CASE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPreventiveCaseTotalIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where preventiveCaseTotal in DEFAULT_PREVENTIVE_CASE_TOTAL or UPDATED_PREVENTIVE_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("preventiveCaseTotal.in=" + DEFAULT_PREVENTIVE_CASE_TOTAL + "," + UPDATED_PREVENTIVE_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where preventiveCaseTotal equals to UPDATED_PREVENTIVE_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("preventiveCaseTotal.in=" + UPDATED_PREVENTIVE_CASE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPreventiveCaseTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where preventiveCaseTotal is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("preventiveCaseTotal.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where preventiveCaseTotal is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("preventiveCaseTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPreventiveCaseTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where preventiveCaseTotal greater than or equals to DEFAULT_PREVENTIVE_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("preventiveCaseTotal.greaterOrEqualThan=" + DEFAULT_PREVENTIVE_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where preventiveCaseTotal greater than or equals to UPDATED_PREVENTIVE_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("preventiveCaseTotal.greaterOrEqualThan=" + UPDATED_PREVENTIVE_CASE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPreventiveCaseTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where preventiveCaseTotal less than or equals to DEFAULT_PREVENTIVE_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("preventiveCaseTotal.lessThan=" + DEFAULT_PREVENTIVE_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where preventiveCaseTotal less than or equals to UPDATED_PREVENTIVE_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("preventiveCaseTotal.lessThan=" + UPDATED_PREVENTIVE_CASE_TOTAL);
    }


    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPreventivePointTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where preventivePointTotal equals to DEFAULT_PREVENTIVE_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("preventivePointTotal.equals=" + DEFAULT_PREVENTIVE_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where preventivePointTotal equals to UPDATED_PREVENTIVE_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("preventivePointTotal.equals=" + UPDATED_PREVENTIVE_POINT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPreventivePointTotalIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where preventivePointTotal in DEFAULT_PREVENTIVE_POINT_TOTAL or UPDATED_PREVENTIVE_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("preventivePointTotal.in=" + DEFAULT_PREVENTIVE_POINT_TOTAL + "," + UPDATED_PREVENTIVE_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where preventivePointTotal equals to UPDATED_PREVENTIVE_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("preventivePointTotal.in=" + UPDATED_PREVENTIVE_POINT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPreventivePointTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where preventivePointTotal is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("preventivePointTotal.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where preventivePointTotal is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("preventivePointTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPreventivePointTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where preventivePointTotal greater than or equals to DEFAULT_PREVENTIVE_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("preventivePointTotal.greaterOrEqualThan=" + DEFAULT_PREVENTIVE_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where preventivePointTotal greater than or equals to UPDATED_PREVENTIVE_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("preventivePointTotal.greaterOrEqualThan=" + UPDATED_PREVENTIVE_POINT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPreventivePointTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where preventivePointTotal less than or equals to DEFAULT_PREVENTIVE_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("preventivePointTotal.lessThan=" + DEFAULT_PREVENTIVE_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where preventivePointTotal less than or equals to UPDATED_PREVENTIVE_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("preventivePointTotal.lessThan=" + UPDATED_PREVENTIVE_POINT_TOTAL);
    }


    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByGeneralCaseTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where generalCaseTotal equals to DEFAULT_GENERAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("generalCaseTotal.equals=" + DEFAULT_GENERAL_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where generalCaseTotal equals to UPDATED_GENERAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("generalCaseTotal.equals=" + UPDATED_GENERAL_CASE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByGeneralCaseTotalIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where generalCaseTotal in DEFAULT_GENERAL_CASE_TOTAL or UPDATED_GENERAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("generalCaseTotal.in=" + DEFAULT_GENERAL_CASE_TOTAL + "," + UPDATED_GENERAL_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where generalCaseTotal equals to UPDATED_GENERAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("generalCaseTotal.in=" + UPDATED_GENERAL_CASE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByGeneralCaseTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where generalCaseTotal is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("generalCaseTotal.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where generalCaseTotal is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("generalCaseTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByGeneralCaseTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where generalCaseTotal greater than or equals to DEFAULT_GENERAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("generalCaseTotal.greaterOrEqualThan=" + DEFAULT_GENERAL_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where generalCaseTotal greater than or equals to UPDATED_GENERAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("generalCaseTotal.greaterOrEqualThan=" + UPDATED_GENERAL_CASE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByGeneralCaseTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where generalCaseTotal less than or equals to DEFAULT_GENERAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("generalCaseTotal.lessThan=" + DEFAULT_GENERAL_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where generalCaseTotal less than or equals to UPDATED_GENERAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("generalCaseTotal.lessThan=" + UPDATED_GENERAL_CASE_TOTAL);
    }


    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByGeneralPointTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where generalPointTotal equals to DEFAULT_GENERAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("generalPointTotal.equals=" + DEFAULT_GENERAL_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where generalPointTotal equals to UPDATED_GENERAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("generalPointTotal.equals=" + UPDATED_GENERAL_POINT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByGeneralPointTotalIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where generalPointTotal in DEFAULT_GENERAL_POINT_TOTAL or UPDATED_GENERAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("generalPointTotal.in=" + DEFAULT_GENERAL_POINT_TOTAL + "," + UPDATED_GENERAL_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where generalPointTotal equals to UPDATED_GENERAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("generalPointTotal.in=" + UPDATED_GENERAL_POINT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByGeneralPointTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where generalPointTotal is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("generalPointTotal.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where generalPointTotal is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("generalPointTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByGeneralPointTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where generalPointTotal greater than or equals to DEFAULT_GENERAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("generalPointTotal.greaterOrEqualThan=" + DEFAULT_GENERAL_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where generalPointTotal greater than or equals to UPDATED_GENERAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("generalPointTotal.greaterOrEqualThan=" + UPDATED_GENERAL_POINT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByGeneralPointTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where generalPointTotal less than or equals to DEFAULT_GENERAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("generalPointTotal.lessThan=" + DEFAULT_GENERAL_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where generalPointTotal less than or equals to UPDATED_GENERAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("generalPointTotal.lessThan=" + UPDATED_GENERAL_POINT_TOTAL);
    }


    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByProfessionalCaseTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where professionalCaseTotal equals to DEFAULT_PROFESSIONAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("professionalCaseTotal.equals=" + DEFAULT_PROFESSIONAL_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where professionalCaseTotal equals to UPDATED_PROFESSIONAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("professionalCaseTotal.equals=" + UPDATED_PROFESSIONAL_CASE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByProfessionalCaseTotalIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where professionalCaseTotal in DEFAULT_PROFESSIONAL_CASE_TOTAL or UPDATED_PROFESSIONAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("professionalCaseTotal.in=" + DEFAULT_PROFESSIONAL_CASE_TOTAL + "," + UPDATED_PROFESSIONAL_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where professionalCaseTotal equals to UPDATED_PROFESSIONAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("professionalCaseTotal.in=" + UPDATED_PROFESSIONAL_CASE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByProfessionalCaseTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where professionalCaseTotal is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("professionalCaseTotal.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where professionalCaseTotal is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("professionalCaseTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByProfessionalCaseTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where professionalCaseTotal greater than or equals to DEFAULT_PROFESSIONAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("professionalCaseTotal.greaterOrEqualThan=" + DEFAULT_PROFESSIONAL_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where professionalCaseTotal greater than or equals to UPDATED_PROFESSIONAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("professionalCaseTotal.greaterOrEqualThan=" + UPDATED_PROFESSIONAL_CASE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByProfessionalCaseTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where professionalCaseTotal less than or equals to DEFAULT_PROFESSIONAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("professionalCaseTotal.lessThan=" + DEFAULT_PROFESSIONAL_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where professionalCaseTotal less than or equals to UPDATED_PROFESSIONAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("professionalCaseTotal.lessThan=" + UPDATED_PROFESSIONAL_CASE_TOTAL);
    }


    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByProfessionalPointTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where professionalPointTotal equals to DEFAULT_PROFESSIONAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("professionalPointTotal.equals=" + DEFAULT_PROFESSIONAL_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where professionalPointTotal equals to UPDATED_PROFESSIONAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("professionalPointTotal.equals=" + UPDATED_PROFESSIONAL_POINT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByProfessionalPointTotalIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where professionalPointTotal in DEFAULT_PROFESSIONAL_POINT_TOTAL or UPDATED_PROFESSIONAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("professionalPointTotal.in=" + DEFAULT_PROFESSIONAL_POINT_TOTAL + "," + UPDATED_PROFESSIONAL_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where professionalPointTotal equals to UPDATED_PROFESSIONAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("professionalPointTotal.in=" + UPDATED_PROFESSIONAL_POINT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByProfessionalPointTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where professionalPointTotal is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("professionalPointTotal.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where professionalPointTotal is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("professionalPointTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByProfessionalPointTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where professionalPointTotal greater than or equals to DEFAULT_PROFESSIONAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("professionalPointTotal.greaterOrEqualThan=" + DEFAULT_PROFESSIONAL_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where professionalPointTotal greater than or equals to UPDATED_PROFESSIONAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("professionalPointTotal.greaterOrEqualThan=" + UPDATED_PROFESSIONAL_POINT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByProfessionalPointTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where professionalPointTotal less than or equals to DEFAULT_PROFESSIONAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("professionalPointTotal.lessThan=" + DEFAULT_PROFESSIONAL_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where professionalPointTotal less than or equals to UPDATED_PROFESSIONAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("professionalPointTotal.lessThan=" + UPDATED_PROFESSIONAL_POINT_TOTAL);
    }


    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPartialCaseTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where partialCaseTotal equals to DEFAULT_PARTIAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("partialCaseTotal.equals=" + DEFAULT_PARTIAL_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where partialCaseTotal equals to UPDATED_PARTIAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("partialCaseTotal.equals=" + UPDATED_PARTIAL_CASE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPartialCaseTotalIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where partialCaseTotal in DEFAULT_PARTIAL_CASE_TOTAL or UPDATED_PARTIAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("partialCaseTotal.in=" + DEFAULT_PARTIAL_CASE_TOTAL + "," + UPDATED_PARTIAL_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where partialCaseTotal equals to UPDATED_PARTIAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("partialCaseTotal.in=" + UPDATED_PARTIAL_CASE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPartialCaseTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where partialCaseTotal is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("partialCaseTotal.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where partialCaseTotal is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("partialCaseTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPartialCaseTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where partialCaseTotal greater than or equals to DEFAULT_PARTIAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("partialCaseTotal.greaterOrEqualThan=" + DEFAULT_PARTIAL_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where partialCaseTotal greater than or equals to UPDATED_PARTIAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("partialCaseTotal.greaterOrEqualThan=" + UPDATED_PARTIAL_CASE_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPartialCaseTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where partialCaseTotal less than or equals to DEFAULT_PARTIAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("partialCaseTotal.lessThan=" + DEFAULT_PARTIAL_CASE_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where partialCaseTotal less than or equals to UPDATED_PARTIAL_CASE_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("partialCaseTotal.lessThan=" + UPDATED_PARTIAL_CASE_TOTAL);
    }


    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPartialPointTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where partialPointTotal equals to DEFAULT_PARTIAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("partialPointTotal.equals=" + DEFAULT_PARTIAL_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where partialPointTotal equals to UPDATED_PARTIAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("partialPointTotal.equals=" + UPDATED_PARTIAL_POINT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPartialPointTotalIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where partialPointTotal in DEFAULT_PARTIAL_POINT_TOTAL or UPDATED_PARTIAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("partialPointTotal.in=" + DEFAULT_PARTIAL_POINT_TOTAL + "," + UPDATED_PARTIAL_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where partialPointTotal equals to UPDATED_PARTIAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("partialPointTotal.in=" + UPDATED_PARTIAL_POINT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPartialPointTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where partialPointTotal is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("partialPointTotal.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where partialPointTotal is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("partialPointTotal.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPartialPointTotalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where partialPointTotal greater than or equals to DEFAULT_PARTIAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("partialPointTotal.greaterOrEqualThan=" + DEFAULT_PARTIAL_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where partialPointTotal greater than or equals to UPDATED_PARTIAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("partialPointTotal.greaterOrEqualThan=" + UPDATED_PARTIAL_POINT_TOTAL);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByPartialPointTotalIsLessThanSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where partialPointTotal less than or equals to DEFAULT_PARTIAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("partialPointTotal.lessThan=" + DEFAULT_PARTIAL_POINT_TOTAL);

        // Get all the nhiMonthDeclarationDetailsList where partialPointTotal less than or equals to UPDATED_PARTIAL_POINT_TOTAL
        defaultNhiMonthDeclarationDetailsShouldBeFound("partialPointTotal.lessThan=" + UPDATED_PARTIAL_POINT_TOTAL);
    }


    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByFileIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where file equals to DEFAULT_FILE
        defaultNhiMonthDeclarationDetailsShouldBeFound("file.equals=" + DEFAULT_FILE);

        // Get all the nhiMonthDeclarationDetailsList where file equals to UPDATED_FILE
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("file.equals=" + UPDATED_FILE);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByFileIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where file in DEFAULT_FILE or UPDATED_FILE
        defaultNhiMonthDeclarationDetailsShouldBeFound("file.in=" + DEFAULT_FILE + "," + UPDATED_FILE);

        // Get all the nhiMonthDeclarationDetailsList where file equals to UPDATED_FILE
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("file.in=" + UPDATED_FILE);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByFileIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where file is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("file.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where file is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("file.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByUploadTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where uploadTime equals to DEFAULT_UPLOAD_TIME
        defaultNhiMonthDeclarationDetailsShouldBeFound("uploadTime.equals=" + DEFAULT_UPLOAD_TIME);

        // Get all the nhiMonthDeclarationDetailsList where uploadTime equals to UPDATED_UPLOAD_TIME
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("uploadTime.equals=" + UPDATED_UPLOAD_TIME);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByUploadTimeIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where uploadTime in DEFAULT_UPLOAD_TIME or UPDATED_UPLOAD_TIME
        defaultNhiMonthDeclarationDetailsShouldBeFound("uploadTime.in=" + DEFAULT_UPLOAD_TIME + "," + UPDATED_UPLOAD_TIME);

        // Get all the nhiMonthDeclarationDetailsList where uploadTime equals to UPDATED_UPLOAD_TIME
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("uploadTime.in=" + UPDATED_UPLOAD_TIME);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByUploadTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where uploadTime is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("uploadTime.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where uploadTime is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("uploadTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByLocalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where localId equals to DEFAULT_LOCAL_ID
        defaultNhiMonthDeclarationDetailsShouldBeFound("localId.equals=" + DEFAULT_LOCAL_ID);

        // Get all the nhiMonthDeclarationDetailsList where localId equals to UPDATED_LOCAL_ID
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("localId.equals=" + UPDATED_LOCAL_ID);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByLocalIdIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where localId in DEFAULT_LOCAL_ID or UPDATED_LOCAL_ID
        defaultNhiMonthDeclarationDetailsShouldBeFound("localId.in=" + DEFAULT_LOCAL_ID + "," + UPDATED_LOCAL_ID);

        // Get all the nhiMonthDeclarationDetailsList where localId equals to UPDATED_LOCAL_ID
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("localId.in=" + UPDATED_LOCAL_ID);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByLocalIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where localId is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("localId.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where localId is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("localId.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByNhiIdIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where nhiId equals to DEFAULT_NHI_ID
        defaultNhiMonthDeclarationDetailsShouldBeFound("nhiId.equals=" + DEFAULT_NHI_ID);

        // Get all the nhiMonthDeclarationDetailsList where nhiId equals to UPDATED_NHI_ID
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("nhiId.equals=" + UPDATED_NHI_ID);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByNhiIdIsInShouldWork() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where nhiId in DEFAULT_NHI_ID or UPDATED_NHI_ID
        defaultNhiMonthDeclarationDetailsShouldBeFound("nhiId.in=" + DEFAULT_NHI_ID + "," + UPDATED_NHI_ID);

        // Get all the nhiMonthDeclarationDetailsList where nhiId equals to UPDATED_NHI_ID
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("nhiId.in=" + UPDATED_NHI_ID);
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByNhiIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);

        // Get all the nhiMonthDeclarationDetailsList where nhiId is not null
        defaultNhiMonthDeclarationDetailsShouldBeFound("nhiId.specified=true");

        // Get all the nhiMonthDeclarationDetailsList where nhiId is null
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("nhiId.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiMonthDeclarationDetailsByNhiMonthDeclarationIsEqualToSomething() throws Exception {
        // Initialize the database
        NhiMonthDeclaration nhiMonthDeclaration = NhiMonthDeclarationResourceIntTest.createEntity(em);
        em.persist(nhiMonthDeclaration);
        em.flush();
        nhiMonthDeclarationDetails.setNhiMonthDeclaration(nhiMonthDeclaration);
        nhiMonthDeclarationDetailsRepository.saveAndFlush(nhiMonthDeclarationDetails);
        Long nhiMonthDeclarationId = nhiMonthDeclaration.getId();

        // Get all the nhiMonthDeclarationDetailsList where nhiMonthDeclaration equals to nhiMonthDeclarationId
        defaultNhiMonthDeclarationDetailsShouldBeFound("nhiMonthDeclarationId.equals=" + nhiMonthDeclarationId);

        // Get all the nhiMonthDeclarationDetailsList where nhiMonthDeclaration equals to nhiMonthDeclarationId + 1
        defaultNhiMonthDeclarationDetailsShouldNotBeFound("nhiMonthDeclarationId.equals=" + (nhiMonthDeclarationId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNhiMonthDeclarationDetailsShouldBeFound(String filter) throws Exception {
        restNhiMonthDeclarationDetailsMockMvc.perform(get("/api/nhi-month-declaration-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiMonthDeclarationDetails.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].way").value(hasItem(DEFAULT_WAY.toString())))
            .andExpect(jsonPath("$.[*].caseTotal").value(hasItem(DEFAULT_CASE_TOTAL)))
            .andExpect(jsonPath("$.[*].pointTotal").value(hasItem(DEFAULT_POINT_TOTAL)))
            .andExpect(jsonPath("$.[*].outPatientPoint").value(hasItem(DEFAULT_OUT_PATIENT_POINT)))
            .andExpect(jsonPath("$.[*].preventiveCaseTotal").value(hasItem(DEFAULT_PREVENTIVE_CASE_TOTAL)))
            .andExpect(jsonPath("$.[*].preventivePointTotal").value(hasItem(DEFAULT_PREVENTIVE_POINT_TOTAL)))
            .andExpect(jsonPath("$.[*].generalCaseTotal").value(hasItem(DEFAULT_GENERAL_CASE_TOTAL)))
            .andExpect(jsonPath("$.[*].generalPointTotal").value(hasItem(DEFAULT_GENERAL_POINT_TOTAL)))
            .andExpect(jsonPath("$.[*].professionalCaseTotal").value(hasItem(DEFAULT_PROFESSIONAL_CASE_TOTAL)))
            .andExpect(jsonPath("$.[*].professionalPointTotal").value(hasItem(DEFAULT_PROFESSIONAL_POINT_TOTAL)))
            .andExpect(jsonPath("$.[*].partialCaseTotal").value(hasItem(DEFAULT_PARTIAL_CASE_TOTAL)))
            .andExpect(jsonPath("$.[*].partialPointTotal").value(hasItem(DEFAULT_PARTIAL_POINT_TOTAL)))
            .andExpect(jsonPath("$.[*].file").value(hasItem(DEFAULT_FILE.toString())))
            .andExpect(jsonPath("$.[*].uploadTime").value(hasItem(DEFAULT_UPLOAD_TIME.toString())))
            .andExpect(jsonPath("$.[*].localId").value(hasItem(DEFAULT_LOCAL_ID.toString())))
            .andExpect(jsonPath("$.[*].nhiId").value(hasItem(DEFAULT_NHI_ID.toString())));

        // Check, that the count call also returns 1
        restNhiMonthDeclarationDetailsMockMvc.perform(get("/api/nhi-month-declaration-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNhiMonthDeclarationDetailsShouldNotBeFound(String filter) throws Exception {
        restNhiMonthDeclarationDetailsMockMvc.perform(get("/api/nhi-month-declaration-details?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNhiMonthDeclarationDetailsMockMvc.perform(get("/api/nhi-month-declaration-details/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNhiMonthDeclarationDetails() throws Exception {
        // Get the nhiMonthDeclarationDetails
        restNhiMonthDeclarationDetailsMockMvc.perform(get("/api/nhi-month-declaration-details/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhiMonthDeclarationDetails() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsService.save(nhiMonthDeclarationDetails);

        int databaseSizeBeforeUpdate = nhiMonthDeclarationDetailsRepository.findAll().size();

        // Update the nhiMonthDeclarationDetails
        NhiMonthDeclarationDetails updatedNhiMonthDeclarationDetails = nhiMonthDeclarationDetailsRepository.findById(nhiMonthDeclarationDetails.getId()).get();
        // Disconnect from session so that the updates on updatedNhiMonthDeclarationDetails are not directly saved in db
        em.detach(updatedNhiMonthDeclarationDetails);
        updatedNhiMonthDeclarationDetails
            .type(UPDATED_TYPE)
            .way(UPDATED_WAY)
            .caseTotal(UPDATED_CASE_TOTAL)
            .pointTotal(UPDATED_POINT_TOTAL)
            .outPatientPoint(UPDATED_OUT_PATIENT_POINT)
            .preventiveCaseTotal(UPDATED_PREVENTIVE_CASE_TOTAL)
            .preventivePointTotal(UPDATED_PREVENTIVE_POINT_TOTAL)
            .generalCaseTotal(UPDATED_GENERAL_CASE_TOTAL)
            .generalPointTotal(UPDATED_GENERAL_POINT_TOTAL)
            .professionalCaseTotal(UPDATED_PROFESSIONAL_CASE_TOTAL)
            .professionalPointTotal(UPDATED_PROFESSIONAL_POINT_TOTAL)
            .partialCaseTotal(UPDATED_PARTIAL_CASE_TOTAL)
            .partialPointTotal(UPDATED_PARTIAL_POINT_TOTAL)
            .file(UPDATED_FILE)
            .uploadTime(UPDATED_UPLOAD_TIME)
            .localId(UPDATED_LOCAL_ID)
            .nhiId(UPDATED_NHI_ID);

        restNhiMonthDeclarationDetailsMockMvc.perform(put("/api/nhi-month-declaration-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNhiMonthDeclarationDetails)))
            .andExpect(status().isOk());

        // Validate the NhiMonthDeclarationDetails in the database
        List<NhiMonthDeclarationDetails> nhiMonthDeclarationDetailsList = nhiMonthDeclarationDetailsRepository.findAll();
        assertThat(nhiMonthDeclarationDetailsList).hasSize(databaseSizeBeforeUpdate);
        NhiMonthDeclarationDetails testNhiMonthDeclarationDetails = nhiMonthDeclarationDetailsList.get(nhiMonthDeclarationDetailsList.size() - 1);
        assertThat(testNhiMonthDeclarationDetails.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testNhiMonthDeclarationDetails.getWay()).isEqualTo(UPDATED_WAY);
        assertThat(testNhiMonthDeclarationDetails.getCaseTotal()).isEqualTo(UPDATED_CASE_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getPointTotal()).isEqualTo(UPDATED_POINT_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getOutPatientPoint()).isEqualTo(UPDATED_OUT_PATIENT_POINT);
        assertThat(testNhiMonthDeclarationDetails.getPreventiveCaseTotal()).isEqualTo(UPDATED_PREVENTIVE_CASE_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getPreventivePointTotal()).isEqualTo(UPDATED_PREVENTIVE_POINT_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getGeneralCaseTotal()).isEqualTo(UPDATED_GENERAL_CASE_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getGeneralPointTotal()).isEqualTo(UPDATED_GENERAL_POINT_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getProfessionalCaseTotal()).isEqualTo(UPDATED_PROFESSIONAL_CASE_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getProfessionalPointTotal()).isEqualTo(UPDATED_PROFESSIONAL_POINT_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getPartialCaseTotal()).isEqualTo(UPDATED_PARTIAL_CASE_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getPartialPointTotal()).isEqualTo(UPDATED_PARTIAL_POINT_TOTAL);
        assertThat(testNhiMonthDeclarationDetails.getFile()).isEqualTo(UPDATED_FILE);
        assertThat(testNhiMonthDeclarationDetails.getUploadTime()).isEqualTo(UPDATED_UPLOAD_TIME);
        assertThat(testNhiMonthDeclarationDetails.getLocalId()).isEqualTo(UPDATED_LOCAL_ID);
        assertThat(testNhiMonthDeclarationDetails.getNhiId()).isEqualTo(UPDATED_NHI_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingNhiMonthDeclarationDetails() throws Exception {
        int databaseSizeBeforeUpdate = nhiMonthDeclarationDetailsRepository.findAll().size();

        // Create the NhiMonthDeclarationDetails

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhiMonthDeclarationDetailsMockMvc.perform(put("/api/nhi-month-declaration-details")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiMonthDeclarationDetails)))
            .andExpect(status().isBadRequest());

        // Validate the NhiMonthDeclarationDetails in the database
        List<NhiMonthDeclarationDetails> nhiMonthDeclarationDetailsList = nhiMonthDeclarationDetailsRepository.findAll();
        assertThat(nhiMonthDeclarationDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhiMonthDeclarationDetails() throws Exception {
        // Initialize the database
        nhiMonthDeclarationDetailsService.save(nhiMonthDeclarationDetails);

        int databaseSizeBeforeDelete = nhiMonthDeclarationDetailsRepository.findAll().size();

        // Get the nhiMonthDeclarationDetails
        restNhiMonthDeclarationDetailsMockMvc.perform(delete("/api/nhi-month-declaration-details/{id}", nhiMonthDeclarationDetails.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhiMonthDeclarationDetails> nhiMonthDeclarationDetailsList = nhiMonthDeclarationDetailsRepository.findAll();
        assertThat(nhiMonthDeclarationDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhiMonthDeclarationDetails.class);
        NhiMonthDeclarationDetails nhiMonthDeclarationDetails1 = new NhiMonthDeclarationDetails();
        nhiMonthDeclarationDetails1.setId(1L);
        NhiMonthDeclarationDetails nhiMonthDeclarationDetails2 = new NhiMonthDeclarationDetails();
        nhiMonthDeclarationDetails2.setId(nhiMonthDeclarationDetails1.getId());
        assertThat(nhiMonthDeclarationDetails1).isEqualTo(nhiMonthDeclarationDetails2);
        nhiMonthDeclarationDetails2.setId(2L);
        assertThat(nhiMonthDeclarationDetails1).isNotEqualTo(nhiMonthDeclarationDetails2);
        nhiMonthDeclarationDetails1.setId(null);
        assertThat(nhiMonthDeclarationDetails1).isNotEqualTo(nhiMonthDeclarationDetails2);
    }
}
