package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentReToothCount;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdPermanentQuarterByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdPermanentThreeYearNearByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

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

    public L41Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odQuarterSource = new OdPermanentQuarterByPatientSource(metricConfig);
        this.odThreeYearNearSource = new OdPermanentThreeYearNearByPatientSource(metricConfig);
        this.odQuarterSource.setExclude(N89013C);
        this.odThreeYearNearSource.setExclude(N89013C);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        OdPermanentReToothCount odPermanentReToothCount =
            new OdPermanentReToothCount(metricConfig, odQuarterSource, odThreeYearNearSource, 1, 1095).apply();
        return new BigDecimal(odPermanentReToothCount.getResult());
    }
}
