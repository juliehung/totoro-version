package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.TreatmentCount;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.source.ThreeMonthNearSource;

import java.math.BigDecimal;

import static java.util.Arrays.asList;

/**
 * ＠date-9＠  89013C 總件數
 */
public class A17h1Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    public A17h1Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new ThreeMonthNearSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        TreatmentCount treatmentCount = new TreatmentCount(metricConfig, source, asList("89013C")).apply();
        return new BigDecimal(treatmentCount.getResult());
    }
}
