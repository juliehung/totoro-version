package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1Pt1;
import io.dentall.totoro.business.service.nhi.metric.meta.Od456SurfaceCount;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdQuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

/**
 * 當季補牙患者平均填補面數
 * ＠date-10＠ 的(1*@OD-4@醫令數量＋2*@OD-5@醫令數量+3*@OD-6@醫令數量)/@OD-1@@PT-1@
 */
public class L56Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> source;

    public L56Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new OdQuarterSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Od1Pt1 od1Pt1 = new Od1Pt1(metricConfig, source).apply();
        Od456SurfaceCount od456SurfaceCount = new Od456SurfaceCount(metricConfig, source).apply();
        try {
            return divide(od456SurfaceCount.getResult(), od1Pt1.getResult());
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }
}
