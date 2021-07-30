package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;

/**
 * 診療費 病患點數(最高者)
 */
public class HighestPoint1ByPatient extends AbstractCalculator {


    public HighestPoint1ByPatient(String sourceName) {
        super(sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        HighestExam1ByPatient exam1 = new HighestExam1ByPatient(sourceName());
        HighestExam2ByPatient exam2 = new HighestExam2ByPatient(sourceName());
        HighestExam3ByPatient exam3 = new HighestExam3ByPatient(sourceName());
        HighestExam4ByPatient exam4 = new HighestExam4ByPatient(sourceName());
        HighestPoint3ByPatient point3 = new HighestPoint3ByPatient(sourceName());
        collector.apply(exam1).apply(exam2).apply(exam3).apply(exam4).apply(point3);

        return point3.getResult() + exam1.getResult() + exam2.getResult() + exam3.getResult() + exam4.getResult();
    }

    @Override
    public MetaType metaType() {
        return MetaType.HighestPoint1ByPatient;
    }

}
