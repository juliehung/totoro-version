package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.CareerOptions;
import io.dentall.totoro.repository.CareerOptionsRepository;
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
 * Test class for the CareerOptionsResource REST controller.
 *
 * @see CareerOptionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class CareerOptionsResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    @Autowired
    private CareerOptionsRepository careerOptionsRepository;

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

    private MockMvc restCareerOptionsMockMvc;

    private CareerOptions careerOptions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CareerOptionsResource careerOptionsResource = new CareerOptionsResource(careerOptionsRepository);
        this.restCareerOptionsMockMvc = MockMvcBuilders.standaloneSetup(careerOptionsResource)
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
    public static CareerOptions createEntity(EntityManager em) {
        CareerOptions careerOptions = new CareerOptions()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .order(DEFAULT_ORDER);
        return careerOptions;
    }

    @Before
    public void initTest() {
        careerOptions = createEntity(em);
    }

    @Test
    @Transactional
    public void createCareerOptions() throws Exception {
        int databaseSizeBeforeCreate = careerOptionsRepository.findAll().size();

        // Create the CareerOptions
        restCareerOptionsMockMvc.perform(post("/api/career-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerOptions)))
            .andExpect(status().isCreated());

        // Validate the CareerOptions in the database
        List<CareerOptions> careerOptionsList = careerOptionsRepository.findAll();
        assertThat(careerOptionsList).hasSize(databaseSizeBeforeCreate + 1);
        CareerOptions testCareerOptions = careerOptionsList.get(careerOptionsList.size() - 1);
        assertThat(testCareerOptions.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCareerOptions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCareerOptions.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createCareerOptionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = careerOptionsRepository.findAll().size();

        // Create the CareerOptions with an existing ID
        careerOptions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCareerOptionsMockMvc.perform(post("/api/career-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerOptions)))
            .andExpect(status().isBadRequest());

        // Validate the CareerOptions in the database
        List<CareerOptions> careerOptionsList = careerOptionsRepository.findAll();
        assertThat(careerOptionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCareerOptions() throws Exception {
        // Initialize the database
        careerOptionsRepository.saveAndFlush(careerOptions);

        // Get all the careerOptionsList
        restCareerOptionsMockMvc.perform(get("/api/career-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(careerOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }
    
    @Test
    @Transactional
    public void getCareerOptions() throws Exception {
        // Initialize the database
        careerOptionsRepository.saveAndFlush(careerOptions);

        // Get the careerOptions
        restCareerOptionsMockMvc.perform(get("/api/career-options/{id}", careerOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(careerOptions.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingCareerOptions() throws Exception {
        // Get the careerOptions
        restCareerOptionsMockMvc.perform(get("/api/career-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCareerOptions() throws Exception {
        // Initialize the database
        careerOptionsRepository.saveAndFlush(careerOptions);

        int databaseSizeBeforeUpdate = careerOptionsRepository.findAll().size();

        // Update the careerOptions
        CareerOptions updatedCareerOptions = careerOptionsRepository.findById(careerOptions.getId()).get();
        // Disconnect from session so that the updates on updatedCareerOptions are not directly saved in db
        em.detach(updatedCareerOptions);
        updatedCareerOptions
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER);

        restCareerOptionsMockMvc.perform(put("/api/career-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCareerOptions)))
            .andExpect(status().isOk());

        // Validate the CareerOptions in the database
        List<CareerOptions> careerOptionsList = careerOptionsRepository.findAll();
        assertThat(careerOptionsList).hasSize(databaseSizeBeforeUpdate);
        CareerOptions testCareerOptions = careerOptionsList.get(careerOptionsList.size() - 1);
        assertThat(testCareerOptions.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCareerOptions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCareerOptions.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingCareerOptions() throws Exception {
        int databaseSizeBeforeUpdate = careerOptionsRepository.findAll().size();

        // Create the CareerOptions

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCareerOptionsMockMvc.perform(put("/api/career-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(careerOptions)))
            .andExpect(status().isBadRequest());

        // Validate the CareerOptions in the database
        List<CareerOptions> careerOptionsList = careerOptionsRepository.findAll();
        assertThat(careerOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCareerOptions() throws Exception {
        // Initialize the database
        careerOptionsRepository.saveAndFlush(careerOptions);

        int databaseSizeBeforeDelete = careerOptionsRepository.findAll().size();

        // Get the careerOptions
        restCareerOptionsMockMvc.perform(delete("/api/career-options/{id}", careerOptions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CareerOptions> careerOptionsList = careerOptionsRepository.findAll();
        assertThat(careerOptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CareerOptions.class);
        CareerOptions careerOptions1 = new CareerOptions();
        careerOptions1.setId(1L);
        CareerOptions careerOptions2 = new CareerOptions();
        careerOptions2.setId(careerOptions1.getId());
        assertThat(careerOptions1).isEqualTo(careerOptions2);
        careerOptions2.setId(2L);
        assertThat(careerOptions1).isNotEqualTo(careerOptions2);
        careerOptions1.setId(null);
        assertThat(careerOptions1).isNotEqualTo(careerOptions2);
    }
}
