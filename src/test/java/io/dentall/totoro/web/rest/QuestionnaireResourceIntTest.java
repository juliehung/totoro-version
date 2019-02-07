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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the QuestionnaireResource REST controller.
 *
 * @see QuestionnaireResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class QuestionnaireResourceIntTest {

    private static final Boolean DEFAULT_DRUG = false;
    private static final Boolean UPDATED_DRUG = true;

    private static final String DEFAULT_DRUG_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DRUG_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_GLYCEMIC_AC = 1;
    private static final Integer UPDATED_GLYCEMIC_AC = 2;

    private static final Integer DEFAULT_GLYCEMIC_PC = 1;
    private static final Integer UPDATED_GLYCEMIC_PC = 2;

    private static final Integer DEFAULT_SMOKE_NUMBER_A_DAY = 1;
    private static final Integer UPDATED_SMOKE_NUMBER_A_DAY = 2;

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

    @Autowired
    private Validator validator;

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
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Questionnaire createEntity(EntityManager em) {
        Questionnaire questionnaire = new Questionnaire()
            .drug(DEFAULT_DRUG)
            .drugName(DEFAULT_DRUG_NAME)
            .glycemicAC(DEFAULT_GLYCEMIC_AC)
            .glycemicPC(DEFAULT_GLYCEMIC_PC)
            .smokeNumberADay(DEFAULT_SMOKE_NUMBER_A_DAY)
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
        assertThat(testQuestionnaire.isDrug()).isEqualTo(DEFAULT_DRUG);
        assertThat(testQuestionnaire.getDrugName()).isEqualTo(DEFAULT_DRUG_NAME);
        assertThat(testQuestionnaire.getGlycemicAC()).isEqualTo(DEFAULT_GLYCEMIC_AC);
        assertThat(testQuestionnaire.getGlycemicPC()).isEqualTo(DEFAULT_GLYCEMIC_PC);
        assertThat(testQuestionnaire.getSmokeNumberADay()).isEqualTo(DEFAULT_SMOKE_NUMBER_A_DAY);
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
            .andExpect(jsonPath("$.[*].drug").value(hasItem(DEFAULT_DRUG.booleanValue())))
            .andExpect(jsonPath("$.[*].drugName").value(hasItem(DEFAULT_DRUG_NAME.toString())))
            .andExpect(jsonPath("$.[*].glycemicAC").value(hasItem(DEFAULT_GLYCEMIC_AC)))
            .andExpect(jsonPath("$.[*].glycemicPC").value(hasItem(DEFAULT_GLYCEMIC_PC)))
            .andExpect(jsonPath("$.[*].smokeNumberADay").value(hasItem(DEFAULT_SMOKE_NUMBER_A_DAY)))
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
            .andExpect(jsonPath("$.drug").value(DEFAULT_DRUG.booleanValue()))
            .andExpect(jsonPath("$.drugName").value(DEFAULT_DRUG_NAME.toString()))
            .andExpect(jsonPath("$.glycemicAC").value(DEFAULT_GLYCEMIC_AC))
            .andExpect(jsonPath("$.glycemicPC").value(DEFAULT_GLYCEMIC_PC))
            .andExpect(jsonPath("$.smokeNumberADay").value(DEFAULT_SMOKE_NUMBER_A_DAY))
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
            .drug(UPDATED_DRUG)
            .drugName(UPDATED_DRUG_NAME)
            .glycemicAC(UPDATED_GLYCEMIC_AC)
            .glycemicPC(UPDATED_GLYCEMIC_PC)
            .smokeNumberADay(UPDATED_SMOKE_NUMBER_A_DAY)
            .otherInTreatment(UPDATED_OTHER_IN_TREATMENT);

        restQuestionnaireMockMvc.perform(put("/api/questionnaires")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuestionnaire)))
            .andExpect(status().isOk());

        // Validate the Questionnaire in the database
        List<Questionnaire> questionnaireList = questionnaireRepository.findAll();
        assertThat(questionnaireList).hasSize(databaseSizeBeforeUpdate);
        Questionnaire testQuestionnaire = questionnaireList.get(questionnaireList.size() - 1);
        assertThat(testQuestionnaire.isDrug()).isEqualTo(UPDATED_DRUG);
        assertThat(testQuestionnaire.getDrugName()).isEqualTo(UPDATED_DRUG_NAME);
        assertThat(testQuestionnaire.getGlycemicAC()).isEqualTo(UPDATED_GLYCEMIC_AC);
        assertThat(testQuestionnaire.getGlycemicPC()).isEqualTo(UPDATED_GLYCEMIC_PC);
        assertThat(testQuestionnaire.getSmokeNumberADay()).isEqualTo(UPDATED_SMOKE_NUMBER_A_DAY);
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
