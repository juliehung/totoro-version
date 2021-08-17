package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

import static io.dentall.totoro.business.service.nhi.metric.meta.ExamHelper.codesByExam1;

/**
 * 一般牙科門診診察費(不含Xray)
 */
public class Exam1 extends Exam<Long> {

    public Exam1(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public Exam1(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
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
