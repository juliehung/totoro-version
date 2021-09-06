package io.dentall.totoro.step_definitions.holders;

import io.cucumber.spring.ScenarioScope;
import io.dentall.totoro.business.service.nhi.metric.meta.Exclude;
import io.dentall.totoro.business.service.nhi.metric.meta.MetaConfig;
import io.dentall.totoro.business.vm.nhi.NhiMetricRawVM;
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

    private User subject;

    private List<Patient> patients = new ArrayList<>();

    private List<? extends NhiMetricRawVM> source;

    private MetaConfig metaConfig = new MetaConfig();

    private Exclude exclude;
}
