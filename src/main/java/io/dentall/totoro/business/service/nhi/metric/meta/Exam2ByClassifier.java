package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.Map;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.metric.meta.ExamHelper.codesByExam2;

/**
 * 一般牙科門診診察費(Xray)  病患點數 or 醫師點數
 */
public class Exam2ByClassifier extends Exam<Map<Long, Long>> {

    private final Function<NhiMetricRawVM, Long> classifier;

    private final MetaType metaType;

    public Exam2ByClassifier(Collector collector, MetaType metaType, String sourceName, Function<NhiMetricRawVM, Long> classifier) {
        this(collector, null, metaType, sourceName, classifier);
    }

    public Exam2ByClassifier(Collector collector, MetaConfig config, MetaType metaType, String sourceName, Function<NhiMetricRawVM, Long> classifier) {
        super(collector, config, sourceName);
        this.classifier = classifier;
        this.metaType = metaType;
    }

    @Override
    public Map<Long, Long> doCalculate(Collector collector) {
        return doCalculateByClassifier(collector, codesByExam2, classifier);
    }

    @Override
    public MetaType metaType() {
        return metaType;
    }

}
