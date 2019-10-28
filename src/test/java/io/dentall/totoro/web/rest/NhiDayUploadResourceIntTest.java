package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.NhiDayUpload;
import io.dentall.totoro.domain.NhiDayUploadDetails;
import io.dentall.totoro.repository.NhiDayUploadRepository;
import io.dentall.totoro.service.NhiDayUploadService;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import io.dentall.totoro.service.NhiDayUploadQueryService;

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
import java.util.Collections;
import java.util.List;

import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the NhiDayUploadResource REST controller.
 *
 * @see NhiDayUploadResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NhiDayUploadResourceIntTest {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private NhiDayUploadRepository nhiDayUploadRepository;

    @Autowired
    private NhiDayUploadService nhiDayUploadService;

    @Autowired
    private NhiDayUploadQueryService nhiDayUploadQueryService;

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

    private MockMvc restNhiDayUploadMockMvc;

    private NhiDayUpload nhiDayUpload;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NhiDayUploadResource nhiDayUploadResource = new NhiDayUploadResource(nhiDayUploadService, nhiDayUploadQueryService);
        this.restNhiDayUploadMockMvc = MockMvcBuilders.standaloneSetup(nhiDayUploadResource)
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
    public static NhiDayUpload createEntity(EntityManager em) {
        NhiDayUpload nhiDayUpload = new NhiDayUpload()
            .date(DEFAULT_DATE);
        return nhiDayUpload;
    }

    @Before
    public void initTest() {
        nhiDayUpload = createEntity(em);
    }

    @Test
    @Transactional
    public void createNhiDayUpload() throws Exception {
        int databaseSizeBeforeCreate = nhiDayUploadRepository.findAll().size();

        // Create the NhiDayUpload
        restNhiDayUploadMockMvc.perform(post("/api/nhi-day-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiDayUpload)))
            .andExpect(status().isCreated());

        // Validate the NhiDayUpload in the database
        List<NhiDayUpload> nhiDayUploadList = nhiDayUploadRepository.findAll();
        assertThat(nhiDayUploadList).hasSize(databaseSizeBeforeCreate + 1);
        NhiDayUpload testNhiDayUpload = nhiDayUploadList.get(nhiDayUploadList.size() - 1);
        assertThat(testNhiDayUpload.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createNhiDayUploadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nhiDayUploadRepository.findAll().size();

        // Create the NhiDayUpload with an existing ID
        nhiDayUpload.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNhiDayUploadMockMvc.perform(post("/api/nhi-day-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiDayUpload)))
            .andExpect(status().isBadRequest());

        // Validate the NhiDayUpload in the database
        List<NhiDayUpload> nhiDayUploadList = nhiDayUploadRepository.findAll();
        assertThat(nhiDayUploadList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = nhiDayUploadRepository.findAll().size();
        // set the field null
        nhiDayUpload.setDate(null);

        // Create the NhiDayUpload, which fails.

        restNhiDayUploadMockMvc.perform(post("/api/nhi-day-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiDayUpload)))
            .andExpect(status().isBadRequest());

        List<NhiDayUpload> nhiDayUploadList = nhiDayUploadRepository.findAll();
        assertThat(nhiDayUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNhiDayUploads() throws Exception {
        // Initialize the database
        nhiDayUploadRepository.saveAndFlush(nhiDayUpload);

        // Get all the nhiDayUploadList
        restNhiDayUploadMockMvc.perform(get("/api/nhi-day-uploads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiDayUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getNhiDayUpload() throws Exception {
        // Initialize the database
        nhiDayUploadRepository.saveAndFlush(nhiDayUpload);

        // Get the nhiDayUpload
        restNhiDayUploadMockMvc.perform(get("/api/nhi-day-uploads/{id}", nhiDayUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nhiDayUpload.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getAllNhiDayUploadsByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        nhiDayUploadRepository.saveAndFlush(nhiDayUpload);

        // Get all the nhiDayUploadList where date equals to DEFAULT_DATE
        defaultNhiDayUploadShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the nhiDayUploadList where date equals to UPDATED_DATE
        defaultNhiDayUploadShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNhiDayUploadsByDateIsInShouldWork() throws Exception {
        // Initialize the database
        nhiDayUploadRepository.saveAndFlush(nhiDayUpload);

        // Get all the nhiDayUploadList where date in DEFAULT_DATE or UPDATED_DATE
        defaultNhiDayUploadShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the nhiDayUploadList where date equals to UPDATED_DATE
        defaultNhiDayUploadShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNhiDayUploadsByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        nhiDayUploadRepository.saveAndFlush(nhiDayUpload);

        // Get all the nhiDayUploadList where date is not null
        defaultNhiDayUploadShouldBeFound("date.specified=true");

        // Get all the nhiDayUploadList where date is null
        defaultNhiDayUploadShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllNhiDayUploadsByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        nhiDayUploadRepository.saveAndFlush(nhiDayUpload);

        // Get all the nhiDayUploadList where date greater than or equals to DEFAULT_DATE
        defaultNhiDayUploadShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the nhiDayUploadList where date greater than or equals to UPDATED_DATE
        defaultNhiDayUploadShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNhiDayUploadsByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        nhiDayUploadRepository.saveAndFlush(nhiDayUpload);

        // Get all the nhiDayUploadList where date less than or equals to DEFAULT_DATE
        defaultNhiDayUploadShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the nhiDayUploadList where date less than or equals to UPDATED_DATE
        defaultNhiDayUploadShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllNhiDayUploadsByNhiDayUploadDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        NhiDayUploadDetails nhiDayUploadDetails = NhiDayUploadDetailsResourceIntTest.createEntity(em);
        em.persist(nhiDayUploadDetails);
        em.flush();
        nhiDayUploadDetails.setNhiDayUpload(nhiDayUpload);
        nhiDayUpload.setNhiDayUploadDetails(Collections.singleton(nhiDayUploadDetails));
        nhiDayUploadRepository.saveAndFlush(nhiDayUpload);
        Long nhiDayUploadDetailsId = nhiDayUploadDetails.getId();

        // Get all the nhiDayUploadList where nhiDayUploadDetails equals to nhiDayUploadDetailsId
        defaultNhiDayUploadShouldBeFound("nhiDayUploadDetailsId.equals=" + nhiDayUploadDetailsId);

        // Get all the nhiDayUploadList where nhiDayUploadDetails equals to nhiDayUploadDetailsId + 1
        defaultNhiDayUploadShouldNotBeFound("nhiDayUploadDetailsId.equals=" + (nhiDayUploadDetailsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultNhiDayUploadShouldBeFound(String filter) throws Exception {
        restNhiDayUploadMockMvc.perform(get("/api/nhi-day-uploads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nhiDayUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restNhiDayUploadMockMvc.perform(get("/api/nhi-day-uploads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultNhiDayUploadShouldNotBeFound(String filter) throws Exception {
        restNhiDayUploadMockMvc.perform(get("/api/nhi-day-uploads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNhiDayUploadMockMvc.perform(get("/api/nhi-day-uploads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingNhiDayUpload() throws Exception {
        // Get the nhiDayUpload
        restNhiDayUploadMockMvc.perform(get("/api/nhi-day-uploads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNhiDayUpload() throws Exception {
        // Initialize the database
        nhiDayUploadService.save(nhiDayUpload);

        int databaseSizeBeforeUpdate = nhiDayUploadRepository.findAll().size();

        // Update the nhiDayUpload
        NhiDayUpload updatedNhiDayUpload = nhiDayUploadRepository.findById(nhiDayUpload.getId()).get();
        // Disconnect from session so that the updates on updatedNhiDayUpload are not directly saved in db
        em.detach(updatedNhiDayUpload);
        updatedNhiDayUpload
            .date(UPDATED_DATE);

        restNhiDayUploadMockMvc.perform(put("/api/nhi-day-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNhiDayUpload)))
            .andExpect(status().isOk());

        // Validate the NhiDayUpload in the database
        List<NhiDayUpload> nhiDayUploadList = nhiDayUploadRepository.findAll();
        assertThat(nhiDayUploadList).hasSize(databaseSizeBeforeUpdate);
        NhiDayUpload testNhiDayUpload = nhiDayUploadList.get(nhiDayUploadList.size() - 1);
        assertThat(testNhiDayUpload.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingNhiDayUpload() throws Exception {
        int databaseSizeBeforeUpdate = nhiDayUploadRepository.findAll().size();

        // Create the NhiDayUpload

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNhiDayUploadMockMvc.perform(put("/api/nhi-day-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nhiDayUpload)))
            .andExpect(status().isBadRequest());

        // Validate the NhiDayUpload in the database
        List<NhiDayUpload> nhiDayUploadList = nhiDayUploadRepository.findAll();
        assertThat(nhiDayUploadList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNhiDayUpload() throws Exception {
        // Initialize the database
        nhiDayUploadService.save(nhiDayUpload);

        int databaseSizeBeforeDelete = nhiDayUploadRepository.findAll().size();

        // Get the nhiDayUpload
        restNhiDayUploadMockMvc.perform(delete("/api/nhi-day-uploads/{id}", nhiDayUpload.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NhiDayUpload> nhiDayUploadList = nhiDayUploadRepository.findAll();
        assertThat(nhiDayUploadList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NhiDayUpload.class);
        NhiDayUpload nhiDayUpload1 = new NhiDayUpload();
        nhiDayUpload1.setId(1L);
        NhiDayUpload nhiDayUpload2 = new NhiDayUpload();
        nhiDayUpload2.setId(nhiDayUpload1.getId());
        assertThat(nhiDayUpload1).isEqualTo(nhiDayUpload2);
        nhiDayUpload2.setId(2L);
        assertThat(nhiDayUpload1).isNotEqualTo(nhiDayUpload2);
        nhiDayUpload1.setId(null);
        assertThat(nhiDayUpload1).isNotEqualTo(nhiDayUpload2);
    }
}
