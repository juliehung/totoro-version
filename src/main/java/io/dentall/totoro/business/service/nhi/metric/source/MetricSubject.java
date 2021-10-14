package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;

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

    public abstract Function<Stream<MetricTooth>, List<MetricTooth>> getFilterFunction();

    public abstract Long getId();

    public abstract String getName();

    public abstract Source<?, ?> getSource(MetricConfig metricConfig);
}
