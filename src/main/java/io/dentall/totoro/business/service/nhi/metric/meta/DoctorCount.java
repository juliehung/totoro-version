package io.dentall.totoro.business.service.nhi.metric.meta;

import io.dentall.totoro.business.service.nhi.metric.source.MetricConfig;
import io.dentall.totoro.business.service.nhi.metric.source.Source;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.stream.Collectors;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;

/**
 * 醫師人數
 */
public class DoctorCount extends SingleSourceMetaCalculator<Long> {

    public DoctorCount(MetricConfig metricConfig, Source<?, ?> source) {
        this(metricConfig, null, source);
    }

    public DoctorCount(MetricConfig metricConfig, MetaConfig config, Source<?, ?> source) {
        super(metricConfig, config, source);
    }

    @Override
    public Long doCalculate(MetricConfig metricConfig) {
        List<NhiMetricRawVM> source = metricConfig.retrieveSource(source().key());
        Exclude exclude = getExclude();
        return (long) source.stream()
            .filter(applyExcludeByVM(exclude))
            .collect(Collectors.groupingBy(NhiMetricRawVM::getDoctorId))
            .keySet()
            .size();
    }

    @Override
    public MetaType metaType() {
        return MetaType.DoctorCount;
    }

}
