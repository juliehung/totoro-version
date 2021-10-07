package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

/**
 * @ OD-4@醫令數
 */
public class Od4TreatmentCount extends SingleSourceMetaCalculator<Long> {

    private static final List<String> codes = unmodifiableList(asList("89001C", "89004C", "89008C", "89011C"));

    public Od4TreatmentCount(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Od4TreatmentCount(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<MetricTooth> source = metricConfig.retrieveSource(source().key());
        return source.stream()
            .filter(dto -> codes.contains(dto.getTreatmentProcedureCode()))
            .count();
    }

}
