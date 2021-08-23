package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.*;
import io.dentall.totoro.business.service.nhi.metric.source.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * (@OD-3@@date-4@)+(@OD-2@@date-5@)/@OD-1@@tooth-1@@tooth-2@
 */
public class H5Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odOneAndHalfYearSource;
    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odTwoYearSource;
    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odMonthSelectedSource;

    public H5Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odOneAndHalfYearSource = new OdDeciduousOneAndHalfYearNearByPatientSource(metricConfig);
        this.odTwoYearSource = new OdPermanentOneYearNearByPatientSource(metricConfig);
        this.odMonthSelectedSource = new OdMonthSelectedByPatientSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        MetaConfig config = new Tro1Config(metricConfig);
        OdToothCount odToothCount = new OdToothCount(metricConfig, config, odMonthSelectedSource).apply();
        OdDeciduousReToothCount odDeciduousToothCount = new OdDeciduousReToothCount(metricConfig, config, odMonthSelectedSource, odOneAndHalfYearSource, 1, 450).apply();
        OdPermanentReToothCount odPermanentReToothCount = new OdPermanentReToothCount(metricConfig, config, odMonthSelectedSource, odTwoYearSource, 1, 730).apply();
        try {
            return divide(odDeciduousToothCount.getResult() + odPermanentReToothCount.getResult(), odToothCount.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
