package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

public class Point2 extends AbstractMetaCalculator<Long> {

    private final Source<MetricTooth, MetricTooth> source;

    private final Source<MetricDisposal, MetricDisposal> disposalSource;

    public Point2(MetricConfig metricConfig,
                  Source<MetricTooth, MetricTooth> source,
                  Source<MetricDisposal, MetricDisposal> disposalSource) {
        this(metricConfig, null, source, disposalSource);
    }

    public Point2(MetricConfig metricConfig,
                  MetaConfig config,
                  Source<MetricTooth, MetricTooth> source,
                  Source<MetricDisposal, MetricDisposal> disposalSource) {
        super(metricConfig, config, new Source[]{source, disposalSource});
        this.source = source;
        this.disposalSource = disposalSource;
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        MetaConfig config = getConfig();
        Point1 point1 = new Point1(metricConfig, config, source, disposalSource).apply();
        Point4 point4 = new Point4(metricConfig, config, disposalSource).apply();

        return point1.getResult() - point4.getResult();
    }

}
