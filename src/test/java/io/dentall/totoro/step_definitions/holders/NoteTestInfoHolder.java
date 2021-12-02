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

    // Keep created notes group by doctor
    private Map<String, List<NoteVM>> doctorNoteMap = new HashMap<>();

    // Keep created notes group by patient
    private Map<String, List<NoteVM>> patientNoteMap = new HashMap<>();

    public void cleanNotes() {
        this.cleanPatientNotes();
        this.cleanDoctorNotes();
    }

    public void assignNotes(String patientName, String doctorName, NoteVM noteVM) {
       this.patientNoteMap
            .computeIfAbsent(
                patientName,
                k -> new ArrayList<>()
            )
            .add(noteVM);
        if (doctorName != null) {
            this.doctorNoteMap
                .computeIfAbsent(
                    doctorName,
                    k -> new ArrayList<>()
                )
                .add(noteVM);
        }
    }

    private void cleanDoctorNotes() {
        this.doctorNoteMap = new HashMap<>();
    }

    private void cleanPatientNotes() {
        this.patientNoteMap = new HashMap<>();
    }
}
