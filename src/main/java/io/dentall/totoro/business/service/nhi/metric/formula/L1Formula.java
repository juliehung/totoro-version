package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.meta.Point1;

import java.math.BigDecimal;

/**
 * 合計點數 ＠date-15＠ 的 @Point-1@
 */
public class L1Formula extends AbstractFormula {

    private final String sourceName;

    public L1Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    protected BigDecimal doCalculate() {
        Point1 point1 = apply(new Point1(sourceName));
        return new BigDecimal(point1.getResult());
    }

}
