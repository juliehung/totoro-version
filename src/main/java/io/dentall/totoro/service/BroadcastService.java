package io.dentall.totoro.service;

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

    private final int SETTING = 0;

    private final int NOTIFICATION = 1;

    public static final String APPOINTMENT_NOT_ARRIVED = " 預約未到";

    public static final String APPOINTMENT_COMING_SOON = " 預約即將到來";

    public static final String REGISTRATION_PENDING = " 已掛號";

    public static final String REGISTRATION_FINISHED = " 完成治療";

    private final MessageSender messageSender;

    public BroadcastService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void broadcastAppointmentStatus(String name, String message) {
        messageSender.send(payloadTemplate(NOTIFICATION, name, message));
    }

    void broadcastRegistrationStatus(String name, RegistrationStatus status) {
        if (status == RegistrationStatus.PENDING) {
            messageSender.send(payloadTemplate(NOTIFICATION, name, REGISTRATION_PENDING));
        } else if (status == RegistrationStatus.FINISHED) {
            messageSender.send(payloadTemplate(NOTIFICATION, name, REGISTRATION_FINISHED));
        }
    }

    private Map<String, Object> payloadTemplate(int type, String name, String message) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("type", type);
        payload.put("message", name + message);

        return payload;
    }
}
