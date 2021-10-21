package io.dentall.totoro.step_definitions.holders;

import io.cucumber.spring.ScenarioScope;
import io.dentall.totoro.business.service.nhi.metric.dto.MetricDisposal;
import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;
import io.dentall.totoro.business.service.nhi.metric.meta.MetaConfig;
import io.dentall.totoro.business.service.nhi.metric.source.MetricSubject;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ScenarioScope
@Data
public class MetricTestInfoHolder {

    private MetricSubject subject;

    private List<Patient> patients = new ArrayList<>();

    private List<User> doctors = new ArrayList<>();

    private List<MetricDisposal> source;

    private MetaConfig metaConfig = new MetaConfig();

    private Exclude exclude;
}
