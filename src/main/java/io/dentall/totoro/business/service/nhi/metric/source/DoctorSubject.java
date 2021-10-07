package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.domain.User;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class DoctorSubject extends MetricSubject {

    private final User doctor;

    public DoctorSubject(User doctor) {
        super(MetricSubjectType.doctor);
        this.doctor = doctor;
    }

    @Override
    public Function<Stream<MetricTooth>, List<MetricTooth>> getFilterFunction() {
        return (source) -> source.filter(vm -> this.doctor.getId().equals(vm.getDoctorId())).collect(toList());
    }

    @Override
    public Long getId() {
        return this.doctor.getId();
    }

    @Override
    public String getName() {
        return this.doctor.getFirstName();
    }

    @Override
    public Source<?, ?> getSource(MetricConfig metricConfig) {
        return new DoctorSource(metricConfig);
    }

}
