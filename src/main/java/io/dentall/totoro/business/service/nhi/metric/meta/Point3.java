package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.Objects;

/**
 * 診療費
 */
public class Point3 extends SingleSourceCalculator<Long> {

    public Point3(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());
        Exam1 exam1 = new Exam1(collector, sourceName()).apply();
        Exam2 exam2 = new Exam2(collector, sourceName()).apply();
        Exam3 exam3 = new Exam3(collector, sourceName()).apply();
        Exam4 exam4 = new Exam4(collector, sourceName()).apply();

        long points = nhiMetricRawVMList.stream()
            .map(NhiMetricRawVM::getTreatmentProcedureTotal)
            .filter(Objects::nonNull)
            .reduce(Long::sum)
            .orElse(0L);

        return points - exam1.getResult() - exam2.getResult() - exam3.getResult() - exam4.getResult();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point3;
    }

}
