package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

/**
 * 診療費
 */
public class Point1 extends SingleSourceCalculator<Long> {

    public Point1(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public Point1(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        MetaConfig config = getConfig();
        Exam1 exam1 = new Exam1(collector, config, sourceName()).apply();
        Exam2 exam2 = new Exam2(collector, config, sourceName()).apply();
        Exam3 exam3 = new Exam3(collector, config, sourceName()).apply();
        Exam4 exam4 = new Exam4(collector, config, sourceName()).apply();
        Point3 point3 = new Point3(collector, config, sourceName()).apply();

        return point3.getResult() + exam1.getResult() + exam2.getResult() + exam3.getResult() + exam4.getResult();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point1;
    }

}
