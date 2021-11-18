package io.dentall.totoro.step_definitions;

import io.cucumber.spring.CucumberContextConfiguration;
import io.dentall.totoro.TotoroApp;
import io.dentall.totoro.config.TimeConfig;
import io.dentall.totoro.service.NhiMetricServiceTest;
import io.dentall.totoro.step_definitions.holders.MetricTestInfoHolder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(classes = TotoroApp.class)
@ContextConfiguration(classes = {NhiMetricServiceTest.class, MetricTestInfoHolder.class, TimeConfig.class}, initializers = CucumberSpringConfiguration.Initializer.class)
public class CucumberSpringConfiguration {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            // Initialize and start test containers
            PostgresContainerConfiguration.initTestContainers();

            TestPropertyValues.of(
                "spring.datasource.url=" + PostgresContainerConfiguration.getJdbcUrl(),
                "spring.datasource.username=" + PostgresContainerConfiguration.getUsername(),
                "spring.datasource.password=" + PostgresContainerConfiguration.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
            TestPropertyValues.of(
                "zoneOffset=+08:00"
            ).applyTo(configurableApplicationContext.getEnvironment());

        }
    }

}
