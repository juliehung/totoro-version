package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.filter.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * 90001C+90002C+90003C+90016C+90018C+90019C+90020C
 */
public class EndoTreatment extends SingleSourceCalculator {

    public static final List<String> codes = asList("90001C", "90002C", "90003C", "90016C", "90018C", "90019C", "90020C");

    public EndoTreatment(Collector collector, String sourceName) {
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
        return MetaType.EndoTreatment;
    }

}
