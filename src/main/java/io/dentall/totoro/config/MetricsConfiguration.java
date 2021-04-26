package io.dentall.totoro.config;

import com.ryantenney.metrics.spring.config.annotation.MetricsConfigurerAdapter;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public class MetricsConfiguration extends MetricsConfigurerAdapter {

    private final Logger log = LoggerFactory.getLogger(MetricsConfiguration.class);

    @Autowired
    private PrometheusMeterRegistry prometheusMeterRegistry;

    @PostConstruct
    public void init() {
    }
}
