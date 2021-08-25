package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Endo1Point;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * (季)非根管治療點數佔總點數之百分比
 * ＠date-10＠ 的[@Point-1@-@date-10@@endo-1@點數總和]/@Point-1@
 */
public class I11Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public I11Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new QuarterSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config(metricConfig);
        Point1 point1 = new Point1(metricConfig, config, source).apply();
        Endo1Point endo1Point = new Endo1Point(metricConfig, config, source).apply();
        try {
            return divide(point1.getResult() - endo1Point.getResult(), point1.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
