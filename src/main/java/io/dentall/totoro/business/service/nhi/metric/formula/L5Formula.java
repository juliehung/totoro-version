package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.Point2;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt1;

import java.math.BigDecimal;

/**
 * 看診人數 ＠date-15＠ 的 @PT-1@
 */
public class L5Formula extends AbstractFormula {

    private final String sourceName;

    public L5Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal doCalculate() {
        Pt1 pt1 = apply(new Pt1(sourceName));
        return new BigDecimal(pt1.getResult());
    }
}
