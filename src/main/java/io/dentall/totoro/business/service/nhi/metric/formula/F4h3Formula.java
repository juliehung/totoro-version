package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Point2;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;

/**
 * 申請點數 ＠date-15＠ 的 @Point-2@
 */
public class F4h3Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public F4h3Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
        this.source.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config(metricConfig);
        Point2 point2 = new Point2(metricConfig, config, source).apply();
        return new BigDecimal(point2.getResult());
    }
}
