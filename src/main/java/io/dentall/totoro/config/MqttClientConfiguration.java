package io.dentall.totoro.config;

import io.moquette.broker.Server;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!no-queue")
@Configuration
public class MqttClientConfiguration {

    @Bean(destroyMethod = "disconnect")
    public IMqttClient mqttClient(
        @Value("${mqtt.clientId}") String clientId,
        @Value("${mqtt.hostname}") String hostname,
        @Value("${mqtt.port}") int port,
        MqttConnectOptions mqttConnectOptions,
        Server server
    ) throws MqttException {
        IMqttClient mqttClient = new MqttClient("tcp://" + hostname + ":" + port, clientId, new MemoryPersistence());
        mqttClient.connect(mqttConnectOptions);

        return mqttClient;
    }

    @Bean
    @ConfigurationProperties(prefix = "mqtt")
    public MqttConnectOptions mqttConnectOptions() {
        return new MqttConnectOptions();
    }
}
