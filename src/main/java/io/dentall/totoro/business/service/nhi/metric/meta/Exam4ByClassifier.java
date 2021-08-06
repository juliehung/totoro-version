package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static io.dentall.totoro.business.service.nhi.metric.meta.ExamHelper.calculateByClassifier;
import static io.dentall.totoro.business.service.nhi.metric.meta.ExamHelper.codesByExam4;

/**
 * 符合牙醫門診加強感染管制實施方案之牙科門診診察費(Xray) 病患點數 or 醫師點數
 */
public class Exam4ByClassifier extends SingleSourceCalculator<Map<Long, Long>> {

    private final Function<NhiMetricRawVM, Long> classifier;

    private final MetaType metaType;

    public Exam4ByClassifier(Collector collector, MetaType metaType, String sourceName, Function<NhiMetricRawVM, Long> classifier) {
        super(collector, sourceName);
        this.classifier = classifier;
        this.metaType = metaType;
    }

    @Override
    public Map<Long, Long> doCalculate(Collector collector) {
        List<NhiMetricRawVM> source = collector.retrieveSource(sourceName());
        return calculateByClassifier(source, codesByExam4, classifier);
    }

    @Override
    public MetaType metaType() {
        return metaType;
    }

}
