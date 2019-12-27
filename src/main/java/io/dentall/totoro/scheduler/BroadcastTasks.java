package io.dentall.totoro.scheduler;

import io.dentall.totoro.domain.Appointment;
import io.dentall.totoro.service.AppointmentQueryService;
import io.dentall.totoro.service.BroadcastService;
import io.dentall.totoro.service.dto.AppointmentCriteria;
import io.github.jhipster.config.JHipsterConstants;
import io.github.jhipster.service.filter.InstantFilter;
import io.github.jhipster.service.filter.LongFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Profile("!" + JHipsterConstants.SPRING_PROFILE_TEST)
@Component
public class BroadcastTasks {

    private final Logger log = LoggerFactory.getLogger(BroadcastTasks.class);

    private final BroadcastService broadcastService;

    private final AppointmentQueryService appointmentQueryService;

    private Set<Appointment> notArrivedAppointments = new CopyOnWriteArraySet<>();

    private Set<Appointment> comingSoonAppointments = new CopyOnWriteArraySet<>();

    @Value("${scheduler.appointment.comingSoon}")
    private int comingSoon;

    public BroadcastTasks(BroadcastService broadcastService, AppointmentQueryService appointmentQueryService) {
        this.broadcastService = broadcastService;
        this.appointmentQueryService = appointmentQueryService;
    }

    @Scheduled(initialDelayString = "${scheduler.initialDelay}", fixedDelayString = "${scheduler.fixedDelay}")
    public void broadcastNotArrived() {
        removeSentAppointments(notArrivedAppointments);

        Instant start = OffsetDateTime.now().toZonedDateTime().with(LocalTime.MIN).toInstant();
        Instant end = OffsetDateTime.now().toInstant();

        broadcastAppointments(start, end, notArrivedAppointments, BroadcastService.APPOINTMENT_NOT_ARRIVED);
    }

    @Scheduled(initialDelayString = "${scheduler.initialDelay}", fixedDelayString = "${scheduler.fixedDelay}")
    public void broadcastComingSoon() {
        removeSentAppointments(comingSoonAppointments);

        Instant start = OffsetDateTime.now().toInstant();
        Instant end = OffsetDateTime.now().plusMinutes(comingSoon).toInstant();

        broadcastAppointments(start, end, comingSoonAppointments, BroadcastService.APPOINTMENT_COMING_SOON);
    }

    private void broadcastAppointments(Instant start, Instant end, Set<Appointment> sentAppointments, String message) {
        InstantFilter expectedArrivalTimeFilter = new InstantFilter();
        expectedArrivalTimeFilter.setGreaterOrEqualThan(start);
        expectedArrivalTimeFilter.setLessOrEqualThan(end);
        LongFilter registrationIdFilter = new LongFilter();
        registrationIdFilter.setSpecified(false);

        AppointmentCriteria criteria = new AppointmentCriteria();
        criteria.setExpectedArrivalTime(expectedArrivalTimeFilter);
        criteria.setRegistrationId(registrationIdFilter);

        List<Appointment> appointments = appointmentQueryService.findByCriteria(criteria);
        appointments.sort(Comparator.comparing(Appointment::getExpectedArrivalTime));
        appointments.removeAll(sentAppointments);

        for (Appointment appointment : appointments) {
            broadcastService.broadcastAppointmentStatus(appointment.getPatient().getName(), message);
            sentAppointments.add(appointment);
        }
    }

    private void removeSentAppointments(Set<Appointment> sentAppointments) {
        Instant start = OffsetDateTime.now().toZonedDateTime().with(LocalTime.MIN).toInstant();
        sentAppointments.removeIf(appointment -> appointment.getExpectedArrivalTime().compareTo(start) < 0);
    }
}
