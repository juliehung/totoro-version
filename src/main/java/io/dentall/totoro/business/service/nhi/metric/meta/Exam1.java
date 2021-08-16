package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.meta.ExamHelper.codesByExam1;

/**
 * 一般牙科門診診察費(不含Xray)
 */
public class Exam1 extends Exam<Long> {

    public Exam1(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    public Exam1(Collector collector, String sourceName, boolean use00121CPoint) {
        super(collector, sourceName, use00121CPoint);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> source = collector.retrieveSource(sourceName());
        return ExamHelper.calculate(source, codesByExam1, use00121CPoint);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Exam1;
    }

}
