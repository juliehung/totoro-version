package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.time.LocalDate;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.codesByExam4;

/**
 * 符合牙醫門診加強感染管制實施方案之牙科門診診察費(Xray)
 */
public class Exam4ByDaily extends Exam<Map<LocalDate, Long>> {

    public Exam4ByDaily(Collector collector, Source<?, ?> source) {
        super(collector, null, source);
    }

    public Exam4ByDaily(Collector collector, MetaConfig config, Source<?, ?> source) {
        super(collector, config, source);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(Collector collector) {
        return doCalculateByDaily(collector, codesByExam4);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Exam4ByDaily;
    }

}
