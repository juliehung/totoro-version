package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.OdDeciduousReToothCount;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdDeciduousQuarterByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdDeciduousTwoYearNearByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

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

    public L44Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odQuarterSource = new OdDeciduousQuarterByPatientSource(metricConfig);
        this.odTwoYearNearSource = new OdDeciduousTwoYearNearByPatientSource(metricConfig);
        this.odQuarterSource.setExclude(NhiCategory_SpecificCode_Group1);
        this.odTwoYearNearSource.setExclude(NhiCategory_SpecificCode_Group1);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        OdDeciduousReToothCount odDeciduousReToothCount =
            new OdDeciduousReToothCount(metricConfig, odQuarterSource, odTwoYearNearSource, 1, 450).apply();
        return new BigDecimal(odDeciduousReToothCount.getResult());
    }
}
