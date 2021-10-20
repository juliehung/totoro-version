package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;

import java.util.List;

import static java.util.stream.Collectors.groupingBy;

/**
 * 醫師人數
 */
public class DoctorCount extends SingleSourceMetaCalculator<Long> {

    public DoctorCount(MetricConfig metricConfig, Source<MetricDisposal, ?> source) {
        this(metricConfig, null, source);
    }

    public DoctorCount(MetricConfig metricConfig, MetaConfig config, Source<MetricDisposal, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<MetricDisposal> source = metricConfig.retrieveSource(source().key());
        return (long) source.stream()
            .collect(groupingBy(MetricDisposal::getDoctorId))
            .keySet()
            .size();
    }

}
