package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

/**
 * 診療費
 */
public class Point1 extends AbstractCalculator {


    public Point1(String sourceName) {
        super(sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        Exam1 exam1 = new Exam1(sourceName());
        Exam2 exam2 = new Exam2(sourceName());
        Exam3 exam3 = new Exam3(sourceName());
        Exam4 exam4 = new Exam4(sourceName());
        Point3 point3 = new Point3(sourceName());
        collector.apply(exam1).apply(exam2).apply(exam3).apply(exam4).apply(point3);

        return point3.getResult() + exam1.getResult() + exam2.getResult() + exam3.getResult() + exam4.getResult();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point1;
    }

}
