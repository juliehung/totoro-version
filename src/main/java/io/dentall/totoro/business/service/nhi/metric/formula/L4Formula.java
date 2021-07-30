package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.Point2;
import io.dentall.totoro.business.service.nhi.metric.meta.Point3;

import java.math.BigDecimal;

/**
 * 申請點數 ＠date-15＠ 的 @Point-2@
 */
public class L4Formula extends AbstractFormula {

    private final String sourceName;

    public L4Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal doCalculate() {
        Point2 point2 = apply(new Point2(sourceName));
        return new BigDecimal(point2.getResult());
    }
}
