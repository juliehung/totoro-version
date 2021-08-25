package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

/**
 * @ OD-4@齒數
 * @ OD-5@齒數
 * @ OD-6@齒數
 */
public class Od456SurfaceCount extends SingleSourceMetaCalculator<Long> {

    public Od456SurfaceCount(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Od456SurfaceCount(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        MetaConfig config = getConfig();
        Od4TreatmentCount od4TreatmentCount = new Od4TreatmentCount(metricConfig, config, source()).apply();
        Od5TreatmentCount od5TreatmentCount = new Od5TreatmentCount(metricConfig, config, source()).apply();
        Od6TreatmentCount od6TreatmentCount = new Od6TreatmentCount(metricConfig, config, source()).apply();
        return od4TreatmentCount.getResult() + 2 * od5TreatmentCount.getResult() + 3 * od6TreatmentCount.getResult();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Od456SurfaceCount;
    }
}
