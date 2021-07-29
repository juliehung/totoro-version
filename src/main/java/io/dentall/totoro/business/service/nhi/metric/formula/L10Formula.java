package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic2;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic3;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;
import static java.math.BigDecimal.ZERO;

/**
 * 病患最高總點數佔比 ＠date-15＠ 的 病患合計點數(最高者)/@Point-1@
 */
public class L10Formula implements Formula {

    private final String sourceName;

    public L10Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal calculate(Collector collector) {
        Ic2 ic2 = new Ic2(sourceName);
        Ic3 ic3 = new Ic3(sourceName);
        collector.apply(ic2).apply(ic3);
        try {
            return divide(ic3.getResult(), ic2.getResult());
        } catch (ArithmeticException e) {
            return ZERO;
        }
    }
}
