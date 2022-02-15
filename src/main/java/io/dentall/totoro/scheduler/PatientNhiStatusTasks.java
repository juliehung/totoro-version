package io.dentall.totoro.scheduler;

import io.dentall.totoro.business.service.nhi.NhiRuleCheckDTO;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.service.AppointmentService;
import io.dentall.totoro.service.PatientService;
import io.dentall.totoro.web.rest.vm.UWPRegistrationPageVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Profile("!no-patient-nhi-status-task")
@Component
public class PatientNhiStatusTasks {

    private final Logger log = LoggerFactory.getLogger(PatientNhiStatusTasks.class);

    private final PatientService patientService;

    private final AppointmentService appointmentService;

    private final PatientRepository patientRepository;

    public PatientNhiStatusTasks(
        PatientService patientService,
        AppointmentService appointmentService,
        PatientRepository patientRepository
    ) {
        this.patientService = patientService;
        this.appointmentService = appointmentService;
        this.patientRepository = patientRepository;
    }

    @Scheduled(
        cron = "0 0 7 * * *",
        zone = "Asia/Taipei"
    )
    @Transactional
    public void updateCurrentAppointmentPatientNhiStatus() {

        LocalDate localDate = LocalDate.now();
        ZonedDateTime begin = ZonedDateTime.of(localDate, LocalTime.MIN, TimeConfig.ZONE_OFF_SET);
        ZonedDateTime end = ZonedDateTime.of(localDate, LocalTime.MAX, TimeConfig.ZONE_OFF_SET);

        List<UWPRegistrationPageVM> todayAppointments =
            appointmentService.findAppointmentWithTonsOfDataForUWPRegistrationPage(
                begin.toInstant(),
                end.toInstant()
            );

        List<NhiStatus> todayPatientNhiStatusList = new ArrayList<>();

        todayAppointments.stream()
            .parallel()
            .forEach(todayAppointment -> {
                if (todayAppointment.getPatientId() == null) {
                    return;
                }

                Optional<Patient> patientOpt = patientService.findPatientById(todayAppointment.getPatientId());
                if (!patientOpt.isPresent()) {
                    return;
                }

                NhiStatus result = new NhiStatus().patient(patientOpt.get());

                NhiRuleCheckDTO dtoFor81 = patientService.createNhiRuleCheckDto(todayAppointment.getPatientId(), PatientService.NHI_STATUS_81);
                NhiRuleCheckResultVM vm81 = patientService.calculateNhiStatus81(dtoFor81);
                if (vm81.getMessages() != null &&
                    vm81.getMessages().size() > 0
                ) {
                    result.setNhiStatus81Message(vm81.getMessages().get(0));
                }

                NhiRuleCheckDTO dtoFor91004C = patientService.createNhiRuleCheckDto(todayAppointment.getPatientId(), PatientService.NHI_STATUS_91004C);
                NhiRuleCheckResultVM vm91004C = patientService.calculateNhiStatus91004C(dtoFor91004C);
                if (vm91004C.getMessages() != null &&
                    vm91004C.getMessages().size() > 0
                ) {
                    result.setNhiStatus91004CMessage(vm91004C.getMessages().get(0));
                }

                todayPatientNhiStatusList.add(result);
            });

        patientRepository.saveAll(todayPatientNhiStatusList.stream().map(NhiStatus::getPatient).collect(Collectors.toList()));
    }

    class NhiStatus {
        private Patient patient;

        public NhiStatus patient(Patient patient) {
            this.patient = patient;
            return this;
        }

        public Patient getPatient() {
            return patient;
        }

        public void setPatient(Patient patient) {
            this.patient = patient;
        }

        public void setNhiStatus81Message(String nhiStatus81Message) {
            this.patient.setNhiStatus81(nhiStatus81Message);
        }

        public void setNhiStatus91004CMessage(String nhiStatus91004CMessage) {
            this.patient.setNhiStatus91004C(nhiStatus91004CMessage);
        }
    }

}
