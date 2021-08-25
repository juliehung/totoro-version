package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

public abstract class SingleSourceMetaCalculator<T> extends AbstractMetaCalculator<T> {

    private final Source<?, ?> source;

    public SingleSourceMetaCalculator(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public SingleSourceMetaCalculator(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, new Source[]{source});
        this.source = source;
    }

    public Source<?, ?> source() {
        return source;
    }

}
