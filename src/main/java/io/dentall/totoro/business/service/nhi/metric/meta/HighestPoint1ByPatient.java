package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

/**
 * 診療費 病患點數(最高者)
 */
public class HighestPoint1ByPatient extends SingleSourceCalculator<Long> {

    public HighestPoint1ByPatient(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        HighestExam1ByPatient exam1 = new HighestExam1ByPatient(collector, sourceName()).apply();
        HighestExam2ByPatient exam2 = new HighestExam2ByPatient(collector, sourceName()).apply();
        HighestExam3ByPatient exam3 = new HighestExam3ByPatient(collector, sourceName()).apply();
        HighestExam4ByPatient exam4 = new HighestExam4ByPatient(collector, sourceName()).apply();
        HighestPoint3ByPatient point3 = new HighestPoint3ByPatient(collector, sourceName()).apply();

        return point3.getResult() + exam1.getResult() + exam2.getResult() + exam3.getResult() + exam4.getResult();
    }

    @Override
    public MetaType metaType() {
        return MetaType.HighestPoint1ByPatient;
    }

}
