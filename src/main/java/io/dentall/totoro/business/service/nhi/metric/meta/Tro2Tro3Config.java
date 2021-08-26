package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro2;

public class Tro2Tro3Config extends MetaConfig {

    public Tro2Tro3Config(MetricConfig metricConfig) {
        super(metricConfig);
        this.setExclude20000Point1ByDay(true);
        this.setExclude(Tro2);
    }

}
