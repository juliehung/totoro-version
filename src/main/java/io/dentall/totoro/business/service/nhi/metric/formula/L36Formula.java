package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentReTreatment;
import io.dentall.totoro.business.service.nhi.metric.meta.OdPermanentTreatment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.toPercentage;
import static java.math.BigDecimal.ZERO;

/**
 * 第三年恆牙重補率
 * 分子：＠date-10＠@OD-2@＠deta-13＠
 * 分母：＠date-10＠@OD-1@@tooth-1@
 * (分子 / 分母) x 100%"""
 */
public class L36Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource;

    private final Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odThreeYearNearSource;

    public L36Formula(Collector collector,
                      Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odQuarterSource,
                      Source<OdDto, Map<Long, Map<String, List<OdDto>>>> odThreeYearNearSource) {
        super(collector);
        this.odQuarterSource = odQuarterSource;
        this.odThreeYearNearSource = odThreeYearNearSource;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        OdPermanentTreatment odPermanentTreatment = new OdPermanentTreatment(collector, odQuarterSource.outputKey()).apply();
        OdPermanentReTreatment odPermanentReTreatment = new OdPermanentReTreatment(collector, odQuarterSource.outputKey(), odThreeYearNearSource.outputKey(), 731, 1095).apply();
        try {
            return toPercentage(divide(odPermanentReTreatment.getResult(), odPermanentTreatment.getResult()));
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
