package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesByExam1;

/**
 * 一般牙科門診診察費(不含Xray)
 */
public class Exam1 extends Exam<Long> {

    public Exam1(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Exam1(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        return doCalculateRegular(metricConfig, CodesByExam1);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Exam1;
    }

}
