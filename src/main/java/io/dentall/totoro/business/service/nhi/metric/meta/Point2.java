package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

public class Point2 extends SingleSourceMetaCalculator<Long> {

    public Point2(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Point2(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        MetaConfig config = getConfig();
        Point1 point1 = new Point1(metricConfig, config, source()).apply();
        Point4 point4 = new Point4(metricConfig, config, source()).apply();

        return point1.getResult() - point4.getResult();
    }

}
