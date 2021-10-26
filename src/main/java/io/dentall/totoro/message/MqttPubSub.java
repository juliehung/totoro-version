package io.dentall.totoro.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Profile("!no-queue")
@Component
public class MqttPubSub {

    private final Logger log = LoggerFactory.getLogger(MqttPubSub.class);

    private final MqttGateway mqttGateway;

    private final ObjectMapper mapper;

    public MqttPubSub(MqttGateway mqttGateway, ObjectMapper mapper) {
        this.mqttGateway = mqttGateway;
        this.mapper = mapper;
    }

    @JmsListener(destination = "${queue.appointment}")
    public void receiveAppointment(Map<String, Object> message) {
        publish("totoro/appointment", message, 0, true);
    }

    @JmsListener(destination = "${queue.patient}")
    public void receivePatient(Map<String, Object> message) {
        publish("totoro/patient", message, 0, true);
    }

    @JmsListener(destination = "${queue.registration}")
    public void receiveRegistration(Map<String, Object> message) {
        publish("totoro/registration", message, 0, true);
    }

    @JmsListener(destination = "${queue.disposal}")
    public void receiveDisposal(Map<String, Object> message) {
        publish("totoro/disposal", message, 0, true);
    }

    @JmsListener(destination = "${queue.message}")
    public void receiveMessage(Map<String, Object> message) {
        publish("totoro/message", message, 0, true);
    }

    private void publish(String topic, Object payload, int qos, boolean retained) {
        try {
            mqttGateway.sendToMqtt(mapper.writeValueAsString(payload).getBytes(), topic, qos, retained);
            log.debug("mqttClient publish: topic[{}], payload[{}]", topic, payload);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException: {}", payload);
        }
    }
}
