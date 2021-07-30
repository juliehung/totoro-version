package io.dentall.totoro.business.service.nhi.metric.filter;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;

public interface Filter {

    List<NhiMetricRawVM> doFilter(List<NhiMetricRawVM> nhiMetricRawVMList);

    String inputKey();

    String outputKey();

}
