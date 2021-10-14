package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Od456SurfaceCount;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdQuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro6;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

/**
 * 當季平均一顆面數
 *
 * @ date-10@ 的 (1*@OD-4@醫令數量＋2*@OD-5@醫令數量+3*@OD-6@醫令數量)/@OD-1@齒數
 */
public class K10Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    public K10Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new OdQuarterSource(metricConfig);
        this.source.setExclude(Tro6);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Od1ToothCount od1ToothCount = new Od1ToothCount(metricConfig, source).apply();
        Od456SurfaceCount od456SurfaceCount = new Od456SurfaceCount(metricConfig, source).apply();
        try {
            return divide(od456SurfaceCount.getResult(), od1ToothCount.getResult());
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }
}
