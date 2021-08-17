package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

import java.time.LocalDate;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.ExamHelper.codesByExam2;

/**
 * 一般牙科門診診察費(Xray)
 */
public class Exam2ByDaily extends Exam<Map<LocalDate, Long>> {

    public Exam2ByDaily(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public Exam2ByDaily(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(Collector collector) {
        return doCalculateByDaily(collector, codesByExam2);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Exam2ByDaily;
    }

}
