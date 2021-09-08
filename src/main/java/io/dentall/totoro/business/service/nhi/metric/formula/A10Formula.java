package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Point2;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro5Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro5;

/**
 * 申請點數 ＠date-15＠ 的 @Point-2@
 */
public class A10Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public A10Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
        this.source.setExclude(Tro5);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro5Config config = new Tro5Config();
        Point2 point2 = new Point2(metricConfig, config, source).apply();
        return new BigDecimal(point2.getResult());
    }
}
