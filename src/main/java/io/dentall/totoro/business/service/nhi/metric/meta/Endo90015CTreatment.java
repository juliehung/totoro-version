package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;
import static java.util.Collections.singletonList;

public class Endo90015CTreatment extends SingleSourceCalculator<Long> {

    private static final List<String> codes = singletonList("90015C");

    public Endo90015CTreatment(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public Endo90015CTreatment(Collector collector, MetaConfig config, String sourceName) {
        super(collector, config, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());
        Exclude exclude = getExclude();

        return nhiMetricRawVMList.stream()
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .filter(applyExcludeByVM(exclude))
            .count();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Endo90015CTreatment;
    }
}
