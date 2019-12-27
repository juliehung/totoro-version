package io.dentall.totoro.config;

import io.github.jhipster.config.JHipsterConstants;
import io.moquette.broker.Server;
import io.moquette.broker.config.ClasspathResourceLoader;
import io.moquette.broker.config.IConfig;
import io.moquette.broker.config.IResourceLoader;
import io.moquette.broker.config.ResourceLoaderConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;

@Profile("!" + JHipsterConstants.SPRING_PROFILE_TEST)
@Configuration
public class MoquetteConfiguration {

    private final Logger log = LoggerFactory.getLogger(MoquetteConfiguration.class);

    @Bean(destroyMethod = "stopServer")
    public Server embeddedMoquetteBrokerServer() throws IOException {
        IResourceLoader classpathLoader = new ClasspathResourceLoader();
        IConfig classPathConfig = new ResourceLoaderConfig(classpathLoader);
        Server mqttBroker = new Server();
        mqttBroker.startServer(classPathConfig);
        log.info("Starting MQTT on host: {} and port: {}", classPathConfig.getProperty("host"), classPathConfig.getProperty("port"));

        return mqttBroker;
    }
}
