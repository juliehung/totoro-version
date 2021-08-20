package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.Exam1;
import io.dentall.totoro.business.service.nhi.metric.meta.Exam2;
import io.dentall.totoro.business.service.nhi.metric.meta.Exam3;
import io.dentall.totoro.business.service.nhi.metric.meta.Exam4;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

/**
 * 診察費點數 ＠date-15＠ (@Exam-1@+@Exam-2@+@Exam-3@+@Exam-4@)
 */
public class L2Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public L2Formula(Collector collector, Source<NhiMetricRawVM, NhiMetricRawVM> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        Exam1 exam1 = new Exam1(collector, source.outputKey()).apply();
        Exam2 exam2 = new Exam2(collector, source.outputKey()).apply();
        Exam3 exam3 = new Exam3(collector, source.outputKey()).apply();
        Exam4 exam4 = new Exam4(collector, source.outputKey()).apply();
        long result = exam1.getResult() + exam2.getResult() + exam3.getResult() + exam4.getResult();

        return new BigDecimal(result);
    }
}
