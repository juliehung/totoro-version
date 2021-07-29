package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class DoctorFilter extends SubjectFilter {

    private Long doctorId;

    public DoctorFilter(Long doctorId) {
        this.doctorId = doctorId;
    }

    @Override
    public List<NhiMetricRawVM> doFilter(List<NhiMetricRawVM> nhiMetricRawVMList) {
        return nhiMetricRawVMList.stream().parallel()
            .filter(vm -> this.doctorId.equals(vm.getDoctorId()))
            .collect(toList());
    }

    @Override
    public FilterKey filterKey() {
        return this.filterKey;
    }
}
