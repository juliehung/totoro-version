package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 申報件數
 * <p>
 * 有卡號＋異常代碼之處置單數量總和
 * 同一療程在當月完成以一件計算，跨月完成則以二件計算
 */
public class Ic3 extends SingleSourceCalculator {

    public Ic3(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());

        return (long) nhiMetricRawVMList.stream()
            .filter(vm -> vm.getDisposalDate() != null && isNotBlank(vm.getCardNumber()))
            .map(vm -> vm.getDisposalDate() + vm.getCardNumber())
            .collect(groupingBy(key -> key))
            .keySet().size();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Ic3;
    }
}
