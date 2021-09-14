package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1Point;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdQuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * ＠date-10＠ 的 @OD-1@總點數/@OD-1@牙齒顆數
 */
public class F1h3Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, MetricTooth> source;

    public F1h3Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new OdQuarterSource(metricConfig);
        this.source.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config();
        Od1Point od1Point = new Od1Point(metricConfig, config, source).apply();
        Od1ToothCount od1ToothCount = new Od1ToothCount(metricConfig, config, source).apply();
        try {
            return divide(od1Point.getResult(), od1ToothCount.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
