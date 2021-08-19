package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.codesByExam3;

/**
 * 符合牙醫門診加強感染管制實施方案之牙科門診診察費(不含Xray)
 */
public class Exam3 extends Exam<Long> {

    public Exam3(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public Exam3(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        return doCalculateRegular(collector, codesByExam3);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Exam3;
    }

}
