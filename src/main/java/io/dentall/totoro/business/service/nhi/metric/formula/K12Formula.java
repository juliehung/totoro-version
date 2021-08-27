package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Point1;
import io.dentall.totoro.business.service.nhi.metric.meta.Sc1Point;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro6;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * (季)洗牙佔率
 *
 * @ date-10@@SC-1@總點數 / @date10@@Point-1@
 */
public class K12Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public K12Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new QuarterSource(metricConfig);
        this.source.setExclude(Tro6);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Sc1Point sc1Point = new Sc1Point(metricConfig, source).apply();
        Point1 point1 = new Point1(metricConfig, source).apply();
        try {
            return divide(sc1Point.getResult(), point1.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
