package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 看診天數
 * <p>
 * 有處置單的不重複日期總和
 */
public class Ic2 extends SingleSourceCalculator<Long> {

    public Ic2(Collector collector, Source<?, ?> source) {
        super(collector, source);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(source());

        return (long) nhiMetricRawVMList.stream()
            .filter(vm -> isNotBlank(vm.getCardNumber()))
            .map(NhiMetricRawVM::getDisposalDate)
            .collect(groupingBy(key -> key))
            .keySet().size();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Ic2;
    }
}
