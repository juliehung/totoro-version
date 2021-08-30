package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.stream.Stream;

public class InitialSource extends AbstractSource<NhiMetricRawVM, NhiMetricRawVM> {

    public InitialSource() {
        super(null);
    }

    @Override
    public List<NhiMetricRawVM> doFilter(Stream<NhiMetricRawVM> source) {
        throw new RuntimeException("Should not be called.");
    }

}
