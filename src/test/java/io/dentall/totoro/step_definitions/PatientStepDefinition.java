package io.dentall.totoro.step_definitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.TagRepository;
import io.dentall.totoro.service.AvatarService;
import io.dentall.totoro.service.BroadcastService;
import io.dentall.totoro.service.PatientService;
import io.dentall.totoro.step_definitions.holders.PatientTestInfoHolder;
import io.dentall.totoro.web.rest.PatientResource;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


public class PatientStepDefinition extends AbstractStepDefinition {

    @Autowired
    private PatientTestInfoHolder patientTestInfoHolder;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private AvatarService avatarService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private BroadcastService broadcastService;

    private String apiPath = "/api/patients";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PatientResource resource = new PatientResource(patientRepository, tagRepository, avatarService, patientService, broadcastService);
        this.mvc = MockMvcBuilders.standaloneSetup(resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @Given("{word} {int} 歲病人")
    public void createPatient(String name, int age) throws Exception {
        LocalDate birthDate = LocalDate.now().minus(age, ChronoUnit.YEARS);
        Patient patient = new Patient();
        patient.setName(name);
        patient.setDisplayName(name);
        patient.setPhone("0920333777");
        patient.setBirth(birthDate);
        patient.setNationalId(randomAlphabetic(10));

        MockHttpServletRequestBuilder requestBuilder = post(apiPath)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(patient));

        ResultActions resultActions = this.mvc.perform(requestBuilder);
        Patient patientReturn = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsByteArray(), Patient.class);

        Patient saved = patientRepository.findById(patientReturn.getId()).get();

        patientTestInfoHolder.setPatient(saved);
        patientTestInfoHolder.getPatients().add(saved);
        patientTestInfoHolder.getPatientMap().put(saved.getName(), saved);
    }

}
