package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;

import io.dentall.totoro.domain.NHIUnusalContent;
import io.dentall.totoro.repository.NHIUnusalContentRepository;
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
 * Test class for the NHIUnusalContentResource REST controller.
 *
 * @see NHIUnusalContentResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class NHIUnusalContentResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_NO_SEQ_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NO_SEQ_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_GOT_SEQ_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_GOT_SEQ_NUMBER = "BBBBBBBBBB";

    @Autowired
    private NHIUnusalContentRepository nHIUnusalContentRepository;

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

    private MockMvc restNHIUnusalContentMockMvc;

    private NHIUnusalContent nHIUnusalContent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NHIUnusalContentResource nHIUnusalContentResource = new NHIUnusalContentResource(nHIUnusalContentRepository);
        this.restNHIUnusalContentMockMvc = MockMvcBuilders.standaloneSetup(nHIUnusalContentResource)
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
    public static NHIUnusalContent createEntity(EntityManager em) {
        NHIUnusalContent nHIUnusalContent = new NHIUnusalContent()
            .content(DEFAULT_CONTENT)
            .noSeqNumber(DEFAULT_NO_SEQ_NUMBER)
            .gotSeqNumber(DEFAULT_GOT_SEQ_NUMBER);
        return nHIUnusalContent;
    }

    @Before
    public void initTest() {
        nHIUnusalContent = createEntity(em);
    }

    @Test
    @Transactional
    public void createNHIUnusalContent() throws Exception {
        int databaseSizeBeforeCreate = nHIUnusalContentRepository.findAll().size();

        // Create the NHIUnusalContent
        restNHIUnusalContentMockMvc.perform(post("/api/nhi-unusal-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHIUnusalContent)))
            .andExpect(status().isCreated());

        // Validate the NHIUnusalContent in the database
        List<NHIUnusalContent> nHIUnusalContentList = nHIUnusalContentRepository.findAll();
        assertThat(nHIUnusalContentList).hasSize(databaseSizeBeforeCreate + 1);
        NHIUnusalContent testNHIUnusalContent = nHIUnusalContentList.get(nHIUnusalContentList.size() - 1);
        assertThat(testNHIUnusalContent.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testNHIUnusalContent.getNoSeqNumber()).isEqualTo(DEFAULT_NO_SEQ_NUMBER);
        assertThat(testNHIUnusalContent.getGotSeqNumber()).isEqualTo(DEFAULT_GOT_SEQ_NUMBER);
    }

    @Test
    @Transactional
    public void createNHIUnusalContentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nHIUnusalContentRepository.findAll().size();

        // Create the NHIUnusalContent with an existing ID
        nHIUnusalContent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNHIUnusalContentMockMvc.perform(post("/api/nhi-unusal-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHIUnusalContent)))
            .andExpect(status().isBadRequest());

        // Validate the NHIUnusalContent in the database
        List<NHIUnusalContent> nHIUnusalContentList = nHIUnusalContentRepository.findAll();
        assertThat(nHIUnusalContentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkContentIsRequired() throws Exception {
        int databaseSizeBeforeTest = nHIUnusalContentRepository.findAll().size();
        // set the field null
        nHIUnusalContent.setContent(null);

        // Create the NHIUnusalContent, which fails.

        restNHIUnusalContentMockMvc.perform(post("/api/nhi-unusal-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHIUnusalContent)))
            .andExpect(status().isBadRequest());

        List<NHIUnusalContent> nHIUnusalContentList = nHIUnusalContentRepository.findAll();
        assertThat(nHIUnusalContentList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNHIUnusalContents() throws Exception {
        // Initialize the database
        nHIUnusalContentRepository.saveAndFlush(nHIUnusalContent);

        // Get all the nHIUnusalContentList
        restNHIUnusalContentMockMvc.perform(get("/api/nhi-unusal-contents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nHIUnusalContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].noSeqNumber").value(hasItem(DEFAULT_NO_SEQ_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].gotSeqNumber").value(hasItem(DEFAULT_GOT_SEQ_NUMBER.toString())));
    }
    
    @Test
    @Transactional
    public void getNHIUnusalContent() throws Exception {
        // Initialize the database
        nHIUnusalContentRepository.saveAndFlush(nHIUnusalContent);

        // Get the nHIUnusalContent
        restNHIUnusalContentMockMvc.perform(get("/api/nhi-unusal-contents/{id}", nHIUnusalContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nHIUnusalContent.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.noSeqNumber").value(DEFAULT_NO_SEQ_NUMBER.toString()))
            .andExpect(jsonPath("$.gotSeqNumber").value(DEFAULT_GOT_SEQ_NUMBER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNHIUnusalContent() throws Exception {
        // Get the nHIUnusalContent
        restNHIUnusalContentMockMvc.perform(get("/api/nhi-unusal-contents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNHIUnusalContent() throws Exception {
        // Initialize the database
        nHIUnusalContentRepository.saveAndFlush(nHIUnusalContent);

        int databaseSizeBeforeUpdate = nHIUnusalContentRepository.findAll().size();

        // Update the nHIUnusalContent
        NHIUnusalContent updatedNHIUnusalContent = nHIUnusalContentRepository.findById(nHIUnusalContent.getId()).get();
        // Disconnect from session so that the updates on updatedNHIUnusalContent are not directly saved in db
        em.detach(updatedNHIUnusalContent);
        updatedNHIUnusalContent
            .content(UPDATED_CONTENT)
            .noSeqNumber(UPDATED_NO_SEQ_NUMBER)
            .gotSeqNumber(UPDATED_GOT_SEQ_NUMBER);

        restNHIUnusalContentMockMvc.perform(put("/api/nhi-unusal-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNHIUnusalContent)))
            .andExpect(status().isOk());

        // Validate the NHIUnusalContent in the database
        List<NHIUnusalContent> nHIUnusalContentList = nHIUnusalContentRepository.findAll();
        assertThat(nHIUnusalContentList).hasSize(databaseSizeBeforeUpdate);
        NHIUnusalContent testNHIUnusalContent = nHIUnusalContentList.get(nHIUnusalContentList.size() - 1);
        assertThat(testNHIUnusalContent.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testNHIUnusalContent.getNoSeqNumber()).isEqualTo(UPDATED_NO_SEQ_NUMBER);
        assertThat(testNHIUnusalContent.getGotSeqNumber()).isEqualTo(UPDATED_GOT_SEQ_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingNHIUnusalContent() throws Exception {
        int databaseSizeBeforeUpdate = nHIUnusalContentRepository.findAll().size();

        // Create the NHIUnusalContent

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNHIUnusalContentMockMvc.perform(put("/api/nhi-unusal-contents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nHIUnusalContent)))
            .andExpect(status().isBadRequest());

        // Validate the NHIUnusalContent in the database
        List<NHIUnusalContent> nHIUnusalContentList = nHIUnusalContentRepository.findAll();
        assertThat(nHIUnusalContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNHIUnusalContent() throws Exception {
        // Initialize the database
        nHIUnusalContentRepository.saveAndFlush(nHIUnusalContent);

        int databaseSizeBeforeDelete = nHIUnusalContentRepository.findAll().size();

        // Get the nHIUnusalContent
        restNHIUnusalContentMockMvc.perform(delete("/api/nhi-unusal-contents/{id}", nHIUnusalContent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<NHIUnusalContent> nHIUnusalContentList = nHIUnusalContentRepository.findAll();
        assertThat(nHIUnusalContentList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NHIUnusalContent.class);
        NHIUnusalContent nHIUnusalContent1 = new NHIUnusalContent();
        nHIUnusalContent1.setId(1L);
        NHIUnusalContent nHIUnusalContent2 = new NHIUnusalContent();
        nHIUnusalContent2.setId(nHIUnusalContent1.getId());
        assertThat(nHIUnusalContent1).isEqualTo(nHIUnusalContent2);
        nHIUnusalContent2.setId(2L);
        assertThat(nHIUnusalContent1).isNotEqualTo(nHIUnusalContent2);
        nHIUnusalContent1.setId(null);
        assertThat(nHIUnusalContent1).isNotEqualTo(nHIUnusalContent2);
    }
}
