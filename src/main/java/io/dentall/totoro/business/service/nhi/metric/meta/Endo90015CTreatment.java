package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static java.util.Collections.singletonList;

public class Endo90015CTreatment extends SingleSourceMetaCalculator<Long> {

    private static final List<String> codes = singletonList("90015C");

    public Endo90015CTreatment(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Endo90015CTreatment(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<MetricTooth> source = metricConfig.retrieveSource(source().key());

        return source.stream()
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .count();
    }

}
