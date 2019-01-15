package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.domain.NHICategory;
import io.dentall.totoro.repository.NHICategoryRepository;
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
 * Test class for the NHICategoryResource REST controller.
 *
 * @see NHICategoryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NHICategoryResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CODES = "AAAAAAAAAA";
    private static final String UPDATED_CODES = "BBBBBBBBBB";

    @Autowired
    private NHICategoryRepository nHICategoryRepository;

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

    private MockMvc restNHICategoryMockMvc;

    private NHICategory nHICategory;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NHICategoryResource nHICategoryResource = new NHICategoryResource(nHICategoryRepository);
        this.restNHICategoryMockMvc = MockMvcBuilders.standaloneSetup(nHICategoryResource)
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
    public static NHICategory createEntity(EntityManager em) {
        NHICategory nHICategory = new NHICategory()
            .name(DEFAULT_NAME)
            .codes(DEFAULT_CODES);
        return nHICategory;
    }

    @Before
    public void initTest() {
        nHICategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createNHICategory() throws Exception {
        int databaseSizeBeforeCreate = nHICategoryRepository.findAll().size();

        // Create the NHICategory
        restNHICategoryMockMvc.perform(post("/api/nhi-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHICategory)))
            .andExpect(status().isCreated());

        // Validate the NHICategory in the database
        List<NHICategory> nHICategoryList = nHICategoryRepository.findAll();
        assertThat(nHICategoryList).hasSize(databaseSizeBeforeCreate + 1);
        NHICategory testNHICategory = nHICategoryList.get(nHICategoryList.size() - 1);
        assertThat(testNHICategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testNHICategory.getCodes()).isEqualTo(DEFAULT_CODES);
    }

    @Test
    @Transactional
    public void createNHICategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nHICategoryRepository.findAll().size();

        // Create the NHICategory with an existing ID
        nHICategory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNHICategoryMockMvc.perform(post("/api/nhi-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHICategory)))
            .andExpect(status().isBadRequest());

        // Validate the NHICategory in the database
        List<NHICategory> nHICategoryList = nHICategoryRepository.findAll();
        assertThat(nHICategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllNHICategories() throws Exception {
        // Initialize the database
        nHICategoryRepository.saveAndFlush(nHICategory);

        // Get all the nHICategoryList
        restNHICategoryMockMvc.perform(get("/api/nhi-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nHICategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].codes").value(hasItem(DEFAULT_CODES.toString())));
    }
    
    @Test
    @Transactional
    public void getNHICategory() throws Exception {
        // Initialize the database
        nHICategoryRepository.saveAndFlush(nHICategory);

        // Get the nHICategory
        restNHICategoryMockMvc.perform(get("/api/nhi-categories/{id}", nHICategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nHICategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.codes").value(DEFAULT_CODES.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNHICategory() throws Exception {
        // Get the nHICategory
        restNHICategoryMockMvc.perform(get("/api/nhi-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNHICategory() throws Exception {
        // Initialize the database
        nHICategoryRepository.saveAndFlush(nHICategory);

        int databaseSizeBeforeUpdate = nHICategoryRepository.findAll().size();

        // Update the nHICategory
        NHICategory updatedNHICategory = nHICategoryRepository.findById(nHICategory.getId()).get();
        // Disconnect from session so that the updates on updatedNHICategory are not directly saved in db
        em.detach(updatedNHICategory);
        updatedNHICategory
            .name(UPDATED_NAME)
            .codes(UPDATED_CODES);

        restNHICategoryMockMvc.perform(put("/api/nhi-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNHICategory)))
            .andExpect(status().isOk());

        // Validate the NHICategory in the database
        List<NHICategory> nHICategoryList = nHICategoryRepository.findAll();
        assertThat(nHICategoryList).hasSize(databaseSizeBeforeUpdate);
        NHICategory testNHICategory = nHICategoryList.get(nHICategoryList.size() - 1);
        assertThat(testNHICategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testNHICategory.getCodes()).isEqualTo(UPDATED_CODES);
    }

    @Test
    @Transactional
    public void updateNonExistingNHICategory() throws Exception {
        int databaseSizeBeforeUpdate = nHICategoryRepository.findAll().size();

        // Create the NHICategory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNHICategoryMockMvc.perform(put("/api/nhi-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHICategory)))
            .andExpect(status().isBadRequest());

        // Validate the NHICategory in the database
        List<NHICategory> nHICategoryList = nHICategoryRepository.findAll();
        assertThat(nHICategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNHICategory() throws Exception {
        // Initialize the database
        nHICategoryRepository.saveAndFlush(nHICategory);

        int databaseSizeBeforeDelete = nHICategoryRepository.findAll().size();

        // Get the nHICategory
        restNHICategoryMockMvc.perform(delete("/api/nhi-categories/{id}", nHICategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NHICategory> nHICategoryList = nHICategoryRepository.findAll();
        assertThat(nHICategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NHICategory.class);
        NHICategory nHICategory1 = new NHICategory();
        nHICategory1.setId(1L);
        NHICategory nHICategory2 = new NHICategory();
        nHICategory2.setId(nHICategory1.getId());
        assertThat(nHICategory1).isEqualTo(nHICategory2);
        nHICategory2.setId(2L);
        assertThat(nHICategory1).isNotEqualTo(nHICategory2);
        nHICategory1.setId(null);
        assertThat(nHICategory1).isNotEqualTo(nHICategory2);
    }
}
