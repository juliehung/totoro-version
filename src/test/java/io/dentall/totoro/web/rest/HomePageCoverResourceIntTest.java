package io.dentall.totoro.web.rest;

import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.domain.DocNp;
import io.dentall.totoro.domain.HomePageCover;
import io.dentall.totoro.domain.Image;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.enumeration.HomePageCoverSourceTable;
import io.dentall.totoro.repository.DocNpRepository;
import io.dentall.totoro.repository.HomePageCoverRepository;
import io.dentall.totoro.repository.ImageRepository;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.service.HomePageCoverQueryService;
import io.dentall.totoro.service.HomePageCoverServiceV1;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Optional;

import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;

/**
 * Test class for the HomePageCoverResource REST controller.
 *
 * @see HomePageCoverResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TotoroApp.class)
public class HomePageCoverResourceIntTest {

    private Logger logger = LoggerFactory.getLogger(HomePageCoverResourceIntTest.class);

    @Autowired
    private HomePageCoverServiceV1 homePageCoverService;

    @Autowired
    private HomePageCoverQueryService homePageCoverQueryService;

    @MockBean
    private DocNpRepository docNpRepository;

    @MockBean
    private ImageRepository imageRepository;

    @MockBean
    private PatientRepository patientRepository;

    @MockBean
    private HomePageCoverRepository homePageCoverRepository;

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

    private MockMvc restHomePageCoverMockMvc;

    private HomePageCover homePageCover;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HomePageCoverResource homePageCoverResource = new HomePageCoverResource(homePageCoverService, homePageCoverQueryService);
        this.restHomePageCoverMockMvc = MockMvcBuilders.standaloneSetup(homePageCoverResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    private Patient createPatient() {
        Patient patient = new Patient();
        patient.setId(1L);

        return patient;
    }

    private DocNp createDocNp() {
        DocNp docNp = new DocNp();
        docNp.setId(1L);

        return docNp;
    }

    private Image createImage() {
        Image image = new Image();
        image.setId(1L);

        return image;
    }

    private HomePageCover createHomePageCover() {
        return new HomePageCover().patientId(1L).sourceId(1L).sourceTable(HomePageCoverSourceTable.DOC_NP);
    }

    @Test
    public void testValidation1() {
        exceptionRule.expect(BadRequestAlertException.class);
        exceptionRule.expectMessage("Must fulfil patient id, source table, and source id");
        HomePageCover homePageCover = new HomePageCover();

        HomePageCover r = homePageCoverService.save(homePageCover);
    }

    @Test
    public void testValidation2() {
        exceptionRule.expect(BadRequestAlertException.class);
        exceptionRule.expectMessage("Patient id not exist");
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        HomePageCover homePageCover = new HomePageCover()
            .patientId(0L)
            .sourceId(1L)
            .sourceTable(HomePageCoverSourceTable.DOC_NP);

        HomePageCover r = homePageCoverService.save(homePageCover);
    }

    @Test
    public void testValidation3() {
        exceptionRule.expect(BadRequestAlertException.class);
        exceptionRule.expectMessage("DocNp id not exist");
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(createPatient()));
        Mockito.when(docNpRepository.findById(1L)).thenReturn(Optional.empty());

        HomePageCover homePageCover = new HomePageCover()
            .patientId(1L)
            .sourceId(1L)
            .sourceTable(HomePageCoverSourceTable.DOC_NP);

        HomePageCover r = homePageCoverService.save(homePageCover);
    }

    @Test
    public void testValidation4() {
        exceptionRule.expect(BadRequestAlertException.class);
        exceptionRule.expectMessage("Image id not exist");
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(createPatient()));
        Mockito.when(imageRepository.findById(1L)).thenReturn(Optional.empty());

        HomePageCover homePageCover = new HomePageCover()
            .patientId(1L)
            .sourceId(1L)
            .sourceTable(HomePageCoverSourceTable.IMAGE);

        HomePageCover r = homePageCoverService.save(homePageCover);
    }

    @Test
    public void testValidation5() {
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(createPatient()));
        Mockito.when(docNpRepository.findById(1L)).thenReturn(Optional.of(createDocNp()));

        HomePageCover homePageCover = new HomePageCover()
            .patientId(1L)
            .sourceId(1L)
            .sourceTable(HomePageCoverSourceTable.DOC_NP);

        HomePageCover r = homePageCoverService.save(homePageCover);
    }

    @Test
    public void testValidation6() {
        exceptionRule.expect(BadRequestAlertException.class);
        exceptionRule.expectMessage("Already mapping coverage to patient");
        Mockito.when(patientRepository.findById(1L)).thenReturn(Optional.of(createPatient()));
        Mockito.when(imageRepository.findById(1L)).thenReturn(Optional.of(createImage()));
        Mockito.when(homePageCoverRepository.findById(1L)).thenReturn(Optional.of(createHomePageCover()));

        HomePageCover homePageCover = new HomePageCover()
            .patientId(1L)
            .sourceId(1L)
            .sourceTable(HomePageCoverSourceTable.IMAGE);

        HomePageCover r = homePageCoverService.save(homePageCover);
    }

}
