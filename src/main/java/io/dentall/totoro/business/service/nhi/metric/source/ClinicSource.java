package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;
import static java.util.stream.Collectors.toList;

public class ClinicSource extends SubjectSource<NhiMetricRawVM, NhiMetricRawVM> {

    public ClinicSource(MetricConfig metricConfig) {
        super(metricConfig);
    }

    @Override
    public List<NhiMetricRawVM> filter(List<NhiMetricRawVM> source) {
        return source.stream().parallel()
            .filter(applyExcludeByVM(getExclude()))
            .collect(toList());
    }

}
