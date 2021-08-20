package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.Od456SurfaceCount;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

/**
 * 當月補牙面數
 * (1*@OD-4@醫令數量＋2*@OD-5@醫令數量+3*@OD-6@醫令數量)
 */
public class L50Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, OdDto> source;


    public L50Formula(Collector collector,
                      Source<OdDto, OdDto> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        Od456SurfaceCount od456SurfaceCount = new Od456SurfaceCount(collector, source).apply();
        return new BigDecimal(od456SurfaceCount.getResult());
    }
}
