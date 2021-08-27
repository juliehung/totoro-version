package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static io.dentall.totoro.business.service.nhi.metric.util.NhiMetricHelper.applyExcludeByVM;
import static java.util.stream.Collectors.toList;

public class DoctorSource extends SubjectSource<NhiMetricRawVM, NhiMetricRawVM> {

    private final Long doctorId;

    public DoctorSource(MetricConfig metricConfig) {
        super(metricConfig);
        this.doctorId = metricConfig.getSubject().getId();
    }

    @Override
    public List<NhiMetricRawVM> filter(List<NhiMetricRawVM> source) {
        return source.stream().parallel()
            .filter(vm -> this.doctorId.equals(vm.getDoctorId()))
            .filter(applyExcludeByVM(getExclude()))
            .collect(toList());
    }

}
