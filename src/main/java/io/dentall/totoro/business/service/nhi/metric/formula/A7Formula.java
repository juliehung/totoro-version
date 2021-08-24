package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Category1416Perio1Perio2Config;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic3;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 一件平均合計點數 ＠date-15＠ 的 @Point-1@/@IC-3@
 */
public class A7Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public A7Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Category1416Perio1Perio2Config config = new Category1416Perio1Perio2Config(metricConfig);
        Point1 point1 = new Point1(metricConfig, config, source).apply();
        Ic3 ic3 = new Ic3(metricConfig, config, source).apply();
        try {
            return divide(point1.getResult(), ic3.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
