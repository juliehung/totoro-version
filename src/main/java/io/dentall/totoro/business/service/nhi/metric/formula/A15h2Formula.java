package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.Category1416SpecialG9Config;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousToothCount;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdDeciduousOneAndHalfYearNearByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * ＠date-4@@OD-1@@tooth-2@齒數總和
 */
public class A15h2Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> source;

    public A15h2Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.source = new OdDeciduousOneAndHalfYearNearByPatientSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Category1416SpecialG9Config config = new Category1416SpecialG9Config(metricConfig);
        OdDeciduousToothCount odDeciduousToothCount = new OdDeciduousToothCount(metricConfig, config, source).apply();
        return new BigDecimal(odDeciduousToothCount.getResult());
    }
}
