package io.dentall.totoro.config;

import io.moquette.BrokerConstants;
import io.moquette.broker.Server;
import io.moquette.broker.config.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.util.Properties;

@Profile("!no-queue")
@Configuration
public class MoquetteConfiguration {

    private final Logger log = LoggerFactory.getLogger(MoquetteConfiguration.class);

    @Value("${moquette.port:1883}")
    private int port;

    @Value("${moquette.host:0.0.0.0}")
    private String host;

    @Value("${moquette.websocket_port:8081}")
    private int websocketPort;

    @Bean(destroyMethod = "stopServer")
    public Server embeddedMoquetteBrokerServer() throws IOException {
        MemoryConfig config = new MemoryConfig(new Properties());
        config.setProperty("port", Integer.toString(port));
        config.setProperty("host", host);
        config.setProperty("websocket_port", Integer.toString(websocketPort));
        config.setProperty(BrokerConstants.ALLOW_ANONYMOUS_PROPERTY_NAME, "true");

        Server mqttBroker = new Server();
        mqttBroker.startServer(config);
        log.info("Starting MQTT on host: {} and port: {}", host, port);

        return mqttBroker;
    }
}
