package io.dentall.totoro.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MessageSender {

    private final Logger log = LoggerFactory.getLogger(MessageSender.class);

    private final JmsTemplate jmsTemplate;

    @Value("${queue.appointment}")
    private String appointmentQueue;

    @Value("${queue.patient}")
    private String patientQueue;

    @Value("${queue.registration}")
    private String registrationQueue;

    @Value("${queue.disposal}")
    private String disposalQueue;

    @Value("${queue.message}")
    private String messageQueue;

    public MessageSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendAppointment(Map<String, Object> message) {
        log.debug("send appointment[{}] to queue[{}]", message, appointmentQueue);
        jmsTemplate.convertAndSend(appointmentQueue, message);
    }

    public void sendPatient(Map<String, Object> message) {
        log.debug("send patient[{}] to queue[{}]", message, patientQueue);
        jmsTemplate.convertAndSend(patientQueue, message);
    }

    public void sendRegistration(Map<String, Object> message) {
        log.debug("send registration[{}] to queue[{}]", message, registrationQueue);
        jmsTemplate.convertAndSend(registrationQueue, message);
    }

    public void sendDisposal(Map<String, Object> message) {
        log.debug("send disposal[{}] to queue[{}]", message, disposalQueue);
        jmsTemplate.convertAndSend(disposalQueue, message);
    }

    public void sendMessage(Map<String, Object> message) {
        log.debug("send message[{}] to queue[{}]", message, messageQueue);
        jmsTemplate.convertAndSend(messageQueue, message);
    }
}
