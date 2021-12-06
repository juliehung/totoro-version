package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ReToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;

/**
 * 一年自家重補率
 * 分子：＠date-15＠＠OD-2＠@OD-3@＠date-3＠
 * 分母：＠date-15＠@OD-1@@tooth-1@@tooth-2@
 * 3. 公式
 * (分子 / 分母) x 100%"
 */
public class F5h3Formula extends AbstractFormula<BigDecimal> {

    private final Source<MetricTooth, MetricTooth> odSource;

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odByPatientSource;

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odPastByPatientSource;

    public F5h3Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odSource = new OdMonthSelectedSource(metricConfig);
        this.odByPatientSource = new OdMonthSelectedByPatientSource(metricConfig);
        this.odPastByPatientSource = new OdQuarterPlusOneYearNearByPatientSource(metricConfig);
        this.odSource.setExclude(Tro1);
        this.odByPatientSource.setExclude(Tro1);
        this.odPastByPatientSource.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config();
        Od1ToothCount od1ToothCount = new Od1ToothCount(metricConfig, config, odSource).apply();
        Od1ReToothCount odReTreatment = new Od1ReToothCount(metricConfig, config, odByPatientSource, odPastByPatientSource, 1, 365).apply();
        try {
            return toPercentage(odReTreatment.getResult(), od1ToothCount.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}