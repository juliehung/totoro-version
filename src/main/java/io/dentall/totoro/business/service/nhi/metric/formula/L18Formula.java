package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt1;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 季平均費用點數 @date-10@@Point-1@/3
 */
public class L18Formula extends AbstractFormula {

    private final String sourceName;

    public L18Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal doCalculate() {
        Point1 point1 = apply(new Point1(sourceName));
        try {
            return divide(point1.getResult(), 3L);
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
