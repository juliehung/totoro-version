package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousReToothCount;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdDeciduousQuarterByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdDeciduousTwoYearNearByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 兩年乳牙重補顆數
 * ＠date-10＠@OD-3@＠date-5＠
 */
public class L45Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odQuarterSource;

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odTwoYearNearSource;

    public L45Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odQuarterSource = new OdDeciduousQuarterByPatientSource(metricConfig);
        this.odTwoYearNearSource = new OdDeciduousTwoYearNearByPatientSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        OdDeciduousReToothCount odDeciduousReToothCount = new OdDeciduousReToothCount(metricConfig, odQuarterSource, odTwoYearNearSource, 1, 730).apply();
        return new BigDecimal(odDeciduousReToothCount.getResult());
    }
}
