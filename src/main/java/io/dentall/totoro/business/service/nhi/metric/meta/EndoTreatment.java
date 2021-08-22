package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

/**
 * 90001C+90002C+90003C+90016C+90018C+90019C+90020C
 */
public class EndoTreatment extends SingleSourceMetaCalculator<Long> {

    public static final List<String> codes = unmodifiableList(asList("90001C", "90002C", "90003C", "90016C", "90018C", "90019C", "90020C"));

    public EndoTreatment(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public EndoTreatment(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<NhiMetricRawVM> nhiMetricRawVMList = metricConfig.retrieveSource(source().key());
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
