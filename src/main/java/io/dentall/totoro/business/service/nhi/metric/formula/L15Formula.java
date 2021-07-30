package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic2;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic3;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1;
import io.dentall.totoro.business.service.nhi.metric.meta.Point2;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 一日平均申請點數 ＠date-15＠ 的 @Point-2@/＠IC-2@
 */
public class L15Formula extends AbstractFormula {

    private final String sourceName;

    public L15Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal doCalculate() {
        Point2 point2 = apply(new Point2(sourceName));
        Ic2 ic2 = apply(new Ic2(sourceName));
        try {
            return divide(point2.getResult(), ic2.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
