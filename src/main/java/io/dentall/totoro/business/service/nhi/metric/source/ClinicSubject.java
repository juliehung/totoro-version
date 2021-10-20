package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricTooth;
import io.dentall.totoro.domain.User;

import java.util.function.Function;
import java.util.stream.Stream;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CLINIC_ID;

public class ClinicSubject extends MetricSubject {

    private final User clinic;

    public ClinicSubject() {
        super(MetricSubjectType.clinic);
        clinic = new User();
        clinic.setId(CLINIC_ID);
        clinic.setFirstName("clinic");
    }

    @Override
    public Function<Stream<MetricTooth>, Stream<MetricTooth>> getFilterFunction() {
        return (source) -> source;
    }

    @Override
    public Function<Stream<MetricDisposal>, Stream<MetricDisposal>> getDisposalFilterFunction() {
        return (source) -> source;
    }

    @Override
    public Long getId() {
        return this.clinic.getId();
    }

    @Override
    public String getName() {
        return this.clinic.getFirstName();
    }

    @Override
    public Source<MetricTooth, MetricTooth> getSource(MetricConfig metricConfig) {
        return new ClinicSource(metricConfig);
    }

    @Override
    public Source<MetricDisposal, MetricDisposal> getDisposalSource(MetricConfig metricConfig) {
        return new ClinicDisposalSource(metricConfig);
    }
}
