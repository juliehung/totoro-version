package io.dentall.totoro.step_definitions.holders;

import io.cucumber.spring.ScenarioScope;
import io.dentall.totoro.domain.Note;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.web.rest.vm.NoteVM;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ScenarioScope
@Data
public class NoteTestInfoHolder {

    // Latest note
    private NoteVM noteVm;

    // Keep all patients in scenario scope as list
    private List<NoteVM> noteVms = new ArrayList<>();

    // Keep created notes group by doctor
    private Map<String, List<NoteVM>> doctorNoteMap = new HashMap<>();

    // Keep created notes group by patient
    private Map<String, List<NoteVM>> patientNoteMap = new HashMap<>();

}
