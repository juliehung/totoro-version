package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.Calendar;
import io.dentall.totoro.repository.CalendarRepository;
import io.dentall.totoro.service.CalendarService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.dto.CalendarCriteria;
import io.dentall.totoro.service.CalendarQueryService;

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

import io.dentall.totoro.domain.enumeration.IntervalType;
import io.dentall.totoro.domain.enumeration.DateType;
/**
 * Test class for the CalendarResource REST controller.
 *
 * @see CalendarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class CalendarResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final IntervalType DEFAULT_INTERVAL_TYPE = IntervalType.MORNING;
    private static final IntervalType UPDATED_INTERVAL_TYPE = IntervalType.NOON;

    private static final DateType DEFAULT_DATE_TYPE = DateType.WORKTIME;
    private static final DateType UPDATED_DATE_TYPE = DateType.HOLIDAY;

    private static final String DEFAULT_START = "21:51";
    private static final String UPDATED_START = "17:14";

    private static final String DEFAULT_END = "23:21";
    private static final String UPDATED_END = "23:01";

    @Autowired
    private CalendarRepository calendarRepository;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private CalendarQueryService calendarQueryService;

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

    private MockMvc restCalendarMockMvc;

    private Calendar calendar;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CalendarResource calendarResource = new CalendarResource(calendarService, calendarQueryService);
        this.restCalendarMockMvc = MockMvcBuilders.standaloneSetup(calendarResource)
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
    public static Calendar createEntity(EntityManager em) {
        Calendar calendar = new Calendar()
            .date(DEFAULT_DATE)
            .intervalType(DEFAULT_INTERVAL_TYPE)
            .dateType(DEFAULT_DATE_TYPE)
            .start(DEFAULT_START)
            .end(DEFAULT_END);
        return calendar;
    }

    @Before
    public void initTest() {
        calendar = createEntity(em);
    }

    @Test
    @Transactional
    public void createCalendar() throws Exception {
        int databaseSizeBeforeCreate = calendarRepository.findAll().size();

        // Create the Calendar
        restCalendarMockMvc.perform(post("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendar)))
            .andExpect(status().isCreated());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeCreate + 1);
        Calendar testCalendar = calendarList.get(calendarList.size() - 1);
        assertThat(testCalendar.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testCalendar.getIntervalType()).isEqualTo(DEFAULT_INTERVAL_TYPE);
        assertThat(testCalendar.getDateType()).isEqualTo(DEFAULT_DATE_TYPE);
        assertThat(testCalendar.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testCalendar.getEnd()).isEqualTo(DEFAULT_END);
    }

    @Test
    @Transactional
    public void createCalendarWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = calendarRepository.findAll().size();

        // Create the Calendar with an existing ID
        calendar.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCalendarMockMvc.perform(post("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendar)))
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarRepository.findAll().size();
        // set the field null
        calendar.setDate(null);

        // Create the Calendar, which fails.

        restCalendarMockMvc.perform(post("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendar)))
            .andExpect(status().isBadRequest());

        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIntervalTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarRepository.findAll().size();
        // set the field null
        calendar.setIntervalType(null);

        // Create the Calendar, which fails.

        restCalendarMockMvc.perform(post("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendar)))
            .andExpect(status().isBadRequest());

        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDateTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarRepository.findAll().size();
        // set the field null
        calendar.setDateType(null);

        // Create the Calendar, which fails.

        restCalendarMockMvc.perform(post("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendar)))
            .andExpect(status().isBadRequest());

        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarRepository.findAll().size();
        // set the field null
        calendar.setStart(null);

        // Create the Calendar, which fails.

        restCalendarMockMvc.perform(post("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendar)))
            .andExpect(status().isBadRequest());

        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEndIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarRepository.findAll().size();
        // set the field null
        calendar.setEnd(null);

        // Create the Calendar, which fails.

        restCalendarMockMvc.perform(post("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendar)))
            .andExpect(status().isBadRequest());

        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCalendars() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList
        restCalendarMockMvc.perform(get("/api/calendars?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendar.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].intervalType").value(hasItem(DEFAULT_INTERVAL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dateType").value(hasItem(DEFAULT_DATE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));
    }
    
    @Test
    @Transactional
    public void getCalendar() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get the calendar
        restCalendarMockMvc.perform(get("/api/calendars/{id}", calendar.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(calendar.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.intervalType").value(DEFAULT_INTERVAL_TYPE.toString()))
            .andExpect(jsonPath("$.dateType").value(DEFAULT_DATE_TYPE.toString()))
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()));
    }

    @Test
    @Transactional
    public void getAllCalendarsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where date equals to DEFAULT_DATE
        defaultCalendarShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the calendarList where date equals to UPDATED_DATE
        defaultCalendarShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCalendarsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where date in DEFAULT_DATE or UPDATED_DATE
        defaultCalendarShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the calendarList where date equals to UPDATED_DATE
        defaultCalendarShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCalendarsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where date is not null
        defaultCalendarShouldBeFound("date.specified=true");

        // Get all the calendarList where date is null
        defaultCalendarShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where date greater than or equals to DEFAULT_DATE
        defaultCalendarShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the calendarList where date greater than or equals to UPDATED_DATE
        defaultCalendarShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllCalendarsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where date less than or equals to DEFAULT_DATE
        defaultCalendarShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the calendarList where date less than or equals to UPDATED_DATE
        defaultCalendarShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllCalendarsByIntervalTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where intervalType equals to DEFAULT_INTERVAL_TYPE
        defaultCalendarShouldBeFound("intervalType.equals=" + DEFAULT_INTERVAL_TYPE);

        // Get all the calendarList where intervalType equals to UPDATED_INTERVAL_TYPE
        defaultCalendarShouldNotBeFound("intervalType.equals=" + UPDATED_INTERVAL_TYPE);
    }

    @Test
    @Transactional
    public void getAllCalendarsByIntervalTypeIsInShouldWork() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where intervalType in DEFAULT_INTERVAL_TYPE or UPDATED_INTERVAL_TYPE
        defaultCalendarShouldBeFound("intervalType.in=" + DEFAULT_INTERVAL_TYPE + "," + UPDATED_INTERVAL_TYPE);

        // Get all the calendarList where intervalType equals to UPDATED_INTERVAL_TYPE
        defaultCalendarShouldNotBeFound("intervalType.in=" + UPDATED_INTERVAL_TYPE);
    }

    @Test
    @Transactional
    public void getAllCalendarsByIntervalTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where intervalType is not null
        defaultCalendarShouldBeFound("intervalType.specified=true");

        // Get all the calendarList where intervalType is null
        defaultCalendarShouldNotBeFound("intervalType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarsByDateTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where dateType equals to DEFAULT_DATE_TYPE
        defaultCalendarShouldBeFound("dateType.equals=" + DEFAULT_DATE_TYPE);

        // Get all the calendarList where dateType equals to UPDATED_DATE_TYPE
        defaultCalendarShouldNotBeFound("dateType.equals=" + UPDATED_DATE_TYPE);
    }

    @Test
    @Transactional
    public void getAllCalendarsByDateTypeIsInShouldWork() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where dateType in DEFAULT_DATE_TYPE or UPDATED_DATE_TYPE
        defaultCalendarShouldBeFound("dateType.in=" + DEFAULT_DATE_TYPE + "," + UPDATED_DATE_TYPE);

        // Get all the calendarList where dateType equals to UPDATED_DATE_TYPE
        defaultCalendarShouldNotBeFound("dateType.in=" + UPDATED_DATE_TYPE);
    }

    @Test
    @Transactional
    public void getAllCalendarsByDateTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where dateType is not null
        defaultCalendarShouldBeFound("dateType.specified=true");

        // Get all the calendarList where dateType is null
        defaultCalendarShouldNotBeFound("dateType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarsByStartIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where start equals to DEFAULT_START
        defaultCalendarShouldBeFound("start.equals=" + DEFAULT_START);

        // Get all the calendarList where start equals to UPDATED_START
        defaultCalendarShouldNotBeFound("start.equals=" + UPDATED_START);
    }

    @Test
    @Transactional
    public void getAllCalendarsByStartIsInShouldWork() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where start in DEFAULT_START or UPDATED_START
        defaultCalendarShouldBeFound("start.in=" + DEFAULT_START + "," + UPDATED_START);

        // Get all the calendarList where start equals to UPDATED_START
        defaultCalendarShouldNotBeFound("start.in=" + UPDATED_START);
    }

    @Test
    @Transactional
    public void getAllCalendarsByStartIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where start is not null
        defaultCalendarShouldBeFound("start.specified=true");

        // Get all the calendarList where start is null
        defaultCalendarShouldNotBeFound("start.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarsByEndIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where end equals to DEFAULT_END
        defaultCalendarShouldBeFound("end.equals=" + DEFAULT_END);

        // Get all the calendarList where end equals to UPDATED_END
        defaultCalendarShouldNotBeFound("end.equals=" + UPDATED_END);
    }

    @Test
    @Transactional
    public void getAllCalendarsByEndIsInShouldWork() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where end in DEFAULT_END or UPDATED_END
        defaultCalendarShouldBeFound("end.in=" + DEFAULT_END + "," + UPDATED_END);

        // Get all the calendarList where end equals to UPDATED_END
        defaultCalendarShouldNotBeFound("end.in=" + UPDATED_END);
    }

    @Test
    @Transactional
    public void getAllCalendarsByEndIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where end is not null
        defaultCalendarShouldBeFound("end.specified=true");

        // Get all the calendarList where end is null
        defaultCalendarShouldNotBeFound("end.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCalendarShouldBeFound(String filter) throws Exception {
        restCalendarMockMvc.perform(get("/api/calendars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendar.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].intervalType").value(hasItem(DEFAULT_INTERVAL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dateType").value(hasItem(DEFAULT_DATE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())));

        // Check, that the count call also returns 1
        restCalendarMockMvc.perform(get("/api/calendars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultCalendarShouldNotBeFound(String filter) throws Exception {
        restCalendarMockMvc.perform(get("/api/calendars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCalendarMockMvc.perform(get("/api/calendars/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCalendar() throws Exception {
        // Get the calendar
        restCalendarMockMvc.perform(get("/api/calendars/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCalendar() throws Exception {
        // Initialize the database
        calendarService.save(calendar);

        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();

        // Update the calendar
        Calendar updatedCalendar = calendarRepository.findById(calendar.getId()).get();
        // Disconnect from session so that the updates on updatedCalendar are not directly saved in db
        em.detach(updatedCalendar);
        updatedCalendar
            .date(UPDATED_DATE)
            .intervalType(UPDATED_INTERVAL_TYPE)
            .dateType(UPDATED_DATE_TYPE)
            .start(UPDATED_START)
            .end(UPDATED_END);

        restCalendarMockMvc.perform(put("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalendar)))
            .andExpect(status().isOk());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
        Calendar testCalendar = calendarList.get(calendarList.size() - 1);
        assertThat(testCalendar.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testCalendar.getIntervalType()).isEqualTo(UPDATED_INTERVAL_TYPE);
        assertThat(testCalendar.getDateType()).isEqualTo(UPDATED_DATE_TYPE);
        assertThat(testCalendar.getStart()).isEqualTo(UPDATED_START);
        assertThat(testCalendar.getEnd()).isEqualTo(UPDATED_END);
    }

    @Test
    @Transactional
    public void updateNonExistingCalendar() throws Exception {
        int databaseSizeBeforeUpdate = calendarRepository.findAll().size();

        // Create the Calendar

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCalendarMockMvc.perform(put("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(calendar)))
            .andExpect(status().isBadRequest());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCalendar() throws Exception {
        // Initialize the database
        calendarService.save(calendar);

        int databaseSizeBeforeDelete = calendarRepository.findAll().size();

        // Get the calendar
        restCalendarMockMvc.perform(delete("/api/calendars/{id}", calendar.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Calendar.class);
        Calendar calendar1 = new Calendar();
        calendar1.setId(1L);
        Calendar calendar2 = new Calendar();
        calendar2.setId(calendar1.getId());
        assertThat(calendar1).isEqualTo(calendar2);
        calendar2.setId(2L);
        assertThat(calendar1).isNotEqualTo(calendar2);
        calendar1.setId(null);
        assertThat(calendar1).isNotEqualTo(calendar2);
    }
}
