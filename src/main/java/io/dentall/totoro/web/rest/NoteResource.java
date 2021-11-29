package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Note;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.User;
import io.dentall.totoro.domain.enumeration.NoteType;
import io.dentall.totoro.repository.NoteRepository;
import io.dentall.totoro.service.PatientService;
import io.dentall.totoro.service.UserService;
import io.dentall.totoro.service.mapper.NoteMapper;
import io.dentall.totoro.service.mapper.PatientDomainMapper;
import io.dentall.totoro.service.mapper.PatientMapper;
import io.dentall.totoro.service.mapper.UserDomainMapper;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.web.rest.vm.NoteCreateVM;
import io.dentall.totoro.web.rest.vm.NoteVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class NoteResource {

    private final Logger log = LoggerFactory.getLogger(NoteResource.class);

    private static final String ENTITY_NAME = "note";

    private final NoteRepository noteRepository;

    private final UserService userService;

    private final PatientService patientService;

    public NoteResource(
        NoteRepository noteRepository,
        UserService userService,
        PatientService patientService
    ) {
        this.noteRepository = noteRepository;
        this.userService = userService;
        this.patientService = patientService;
    }

    @PostMapping("/notes")
    @Timed
    @Transactional
    public NoteVM createNotes(
        @Valid @RequestBody NoteCreateVM noteCreateVM
    ) {
        if (!patientService.hasPatient(noteCreateVM.getPatientId())) {
            throw new BadRequestAlertException("Can not found patient by id", ENTITY_NAME, "notfound");
        }

        if (NoteType.DOCTOR.equals(noteCreateVM.getType()) &&
            noteCreateVM.getUserId() == null
        ) {
            throw new BadRequestAlertException("Require user id wile note type is treatment", ENTITY_NAME, "required");
        }

        if (noteCreateVM.getUserId() != null &&
            !userService.hasUser(noteCreateVM.getUserId())
        ) {
            throw new BadRequestAlertException("Can not found user by id", ENTITY_NAME, "notfound");
        }

        Note note = NoteMapper.INSTANCE.convertNoteCreateVmToDomain(noteCreateVM);

        Patient p = new Patient();
        p.setId(noteCreateVM.getPatientId());
        note.setPatient(p);

        if (noteCreateVM.getUserId() != null) {
            User u = new User();
            u.setId(noteCreateVM.getUserId());
            note.setUser(u);
        }

        return NoteMapper.INSTANCE.convertNoteDomainToVm(
            noteRepository.save(note)
        );
    }

    @PatchMapping("/notes/{id}")
    @Timed
    @Transactional
    public Note createNotes(
        @PathVariable(name = "id") Long id,
        @RequestBody String content
    ) {
        Note note = noteRepository.findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Can not found note by id", ENTITY_NAME, "notfound"));

        note.setContent(content);

        return note;
    }

    @GetMapping("/notes")
    @Timed
    @Transactional
    public ResponseEntity<List<NoteVM>> getNotes(
        Pageable page,
        @RequestParam(value = "id", required = false) Long id,
        @RequestParam(value ="patientId", required = false) Long patientId,
        @RequestParam(value ="userId", required = false) Long userId,
        @RequestParam(value ="type", required = false) NoteType type
    ) {
        Note note = new Note();
        note.setId(id);
        note.setType(type);

        if (patientId != null) {
            Patient patient = new Patient();
            patient.setId(patientId);
            note.setPatient(patient);
        }

        if (userId != null) {
            User user = new User();
            user.setId(userId);
            note.setUser(user);
        }

        ExampleMatcher matcher = ExampleMatcher.matching()
            .withIgnorePaths("createdDate", "lastModifiedDate", "user.createdDate", "user.lastModifiedDate", "user.activated", "patient.createdDate", "patient.lastModifiedDate");

        Page<Note> notes = noteRepository.findAll(
            Example.of(note, matcher),
            page
        );

        List<NoteVM> contents = notes.stream()
            .map(NoteMapper.INSTANCE::convertNoteDomainToVm)
            .collect(Collectors.toList());

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(notes, "/api/notes");
        return ResponseEntity.ok().headers(headers).body(contents);
    }

}
