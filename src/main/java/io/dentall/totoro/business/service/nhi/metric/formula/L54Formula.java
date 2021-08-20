package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.Od456SurfaceCount;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

/**
 * 當季補牙面數
 * (1*@OD-4@醫令數量＋2*@OD-5@醫令數量+3*@OD-6@醫令數量)
 */
public class L54Formula extends AbstractFormula<BigDecimal> {

    private final Source<NhiMetricRawVM, OdDto> source;


    public L54Formula(Collector collector,
                      Source<NhiMetricRawVM, OdDto> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        Od456SurfaceCount od456SurfaceCount = new Od456SurfaceCount(collector, source.outputKey()).apply();
        return new BigDecimal(od456SurfaceCount.getResult());
    }
}
