package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

public class ClinicFilter extends SubjectFilter {

    @Override
    public List<NhiMetricRawVM> doFilter(List<NhiMetricRawVM> nhiMetricRawVMList) {
        return nhiMetricRawVMList;
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
