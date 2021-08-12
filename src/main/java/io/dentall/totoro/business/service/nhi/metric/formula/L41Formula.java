package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.filter.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentReTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentTreatment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 三年恆牙重補顆數
 * ＠date-10＠＠OD-2＠＠deta-12＠
 */
public class L41Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource;

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odThreeYearNearSource;

    public L41Formula(Collector collector,
                      Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource,
                      Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odThreeYearNearSource) {
        super(collector);
        this.odQuarterSource = odQuarterSource;
        this.odThreeYearNearSource = odThreeYearNearSource;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        OdPermanentReTreatment odPermanentReTreatment = new OdPermanentReTreatment(collector, odQuarterSource.outputKey(), odThreeYearNearSource.outputKey(), 1, 1095).apply();
        return new BigDecimal(odPermanentReTreatment.getResult());
    }
}
