package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.filter.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousReTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousTreatment;
import io.dentall.totoro.business.service.nhi.metric.util.OdDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 一年乳牙重補率
 * 分子：＠date-10＠@OD-3@＠date-3＠
 * 分母：＠date-10＠@OD-1@@tooth-2@
 * (分子 / 分母) x 100%
 */
public class L25Formula extends AbstractFormula {

    private final Source<OdDto, Map<Long, List<OdDto>>> odQuarterSource;

    private final Source<OdDto, Map<Long, List<OdDto>>> odOneYearNearSource;

    public L25Formula(Collector collector, Source<OdDto, Map<Long, List<OdDto>>> odQuarterSource, Source<OdDto, Map<Long, List<OdDto>>> odOneYearNearSource) {
        super(collector);
        this.odQuarterSource = odQuarterSource;
        this.odOneYearNearSource = odOneYearNearSource;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        OdDeciduousTreatment odDeciduousTreatment = new OdDeciduousTreatment(collector, odQuarterSource.outputKey()).apply();
        OdDeciduousReTreatment odDeciduousReTreatment = new OdDeciduousReTreatment(collector, odQuarterSource.outputKey(), odOneYearNearSource.outputKey(), 0, 365).apply();
        try {
            return divide(odDeciduousReTreatment.getResult(), odDeciduousTreatment.getResult()).multiply(new BigDecimal(100L));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
