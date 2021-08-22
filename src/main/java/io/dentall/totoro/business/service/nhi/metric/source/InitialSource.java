package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

public class InitialSource extends AbstractSource<NhiMetricRawVM, NhiMetricRawVM> {

    public InitialSource() {
        super(null);
    }

    @Override
    public List<NhiMetricRawVM> filter(List<NhiMetricRawVM> source) {
        return source;
    }

}
