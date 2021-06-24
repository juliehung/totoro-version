package io.dentall.totoro.step_definitions.holders;

import io.cucumber.spring.ScenarioScope;
import io.dentall.totoro.domain.Appointment;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@ScenarioScope
@Data
public class AppointmentTestInfoHolder {

    private Appointment appointment;
}
