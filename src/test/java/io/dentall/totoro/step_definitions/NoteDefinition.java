package io.dentall.totoro.step_definitions;

import com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.dentall.totoro.domain.Note;
import io.dentall.totoro.domain.enumeration.NoteType;
import io.dentall.totoro.repository.NoteRepository;
import io.dentall.totoro.service.PatientService;
import io.dentall.totoro.service.UserService;
import io.dentall.totoro.step_definitions.holders.NoteTestInfoHolder;
import io.dentall.totoro.step_definitions.holders.PatientTestInfoHolder;
import io.dentall.totoro.step_definitions.holders.UserTestInfoHolder;
import io.dentall.totoro.test.mapper.NoteTestMapper;
import io.dentall.totoro.web.rest.NoteResource;
import io.dentall.totoro.web.rest.vm.NoteCreateVM;
import io.dentall.totoro.web.rest.vm.NoteVM;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.Assert;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NoteDefinition extends AbstractStepDefinition {

    @Autowired
    NoteRepository noteRepository;

    @Autowired
    UserService userService;

    @Autowired
    PatientService patientService;

    @Autowired
    UserTestInfoHolder userTestInfoHolder;

    @Autowired
    PatientTestInfoHolder patientTestInfoHolder;

    @Autowired
    NoteTestInfoHolder noteTestInfoHolder;

    String noteApi = "/api/notes";

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this.getClass());

        final NoteResource resource = new NoteResource(
            noteRepository,
            userService,
            patientService
        );

        this.mvc = MockMvcBuilders.standaloneSetup(resource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .build();

    }

    @DataTableType
    public Note note(Map<String, String> entry) {
        Note note = NoteTestMapper.INSTANCE.mapToNote(entry);


        note.setPatient(
            patientTestInfoHolder.getPatientMap().get(entry.get("patientName"))
        );
        note.setUser(
            userTestInfoHolder.getUserMap().get(entry.get("doctorName"))
        );

        return note;
    }

    @Given("建立筆記")
    public void createDoctorNote(List<Note> notes) {
        notes.forEach(note -> {
            try {
                NoteVM noteVM = postNote(NoteTestMapper.INSTANCE.noteToNoteCreateVM(note));
                noteTestInfoHolder.getPatientNoteMap().putIfAbsent(
                    note.getPatient().getName(),
                    new ArrayList<>(Arrays.asList(noteVM))
                ).add(noteVM);
                noteTestInfoHolder.getDoctorNoteMap().putIfAbsent(
                    note.getUser().getFirstName(),
                    new ArrayList<>(Arrays.asList(noteVM))
                ).add(noteVM);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Then("查詢醫生 {word} 的筆記")
    public void queryDoctorNotesValidation(String doctorName) throws Exception {
        List<NoteVM> notes = getNotes(
            NoteType.DOCTOR,
            null,
            userTestInfoHolder.getUserMap().get(doctorName).getId()
        );

        validateNotes(noteTestInfoHolder.getDoctorNoteMap().get(doctorName), notes);
    }

    @Then("查詢有關病患 {word} 的筆記")
    public void queryPatientNotesValidation(String patientName) throws Exception {
        List<NoteVM> notes = getNotes(
            NoteType.DOCTOR,
            patientTestInfoHolder.getPatientMap().get(patientName).getId(),
            null
        );

        validateNotes(noteTestInfoHolder.getPatientNoteMap().get(patientName), notes);
    }

    @Then("應當查無醫師 {word} 的筆記")
    public void queryDoctorNotesButEmpty(String doctorName) throws Exception {
        List<NoteVM> notes = getEmptyNoteList(
            null,
            userTestInfoHolder.getUserMap().get(doctorName).getId()
        );

        Assert.assertEquals(0, notes.size());
    }

    @Then("應當查無病患 {word} 的筆記")
    public void queryPatientNotesButEmpty(String patientName) throws Exception {
        List<NoteVM> notes = getEmptyNoteList(
            patientTestInfoHolder.getPatientMap().get(patientName).getId(),
            null
        );

        Assert.assertEquals(0, notes.size());
    }

    private NoteVM postNote(NoteCreateVM note) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post(noteApi)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(note));

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        NoteVM noteVM = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsByteArray(), NoteVM.class);

        noteTestInfoHolder.setNoteVm(noteVM);
        noteTestInfoHolder.getNoteVms().add(noteVM);

        return noteVM;
    }

    private List<NoteVM> getNotes(
        NoteType type,
        Long patientId,
        Long userId
    ) throws Exception {
        ResultActions resultActions = this.mvc.perform(
                get(noteApi)
                    .contentType(APPLICATION_JSON)
                    .params(
                        generateRequestParams(
                            type,
                            patientId,
                            userId
                        )
                    )
            )
            .andExpect(status().is2xxSuccessful());

        return objectMapper.readValue(
            resultActions.andReturn().getResponse().getContentAsString(),
            new TypeReference<List<NoteVM>>() {}
        );
    }

    private List<NoteVM> getEmptyNoteList(
        Long patientId,
        Long userId
    ) throws Exception {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        ResultActions resultActions = this.mvc.perform(
                get(noteApi)
                    .contentType(APPLICATION_JSON)
                    .params(
                        generateRequestParams(
                            null,
                            patientId,
                            userId
                        )
                    )
            )
            .andExpect(status().is2xxSuccessful());

        return objectMapper.readValue(
            resultActions.andReturn().getResponse().getContentAsString(),
            new TypeReference<List<NoteVM>>() {}
        );
    }

    private LinkedMultiValueMap<String, String> generateRequestParams(
        NoteType type,
        Long patientId,
        Long userId
    ) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        if (type != null) {
            params.add("type", type.toString());
        }
        if (patientId != null) {
            params.add("patientId", String.valueOf(patientId));
        }
        if (userId != null) {
            params.add("userId", String.valueOf(userId));
        }

        return params;
    }

    private void validateNotes(
        List<NoteVM> expected,
        List<NoteVM> target
    ) {
        Assert.assertEquals(expected.size(), target.size());

        for (int i = 0; i < expected.size(); i++) {
            validateNote(expected.get(i), target.get(i));
        }
    }

    private void validateNote(
        NoteVM expected,
        NoteVM target
    ) {
        Assert.assertTrue(expected.equals(target));
    }
}
