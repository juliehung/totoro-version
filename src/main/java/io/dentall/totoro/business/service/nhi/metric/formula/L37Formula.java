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
 * 一年恆牙重補顆數
 * ＠date-10＠＠OD-2＠＠deta-3＠
 */
public class L37Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource;

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odOneYearNearSource;

    public L37Formula(Collector collector,
                      Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource,
                      Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odOneYearNearSource) {
        super(collector);
        this.odQuarterSource = odQuarterSource;
        this.odOneYearNearSource = odOneYearNearSource;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        OdPermanentReTreatment odPermanentReTreatment = new OdPermanentReTreatment(collector, odQuarterSource.outputKey(), odOneYearNearSource.outputKey(), 1, 365).apply();
        return new BigDecimal(odPermanentReTreatment.getResult());
    }
}
