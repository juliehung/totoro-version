package io.dentall.totoro.config;

import io.dentall.totoro.message.DefaultJmsErrorHandler;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

@Profile("!no-queue")
@Configuration
public class ActiveMQConfiguration {

    @Bean(initMethod = "start", destroyMethod = "stop")
    public EmbeddedActiveMQ embeddedActiveMQ() {
        EmbeddedActiveMQ server = new EmbeddedActiveMQ();
        server.setConfigResourcePath("broker.xml");
        return server;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
        DefaultJmsListenerContainerFactoryConfigurer configurer,
        ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setErrorHandler(new DefaultJmsErrorHandler());
        configurer.configure(factory, connectionFactory);
        return factory;
    }

}
