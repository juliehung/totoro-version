package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.OdReToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.OdToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Tro1Config;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.OdQuarterByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.OdThreeYearNearByPatientSource;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;

/**
 * 三年自家重補率
 * 分子：＠date-10＠＠OD-2＠@OD-3@＠deta-12＠
 * 分母：＠date-10＠@OD-1@@tooth-1@@tooth-2@
 * 3. 公式
 * (分子 / 分母) x 100%"
 */
public class F5h3Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odSource;

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odPastSource;

    public F5h3Formula(MetricConfig metricConfig) {
        super(metricConfig);
        this.odSource = new OdQuarterByPatientSource(metricConfig);
        this.odPastSource = new OdThreeYearNearByPatientSource(metricConfig);
    }

    @Override
    public BigDecimal doCalculate(MetricConfig metricConfig) {
        Tro1Config config = new Tro1Config(metricConfig);
        OdToothCount odTreatment = new OdToothCount(metricConfig, config, odSource).apply();
        OdReToothCount odReTreatment = new OdReToothCount(metricConfig, config, odSource, odPastSource, 1, 1095).apply();
        try {
            return toPercentage(divide(odReTreatment.getResult(), odTreatment.getResult()));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
