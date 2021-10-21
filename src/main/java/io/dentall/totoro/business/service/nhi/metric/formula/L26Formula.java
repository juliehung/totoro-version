package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousReToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdDeciduousQuarterByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdDeciduousQuarterPlusOneAndHalfYearNearByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;

/**
 * 一年半乳牙重補率
 * 分子：＠date-10＠@OD-3@＠date-4＠
 * 分母：＠date-10＠@OD-1@@tooth-2@
 * (分子 / 分母) x 100%"""
 */
public class L26Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odQuarterSource;

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odPastYearNearSource;

    public L26Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odQuarterSource = new OdDeciduousQuarterByPatientSource(metricConfig);
        this.odPastYearNearSource = new OdDeciduousQuarterPlusOneAndHalfYearNearByPatientSource(metricConfig);
        this.odQuarterSource.setExclude(Tro1);
        this.odPastYearNearSource.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config();
        OdDeciduousToothCount odDeciduousToothCount = new OdDeciduousToothCount(metricConfig, config, odQuarterSource).apply();
        OdDeciduousReToothCount odDeciduousReToothCount = new OdDeciduousReToothCount(metricConfig, config, odQuarterSource, odPastYearNearSource, 1, 450).apply();
        try {
            return toPercentage(odDeciduousReToothCount.getResult(), odDeciduousToothCount.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
