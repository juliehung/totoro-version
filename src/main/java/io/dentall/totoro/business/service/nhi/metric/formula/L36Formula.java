package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentReToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentToothCount;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdPermanentQuarterByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdPermanentThreeYearNearByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;

/**
 * 第三年恆牙重補率
 * 分子：＠date-10＠@OD-2@＠deta-13＠
 * 分母：＠date-10＠@OD-1@@tooth-1@
 * (分子 / 分母) x 100%"""
 */
public class L36Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource;

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odThreeYearNearSource;

    public L36Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odQuarterSource = new OdPermanentQuarterByPatientSource(metricConfig);
        this.odThreeYearNearSource = new OdPermanentThreeYearNearByPatientSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        OdPermanentToothCount odPermanentToothCount = new OdPermanentToothCount(metricConfig, odQuarterSource).apply();
        OdPermanentReToothCount odPermanentReToothCount = new OdPermanentReToothCount(metricConfig, odQuarterSource, odThreeYearNearSource, 731, 1095).apply();
        try {
            return toPercentage(divide(odPermanentReToothCount.getResult(), odPermanentToothCount.getResult()));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
