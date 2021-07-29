package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 看診人數/就醫人數 不重複病患數量
 */
public class Pt1 extends AbstractCalculator {

    public Pt1(String sourceName) {
        super(sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());

        return (long) nhiMetricRawVMList.stream()
            .map(NhiMetricRawVM::getPatientId)
            .filter(Objects::nonNull)
            .collect(Collectors.groupingBy(id -> id))
            .keySet().size();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Pt1;
    }
}
