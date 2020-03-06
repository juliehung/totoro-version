package io.dentall.totoro.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisConfigurationCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!no-queue")
@Configuration
public class ArtemisConfig implements ArtemisConfigurationCustomizer {

    private final Logger log = LoggerFactory.getLogger(ArtemisConfig.class);

    @Value("${artemis.acceptor.netty.port}")
    private int port;

    @Override
    public void customize(org.apache.activemq.artemis.core.config.Configuration configuration) {
        try {
            configuration.addAcceptorConfiguration("netty", "tcp://0.0.0.0:" + port + "?protocols=AMQP,CORE");
            log.debug("Customize artemis netty transport acceptor");
        } catch (Exception e) {
            throw new RuntimeException("Failed to add netty transport acceptor to artemis instance");
        }
    }
}
