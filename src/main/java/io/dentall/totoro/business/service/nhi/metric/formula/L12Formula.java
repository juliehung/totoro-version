package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.CourseCase;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic2;

import java.math.BigDecimal;

/**
 * 看診天數 ＠date-15＠ 的 @IC-2@
 */
public class L12Formula extends AbstractFormula {

    private final String sourceName;

    public L12Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal doCalculate() {
        Ic2 ic2 = apply(new Ic2(sourceName));
        return new BigDecimal(ic2.getResult());
    }
}
