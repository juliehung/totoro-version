package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro2;

public class Tro2Config extends MetaConfig {

    public Tro2Config(MetricConfig metricConfig) {
        super(metricConfig);
        this.setExcludeHolidayPoint(true);
        this.setExclude(Tro2);
    }

}
