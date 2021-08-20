package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

public class Point2 extends SingleSourceCalculator<Long> {

    public Point2(Collector collector, Source<?, ?> source) {
        this(collector, null, source);
    }

    public Point2(Collector collector, MetaConfig config, Source<?, ?> source) {
        super(collector, config, source);
    }

    @Override
    public Long doCalculate(Collector collector) {
        MetaConfig config = getConfig();
        Point1 point1 = new Point1(collector, config, source()).apply();
        Point4 point4 = new Point4(collector, config, source()).apply();

        return point1.getResult() - point4.getResult();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point2;
    }
}
