package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ReToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.*;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.Tro1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;

/**
 * 一年自家O.D.重補率
 * <p>
 * 分子：＠date-10＠＠OD-2＠@OD-3@＠date-3＠
 * 分母：＠date-10＠@OD-1@@tooth-1@@tooth-2@
 * (分子 / 分母) x 100%"
 */
public class E1h14Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, MetricTooth> odSource;

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odByPatientSource;

    private final Source<MetricTooth, Map<Long, Map<String, List<MetricTooth>>>> odPastByPatientSource;

    public E1h14Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odSource = new OdQuarterSource(metricConfig);
        this.odByPatientSource = new OdQuarterByPatientSource(metricConfig);
        this.odPastByPatientSource = new OdOneYearNearByPatientSource(metricConfig);
        this.odSource.setExclude(Tro1);
        this.odByPatientSource.setExclude(Tro1);
        this.odPastByPatientSource.setExclude(Tro1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config();
        Od1ToothCount od1ToothCount = new Od1ToothCount(metricConfig, config, odSource).apply();
        Od1ReToothCount od1ReToothCount = new Od1ReToothCount(metricConfig, config, odByPatientSource, odPastByPatientSource, 1, 365).apply();
        try {
            return toPercentage(od1ReToothCount.getResult(), od1ToothCount.getResult());
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }
}
