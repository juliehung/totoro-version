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
public class L7Formula implements Formula {

    private final String sourceName;

    public L7Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal calculate(Collector collector) {
        Ic3 ic3 = new Ic3(sourceName);
        collector.apply(ic3);
        return new BigDecimal(ic3.getResult());
    }
}
