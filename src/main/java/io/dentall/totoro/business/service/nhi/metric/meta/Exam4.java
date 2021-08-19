package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.codesByExam4;

/**
 * 符合牙醫門診加強感染管制實施方案之牙科門診診察費(Xray)
 */
public class Exam4 extends Exam<Long> {

    public Exam4(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public Exam4(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        return doCalculateRegular(collector, codesByExam4);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Exam4;
    }

}
