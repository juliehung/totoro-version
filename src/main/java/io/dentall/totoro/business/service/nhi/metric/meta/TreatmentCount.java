package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

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
        List<NhiMetricRawVM> source = metricConfig.retrieveSource(source().key());
        return source.stream()
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .count();
    }

    @Override
    public MetaType metaType() {
        return MetaType.TreatmentCount;
    }

    @Override
    public List<?> getExtraKeyAttribute() {
        return this.codes;
    }
}
