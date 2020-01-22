package io.dentall.totoro.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Version {

    Logger logger = LoggerFactory.getLogger(Version.class);



    private String version = "build-project-first";

    private Version() {
        try {
            Properties p = new Properties();
            p.load(new ClassPathResource("totoro.properties").getInputStream());
            this.version = p.getProperty("version");
        } catch (FileNotFoundException e) {
            logger.error("Can not found totoro.properties. " + e.getMessage());
        } catch (IOException e) {
            logger.error("Can not access totoro.properties. " + e.getMessage());
        }
    }

    @Bean
    @Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public static Version versionSingleton() {
        return new Version();
    }

    public String getVersion() {
        return version;
    }

}
