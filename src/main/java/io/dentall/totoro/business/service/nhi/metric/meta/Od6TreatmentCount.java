package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

/**
 * @ OD-6@醫令數
 */
public class Od6TreatmentCount extends SingleSourceMetaCalculator<Long> {

    private static final List<String> codes = unmodifiableList(asList("89003C", "89010C", "89012C"));

    public Od6TreatmentCount(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public Od6TreatmentCount(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
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