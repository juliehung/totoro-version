package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.business.service.DrugBusinessService;
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

    private static final String DEFAULT_NHI_CODE = "AAAAAAAAAA";
    private static final String UPDATED_NHI_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_WARNING = "AAAAAAAAAA";
    private static final String UPDATED_WARNING = "BBBBBBBBBB";

    private static final Integer DEFAULT_DAYS = 1;
    private static final Integer UPDATED_DAYS = 2;

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;

    @Autowired
    private DrugRepository drugRepository;

    @Autowired
    private DrugService drugService;

    @Autowired
    private DrugBusinessService drugBusinessService;

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
        final DrugBusinessResource drugBusinessResource =
            new DrugBusinessResource(drugBusinessService);

        this.restDrugMockMvc = MockMvcBuilders.standaloneSetup(
            drugResource,
            drugBusinessResource)
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
            .unit(DEFAULT_UNIT)
            .price(DEFAULT_PRICE)
            .quantity(DEFAULT_QUANTITY)
            .frequency(DEFAULT_FREQUENCY)
            .way(DEFAULT_WAY)
            .nhiCode(DEFAULT_NHI_CODE)
            .warning(DEFAULT_WARNING)
            .days(DEFAULT_DAYS)
            .order(DEFAULT_ORDER);
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
        assertThat(testDrug.getUnit()).isEqualTo(DEFAULT_UNIT);
        assertThat(testDrug.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testDrug.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testDrug.getFrequency()).isEqualTo(DEFAULT_FREQUENCY);
        assertThat(testDrug.getWay()).isEqualTo(DEFAULT_WAY);
        assertThat(testDrug.getNhiCode()).isEqualTo(DEFAULT_NHI_CODE);
        assertThat(testDrug.getWarning()).isEqualTo(DEFAULT_WARNING);
        assertThat(testDrug.getDays()).isEqualTo(DEFAULT_DAYS);
        assertThat(testDrug.getOrder()).isEqualTo(DEFAULT_ORDER);
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
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].way").value(hasItem(DEFAULT_WAY.toString())))
            .andExpect(jsonPath("$.[*].nhiCode").value(hasItem(DEFAULT_NHI_CODE.toString())))
            .andExpect(jsonPath("$.[*].warning").value(hasItem(DEFAULT_WARNING.toString())))
            .andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));
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
            .andExpect(jsonPath("$.unit").value(DEFAULT_UNIT.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.doubleValue()))
            .andExpect(jsonPath("$.frequency").value(DEFAULT_FREQUENCY.toString()))
            .andExpect(jsonPath("$.way").value(DEFAULT_WAY.toString()))
            .andExpect(jsonPath("$.nhiCode").value(DEFAULT_NHI_CODE.toString()))
            .andExpect(jsonPath("$.warning").value(DEFAULT_WARNING.toString()))
            .andExpect(jsonPath("$.days").value(DEFAULT_DAYS))
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER));
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

    @Test
    @Transactional
    public void getAllDrugsByNhiCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where nhiCode equals to DEFAULT_NHI_CODE
        defaultDrugShouldBeFound("nhiCode.equals=" + DEFAULT_NHI_CODE);

        // Get all the drugList where nhiCode equals to UPDATED_NHI_CODE
        defaultDrugShouldNotBeFound("nhiCode.equals=" + UPDATED_NHI_CODE);
    }

    @Test
    @Transactional
    public void getAllDrugsByNhiCodeIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where nhiCode in DEFAULT_NHI_CODE or UPDATED_NHI_CODE
        defaultDrugShouldBeFound("nhiCode.in=" + DEFAULT_NHI_CODE + "," + UPDATED_NHI_CODE);

        // Get all the drugList where nhiCode equals to UPDATED_NHI_CODE
        defaultDrugShouldNotBeFound("nhiCode.in=" + UPDATED_NHI_CODE);
    }

    @Test
    @Transactional
    public void getAllDrugsByNhiCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where nhiCode is not null
        defaultDrugShouldBeFound("nhiCode.specified=true");

        // Get all the drugList where nhiCode is null
        defaultDrugShouldNotBeFound("nhiCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllDrugsByWarningIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where warning equals to DEFAULT_WARNING
        defaultDrugShouldBeFound("warning.equals=" + DEFAULT_WARNING);

        // Get all the drugList where warning equals to UPDATED_WARNING
        defaultDrugShouldNotBeFound("warning.equals=" + UPDATED_WARNING);
    }

    @Test
    @Transactional
    public void getAllDrugsByWarningIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where warning in DEFAULT_WARNING or UPDATED_WARNING
        defaultDrugShouldBeFound("warning.in=" + DEFAULT_WARNING + "," + UPDATED_WARNING);

        // Get all the drugList where warning equals to UPDATED_WARNING
        defaultDrugShouldNotBeFound("warning.in=" + UPDATED_WARNING);
    }

    @Test
    @Transactional
    public void getAllDrugsByWarningIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where warning is not null
        defaultDrugShouldBeFound("warning.specified=true");

        // Get all the drugList where warning is null
        defaultDrugShouldNotBeFound("warning.specified=false");
    }

    @Test
    @Transactional
    public void getAllDrugsByDaysIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where days equals to DEFAULT_DAYS
        defaultDrugShouldBeFound("days.equals=" + DEFAULT_DAYS);

        // Get all the drugList where days equals to UPDATED_DAYS
        defaultDrugShouldNotBeFound("days.equals=" + UPDATED_DAYS);
    }

    @Test
    @Transactional
    public void getAllDrugsByDaysIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where days in DEFAULT_DAYS or UPDATED_DAYS
        defaultDrugShouldBeFound("days.in=" + DEFAULT_DAYS + "," + UPDATED_DAYS);

        // Get all the drugList where days equals to UPDATED_DAYS
        defaultDrugShouldNotBeFound("days.in=" + UPDATED_DAYS);
    }

    @Test
    @Transactional
    public void getAllDrugsByDaysIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where days is not null
        defaultDrugShouldBeFound("days.specified=true");

        // Get all the drugList where days is null
        defaultDrugShouldNotBeFound("days.specified=false");
    }

    @Test
    @Transactional
    public void getAllDrugsByDaysIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where days greater than or equals to DEFAULT_DAYS
        defaultDrugShouldBeFound("days.greaterOrEqualThan=" + DEFAULT_DAYS);

        // Get all the drugList where days greater than or equals to UPDATED_DAYS
        defaultDrugShouldNotBeFound("days.greaterOrEqualThan=" + UPDATED_DAYS);
    }

    @Test
    @Transactional
    public void getAllDrugsByDaysIsLessThanSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where days less than or equals to DEFAULT_DAYS
        defaultDrugShouldNotBeFound("days.lessThan=" + DEFAULT_DAYS);

        // Get all the drugList where days less than or equals to UPDATED_DAYS
        defaultDrugShouldBeFound("days.lessThan=" + UPDATED_DAYS);
    }


    @Test
    @Transactional
    public void getAllDrugsByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where order equals to DEFAULT_ORDER
        defaultDrugShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the drugList where order equals to UPDATED_ORDER
        defaultDrugShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllDrugsByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultDrugShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the drugList where order equals to UPDATED_ORDER
        defaultDrugShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllDrugsByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where order is not null
        defaultDrugShouldBeFound("order.specified=true");

        // Get all the drugList where order is null
        defaultDrugShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    public void getAllDrugsByOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where order greater than or equals to DEFAULT_ORDER
        defaultDrugShouldBeFound("order.greaterOrEqualThan=" + DEFAULT_ORDER);

        // Get all the drugList where order greater than or equals to UPDATED_ORDER
        defaultDrugShouldNotBeFound("order.greaterOrEqualThan=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    public void getAllDrugsByOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        drugRepository.saveAndFlush(drug);

        // Get all the drugList where order less than or equals to DEFAULT_ORDER
        defaultDrugShouldNotBeFound("order.lessThan=" + DEFAULT_ORDER);

        // Get all the drugList where order less than or equals to UPDATED_ORDER
        defaultDrugShouldBeFound("order.lessThan=" + UPDATED_ORDER);
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
            .andExpect(jsonPath("$.[*].unit").value(hasItem(DEFAULT_UNIT.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.doubleValue())))
            .andExpect(jsonPath("$.[*].frequency").value(hasItem(DEFAULT_FREQUENCY.toString())))
            .andExpect(jsonPath("$.[*].way").value(hasItem(DEFAULT_WAY.toString())))
            .andExpect(jsonPath("$.[*].nhiCode").value(hasItem(DEFAULT_NHI_CODE.toString())))
            .andExpect(jsonPath("$.[*].warning").value(hasItem(DEFAULT_WARNING.toString())))
            .andExpect(jsonPath("$.[*].days").value(hasItem(DEFAULT_DAYS)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)));

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
            .unit(UPDATED_UNIT)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .frequency(UPDATED_FREQUENCY)
            .way(UPDATED_WAY)
            .nhiCode(UPDATED_NHI_CODE)
            .warning(UPDATED_WARNING)
            .days(UPDATED_DAYS)
            .order(UPDATED_ORDER);

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
        assertThat(testDrug.getUnit()).isEqualTo(UPDATED_UNIT);
        assertThat(testDrug.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testDrug.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testDrug.getFrequency()).isEqualTo(UPDATED_FREQUENCY);
        assertThat(testDrug.getWay()).isEqualTo(UPDATED_WAY);
        assertThat(testDrug.getNhiCode()).isEqualTo(UPDATED_NHI_CODE);
        assertThat(testDrug.getWarning()).isEqualTo(UPDATED_WARNING);
        assertThat(testDrug.getDays()).isEqualTo(UPDATED_DAYS);
        assertThat(testDrug.getOrder()).isEqualTo(UPDATED_ORDER);
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
        restDrugMockMvc.perform(delete("/api/business/drugs/{id}", drug.getId())
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
