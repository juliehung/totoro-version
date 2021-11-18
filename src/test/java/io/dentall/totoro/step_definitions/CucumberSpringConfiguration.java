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
@SpringBootTest(classes = TotoroApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {NhiMetricServiceTest.class, MetricTestInfoHolder.class, TimeConfig.class}, initializers = CucumberSpringConfiguration.Initializer.class)
public class CucumberSpringConfiguration {

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                "spring.datasource.url=jdbc:postgresql://localhost:5432/totoro",
                "spring.datasource.username=totoro",
                "spring.datasource.password=totoro"
            ).applyTo(configurableApplicationContext.getEnvironment());
            TestPropertyValues.of(
                "zoneOffset=+08:00"
            ).applyTo(configurableApplicationContext.getEnvironment());

        }
    }

}
