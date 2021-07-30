package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic3;
import io.dentall.totoro.business.service.nhi.metric.meta.Point1;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt1;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 季平均就醫>2次 ＠date-10@ 的 ＠IC-3＠/@PT-1@
 */
public class L19Formula extends AbstractFormula {

    private final String sourceName;

    public L19Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal doCalculate() {
        Pt1 pt1 = apply(new Pt1(sourceName));
        Ic3 ic3 = apply(new Ic3(sourceName));
        try {
            return divide(ic3.getResult(), pt1.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
