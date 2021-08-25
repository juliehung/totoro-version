package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Endo90015CTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.EndoTreatment;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OneYearNearSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

/**
 * 一年根管未完成率
 * [1–(＠date-3＠ 90001C+ ＠date-3＠ 90002C+ ＠date-3＠ 90003C+ ＠date-3＠ 90016C+ ＠date-3＠ 90018C+ ＠date-3＠ 90019C+＠date-3＠ 90020C) ∕ ＠date-3＠  90015C]*100%
 */
public class L23Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public L23Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new OneYearNearSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        EndoTreatment endoTreatment = new EndoTreatment(metricConfig, source).apply();
        Endo90015CTreatment endo90015CTreatment = new Endo90015CTreatment(metricConfig, source).apply();
        try {
            BigDecimal tmp = divide(endoTreatment.getResult(), endo90015CTreatment.getResult());
            return toPercentage(ONE.subtract(tmp));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
