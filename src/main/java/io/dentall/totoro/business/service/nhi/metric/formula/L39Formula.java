package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentReToothCount;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdPermanentQuarterByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdPermanentTwoYearNearByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 兩年恆牙重補顆數
 * ＠date-10＠＠OD-2＠＠date-5＠
 */
public class L39Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource;

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odTwoYearNearSource;

    public L39Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odQuarterSource = new OdPermanentQuarterByPatientSource(metricConfig);
        this.odTwoYearNearSource = new OdPermanentTwoYearNearByPatientSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        OdPermanentReToothCount odPermanentReToothCount = new OdPermanentReToothCount(metricConfig, odQuarterSource, odTwoYearNearSource, 1, 730).apply();
        return new BigDecimal(odPermanentReToothCount.getResult());
    }
}
