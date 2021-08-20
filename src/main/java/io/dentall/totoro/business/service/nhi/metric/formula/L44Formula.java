package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.MetaConfig;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousReTreatment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.NhiCategory_SpecificCode_Group1;

/**
 * 一年半乳牙重補顆數
 * ＠date-10＠@OD-3@＠date-4＠
 */
public class L44Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource;

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odTwoYearNearSource;

    public L44Formula(Collector collector,
                      Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource,
                      Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odTwoYearNearSource) {
        super(collector);
        this.odQuarterSource = odQuarterSource;
        this.odTwoYearNearSource = odTwoYearNearSource;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        MetaConfig config = new MetaConfig(collector).setExclude(NhiCategory_SpecificCode_Group1);
        OdDeciduousReTreatment odDeciduousReTreatment =
            new OdDeciduousReTreatment(collector, config, odQuarterSource.outputKey(), odTwoYearNearSource.outputKey(), 1, 450).apply();
        return new BigDecimal(odDeciduousReTreatment.getResult());
    }
}
