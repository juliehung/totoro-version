package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.domain.User;

import java.util.function.Function;
import java.util.stream.Stream;

public class DoctorSubject extends MetricSubject {

    private final User doctor;

    public DoctorSubject(User doctor) {
        super(MetricSubjectType.doctor);
        this.doctor = doctor;
    }

    @Override
    public Function<Stream<MetricTooth>, Stream<MetricTooth>> getFilterFunction() {
        return (source) -> source.filter(vm -> this.doctor.getId().equals(vm.getDoctorId()));
    }

    @Override
    public Function<Stream<MetricDisposal>, Stream<MetricDisposal>> getDisposalFilterFunction() {
        return (source) -> source.filter(vm -> this.doctor.getId().equals(vm.getDoctorId()));
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
    public Source<MetricTooth, MetricTooth> getSource(MetricConfig metricConfig) {
        return new DoctorSource(metricConfig);
    }

    @Override
    public Source<MetricDisposal, MetricDisposal> getDisposalSource(MetricConfig metricConfig) {
        return new DoctorDisposalSource(metricConfig);
    }

}
