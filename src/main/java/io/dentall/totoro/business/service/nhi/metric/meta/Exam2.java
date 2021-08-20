package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.codesByExam2;

/**
 * 一般牙科門診診察費(Xray)
 */
public class Exam2 extends Exam<Long> {

    public Exam2(Collector collector, Source<?, ?> source) {
        this(collector, null, source);
    }

    public Exam2(Collector collector, MetaConfig config, Source<?, ?> source) {
        super(collector, config, source);
    }

    @Override
    public Long doCalculate(Collector collector) {
        return doCalculateRegular(collector, codesByExam2);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Exam2;
    }

}
