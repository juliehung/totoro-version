package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

import java.time.LocalDate;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.codesByExam1;

/**
 * 一般牙科門診診察費(不含Xray)
 */
public class Exam1ByDaily extends Exam<Map<LocalDate, Long>> {

    public Exam1ByDaily(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public Exam1ByDaily(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    @Override
    public Map<LocalDate, Long> doCalculate(Collector collector) {
        return doCalculateByDaily(collector, codesByExam1);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Exam1ByDaily;
    }

}
