package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.TreatmentAndAgeCount;
import io.dentall.totoro.business.service.nhi.metric.meta.TreatmentCount;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.source.ThreeMonthNearSource;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.util.Collections.singletonList;

/**
 * ＠date-9＠ 治療時病患年齡<50歲的件數 / ＠date-9＠ 89013C 總件數 *100%
 */
public class A17h2Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    public A17h2Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new ThreeMonthNearSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        TreatmentAndAgeCount treatmentAndAgeCount =
            new TreatmentAndAgeCount(metricConfig, source, singletonList("89013C")).setUpperAge(49).apply();
        TreatmentCount treatmentCount = new TreatmentCount(metricConfig, source, singletonList("89013C")).apply();

        try {
            return toPercentage(treatmentAndAgeCount.getResult(), treatmentCount.getResult());
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }
}
