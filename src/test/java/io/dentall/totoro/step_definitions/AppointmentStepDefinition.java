package io.dentall.totoro.step_definitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.dentall.totoro.domain.Appointment;
import io.dentall.totoro.domain.ExtendUser;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.repository.TagRepository;
import io.dentall.totoro.service.AppointmentQueryService;
import io.dentall.totoro.service.AppointmentService;
import io.dentall.totoro.service.BroadcastService;
import io.dentall.totoro.step_definitions.holders.AppointmentTestInfoHolder;
import io.dentall.totoro.step_definitions.holders.PatientTestInfoHolder;
import io.dentall.totoro.step_definitions.holders.UserTestInfoHolder;
import io.dentall.totoro.web.rest.AppointmentResource;
import lombok.extern.slf4j.Slf4j;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import static io.dentall.totoro.service.util.DateTimeUtil.pastInstant;
import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@Slf4j
public class AppointmentStepDefinition extends AbstractStepDefinition {

    @Autowired
    private UserTestInfoHolder userTestInfoHolder;

    @Autowired
    private PatientTestInfoHolder patientTestInfoHolder;

    @Autowired
    private AppointmentTestInfoHolder appointmentTestInfoHolder;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentQueryService appointmentQueryService;

    @Autowired
    private BroadcastService broadcastService;

    @Autowired
    private TagRepository tagRepository;

    private String appointmentApiPath = "/api/appointments";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AppointmentResource resource = new AppointmentResource(appointmentService, appointmentQueryService, broadcastService, tagRepository);
        this.mvc = MockMvcBuilders.standaloneSetup(resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .build();
    }

    @Given("建立預約")
    public void createAppointment() throws Exception {
        User user = userTestInfoHolder.getUser();
        ExtendUser doctor = new ExtendUser();
        doctor.setId(user.getExtendUser().getId());
        Patient patient = patientTestInfoHolder.getPatient();
        Appointment appointmentReturn = doCreateAppointment(null, patient, doctor);

        appointmentTestInfoHolder.setAppointment(appointmentReturn);
    }

    @Given("在(過去) {monthAndDate} ，建立預約")
    @Given("在過去第 {int} 天，建立預約")
    public void createAppointmentPast(int pastDays) throws Exception {
        User user = userTestInfoHolder.getUser();
        ExtendUser doctor = new ExtendUser();
        doctor.setId(user.getExtendUser().getId());
        Patient patient = patientTestInfoHolder.getPatient();

        Appointment appointment = new Appointment();
        appointment.setExpectedArrivalTime(pastInstant(pastDays));

        Appointment appointmentReturn = doCreateAppointment(appointment, patient, doctor);

        appointmentTestInfoHolder.setAppointment(appointmentReturn);
    }

    @Then("預約建立成功")
    public void checkAppointment() {
        assertThat(appointmentTestInfoHolder.getAppointment().getId()).isNotNull();
    }

    @Then("確定預約是建立在過去第 {int} 天")
    public void checkAppointmentPast(int pastDays) {
        Appointment appointment = appointmentTestInfoHolder.getAppointment();
        assertThat(appointment.getExpectedArrivalTime().truncatedTo(ChronoUnit.DAYS))
            .isEqualTo(pastInstant(pastDays).truncatedTo(ChronoUnit.DAYS));
    }

    private Appointment doCreateAppointment(Appointment appointment, Patient patient, ExtendUser doctor) throws Exception {
        Appointment newAppointment = Optional.ofNullable(appointment).orElse(baseAppointment());
        newAppointment.setDoctor(doctor);
        newAppointment.setPatient(patient);

        MockHttpServletRequestBuilder requestBuilder = post(appointmentApiPath)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(newAppointment));

        ResultActions resultActions = this.mvc.perform(requestBuilder);
        Appointment appointmentReturn = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsByteArray(), Appointment.class);

        return appointmentReturn;
    }

    private Appointment baseAppointment() {
        Appointment appointment = new Appointment();
        appointment.setExpectedArrivalTime(Instant.now());
        appointment.setNote("cucumber");
        return appointment;
    }

}
