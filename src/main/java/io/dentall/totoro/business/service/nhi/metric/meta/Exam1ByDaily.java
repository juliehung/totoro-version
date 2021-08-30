package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.time.LocalDate;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesByExam1;

/**
 * 一般牙科門診診察費(不含Xray)
 */
public class Exam1ByDaily extends Exam<Map<LocalDate, Long>> {

    public Exam1ByDaily(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Exam1ByDaily(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(MetricConfig metricConfig) {
        return doCalculateByDaily(metricConfig, CodesByExam1);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Exam1ByDaily;
    }

}
