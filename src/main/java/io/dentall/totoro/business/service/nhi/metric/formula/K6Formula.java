package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ReToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.source.*;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro6;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;

/**
 * 二年自家O.D.重補率
 * <p>
 * 分子：＠date-10＠＠OD-2＠@OD-3@＠date-5＠
 * 分母：＠date-10＠@OD-1@@tooth-1@@tooth-2@
 * (分子 / 分母) x 100%"
 */
public class K6Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, MetricTooth> odSource;

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odByPatientSource;

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odPastByPatientSource;

    public K6Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odSource = new OdQuarterSource(metricConfig);
        this.odByPatientSource = new OdQuarterByPatientSource(metricConfig);
        this.odPastByPatientSource = new OdTwoYearNearByPatientSource(metricConfig);
        this.odSource.setExclude(Tro6);
        this.odByPatientSource.setExclude(Tro6);
        this.odPastByPatientSource.setExclude(Tro6);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Od1ToothCount od1ToothCount = new Od1ToothCount(metricConfig, odSource).apply();
        Od1ReToothCount od1ReToothCount = new Od1ReToothCount(metricConfig, odByPatientSource, odPastByPatientSource, 1, 730).apply();
        try {
            return toPercentage(od1ReToothCount.getResult(), od1ToothCount.getResult());
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }
}
