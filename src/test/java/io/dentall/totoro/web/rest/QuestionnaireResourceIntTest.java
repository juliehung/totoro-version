package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.domain.Questionnaire;
import io.dentall.totoro.repository.QuestionnaireRepository;
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
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.dentall.totoro.domain.enumeration.Hepatitis;
import io.dentall.totoro.domain.enumeration.Month;
/**
 * Test class for the QuestionnaireResource REST controller.
 *
 * @see QuestionnaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class QuestionnaireResourceIntTest {

    private static final String DEFAULT_HYPERTENSION = "AAAAAAAAAA";
    private static final String UPDATED_HYPERTENSION = "BBBBBBBBBB";

    private static final String DEFAULT_HEART_DISEASES = "AAAAAAAAAA";
    private static final String UPDATED_HEART_DISEASES = "BBBBBBBBBB";

    private static final String DEFAULT_KIDNEY_DISEASES = "AAAAAAAAAA";
    private static final String UPDATED_KIDNEY_DISEASES = "BBBBBBBBBB";

    private static final String DEFAULT_BLOOD_DISEASES = "AAAAAAAAAA";
    private static final String UPDATED_BLOOD_DISEASES = "BBBBBBBBBB";

    private static final String DEFAULT_LIVER_DISEASES = "AAAAAAAAAA";
    private static final String UPDATED_LIVER_DISEASES = "BBBBBBBBBB";

    private static final Hepatitis DEFAULT_HEPATITIS_TYPE = Hepatitis.A;
    private static final Hepatitis UPDATED_HEPATITIS_TYPE = Hepatitis.B;

    private static final String DEFAULT_GASTROINTESTINAL_DISEASES = "AAAAAAAAAA";
    private static final String UPDATED_GASTROINTESTINAL_DISEASES = "BBBBBBBBBB";

    private static final String DEFAULT_RECEIVING_MEDICATION = "AAAAAAAAAA";
    private static final String UPDATED_RECEIVING_MEDICATION = "BBBBBBBBBB";

    private static final String DEFAULT_ANY_ALLERGY_SENSITIVITY = "AAAAAAAAAA";
    private static final String UPDATED_ANY_ALLERGY_SENSITIVITY = "BBBBBBBBBB";

    private static final Integer DEFAULT_GLYCEMIC_AC = 1;
    private static final Integer UPDATED_GLYCEMIC_AC = 2;

    private static final Integer DEFAULT_GLYCEMIC_PC = 1;
    private static final Integer UPDATED_GLYCEMIC_PC = 2;

    private static final Integer DEFAULT_SMOKE_NUMBER_A_DAY = 1;
    private static final Integer UPDATED_SMOKE_NUMBER_A_DAY = 2;

    private static final Integer DEFAULT_PRODUCTION_YEAR = 1;
    private static final Integer UPDATED_PRODUCTION_YEAR = 2;

    private static final Month DEFAULT_PRODUCTION_MONTH = Month.JAN;
    private static final Month UPDATED_PRODUCTION_MONTH = Month.FEB;

    private static final String DEFAULT_OTHER = "AAAAAAAAAA";
    private static final String UPDATED_OTHER = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DIFFICULT_EXTRACTION_OR_CONTINUOUS_BLEEDING = false;
    private static final Boolean UPDATED_DIFFICULT_EXTRACTION_OR_CONTINUOUS_BLEEDING = true;

    private static final Boolean DEFAULT_NAUSEA_OR_DIZZINESS = false;
    private static final Boolean UPDATED_NAUSEA_OR_DIZZINESS = true;

    private static final Boolean DEFAULT_ADVERSE_REACTIONS_TO_ANESTHETIC_INJECTIONS = false;
    private static final Boolean UPDATED_ADVERSE_REACTIONS_TO_ANESTHETIC_INJECTIONS = true;

    private static final String DEFAULT_OTHER_IN_TREATMENT = "AAAAAAAAAA";
    private static final String UPDATED_OTHER_IN_TREATMENT = "BBBBBBBBBB";

    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restQuestionnaireMockMvc;

    private Questionnaire questionnaire;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final QuestionnaireResource questionnaireResource = new QuestionnaireResource(questionnaireRepository);
        this.restQuestionnaireMockMvc = MockMvcBuilders.standaloneSetup(questionnaireResource)
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
    public static Questionnaire createEntity(EntityManager em) {
        Questionnaire questionnaire = new Questionnaire()
            .hypertension(DEFAULT_HYPERTENSION)
            .heartDiseases(DEFAULT_HEART_DISEASES)
            .kidneyDiseases(DEFAULT_KIDNEY_DISEASES)
            .bloodDiseases(DEFAULT_BLOOD_DISEASES)
            .liverDiseases(DEFAULT_LIVER_DISEASES)
            .hepatitisType(DEFAULT_HEPATITIS_TYPE)
            .gastrointestinalDiseases(DEFAULT_GASTROINTESTINAL_DISEASES)
            .receivingMedication(DEFAULT_RECEIVING_MEDICATION)
            .anyAllergySensitivity(DEFAULT_ANY_ALLERGY_SENSITIVITY)
            .glycemicAC(DEFAULT_GLYCEMIC_AC)
            .glycemicPC(DEFAULT_GLYCEMIC_PC)
            .smokeNumberADay(DEFAULT_SMOKE_NUMBER_A_DAY)
            .productionYear(DEFAULT_PRODUCTION_YEAR)
            .productionMonth(DEFAULT_PRODUCTION_MONTH)
            .other(DEFAULT_OTHER)
            .difficultExtractionOrContinuousBleeding(DEFAULT_DIFFICULT_EXTRACTION_OR_CONTINUOUS_BLEEDING)
            .nauseaOrDizziness(DEFAULT_NAUSEA_OR_DIZZINESS)
            .adverseReactionsToAnestheticInjections(DEFAULT_ADVERSE_REACTIONS_TO_ANESTHETIC_INJECTIONS)
            .otherInTreatment(DEFAULT_OTHER_IN_TREATMENT);
        return questionnaire;
    }

    @Before
    public void initTest() {
        questionnaire = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuestionnaire() throws Exception {
        int databaseSizeBeforeCreate = questionnaireRepository.findAll().size();

        // Create the Questionnaire
        restQuestionnaireMockMvc.perform(post("/api/questionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionnaire)))
            .andExpect(status().isCreated());

        // Validate the Questionnaire in the database
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeCreate + 1);
        Questionnaire testQuestionnaire = questionnaireList.get(questionnaireList.size() - 1);
        assertThat(testQuestionnaire.getHypertension()).isEqualTo(DEFAULT_HYPERTENSION);
        assertThat(testQuestionnaire.getHeartDiseases()).isEqualTo(DEFAULT_HEART_DISEASES);
        assertThat(testQuestionnaire.getKidneyDiseases()).isEqualTo(DEFAULT_KIDNEY_DISEASES);
        assertThat(testQuestionnaire.getBloodDiseases()).isEqualTo(DEFAULT_BLOOD_DISEASES);
        assertThat(testQuestionnaire.getLiverDiseases()).isEqualTo(DEFAULT_LIVER_DISEASES);
        assertThat(testQuestionnaire.getHepatitisType()).isEqualTo(DEFAULT_HEPATITIS_TYPE);
        assertThat(testQuestionnaire.getGastrointestinalDiseases()).isEqualTo(DEFAULT_GASTROINTESTINAL_DISEASES);
        assertThat(testQuestionnaire.getReceivingMedication()).isEqualTo(DEFAULT_RECEIVING_MEDICATION);
        assertThat(testQuestionnaire.getAnyAllergySensitivity()).isEqualTo(DEFAULT_ANY_ALLERGY_SENSITIVITY);
        assertThat(testQuestionnaire.getGlycemicAC()).isEqualTo(DEFAULT_GLYCEMIC_AC);
        assertThat(testQuestionnaire.getGlycemicPC()).isEqualTo(DEFAULT_GLYCEMIC_PC);
        assertThat(testQuestionnaire.getSmokeNumberADay()).isEqualTo(DEFAULT_SMOKE_NUMBER_A_DAY);
        assertThat(testQuestionnaire.getProductionYear()).isEqualTo(DEFAULT_PRODUCTION_YEAR);
        assertThat(testQuestionnaire.getProductionMonth()).isEqualTo(DEFAULT_PRODUCTION_MONTH);
        assertThat(testQuestionnaire.getOther()).isEqualTo(DEFAULT_OTHER);
        assertThat(testQuestionnaire.isDifficultExtractionOrContinuousBleeding()).isEqualTo(DEFAULT_DIFFICULT_EXTRACTION_OR_CONTINUOUS_BLEEDING);
        assertThat(testQuestionnaire.isNauseaOrDizziness()).isEqualTo(DEFAULT_NAUSEA_OR_DIZZINESS);
        assertThat(testQuestionnaire.isAdverseReactionsToAnestheticInjections()).isEqualTo(DEFAULT_ADVERSE_REACTIONS_TO_ANESTHETIC_INJECTIONS);
        assertThat(testQuestionnaire.getOtherInTreatment()).isEqualTo(DEFAULT_OTHER_IN_TREATMENT);
    }

    @Test
    @Transactional
    public void createQuestionnaireWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = questionnaireRepository.findAll().size();

        // Create the Questionnaire with an existing ID
        questionnaire.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQuestionnaireMockMvc.perform(post("/api/questionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionnaire)))
            .andExpect(status().isBadRequest());

        // Validate the Questionnaire in the database
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllQuestionnaires() throws Exception {
        // Initialize the database
        questionnaireRepository.saveAndFlush(questionnaire);

        // Get all the questionnaireList
        restQuestionnaireMockMvc.perform(get("/api/questionnaires?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(questionnaire.getId().intValue())))
            .andExpect(jsonPath("$.[*].hypertension").value(hasItem(DEFAULT_HYPERTENSION.toString())))
            .andExpect(jsonPath("$.[*].heartDiseases").value(hasItem(DEFAULT_HEART_DISEASES.toString())))
            .andExpect(jsonPath("$.[*].kidneyDiseases").value(hasItem(DEFAULT_KIDNEY_DISEASES.toString())))
            .andExpect(jsonPath("$.[*].bloodDiseases").value(hasItem(DEFAULT_BLOOD_DISEASES.toString())))
            .andExpect(jsonPath("$.[*].liverDiseases").value(hasItem(DEFAULT_LIVER_DISEASES.toString())))
            .andExpect(jsonPath("$.[*].hepatitisType").value(hasItem(DEFAULT_HEPATITIS_TYPE.toString())))
            .andExpect(jsonPath("$.[*].gastrointestinalDiseases").value(hasItem(DEFAULT_GASTROINTESTINAL_DISEASES.toString())))
            .andExpect(jsonPath("$.[*].receivingMedication").value(hasItem(DEFAULT_RECEIVING_MEDICATION.toString())))
            .andExpect(jsonPath("$.[*].anyAllergySensitivity").value(hasItem(DEFAULT_ANY_ALLERGY_SENSITIVITY.toString())))
            .andExpect(jsonPath("$.[*].glycemicAC").value(hasItem(DEFAULT_GLYCEMIC_AC)))
            .andExpect(jsonPath("$.[*].glycemicPC").value(hasItem(DEFAULT_GLYCEMIC_PC)))
            .andExpect(jsonPath("$.[*].smokeNumberADay").value(hasItem(DEFAULT_SMOKE_NUMBER_A_DAY)))
            .andExpect(jsonPath("$.[*].productionYear").value(hasItem(DEFAULT_PRODUCTION_YEAR)))
            .andExpect(jsonPath("$.[*].productionMonth").value(hasItem(DEFAULT_PRODUCTION_MONTH.toString())))
            .andExpect(jsonPath("$.[*].other").value(hasItem(DEFAULT_OTHER.toString())))
            .andExpect(jsonPath("$.[*].difficultExtractionOrContinuousBleeding").value(hasItem(DEFAULT_DIFFICULT_EXTRACTION_OR_CONTINUOUS_BLEEDING.booleanValue())))
            .andExpect(jsonPath("$.[*].nauseaOrDizziness").value(hasItem(DEFAULT_NAUSEA_OR_DIZZINESS.booleanValue())))
            .andExpect(jsonPath("$.[*].adverseReactionsToAnestheticInjections").value(hasItem(DEFAULT_ADVERSE_REACTIONS_TO_ANESTHETIC_INJECTIONS.booleanValue())))
            .andExpect(jsonPath("$.[*].otherInTreatment").value(hasItem(DEFAULT_OTHER_IN_TREATMENT.toString())));
    }
    
    @Test
    @Transactional
    public void getQuestionnaire() throws Exception {
        // Initialize the database
        questionnaireRepository.saveAndFlush(questionnaire);

        // Get the questionnaire
        restQuestionnaireMockMvc.perform(get("/api/questionnaires/{id}", questionnaire.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(questionnaire.getId().intValue()))
            .andExpect(jsonPath("$.hypertension").value(DEFAULT_HYPERTENSION.toString()))
            .andExpect(jsonPath("$.heartDiseases").value(DEFAULT_HEART_DISEASES.toString()))
            .andExpect(jsonPath("$.kidneyDiseases").value(DEFAULT_KIDNEY_DISEASES.toString()))
            .andExpect(jsonPath("$.bloodDiseases").value(DEFAULT_BLOOD_DISEASES.toString()))
            .andExpect(jsonPath("$.liverDiseases").value(DEFAULT_LIVER_DISEASES.toString()))
            .andExpect(jsonPath("$.hepatitisType").value(DEFAULT_HEPATITIS_TYPE.toString()))
            .andExpect(jsonPath("$.gastrointestinalDiseases").value(DEFAULT_GASTROINTESTINAL_DISEASES.toString()))
            .andExpect(jsonPath("$.receivingMedication").value(DEFAULT_RECEIVING_MEDICATION.toString()))
            .andExpect(jsonPath("$.anyAllergySensitivity").value(DEFAULT_ANY_ALLERGY_SENSITIVITY.toString()))
            .andExpect(jsonPath("$.glycemicAC").value(DEFAULT_GLYCEMIC_AC))
            .andExpect(jsonPath("$.glycemicPC").value(DEFAULT_GLYCEMIC_PC))
            .andExpect(jsonPath("$.smokeNumberADay").value(DEFAULT_SMOKE_NUMBER_A_DAY))
            .andExpect(jsonPath("$.productionYear").value(DEFAULT_PRODUCTION_YEAR))
            .andExpect(jsonPath("$.productionMonth").value(DEFAULT_PRODUCTION_MONTH.toString()))
            .andExpect(jsonPath("$.other").value(DEFAULT_OTHER.toString()))
            .andExpect(jsonPath("$.difficultExtractionOrContinuousBleeding").value(DEFAULT_DIFFICULT_EXTRACTION_OR_CONTINUOUS_BLEEDING.booleanValue()))
            .andExpect(jsonPath("$.nauseaOrDizziness").value(DEFAULT_NAUSEA_OR_DIZZINESS.booleanValue()))
            .andExpect(jsonPath("$.adverseReactionsToAnestheticInjections").value(DEFAULT_ADVERSE_REACTIONS_TO_ANESTHETIC_INJECTIONS.booleanValue()))
            .andExpect(jsonPath("$.otherInTreatment").value(DEFAULT_OTHER_IN_TREATMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingQuestionnaire() throws Exception {
        // Get the questionnaire
        restQuestionnaireMockMvc.perform(get("/api/questionnaires/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuestionnaire() throws Exception {
        // Initialize the database
        questionnaireRepository.saveAndFlush(questionnaire);

        int databaseSizeBeforeUpdate = questionnaireRepository.findAll().size();

        // Update the questionnaire
        Questionnaire updatedQuestionnaire = questionnaireRepository.findById(questionnaire.getId()).get();
        // Disconnect from session so that the updates on updatedQuestionnaire are not directly saved in db
        em.detach(updatedQuestionnaire);
        updatedQuestionnaire
            .hypertension(UPDATED_HYPERTENSION)
            .heartDiseases(UPDATED_HEART_DISEASES)
            .kidneyDiseases(UPDATED_KIDNEY_DISEASES)
            .bloodDiseases(UPDATED_BLOOD_DISEASES)
            .liverDiseases(UPDATED_LIVER_DISEASES)
            .hepatitisType(UPDATED_HEPATITIS_TYPE)
            .gastrointestinalDiseases(UPDATED_GASTROINTESTINAL_DISEASES)
            .receivingMedication(UPDATED_RECEIVING_MEDICATION)
            .anyAllergySensitivity(UPDATED_ANY_ALLERGY_SENSITIVITY)
            .glycemicAC(UPDATED_GLYCEMIC_AC)
            .glycemicPC(UPDATED_GLYCEMIC_PC)
            .smokeNumberADay(UPDATED_SMOKE_NUMBER_A_DAY)
            .productionYear(UPDATED_PRODUCTION_YEAR)
            .productionMonth(UPDATED_PRODUCTION_MONTH)
            .other(UPDATED_OTHER)
            .difficultExtractionOrContinuousBleeding(UPDATED_DIFFICULT_EXTRACTION_OR_CONTINUOUS_BLEEDING)
            .nauseaOrDizziness(UPDATED_NAUSEA_OR_DIZZINESS)
            .adverseReactionsToAnestheticInjections(UPDATED_ADVERSE_REACTIONS_TO_ANESTHETIC_INJECTIONS)
            .otherInTreatment(UPDATED_OTHER_IN_TREATMENT);

        restQuestionnaireMockMvc.perform(put("/api/questionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionnaire)))
            .andExpect(status().isOk());

        // Validate the Questionnaire in the database
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeUpdate);
        Questionnaire testQuestionnaire = questionnaireList.get(questionnaireList.size() - 1);
        assertThat(testQuestionnaire.getHypertension()).isEqualTo(UPDATED_HYPERTENSION);
        assertThat(testQuestionnaire.getHeartDiseases()).isEqualTo(UPDATED_HEART_DISEASES);
        assertThat(testQuestionnaire.getKidneyDiseases()).isEqualTo(UPDATED_KIDNEY_DISEASES);
        assertThat(testQuestionnaire.getBloodDiseases()).isEqualTo(UPDATED_BLOOD_DISEASES);
        assertThat(testQuestionnaire.getLiverDiseases()).isEqualTo(UPDATED_LIVER_DISEASES);
        assertThat(testQuestionnaire.getHepatitisType()).isEqualTo(UPDATED_HEPATITIS_TYPE);
        assertThat(testQuestionnaire.getGastrointestinalDiseases()).isEqualTo(UPDATED_GASTROINTESTINAL_DISEASES);
        assertThat(testQuestionnaire.getReceivingMedication()).isEqualTo(UPDATED_RECEIVING_MEDICATION);
        assertThat(testQuestionnaire.getAnyAllergySensitivity()).isEqualTo(UPDATED_ANY_ALLERGY_SENSITIVITY);
        assertThat(testQuestionnaire.getGlycemicAC()).isEqualTo(UPDATED_GLYCEMIC_AC);
        assertThat(testQuestionnaire.getGlycemicPC()).isEqualTo(UPDATED_GLYCEMIC_PC);
        assertThat(testQuestionnaire.getSmokeNumberADay()).isEqualTo(UPDATED_SMOKE_NUMBER_A_DAY);
        assertThat(testQuestionnaire.getProductionYear()).isEqualTo(UPDATED_PRODUCTION_YEAR);
        assertThat(testQuestionnaire.getProductionMonth()).isEqualTo(UPDATED_PRODUCTION_MONTH);
        assertThat(testQuestionnaire.getOther()).isEqualTo(UPDATED_OTHER);
        assertThat(testQuestionnaire.isDifficultExtractionOrContinuousBleeding()).isEqualTo(UPDATED_DIFFICULT_EXTRACTION_OR_CONTINUOUS_BLEEDING);
        assertThat(testQuestionnaire.isNauseaOrDizziness()).isEqualTo(UPDATED_NAUSEA_OR_DIZZINESS);
        assertThat(testQuestionnaire.isAdverseReactionsToAnestheticInjections()).isEqualTo(UPDATED_ADVERSE_REACTIONS_TO_ANESTHETIC_INJECTIONS);
        assertThat(testQuestionnaire.getOtherInTreatment()).isEqualTo(UPDATED_OTHER_IN_TREATMENT);
    }

    @Test
    @Transactional
    public void updateNonExistingQuestionnaire() throws Exception {
        int databaseSizeBeforeUpdate = questionnaireRepository.findAll().size();

        // Create the Questionnaire

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQuestionnaireMockMvc.perform(put("/api/questionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(questionnaire)))
            .andExpect(status().isBadRequest());

        // Validate the Questionnaire in the database
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuestionnaire() throws Exception {
        // Initialize the database
        questionnaireRepository.saveAndFlush(questionnaire);

        int databaseSizeBeforeDelete = questionnaireRepository.findAll().size();

        // Get the questionnaire
        restQuestionnaireMockMvc.perform(delete("/api/questionnaires/{id}", questionnaire.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Questionnaire.class);
        Questionnaire questionnaire1 = new Questionnaire();
        questionnaire1.setId(1L);
        Questionnaire questionnaire2 = new Questionnaire();
        questionnaire2.setId(questionnaire1.getId());
        assertThat(questionnaire1).isEqualTo(questionnaire2);
        questionnaire2.setId(2L);
        assertThat(questionnaire1).isNotEqualTo(questionnaire2);
        questionnaire1.setId(null);
        assertThat(questionnaire1).isNotEqualTo(questionnaire2);
    }
}
