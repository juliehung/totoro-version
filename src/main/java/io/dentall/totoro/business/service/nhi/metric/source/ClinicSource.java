package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

public class ClinicSource extends SubjectSource<NhiMetricRawVM, NhiMetricRawVM> {

    @Override
    public List<NhiMetricRawVM> doFilter(List<NhiMetricRawVM> nhiMetricRawVMList) {
        return nhiMetricRawVMList;
    }

    @Override
    public String inputKey() {
        return this.sourceId.input();
    }

    @Override
    public String outputKey() {
        return this.sourceId.output();
    }
}
