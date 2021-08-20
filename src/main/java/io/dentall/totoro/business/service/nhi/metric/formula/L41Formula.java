package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.MetaConfig;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentReTreatment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.meta.Exclude.N89013C;

/**
 * 三年恆牙重補顆數
 * ＠date-10＠＠OD-2＠＠date-12＠
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
        MetaConfig config = new MetaConfig(collector).setExclude(N89013C);
        OdPermanentReTreatment odPermanentReTreatment =
            new OdPermanentReTreatment(collector, config, odQuarterSource.outputKey(), odThreeYearNearSource.outputKey(), 1, 1095).apply();
        return new BigDecimal(odPermanentReTreatment.getResult());
    }
}
