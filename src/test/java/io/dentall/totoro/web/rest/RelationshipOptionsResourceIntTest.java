package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.RelationshipOptions;
import io.dentall.totoro.repository.RelationshipOptionsRepository;
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
 * Test class for the RelationshipOptionsResource REST controller.
 *
 * @see RelationshipOptionsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class RelationshipOptionsResourceIntTest {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    @Autowired
    private RelationshipOptionsRepository relationshipOptionsRepository;

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

    private MockMvc restRelationshipOptionsMockMvc;

    private RelationshipOptions relationshipOptions;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RelationshipOptionsResource relationshipOptionsResource = new RelationshipOptionsResource(relationshipOptionsRepository);
        this.restRelationshipOptionsMockMvc = MockMvcBuilders.standaloneSetup(relationshipOptionsResource)
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
    public static RelationshipOptions createEntity(EntityManager em) {
        RelationshipOptions relationshipOptions = new RelationshipOptions()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .order(DEFAULT_ORDER);
        return relationshipOptions;
    }

    @Before
    public void initTest() {
        relationshipOptions = createEntity(em);
    }

    @Test
    @Transactional
    public void createRelationshipOptions() throws Exception {
        int databaseSizeBeforeCreate = relationshipOptionsRepository.findAll().size();

        // Create the RelationshipOptions
        restRelationshipOptionsMockMvc.perform(post("/api/relationship-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipOptions)))
            .andExpect(status().isCreated());

        // Validate the RelationshipOptions in the database
        List<RelationshipOptions> relationshipOptionsList = relationshipOptionsRepository.findAll();
        assertThat(relationshipOptionsList).hasSize(databaseSizeBeforeCreate + 1);
        RelationshipOptions testRelationshipOptions = relationshipOptionsList.get(relationshipOptionsList.size() - 1);
        assertThat(testRelationshipOptions.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testRelationshipOptions.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testRelationshipOptions.getOrder()).isEqualTo(DEFAULT_ORDER);
    }

    @Test
    @Transactional
    public void createRelationshipOptionsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = relationshipOptionsRepository.findAll().size();

        // Create the RelationshipOptions with an existing ID
        relationshipOptions.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRelationshipOptionsMockMvc.perform(post("/api/relationship-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipOptions)))
            .andExpect(status().isBadRequest());

        // Validate the RelationshipOptions in the database
        List<RelationshipOptions> relationshipOptionsList = relationshipOptionsRepository.findAll();
        assertThat(relationshipOptionsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRelationshipOptions() throws Exception {
        // Initialize the database
        relationshipOptionsRepository.saveAndFlush(relationshipOptions);

        // Get all the relationshipOptionsList
        restRelationshipOptionsMockMvc.perform(get("/api/relationship-options?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(relationshipOptions.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
    }
    
    @Test
    @Transactional
    public void getRelationshipOptions() throws Exception {
        // Initialize the database
        relationshipOptionsRepository.saveAndFlush(relationshipOptions);

        // Get the relationshipOptions
        restRelationshipOptionsMockMvc.perform(get("/api/relationship-options/{id}", relationshipOptions.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(relationshipOptions.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
    }

    @Test
    @Transactional
    public void getNonExistingRelationshipOptions() throws Exception {
        // Get the relationshipOptions
        restRelationshipOptionsMockMvc.perform(get("/api/relationship-options/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRelationshipOptions() throws Exception {
        // Initialize the database
        relationshipOptionsRepository.saveAndFlush(relationshipOptions);

        int databaseSizeBeforeUpdate = relationshipOptionsRepository.findAll().size();

        // Update the relationshipOptions
        RelationshipOptions updatedRelationshipOptions = relationshipOptionsRepository.findById(relationshipOptions.getId()).get();
        // Disconnect from session so that the updates on updatedRelationshipOptions are not directly saved in db
        em.detach(updatedRelationshipOptions);
        updatedRelationshipOptions
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .order(UPDATED_ORDER);

        restRelationshipOptionsMockMvc.perform(put("/api/relationship-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRelationshipOptions)))
            .andExpect(status().isOk());

        // Validate the RelationshipOptions in the database
        List<RelationshipOptions> relationshipOptionsList = relationshipOptionsRepository.findAll();
        assertThat(relationshipOptionsList).hasSize(databaseSizeBeforeUpdate);
        RelationshipOptions testRelationshipOptions = relationshipOptionsList.get(relationshipOptionsList.size() - 1);
        assertThat(testRelationshipOptions.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testRelationshipOptions.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testRelationshipOptions.getOrder()).isEqualTo(UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void updateNonExistingRelationshipOptions() throws Exception {
        int databaseSizeBeforeUpdate = relationshipOptionsRepository.findAll().size();

        // Create the RelationshipOptions

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRelationshipOptionsMockMvc.perform(put("/api/relationship-options")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(relationshipOptions)))
            .andExpect(status().isBadRequest());

        // Validate the RelationshipOptions in the database
        List<RelationshipOptions> relationshipOptionsList = relationshipOptionsRepository.findAll();
        assertThat(relationshipOptionsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRelationshipOptions() throws Exception {
        // Initialize the database
        relationshipOptionsRepository.saveAndFlush(relationshipOptions);

        int databaseSizeBeforeDelete = relationshipOptionsRepository.findAll().size();

        // Get the relationshipOptions
        restRelationshipOptionsMockMvc.perform(delete("/api/relationship-options/{id}", relationshipOptions.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RelationshipOptions> relationshipOptionsList = relationshipOptionsRepository.findAll();
        assertThat(relationshipOptionsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RelationshipOptions.class);
        RelationshipOptions relationshipOptions1 = new RelationshipOptions();
        relationshipOptions1.setId(1L);
        RelationshipOptions relationshipOptions2 = new RelationshipOptions();
        relationshipOptions2.setId(relationshipOptions1.getId());
        assertThat(relationshipOptions1).isEqualTo(relationshipOptions2);
        relationshipOptions2.setId(2L);
        assertThat(relationshipOptions1).isNotEqualTo(relationshipOptions2);
        relationshipOptions1.setId(null);
        assertThat(relationshipOptions1).isNotEqualTo(relationshipOptions2);
    }
}
