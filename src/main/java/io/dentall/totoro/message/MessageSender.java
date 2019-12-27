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

    @Value("${queue.message}")
    private String queue;

    public MessageSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(Map<String, Object> message) {
        log.debug("send message[{}] to queue[{}]", message, queue);
        jmsTemplate.convertAndSend(queue, message);
    }
}
