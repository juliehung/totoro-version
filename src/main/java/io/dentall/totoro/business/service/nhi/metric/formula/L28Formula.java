package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousReToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousToothCount;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdDeciduousQuarterByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdDeciduousTwoYearNearByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;

/**
 * 第二年乳牙重補率
 * 分子：＠date-10＠@OD-3@＠deta-11＠
 * 分母：＠date-10＠@OD-1@@tooth-2@
 * (分子 / 分母) x 100%"
 */
public class L28Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource;

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odTwoYearNearSource;

    public L28Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odQuarterSource = new OdDeciduousQuarterByPatientSource(metricConfig);
        this.odTwoYearNearSource = new OdDeciduousTwoYearNearByPatientSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        OdDeciduousToothCount odDeciduousToothCount = new OdDeciduousToothCount(metricConfig, odQuarterSource).apply();
        OdDeciduousReToothCount odDeciduousReToothCount = new OdDeciduousReToothCount(metricConfig, odQuarterSource, odTwoYearNearSource, 366, 730).apply();
        try {
            return toPercentage(divide(odDeciduousReToothCount.getResult(), odDeciduousToothCount.getResult()));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
