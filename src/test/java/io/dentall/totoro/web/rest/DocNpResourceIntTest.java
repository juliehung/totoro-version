package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.domain.DocNp;
import io.dentall.totoro.repository.DocNpRepository;
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
 * Test class for the DocNpResource REST controller.
 *
 * @see DocNpResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class DocNpResourceIntTest {

    private static final Long DEFAULT_PATIENT_ID = 1L;
    private static final Long UPDATED_PATIENT_ID = 2L;

    private static final Long DEFAULT_ESIGN_ID = 1L;
    private static final Long UPDATED_ESIGN_ID = 2L;

    @Autowired
    private DocNpRepository docNpRepository;

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

    private MockMvc restDocNpMockMvc;

    private DocNp docNp;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DocNpResource docNpResource = new DocNpResource(docNpRepository);
        this.restDocNpMockMvc = MockMvcBuilders.standaloneSetup(docNpResource)
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
    public static DocNp createEntity(EntityManager em) {
        DocNp docNp = new DocNp()
            .patientId(DEFAULT_PATIENT_ID)
            .esignId(DEFAULT_ESIGN_ID);
        return docNp;
    }

    @Before
    public void initTest() {
        docNp = createEntity(em);
    }

    @Test
    @Transactional
    public void createDocNp() throws Exception {
        int databaseSizeBeforeCreate = docNpRepository.findAll().size();

        // Create the DocNp
        restDocNpMockMvc.perform(post("/api/doc-nps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docNp)))
            .andExpect(status().isCreated());

        // Validate the DocNp in the database
        List<DocNp> docNpList = docNpRepository.findAll();
        assertThat(docNpList).hasSize(databaseSizeBeforeCreate + 1);
        DocNp testDocNp = docNpList.get(docNpList.size() - 1);
        assertThat(testDocNp.getPatientId()).isEqualTo(DEFAULT_PATIENT_ID);
        assertThat(testDocNp.getEsignId()).isEqualTo(DEFAULT_ESIGN_ID);
    }

    @Test
    @Transactional
    public void createDocNpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = docNpRepository.findAll().size();

        // Create the DocNp with an existing ID
        docNp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDocNpMockMvc.perform(post("/api/doc-nps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docNp)))
            .andExpect(status().isBadRequest());

        // Validate the DocNp in the database
        List<DocNp> docNpList = docNpRepository.findAll();
        assertThat(docNpList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDocNps() throws Exception {
        // Initialize the database
        docNpRepository.saveAndFlush(docNp);

        // Get all the docNpList
        restDocNpMockMvc.perform(get("/api/doc-nps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(docNp.getId().intValue())))
            .andExpect(jsonPath("$.[*].patientId").value(hasItem(DEFAULT_PATIENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].esignId").value(hasItem(DEFAULT_ESIGN_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getDocNp() throws Exception {
        // Initialize the database
        docNpRepository.saveAndFlush(docNp);

        // Get the docNp
        restDocNpMockMvc.perform(get("/api/doc-nps/{id}", docNp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(docNp.getId().intValue()))
            .andExpect(jsonPath("$.patientId").value(DEFAULT_PATIENT_ID.intValue()))
            .andExpect(jsonPath("$.esignId").value(DEFAULT_ESIGN_ID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingDocNp() throws Exception {
        // Get the docNp
        restDocNpMockMvc.perform(get("/api/doc-nps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDocNp() throws Exception {
        // Initialize the database
        docNpRepository.saveAndFlush(docNp);

        int databaseSizeBeforeUpdate = docNpRepository.findAll().size();

        // Update the docNp
        DocNp updatedDocNp = docNpRepository.findById(docNp.getId()).get();
        // Disconnect from session so that the updates on updatedDocNp are not directly saved in db
        em.detach(updatedDocNp);
        updatedDocNp
            .patientId(UPDATED_PATIENT_ID)
            .esignId(UPDATED_ESIGN_ID);

        restDocNpMockMvc.perform(put("/api/doc-nps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDocNp)))
            .andExpect(status().isOk());

        // Validate the DocNp in the database
        List<DocNp> docNpList = docNpRepository.findAll();
        assertThat(docNpList).hasSize(databaseSizeBeforeUpdate);
        DocNp testDocNp = docNpList.get(docNpList.size() - 1);
        assertThat(testDocNp.getPatientId()).isEqualTo(UPDATED_PATIENT_ID);
        assertThat(testDocNp.getEsignId()).isEqualTo(UPDATED_ESIGN_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingDocNp() throws Exception {
        int databaseSizeBeforeUpdate = docNpRepository.findAll().size();

        // Create the DocNp

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDocNpMockMvc.perform(put("/api/doc-nps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(docNp)))
            .andExpect(status().isBadRequest());

        // Validate the DocNp in the database
        List<DocNp> docNpList = docNpRepository.findAll();
        assertThat(docNpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDocNp() throws Exception {
        // Initialize the database
        docNpRepository.saveAndFlush(docNp);

        int databaseSizeBeforeDelete = docNpRepository.findAll().size();

        // Get the docNp
        restDocNpMockMvc.perform(delete("/api/doc-nps/{id}", docNp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DocNp> docNpList = docNpRepository.findAll();
        assertThat(docNpList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DocNp.class);
        DocNp docNp1 = new DocNp();
        docNp1.setId(1L);
        DocNp docNp2 = new DocNp();
        docNp2.setId(docNp1.getId());
        assertThat(docNp1).isEqualTo(docNp2);
        docNp2.setId(2L);
        assertThat(docNp1).isNotEqualTo(docNp2);
        docNp1.setId(null);
        assertThat(docNp1).isNotEqualTo(docNp2);
    }
}
