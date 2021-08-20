package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;

public class Point2 extends SingleSourceCalculator<Long> {

    public Point2(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public Point2(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        MetaConfig config = getConfig();
        Point1 point1 = new Point1(collector, config, sourceName()).apply();
        Point4 point4 = new Point4(collector, config, sourceName()).apply();

        return point1.getResult() - point4.getResult();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point2;
    }
}
