package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.*;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 病患最高總點數佔比 ＠date-15＠ 的 病患合計點數(最高者)/@Point-1@
 */
public class L10Formula extends AbstractFormula {

    private final String sourceName;

    public L10Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal doCalculate() {
        Point1 point1 = apply(new Point1(sourceName));
        HighestPoint1ByPatient highestPoint1ByPatient = apply(new HighestPoint1ByPatient(sourceName));
        try {
            return divide(highestPoint1ByPatient.getResult(), point1.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
