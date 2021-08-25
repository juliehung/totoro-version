package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.NhiCategory1416;

public class Category1416Config extends MetaConfig {

    public Category1416Config(MetricConfig metricConfig) {
        super(metricConfig);
        this.setExclude(NhiCategory1416);
    }

}
