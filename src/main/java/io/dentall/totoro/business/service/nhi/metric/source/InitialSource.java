package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

public class InitialSource extends AbstractSource<NhiMetricRawVM, NhiMetricRawVM> {

    public InitialSource() {
        super(new InputSource<NhiMetricRawVM>() {
        });
    }

    @Override
    public List<NhiMetricRawVM> doFilter(List<NhiMetricRawVM> source) {
        return source;
    }

}
