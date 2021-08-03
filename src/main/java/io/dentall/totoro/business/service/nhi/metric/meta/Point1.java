package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

/**
 * 診療費
 */
public class Point1 extends SingleSourceCalculator<Long> {

    public Point1(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        Exam1 exam1 = new Exam1(collector, sourceName()).apply();
        Exam2 exam2 = new Exam2(collector, sourceName()).apply();
        Exam3 exam3 = new Exam3(collector, sourceName()).apply();
        Exam4 exam4 = new Exam4(collector, sourceName()).apply();
        Point3 point3 = new Point3(collector, sourceName()).apply();

        return point3.getResult() + exam1.getResult() + exam2.getResult() + exam3.getResult() + exam4.getResult();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point1;
    }

}
