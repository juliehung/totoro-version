package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Endo90015CTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.EndoTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1ButPoint6Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.source.ThreeMonthNearSource;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toOppositePercentage;
import static java.math.BigDecimal.ZERO;

/**
 * 近三月根管未完成率 ＠date-15＠ 的 [1–(90001C+90002C+90003C+90016C+90018C+90019C+90020C) ∕ 90015C]*100%
 */
public class H4Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    public H4Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new ThreeMonthNearSource(metricConfig);
        this.source.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1ButPoint6Config config = new Tro1ButPoint6Config();
        EndoTreatment endoTreatment = new EndoTreatment(metricConfig, config, source).apply();
        Endo90015CTreatment endo90015CTreatment = new Endo90015CTreatment(metricConfig, config, source).apply();
        try {
            return toOppositePercentage(endoTreatment.getResult(), endo90015CTreatment.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}