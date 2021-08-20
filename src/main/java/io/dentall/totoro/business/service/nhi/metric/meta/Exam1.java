package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.codesByExam1;

/**
 * 一般牙科門診診察費(不含Xray)
 */
public class Exam1 extends Exam<Long> {

    public Exam1(Collector collector, Source<?, ?> source) {
        this(collector, null, source);
    }

    public Exam1(Collector collector, MetaConfig config, Source<?, ?> source) {
        super(collector, config, source);
    }

    @Override
    public Long doCalculate(Collector collector) {
        return doCalculateRegular(collector, codesByExam1);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Exam1;
    }

}
