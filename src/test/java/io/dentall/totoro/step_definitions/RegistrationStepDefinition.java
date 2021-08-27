package io.dentall.totoro.step_definitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.dentall.totoro.domain.Appointment;
import io.dentall.totoro.domain.Registration;
import io.dentall.totoro.domain.enumeration.RegistrationStatus;
import io.dentall.totoro.repository.RegistrationRepository;
import io.dentall.totoro.repository.TagRepository;
import io.dentall.totoro.service.AppointmentQueryService;
import io.dentall.totoro.service.AppointmentService;
import io.dentall.totoro.service.BroadcastService;
import io.dentall.totoro.service.RegistrationService;
import io.dentall.totoro.step_definitions.holders.AppointmentTestInfoHolder;
import io.dentall.totoro.step_definitions.holders.RegistrationTestInfoHolder;
import io.dentall.totoro.web.rest.AppointmentResource;
import io.dentall.totoro.web.rest.RegistrationResource;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.Optional;

import static io.dentall.totoro.service.util.DateTimeUtil.pastInstant;
import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@Slf4j
public class RegistrationStepDefinition extends AbstractStepDefinition {

    @Autowired
    private AppointmentTestInfoHolder appointmentTestInfoHolder;

    @Autowired
    private RegistrationTestInfoHolder registrationTestInfoHolder;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private BroadcastService broadcastService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentQueryService appointmentQueryService;

    @Autowired
    private TagRepository tagRepository;

    private String registrationApiPath = "/api/registrations";

    private String appointmentApiPath = "/api/appointments";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegistrationResource resource1 = new RegistrationResource(registrationRepository, registrationService, broadcastService);
        final AppointmentResource resource2 = new AppointmentResource(appointmentService, appointmentQueryService, broadcastService, tagRepository);
        this.mvc = MockMvcBuilders.standaloneSetup(resource1, resource2)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @Given("建立掛號")
    public void createRegistration() throws Exception {
        Registration registration = doCreateRegistration();
        applyToAppointment(registration);
        registrationTestInfoHolder.setRegistration(registration);
    }

    @Given("在(過去) {monthAndDate} ，建立掛號")
    @Given("在過去第 {int} 天，建立掛號")
    public void createRegistration(int pastDays) throws Exception {
        Registration registration = baseRegistration();
        registration.setArrivalTime(pastInstant(pastDays));

        registration = doCreateRegistration(registration);
        applyToAppointment(registration);
        registrationTestInfoHolder.setRegistration(registration);
    }

    @Then("掛號建立成功")
    public void checkRegistration() {
        Registration registration = registrationTestInfoHolder.getRegistration();
        Assertions.assertThat(registration.getId()).isNotNull();
    }

    private Registration doCreateRegistration() throws Exception {
        return doCreateRegistration(null);
    }

    private Registration doCreateRegistration(Registration registration) throws Exception {
        Registration newRegistration = Optional.ofNullable(registration).orElse(baseRegistration());

        MockHttpServletRequestBuilder requestBuilder = post(registrationApiPath)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(newRegistration));

        ResultActions resultActions = this.mvc.perform(requestBuilder);
        Registration registrationReturn = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsByteArray(), Registration.class);

        return registrationReturn;
    }

    private Registration baseRegistration() {
        Registration registration = new Registration();
        registration.setArrivalTime(Instant.now());
        registration.setStatus(RegistrationStatus.IN_PROGRESS);

        return registration;
    }

    private void applyToAppointment(Registration registration) throws Exception {
        Appointment appointment = appointmentTestInfoHolder.getAppointment();

        if (appointment == null)
            return;

        appointment.setRegistration(registration);
        updateAppointment(appointment);
    }

    private Appointment updateAppointment(Appointment appointment) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = put(appointmentApiPath)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(appointment));

        ResultActions resultActions = this.mvc.perform(requestBuilder);
        Appointment appointmentReturn = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsByteArray(), Appointment.class);

        return appointmentReturn;
    }
}
