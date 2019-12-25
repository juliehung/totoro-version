package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.*;
import io.dentall.totoro.repository.*;
import io.dentall.totoro.service.DisposalService;
import io.dentall.totoro.service.NhiService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.DisposalQueryService;

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
import java.util.HashSet;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.dentall.totoro.domain.enumeration.DisposalStatus;
/**
 * Test class for the DisposalResource REST controller.
 *
 * @see DisposalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class DisposalResourceIntTest {

    private static final DisposalStatus DEFAULT_STATUS = DisposalStatus.TEMPORARY;
    private static final DisposalStatus UPDATED_STATUS = DisposalStatus.PERMANENT;

    private static final Double DEFAULT_TOTAL = 1D;
    private static final Double UPDATED_TOTAL = 2D;

    private static final Instant DEFAULT_DATE_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DATE_TIME_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_TIME_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CHIEF_COMPLAINT = "AAAAAAAAAA";
    private static final String UPDATED_CHIEF_COMPLAINT = "BBBBBBBBBB";

    @Autowired
    private DisposalRepository disposalRepository;

    @Autowired
    private DisposalService disposalService;

    @Autowired
    private DisposalQueryService disposalQueryService;

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
    private PatientRepository patientRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private NhiService nhiService;

    private MockMvc restDisposalMockMvc;

    private Disposal disposal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DisposalResource disposalResource = new DisposalResource(disposalService, disposalQueryService, nhiService);
        this.restDisposalMockMvc = MockMvcBuilders.standaloneSetup(disposalResource)
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
    public static Disposal createEntity(EntityManager em) {
        Disposal disposal = new Disposal()
            .status(DEFAULT_STATUS)
            .total(DEFAULT_TOTAL)
            .dateTime(DEFAULT_DATE_TIME)
            .dateTimeEnd(DEFAULT_DATE_TIME_END)
            .chiefComplaint(DEFAULT_CHIEF_COMPLAINT);
        return disposal;
    }

    @Before
    public void initTest() {
        disposal = createEntity(em);
    }

    @Test
    @Transactional
    public void createDisposal() throws Exception {
        Patient patient = TestUtil.createPatient(em, userRepository, tagRepository, patientRepository);
        Appointment appointment = createAppointment(patient);
        disposal.setRegistration(appointment.getRegistration());

        int databaseSizeBeforeCreate = disposalRepository.findAll().size();

        // Create the Disposal
        restDisposalMockMvc.perform(post("/api/disposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disposal)))
            .andExpect(status().isCreated());

        // Validate the Disposal in the database
        List<Disposal> disposalList = disposalRepository.findAll();
        assertThat(disposalList).hasSize(databaseSizeBeforeCreate + 1);
        Disposal testDisposal = disposalList.get(disposalList.size() - 1);
        assertThat(testDisposal.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDisposal.getTotal()).isEqualTo(DEFAULT_TOTAL);
        assertThat(testDisposal.getCreatedBy()).isEqualTo(appointment.getDoctor().getUser().getLogin());
        assertThat(testDisposal.getDateTime()).isEqualTo(DEFAULT_DATE_TIME);
        assertThat(testDisposal.getDateTimeEnd()).isEqualTo(DEFAULT_DATE_TIME_END);
        assertThat(testDisposal.getChiefComplaint()).isEqualTo(DEFAULT_CHIEF_COMPLAINT);
    }

    @Test
    @Transactional
    public void createDisposalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = disposalRepository.findAll().size();

        // Create the Disposal with an existing ID
        disposal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDisposalMockMvc.perform(post("/api/disposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disposal)))
            .andExpect(status().isBadRequest());

        // Validate the Disposal in the database
        List<Disposal> disposalList = disposalRepository.findAll();
        assertThat(disposalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = disposalRepository.findAll().size();
        // set the field null
        disposal.setStatus(null);

        // Create the Disposal, which fails.

        restDisposalMockMvc.perform(post("/api/disposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disposal)))
            .andExpect(status().isBadRequest());

        List<Disposal> disposalList = disposalRepository.findAll();
        assertThat(disposalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDisposals() throws Exception {
        // Initialize the database
        disposalRepository.saveAndFlush(disposal);

        // Get all the disposalList
        restDisposalMockMvc.perform(get("/api/disposals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disposal.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].dateTimeEnd").value(hasItem(DEFAULT_DATE_TIME_END.toString())))
            .andExpect(jsonPath("$.[*].chiefComplaint").value(hasItem(DEFAULT_CHIEF_COMPLAINT.toString())));
    }

    @Test
    @Transactional
    public void getDisposal() throws Exception {
        // Initialize the database
        disposalRepository.saveAndFlush(disposal);

        // Get the disposal
        restDisposalMockMvc.perform(get("/api/disposals/{id}", disposal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(disposal.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.total").value(DEFAULT_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.dateTime").value(DEFAULT_DATE_TIME.toString()))
            .andExpect(jsonPath("$.dateTimeEnd").value(DEFAULT_DATE_TIME_END.toString()))
            .andExpect(jsonPath("$.chiefComplaint").value(DEFAULT_CHIEF_COMPLAINT.toString()));
    }

    @Test
    @Transactional
    public void getAllDisposalsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        disposalRepository.saveAndFlush(disposal);

        // Get all the disposalList where status equals to DEFAULT_STATUS
        defaultDisposalShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the disposalList where status equals to UPDATED_STATUS
        defaultDisposalShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllDisposalsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        disposalRepository.saveAndFlush(disposal);

        // Get all the disposalList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultDisposalShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the disposalList where status equals to UPDATED_STATUS
        defaultDisposalShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllDisposalsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        disposalRepository.saveAndFlush(disposal);

        // Get all the disposalList where status is not null
        defaultDisposalShouldBeFound("status.specified=true");

        // Get all the disposalList where status is null
        defaultDisposalShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllDisposalsByTotalIsEqualToSomething() throws Exception {
        // Initialize the database
        disposalRepository.saveAndFlush(disposal);

        // Get all the disposalList where total equals to DEFAULT_TOTAL
        defaultDisposalShouldBeFound("total.equals=" + DEFAULT_TOTAL);

        // Get all the disposalList where total equals to UPDATED_TOTAL
        defaultDisposalShouldNotBeFound("total.equals=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllDisposalsByTotalIsInShouldWork() throws Exception {
        // Initialize the database
        disposalRepository.saveAndFlush(disposal);

        // Get all the disposalList where total in DEFAULT_TOTAL or UPDATED_TOTAL
        defaultDisposalShouldBeFound("total.in=" + DEFAULT_TOTAL + "," + UPDATED_TOTAL);

        // Get all the disposalList where total equals to UPDATED_TOTAL
        defaultDisposalShouldNotBeFound("total.in=" + UPDATED_TOTAL);
    }

    @Test
    @Transactional
    public void getAllDisposalsByTotalIsNullOrNotNull() throws Exception {
        // Initialize the database
        disposalRepository.saveAndFlush(disposal);

        // Get all the disposalList where total is not null
        defaultDisposalShouldBeFound("total.specified=true");

        // Get all the disposalList where total is null
        defaultDisposalShouldNotBeFound("total.specified=false");
    }

    @Test
    @Transactional
    public void getAllDisposalsByDateTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        disposalRepository.saveAndFlush(disposal);

        // Get all the disposalList where dateTime equals to DEFAULT_DATE_TIME
        defaultDisposalShouldBeFound("dateTime.equals=" + DEFAULT_DATE_TIME);

        // Get all the disposalList where dateTime equals to UPDATED_DATE_TIME
        defaultDisposalShouldNotBeFound("dateTime.equals=" + UPDATED_DATE_TIME);

        // Get all the disposalList where dateTime equals to DEFAULT_DATE_TIME
        defaultDisposalShouldBeFound("dateTimeEnd.equals=" + DEFAULT_DATE_TIME_END);

        // Get all the disposalList where dateTime equals to UPDATED_DATE_TIME
        defaultDisposalShouldNotBeFound("dateTimeEnd.equals=" + UPDATED_DATE_TIME_END);
    }

    @Test
    @Transactional
    public void getAllDisposalsByDateTimeIsInShouldWork() throws Exception {
        // Initialize the database
        disposalRepository.saveAndFlush(disposal);

        // Get all the disposalList where dateTime in DEFAULT_DATE_TIME or UPDATED_DATE_TIME
        defaultDisposalShouldBeFound("dateTime.in=" + DEFAULT_DATE_TIME + "," + UPDATED_DATE_TIME);

        // Get all the disposalList where dateTime equals to UPDATED_DATE_TIME
        defaultDisposalShouldNotBeFound("dateTime.in=" + UPDATED_DATE_TIME);

        // Get all the disposalList where dateTime in DEFAULT_DATE_TIME_END or UPDATED_DATE_TIME_END
        defaultDisposalShouldBeFound("dateTimeEnd.in=" + DEFAULT_DATE_TIME_END + "," + UPDATED_DATE_TIME_END);

        // Get all the disposalList where dateTime equals to UPDATED_DATE_TIME_END
        defaultDisposalShouldNotBeFound("dateTimeEnd.in=" + UPDATED_DATE_TIME_END);
    }

    @Test
    @Transactional
    public void getAllDisposalsByDateTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        disposalRepository.saveAndFlush(disposal);

        // Get all the disposalList where dateTime is not null
        defaultDisposalShouldBeFound("dateTime.specified=true");

        // Get all the disposalList where dateTime is null
        defaultDisposalShouldNotBeFound("dateTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllDisposalsByTreatmentProcedureIsEqualToSomething() throws Exception {
        // Initialize the database
        TreatmentProcedure treatmentProcedure = TreatmentProcedureResourceIntTest.createEntity(em);
        em.persist(treatmentProcedure);
        em.flush();

        if (disposal.getTreatmentProcedures() == null) {
            disposal.setTreatmentProcedures(new HashSet<>());
        }

        disposal.addTreatmentProcedure(treatmentProcedure);
        disposalRepository.saveAndFlush(disposal);
        Long treatmentProcedureId = treatmentProcedure.getId();

        // Get all the disposalList where treatmentProcedure equals to treatmentProcedureId
        defaultDisposalShouldBeFound("treatmentProcedureId.equals=" + treatmentProcedureId);

        // Get all the disposalList where treatmentProcedure equals to treatmentProcedureId + 1
        defaultDisposalShouldNotBeFound("treatmentProcedureId.equals=" + (treatmentProcedureId + 1));
    }


    @Test
    @Transactional
    public void getAllDisposalsByPrescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        Prescription prescription = PrescriptionResourceIntTest.createEntity(em);
        em.persist(prescription);
        em.flush();
        disposal.setPrescription(prescription);
        disposalRepository.saveAndFlush(disposal);
        Long prescriptionId = prescription.getId();

        // Get all the disposalList where prescription equals to prescriptionId
        defaultDisposalShouldBeFound("prescriptionId.equals=" + prescriptionId);

        // Get all the disposalList where prescription equals to prescriptionId + 1
        defaultDisposalShouldNotBeFound("prescriptionId.equals=" + (prescriptionId + 1));
    }


    @Test
    @Transactional
    public void getAllDisposalsByTodoIsEqualToSomething() throws Exception {
        // Initialize the database
        Todo todo = TodoResourceIntTest.createEntity(em);
        em.persist(todo);
        em.flush();
        disposal.setTodo(todo);
        disposalRepository.saveAndFlush(disposal);
        Long todoId = todo.getId();

        // Get all the disposalList where todo equals to todoId
        defaultDisposalShouldBeFound("todoId.equals=" + todoId);

        // Get all the disposalList where todo equals to todoId + 1
        defaultDisposalShouldNotBeFound("todoId.equals=" + (todoId + 1));
    }


    @Test
    @Transactional
    public void getAllDisposalsByRegistrationIsEqualToSomething() throws Exception {
        // Initialize the database
        Registration registration = RegistrationResourceIntTest.createEntity(em);
        em.persist(registration);
        em.flush();
        disposal.setRegistration(registration);
        disposalRepository.saveAndFlush(disposal);
        Long registrationId = registration.getId();

        // Get all the disposalList where registration equals to registrationId
        defaultDisposalShouldBeFound("registrationId.equals=" + registrationId);

        // Get all the disposalList where registration equals to registrationId + 1
        defaultDisposalShouldNotBeFound("registrationId.equals=" + (registrationId + 1));
    }

    @Test
    @Transactional
    public void getAllDisposalsByToothIsEqualToSomething() throws Exception {
        // Initialize the database
        Tooth tooth = ToothResourceIntTest.createEntity(em);
        em.persist(tooth);
        em.flush();
        disposal.teeth(new HashSet<>()).addTooth(tooth);
        disposalRepository.saveAndFlush(disposal);
        Long toothId = tooth.getId();

        // Get all the disposalList where tooth equals to toothId
        defaultDisposalShouldBeFound("toothId.equals=" + toothId);

        // Get all the disposalList where tooth equals to toothId + 1
        defaultDisposalShouldNotBeFound("toothId.equals=" + (toothId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDisposalShouldBeFound(String filter) throws Exception {
        restDisposalMockMvc.perform(get("/api/disposals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(disposal.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].total").value(hasItem(DEFAULT_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].dateTime").value(hasItem(DEFAULT_DATE_TIME.toString())))
            .andExpect(jsonPath("$.[*].dateTimeEnd").value(hasItem(DEFAULT_DATE_TIME_END.toString())))
            .andExpect(jsonPath("$.[*].chiefComplaint").value(hasItem(DEFAULT_CHIEF_COMPLAINT.toString())));

        // Check, that the count call also returns 1
        restDisposalMockMvc.perform(get("/api/disposals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDisposalShouldNotBeFound(String filter) throws Exception {
        restDisposalMockMvc.perform(get("/api/disposals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDisposalMockMvc.perform(get("/api/disposals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDisposal() throws Exception {
        // Get the disposal
        restDisposalMockMvc.perform(get("/api/disposals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDisposal() throws Exception {
        // Initialize the database
        disposalService.save(disposal);

        int databaseSizeBeforeUpdate = disposalRepository.findAll().size();

        // Update the disposal
        Disposal updatedDisposal = disposalRepository.findById(disposal.getId()).get();
        // Disconnect from session so that the updates on updatedDisposal are not directly saved in db
        em.detach(updatedDisposal);
        updatedDisposal
            .status(UPDATED_STATUS)
            .total(UPDATED_TOTAL)
            .dateTime(UPDATED_DATE_TIME)
            .chiefComplaint(UPDATED_CHIEF_COMPLAINT);

        restDisposalMockMvc.perform(put("/api/disposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDisposal)))
            .andExpect(status().isOk());

        // Validate the Disposal in the database
        List<Disposal> disposalList = disposalRepository.findAll();
        assertThat(disposalList).hasSize(databaseSizeBeforeUpdate);
        Disposal testDisposal = disposalList.get(disposalList.size() - 1);
        assertThat(testDisposal.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDisposal.getTotal()).isEqualTo(UPDATED_TOTAL);
        assertThat(testDisposal.getDateTime()).isEqualTo(UPDATED_DATE_TIME);
        assertThat(testDisposal.getChiefComplaint()).isEqualTo(UPDATED_CHIEF_COMPLAINT);
    }

    @Test
    @Transactional
    public void updateNonExistingDisposal() throws Exception {
        int databaseSizeBeforeUpdate = disposalRepository.findAll().size();

        // Create the Disposal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDisposalMockMvc.perform(put("/api/disposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disposal)))
            .andExpect(status().isBadRequest());

        // Validate the Disposal in the database
        List<Disposal> disposalList = disposalRepository.findAll();
        assertThat(disposalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDisposal() throws Exception {
        // Initialize the database
        disposalService.save(disposal);

        int databaseSizeBeforeDelete = disposalRepository.findAll().size();

        // Get the disposal
        restDisposalMockMvc.perform(delete("/api/disposals/{id}", disposal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Disposal> disposalList = disposalRepository.findAll();
        assertThat(disposalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Disposal.class);
        Disposal disposal1 = new Disposal();
        disposal1.setId(1L);
        Disposal disposal2 = new Disposal();
        disposal2.setId(disposal1.getId());
        assertThat(disposal1).isEqualTo(disposal2);
        disposal2.setId(2L);
        assertThat(disposal1).isNotEqualTo(disposal2);
        disposal1.setId(null);
        assertThat(disposal1).isNotEqualTo(disposal2);
    }

    private Appointment createAppointment(Patient patient) {
        Appointment appointment = AppointmentResourceIntTest.createEntity(em);
        appointment.setPatient(patient);
        appointment.setDoctor(userRepository.save(UserResourceIntTest.createEntity(em)).getExtendUser());

        Registration registration = registrationRepository.save(RegistrationResourceIntTest.createEntity(em));
        appointment.setRegistration(registration);
        registration.setAppointment(appointment);

        return appointmentRepository.saveAndFlush(appointment);
    }
}
