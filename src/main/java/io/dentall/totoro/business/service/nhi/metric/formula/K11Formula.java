package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Ic1;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt1;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro6;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 平均耗格數
 *
 * @ date-10@ 的 @IC-1@/@PT-1@
 */
public class K11Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public K11Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new QuarterSource(metricConfig);
        this.source.setExclude(Tro6);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Ic1 ic1 = new Ic1(metricConfig, source).apply();
        Pt1 pt1 = new Pt1(metricConfig, source).apply();
        try {
            return divide(ic1.getResult(), pt1.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
