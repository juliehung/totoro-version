package io.dentall.totoro.service;

import io.dentall.totoro.domain.Appointment;
import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.Registration;
import io.dentall.totoro.domain.enumeration.RegistrationStatus;
import io.dentall.totoro.message.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BroadcastService {

    private final Logger log = LoggerFactory.getLogger(BroadcastService.class);

    private static final String REGISTRATION_PENDING = " 已掛號";

    private static final String REGISTRATION_FINISHED = " 完成治療";

    public static final String APPOINTMENT_NOT_ARRIVED = " 預約未到";

    public static final String APPOINTMENT_COMING_SOON = " 預約即將到來";

    private final MessageSender messageSender;

    public BroadcastService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void broadcastDomainId(Long id, Class domain) {
        if (domain == Appointment.class) {
            messageSender.sendAppointment(payloadTemplate(id));
        } else if (domain == Patient.class) {
            messageSender.sendPatient(payloadTemplate(id));
        } else if (domain == Registration.class) {
            messageSender.sendRegistration(payloadTemplate(id));
        } else if (domain == Disposal.class) {
            messageSender.sendDisposal(payloadTemplate(id));
        }
    }

    public void broadcastAppointmentStatus(String name, String message) {
        messageSender.sendMessage(payloadTemplate(name + message));
    }

    void broadcastRegistrationStatus(String name, RegistrationStatus status) {
        if (status == RegistrationStatus.PENDING) {
            messageSender.sendMessage(payloadTemplate(name + REGISTRATION_PENDING));
        } else if (status == RegistrationStatus.FINISHED) {
            messageSender.sendMessage(payloadTemplate(name + REGISTRATION_FINISHED));
        }
    }

    private Map<String, Object> payloadTemplate(String message) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("message", message);

        return payload;
    }

    private Map<String, Object> payloadTemplate(Long id) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("message", id);

        return payload;
    }
}
