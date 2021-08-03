package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.dto.OdDto;
import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.filter.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.Od1Pt1;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

/**
 * 當季全部病患
 * ＠date-10＠ 的 @OD-1@@PT-1@
 */
public class L55Formula extends AbstractFormula {

    private final Source<NhiMetricRawVM, OdDto> source;


    public L55Formula(Collector collector,
                      Source<NhiMetricRawVM, OdDto> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        Od1Pt1 od1Pt1 = new Od1Pt1(collector, source.outputKey()).apply();
        return new BigDecimal(od1Pt1.getResult());
    }
}
