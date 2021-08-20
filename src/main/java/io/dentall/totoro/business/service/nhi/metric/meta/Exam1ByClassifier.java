package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.Map;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.codesByExam1;

/**
 * 一般牙科門診診察費(不含Xray)  病患點數 or 醫師點數
 */
public class Exam1ByClassifier extends Exam<Map<Long, Long>> {

    private final Function<NhiMetricRawVM, Long> classifier;

    private final MetaType metaType;

    public Exam1ByClassifier(Collector collector, MetaType metaType, String sourceName, Function<NhiMetricRawVM, Long> classifier) {
        this(collector, null, metaType, sourceName, classifier);
    }

    public Exam1ByClassifier(Collector collector, MetaConfig config, MetaType metaType, String sourceName, Function<NhiMetricRawVM, Long> classifier) {
        super(collector, config, sourceName);
        this.classifier = classifier;
        this.metaType = metaType;
    }

    @Override
    public Map<Long, Long> doCalculate(Collector collector) {
        return doCalculateByClassifier(collector, codesByExam1, classifier);
    }

    @Override
    public MetaType metaType() {
        return metaType;
    }

}
