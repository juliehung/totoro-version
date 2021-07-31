package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.filter.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic3;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt1;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 季平均就醫>2次 ＠date-10@ 的 ＠IC-3＠/@PT-1@
 */
public class L19Formula extends AbstractFormula {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public L19Formula(Collector collector, Source<NhiMetricRawVM, NhiMetricRawVM> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        Pt1 pt1 = new Pt1(collector, source.outputKey()).apply();
        Ic3 ic3 = new Ic3(collector, source.outputKey()).apply();
        try {
            return divide(ic3.getResult(), pt1.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
