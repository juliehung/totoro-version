package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousReTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousTreatment;

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

    public L28Formula(Collector collector,
                      Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource,
                      Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odTwoYearNearSource) {
        super(collector);
        this.odQuarterSource = odQuarterSource;
        this.odTwoYearNearSource = odTwoYearNearSource;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        OdDeciduousTreatment odDeciduousTreatment = new OdDeciduousTreatment(collector, odQuarterSource.outputKey()).apply();
        OdDeciduousReTreatment odDeciduousReTreatment = new OdDeciduousReTreatment(collector, odQuarterSource.outputKey(), odTwoYearNearSource.outputKey(), 366, 730).apply();
        try {
            return toPercentage(divide(odDeciduousReTreatment.getResult(), odDeciduousTreatment.getResult()));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
