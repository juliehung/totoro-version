package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1Point;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.*;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * ＠date-10＠ 的 @OD-1@總點數/@OD-1@牙齒顆數
 */
public class F1h3Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, OdDto> odQuarterSource;

    public F1h3Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odQuarterSource = new OdQuarterSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config(metricConfig);
        Od1Point od1Point = new Od1Point(metricConfig, config, odQuarterSource).apply();
        Od1ToothCount od1ToothCount = new Od1ToothCount(metricConfig, config, odQuarterSource).apply();
        try {
            return divide(od1Point.getResult(), od1ToothCount.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
