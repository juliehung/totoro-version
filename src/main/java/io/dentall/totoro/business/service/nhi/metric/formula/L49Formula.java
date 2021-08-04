package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.filter.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Od456SurfaceCount;
import io.dentall.totoro.business.service.nhi.metric.util.NumericUtils;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

/**
 * 當月平均一顆面數
 * (1*@OD-4@醫令數量＋2*@OD-5@醫令數量+3*@OD-6@醫令數量)/@OD-1@齒數
 */
public class L49Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, OdDto> source;


    public L49Formula(Collector collector,
                      Source<OdDto, OdDto> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        Od1ToothCount od1ToothCount = new Od1ToothCount(collector, source.outputKey()).apply();
        Od456SurfaceCount od456SurfaceCount = new Od456SurfaceCount(collector, source.outputKey()).apply();
        try {
            return divide(od456SurfaceCount.getResult(), od1ToothCount.getResult());
        } catch (ArithmeticException e) {
            return BigDecimal.ZERO;
        }
    }
}
