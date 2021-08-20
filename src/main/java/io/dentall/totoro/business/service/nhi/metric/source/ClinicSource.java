package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

public class ClinicSource extends SubjectSource<NhiMetricRawVM, NhiMetricRawVM> {

    public ClinicSource(InputSource<NhiMetricRawVM> inputSource) {
        super(inputSource);
    }

    @Override
    public List<NhiMetricRawVM> doFilter(List<NhiMetricRawVM> nhiMetricRawVMList) {
        return nhiMetricRawVMList;
    }

}
