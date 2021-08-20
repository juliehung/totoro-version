package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1Pt1;
import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.math.BigDecimal;

/**
 * 當月全部病患
 * ＠date-15＠ 的 @OD-1@@PT-1@
 */
public class L51Formula extends AbstractFormula<BigDecimal> {

    private final Source<OdDto, OdDto> source;


    public L51Formula(Collector collector,
                      Source<OdDto, OdDto> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        Od1Pt1 od1Pt1 = new Od1Pt1(collector, source).apply();
        return new BigDecimal(od1Pt1.getResult());
    }
}
