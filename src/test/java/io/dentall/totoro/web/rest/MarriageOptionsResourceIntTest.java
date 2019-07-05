package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.MarriageOptions;
import io.dentall.totoro.repository.MarriageOptionsRepository;
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
 * Test class for the MarriageOptionsResource REST controller.
 *
 * @see MarriageOptionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class MarriageOptionsResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    @Autowired
    private MarriageOptionsRepository marriageOptionsRepository;

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

    private MockMvc restMarriageOptionsMockMvc;

    private MarriageOptions marriageOptions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MarriageOptionsResource marriageOptionsResource = new MarriageOptionsResource(marriageOptionsRepository);
        this.restMarriageOptionsMockMvc = MockMvcBuilders.standaloneSetup(marriageOptionsResource)
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
    public static MarriageOptions createEntity(EntityManager em) {
        MarriageOptions marriageOptions = new MarriageOptions()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .order(DEFAULT_ORDER);
        return marriageOptions;
    }

    @Before
    public void initTest() {
        marriageOptions = createEntity(em);
    }

    @Test
    @Transactional
    public void createMarriageOptions() throws Exception {
        int databaseSizeBeforeCreate = marriageOptionsRepository.findAll().size();

        // Create the MarriageOptions
        restMarriageOptionsMockMvc.perform(post("/api/marriage-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marriageOptions)))
            .andExpect(status().isCreated());

        // Validate the MarriageOptions in the database
        List<MarriageOptions> marriageOptionsList = marriageOptionsRepository.findAll();
        assertThat(marriageOptionsList).hasSize(databaseSizeBeforeCreate + 1);
        MarriageOptions testMarriageOptions = marriageOptionsList.get(marriageOptionsList.size() - 1);
        assertThat(testMarriageOptions.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testMarriageOptions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMarriageOptions.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createMarriageOptionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = marriageOptionsRepository.findAll().size();

        // Create the MarriageOptions with an existing ID
        marriageOptions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMarriageOptionsMockMvc.perform(post("/api/marriage-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marriageOptions)))
            .andExpect(status().isBadRequest());

        // Validate the MarriageOptions in the database
        List<MarriageOptions> marriageOptionsList = marriageOptionsRepository.findAll();
        assertThat(marriageOptionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMarriageOptions() throws Exception {
        // Initialize the database
        marriageOptionsRepository.saveAndFlush(marriageOptions);

        // Get all the marriageOptionsList
        restMarriageOptionsMockMvc.perform(get("/api/marriage-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(marriageOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }
    
    @Test
    @Transactional
    public void getMarriageOptions() throws Exception {
        // Initialize the database
        marriageOptionsRepository.saveAndFlush(marriageOptions);

        // Get the marriageOptions
        restMarriageOptionsMockMvc.perform(get("/api/marriage-options/{id}", marriageOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(marriageOptions.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingMarriageOptions() throws Exception {
        // Get the marriageOptions
        restMarriageOptionsMockMvc.perform(get("/api/marriage-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMarriageOptions() throws Exception {
        // Initialize the database
        marriageOptionsRepository.saveAndFlush(marriageOptions);

        int databaseSizeBeforeUpdate = marriageOptionsRepository.findAll().size();

        // Update the marriageOptions
        MarriageOptions updatedMarriageOptions = marriageOptionsRepository.findById(marriageOptions.getId()).get();
        // Disconnect from session so that the updates on updatedMarriageOptions are not directly saved in db
        em.detach(updatedMarriageOptions);
        updatedMarriageOptions
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER);

        restMarriageOptionsMockMvc.perform(put("/api/marriage-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMarriageOptions)))
            .andExpect(status().isOk());

        // Validate the MarriageOptions in the database
        List<MarriageOptions> marriageOptionsList = marriageOptionsRepository.findAll();
        assertThat(marriageOptionsList).hasSize(databaseSizeBeforeUpdate);
        MarriageOptions testMarriageOptions = marriageOptionsList.get(marriageOptionsList.size() - 1);
        assertThat(testMarriageOptions.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testMarriageOptions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMarriageOptions.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingMarriageOptions() throws Exception {
        int databaseSizeBeforeUpdate = marriageOptionsRepository.findAll().size();

        // Create the MarriageOptions

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMarriageOptionsMockMvc.perform(put("/api/marriage-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(marriageOptions)))
            .andExpect(status().isBadRequest());

        // Validate the MarriageOptions in the database
        List<MarriageOptions> marriageOptionsList = marriageOptionsRepository.findAll();
        assertThat(marriageOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMarriageOptions() throws Exception {
        // Initialize the database
        marriageOptionsRepository.saveAndFlush(marriageOptions);

        int databaseSizeBeforeDelete = marriageOptionsRepository.findAll().size();

        // Get the marriageOptions
        restMarriageOptionsMockMvc.perform(delete("/api/marriage-options/{id}", marriageOptions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<MarriageOptions> marriageOptionsList = marriageOptionsRepository.findAll();
        assertThat(marriageOptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MarriageOptions.class);
        MarriageOptions marriageOptions1 = new MarriageOptions();
        marriageOptions1.setId(1L);
        MarriageOptions marriageOptions2 = new MarriageOptions();
        marriageOptions2.setId(marriageOptions1.getId());
        assertThat(marriageOptions1).isEqualTo(marriageOptions2);
        marriageOptions2.setId(2L);
        assertThat(marriageOptions1).isNotEqualTo(marriageOptions2);
        marriageOptions1.setId(null);
        assertThat(marriageOptions1).isNotEqualTo(marriageOptions2);
    }
}
