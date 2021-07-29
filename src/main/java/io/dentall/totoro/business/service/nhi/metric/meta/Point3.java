package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;

/**
 * 診療費
 */
public class Point3 extends AbstractCalculator {


    public Point3(String sourceName) {
        super(sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());

        //  雖然公式是「全部代碼-(@Exam-1@+@Exam-2@+@Exam-3@+@Exam-4@)」，但因為examination code，不會出現在treatment procedure資料表裡，所以就不需要特別扣除examination code的點數
        return nhiMetricRawVMList.stream()
            .map(NhiMetricRawVM::getTreatmentProcedureTotal)
            .filter(Objects::nonNull)
            .reduce(Long::sum)
            .orElse(0L);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point3;
    }

}
