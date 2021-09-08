package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1Pt1;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdMonthSelectedSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

/**
 * 當月全部病患
 * ＠date-15＠ 的 @OD-1@@PT-1@
 */
public class L51Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    public L51Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new OdMonthSelectedSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Od1Pt1 od1Pt1 = new Od1Pt1(metricConfig, source).apply();
        return new BigDecimal(od1Pt1.getResult());
    }
}
