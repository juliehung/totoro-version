package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;

public class Tro1Config extends MetaConfig {

    public Tro1Config(MetricConfig metricConfig) {
        super(metricConfig);
        this.setUse00121CPoint(true);
        this.setExcludeHideoutPoint(true);
        this.setUseOriginPoint(true);
        this.setExcludeHolidayPoint(true);
    }

}
