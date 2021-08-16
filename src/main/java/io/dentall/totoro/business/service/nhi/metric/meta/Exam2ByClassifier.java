package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.metric.meta.ExamHelper.calculateByClassifier;
import static io.dentall.totoro.business.service.nhi.metric.meta.ExamHelper.codesByExam2;

/**
 * 一般牙科門診診察費(Xray)  病患點數 or 醫師點數
 */
public class Exam2ByClassifier extends Exam<Map<Long, Long>> {

    private final Function<NhiMetricRawVM, Long> classifier;

    private final MetaType metaType;

    public Exam2ByClassifier(Collector collector, MetaType metaType, String sourceName, Function<NhiMetricRawVM, Long> classifier) {
        super(collector, sourceName);
        this.classifier = classifier;
        this.metaType = metaType;
    }

    public Exam2ByClassifier(Collector collector, MetaType metaType, String sourceName, Function<NhiMetricRawVM, Long> classifier, boolean use00121CPoint) {
        super(collector, sourceName, use00121CPoint);
        this.classifier = classifier;
        this.metaType = metaType;
    }

    @Override
    public Map<Long, Long> doCalculate(Collector collector) {
        List<NhiMetricRawVM> source = collector.retrieveSource(sourceName());
        return calculateByClassifier(source, codesByExam2, classifier, use00121CPoint);
    }

    @Override
    public MetaType metaType() {
        return metaType;
    }

}
