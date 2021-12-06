package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Point3;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

/**
 * 診療費點數 ＠date-15＠ 的 @Point-3@
 */
public class L3Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    public L3Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Point3 point3 = new Point3(metricConfig, source).apply();
        return new BigDecimal(point3.getResult());
    }
}