package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.Point2;
import io.dentall.totoro.business.service.nhi.metric.meta.Pt1;

import java.math.BigDecimal;

/**
 * 看診人數 ＠date-15＠ 的 @PT-1@
 */
public class L5Formula implements Formula {

    private final String sourceName;

    public L5Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal calculate(Collector collector) {
        Pt1 pt1 = new Pt1(sourceName);
        collector.apply(pt1);
        return new BigDecimal(pt1.getResult());
    }
}
