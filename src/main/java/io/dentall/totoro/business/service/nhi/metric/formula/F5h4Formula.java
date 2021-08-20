package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Od456SurfaceCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdQuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

/**
 * 當季平均一顆面數
 *
 * @ date-10@ 的 (1*@OD-4@醫令數量＋2*@OD-5@醫令數量+3*@OD-6@醫令數量)/@OD-1@齒數
 */
public class F5h4Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, OdDto> source;

    public F5h4Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new OdQuarterSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config(metricConfig);
        Od1ToothCount od1ToothCount = new Od1ToothCount(metricConfig, config, source).apply();
        Od456SurfaceCount od456SurfaceCount = new Od456SurfaceCount(metricConfig, config, source).apply();
        try {
            return divide(od456SurfaceCount.getResult(), od1ToothCount.getResult());
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }
}
