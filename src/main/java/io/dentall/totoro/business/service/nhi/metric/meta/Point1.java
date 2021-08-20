package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

/**
 * 診療費
 */
public class Point1 extends SingleSourceCalculator<Long> {

    public Point1(Collector collector, Source<?, ?> source) {
        this(collector, null, source);
    }

    public Point1(Collector collector, MetaConfig config, Source<?, ?> source) {
        super(collector, config, source);
    }

    @Override
    public Long doCalculate(Collector collector) {
        MetaConfig config = getConfig();
        Exam1 exam1 = new Exam1(collector, config, source()).apply();
        Exam2 exam2 = new Exam2(collector, config, source()).apply();
        Exam3 exam3 = new Exam3(collector, config, source()).apply();
        Exam4 exam4 = new Exam4(collector, config, source()).apply();
        Point3 point3 = new Point3(collector, config, source()).apply();

        return point3.getResult() + exam1.getResult() + exam2.getResult() + exam3.getResult() + exam4.getResult();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point1;
    }

}
