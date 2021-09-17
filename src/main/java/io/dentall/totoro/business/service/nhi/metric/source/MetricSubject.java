package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public abstract class MetricSubject {

    protected final MetricSubjectType subjectType;

    public MetricSubject(MetricSubjectType subjectType) {
        this.subjectType = subjectType;
    }

    public MetricSubjectType getSubjectType() {
        return subjectType;
    }

    public abstract Function<Stream<NhiMetricRawVM>, List<NhiMetricRawVM>> getFilterFunction();

    public abstract Long getId();

    public abstract String getName();

    public abstract Source<?, ?> getSource(MetricConfig metricConfig);
}
