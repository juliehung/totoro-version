package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CodesByEndo1;

/**
 * 90001C+90002C+90003C+90016C+90018C+90019C+90020C
 */
public class EndoTreatment extends SingleSourceMetaCalculator<Long> {

    public static final List<String> codes = CodesByEndo1;

    public EndoTreatment(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public EndoTreatment(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
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
