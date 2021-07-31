package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.filter.Source;
import io.dentall.totoro.business.service.nhi.metric.meta.Point2;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt1;
import io.dentall.totoro.business.service.nhi.metric.util.NumericUtils;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 平均每位醫療耗用值 @date-15@ 的 @Point-2@/@PT-1@
 */
public class L6Formula extends AbstractFormula {

    private final Source<NhiMetricRawVM, NhiMetricRawVM> source;

    public L6Formula(Collector collector, Source<NhiMetricRawVM, NhiMetricRawVM> source) {
        super(collector);
        this.source = source;
    }

    @Override
    public BigDecimal doCalculate(Collector collector) {
        Point2 point2 = new Point2(collector, source.outputKey()).apply();
        Pt1 pt1 = new Pt1(collector, source.outputKey()).apply();
        try {
            return divide(point2.getResult(), pt1.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
