package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousReToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentReToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1ButPoint6Config;
import io.dentall.totoro.business.service.nhi.metric.source.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * (@OD-3@@date-4@)+(@OD-2@@date-5@)/@OD-1@@tooth-1@@tooth-2@
 */
public class H5Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odOneAndHalfYearByPatientSource;
    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odTwoYearByPatientSource;
    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odMonthSelectedByPatientSource;
    private final Source<MetricTooth, MetricTooth> odMonthSelectedSource;

    public H5Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odOneAndHalfYearByPatientSource = new OdDeciduousOneAndHalfYearNearByPatientSource(metricConfig);
        this.odTwoYearByPatientSource = new OdPermanentOneYearNearByPatientSource(metricConfig);
        this.odMonthSelectedByPatientSource = new OdMonthSelectedByPatientSource(metricConfig);
        this.odMonthSelectedSource = new OdMonthSelectedSource(metricConfig);
        this.odOneAndHalfYearByPatientSource.setExclude(Tro1);
        this.odTwoYearByPatientSource.setExclude(Tro1);
        this.odMonthSelectedByPatientSource.setExclude(Tro1);
        this.odMonthSelectedSource.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1ButPoint6Config config = new Tro1ButPoint6Config();
        Od1ToothCount od1ToothCount = new Od1ToothCount(metricConfig, config, odMonthSelectedSource).apply();
        OdDeciduousReToothCount odDeciduousToothCount =
            new OdDeciduousReToothCount(metricConfig, config, odMonthSelectedByPatientSource, odOneAndHalfYearByPatientSource, 1, 450).apply();
        OdPermanentReToothCount odPermanentReToothCount =
            new OdPermanentReToothCount(metricConfig, config, odMonthSelectedByPatientSource, odTwoYearByPatientSource, 1, 730).apply();
        try {
            return divide(odDeciduousToothCount.getResult() + odPermanentReToothCount.getResult(), od1ToothCount.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
