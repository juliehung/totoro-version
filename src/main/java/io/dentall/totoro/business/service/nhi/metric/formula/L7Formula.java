package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.filter.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic3;
import io.dentall.totoro.business.service.nhi.metric.meta.Point2;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt1;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

/**
 * 申報件數 ＠date-15＠ 的 @IC-3@
 */
public class L7Formula extends AbstractFormula {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public L7Formula(Collector collector, Source<NhiMetricRawVM, NhiMetricRawVM> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        Ic3 ic3 = new Ic3(collector, source.outputKey()).apply();
        return new BigDecimal(ic3.getResult());
    }
}
