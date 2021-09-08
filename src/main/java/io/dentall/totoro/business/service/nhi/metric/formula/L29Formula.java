package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousReToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousToothCount;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdDeciduousQuarterByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdDeciduousThreeYearNearByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;

/**
 * 三年乳牙重補率
 * 分子：＠date-10＠@OD-3@＠date-12＠
 * 分母：＠date-10＠@OD-1@@tooth-2@
 * (分子 / 分母) x 100%
 */
public class L29Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odQuarterSource;

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odThreeYearNearSource;

    public L29Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odQuarterSource = new OdDeciduousQuarterByPatientSource(metricConfig);
        this.odThreeYearNearSource = new OdDeciduousThreeYearNearByPatientSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        OdDeciduousToothCount odDeciduousToothCount = new OdDeciduousToothCount(metricConfig, odQuarterSource).apply();
        OdDeciduousReToothCount odDeciduousReToothCount = new OdDeciduousReToothCount(metricConfig, odQuarterSource, odThreeYearNearSource, 1, 1095).apply();
        try {
            return toPercentage(odDeciduousReToothCount.getResult(), odDeciduousToothCount.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
