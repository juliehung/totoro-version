package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.NhiCategory_SpecificCode_Group1;

public class Category1416SpecialG9Config extends MetaConfig {

    public Category1416SpecialG9Config(MetricConfig metricConfig) {
        super(metricConfig);
        this.setExclude(NhiCategory_SpecificCode_Group1);
    }

}
