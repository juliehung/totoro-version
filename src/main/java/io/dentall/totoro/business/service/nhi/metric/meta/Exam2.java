package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.codesByExam2;

/**
 * 一般牙科門診診察費(Xray)
 */
public class Exam2 extends Exam<Long> {

    public Exam2(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Exam2(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        return doCalculateRegular(metricConfig, codesByExam2);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Exam2;
    }

}
