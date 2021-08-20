package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.*;

/**
 * 總人數(有診察費)
 */
public class Pt2 extends SingleSourceCalculator<Long> {

    public Pt2(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public Pt2(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());
        Exclude exclude = getExclude();

        return nhiMetricRawVMList.stream()
            .filter(applyExcludeByVM(exclude))
            .filter(vm -> !isPreventionCardNumber(vm.getCardNumber()))
            .reduce(0L, calculatePt(), Long::sum);
    }

    @Override
    public MetaType metaType() {
        return MetaType.Pt2;
    }
}
