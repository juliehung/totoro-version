package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

import static io.dentall.totoro.business.service.nhi.metric.meta.ExamHelper.codesByExam2;

/**
 * 一般牙科門診診察費(Xray)
 */
public class Exam2 extends Exam<Long> {

    public Exam2(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public Exam2(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
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
