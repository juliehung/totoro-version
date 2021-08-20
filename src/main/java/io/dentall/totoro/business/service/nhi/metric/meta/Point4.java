package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.Objects;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;

/**
 * 部分負擔點數
 */
public class Point4 extends SingleSourceCalculator<Long> {

    public Point4(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public Point4(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());
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
