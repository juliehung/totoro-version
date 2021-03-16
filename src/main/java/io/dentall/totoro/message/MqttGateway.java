package io.dentall.totoro.message;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {

    void sendToMqtt(byte[] playload, @Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, @Header(MqttHeaders.RETAINED) boolean retained);
}
