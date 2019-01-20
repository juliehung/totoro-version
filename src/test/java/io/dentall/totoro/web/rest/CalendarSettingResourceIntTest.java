package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.CalendarSetting;
import io.dentall.totoro.repository.CalendarSettingRepository;
import io.dentall.totoro.service.CalendarSettingService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.CalendarSettingQueryService;

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
import java.util.Arrays;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.dentall.totoro.domain.enumeration.TimeInterval;
import io.dentall.totoro.domain.enumeration.WeekDay;
/**
 * Test class for the CalendarSettingResource REST controller.
 *
 * @see CalendarSettingResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class CalendarSettingResourceIntTest {

    private static final String DEFAULT_START_TIME = "22:03";
    private static final String UPDATED_START_TIME = "21:58";

    private static final String DEFAULT_END_TIME = "20:17";
    private static final String UPDATED_END_TIME = "21:14";

    private static final TimeInterval DEFAULT_TIME_INTERVAL = TimeInterval.MORNING;
    private static final TimeInterval UPDATED_TIME_INTERVAL = TimeInterval.NOON;

    private static final WeekDay DEFAULT_WEEKDAY = WeekDay.SUN;
    private static final WeekDay UPDATED_WEEKDAY = WeekDay.MON;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private CalendarSettingRepository calendarSettingRepository;

    @Autowired
    private CalendarSettingService calendarSettingService;

    @Autowired
    private CalendarSettingQueryService calendarSettingQueryService;

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

    private MockMvc restCalendarSettingMockMvc;

    private CalendarSetting calendarSetting;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CalendarSettingResource calendarSettingResource = new CalendarSettingResource(calendarSettingService, calendarSettingQueryService);
        this.restCalendarSettingMockMvc = MockMvcBuilders.standaloneSetup(calendarSettingResource)
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
    public static CalendarSetting createEntity(EntityManager em) {
        CalendarSetting calendarSetting = new CalendarSetting()
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .timeInterval(DEFAULT_TIME_INTERVAL)
            .weekday(DEFAULT_WEEKDAY)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return calendarSetting;
    }

    @Before
    public void initTest() {
        calendarSetting = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalendarSetting() throws Exception {
        int databaseSizeBeforeCreate = calendarSettingRepository.findAll().size();

        // Create the CalendarSetting
        restCalendarSettingMockMvc.perform(post("/api/calendar-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarSetting)))
            .andExpect(status().isCreated());

        // Validate the CalendarSetting in the database
        List<CalendarSetting> calendarSettingList = calendarSettingRepository.findAll();
        assertThat(calendarSettingList).hasSize(databaseSizeBeforeCreate + 1);
        CalendarSetting testCalendarSetting = calendarSettingList.get(calendarSettingList.size() - 1);
        assertThat(testCalendarSetting.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testCalendarSetting.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testCalendarSetting.getTimeInterval()).isEqualTo(DEFAULT_TIME_INTERVAL);
        assertThat(testCalendarSetting.getWeekday()).isEqualTo(DEFAULT_WEEKDAY);
        assertThat(testCalendarSetting.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testCalendarSetting.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createCalendarSettingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calendarSettingRepository.findAll().size();

        // Create the CalendarSetting with an existing ID
        calendarSetting.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendarSettingMockMvc.perform(post("/api/calendar-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarSetting)))
            .andExpect(status().isBadRequest());

        // Validate the CalendarSetting in the database
        List<CalendarSetting> calendarSettingList = calendarSettingRepository.findAll();
        assertThat(calendarSettingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkStartTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarSettingRepository.findAll().size();
        // set the field null
        calendarSetting.setStartTime(null);

        // Create the CalendarSetting, which fails.

        restCalendarSettingMockMvc.perform(post("/api/calendar-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarSetting)))
            .andExpect(status().isBadRequest());

        List<CalendarSetting> calendarSettingList = calendarSettingRepository.findAll();
        assertThat(calendarSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarSettingRepository.findAll().size();
        // set the field null
        calendarSetting.setEndTime(null);

        // Create the CalendarSetting, which fails.

        restCalendarSettingMockMvc.perform(post("/api/calendar-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarSetting)))
            .andExpect(status().isBadRequest());

        List<CalendarSetting> calendarSettingList = calendarSettingRepository.findAll();
        assertThat(calendarSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTimeIntervalIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarSettingRepository.findAll().size();
        // set the field null
        calendarSetting.setTimeInterval(null);

        // Create the CalendarSetting, which fails.

        restCalendarSettingMockMvc.perform(post("/api/calendar-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarSetting)))
            .andExpect(status().isBadRequest());

        List<CalendarSetting> calendarSettingList = calendarSettingRepository.findAll();
        assertThat(calendarSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkWeekdayIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarSettingRepository.findAll().size();
        // set the field null
        calendarSetting.setWeekday(null);

        // Create the CalendarSetting, which fails.

        restCalendarSettingMockMvc.perform(post("/api/calendar-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarSetting)))
            .andExpect(status().isBadRequest());

        List<CalendarSetting> calendarSettingList = calendarSettingRepository.findAll();
        assertThat(calendarSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarSettingRepository.findAll().size();
        // set the field null
        calendarSetting.setStartDate(null);

        // Create the CalendarSetting, which fails.

        restCalendarSettingMockMvc.perform(post("/api/calendar-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarSetting)))
            .andExpect(status().isBadRequest());

        List<CalendarSetting> calendarSettingList = calendarSettingRepository.findAll();
        assertThat(calendarSettingList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCalendarSettings() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList
        restCalendarSettingMockMvc.perform(get("/api/calendar-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendarSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].timeInterval").value(hasItem(DEFAULT_TIME_INTERVAL.toString())))
            .andExpect(jsonPath("$.[*].weekday").value(hasItem(DEFAULT_WEEKDAY.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getCalendarSetting() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get the calendarSetting
        restCalendarSettingMockMvc.perform(get("/api/calendar-settings/{id}", calendarSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calendarSetting.getId().intValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.timeInterval").value(DEFAULT_TIME_INTERVAL.toString()))
            .andExpect(jsonPath("$.weekday").value(DEFAULT_WEEKDAY.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByStartTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where startTime equals to DEFAULT_START_TIME
        defaultCalendarSettingShouldBeFound("startTime.equals=" + DEFAULT_START_TIME);

        // Get all the calendarSettingList where startTime equals to UPDATED_START_TIME
        defaultCalendarSettingShouldNotBeFound("startTime.equals=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByStartTimeIsInShouldWork() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where startTime in DEFAULT_START_TIME or UPDATED_START_TIME
        defaultCalendarSettingShouldBeFound("startTime.in=" + DEFAULT_START_TIME + "," + UPDATED_START_TIME);

        // Get all the calendarSettingList where startTime equals to UPDATED_START_TIME
        defaultCalendarSettingShouldNotBeFound("startTime.in=" + UPDATED_START_TIME);
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByStartTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where startTime is not null
        defaultCalendarSettingShouldBeFound("startTime.specified=true");

        // Get all the calendarSettingList where startTime is null
        defaultCalendarSettingShouldNotBeFound("startTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByEndTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where endTime equals to DEFAULT_END_TIME
        defaultCalendarSettingShouldBeFound("endTime.equals=" + DEFAULT_END_TIME);

        // Get all the calendarSettingList where endTime equals to UPDATED_END_TIME
        defaultCalendarSettingShouldNotBeFound("endTime.equals=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByEndTimeIsInShouldWork() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where endTime in DEFAULT_END_TIME or UPDATED_END_TIME
        defaultCalendarSettingShouldBeFound("endTime.in=" + DEFAULT_END_TIME + "," + UPDATED_END_TIME);

        // Get all the calendarSettingList where endTime equals to UPDATED_END_TIME
        defaultCalendarSettingShouldNotBeFound("endTime.in=" + UPDATED_END_TIME);
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByEndTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where endTime is not null
        defaultCalendarSettingShouldBeFound("endTime.specified=true");

        // Get all the calendarSettingList where endTime is null
        defaultCalendarSettingShouldNotBeFound("endTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByTimeIntervalIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where timeInterval equals to DEFAULT_TIME_INTERVAL
        defaultCalendarSettingShouldBeFound("timeInterval.equals=" + DEFAULT_TIME_INTERVAL);

        // Get all the calendarSettingList where timeInterval equals to UPDATED_TIME_INTERVAL
        defaultCalendarSettingShouldNotBeFound("timeInterval.equals=" + UPDATED_TIME_INTERVAL);
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByTimeIntervalIsInShouldWork() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where timeInterval in DEFAULT_TIME_INTERVAL or UPDATED_TIME_INTERVAL
        defaultCalendarSettingShouldBeFound("timeInterval.in=" + DEFAULT_TIME_INTERVAL + "," + UPDATED_TIME_INTERVAL);

        // Get all the calendarSettingList where timeInterval equals to UPDATED_TIME_INTERVAL
        defaultCalendarSettingShouldNotBeFound("timeInterval.in=" + UPDATED_TIME_INTERVAL);
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByTimeIntervalIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where timeInterval is not null
        defaultCalendarSettingShouldBeFound("timeInterval.specified=true");

        // Get all the calendarSettingList where timeInterval is null
        defaultCalendarSettingShouldNotBeFound("timeInterval.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByWeekdayIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where weekday equals to DEFAULT_WEEKDAY
        defaultCalendarSettingShouldBeFound("weekday.equals=" + DEFAULT_WEEKDAY);

        // Get all the calendarSettingList where weekday equals to UPDATED_WEEKDAY
        defaultCalendarSettingShouldNotBeFound("weekday.equals=" + UPDATED_WEEKDAY);
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByWeekdayIsInShouldWork() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where weekday in DEFAULT_WEEKDAY or UPDATED_WEEKDAY
        defaultCalendarSettingShouldBeFound("weekday.in=" + DEFAULT_WEEKDAY + "," + UPDATED_WEEKDAY);

        // Get all the calendarSettingList where weekday equals to UPDATED_WEEKDAY
        defaultCalendarSettingShouldNotBeFound("weekday.in=" + UPDATED_WEEKDAY);
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByWeekdayIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where weekday is not null
        defaultCalendarSettingShouldBeFound("weekday.specified=true");

        // Get all the calendarSettingList where weekday is null
        defaultCalendarSettingShouldNotBeFound("weekday.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where startDate equals to DEFAULT_START_DATE
        defaultCalendarSettingShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the calendarSettingList where startDate equals to UPDATED_START_DATE
        defaultCalendarSettingShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultCalendarSettingShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the calendarSettingList where startDate equals to UPDATED_START_DATE
        defaultCalendarSettingShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where startDate is not null
        defaultCalendarSettingShouldBeFound("startDate.specified=true");

        // Get all the calendarSettingList where startDate is null
        defaultCalendarSettingShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where startDate greater than or equals to DEFAULT_START_DATE
        defaultCalendarSettingShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the calendarSettingList where startDate greater than or equals to UPDATED_START_DATE
        defaultCalendarSettingShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where startDate less than or equals to DEFAULT_START_DATE
        defaultCalendarSettingShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the calendarSettingList where startDate less than or equals to UPDATED_START_DATE
        defaultCalendarSettingShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    @Test
    @Transactional
    public void getAllCalendarSettingsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where endDate equals to DEFAULT_END_DATE
        defaultCalendarSettingShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the calendarSettingList where endDate equals to UPDATED_END_DATE
        defaultCalendarSettingShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultCalendarSettingShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the calendarSettingList where endDate equals to UPDATED_END_DATE
        defaultCalendarSettingShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where endDate is not null
        defaultCalendarSettingShouldBeFound("endDate.specified=true");

        // Get all the calendarSettingList where endDate is null
        defaultCalendarSettingShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where endDate greater than or equals to DEFAULT_END_DATE
        defaultCalendarSettingShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the calendarSettingList where endDate greater than or equals to UPDATED_END_DATE
        defaultCalendarSettingShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllCalendarSettingsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the calendarSettingList where endDate less than or equals to DEFAULT_END_DATE
        defaultCalendarSettingShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the calendarSettingList where endDate less than or equals to UPDATED_END_DATE
        defaultCalendarSettingShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCalendarSettingShouldBeFound(String filter) throws Exception {
        restCalendarSettingMockMvc.perform(get("/api/calendar-settings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendarSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].timeInterval").value(hasItem(DEFAULT_TIME_INTERVAL.toString())))
            .andExpect(jsonPath("$.[*].weekday").value(hasItem(DEFAULT_WEEKDAY.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));

        // Check, that the count call also returns 1
        restCalendarSettingMockMvc.perform(get("/api/calendar-settings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCalendarSettingShouldNotBeFound(String filter) throws Exception {
        restCalendarSettingMockMvc.perform(get("/api/calendar-settings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCalendarSettingMockMvc.perform(get("/api/calendar-settings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCalendarSetting() throws Exception {
        // Get the calendarSetting
        restCalendarSettingMockMvc.perform(get("/api/calendar-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalendarSetting() throws Exception {
        // Initialize the database
        calendarSettingService.save(calendarSetting);

        int databaseSizeBeforeUpdate = calendarSettingRepository.findAll().size();

        // Update the calendarSetting
        CalendarSetting updatedCalendarSetting = calendarSettingRepository.findById(calendarSetting.getId()).get();
        // Disconnect from session so that the updates on updatedCalendarSetting are not directly saved in db
        em.detach(updatedCalendarSetting);
        updatedCalendarSetting
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .timeInterval(UPDATED_TIME_INTERVAL)
            .weekday(UPDATED_WEEKDAY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restCalendarSettingMockMvc.perform(put("/api/calendar-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalendarSetting)))
            .andExpect(status().isOk());

        // Validate the CalendarSetting in the database
        List<CalendarSetting> calendarSettingList = calendarSettingRepository.findAll();
        assertThat(calendarSettingList).hasSize(databaseSizeBeforeUpdate);
        CalendarSetting testCalendarSetting = calendarSettingList.get(calendarSettingList.size() - 1);
        assertThat(testCalendarSetting.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testCalendarSetting.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testCalendarSetting.getTimeInterval()).isEqualTo(UPDATED_TIME_INTERVAL);
        assertThat(testCalendarSetting.getWeekday()).isEqualTo(UPDATED_WEEKDAY);
        assertThat(testCalendarSetting.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testCalendarSetting.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCalendarSetting() throws Exception {
        int databaseSizeBeforeUpdate = calendarSettingRepository.findAll().size();

        // Create the CalendarSetting

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarSettingMockMvc.perform(put("/api/calendar-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendarSetting)))
            .andExpect(status().isBadRequest());

        // Validate the CalendarSetting in the database
        List<CalendarSetting> calendarSettingList = calendarSettingRepository.findAll();
        assertThat(calendarSettingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalendarSetting() throws Exception {
        // Initialize the database
        calendarSettingService.save(calendarSetting);

        int databaseSizeBeforeDelete = calendarSettingRepository.findAll().size();

        // Get the calendarSetting
        restCalendarSettingMockMvc.perform(delete("/api/calendar-settings/{id}", calendarSetting.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CalendarSetting> calendarSettingList = calendarSettingRepository.findAll();
        assertThat(calendarSettingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CalendarSetting.class);
        CalendarSetting calendarSetting1 = new CalendarSetting();
        calendarSetting1.setId(1L);
        CalendarSetting calendarSetting2 = new CalendarSetting();
        calendarSetting2.setId(calendarSetting1.getId());
        assertThat(calendarSetting1).isEqualTo(calendarSetting2);
        calendarSetting2.setId(2L);
        assertThat(calendarSetting1).isNotEqualTo(calendarSetting2);
        calendarSetting1.setId(null);
        assertThat(calendarSetting1).isNotEqualTo(calendarSetting2);
    }

    @Test
    @Transactional
    public void getValidCalendarSettingsByNullEndDate() throws Exception {
        calendarSetting.setEndDate(null);

        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        Object jsonNull = null;
        // Get all the valid calendarSettingList
        restCalendarSettingMockMvc.perform(get("/api/calendar-settings?startDate.equals={start}&endDate.equals={end}", LocalDate.now().minusDays(3), LocalDate.now().plusDays(3)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendarSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].timeInterval").value(hasItem(DEFAULT_TIME_INTERVAL.toString())))
            .andExpect(jsonPath("$.[*].weekday").value(hasItem(DEFAULT_WEEKDAY.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(jsonNull)));
    }

    @Test
    @Transactional
    public void getValidCalendarSettingsByDateRange() throws Exception {
        LocalDate startDate = LocalDate.now().minusDays(2);
        calendarSetting.setStartDate(startDate);
        LocalDate endDate = LocalDate.now().plusDays(2);
        calendarSetting.setEndDate(endDate);

        // Initialize the database
        calendarSettingRepository.saveAndFlush(calendarSetting);

        // Get all the valid calendarSettingList
        restCalendarSettingMockMvc.perform(get("/api/calendar-settings?startDate.equals={start}&endDate.equals={end}", LocalDate.now().minusDays(3), LocalDate.now().plusDays(3)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendarSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].timeInterval").value(hasItem(DEFAULT_TIME_INTERVAL.toString())))
            .andExpect(jsonPath("$.[*].weekday").value(hasItem(DEFAULT_WEEKDAY.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(startDate.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(endDate.toString())));
    }

    @Test
    @Transactional
    public void createCalendarSettings() throws Exception {
        int databaseSizeBeforeCreate = calendarSettingRepository.findAll().size();

        // Create the CalendarSetting list
        restCalendarSettingMockMvc.perform(post("/api/calendar-settings/settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(Arrays.asList(calendarSetting, createEntity(em)))))
            .andExpect(status().isCreated());

        // Validate the CalendarSetting in the database
        List<CalendarSetting> calendarSettingList = calendarSettingRepository.findAll();
        assertThat(calendarSettingList).hasSize(databaseSizeBeforeCreate + 2);
    }
}
