package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 90001C+90002C+90003C+90016C+ 90018C+90019C+ 90020C tooth
 */
public class EndoTreatmentByTooth extends EndoTreatment {

    public EndoTreatmentByTooth(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<MetricTooth> nhiMetricRawVMList = metricConfig.retrieveSource(source().key());

        // 加總所有病人的所有處置的牙數數目
        return nhiMetricRawVMList.stream()
            .filter(vm -> codes.contains(vm.getTreatmentProcedureCode()))
            .map(MetricTooth::getTooth)
            .filter(StringUtils::isNotBlank)
            .count();
    }

}
