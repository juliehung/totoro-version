package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterDisposalSource;
import io.dentall.totoro.business.service.nhi.metric.source.QuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

/**
 * (季)醫師月之平均申報點數
 *
 * @ date-10@@Point-1@/3
 */
public class J2h2Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    private final Source<MetricDisposal, MetricDisposal> disposalSource;

    public J2h2Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new QuarterSource(metricConfig);
        this.disposalSource = new QuarterDisposalSource(metricConfig);
    }

    @Override
    protected BigDecimal doCalculate(MetricConfig metricConfig) {
        Point1 point1 = new Point1(metricConfig, source, disposalSource).apply();
        return divide(point1.getResult(), 3L);
    }

}
