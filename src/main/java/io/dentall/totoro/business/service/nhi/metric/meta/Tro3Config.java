package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;

public class Tro3Config extends MetaConfig {

    public Tro3Config(MetricConfig metricConfig) {
        super(metricConfig);
        this.setExclude20000Point1ByDay(true);
    }

}
