package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

/**
 * 診療費
 */
public class Point1 extends SingleSourceMetaCalculator<Long> {

    public Point1(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Point1(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        MetaConfig config = getConfig();
        Exam1 exam1 = new Exam1(metricConfig, config, source()).apply();
        Exam2 exam2 = new Exam2(metricConfig, config, source()).apply();
        Exam3 exam3 = new Exam3(metricConfig, config, source()).apply();
        Exam4 exam4 = new Exam4(metricConfig, config, source()).apply();
        Point3 point3 = new Point3(metricConfig, config, source()).apply();

        return point3.getResult() + exam1.getResult() + exam2.getResult() + exam3.getResult() + exam4.getResult();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point1;
    }

}
