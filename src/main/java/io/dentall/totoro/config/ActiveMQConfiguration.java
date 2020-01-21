package io.dentall.totoro.config;

import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!no-queue")
@Configuration
public class ActiveMQConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public EmbeddedActiveMQ embeddedActiveMQ() {
        return new EmbeddedActiveMQ();
    }
}
