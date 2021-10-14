package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

/**
 * 醫令數
 */
public class TreatmentCount extends SingleSourceMetaCalculator<Long> {

    private final List<String> codes;

    public TreatmentCount(MetricConfig metricConfig, Source<?, ?> source, List<String> codes) {
        this(metricConfig, null, source, codes);
    }

    public TreatmentCount(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source, List<String> codes) {
        super(metricConfig, config, source);
        this.codes = codes;
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<MetricTooth> source = metricConfig.retrieveSource(source().key());
        return source.stream()
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .count();
    }

    @Override
    public List<?> getExtraKeyAttribute() {
        return this.codes;
    }
}
