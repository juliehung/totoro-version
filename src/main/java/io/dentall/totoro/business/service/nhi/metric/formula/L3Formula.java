package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.*;

import java.math.BigDecimal;

/**
 * 診療費點數 ＠date-15＠ 的 @Point-3@
 */
public class L3Formula extends AbstractFormula {

    private final String sourceName;

    public L3Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal doCalculate() {
        Point3 point3 = apply(new Point3(sourceName));
        return new BigDecimal(point3.getResult());
    }
}
