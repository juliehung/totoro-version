package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.filter.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1Pt1;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1ToothCount;
import io.dentall.totoro.business.service.nhi.metric.meta.Od456SurfaceCount;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

/**
 * 當季平均一顆面數
 * @ date-10@ 的 (1*@OD-4@醫令數量＋2*@OD-5@醫令數量+3*@OD-6@醫令數量)/@OD-1@齒數
 */
public class L53Formula extends AbstractFormula {

    private final Source<NhiMetricRawVM, OdDto> source;


    public L53Formula(Collector collector,
                      Source<NhiMetricRawVM, OdDto> source) {
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
