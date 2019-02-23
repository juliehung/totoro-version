package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.Prescription;
import io.dentall.totoro.domain.TreatmentDrug;
import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.repository.PrescriptionRepository;
import io.dentall.totoro.service.PrescriptionService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.dto.PrescriptionCriteria;
import io.dentall.totoro.service.PrescriptionQueryService;

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

import io.dentall.totoro.domain.enumeration.PrescriptionStatus;
/**
 * Test class for the PrescriptionResource REST controller.
 *
 * @see PrescriptionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class PrescriptionResourceIntTest {

    private static final Boolean DEFAULT_CLINIC_ADMINISTRATION = false;
    private static final Boolean UPDATED_CLINIC_ADMINISTRATION = true;

    private static final Boolean DEFAULT_ANTI_INFLAMMATORY_DRUG = false;
    private static final Boolean UPDATED_ANTI_INFLAMMATORY_DRUG = true;

    private static final Boolean DEFAULT_PAIN = false;
    private static final Boolean UPDATED_PAIN = true;

    private static final Boolean DEFAULT_TAKEN_ALL = false;
    private static final Boolean UPDATED_TAKEN_ALL = true;

    private static final PrescriptionStatus DEFAULT_STATUS = PrescriptionStatus.TEMPORARY;
    private static final PrescriptionStatus UPDATED_STATUS = PrescriptionStatus.PERMANENT;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PrescriptionQueryService prescriptionQueryService;

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

    private MockMvc restPrescriptionMockMvc;

    private Prescription prescription;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrescriptionResource prescriptionResource = new PrescriptionResource(prescriptionService, prescriptionQueryService);
        this.restPrescriptionMockMvc = MockMvcBuilders.standaloneSetup(prescriptionResource)
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
    public static Prescription createEntity(EntityManager em) {
        Prescription prescription = new Prescription()
            .clinicAdministration(DEFAULT_CLINIC_ADMINISTRATION)
            .antiInflammatoryDrug(DEFAULT_ANTI_INFLAMMATORY_DRUG)
            .pain(DEFAULT_PAIN)
            .takenAll(DEFAULT_TAKEN_ALL)
            .status(DEFAULT_STATUS);
        return prescription;
    }

    @Before
    public void initTest() {
        prescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrescription() throws Exception {
        int databaseSizeBeforeCreate = prescriptionRepository.findAll().size();

        // Create the Prescription
        restPrescriptionMockMvc.perform(post("/api/prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescription)))
            .andExpect(status().isCreated());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        Prescription testPrescription = prescriptionList.get(prescriptionList.size() - 1);
        assertThat(testPrescription.isClinicAdministration()).isEqualTo(DEFAULT_CLINIC_ADMINISTRATION);
        assertThat(testPrescription.isAntiInflammatoryDrug()).isEqualTo(DEFAULT_ANTI_INFLAMMATORY_DRUG);
        assertThat(testPrescription.isPain()).isEqualTo(DEFAULT_PAIN);
        assertThat(testPrescription.isTakenAll()).isEqualTo(DEFAULT_TAKEN_ALL);
        assertThat(testPrescription.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPrescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prescriptionRepository.findAll().size();

        // Create the Prescription with an existing ID
        prescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrescriptionMockMvc.perform(post("/api/prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescription)))
            .andExpect(status().isBadRequest());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = prescriptionRepository.findAll().size();
        // set the field null
        prescription.setStatus(null);

        // Create the Prescription, which fails.

        restPrescriptionMockMvc.perform(post("/api/prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescription)))
            .andExpect(status().isBadRequest());

        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPrescriptions() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList
        restPrescriptionMockMvc.perform(get("/api/prescriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].clinicAdministration").value(hasItem(DEFAULT_CLINIC_ADMINISTRATION.booleanValue())))
            .andExpect(jsonPath("$.[*].antiInflammatoryDrug").value(hasItem(DEFAULT_ANTI_INFLAMMATORY_DRUG.booleanValue())))
            .andExpect(jsonPath("$.[*].pain").value(hasItem(DEFAULT_PAIN.booleanValue())))
            .andExpect(jsonPath("$.[*].takenAll").value(hasItem(DEFAULT_TAKEN_ALL.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }
    
    @Test
    @Transactional
    public void getPrescription() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get the prescription
        restPrescriptionMockMvc.perform(get("/api/prescriptions/{id}", prescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prescription.getId().intValue()))
            .andExpect(jsonPath("$.clinicAdministration").value(DEFAULT_CLINIC_ADMINISTRATION.booleanValue()))
            .andExpect(jsonPath("$.antiInflammatoryDrug").value(DEFAULT_ANTI_INFLAMMATORY_DRUG.booleanValue()))
            .andExpect(jsonPath("$.pain").value(DEFAULT_PAIN.booleanValue()))
            .andExpect(jsonPath("$.takenAll").value(DEFAULT_TAKEN_ALL.booleanValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByClinicAdministrationIsEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where clinicAdministration equals to DEFAULT_CLINIC_ADMINISTRATION
        defaultPrescriptionShouldBeFound("clinicAdministration.equals=" + DEFAULT_CLINIC_ADMINISTRATION);

        // Get all the prescriptionList where clinicAdministration equals to UPDATED_CLINIC_ADMINISTRATION
        defaultPrescriptionShouldNotBeFound("clinicAdministration.equals=" + UPDATED_CLINIC_ADMINISTRATION);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByClinicAdministrationIsInShouldWork() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where clinicAdministration in DEFAULT_CLINIC_ADMINISTRATION or UPDATED_CLINIC_ADMINISTRATION
        defaultPrescriptionShouldBeFound("clinicAdministration.in=" + DEFAULT_CLINIC_ADMINISTRATION + "," + UPDATED_CLINIC_ADMINISTRATION);

        // Get all the prescriptionList where clinicAdministration equals to UPDATED_CLINIC_ADMINISTRATION
        defaultPrescriptionShouldNotBeFound("clinicAdministration.in=" + UPDATED_CLINIC_ADMINISTRATION);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByClinicAdministrationIsNullOrNotNull() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where clinicAdministration is not null
        defaultPrescriptionShouldBeFound("clinicAdministration.specified=true");

        // Get all the prescriptionList where clinicAdministration is null
        defaultPrescriptionShouldNotBeFound("clinicAdministration.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByAntiInflammatoryDrugIsEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where antiInflammatoryDrug equals to DEFAULT_ANTI_INFLAMMATORY_DRUG
        defaultPrescriptionShouldBeFound("antiInflammatoryDrug.equals=" + DEFAULT_ANTI_INFLAMMATORY_DRUG);

        // Get all the prescriptionList where antiInflammatoryDrug equals to UPDATED_ANTI_INFLAMMATORY_DRUG
        defaultPrescriptionShouldNotBeFound("antiInflammatoryDrug.equals=" + UPDATED_ANTI_INFLAMMATORY_DRUG);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByAntiInflammatoryDrugIsInShouldWork() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where antiInflammatoryDrug in DEFAULT_ANTI_INFLAMMATORY_DRUG or UPDATED_ANTI_INFLAMMATORY_DRUG
        defaultPrescriptionShouldBeFound("antiInflammatoryDrug.in=" + DEFAULT_ANTI_INFLAMMATORY_DRUG + "," + UPDATED_ANTI_INFLAMMATORY_DRUG);

        // Get all the prescriptionList where antiInflammatoryDrug equals to UPDATED_ANTI_INFLAMMATORY_DRUG
        defaultPrescriptionShouldNotBeFound("antiInflammatoryDrug.in=" + UPDATED_ANTI_INFLAMMATORY_DRUG);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByAntiInflammatoryDrugIsNullOrNotNull() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where antiInflammatoryDrug is not null
        defaultPrescriptionShouldBeFound("antiInflammatoryDrug.specified=true");

        // Get all the prescriptionList where antiInflammatoryDrug is null
        defaultPrescriptionShouldNotBeFound("antiInflammatoryDrug.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByPainIsEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where pain equals to DEFAULT_PAIN
        defaultPrescriptionShouldBeFound("pain.equals=" + DEFAULT_PAIN);

        // Get all the prescriptionList where pain equals to UPDATED_PAIN
        defaultPrescriptionShouldNotBeFound("pain.equals=" + UPDATED_PAIN);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByPainIsInShouldWork() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where pain in DEFAULT_PAIN or UPDATED_PAIN
        defaultPrescriptionShouldBeFound("pain.in=" + DEFAULT_PAIN + "," + UPDATED_PAIN);

        // Get all the prescriptionList where pain equals to UPDATED_PAIN
        defaultPrescriptionShouldNotBeFound("pain.in=" + UPDATED_PAIN);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByPainIsNullOrNotNull() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where pain is not null
        defaultPrescriptionShouldBeFound("pain.specified=true");

        // Get all the prescriptionList where pain is null
        defaultPrescriptionShouldNotBeFound("pain.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByTakenAllIsEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where takenAll equals to DEFAULT_TAKEN_ALL
        defaultPrescriptionShouldBeFound("takenAll.equals=" + DEFAULT_TAKEN_ALL);

        // Get all the prescriptionList where takenAll equals to UPDATED_TAKEN_ALL
        defaultPrescriptionShouldNotBeFound("takenAll.equals=" + UPDATED_TAKEN_ALL);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByTakenAllIsInShouldWork() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where takenAll in DEFAULT_TAKEN_ALL or UPDATED_TAKEN_ALL
        defaultPrescriptionShouldBeFound("takenAll.in=" + DEFAULT_TAKEN_ALL + "," + UPDATED_TAKEN_ALL);

        // Get all the prescriptionList where takenAll equals to UPDATED_TAKEN_ALL
        defaultPrescriptionShouldNotBeFound("takenAll.in=" + UPDATED_TAKEN_ALL);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByTakenAllIsNullOrNotNull() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where takenAll is not null
        defaultPrescriptionShouldBeFound("takenAll.specified=true");

        // Get all the prescriptionList where takenAll is null
        defaultPrescriptionShouldNotBeFound("takenAll.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where status equals to DEFAULT_STATUS
        defaultPrescriptionShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the prescriptionList where status equals to UPDATED_STATUS
        defaultPrescriptionShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultPrescriptionShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the prescriptionList where status equals to UPDATED_STATUS
        defaultPrescriptionShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where status is not null
        defaultPrescriptionShouldBeFound("status.specified=true");

        // Get all the prescriptionList where status is null
        defaultPrescriptionShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByTreatmentDrugIsEqualToSomething() throws Exception {
        // Initialize the database
        TreatmentDrug treatmentDrug = TreatmentDrugResourceIntTest.createEntity(em);
        em.persist(treatmentDrug);
        em.flush();
        prescription.addTreatmentDrug(treatmentDrug);
        prescriptionRepository.saveAndFlush(prescription);
        Long treatmentDrugId = treatmentDrug.getId();

        // Get all the prescriptionList where treatmentDrug equals to treatmentDrugId
        defaultPrescriptionShouldBeFound("treatmentDrugId.equals=" + treatmentDrugId);

        // Get all the prescriptionList where treatmentDrug equals to treatmentDrugId + 1
        defaultPrescriptionShouldNotBeFound("treatmentDrugId.equals=" + (treatmentDrugId + 1));
    }


    @Test
    @Transactional
    public void getAllPrescriptionsByDisposalIsEqualToSomething() throws Exception {
        // Initialize the database
        Disposal disposal = DisposalResourceIntTest.createEntity(em);
        em.persist(disposal);
        em.flush();
        prescription.setDisposal(disposal);
        disposal.setPrescription(prescription);
        prescriptionRepository.saveAndFlush(prescription);
        Long disposalId = disposal.getId();

        // Get all the prescriptionList where disposal equals to disposalId
        defaultPrescriptionShouldBeFound("disposalId.equals=" + disposalId);

        // Get all the prescriptionList where disposal equals to disposalId + 1
        defaultPrescriptionShouldNotBeFound("disposalId.equals=" + (disposalId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultPrescriptionShouldBeFound(String filter) throws Exception {
        restPrescriptionMockMvc.perform(get("/api/prescriptions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].clinicAdministration").value(hasItem(DEFAULT_CLINIC_ADMINISTRATION.booleanValue())))
            .andExpect(jsonPath("$.[*].antiInflammatoryDrug").value(hasItem(DEFAULT_ANTI_INFLAMMATORY_DRUG.booleanValue())))
            .andExpect(jsonPath("$.[*].pain").value(hasItem(DEFAULT_PAIN.booleanValue())))
            .andExpect(jsonPath("$.[*].takenAll").value(hasItem(DEFAULT_TAKEN_ALL.booleanValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));

        // Check, that the count call also returns 1
        restPrescriptionMockMvc.perform(get("/api/prescriptions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultPrescriptionShouldNotBeFound(String filter) throws Exception {
        restPrescriptionMockMvc.perform(get("/api/prescriptions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPrescriptionMockMvc.perform(get("/api/prescriptions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPrescription() throws Exception {
        // Get the prescription
        restPrescriptionMockMvc.perform(get("/api/prescriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrescription() throws Exception {
        // Initialize the database
        prescriptionService.save(prescription);

        int databaseSizeBeforeUpdate = prescriptionRepository.findAll().size();

        // Update the prescription
        Prescription updatedPrescription = prescriptionRepository.findById(prescription.getId()).get();
        // Disconnect from session so that the updates on updatedPrescription are not directly saved in db
        em.detach(updatedPrescription);
        updatedPrescription
            .clinicAdministration(UPDATED_CLINIC_ADMINISTRATION)
            .antiInflammatoryDrug(UPDATED_ANTI_INFLAMMATORY_DRUG)
            .pain(UPDATED_PAIN)
            .takenAll(UPDATED_TAKEN_ALL)
            .status(UPDATED_STATUS);

        restPrescriptionMockMvc.perform(put("/api/prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrescription)))
            .andExpect(status().isOk());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeUpdate);
        Prescription testPrescription = prescriptionList.get(prescriptionList.size() - 1);
        assertThat(testPrescription.isClinicAdministration()).isEqualTo(UPDATED_CLINIC_ADMINISTRATION);
        assertThat(testPrescription.isAntiInflammatoryDrug()).isEqualTo(UPDATED_ANTI_INFLAMMATORY_DRUG);
        assertThat(testPrescription.isPain()).isEqualTo(UPDATED_PAIN);
        assertThat(testPrescription.isTakenAll()).isEqualTo(UPDATED_TAKEN_ALL);
        assertThat(testPrescription.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPrescription() throws Exception {
        int databaseSizeBeforeUpdate = prescriptionRepository.findAll().size();

        // Create the Prescription

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrescriptionMockMvc.perform(put("/api/prescriptions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prescription)))
            .andExpect(status().isBadRequest());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrescription() throws Exception {
        // Initialize the database
        prescriptionService.save(prescription);

        int databaseSizeBeforeDelete = prescriptionRepository.findAll().size();

        // Get the prescription
        restPrescriptionMockMvc.perform(delete("/api/prescriptions/{id}", prescription.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prescription.class);
        Prescription prescription1 = new Prescription();
        prescription1.setId(1L);
        Prescription prescription2 = new Prescription();
        prescription2.setId(prescription1.getId());
        assertThat(prescription1).isEqualTo(prescription2);
        prescription2.setId(2L);
        assertThat(prescription1).isNotEqualTo(prescription2);
        prescription1.setId(null);
        assertThat(prescription1).isNotEqualTo(prescription2);
    }
}
