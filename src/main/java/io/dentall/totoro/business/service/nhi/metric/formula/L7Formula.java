package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic3;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

/**
 * 申報件數 ＠date-15＠ 的 @IC-3@
 */
public class L7Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    public L7Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new MonthSelectedSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Ic3 ic3 = new Ic3(metricConfig, source).apply();
        return new BigDecimal(ic3.getResult());
    }
}
