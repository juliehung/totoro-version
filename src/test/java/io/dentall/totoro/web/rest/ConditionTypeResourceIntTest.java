package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.ConditionType;
import io.dentall.totoro.repository.ConditionTypeRepository;
import io.dentall.totoro.service.ConditionTypeService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.dto.ConditionTypeCriteria;
import io.dentall.totoro.service.ConditionTypeQueryService;

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
 * Test class for the ConditionTypeResource REST controller.
 *
 * @see ConditionTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class ConditionTypeResourceIntTest {

    private static final String DEFAULT_MAJOR = "AAAAAAAAAA";
    private static final String UPDATED_MAJOR = "BBBBBBBBBB";

    private static final String DEFAULT_MINOR = "AAAAAAAAAA";
    private static final String UPDATED_MINOR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPLAY = false;
    private static final Boolean UPDATED_DISPLAY = true;

    @Autowired
    private ConditionTypeRepository conditionTypeRepository;

    @Autowired
    private ConditionTypeService conditionTypeService;

    @Autowired
    private ConditionTypeQueryService conditionTypeQueryService;

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

    private MockMvc restConditionTypeMockMvc;

    private ConditionType conditionType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ConditionTypeResource conditionTypeResource = new ConditionTypeResource(conditionTypeService, conditionTypeQueryService);
        this.restConditionTypeMockMvc = MockMvcBuilders.standaloneSetup(conditionTypeResource)
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
    public static ConditionType createEntity(EntityManager em) {
        ConditionType conditionType = new ConditionType()
            .major(DEFAULT_MAJOR)
            .minor(DEFAULT_MINOR)
            .display(DEFAULT_DISPLAY);
        return conditionType;
    }

    @Before
    public void initTest() {
        conditionType = createEntity(em);
    }

    @Test
    @Transactional
    public void createConditionType() throws Exception {
        int databaseSizeBeforeCreate = conditionTypeRepository.findAll().size();

        // Create the ConditionType
        restConditionTypeMockMvc.perform(post("/api/condition-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conditionType)))
            .andExpect(status().isCreated());

        // Validate the ConditionType in the database
        List<ConditionType> conditionTypeList = conditionTypeRepository.findAll();
        assertThat(conditionTypeList).hasSize(databaseSizeBeforeCreate + 1);
        ConditionType testConditionType = conditionTypeList.get(conditionTypeList.size() - 1);
        assertThat(testConditionType.getMajor()).isEqualTo(DEFAULT_MAJOR);
        assertThat(testConditionType.getMinor()).isEqualTo(DEFAULT_MINOR);
        assertThat(testConditionType.isDisplay()).isEqualTo(DEFAULT_DISPLAY);
    }

    @Test
    @Transactional
    public void createConditionTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = conditionTypeRepository.findAll().size();

        // Create the ConditionType with an existing ID
        conditionType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restConditionTypeMockMvc.perform(post("/api/condition-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conditionType)))
            .andExpect(status().isBadRequest());

        // Validate the ConditionType in the database
        List<ConditionType> conditionTypeList = conditionTypeRepository.findAll();
        assertThat(conditionTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMajorIsRequired() throws Exception {
        int databaseSizeBeforeTest = conditionTypeRepository.findAll().size();
        // set the field null
        conditionType.setMajor(null);

        // Create the ConditionType, which fails.

        restConditionTypeMockMvc.perform(post("/api/condition-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conditionType)))
            .andExpect(status().isBadRequest());

        List<ConditionType> conditionTypeList = conditionTypeRepository.findAll();
        assertThat(conditionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDisplayIsRequired() throws Exception {
        int databaseSizeBeforeTest = conditionTypeRepository.findAll().size();
        // set the field null
        conditionType.setDisplay(null);

        // Create the ConditionType, which fails.

        restConditionTypeMockMvc.perform(post("/api/condition-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conditionType)))
            .andExpect(status().isBadRequest());

        List<ConditionType> conditionTypeList = conditionTypeRepository.findAll();
        assertThat(conditionTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllConditionTypes() throws Exception {
        // Initialize the database
        conditionTypeRepository.saveAndFlush(conditionType);

        // Get all the conditionTypeList
        restConditionTypeMockMvc.perform(get("/api/condition-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conditionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR.toString())))
            .andExpect(jsonPath("$.[*].minor").value(hasItem(DEFAULT_MINOR.toString())))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getConditionType() throws Exception {
        // Initialize the database
        conditionTypeRepository.saveAndFlush(conditionType);

        // Get the conditionType
        restConditionTypeMockMvc.perform(get("/api/condition-types/{id}", conditionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(conditionType.getId().intValue()))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR.toString()))
            .andExpect(jsonPath("$.minor").value(DEFAULT_MINOR.toString()))
            .andExpect(jsonPath("$.display").value(DEFAULT_DISPLAY.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllConditionTypesByMajorIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionTypeRepository.saveAndFlush(conditionType);

        // Get all the conditionTypeList where major equals to DEFAULT_MAJOR
        defaultConditionTypeShouldBeFound("major.equals=" + DEFAULT_MAJOR);

        // Get all the conditionTypeList where major equals to UPDATED_MAJOR
        defaultConditionTypeShouldNotBeFound("major.equals=" + UPDATED_MAJOR);
    }

    @Test
    @Transactional
    public void getAllConditionTypesByMajorIsInShouldWork() throws Exception {
        // Initialize the database
        conditionTypeRepository.saveAndFlush(conditionType);

        // Get all the conditionTypeList where major in DEFAULT_MAJOR or UPDATED_MAJOR
        defaultConditionTypeShouldBeFound("major.in=" + DEFAULT_MAJOR + "," + UPDATED_MAJOR);

        // Get all the conditionTypeList where major equals to UPDATED_MAJOR
        defaultConditionTypeShouldNotBeFound("major.in=" + UPDATED_MAJOR);
    }

    @Test
    @Transactional
    public void getAllConditionTypesByMajorIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionTypeRepository.saveAndFlush(conditionType);

        // Get all the conditionTypeList where major is not null
        defaultConditionTypeShouldBeFound("major.specified=true");

        // Get all the conditionTypeList where major is null
        defaultConditionTypeShouldNotBeFound("major.specified=false");
    }

    @Test
    @Transactional
    public void getAllConditionTypesByMinorIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionTypeRepository.saveAndFlush(conditionType);

        // Get all the conditionTypeList where minor equals to DEFAULT_MINOR
        defaultConditionTypeShouldBeFound("minor.equals=" + DEFAULT_MINOR);

        // Get all the conditionTypeList where minor equals to UPDATED_MINOR
        defaultConditionTypeShouldNotBeFound("minor.equals=" + UPDATED_MINOR);
    }

    @Test
    @Transactional
    public void getAllConditionTypesByMinorIsInShouldWork() throws Exception {
        // Initialize the database
        conditionTypeRepository.saveAndFlush(conditionType);

        // Get all the conditionTypeList where minor in DEFAULT_MINOR or UPDATED_MINOR
        defaultConditionTypeShouldBeFound("minor.in=" + DEFAULT_MINOR + "," + UPDATED_MINOR);

        // Get all the conditionTypeList where minor equals to UPDATED_MINOR
        defaultConditionTypeShouldNotBeFound("minor.in=" + UPDATED_MINOR);
    }

    @Test
    @Transactional
    public void getAllConditionTypesByMinorIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionTypeRepository.saveAndFlush(conditionType);

        // Get all the conditionTypeList where minor is not null
        defaultConditionTypeShouldBeFound("minor.specified=true");

        // Get all the conditionTypeList where minor is null
        defaultConditionTypeShouldNotBeFound("minor.specified=false");
    }

    @Test
    @Transactional
    public void getAllConditionTypesByDisplayIsEqualToSomething() throws Exception {
        // Initialize the database
        conditionTypeRepository.saveAndFlush(conditionType);

        // Get all the conditionTypeList where display equals to DEFAULT_DISPLAY
        defaultConditionTypeShouldBeFound("display.equals=" + DEFAULT_DISPLAY);

        // Get all the conditionTypeList where display equals to UPDATED_DISPLAY
        defaultConditionTypeShouldNotBeFound("display.equals=" + UPDATED_DISPLAY);
    }

    @Test
    @Transactional
    public void getAllConditionTypesByDisplayIsInShouldWork() throws Exception {
        // Initialize the database
        conditionTypeRepository.saveAndFlush(conditionType);

        // Get all the conditionTypeList where display in DEFAULT_DISPLAY or UPDATED_DISPLAY
        defaultConditionTypeShouldBeFound("display.in=" + DEFAULT_DISPLAY + "," + UPDATED_DISPLAY);

        // Get all the conditionTypeList where display equals to UPDATED_DISPLAY
        defaultConditionTypeShouldNotBeFound("display.in=" + UPDATED_DISPLAY);
    }

    @Test
    @Transactional
    public void getAllConditionTypesByDisplayIsNullOrNotNull() throws Exception {
        // Initialize the database
        conditionTypeRepository.saveAndFlush(conditionType);

        // Get all the conditionTypeList where display is not null
        defaultConditionTypeShouldBeFound("display.specified=true");

        // Get all the conditionTypeList where display is null
        defaultConditionTypeShouldNotBeFound("display.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultConditionTypeShouldBeFound(String filter) throws Exception {
        restConditionTypeMockMvc.perform(get("/api/condition-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(conditionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR.toString())))
            .andExpect(jsonPath("$.[*].minor").value(hasItem(DEFAULT_MINOR.toString())))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY.booleanValue())));

        // Check, that the count call also returns 1
        restConditionTypeMockMvc.perform(get("/api/condition-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultConditionTypeShouldNotBeFound(String filter) throws Exception {
        restConditionTypeMockMvc.perform(get("/api/condition-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restConditionTypeMockMvc.perform(get("/api/condition-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingConditionType() throws Exception {
        // Get the conditionType
        restConditionTypeMockMvc.perform(get("/api/condition-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateConditionType() throws Exception {
        // Initialize the database
        conditionTypeService.save(conditionType);

        int databaseSizeBeforeUpdate = conditionTypeRepository.findAll().size();

        // Update the conditionType
        ConditionType updatedConditionType = conditionTypeRepository.findById(conditionType.getId()).get();
        // Disconnect from session so that the updates on updatedConditionType are not directly saved in db
        em.detach(updatedConditionType);
        updatedConditionType
            .major(UPDATED_MAJOR)
            .minor(UPDATED_MINOR)
            .display(UPDATED_DISPLAY);

        restConditionTypeMockMvc.perform(put("/api/condition-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedConditionType)))
            .andExpect(status().isOk());

        // Validate the ConditionType in the database
        List<ConditionType> conditionTypeList = conditionTypeRepository.findAll();
        assertThat(conditionTypeList).hasSize(databaseSizeBeforeUpdate);
        ConditionType testConditionType = conditionTypeList.get(conditionTypeList.size() - 1);
        assertThat(testConditionType.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testConditionType.getMinor()).isEqualTo(UPDATED_MINOR);
        assertThat(testConditionType.isDisplay()).isEqualTo(UPDATED_DISPLAY);
    }

    @Test
    @Transactional
    public void updateNonExistingConditionType() throws Exception {
        int databaseSizeBeforeUpdate = conditionTypeRepository.findAll().size();

        // Create the ConditionType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restConditionTypeMockMvc.perform(put("/api/condition-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(conditionType)))
            .andExpect(status().isBadRequest());

        // Validate the ConditionType in the database
        List<ConditionType> conditionTypeList = conditionTypeRepository.findAll();
        assertThat(conditionTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteConditionType() throws Exception {
        // Initialize the database
        conditionTypeService.save(conditionType);

        int databaseSizeBeforeDelete = conditionTypeRepository.findAll().size();

        // Get the conditionType
        restConditionTypeMockMvc.perform(delete("/api/condition-types/{id}", conditionType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ConditionType> conditionTypeList = conditionTypeRepository.findAll();
        assertThat(conditionTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ConditionType.class);
        ConditionType conditionType1 = new ConditionType();
        conditionType1.setId(1L);
        ConditionType conditionType2 = new ConditionType();
        conditionType2.setId(conditionType1.getId());
        assertThat(conditionType1).isEqualTo(conditionType2);
        conditionType2.setId(2L);
        assertThat(conditionType1).isNotEqualTo(conditionType2);
        conditionType1.setId(null);
        assertThat(conditionType1).isNotEqualTo(conditionType2);
    }
}
