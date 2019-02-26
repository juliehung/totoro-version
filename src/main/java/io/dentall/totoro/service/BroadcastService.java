package io.dentall.totoro.service;

import io.dentall.totoro.domain.enumeration.RegistrationStatus;
import io.dentall.totoro.handler.BroadcastWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BroadcastService {

    private final Logger log = LoggerFactory.getLogger(BroadcastService.class);

    private final BroadcastWebSocket webSocket;

    public BroadcastService(BroadcastWebSocket webSocket) {
        this.webSocket = webSocket;
    }

    public void broadcastAppointmentStatus(String name, String message) {
        webSocket.broadcast(webSocket.payloadTemplate(BroadcastWebSocket.NOTIFICATION, name, message));
    }

    void broadcastRegistrationStatus(String name, RegistrationStatus status) {
        if (status == RegistrationStatus.PENDING) {
            webSocket.broadcast(webSocket.payloadTemplate(BroadcastWebSocket.NOTIFICATION, name, BroadcastWebSocket.REGISTRATION_PENDING));
        } else if (status == RegistrationStatus.FINISHED) {
            webSocket.broadcast(webSocket.payloadTemplate(BroadcastWebSocket.NOTIFICATION, name, BroadcastWebSocket.REGISTRATION_FINISHED));
        }
    }
}
