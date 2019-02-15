package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.Drug;
import io.dentall.totoro.repository.DrugRepository;
import io.dentall.totoro.service.DrugService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.dto.DrugCriteria;
import io.dentall.totoro.service.DrugQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the DrugResource REST controller.
 *
 * @see DrugResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class DrugResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CHINESE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CHINESE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_VALID_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALID_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_UNIT = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Double DEFAULT_QUANTITY = 1D;
    private static final Double UPDATED_QUANTITY = 2D;

    private static final String DEFAULT_FREQUENCY = "AAAAAAAAAA";
    private static final String UPDATED_FREQUENCY = "BBBBBBBBBB";

    private static final String DEFAULT_WAY = "AAAAAAAAAA";
    private static final String UPDATED_WAY = "BBBBBBBBBB";

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private DrugService drugService;

    @Autowired
    private DrugQueryService drugQueryService;

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

    private MockMvc restDrugMockMvc;

    private Drug drug;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DrugResource drugResource = new DrugResource(drugService, drugQueryService);
        this.restDrugMockMvc = MockMvcBuilders.standaloneSetup(drugResource)
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
    public static Drug createEntity(EntityManager em) {
        Drug drug = new Drug()
            .name(DEFAULT_NAME)
            .chineseName(DEFAULT_CHINESE_NAME)
            .type(DEFAULT_TYPE)
            .validDate(DEFAULT_VALID_DATE)
            .endDate(DEFAULT_END_DATE)
            .unit(DEFAULT_UNIT)
            .price(DEFAULT_PRICE)
            .quantity(DEFAULT_QUANTITY)
            .frequency(DEFAULT_FREQUENCY)
            .way(DEFAULT_WAY);
        return drug;
    }

    @Before
    public void initTest() {
        drug = createEntity(em);
    }

    @Test
    @Transactional
    public void createDrug() throws Exception {
        int databaseSizeBeforeCreate = drugRepository.findAll().size();

        // Create the Drug
        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drug)))
            .andExpect(status().isCreated());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeCreate + 1);
        Drug testDrug = drugList.get(drugList.size() - 1);
        assertThat(testDrug.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDrug.getChineseName()).isEqualTo(DEFAULT_CHINESE_NAME);
        assertThat(testDrug.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testDrug.getValidDate()).isEqualTo(DEFAULT_VALID_DATE);
        assertThat(testDrug.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testDrug.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testDrug.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testDrug.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testDrug.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
        assertThat(testDrug.getWay()).isEqualTo(DEFAULT_WAY);
    }

    @Test
    @Transactional
    public void createDrugWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = drugRepository.findAll().size();

        // Create the Drug with an existing ID
        drug.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drug)))
            .andExpect(status().isBadRequest());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = drugRepository.findAll().size();
        // set the field null
        drug.setName(null);

        // Create the Drug, which fails.

        restDrugMockMvc.perform(post("/api/drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drug)))
            .andExpect(status().isBadRequest());

        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDrugs() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList
        restDrugMockMvc.perform(get("/api/drugs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drug.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].chineseName").value(hasItem(DEFAULT_CHINESE_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].validDate").value(hasItem(DEFAULT_VALID_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].way").value(hasItem(DEFAULT_WAY.toString())));
    }
    
    @Test
    @Transactional
    public void getDrug() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get the drug
        restDrugMockMvc.perform(get("/api/drugs/{id}", drug.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(drug.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.chineseName").value(DEFAULT_CHINESE_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.validDate").value(DEFAULT_VALID_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY.toString()))
            .andExpect(jsonPath("$.way").value(DEFAULT_WAY.toString()));
    }

    @Test
    @Transactional
    public void getAllDrugsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where name equals to DEFAULT_NAME
        defaultDrugShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the drugList where name equals to UPDATED_NAME
        defaultDrugShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDrugsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDrugShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the drugList where name equals to UPDATED_NAME
        defaultDrugShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDrugsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where name is not null
        defaultDrugShouldBeFound("name.specified=true");

        // Get all the drugList where name is null
        defaultDrugShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllDrugsByChineseNameIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where chineseName equals to DEFAULT_CHINESE_NAME
        defaultDrugShouldBeFound("chineseName.equals=" + DEFAULT_CHINESE_NAME);

        // Get all the drugList where chineseName equals to UPDATED_CHINESE_NAME
        defaultDrugShouldNotBeFound("chineseName.equals=" + UPDATED_CHINESE_NAME);
    }

    @Test
    @Transactional
    public void getAllDrugsByChineseNameIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where chineseName in DEFAULT_CHINESE_NAME or UPDATED_CHINESE_NAME
        defaultDrugShouldBeFound("chineseName.in=" + DEFAULT_CHINESE_NAME + "," + UPDATED_CHINESE_NAME);

        // Get all the drugList where chineseName equals to UPDATED_CHINESE_NAME
        defaultDrugShouldNotBeFound("chineseName.in=" + UPDATED_CHINESE_NAME);
    }

    @Test
    @Transactional
    public void getAllDrugsByChineseNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where chineseName is not null
        defaultDrugShouldBeFound("chineseName.specified=true");

        // Get all the drugList where chineseName is null
        defaultDrugShouldNotBeFound("chineseName.specified=false");
    }

    @Test
    @Transactional
    public void getAllDrugsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where type equals to DEFAULT_TYPE
        defaultDrugShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the drugList where type equals to UPDATED_TYPE
        defaultDrugShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllDrugsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultDrugShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the drugList where type equals to UPDATED_TYPE
        defaultDrugShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    public void getAllDrugsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where type is not null
        defaultDrugShouldBeFound("type.specified=true");

        // Get all the drugList where type is null
        defaultDrugShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    public void getAllDrugsByValidDateIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where validDate equals to DEFAULT_VALID_DATE
        defaultDrugShouldBeFound("validDate.equals=" + DEFAULT_VALID_DATE);

        // Get all the drugList where validDate equals to UPDATED_VALID_DATE
        defaultDrugShouldNotBeFound("validDate.equals=" + UPDATED_VALID_DATE);
    }

    @Test
    @Transactional
    public void getAllDrugsByValidDateIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where validDate in DEFAULT_VALID_DATE or UPDATED_VALID_DATE
        defaultDrugShouldBeFound("validDate.in=" + DEFAULT_VALID_DATE + "," + UPDATED_VALID_DATE);

        // Get all the drugList where validDate equals to UPDATED_VALID_DATE
        defaultDrugShouldNotBeFound("validDate.in=" + UPDATED_VALID_DATE);
    }

    @Test
    @Transactional
    public void getAllDrugsByValidDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where validDate is not null
        defaultDrugShouldBeFound("validDate.specified=true");

        // Get all the drugList where validDate is null
        defaultDrugShouldNotBeFound("validDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDrugsByValidDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where validDate greater than or equals to DEFAULT_VALID_DATE
        defaultDrugShouldBeFound("validDate.greaterOrEqualThan=" + DEFAULT_VALID_DATE);

        // Get all the drugList where validDate greater than or equals to UPDATED_VALID_DATE
        defaultDrugShouldNotBeFound("validDate.greaterOrEqualThan=" + UPDATED_VALID_DATE);
    }

    @Test
    @Transactional
    public void getAllDrugsByValidDateIsLessThanSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where validDate less than or equals to DEFAULT_VALID_DATE
        defaultDrugShouldNotBeFound("validDate.lessThan=" + DEFAULT_VALID_DATE);

        // Get all the drugList where validDate less than or equals to UPDATED_VALID_DATE
        defaultDrugShouldBeFound("validDate.lessThan=" + UPDATED_VALID_DATE);
    }


    @Test
    @Transactional
    public void getAllDrugsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where endDate equals to DEFAULT_END_DATE
        defaultDrugShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the drugList where endDate equals to UPDATED_END_DATE
        defaultDrugShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllDrugsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultDrugShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the drugList where endDate equals to UPDATED_END_DATE
        defaultDrugShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllDrugsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where endDate is not null
        defaultDrugShouldBeFound("endDate.specified=true");

        // Get all the drugList where endDate is null
        defaultDrugShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDrugsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where endDate greater than or equals to DEFAULT_END_DATE
        defaultDrugShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the drugList where endDate greater than or equals to UPDATED_END_DATE
        defaultDrugShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllDrugsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where endDate less than or equals to DEFAULT_END_DATE
        defaultDrugShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the drugList where endDate less than or equals to UPDATED_END_DATE
        defaultDrugShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }


    @Test
    @Transactional
    public void getAllDrugsByUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where unit equals to DEFAULT_UNIT
        defaultDrugShouldBeFound("unit.equals=" + DEFAULT_UNIT);

        // Get all the drugList where unit equals to UPDATED_UNIT
        defaultDrugShouldNotBeFound("unit.equals=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void getAllDrugsByUnitIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where unit in DEFAULT_UNIT or UPDATED_UNIT
        defaultDrugShouldBeFound("unit.in=" + DEFAULT_UNIT + "," + UPDATED_UNIT);

        // Get all the drugList where unit equals to UPDATED_UNIT
        defaultDrugShouldNotBeFound("unit.in=" + UPDATED_UNIT);
    }

    @Test
    @Transactional
    public void getAllDrugsByUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where unit is not null
        defaultDrugShouldBeFound("unit.specified=true");

        // Get all the drugList where unit is null
        defaultDrugShouldNotBeFound("unit.specified=false");
    }

    @Test
    @Transactional
    public void getAllDrugsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where price equals to DEFAULT_PRICE
        defaultDrugShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the drugList where price equals to UPDATED_PRICE
        defaultDrugShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllDrugsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultDrugShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the drugList where price equals to UPDATED_PRICE
        defaultDrugShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    public void getAllDrugsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where price is not null
        defaultDrugShouldBeFound("price.specified=true");

        // Get all the drugList where price is null
        defaultDrugShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    public void getAllDrugsByQuantityIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where quantity equals to DEFAULT_QUANTITY
        defaultDrugShouldBeFound("quantity.equals=" + DEFAULT_QUANTITY);

        // Get all the drugList where quantity equals to UPDATED_QUANTITY
        defaultDrugShouldNotBeFound("quantity.equals=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllDrugsByQuantityIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where quantity in DEFAULT_QUANTITY or UPDATED_QUANTITY
        defaultDrugShouldBeFound("quantity.in=" + DEFAULT_QUANTITY + "," + UPDATED_QUANTITY);

        // Get all the drugList where quantity equals to UPDATED_QUANTITY
        defaultDrugShouldNotBeFound("quantity.in=" + UPDATED_QUANTITY);
    }

    @Test
    @Transactional
    public void getAllDrugsByQuantityIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where quantity is not null
        defaultDrugShouldBeFound("quantity.specified=true");

        // Get all the drugList where quantity is null
        defaultDrugShouldNotBeFound("quantity.specified=false");
    }

    @Test
    @Transactional
    public void getAllDrugsByFrequencyIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where frequency equals to DEFAULT_FREQUENCY
        defaultDrugShouldBeFound("frequency.equals=" + DEFAULT_FREQUENCY);

        // Get all the drugList where frequency equals to UPDATED_FREQUENCY
        defaultDrugShouldNotBeFound("frequency.equals=" + UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    public void getAllDrugsByFrequencyIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where frequency in DEFAULT_FREQUENCY or UPDATED_FREQUENCY
        defaultDrugShouldBeFound("frequency.in=" + DEFAULT_FREQUENCY + "," + UPDATED_FREQUENCY);

        // Get all the drugList where frequency equals to UPDATED_FREQUENCY
        defaultDrugShouldNotBeFound("frequency.in=" + UPDATED_FREQUENCY);
    }

    @Test
    @Transactional
    public void getAllDrugsByFrequencyIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where frequency is not null
        defaultDrugShouldBeFound("frequency.specified=true");

        // Get all the drugList where frequency is null
        defaultDrugShouldNotBeFound("frequency.specified=false");
    }

    @Test
    @Transactional
    public void getAllDrugsByWayIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where way equals to DEFAULT_WAY
        defaultDrugShouldBeFound("way.equals=" + DEFAULT_WAY);

        // Get all the drugList where way equals to UPDATED_WAY
        defaultDrugShouldNotBeFound("way.equals=" + UPDATED_WAY);
    }

    @Test
    @Transactional
    public void getAllDrugsByWayIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where way in DEFAULT_WAY or UPDATED_WAY
        defaultDrugShouldBeFound("way.in=" + DEFAULT_WAY + "," + UPDATED_WAY);

        // Get all the drugList where way equals to UPDATED_WAY
        defaultDrugShouldNotBeFound("way.in=" + UPDATED_WAY);
    }

    @Test
    @Transactional
    public void getAllDrugsByWayIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where way is not null
        defaultDrugShouldBeFound("way.specified=true");

        // Get all the drugList where way is null
        defaultDrugShouldNotBeFound("way.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultDrugShouldBeFound(String filter) throws Exception {
        restDrugMockMvc.perform(get("/api/drugs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drug.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].chineseName").value(hasItem(DEFAULT_CHINESE_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].validDate").value(hasItem(DEFAULT_VALID_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].way").value(hasItem(DEFAULT_WAY.toString())));

        // Check, that the count call also returns 1
        restDrugMockMvc.perform(get("/api/drugs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultDrugShouldNotBeFound(String filter) throws Exception {
        restDrugMockMvc.perform(get("/api/drugs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDrugMockMvc.perform(get("/api/drugs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDrug() throws Exception {
        // Get the drug
        restDrugMockMvc.perform(get("/api/drugs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDrug() throws Exception {
        // Initialize the database
        drugService.save(drug);

        int databaseSizeBeforeUpdate = drugRepository.findAll().size();

        // Update the drug
        Drug updatedDrug = drugRepository.findById(drug.getId()).get();
        // Disconnect from session so that the updates on updatedDrug are not directly saved in db
        em.detach(updatedDrug);
        updatedDrug
            .name(UPDATED_NAME)
            .chineseName(UPDATED_CHINESE_NAME)
            .type(UPDATED_TYPE)
            .validDate(UPDATED_VALID_DATE)
            .endDate(UPDATED_END_DATE)
            .unit(UPDATED_UNIT)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .frequency(UPDATED_FREQUENCY)
            .way(UPDATED_WAY);

        restDrugMockMvc.perform(put("/api/drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDrug)))
            .andExpect(status().isOk());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeUpdate);
        Drug testDrug = drugList.get(drugList.size() - 1);
        assertThat(testDrug.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDrug.getChineseName()).isEqualTo(UPDATED_CHINESE_NAME);
        assertThat(testDrug.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testDrug.getValidDate()).isEqualTo(UPDATED_VALID_DATE);
        assertThat(testDrug.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testDrug.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testDrug.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testDrug.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDrug.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testDrug.getWay()).isEqualTo(UPDATED_WAY);
    }

    @Test
    @Transactional
    public void updateNonExistingDrug() throws Exception {
        int databaseSizeBeforeUpdate = drugRepository.findAll().size();

        // Create the Drug

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrugMockMvc.perform(put("/api/drugs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(drug)))
            .andExpect(status().isBadRequest());

        // Validate the Drug in the database
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDrug() throws Exception {
        // Initialize the database
        drugService.save(drug);

        int databaseSizeBeforeDelete = drugRepository.findAll().size();

        // Get the drug
        restDrugMockMvc.perform(delete("/api/drugs/{id}", drug.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Drug> drugList = drugRepository.findAll();
        assertThat(drugList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Drug.class);
        Drug drug1 = new Drug();
        drug1.setId(1L);
        Drug drug2 = new Drug();
        drug2.setId(drug1.getId());
        assertThat(drug1).isEqualTo(drug2);
        drug2.setId(2L);
        assertThat(drug1).isNotEqualTo(drug2);
        drug1.setId(null);
        assertThat(drug1).isNotEqualTo(drug2);
    }
}
