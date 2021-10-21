package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.domain.Patient;

import java.util.function.Function;
import java.util.stream.Stream;

public class PatientSubject extends MetricSubject {

    private final Patient patient;

    public PatientSubject(Patient patient) {
        super(MetricSubjectType.patient);
        this.patient = patient;
    }

    @Override
    public Function<Stream<MetricTooth>, Stream<MetricTooth>> getFilterFunction() {
        return source -> source.filter(vm -> vm.getPatientId().equals(this.patient.getId()));
    }

    @Override
    public Function<Stream<MetricDisposal>, Stream<MetricDisposal>> getDisposalFilterFunction() {
        return source -> source.filter(vm -> vm.getPatientId().equals(this.patient.getId()));
    }

    @Override
    public Long getId() {
        return this.patient.getId();
    }

    @Override
    public String getName() {
        return this.patient.getName();
    }

    @Override
    public Source<MetricTooth, MetricTooth> getSource(MetricConfig metricConfig) {
        return new PatientSource(metricConfig);
    }

    @Override
    public Source<MetricDisposal, MetricDisposal> getDisposalSource(MetricConfig metricConfig) {
        return new PatientDisposalSource(metricConfig);
    }
}
