package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.business.vm.nhi.NhiRuleCheckResultVM;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.Tag;
import io.dentall.totoro.domain.enumeration.PatientRelationshipType;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.TagRepository;
import io.dentall.totoro.service.AvatarService;
import io.dentall.totoro.service.BroadcastService;
import io.dentall.totoro.service.PatientService;
import io.dentall.totoro.service.dto.CustomGroup;
import io.dentall.totoro.service.dto.PatientCriteria;
import io.dentall.totoro.service.dto.PatientDTO;
import io.dentall.totoro.service.dto.table.PatientTable;
import io.dentall.totoro.service.mapper.PatientMapper;
import io.dentall.totoro.service.util.LogUtil;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.errors.InternalServerErrorException;
import io.dentall.totoro.web.rest.util.AvatarUtil;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.web.rest.vm.AvatarVM;
import io.dentall.totoro.web.rest.vm.PatientFirstLatestVisitDateVM;
import io.dentall.totoro.web.rest.vm.PatientNationalIdValidationVM;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * REST controller for managing Patient.
 */
@RestController
@RequestMapping("/api")
public class PatientResource {

    private final Logger log = LoggerFactory.getLogger(PatientResource.class);

    private static final String ENTITY_NAME = "patient";

    private final PatientRepository patientRepository;

    private final TagRepository tagRepository;

    private final AvatarService avatarService;

    private final PatientService patientService;

    private final BroadcastService broadcastService;

    public PatientResource(
        PatientRepository patientRepository,
        TagRepository tagRepository,
        AvatarService avatarService,
        PatientService patientService,
        BroadcastService broadcastService
    ) {
        this.patientRepository = patientRepository;
        this.tagRepository = tagRepository;
        this.avatarService = avatarService;
        this.patientService = patientService;
        this.broadcastService = broadcastService;
    }

    /**
     * POST  /patients : Create a new patient.
     *
     * @param patient the patient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patient, or with status 400 (Bad Request) if the patient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patients")
    @Timed
    public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) throws URISyntaxException {
        log.debug("REST request to save Patient : {}", patient);
        if (patient.getId() != null) {
            throw new BadRequestAlertException("A new patient cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Patient result = patientService.save(patient);
        broadcastService.broadcastDomainId(result.getId(), Patient.class);

        try {
            log.info(LogUtil.RECORD_SNAPSHOT_MESSAGE_STRING_FORMAT, "Patient", result.getId(), result);
        } catch (Exception e) {
            log.error("Patient snap fail: " + e.getMessage());
        }

        return ResponseEntity.created(new URI("/api/patients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patients : Updates an existing patient.
     *
     * @param patient the patient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patient,
     * or with status 400 (Bad Request) if the patient is not valid,
     * or with status 500 (Internal Server Error) if the patient couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patients")
    @Timed
    public ResponseEntity<Patient> updatePatient(@Validated(CustomGroup.class) @RequestBody PatientDTO patient) throws URISyntaxException {
        log.debug("REST request to update Patient : {}", patient);
        if (patient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Patient result = patientService.update(patient);
        broadcastService.broadcastDomainId(result.getId(), Patient.class);

        try {
            log.info(LogUtil.RECORD_SNAPSHOT_MESSAGE_STRING_FORMAT, "Patient", result.getId(), result);
        } catch (Exception e) {
            log.error("Patient snap fail: " + e.getMessage());
        }

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patients : get all the patients.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of patients in body
     */
    @GetMapping("/patients")
    @Timed
    public ResponseEntity<List<Patient>> getAllPatients(Pageable pageable, PatientCriteria criteria) {
        log.debug("REST request to get a page of Patients");
        Page<Patient> page = patientService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/patients");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /patients/:id : get the "id" patient.
     *
     * @param id the id of the patient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patient, or with status 404 (Not Found)
     */
    @GetMapping("/patients/{id}")
    @Timed
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        log.debug("REST request to get Patient : {}", id);
        Optional<Patient> patient = patientRepository.findById(id);
        if (patient.isPresent()) {
            patientService.getFirstLastDoctor(patient.get());
        }

        return ResponseUtil.wrapOrNotFound(patient);
    }

    @GetMapping("/patients/{id}/visitDate")
    @Timed
    public ResponseEntity<PatientFirstLatestVisitDateVM> getPatientFirstLatestVisitDate(@PathVariable Long id) {
        log.debug("REST request to get Patient : {}", id);
        return ResponseEntity.ok(patientService.findPatientFirstLatestVisitDate(id));
    }

    /**
     * DELETE  /patients/:id : delete the "id" patient.
     *
     * @param id the id of the patient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patients/{id}")
    @Timed
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        log.debug("REST request to delete Patient : {}", id);

        patientRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET  /patients/:id/spouse1S : get the spouse1S of "id" patient.
     *
     * @param id the id of the patient to retrieve spouse1S
     * @return the ResponseEntity with status 200 (OK) and the list of spouse1S in body
     */
    @GetMapping("/patients/{id}/{relationshipType}")
    @Timed
    public ResponseEntity<HashSet<Patient>> getPatientSpouse1S(
        @PathVariable Long id,
        @PathVariable("relationshipType") PatientRelationshipType relationshipType) {
        log.debug("REST request to get spouse1S of Patient : {}", id);
        try {
            return ResponseEntity.ok().body(patientService.getPatientRelationship(id, Patient.class.getMethod(relationshipType.getGetterName())));
        } catch (NoSuchMethodException e) {
            log.error(e.toString());
            return ResponseEntity.ok().body(new HashSet<>());
        }
    }

    /**
     * POST  /patients/:id/spouse1S/:spouse1_id : Create a new spouse1 of patient.
     *
     * @param mainId the id of the patient
     * @param subId the id of the spouse1
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/patients/{mainId}/{relationshipType}/{subId}")
    @Timed
    public ResponseEntity<Collection<Patient>> createPatientSpouse1(@PathVariable Long mainId, @PathVariable("relationshipType") PatientRelationshipType relationshipType, @PathVariable Long subId) {
        log.debug("REST request to save {} (id: {}) of Patient(id: {})", relationshipType, mainId, subId);
        if (mainId.equals(subId)) {
            throw new BadRequestAlertException("patient_id equals spouse1_id not allow", ENTITY_NAME, "patient_id_equal_spouse1_id");
        }

        // 為了配合不異動前端，修改主從邏輯，因為 spouse1 是配偶關係的關係人，不是主要人，換句話說 mainId 應為 spouse2。
        if (
            relationshipType.equals(PatientRelationshipType.SPOUSE1S) ||
            relationshipType.equals(PatientRelationshipType.SPOUSE2S)
        ) {
            return ResponseEntity.ok().body(patientService.createPatientRelationship(subId, mainId, relationshipType.getMainSetterName(), relationshipType.getSubSetterName()));
        } else {
            return ResponseEntity.ok().body(patientService.createPatientRelationship(mainId, subId, relationshipType.getMainSetterName(), relationshipType.getSubSetterName()));
        }
    }

    /**
     * DELETE  /patients/:id/spouse1S/:spouse1_id : delete the "spouse1_id" spouse1 of "id" patient.
     *
     * @param mainId the id of the patient
     * @param subId the id of the spouse1
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patients/{mainId}/{relationshipType}/{subId}")
    @Timed
    public ResponseEntity<Collection<Patient>> deletePatientSpouse(@PathVariable Long mainId, @PathVariable("relationshipType") PatientRelationshipType relationshipType, @PathVariable Long subId) {
        log.debug("REST request to delete {} (id: {}) of Patient(id: {})", relationshipType, mainId, subId);
        if (mainId.equals(subId)) {
            throw new BadRequestAlertException("patient_id equals spouse1_id not allow", ENTITY_NAME, "patient_id_equal_spouse1_id");
        }

        // 為了配合不異動前端，修改主從邏輯，因為 spouse1 是配偶關係的關係人，不是主要人，換句話說 mainId 應為 spouse2。
        if (
            relationshipType.equals(PatientRelationshipType.SPOUSE1S) ||
                relationshipType.equals(PatientRelationshipType.SPOUSE2S)
        ) {
            return ResponseEntity.ok().body(patientService.deletePatientRelationship(subId, mainId, relationshipType.getMainSetterName(), relationshipType.getSubSetterName()));
        } else {
            return ResponseEntity.ok().body(patientService.deletePatientRelationship(mainId, subId, relationshipType.getMainSetterName(), relationshipType.getSubSetterName()));
        }
    }

    /**
     * GET  /patients/:id/tags : get the tags of "id" patient.
     *
     * @param id the id of the patient to retrieve tags
     * @return the ResponseEntity with status 200 (OK) and the list of tags in body
     */
    @GetMapping("/patients/{id}/tags")
    @Timed
    public ResponseEntity<Collection<Tag>> getPatientTags(@PathVariable Long id) {
        log.debug("REST request to get tags of Patient : {}", id);

        return ResponseEntity.ok().body(getPatient(id).getTags());
    }

    /**
     * POST  /patients/:id/tags/:tag_id : Create a new tag of patient.
     *
     * @param id the id of the patient
     * @param tag_id the id of the tag
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/patients/{id}/tags/{tag_id}")
    @Timed
    public ResponseEntity<Collection<Tag>> createPatientTag(@PathVariable Long id, @PathVariable Long tag_id) {
        log.debug("REST request to save Tag(id: {}) of Patient(id: {})", tag_id, id);

        return ResponseEntity.ok().body(getPatient(id, getPatientByTagConsumer(tag_id, (patient, tag) -> patient.getTags().add(tag))).getTags());
    }

    /**
     * DELETE  /patients/:id/tags/:tag_id : delete the "tag_id" tag of "id" patient.
     *
     * @param id the id of the patient
     * @param tag_id the id of the tag
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patients/{id}/tags/{tag_id}")
    @Timed
    public ResponseEntity<Collection<Tag>> deletePatientTag(@PathVariable Long id, @PathVariable Long tag_id) {
        log.debug("REST request to delete Tag(id: {}) of Patient(id: {})", tag_id, id);

        return ResponseEntity.ok().body(getPatient(id, getPatientByTagConsumer(tag_id, (patient, tag) -> patient.getTags().remove(tag))).getTags());
    }

    /**
     * POST  /patients/:id/tags : Create tags of "id" patient.
     *
     * @param id the id of the patient
     * @param tags the set of the tags
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/patients/{id}/tags")
    @Timed
    public ResponseEntity<Collection<Tag>> createPatientTags(@PathVariable Long id, @RequestBody Collection<Tag> tags) {
        log.debug("REST request to save Tags({}) of Patient(id: {})", tags, id);

        Function<Patient, Patient> func = patient -> {
            patient.getTags().clear();
            tags.forEach(tag -> tagRepository.findById(tag.getId()).ifPresent(patient.getTags()::add));
            return patientRepository.save(patient);
        };
        return ResponseEntity.ok().body(getPatient(id, func).getTags());
    }

    /**
     * DELETE  /patients/:id/tags : delete the tags of "id" patient.
     *
     * @param id the id of the patient
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patients/{id}/tags")
    @Timed
    public ResponseEntity<Collection<Tag>> deletePatientTag(@PathVariable Long id) {
        log.debug("REST request to delete tags of Patient(id: {})", id);

        Function<Patient, Patient> func = patient -> {
            patient.getTags().clear();
            return patientRepository.save(patient);
        };
        return ResponseEntity.ok().body(getPatient(id, func).getTags());
    }

    /**
     * POST  /patients/:id/avatar : upload the patient's avatar.
     *
     * @param id the id of the patient
     * @param file the avatar to create
     * @return the patient
     * @throws InternalServerErrorException 500 (InternalServerError) if patient could not store avatar or not found
     */
    @PostMapping("/patients/{id}/avatar")
    @Timed
    public Patient uploadAvatar(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            return avatarService.storePatientAvatar(id, file).orElseThrow(() -> new InternalServerErrorException("Patient could not found"));
        } catch (IOException e) {
            throw new InternalServerErrorException("Patient could not store avatar");
        }
    }

    /**
     * GET /patients/:id/avatar : get the patient's avatar.
     *
     * @return AvatarVM object body
     */
    @GetMapping("/patients/{id}/avatar")
    @Timed
    public ResponseEntity<AvatarVM> getAvatar(@PathVariable Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new InternalServerErrorException("Patient could not found"));
        return AvatarUtil.responseAvatarVM(patient);
    }

    private Patient getPatient(Long id) {
        return getPatient(id, patient -> patient);
    }

    private Patient getPatient(Long id, Function<Patient, Patient> func) {
        return patientRepository
            .findById(id)
            .map(func)
            .orElseThrow(() -> new BadRequestAlertException("Invalid patient_id", ENTITY_NAME, "patient_not_found"));
    }

    private <T> T getPatientCRUDTarget(Long id, JpaRepository<T, Long> repo) {
        return repo
            .findById(id)
            .orElseThrow(() -> new BadRequestAlertException("Invalid id", ENTITY_NAME, "target_not_found"));
    }

    private <T> Patient getPatientCRUDResult(Patient patient, T target, BiFunction<Patient, T, Patient> crud) {
        return patientRepository.save(crud.apply(patient, target));
    }

    private Function<Patient, Patient> getPatientByTagConsumer(Long id, BiConsumer<Patient, Tag> crud) {
        return patient -> {
            tagRepository.findById(id).ifPresent(tag -> crud.accept(patient, tag));
            return patientRepository.save(patient);
        };
    }

    private void checkKinship(Long id, Long targetId) {
        if (patientRepository.findByParentsId(id, PatientKinship.class)
            .stream()
            .anyMatch(patientKinship -> patientKinship.getId().equals(targetId))
        ) {
            throw new BadRequestAlertException("parent relation cycle", ENTITY_NAME, "parent_relation_cycle");
        }

        if (patientRepository.findBySpouse1SId(id, PatientKinship.class)
            .stream()
            .anyMatch(patientKinship -> patientKinship.getId().equals(targetId))
        ) {
            throw new BadRequestAlertException("spouse relation cycle", ENTITY_NAME, "spouse_relation_cycle");
        }
    }

    public interface PatientKinship {

        Long getId();
    }

    @GetMapping("/patients/{id}/without/relationship")
    @Timed
    public ResponseEntity<Patient> getPatientByIdWithoutRelationship(@PathVariable Long id) {
        log.debug("REST request to get Patient : {}", id);
        Optional<PatientTable> patient = patientRepository.findPatientById(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(patient.isPresent()
            ? PatientMapper.patientTableToPatient(patient.get())
            : null));
    }

    @GetMapping("/patients/nationalId/validation")
    @Timed
    public ResponseEntity<PatientNationalIdValidationVM> patientNationalIdValidation(@RequestParam String nationalId) {
        log.debug("REST request to get Patient : {}", nationalId);
        List<PatientNationalIdValidationVM> result = patientRepository.findByNationalId(nationalId);
        if (result.size() > 0) {
            return ResponseEntity.ok(result.get(0));
        } else {
            return ResponseEntity.ok(new PatientNationalIdValidationVM(null));
        }

    }

    @GetMapping("/patients/nhi/{code}/status")
    @Timed
    public ResponseEntity<NhiRuleCheckResultVM> patientNhiStatus(
        @PathVariable("code") String code,
        @RequestParam("patientId") Long patientId
    ) {
        log.debug("REST request to get Patient's nhi status by patient id : {}", patientId);
        if (!code.equals("81") &&
            !code.equals("91004C")
        ) {
            throw new BadRequestAlertException("Only support nhi code 81, 91004C", ENTITY_NAME, "not.supported.nhi.code");
        }
        return ResponseEntity.ok(patientService.getPatientNhiStatus(code, patientId));
    }

}
