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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;


import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import io.dentall.totoro.domain.enumeration.TimeType;
import io.dentall.totoro.domain.enumeration.TimeInterval;
/**
 * Test class for the CalendarResource REST controller.
 *
 * @see CalendarResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class CalendarResourceIntTest {

    private static final Instant DEFAULT_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final TimeType DEFAULT_TIME_TYPE = TimeType.WORK_TIME;
    private static final TimeType UPDATED_TIME_TYPE = TimeType.HOLIDAY;

    private static final TimeInterval DEFAULT_TIME_INTERVAL = TimeInterval.MORNING;
    private static final TimeInterval UPDATED_TIME_INTERVAL = TimeInterval.NOON;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

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
            .start(DEFAULT_START)
            .end(DEFAULT_END)
            .timeType(DEFAULT_TIME_TYPE)
            .timeInterval(DEFAULT_TIME_INTERVAL)
            .note(DEFAULT_NOTE);
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
        assertThat(testCalendar.getStart()).isEqualTo(DEFAULT_START);
        assertThat(testCalendar.getEnd()).isEqualTo(DEFAULT_END);
        assertThat(testCalendar.getTimeType()).isEqualTo(DEFAULT_TIME_TYPE);
        assertThat(testCalendar.getTimeInterval()).isEqualTo(DEFAULT_TIME_INTERVAL);
        assertThat(testCalendar.getNote()).isEqualTo(DEFAULT_NOTE);
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
    public void checkTimeTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = calendarRepository.findAll().size();
        // set the field null
        calendar.setTimeType(null);

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
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].timeType").value(hasItem(DEFAULT_TIME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].timeInterval").value(hasItem(DEFAULT_TIME_INTERVAL.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));
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
            .andExpect(jsonPath("$.start").value(DEFAULT_START.toString()))
            .andExpect(jsonPath("$.end").value(DEFAULT_END.toString()))
            .andExpect(jsonPath("$.timeType").value(DEFAULT_TIME_TYPE.toString()))
            .andExpect(jsonPath("$.timeInterval").value(DEFAULT_TIME_INTERVAL.toString()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE.toString()));
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

    @Test
    @Transactional
    public void getAllCalendarsByTimeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where timeType equals to DEFAULT_TIME_TYPE
        defaultCalendarShouldBeFound("timeType.equals=" + DEFAULT_TIME_TYPE);

        // Get all the calendarList where timeType equals to UPDATED_TIME_TYPE
        defaultCalendarShouldNotBeFound("timeType.equals=" + UPDATED_TIME_TYPE);
    }

    @Test
    @Transactional
    public void getAllCalendarsByTimeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where timeType in DEFAULT_TIME_TYPE or UPDATED_TIME_TYPE
        defaultCalendarShouldBeFound("timeType.in=" + DEFAULT_TIME_TYPE + "," + UPDATED_TIME_TYPE);

        // Get all the calendarList where timeType equals to UPDATED_TIME_TYPE
        defaultCalendarShouldNotBeFound("timeType.in=" + UPDATED_TIME_TYPE);
    }

    @Test
    @Transactional
    public void getAllCalendarsByTimeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where timeType is not null
        defaultCalendarShouldBeFound("timeType.specified=true");

        // Get all the calendarList where timeType is null
        defaultCalendarShouldNotBeFound("timeType.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarsByTimeIntervalIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where timeInterval equals to DEFAULT_TIME_INTERVAL
        defaultCalendarShouldBeFound("timeInterval.equals=" + DEFAULT_TIME_INTERVAL);

        // Get all the calendarList where timeInterval equals to UPDATED_TIME_INTERVAL
        defaultCalendarShouldNotBeFound("timeInterval.equals=" + UPDATED_TIME_INTERVAL);
    }

    @Test
    @Transactional
    public void getAllCalendarsByTimeIntervalIsInShouldWork() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where timeInterval in DEFAULT_TIME_INTERVAL or UPDATED_TIME_INTERVAL
        defaultCalendarShouldBeFound("timeInterval.in=" + DEFAULT_TIME_INTERVAL + "," + UPDATED_TIME_INTERVAL);

        // Get all the calendarList where timeInterval equals to UPDATED_TIME_INTERVAL
        defaultCalendarShouldNotBeFound("timeInterval.in=" + UPDATED_TIME_INTERVAL);
    }

    @Test
    @Transactional
    public void getAllCalendarsByTimeIntervalIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where timeInterval is not null
        defaultCalendarShouldBeFound("timeInterval.specified=true");

        // Get all the calendarList where timeInterval is null
        defaultCalendarShouldNotBeFound("timeInterval.specified=false");
    }

    @Test
    @Transactional
    public void getAllCalendarsByNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where note equals to DEFAULT_NOTE
        defaultCalendarShouldBeFound("note.equals=" + DEFAULT_NOTE);

        // Get all the calendarList where note equals to UPDATED_NOTE
        defaultCalendarShouldNotBeFound("note.equals=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllCalendarsByNoteIsInShouldWork() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where note in DEFAULT_NOTE or UPDATED_NOTE
        defaultCalendarShouldBeFound("note.in=" + DEFAULT_NOTE + "," + UPDATED_NOTE);

        // Get all the calendarList where note equals to UPDATED_NOTE
        defaultCalendarShouldNotBeFound("note.in=" + UPDATED_NOTE);
    }

    @Test
    @Transactional
    public void getAllCalendarsByNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        calendarRepository.saveAndFlush(calendar);

        // Get all the calendarList where note is not null
        defaultCalendarShouldBeFound("note.specified=true");

        // Get all the calendarList where note is null
        defaultCalendarShouldNotBeFound("note.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultCalendarShouldBeFound(String filter) throws Exception {
        restCalendarMockMvc.perform(get("/api/calendars?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(calendar.getId().intValue())))
            .andExpect(jsonPath("$.[*].start").value(hasItem(DEFAULT_START.toString())))
            .andExpect(jsonPath("$.[*].end").value(hasItem(DEFAULT_END.toString())))
            .andExpect(jsonPath("$.[*].timeType").value(hasItem(DEFAULT_TIME_TYPE.toString())))
            .andExpect(jsonPath("$.[*].timeInterval").value(hasItem(DEFAULT_TIME_INTERVAL.toString())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE.toString())));

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
            .start(UPDATED_START)
            .end(UPDATED_END)
            .timeType(UPDATED_TIME_TYPE)
            .timeInterval(UPDATED_TIME_INTERVAL)
            .note(UPDATED_NOTE);

        restCalendarMockMvc.perform(put("/api/calendars")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCalendar)))
            .andExpect(status().isOk());

        // Validate the Calendar in the database
        List<Calendar> calendarList = calendarRepository.findAll();
        assertThat(calendarList).hasSize(databaseSizeBeforeUpdate);
        Calendar testCalendar = calendarList.get(calendarList.size() - 1);
        assertThat(testCalendar.getStart()).isEqualTo(UPDATED_START);
        assertThat(testCalendar.getEnd()).isEqualTo(UPDATED_END);
        assertThat(testCalendar.getTimeType()).isEqualTo(UPDATED_TIME_TYPE);
        assertThat(testCalendar.getTimeInterval()).isEqualTo(UPDATED_TIME_INTERVAL);
        assertThat(testCalendar.getNote()).isEqualTo(UPDATED_NOTE);
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
