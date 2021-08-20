package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.*;

/**
 * 看診人數/就醫人數 不重複病患數量
 */
public class Pt1 extends SingleSourceCalculator<Long> {

    public Pt1(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public Pt1(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());
        Exclude exclude = getExclude();

        return nhiMetricRawVMList.stream()
            .filter(applyExcludeByVM(exclude))
            .reduce(0L, calculatePt(), Long::sum);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Pt1;
    }
}
