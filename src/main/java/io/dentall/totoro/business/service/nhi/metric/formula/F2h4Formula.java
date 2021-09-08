package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentReToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdPermanentQuarterByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdPermanentTwoYearNearByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;

/**
 * 兩年恆牙重補率
 * 分子：＠date-10＠＠OD-2＠＠date-5＠
 * 分母：＠date-10＠@OD-1@@tooth-1@
 * (分子 / 分母) x 100%"
 */
public class F2h4Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odQuarterSource;

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odTwoYearNearSource;

    public F2h4Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odQuarterSource = new OdPermanentQuarterByPatientSource(metricConfig);
        this.odTwoYearNearSource = new OdPermanentTwoYearNearByPatientSource(metricConfig);
        this.odQuarterSource.setExclude(Tro1);
        this.odTwoYearNearSource.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config(metricConfig);
        OdPermanentToothCount odPermanentTreatment = new OdPermanentToothCount(metricConfig, config, odQuarterSource).apply();
        OdPermanentReToothCount odPermanentReTreatment =
            new OdPermanentReToothCount(metricConfig, config, odQuarterSource, odTwoYearNearSource, 1, 730).apply();
        try {
            return toPercentage(divide(odPermanentReTreatment.getResult(), odPermanentTreatment.getResult()));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
