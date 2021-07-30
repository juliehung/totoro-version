package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic3;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1;
import io.dentall.totoro.business.service.nhi.metric.meta.Point2;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 一件平均合計點數 ＠date-15＠ 的 @Point-1@/@IC-3@
 */
public class L14Formula extends AbstractFormula {

    private final String sourceName;

    public L14Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal doCalculate() {
        Point1 point1 = apply(new Point1(sourceName));
        Ic3 ic3 = apply(new Ic3(sourceName));
        try {
            return divide(point1.getResult(), ic3.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
