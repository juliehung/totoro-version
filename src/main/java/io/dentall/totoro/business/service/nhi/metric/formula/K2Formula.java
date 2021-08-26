package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Point2;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt1;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro6Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

/**
 * (季)就醫病患者平均耗用值
 * <p>
 * (@date-10@@Point-2@/@date-10@@PT-1@)/3
 */
public class K2Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public K2Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new QuarterSource(metricConfig);
    }

    @Override
    protected BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro6Config config = new Tro6Config(metricConfig);
        Point2 point2 = new Point2(metricConfig, config, source).apply();
        Pt1 pt1 = new Pt1(metricConfig, config, source).apply();

        try {
            return divide(divide(point2.getResult(), pt1.getResult()), valueOf(3));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }

}
