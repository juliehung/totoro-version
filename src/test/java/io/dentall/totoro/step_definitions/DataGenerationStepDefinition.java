package io.dentall.totoro.step_definitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.dentall.totoro.business.service.nhi.util.NhiRuleCheckUtil;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.domain.enumeration.DisposalStatus;
import io.dentall.totoro.repository.DisposalRepository;
import io.dentall.totoro.repository.NhiExtendDisposalRepository;
import io.dentall.totoro.repository.UserRepository;
import io.dentall.totoro.service.DisposalQueryService;
import io.dentall.totoro.service.DisposalService;
import io.dentall.totoro.service.NhiExtendDisposalService;
import io.dentall.totoro.service.NhiService;
import io.dentall.totoro.service.util.DateTimeUtil;
import io.dentall.totoro.util.DomainGenerator;
import io.dentall.totoro.web.rest.DisposalResource;
import io.dentall.totoro.web.rest.TestUtil;
import io.dentall.totoro.web.rest.errors.ExceptionTranslator;
import org.junit.Assert;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@CucumberContextConfiguration
@SpringBootTest
public class DataGenerationStepDefinition {

    @Autowired
    private DisposalRepository disposalRepository;

    @Autowired
    private DisposalService disposalService;

    @Autowired
    private DisposalQueryService disposalQueryService;

    @Autowired
    private NhiExtendDisposalService nhiExtendDisposalService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private NhiService nhiService;

    @Autowired
    private NhiRuleCheckUtil nhiRuleCheckUtil;

    @Autowired
    private NhiExtendDisposalRepository nhiExtendDisposalRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    DomainGenerator domainGenerator;

    private MockMvc restDisposalMockMvc;

    private Disposal disposal;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DisposalResource disposalResource = new DisposalResource(disposalService, disposalQueryService, nhiService, nhiExtendDisposalRepository, userRepository, null);
        this.restDisposalMockMvc = MockMvcBuilders.standaloneSetup(disposalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .build();

    }

    @Given("產生指定id為 {long} 的病患，在日期為 {string} 就醫類別為 {string} 的處置")
    public void generate_disposal(
        Long patientId,
        String disposalDate,
        String nhiCategory
    ) throws Exception {
        disposal = new Disposal();
        disposal.status(DisposalStatus.PERMANENT)
            .dateTime(DateTimeUtil.transformROCDateToLocalDate(disposalDate).atStartOfDay().toInstant(TimeConfig.ZONE_OFF_SET));
        restDisposalMockMvc.perform(post("/api/disposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(disposal)))
            .andExpect(status().isCreated());
    }

    @And("加入健保代碼為 {string}，牙位為 {string}，牙面為 {string}")
    public void generate_treatment_procedure(
        String nhiCode,
        String teeth,
        String surface
    ) {
        // TODO
    }

    @Then("Hi i'm cucumber")
    public void hi_im_cucumber() {
        List<Disposal> disposalList = disposalRepository.findAll();
        Assert.assertNotNull(disposalList);
        Assert.assertEquals(1, disposalList.size());
    }
}
