package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousReTreatment;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 一年乳牙重補顆數
 * ＠date-10＠@OD-3@＠date-3＠
 */
public class L43Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource;

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odOneYearNearSource;

    public L43Formula(Collector collector,
                      Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource,
                      Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odOneYearNearSource) {
        super(collector);
        this.odQuarterSource = odQuarterSource;
        this.odOneYearNearSource = odOneYearNearSource;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        OdDeciduousReTreatment odDeciduousReTreatment = new OdDeciduousReTreatment(collector, odQuarterSource, odOneYearNearSource, 1, 365).apply();
        return new BigDecimal(odDeciduousReTreatment.getResult());
    }
}
