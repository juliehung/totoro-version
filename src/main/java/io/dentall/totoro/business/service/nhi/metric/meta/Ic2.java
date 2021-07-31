package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 看診天數
 * <p>
 * 有處置單的不重複日期總和
 */
public class Ic2 extends SingleSourceCalculator {

    public Ic2(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());

        return (long) nhiMetricRawVMList.stream()
            .filter(vm -> isNotBlank(vm.getCardNumber()))
            .map(vm -> vm.getDisposalDate())
            .collect(Collectors.groupingBy(key -> key))
            .keySet().size();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Ic2;
    }
}
