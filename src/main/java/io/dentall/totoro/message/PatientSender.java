package io.dentall.totoro.message;

import io.dentall.totoro.domain.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class PatientSender {

    private final Logger log = LoggerFactory.getLogger(PatientSender.class);

    private final JmsTemplate jmsTemplate;

    @Value("${queue.patient}")
    private String queue;

    public PatientSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(Patient patient) {
        log.debug("send patient[{}] to queue[{}]", patient, queue);
        jmsTemplate.convertAndSend(queue, patient);
    }
}
