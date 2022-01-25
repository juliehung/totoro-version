package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Od456SurfaceCount;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdMonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

/**
 * 當月補牙面數
 * (1*@OD-4@醫令數量＋2*@OD-5@醫令數量+3*@OD-6@醫令數量)
 */
public class E1h3Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    public E1h3Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new OdMonthSelectedSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Od456SurfaceCount od456SurfaceCount = new Od456SurfaceCount(metricConfig, source).apply();
        return new BigDecimal(od456SurfaceCount.getResult());
    }
}