package io.dentall.totoro.config;

import org.springframework.context.annotation.Configuration;

public class PrometheusConfig implements io.micrometer.prometheus.PrometheusConfig {

    @Override
    public String get(String key) {
        return null;
    }
}
