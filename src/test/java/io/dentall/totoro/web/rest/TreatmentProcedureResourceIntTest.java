package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.TreatmentProcedureRepository;
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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TreatmentProcedureResource REST controller.
 *
 * @see TreatmentProcedureResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class TreatmentProcedureResourceIntTest {

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final String DEFAULT_TEETH = "AAAAAAAAAA";
    private static final String UPDATED_TEETH = "BBBBBBBBBB";

    private static final String DEFAULT_SURFACES = "AAAAAAAAAA";
    private static final String UPDATED_SURFACES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_NHI_DECLARED = false;
    private static final Boolean UPDATED_NHI_DECLARED = true;

    @Autowired
    private TreatmentProcedureRepository treatmentProcedureRepository;

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

    private MockMvc restTreatmentProcedureMockMvc;

    private TreatmentProcedure treatmentProcedure;

    private ExtendUser extendUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TreatmentProcedureResource treatmentProcedureResource = new TreatmentProcedureResource(treatmentProcedureRepository);
        this.restTreatmentProcedureMockMvc = MockMvcBuilders.standaloneSetup(treatmentProcedureResource)
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
    public static TreatmentProcedure createEntity(EntityManager em) {
        TreatmentProcedure treatmentProcedure = new TreatmentProcedure()
            .price(DEFAULT_PRICE)
            .teeth(DEFAULT_TEETH)
            .surfaces(DEFAULT_SURFACES)
            .nhiDeclared(DEFAULT_NHI_DECLARED);
        return treatmentProcedure;
    }

    @Before
    public void initTest() {
        User user = userRepository.save(UserResourceIntTest.createEntity(em));
        extendUser = user.getExtendUser();

        treatmentProcedure = createEntity(em);
        treatmentProcedure.setDoctor(extendUser);
    }

    @Test
    @Transactional
    public void createTreatmentProcedure() throws Exception {
        int databaseSizeBeforeCreate = treatmentProcedureRepository.findAll().size();

        // Create the TreatmentProcedure
        restTreatmentProcedureMockMvc.perform(post("/api/treatment-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentProcedure)))
            .andExpect(status().isCreated());

        // Validate the TreatmentProcedure in the database
        List<TreatmentProcedure> treatmentProcedureList = treatmentProcedureRepository.findAll();
        assertThat(treatmentProcedureList).hasSize(databaseSizeBeforeCreate + 1);
        TreatmentProcedure testTreatmentProcedure = treatmentProcedureList.get(treatmentProcedureList.size() - 1);
        assertThat(testTreatmentProcedure.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testTreatmentProcedure.getTeeth()).isEqualTo(DEFAULT_TEETH);
        assertThat(testTreatmentProcedure.getSurfaces()).isEqualTo(DEFAULT_SURFACES);
        assertThat(testTreatmentProcedure.isNhiDeclared()).isEqualTo(DEFAULT_NHI_DECLARED);
        assertThat(testTreatmentProcedure.getDoctor()).isEqualTo(extendUser);
    }

    @Test
    @Transactional
    public void createTreatmentProcedureWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = treatmentProcedureRepository.findAll().size();

        // Create the TreatmentProcedure with an existing ID
        treatmentProcedure.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTreatmentProcedureMockMvc.perform(post("/api/treatment-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentProcedure)))
            .andExpect(status().isBadRequest());

        // Validate the TreatmentProcedure in the database
        List<TreatmentProcedure> treatmentProcedureList = treatmentProcedureRepository.findAll();
        assertThat(treatmentProcedureList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTreatmentProcedures() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get all the treatmentProcedureList
        restTreatmentProcedureMockMvc.perform(get("/api/treatment-procedures?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(treatmentProcedure.getId().intValue())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].teeth").value(hasItem(DEFAULT_TEETH.toString())))
            .andExpect(jsonPath("$.[*].surfaces").value(hasItem(DEFAULT_SURFACES.toString())))
            .andExpect(jsonPath("$.[*].nhiDeclared").value(hasItem(DEFAULT_NHI_DECLARED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getTreatmentProcedure() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        // Get the treatmentProcedure
        restTreatmentProcedureMockMvc.perform(get("/api/treatment-procedures/{id}", treatmentProcedure.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(treatmentProcedure.getId().intValue()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.teeth").value(DEFAULT_TEETH.toString()))
            .andExpect(jsonPath("$.surfaces").value(DEFAULT_SURFACES.toString()))
            .andExpect(jsonPath("$.nhiDeclared").value(DEFAULT_NHI_DECLARED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTreatmentProcedure() throws Exception {
        // Get the treatmentProcedure
        restTreatmentProcedureMockMvc.perform(get("/api/treatment-procedures/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTreatmentProcedure() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        int databaseSizeBeforeUpdate = treatmentProcedureRepository.findAll().size();

        // Update the treatmentProcedure
        TreatmentProcedure updatedTreatmentProcedure = treatmentProcedureRepository.findById(treatmentProcedure.getId()).get();
        // Disconnect from session so that the updates on updatedTreatmentProcedure are not directly saved in db
        em.detach(updatedTreatmentProcedure);
        updatedTreatmentProcedure
            .price(UPDATED_PRICE)
            .teeth(UPDATED_TEETH)
            .surfaces(UPDATED_SURFACES)
            .nhiDeclared(UPDATED_NHI_DECLARED);

        restTreatmentProcedureMockMvc.perform(put("/api/treatment-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTreatmentProcedure)))
            .andExpect(status().isOk());

        // Validate the TreatmentProcedure in the database
        List<TreatmentProcedure> treatmentProcedureList = treatmentProcedureRepository.findAll();
        assertThat(treatmentProcedureList).hasSize(databaseSizeBeforeUpdate);
        TreatmentProcedure testTreatmentProcedure = treatmentProcedureList.get(treatmentProcedureList.size() - 1);
        assertThat(testTreatmentProcedure.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testTreatmentProcedure.getTeeth()).isEqualTo(UPDATED_TEETH);
        assertThat(testTreatmentProcedure.getSurfaces()).isEqualTo(UPDATED_SURFACES);
        assertThat(testTreatmentProcedure.isNhiDeclared()).isEqualTo(UPDATED_NHI_DECLARED);
    }

    @Test
    @Transactional
    public void updateNonExistingTreatmentProcedure() throws Exception {
        int databaseSizeBeforeUpdate = treatmentProcedureRepository.findAll().size();

        // Create the TreatmentProcedure

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTreatmentProcedureMockMvc.perform(put("/api/treatment-procedures")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(treatmentProcedure)))
            .andExpect(status().isBadRequest());

        // Validate the TreatmentProcedure in the database
        List<TreatmentProcedure> treatmentProcedureList = treatmentProcedureRepository.findAll();
        assertThat(treatmentProcedureList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTreatmentProcedure() throws Exception {
        // Initialize the database
        treatmentProcedureRepository.saveAndFlush(treatmentProcedure);

        int databaseSizeBeforeDelete = treatmentProcedureRepository.findAll().size();

        // Get the treatmentProcedure
        restTreatmentProcedureMockMvc.perform(delete("/api/treatment-procedures/{id}", treatmentProcedure.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TreatmentProcedure> treatmentProcedureList = treatmentProcedureRepository.findAll();
        assertThat(treatmentProcedureList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TreatmentProcedure.class);
        TreatmentProcedure treatmentProcedure1 = new TreatmentProcedure();
        treatmentProcedure1.setId(1L);
        TreatmentProcedure treatmentProcedure2 = new TreatmentProcedure();
        treatmentProcedure2.setId(treatmentProcedure1.getId());
        assertThat(treatmentProcedure1).isEqualTo(treatmentProcedure2);
        treatmentProcedure2.setId(2L);
        assertThat(treatmentProcedure1).isNotEqualTo(treatmentProcedure2);
        treatmentProcedure1.setId(null);
        assertThat(treatmentProcedure1).isNotEqualTo(treatmentProcedure2);
    }
}
