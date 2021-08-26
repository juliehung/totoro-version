package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro5;
import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro6;

public class Tro6Config extends MetaConfig {

    public Tro6Config(MetricConfig metricConfig) {
        super(metricConfig);
        this.setExclude(Tro6);
    }

}
