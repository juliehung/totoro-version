package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.Point2;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt1;
import io.dentall.totoro.business.service.nhi.metric.util.NumericUtils;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 平均每位醫療耗用值 @date-15@ 的 @Point-2@/@PT-1@
 */
public class L6Formula implements Formula {

    private final String sourceName;

    public L6Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal calculate(Collector collector) {
        Point2 point2 = new Point2(sourceName);
        Pt1 pt1 = new Pt1(sourceName);
        collector.apply(point2).apply(pt1);
        try {
            return divide(point2.getResult(), pt1.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
