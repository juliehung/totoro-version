package io.dentall.totoro.message;

import io.dentall.totoro.domain.Appointment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class AppointmentSender {

    private final Logger log = LoggerFactory.getLogger(AppointmentSender.class);

    private final JmsTemplate jmsTemplate;

    @Value("${queue.appointment}")
    private String queue;

    public AppointmentSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(Appointment appointment) {
        log.debug("send appointment[{}] to queue[{}]", appointment, queue);
        jmsTemplate.convertAndSend(queue, appointment);
    }
}
