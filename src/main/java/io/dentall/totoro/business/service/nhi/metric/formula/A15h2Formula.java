package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousToothCount;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdDeciduousOneAndHalfYearNearByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.NhiCategory_SpecificCode_Group1;

/**
 * ＠date-4@@OD-1@@tooth-2@齒數總和
 */
public class A15h2Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> source;

    public A15h2Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new OdDeciduousOneAndHalfYearNearByPatientSource(metricConfig);
        this.source.setExclude(NhiCategory_SpecificCode_Group1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        OdDeciduousToothCount odDeciduousToothCount = new OdDeciduousToothCount(metricConfig, source).apply();
        return new BigDecimal(odDeciduousToothCount.getResult());
    }
}
