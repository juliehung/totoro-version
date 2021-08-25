package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro5;

public class Tro5Config extends MetaConfig {

    public Tro5Config(MetricConfig metricConfig) {
        super(metricConfig);
        this.setUse00121CPoint(true);
        this.setExcludeHolidayPoint(true);
        this.setExclude(Tro5);
    }

}
