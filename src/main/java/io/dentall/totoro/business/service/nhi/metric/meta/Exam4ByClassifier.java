package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.Map;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesByExam4;

/**
 * 符合牙醫門診加強感染管制實施方案之牙科門診診察費(Xray) 病患點數 or 醫師點數
 */
public class Exam4ByClassifier extends Exam<Map<Long, Long>> {

    private final Function<NhiMetricRawVM, Long> classifier;

    private final MetaType metaType;

    public Exam4ByClassifier(MetricConfig metricConfig, MetaType metaType, Source<?, ?> source, Function<NhiMetricRawVM, Long> classifier) {
        this(metricConfig, null, metaType, source, classifier);
    }

    public Exam4ByClassifier(MetricConfig metricConfig, MetaConfig config, MetaType metaType, Source<?, ?> source, Function<NhiMetricRawVM, Long> classifier) {
        super(metricConfig, config, source);
        this.classifier = classifier;
        this.metaType = metaType;
    }

    @Override
    public Map<Long, Long> doCalculate(MetricConfig metricConfig) {
        return doCalculateByClassifier(metricConfig, CodesByExam4, classifier);
    }

    @Override
    public MetaType metaType() {
        return metaType;
    }

}
