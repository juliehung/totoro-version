package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentReToothCount;
import io.dentall.totoro.business.service.nhi.metric.source.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 兩年恆牙重補顆數
 * ＠date-15＠＠OD-2＠＠date-5＠
 */
public class F1h5Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odBaseSource;

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odTwoYearNearSource;

    public F1h5Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odBaseSource = new OdPermanentMonthSelectedByPatientSource(metricConfig);
        this.odTwoYearNearSource = new OdPermanentTwoYearNearByPatientSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        OdPermanentReToothCount odPermanentReToothCount = new OdPermanentReToothCount(metricConfig, odBaseSource, odTwoYearNearSource, 1, 730).apply();
        return new BigDecimal(odPermanentReToothCount.getResult());
    }
}
