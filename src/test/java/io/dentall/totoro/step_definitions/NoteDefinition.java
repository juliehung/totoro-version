package io.dentall.totoro.step_definitions;

import com.fasterxml.jackson.core.type.TypeReference;
import io.cucumber.java.Before;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.dentall.totoro.domain.Note;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.User;
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
import org.apache.commons.lang.StringUtils;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.Assert;
import org.springframework.util.LinkedMultiValueMap;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.dentall.totoro.web.rest.TestUtil.createFormattingConversionService;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    String notePatchApi = "/api/notes/%d";

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

        User fakeUser = new User();
        fakeUser.setId(0L);

        Patient fakePatient = new Patient();
        fakePatient.setId(0L);

        switch (entry.get("patientName")) {
            case "NotExist":
                note.setPatient(fakePatient);
                break;
            case "Null":
                break;
            default:
                note.setPatient(
                    patientTestInfoHolder.getPatientMap().get(entry.get("patientName"))
                );
                break;
        }

        switch (entry.get("doctorName")) {
            case "NotExist":
                note.setUser(fakeUser);
                break;
            case "Null":
                break;
            default:
                note.setUser(
                    userTestInfoHolder.getUserMap().get(entry.get("doctorName"))
                );
                break;
        }

        return note;
    }

    @Given("建立筆記")
    public void createDoctorNote(List<Note> notes) {
        notes.forEach(note -> {
            try {
                NoteVM noteVM = postNote(NoteTestMapper.INSTANCE.noteToNoteCreateVM(note));
                noteTestInfoHolder.assignNotes(
                    note.getPatient().getName(),
                    note.getUser() == null ? null : note.getUser().getFirstName(),
                    noteVM
                );
            } catch (Exception e) {
                e.printStackTrace();
                Assert.fail();
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
            null,
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

    @Then("建立筆記失敗")
    public void createDoctorNoteFail(List<Note> notes) throws Exception {
        for (Note note : notes) {
            postNoteAssumeFail(NoteTestMapper.INSTANCE.noteToNoteCreateVM(note));
        }
    }

    @Then("更改內容")
    public void patchingNotes(List<Note> inputNotes) throws Exception {
        String samePatient = null;

        noteTestInfoHolder.cleanNotes();

        for (Note inputNote : inputNotes) {
            if (StringUtils.isNotBlank(samePatient) &&
                !samePatient.equals(inputNote.getPatient().getName())
            ) {
                Assert.fail("This test case must be using the same patient.");
            } else {
                samePatient = inputNote.getPatient().getName();
            }

            // Query and validate data
            List<NoteVM> noteVMs = getNotes(
                inputNote.getType(),
                inputNote.getPatient() == null ? null : inputNote.getPatient().getId(),
                inputNote.getUser() == null ? null : inputNote.getUser().getId()
            );
            Assert.assertEquals(1, noteVMs.size());

            // Patching
            NoteCreateVM noteCreateVM = new NoteCreateVM();
            noteCreateVM.setContent(inputNote.getContent());
            NoteVM noteVM = patchNote(noteVMs.get(0).getId(), noteCreateVM);

            // Set response as state for later validation
            noteTestInfoHolder.assignNotes(
                inputNote.getPatient().getName(),
                inputNote.getUser() == null ? null : inputNote.getUser().getFirstName(),
                noteVM
            );
        }

        // Query all notes by patient
        List<NoteVM> expectedNotes = noteTestInfoHolder.getPatientNoteMap().get(samePatient);
        List<NoteVM> targetNotes = getNotes(
            null,
            patientTestInfoHolder.getPatientMap().get(samePatient).getId(),
            null
        );

        Assert.assertEquals(expectedNotes.size(), targetNotes.size());
        validateNotes(
            expectedNotes.stream().sorted(this::compareNotes).collect(Collectors.toList()),
            targetNotes.stream().sorted(this::compareNotes).collect(Collectors.toList())
        );

    }

    private NoteVM postNote(NoteCreateVM noteCreateVM) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post(noteApi)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(noteCreateVM));

        ResultActions resultActions = this.mvc.perform(requestBuilder);

        NoteVM noteVM = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsByteArray(), NoteVM.class);

        return noteVM;
    }

    private void postNoteAssumeFail(NoteCreateVM noteCreateVM) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = post(noteApi)
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(noteCreateVM));

        this.mvc.perform(requestBuilder)
            .andExpect(status().is4xxClientError());
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

    private NoteVM patchNote(Long id, NoteCreateVM noteCreateVM) throws Exception {
        MockHttpServletRequestBuilder requestBuilder = patch(String.format(notePatchApi, id))
            .contentType(APPLICATION_JSON)
            .content(objectMapper.writeValueAsBytes(noteCreateVM));

        ResultActions resultActions = this.mvc.perform(requestBuilder)
            .andExpect(status().is2xxSuccessful());

        NoteVM resNoteVM = objectMapper.readValue(resultActions.andReturn().getResponse().getContentAsByteArray(), NoteVM.class);

        return resNoteVM;
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

    private int compareNotes(
        NoteVM o1,
        NoteVM o2
    ) {
        return o1.getId() > o2.getId() ? 1 : -1;
    }
}
