package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.Ic3;
import io.dentall.totoro.business.service.nhi.metric.meta.Point2;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt1;

import java.math.BigDecimal;

import static io.dentall.totoro.business.service.nhi.metric.util.NumericUtils.divide;

/**
 * 申報件數 ＠date-15＠ 的 @IC-3@
 */
public class L7Formula extends AbstractFormula {

    private final String sourceName;

    public L7Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal doCalculate() {
        Ic3 ic3 = apply(new Ic3(sourceName));
        return new BigDecimal(ic3.getResult());
    }
}
