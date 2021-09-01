package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
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
public class Ic2 extends SingleSourceMetaCalculator<Long> {

    public Ic2(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Ic2(MetricConfig metricConfig, MetaConfig metaConfig, Source<?, ?> source) {
        super(metricConfig, metaConfig, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<NhiMetricRawVM> nhiMetricRawVMList = metricConfig.retrieveSource(source().key());

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
