package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.time.LocalDate;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.codesByExam2;

/**
 * 一般牙科門診診察費(Xray)
 */
public class Exam2ByDaily extends Exam<Map<LocalDate, Long>> {

    public Exam2ByDaily(Collector collector, Source<?, ?> source) {
        this(collector, null, source);
    }

    public Exam2ByDaily(Collector collector, MetaConfig config, Source<?, ?> source) {
        super(collector, config, source);
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
