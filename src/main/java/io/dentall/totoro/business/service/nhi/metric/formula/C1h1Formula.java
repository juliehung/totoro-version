package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.meta.Exam1;
import io.dentall.totoro.business.service.nhi.metric.meta.Exam2;
import io.dentall.totoro.business.service.nhi.metric.meta.Exam3;
import io.dentall.totoro.business.service.nhi.metric.meta.Exam4;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedDisposalSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

/**
 * (月)診察費點數
 * ＠date-15＠ (@Exam-1@+@Exam-2@+@Exam-3@+@Exam-4@)
 */
public class C1h1Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricDisposal, MetricDisposal> source;

    public C1h1Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedDisposalSource(metricConfig);
    }

    @Override
    protected BigDecimal doCalculate(MetricConfig metricConfig) {
        Exam1 exam1 = new Exam1(metricConfig, source).apply();
        Exam2 exam2 = new Exam2(metricConfig, source).apply();
        Exam3 exam3 = new Exam3(metricConfig, source).apply();
        Exam4 exam4 = new Exam4(metricConfig, source).apply();
        return new BigDecimal(exam1.getResult() + exam2.getResult() + exam3.getResult() + exam4.getResult());
    }

}
