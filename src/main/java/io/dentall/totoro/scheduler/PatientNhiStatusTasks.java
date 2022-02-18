package io.dentall.totoro.scheduler;

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

    int count = 0;

    @Scheduled(
//        cron = "0 0 7 * * *",
        cron = "0 * * * * *",
        zone = "Asia/Taipei"
    )
    @Transactional
    public void updateTodayAppointmentPatientNhiStatus() {
        if (count > 0) {
            return ;
        }

        count = 1;

        try {
            LocalDate localDate = LocalDate.now();
            ZonedDateTime begin = ZonedDateTime.of(localDate, LocalTime.MIN, TimeConfig.ZONE_OFF_SET);
            ZonedDateTime end = ZonedDateTime.of(localDate, LocalTime.MAX, TimeConfig.ZONE_OFF_SET);

            List<UWPRegistrationPageVM> todayAppointments =
                appointmentService.findAppointmentWithTonsOfDataForUWPRegistrationPage(
                    begin.toInstant(),
                    end.toInstant()
                );

            List<Patient> todayPatient = new ArrayList<>();

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

                    Patient p = patientOpt.get();
                    patientService.updatePatientNhiStatus(p);
                    todayPatient.add(p);
                });

            patientRepository.saveAll(todayPatient);
        } catch (Exception e) {
            log.error("[PatientNhiStatusTasks] " + e.getMessage().toString());
        }
    }

}
