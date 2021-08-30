package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1Pt1;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdQuarterSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;

/**
 * (季)有O.D.患者之平均填補顆數
 *
 * @ date-10@ 的 @OD-1@齒數/@date-10@@OD-1@@PT-1@
 */
public class I6Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, OdDto> source;

    public I6Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new OdQuarterSource(metricConfig);
        this.source.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config(metricConfig);
        Od1Pt1 od1Pt1 = new Od1Pt1(metricConfig, config, source).apply();
        Od1ToothCount od1ToothCount = new Od1ToothCount(metricConfig, config, source).apply();
        try {
            return toPercentage(divide(od1ToothCount.getResult(), od1Pt1.getResult()));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
