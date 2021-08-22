package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

public class ClinicSource extends SubjectSource<NhiMetricRawVM, NhiMetricRawVM> {

    public ClinicSource(MetricConfig metricConfig) {
        super(metricConfig.getInitialSource());
    }

    @Override
    public List<NhiMetricRawVM> filter(List<NhiMetricRawVM> nhiMetricRawVMList) {
        return nhiMetricRawVMList;
    }

}
