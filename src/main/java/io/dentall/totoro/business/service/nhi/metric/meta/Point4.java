package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.Objects;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;

/**
 * 部分負擔點數
 */
public class Point4 extends SingleSourceMetaCalculator<Long> {

    public Point4(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Point4(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<NhiMetricRawVM> nhiMetricRawVMList = metricConfig.retrieveSource(source().key());
        Exclude exclude = getExclude();

        return nhiMetricRawVMList.stream()
            .filter(applyExcludeByVM(exclude))
            .map(NhiMetricRawVM::getPartialBurden)
            .filter(Objects::nonNull)
            .map(Long::valueOf)
            .reduce(Long::sum)
            .orElse(0L);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Point4;
    }
}
