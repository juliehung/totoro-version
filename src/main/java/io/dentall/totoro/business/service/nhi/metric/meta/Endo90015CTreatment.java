package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.Optional;

import static io.dentall.totoro.business.service.nhi.metric.mapper.NhiMetricRawMapper.INSTANCE;
import static java.util.Collections.singletonList;
import static java.util.Optional.ofNullable;

public class Endo90015CTreatment extends SingleSourceCalculator<Long> {

    private static final List<String> codes = singletonList("90015C");

    public Endo90015CTreatment(Collector collector, String sourceName) {
        super(collector, sourceName);
    }

    public Endo90015CTreatment(Collector collector, Exclude exclude, String sourceName) {
        super(collector, exclude, sourceName);
    }

    @Override
    public Long doCalculate(Collector collector) {
        List<NhiMetricRawVM> nhiMetricRawVMList = collector.retrieveSource(sourceName());
        Exclude exclude = getExclude();

        return nhiMetricRawVMList.stream()
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .filter(vm -> ofNullable(exclude).map(exclude1 -> exclude1.test(INSTANCE.mapToExcludeDto(vm))).orElse(true))
            .count();
    }

    @Override
    public MetaType metaType() {
        return MetaType.Endo90015CTreatment;
    }
}
