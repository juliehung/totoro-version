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
public class L6Formula extends AbstractFormula {

    private final String sourceName;

    public L6Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal doCalculate() {
        Point2 point2 = apply(new Point2(sourceName));
        Pt1 pt1 = apply(new Pt1(sourceName));
        try {
            return divide(point2.getResult(), pt1.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
