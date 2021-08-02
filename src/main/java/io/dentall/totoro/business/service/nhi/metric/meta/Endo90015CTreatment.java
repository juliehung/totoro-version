package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static java.util.Collections.singletonList;

public class Endo90015CTreatment extends SingleSourceCalculator {

    private static final List<String> codes = singletonList("90015C");

    public Endo90015CTreatment(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());

        return nhiMetricRawVMList.stream()
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .count();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Endo90015CTreatment;
    }
}
