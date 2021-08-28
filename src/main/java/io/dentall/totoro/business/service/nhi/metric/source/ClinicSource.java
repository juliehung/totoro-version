package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class ClinicSource extends SubjectSource<NhiMetricRawVM, NhiMetricRawVM> {

    public ClinicSource(MetricConfig metricConfig) {
        super(metricConfig);
    }

    @Override
    public List<NhiMetricRawVM> doFilter(Stream<NhiMetricRawVM> source) {
        return source.collect(toList());
    }

}
