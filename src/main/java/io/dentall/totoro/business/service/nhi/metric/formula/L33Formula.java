package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentReToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentToothCount;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdPermanentQuarterByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdPermanentTwoYearNearByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.NhiCategory_SpecificCode_Group1;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;

/**
 * 兩年恆牙重補率
 * 分子：＠date-10＠＠OD-2＠＠date-5＠
 * 分母：＠date-10＠@OD-1@@tooth-1@
 * (分子 / 分母) x 100%"
 */
public class L33Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource;

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odTwoYearNearSource;

    public L33Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odQuarterSource = new OdPermanentQuarterByPatientSource(metricConfig);
        this.odTwoYearNearSource = new OdPermanentTwoYearNearByPatientSource(metricConfig);
        this.odQuarterSource.setExclude(NhiCategory_SpecificCode_Group1);
        this.odTwoYearNearSource.setExclude(NhiCategory_SpecificCode_Group1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        OdPermanentToothCount odPermanentToothCount = new OdPermanentToothCount(metricConfig, odQuarterSource).apply();
        OdPermanentReToothCount odPermanentReToothCount =
            new OdPermanentReToothCount(metricConfig, odQuarterSource, odTwoYearNearSource, 1, 730).apply();
        try {
            return toPercentage(divide(odPermanentReToothCount.getResult(), odPermanentToothCount.getResult()));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
