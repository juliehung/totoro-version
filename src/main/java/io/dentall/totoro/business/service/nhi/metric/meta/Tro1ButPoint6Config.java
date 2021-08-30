package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;

public class Tro1ButPoint6Config extends Tro1Config {

    public Tro1ButPoint6Config(MetricConfig metricConfig) {
        super(metricConfig);
        this.setIncludePoint6By12MPoints(true);
    }

}
