package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;

import java.time.LocalDate;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.codesByExam3;

/**
 * 符合牙醫門診加強感染管制實施方案之牙科門診診察費(不含Xray)
 */
public class Exam3ByDaily extends Exam<Map<LocalDate, Long>> {

    public Exam3ByDaily(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public Exam3ByDaily(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(Collector collector) {
        return doCalculateByDaily(collector, codesByExam3);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Exam3ByDaily;
    }

}
