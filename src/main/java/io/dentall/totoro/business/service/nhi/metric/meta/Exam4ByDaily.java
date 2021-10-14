package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.time.LocalDate;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesByExam4;

/**
 * 符合牙醫門診加強感染管制實施方案之牙科門診診察費(Xray)
 */
public class Exam4ByDaily extends Exam<Map<LocalDate, Long>> {

    public Exam4ByDaily(MetricConfig metricConfig, Source<?, ?> source) {
        super(metricConfig, null, source);
    }

    public Exam4ByDaily(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(MetricConfig metricConfig) {
        return doCalculateByDaily(metricConfig, CodesByExam4);
    }

}
