package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentReToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdPermanentQuarterByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdPermanentThreeYearNearByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;

/**
 * 三年恆牙重補率
 * 分子：＠OD-2＠＠date-12＠＠date-10＠@tooth-1@
 * 分母：＠date-10＠@OD-1@@tooth-1@
 * (分子 / 分母) x 100%"
 */
public class L35Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odQuarterSource;

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odThreeYearNearSource;

    public L35Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odQuarterSource = new OdPermanentQuarterByPatientSource(metricConfig);
        this.odThreeYearNearSource = new OdPermanentThreeYearNearByPatientSource(metricConfig);
        this.odQuarterSource.setExclude(Tro1);
        this.odThreeYearNearSource.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config(metricConfig);
        OdPermanentToothCount odPermanentToothCount = new OdPermanentToothCount(metricConfig, config, odQuarterSource).apply();
        OdPermanentReToothCount odPermanentReToothCount =
            new OdPermanentReToothCount(metricConfig, config, odQuarterSource, odThreeYearNearSource, 1, 1095).apply();
        try {
            return toPercentage(divide(odPermanentReToothCount.getResult(), odPermanentToothCount.getResult()));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
