package io.dentall.totoro.step_definitions.holders;

import io.cucumber.spring.ScenarioScope;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ScenarioScope
@Data
public class PatientTestInfoHolder {

    // Latest patient
    private Patient patient;

    // Keep all patients in scenario scope as list
    private List<Patient> patients = new ArrayList<>();

    // Keep all patients in scenario scope as map that key is a name
    private Map<String, Patient> patientMap = new HashMap<>();
}
