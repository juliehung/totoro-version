package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

/**
 * 診療費
 */
public class Point1 extends AbstractMetaCalculator<Long> {

    private final Source<MetricTooth, MetricTooth> source;

    private final Source<MetricDisposal, MetricDisposal> disposalSource;

    public Point1(
        MetricConfig metricConfig,
        Source<MetricTooth, MetricTooth> source,
        Source<MetricDisposal, MetricDisposal> disposalSource) {
        this(metricConfig, null, source, disposalSource);
    }

    public Point1(
        MetricConfig metricConfig,
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
        Exam1 exam1 = new Exam1(metricConfig, config, disposalSource).apply();
        Exam2 exam2 = new Exam2(metricConfig, config, disposalSource).apply();
        Exam3 exam3 = new Exam3(metricConfig, config, disposalSource).apply();
        Exam4 exam4 = new Exam4(metricConfig, config, disposalSource).apply();
        Point3 point3 = new Point3(metricConfig, config, source).apply();

        return point3.getResult() + exam1.getResult() + exam2.getResult() + exam3.getResult() + exam4.getResult();
    }

}
