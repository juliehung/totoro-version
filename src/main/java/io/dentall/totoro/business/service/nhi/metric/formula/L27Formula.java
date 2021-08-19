package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.filter.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.MetaConfig;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousReTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousTreatment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.N89013C;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 兩年乳牙重補率
 * 分子：＠date-10＠@OD-3@＠date-5＠
 * 分母：＠date-10＠@OD-1@@tooth-2@
 * (分子 / 分母) x 100%
 */
public class L27Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource;

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odTwoYearNearSource;

    public L27Formula(Collector collector,
                      Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource,
                      Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odTwoYearNearSource) {
        super(collector);
        this.odQuarterSource = odQuarterSource;
        this.odTwoYearNearSource = odTwoYearNearSource;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        MetaConfig config = new MetaConfig(collector).setExclude(N89013C);
        OdDeciduousTreatment odDeciduousTreatment = new OdDeciduousTreatment(collector, config, odQuarterSource.outputKey()).apply();
        OdDeciduousReTreatment odDeciduousReTreatment =
            new OdDeciduousReTreatment(collector, config, odQuarterSource.outputKey(), odTwoYearNearSource.outputKey(), 1, 730).apply();
        try {
            return divide(odDeciduousReTreatment.getResult(), odDeciduousTreatment.getResult()).multiply(new BigDecimal(100L));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
