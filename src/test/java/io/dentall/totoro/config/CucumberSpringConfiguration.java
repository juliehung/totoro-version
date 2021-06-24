package io.dentall.totoro.config;

import io.cucumber.spring.CucumberContextConfiguration;
import io.dentall.totoro.TotoroApp;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = TotoroApp.class)
public class CucumberSpringConfiguration {
}
