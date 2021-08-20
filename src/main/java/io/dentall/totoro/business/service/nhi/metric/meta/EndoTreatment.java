package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.Collector;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

/**
 * 90001C+90002C+90003C+90016C+90018C+90019C+90020C
 */
public class EndoTreatment extends SingleSourceCalculator<Long> {

    public static final List<String> codes = unmodifiableList(asList("90001C", "90002C", "90003C", "90016C", "90018C", "90019C", "90020C"));

    public EndoTreatment(Collector collector, String sourceName) {
        this(collector, null, sourceName);
    }

    public EndoTreatment(Collector collector, MetaConfig config, String sourceName) {
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
        return MetaType.EndoTreatment;
    }

}
