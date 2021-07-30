package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic2;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic3;
import io.dentall.totoro.business.service.nhi.metric.util.NumericUtils;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 一日平均件數 ＠date-15＠ 的 @IC-3@/@IC-2@
 */
public class L8Formula extends AbstractFormula {

    private final String sourceName;

    public L8Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal doCalculate() {
        Ic2 ic2 = apply(new Ic2(sourceName));
        Ic3 ic3 = apply(new Ic3(sourceName));
        try {
            return divide(ic3.getResult(), ic2.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
