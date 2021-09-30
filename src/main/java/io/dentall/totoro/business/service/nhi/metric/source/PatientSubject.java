package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.domain.Patient;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class PatientSubject extends MetricSubject {

    private final Patient patient;

    public PatientSubject(Patient patient) {
        super(MetricSubjectType.patient);
        this.patient = patient;
    }

    @Override
    public Function<Stream<MetricTooth>, List<MetricTooth>> getFilterFunction() {
        return source -> source.filter(vm -> vm.getPatientId().equals(this.patient.getId())).collect(toList());
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
    public Source<?, ?> getSource(MetricConfig metricConfig) {
        return new PatientSource(metricConfig);
    }
}
