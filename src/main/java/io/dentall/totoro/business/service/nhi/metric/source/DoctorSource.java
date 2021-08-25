package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class DoctorSource extends SubjectSource<NhiMetricRawVM, NhiMetricRawVM> {

    private final Long doctorId;

    public DoctorSource(MetricConfig metricConfig) {
        super(metricConfig.getInitialSource());
        this.doctorId = metricConfig.getSubject().getId();
    }

    @Override
    public List<NhiMetricRawVM> filter(List<NhiMetricRawVM> nhiMetricRawVMList) {
        return nhiMetricRawVMList.stream().parallel()
            .filter(vm -> this.doctorId.equals(vm.getDoctorId()))
            .collect(toList());
    }

}
