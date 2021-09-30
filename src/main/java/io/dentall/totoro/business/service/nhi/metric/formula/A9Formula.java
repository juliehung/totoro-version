package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1Point;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdMonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.NhiCategory1416;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;

/**
 * (月)OD 點數比率
 *
 * @ date-15@ 的 [(@OD-1@/@Point-1@)]*100%
 */
public class A9Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    private final Source<MetricTooth, MetricTooth> odSource;

    public A9Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
        this.odSource = new OdMonthSelectedSource(metricConfig);
        this.source.setExclude(NhiCategory1416);
        this.odSource.setExclude(NhiCategory1416);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Od1Point od1Point = new Od1Point(metricConfig, odSource).apply();
        Point1 point1 = new Point1(metricConfig, source).apply();
        try {
            return toPercentage(od1Point.getResult(), point1.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
