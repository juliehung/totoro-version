package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.meta.Exam1;
import io.dentall.totoro.business.service.nhi.metric.meta.Exam2;
import io.dentall.totoro.business.service.nhi.metric.meta.Exam3Purge;
import io.dentall.totoro.business.service.nhi.metric.meta.Exam4Purge;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedDisposalSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

/**
 * 診察費合計(扣除感控差額)
 * ＠date-15＠ (@Exam-1@+@Exam-2@+@Exam-3@+@Exam-4@)
 */
public class L57Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricDisposal, MetricDisposal> source;

    public L57Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedDisposalSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Exam1 exam1 = new Exam1(metricConfig, source).apply();
        Exam2 exam2 = new Exam2(metricConfig, source).apply();
        Exam3Purge exam3 = new Exam3Purge(metricConfig, source).apply();
        Exam4Purge exam4 = new Exam4Purge(metricConfig, source).apply();
        return new BigDecimal(exam1.getResult() + exam2.getResult() + exam3.getResult() + exam4.getResult());
    }
}
