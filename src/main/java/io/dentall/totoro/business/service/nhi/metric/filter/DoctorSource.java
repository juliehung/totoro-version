package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class DoctorSource extends SubjectSource<NhiMetricRawVM, NhiMetricRawVM> {

    private final Long doctorId;

    public DoctorSource(Long doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public List<NhiMetricRawVM> doFilter(List<NhiMetricRawVM> nhiMetricRawVMList) {
        return nhiMetricRawVMList.stream().parallel()
            .filter(vm -> this.doctorId.equals(vm.getDoctorId()))
            .collect(toList());
    }

    @Override
    public String inputKey() {
        return this.filterKey.input();
    }

    @Override
    public String outputKey() {
        return this.filterKey.output();
    }
}
