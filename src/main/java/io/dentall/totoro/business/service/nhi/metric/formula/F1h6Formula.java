package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousReToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdDeciduousMonthSelectedByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdDeciduousQuarterPlusOneAndHalfYearNearByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;

/**
 * 一年半乳牙重補顆數
 * ＠date-15＠@OD-3@＠date-4＠
 */
public class F1h6Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odBaseSource;

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odPastYearNearSource;

    public F1h6Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odBaseSource = new OdDeciduousMonthSelectedByPatientSource(metricConfig);
        this.odPastYearNearSource = new OdDeciduousQuarterPlusOneAndHalfYearNearByPatientSource(metricConfig);
        this.odBaseSource.setExclude(Tro1);
        this.odPastYearNearSource.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config();
        OdDeciduousReToothCount odDeciduousReToothCount =
            new OdDeciduousReToothCount(metricConfig, config, odBaseSource, odPastYearNearSource, 1, 450).apply();
        return new BigDecimal(odDeciduousReToothCount.getResult());
    }
}
