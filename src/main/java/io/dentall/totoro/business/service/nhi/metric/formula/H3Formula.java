package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1Point;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdMonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;

/**
 * (月)OD 點數比率
 *
 * @ date-15@ 的 (@OD-1@/@Point-1@)*100%
 */
public class H3Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    private final Source<OdDto, OdDto> odSource;

    public H3Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
        this.odSource = new OdMonthSelectedSource(metricConfig);
        this.source.setExclude(Tro1);
        this.odSource.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config(metricConfig);
        Od1Point od1Point = new Od1Point(metricConfig, config, odSource).apply();
        Point1 point1 = new Point1(metricConfig, config, source).apply();
        try {
            return toPercentage(divide(od1Point.getResult(), point1.getResult()));
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }
}
