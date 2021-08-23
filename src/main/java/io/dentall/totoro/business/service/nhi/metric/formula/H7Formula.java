package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Point2;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt1;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdMonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.util.NumericUtils;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

/**
 * ＠date-15＠ 的 @Point-2@/@PT-1@
 */
public class H7Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, OdDto> odSource;

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public H7Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odSource = new OdMonthSelectedSource(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config(metricConfig);
        Od1ToothCount od1ToothCount = new Od1ToothCount(metricConfig, config, odSource).apply();
        Pt1 pt1 = new Pt1(metricConfig, config, source).apply();
        try {
            return divide(od1ToothCount.getResult(), pt1.getResult());
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }
}
