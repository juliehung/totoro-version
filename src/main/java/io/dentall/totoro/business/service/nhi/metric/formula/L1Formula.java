package io.dentall.totoro.business.service.nhi.metric.formula;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.service.nhi.metric.meta.*;

import java.math.BigDecimal;

/**
 * 合計點數 ＠date-15＠ 的 @Point-1@
 */
public class L1Formula implements Formula {

    private final String sourceName;

    public L1Formula(String sourceName) {
        this.sourceName = sourceName;
    }

    @Override
    public BigDecimal calculate(Collector collector) {
        Point1 point1 = new Point1(sourceName);
        collector.apply(point1);
        return new BigDecimal(point1.getResult());
    }
}
