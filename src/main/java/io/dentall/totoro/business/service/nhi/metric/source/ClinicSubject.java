package io.dentall.totoro.business.service.nhi.metric.source;

import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
import io.dentall.totoro.domain.User;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static io.dentall.totoro.business.service.nhi.metric.source.MetricConstants.CLINIC_ID;
import static java.util.stream.Collectors.toList;

public class ClinicSubject extends MetricSubject {

    private final User clinic;

    public ClinicSubject() {
        super(MetricSubjectType.clinic);
        clinic = new User();
        clinic.setId(CLINIC_ID);
        clinic.setFirstName("clinic");
    }

    @Override
    public Function<Stream<NhiMetricRawVM>, List<NhiMetricRawVM>> getFilterFunction() {
        return (source) -> source.collect(toList());
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
    public Source<?, ?> getSource(MetricConfig metricConfig) {
        return new ClinicSource(metricConfig);
    }
}
