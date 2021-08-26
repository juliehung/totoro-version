package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ReToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro6Config;
import io.dentall.totoro.business.service.nhi.metric.source.*;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;

/**
 * 第三年自家O.D.重補率
 * <p>
 * 分子：＠date-10＠＠OD-2＠@OD-3@＠date-11＠
 * 分母：＠date-10＠@OD-1@@tooth-1@@tooth-2@
 * (分子 / 分母) x 100%"
 */
public class K7Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, OdDto> odSource;

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odByPatientSource;

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odPastByPatientSource;

    public K7Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odSource = new OdQuarterSource(metricConfig);
        this.odByPatientSource = new OdQuarterByPatientSource(metricConfig);
        this.odPastByPatientSource = new OdThreeYearNearByPatientSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro6Config config = new Tro6Config(metricConfig);
        Od1ToothCount od1ToothCount = new Od1ToothCount(metricConfig, config, odSource).apply();
        Od1ReToothCount od1ReToothCount = new Od1ReToothCount(metricConfig, config, odByPatientSource, odPastByPatientSource, 731, 1095).apply();
        try {
            return toPercentage(divide(od1ReToothCount.getResult(), od1ToothCount.getResult()));
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }
}
