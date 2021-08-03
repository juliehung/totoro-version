package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

public class Point2 extends SingleSourceCalculator<Long> {


    public Point2(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        Point1 point1 = new Point1(collector, sourceName()).apply();
        Point4 point4 = new Point4(collector, sourceName()).apply();

        return point1.getResult() - point4.getResult();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point2;
    }
}
