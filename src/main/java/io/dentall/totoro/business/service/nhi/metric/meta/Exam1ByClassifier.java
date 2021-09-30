package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.Map;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesByExam1;

/**
 * 一般牙科門診診察費(不含Xray)  病患點數 or 醫師點數
 */
public class Exam1ByClassifier extends Exam<Map<Long, Long>> {

    private final Function<MetricTooth, Long> classifier;

    public Exam1ByClassifier(MetricConfig metricConfig, Source<?, ?> source, Function<MetricTooth, Long> classifier) {
        this(metricConfig, null, source, classifier);
    }

    public Exam1ByClassifier(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source, Function<MetricTooth, Long> classifier) {
        super(metricConfig, config, source);
        this.classifier = classifier;
    }

    @Override
    public Map<Long, Long> doCalculate(MetricConfig metricConfig) {
        return doCalculateByClassifier(metricConfig, CodesByExam1, classifier);
    }

}
