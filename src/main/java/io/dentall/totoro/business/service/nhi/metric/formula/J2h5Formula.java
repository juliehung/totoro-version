package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Endo90015CTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.EndoTreatment;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toOppositePercentage;
import static java.math.BigDecimal.ZERO;

/**
 * 當季根管未完成率 "＠date-10＠ 的 [1–(90001C+90002C+90003C+90016C+90018C+90019C+90020C) ∕ 90015C]*100%"
 */
public class J2h5Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    public J2h5Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new QuarterSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        EndoTreatment endoTreatment = new EndoTreatment(metricConfig, source).apply();
        Endo90015CTreatment endo90015CTreatment = new Endo90015CTreatment(metricConfig, source).apply();
        try {
            return toOppositePercentage(endoTreatment.getResult(), endo90015CTreatment.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
