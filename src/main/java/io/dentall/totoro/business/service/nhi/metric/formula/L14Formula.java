package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.filter.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic3;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1;
import io.dentall.totoro.business.service.nhi.metric.meta.Point2;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 一件平均合計點數 ＠date-15＠ 的 @Point-1@/@IC-3@
 */
public class L14Formula extends AbstractFormula {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public L14Formula(Collector collector, Source<NhiMetricRawVM, NhiMetricRawVM> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        Point1 point1 = new Point1(collector, source.outputKey()).apply();
        Ic3 ic3 = new Ic3(collector, source.outputKey()).apply();
        try {
            return divide(point1.getResult(), ic3.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
