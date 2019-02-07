package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.FindingType;
import io.dentall.totoro.repository.FindingTypeRepository;
import io.dentall.totoro.service.FindingTypeService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.dto.FindingTypeCriteria;
import io.dentall.totoro.service.FindingTypeQueryService;

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
 * Test class for the FindingTypeResource REST controller.
 *
 * @see FindingTypeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class FindingTypeResourceIntTest {

    private static final String DEFAULT_MAJOR = "AAAAAAAAAA";
    private static final String UPDATED_MAJOR = "BBBBBBBBBB";

    private static final String DEFAULT_MINOR = "AAAAAAAAAA";
    private static final String UPDATED_MINOR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPLAY = false;
    private static final Boolean UPDATED_DISPLAY = true;

    @Autowired
    private FindingTypeRepository findingTypeRepository;

    @Autowired
    private FindingTypeService findingTypeService;

    @Autowired
    private FindingTypeQueryService findingTypeQueryService;

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

    private MockMvc restFindingTypeMockMvc;

    private FindingType findingType;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FindingTypeResource findingTypeResource = new FindingTypeResource(findingTypeService, findingTypeQueryService);
        this.restFindingTypeMockMvc = MockMvcBuilders.standaloneSetup(findingTypeResource)
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
    public static FindingType createEntity(EntityManager em) {
        FindingType findingType = new FindingType()
            .major(DEFAULT_MAJOR)
            .minor(DEFAULT_MINOR)
            .display(DEFAULT_DISPLAY);
        return findingType;
    }

    @Before
    public void initTest() {
        findingType = createEntity(em);
    }

    @Test
    @Transactional
    public void createFindingType() throws Exception {
        int databaseSizeBeforeCreate = findingTypeRepository.findAll().size();

        // Create the FindingType
        restFindingTypeMockMvc.perform(post("/api/finding-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(findingType)))
            .andExpect(status().isCreated());

        // Validate the FindingType in the database
        List<FindingType> findingTypeList = findingTypeRepository.findAll();
        assertThat(findingTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FindingType testFindingType = findingTypeList.get(findingTypeList.size() - 1);
        assertThat(testFindingType.getMajor()).isEqualTo(DEFAULT_MAJOR);
        assertThat(testFindingType.getMinor()).isEqualTo(DEFAULT_MINOR);
        assertThat(testFindingType.isDisplay()).isEqualTo(DEFAULT_DISPLAY);
    }

    @Test
    @Transactional
    public void createFindingTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = findingTypeRepository.findAll().size();

        // Create the FindingType with an existing ID
        findingType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFindingTypeMockMvc.perform(post("/api/finding-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(findingType)))
            .andExpect(status().isBadRequest());

        // Validate the FindingType in the database
        List<FindingType> findingTypeList = findingTypeRepository.findAll();
        assertThat(findingTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkMajorIsRequired() throws Exception {
        int databaseSizeBeforeTest = findingTypeRepository.findAll().size();
        // set the field null
        findingType.setMajor(null);

        // Create the FindingType, which fails.

        restFindingTypeMockMvc.perform(post("/api/finding-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(findingType)))
            .andExpect(status().isBadRequest());

        List<FindingType> findingTypeList = findingTypeRepository.findAll();
        assertThat(findingTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDisplayIsRequired() throws Exception {
        int databaseSizeBeforeTest = findingTypeRepository.findAll().size();
        // set the field null
        findingType.setDisplay(null);

        // Create the FindingType, which fails.

        restFindingTypeMockMvc.perform(post("/api/finding-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(findingType)))
            .andExpect(status().isBadRequest());

        List<FindingType> findingTypeList = findingTypeRepository.findAll();
        assertThat(findingTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFindingTypes() throws Exception {
        // Initialize the database
        findingTypeRepository.saveAndFlush(findingType);

        // Get all the findingTypeList
        restFindingTypeMockMvc.perform(get("/api/finding-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(findingType.getId().intValue())))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR.toString())))
            .andExpect(jsonPath("$.[*].minor").value(hasItem(DEFAULT_MINOR.toString())))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getFindingType() throws Exception {
        // Initialize the database
        findingTypeRepository.saveAndFlush(findingType);

        // Get the findingType
        restFindingTypeMockMvc.perform(get("/api/finding-types/{id}", findingType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(findingType.getId().intValue()))
            .andExpect(jsonPath("$.major").value(DEFAULT_MAJOR.toString()))
            .andExpect(jsonPath("$.minor").value(DEFAULT_MINOR.toString()))
            .andExpect(jsonPath("$.display").value(DEFAULT_DISPLAY.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllFindingTypesByMajorIsEqualToSomething() throws Exception {
        // Initialize the database
        findingTypeRepository.saveAndFlush(findingType);

        // Get all the findingTypeList where major equals to DEFAULT_MAJOR
        defaultFindingTypeShouldBeFound("major.equals=" + DEFAULT_MAJOR);

        // Get all the findingTypeList where major equals to UPDATED_MAJOR
        defaultFindingTypeShouldNotBeFound("major.equals=" + UPDATED_MAJOR);
    }

    @Test
    @Transactional
    public void getAllFindingTypesByMajorIsInShouldWork() throws Exception {
        // Initialize the database
        findingTypeRepository.saveAndFlush(findingType);

        // Get all the findingTypeList where major in DEFAULT_MAJOR or UPDATED_MAJOR
        defaultFindingTypeShouldBeFound("major.in=" + DEFAULT_MAJOR + "," + UPDATED_MAJOR);

        // Get all the findingTypeList where major equals to UPDATED_MAJOR
        defaultFindingTypeShouldNotBeFound("major.in=" + UPDATED_MAJOR);
    }

    @Test
    @Transactional
    public void getAllFindingTypesByMajorIsNullOrNotNull() throws Exception {
        // Initialize the database
        findingTypeRepository.saveAndFlush(findingType);

        // Get all the findingTypeList where major is not null
        defaultFindingTypeShouldBeFound("major.specified=true");

        // Get all the findingTypeList where major is null
        defaultFindingTypeShouldNotBeFound("major.specified=false");
    }

    @Test
    @Transactional
    public void getAllFindingTypesByMinorIsEqualToSomething() throws Exception {
        // Initialize the database
        findingTypeRepository.saveAndFlush(findingType);

        // Get all the findingTypeList where minor equals to DEFAULT_MINOR
        defaultFindingTypeShouldBeFound("minor.equals=" + DEFAULT_MINOR);

        // Get all the findingTypeList where minor equals to UPDATED_MINOR
        defaultFindingTypeShouldNotBeFound("minor.equals=" + UPDATED_MINOR);
    }

    @Test
    @Transactional
    public void getAllFindingTypesByMinorIsInShouldWork() throws Exception {
        // Initialize the database
        findingTypeRepository.saveAndFlush(findingType);

        // Get all the findingTypeList where minor in DEFAULT_MINOR or UPDATED_MINOR
        defaultFindingTypeShouldBeFound("minor.in=" + DEFAULT_MINOR + "," + UPDATED_MINOR);

        // Get all the findingTypeList where minor equals to UPDATED_MINOR
        defaultFindingTypeShouldNotBeFound("minor.in=" + UPDATED_MINOR);
    }

    @Test
    @Transactional
    public void getAllFindingTypesByMinorIsNullOrNotNull() throws Exception {
        // Initialize the database
        findingTypeRepository.saveAndFlush(findingType);

        // Get all the findingTypeList where minor is not null
        defaultFindingTypeShouldBeFound("minor.specified=true");

        // Get all the findingTypeList where minor is null
        defaultFindingTypeShouldNotBeFound("minor.specified=false");
    }

    @Test
    @Transactional
    public void getAllFindingTypesByDisplayIsEqualToSomething() throws Exception {
        // Initialize the database
        findingTypeRepository.saveAndFlush(findingType);

        // Get all the findingTypeList where display equals to DEFAULT_DISPLAY
        defaultFindingTypeShouldBeFound("display.equals=" + DEFAULT_DISPLAY);

        // Get all the findingTypeList where display equals to UPDATED_DISPLAY
        defaultFindingTypeShouldNotBeFound("display.equals=" + UPDATED_DISPLAY);
    }

    @Test
    @Transactional
    public void getAllFindingTypesByDisplayIsInShouldWork() throws Exception {
        // Initialize the database
        findingTypeRepository.saveAndFlush(findingType);

        // Get all the findingTypeList where display in DEFAULT_DISPLAY or UPDATED_DISPLAY
        defaultFindingTypeShouldBeFound("display.in=" + DEFAULT_DISPLAY + "," + UPDATED_DISPLAY);

        // Get all the findingTypeList where display equals to UPDATED_DISPLAY
        defaultFindingTypeShouldNotBeFound("display.in=" + UPDATED_DISPLAY);
    }

    @Test
    @Transactional
    public void getAllFindingTypesByDisplayIsNullOrNotNull() throws Exception {
        // Initialize the database
        findingTypeRepository.saveAndFlush(findingType);

        // Get all the findingTypeList where display is not null
        defaultFindingTypeShouldBeFound("display.specified=true");

        // Get all the findingTypeList where display is null
        defaultFindingTypeShouldNotBeFound("display.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultFindingTypeShouldBeFound(String filter) throws Exception {
        restFindingTypeMockMvc.perform(get("/api/finding-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(findingType.getId().intValue())))
            .andExpect(jsonPath("$.[*].major").value(hasItem(DEFAULT_MAJOR.toString())))
            .andExpect(jsonPath("$.[*].minor").value(hasItem(DEFAULT_MINOR.toString())))
            .andExpect(jsonPath("$.[*].display").value(hasItem(DEFAULT_DISPLAY.booleanValue())));

        // Check, that the count call also returns 1
        restFindingTypeMockMvc.perform(get("/api/finding-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultFindingTypeShouldNotBeFound(String filter) throws Exception {
        restFindingTypeMockMvc.perform(get("/api/finding-types?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFindingTypeMockMvc.perform(get("/api/finding-types/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFindingType() throws Exception {
        // Get the findingType
        restFindingTypeMockMvc.perform(get("/api/finding-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFindingType() throws Exception {
        // Initialize the database
        findingTypeService.save(findingType);

        int databaseSizeBeforeUpdate = findingTypeRepository.findAll().size();

        // Update the findingType
        FindingType updatedFindingType = findingTypeRepository.findById(findingType.getId()).get();
        // Disconnect from session so that the updates on updatedFindingType are not directly saved in db
        em.detach(updatedFindingType);
        updatedFindingType
            .major(UPDATED_MAJOR)
            .minor(UPDATED_MINOR)
            .display(UPDATED_DISPLAY);

        restFindingTypeMockMvc.perform(put("/api/finding-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFindingType)))
            .andExpect(status().isOk());

        // Validate the FindingType in the database
        List<FindingType> findingTypeList = findingTypeRepository.findAll();
        assertThat(findingTypeList).hasSize(databaseSizeBeforeUpdate);
        FindingType testFindingType = findingTypeList.get(findingTypeList.size() - 1);
        assertThat(testFindingType.getMajor()).isEqualTo(UPDATED_MAJOR);
        assertThat(testFindingType.getMinor()).isEqualTo(UPDATED_MINOR);
        assertThat(testFindingType.isDisplay()).isEqualTo(UPDATED_DISPLAY);
    }

    @Test
    @Transactional
    public void updateNonExistingFindingType() throws Exception {
        int databaseSizeBeforeUpdate = findingTypeRepository.findAll().size();

        // Create the FindingType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFindingTypeMockMvc.perform(put("/api/finding-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(findingType)))
            .andExpect(status().isBadRequest());

        // Validate the FindingType in the database
        List<FindingType> findingTypeList = findingTypeRepository.findAll();
        assertThat(findingTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFindingType() throws Exception {
        // Initialize the database
        findingTypeService.save(findingType);

        int databaseSizeBeforeDelete = findingTypeRepository.findAll().size();

        // Get the findingType
        restFindingTypeMockMvc.perform(delete("/api/finding-types/{id}", findingType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FindingType> findingTypeList = findingTypeRepository.findAll();
        assertThat(findingTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FindingType.class);
        FindingType findingType1 = new FindingType();
        findingType1.setId(1L);
        FindingType findingType2 = new FindingType();
        findingType2.setId(findingType1.getId());
        assertThat(findingType1).isEqualTo(findingType2);
        findingType2.setId(2L);
        assertThat(findingType1).isNotEqualTo(findingType2);
        findingType1.setId(null);
        assertThat(findingType1).isNotEqualTo(findingType2);
    }
}
