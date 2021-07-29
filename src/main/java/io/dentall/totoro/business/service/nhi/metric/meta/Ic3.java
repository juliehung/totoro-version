package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * 申報件數
 * <p>
 * 有卡號＋異常代碼之處置單數量總和
 * 同一療程在當月完成以一件計算，跨月完成則以二件計算
 */
public class Ic3 extends AbstractCalculator {

    public Ic3(String sourceName) {
        super(sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());

        return (long) nhiMetricRawVMList.stream()
            .filter(vm -> vm.getDisposalDate() != null && isNotBlank(vm.getCardNumber()))
            .map(vm -> vm.getDisposalDate() + vm.getCardNumber())
            .collect(Collectors.groupingBy(key -> key))
            .keySet().size();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Ic3;
    }
}
