package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.meta.ExamHelper.codesByExam2;

/**
 * 一般牙科門診診察費(Xray)
 */
public class Exam2 extends Exam<Long> {

    public Exam2(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    public Exam2(Collector collector, String sourceName, boolean use00121CPoint) {
        super(collector, sourceName, use00121CPoint);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> source = collector.retrieveSource(sourceName());
        return ExamHelper.calculate(source, codesByExam2, use00121CPoint);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Exam2;
    }

}
