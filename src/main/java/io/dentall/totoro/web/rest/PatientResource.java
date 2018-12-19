package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.domain.Tag;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.repository.TagRepository;
import io.dentall.totoro.service.PatientService;
import io.dentall.totoro.service.dto.PatientDTO;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.*;
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

    private final PatientService patientService;

    public PatientResource(PatientRepository patientRepository, TagRepository tagRepository, PatientService patientService) {
        this.patientRepository = patientRepository;
        this.tagRepository = tagRepository;
        this.patientService = patientService;
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

        patientService.setTagsByQuestionnaire(patient.getTags(), patient.getQuestionnaire());
        Patient result = patientRepository.save(patient);
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
    public ResponseEntity<Patient> updatePatient(@Validated(PatientDTO.NullGroup.class) @RequestBody PatientDTO patient) throws URISyntaxException {
        log.debug("REST request to update Patient : {}", patient);
        if (patient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        return ResponseUtil.wrapOrNotFound(
            patientService.updatePatient(patient),
            HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, patient.getId().toString())
        );
    }

    /**
     * GET  /patients : get all the patients.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @param search patients who contain search keyword to retrieve
     * @param isDeleted patients were deleted
     * @return the ResponseEntity with status 200 (OK) and the list of patients in body
     */
    @GetMapping("/patients")
    @Timed
    public ResponseEntity<List<Patient>> getAllPatients(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload, @RequestParam(required = false) String search, @RequestParam(required = false) Boolean isDeleted) {
        log.debug("REST request to get a page of Patients");
        Page<Patient> page;
        if (eagerload) {
            page = (search != null || isDeleted != null) ? patientRepository.findByQueryWithEagerRelationships(search, isDeleted, pageable) : patientRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = (search != null || isDeleted != null) ? patientRepository.findByQuery(search, isDeleted, pageable) : patientRepository.findAll(pageable);
        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/patients?eagerload=%b&search=%s&isDeleted=%b", eagerload, search, isDeleted));
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
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
        Optional<Patient> patient = patientRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(patient);
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
     * GET  /patients/:id/parents : get the parents of "id" patient.
     *
     * @param id the id of the patient to retrieve parents
     * @return the ResponseEntity with status 200 (OK) and the list of parents in body
     */
    @GetMapping("/patients/{id}/parents")
    @Timed
    public ResponseEntity<Collection<Patient>> getPatientParents(@PathVariable Long id) {
        log.debug("REST request to get parents of Patient : {}", id);

        return ResponseEntity.ok().body(getPatient(id).getParents());
    }

    /**
     * POST  /patients/:id/parents/:parent_id : Create a new parent of patient.
     *
     * @param id the id of the patient
     * @param parent_id the id of the parent
     * @return the ResponseEntity with status 200 (OK) and the list of parents in body
     */
    @PostMapping("/patients/{id}/parents/{parent_id}")
    @Timed
    public ResponseEntity<Collection<Patient>> createPatientParent(@PathVariable Long id, @PathVariable Long parent_id) {
        log.debug("REST request to save Parent(id: {}) of Patient(id: {})", parent_id, id);
        if (id.equals(parent_id)) {
            throw new BadRequestAlertException("patient_id equals parent_id not allow", ENTITY_NAME, "patient_id_equal_parent_id");
        }

        Function<Patient, Patient> func = patient ->
            getPatientCRUDResult(patient, getPatientCRUDTarget(parent_id, patientRepository), Patient::addParent);
        return ResponseEntity.ok().body(getPatient(id, func).getParents());
    }

    /**
     * DELETE  /patients/:id/parents/:parent_id : delete the "parent_id" parent of "id" patient.
     *
     * @param id the id of the patient
     * @param parent_id the id of the parent
     * @return the ResponseEntity with status 200 (OK) and the list of parents in body
     */
    @DeleteMapping("/patients/{id}/parents/{parent_id}")
    @Timed
    public ResponseEntity<Collection<Patient>> deletePatientParent(@PathVariable Long id, @PathVariable Long parent_id) {
        log.debug("REST request to delete Parent(id: {}) of Patient(id: {})", parent_id, id);
        if (id.equals(parent_id)) {
            throw new BadRequestAlertException("patient_id equals parent_id not allow", ENTITY_NAME, "patient_id_equal_parent_id");
        }

        Function<Patient, Patient> func = patient ->
            getPatientCRUDResult(patient, getPatientCRUDTarget(parent_id, patientRepository), Patient::removeParent);
        return ResponseEntity.ok().body(getPatient(id, func).getParents());
    }

    /**
     * GET  /patients/:id/children : get the children of "id" patient.
     *
     * @param id the id of the patient to retrieve children
     * @return the ResponseEntity with status 200 (OK) and the list of children in body
     */
    @GetMapping("/patients/{id}/children")
    @Timed
    public ResponseEntity<Collection<Patient>> getPatientChildren(@PathVariable Long id) {
        log.debug("REST request to get children of Patient : {}", id);

        return ResponseEntity.ok().body(getPatient(id).getChildren());
    }

    /**
     * POST  /patients/:id/children/:child_id : Create a new child of patient.
     *
     * @param id the id of the patient
     * @param child_id the id of the child
     * @return the ResponseEntity with status 200 (OK) and the list of children in body
     */
    @PostMapping("/patients/{id}/children/{child_id}")
    @Timed
    public ResponseEntity<Collection<Patient>> createPatientChild(@PathVariable Long id, @PathVariable Long child_id) {
        log.debug("REST request to save Child(id: {}) of Patient(id: {})", child_id, id);
        if (id.equals(child_id)) {
            throw new BadRequestAlertException("patient_id equals child_id not allow", ENTITY_NAME, "patient_id_equal_child_id");
        }

        Function<Patient, Patient> func = patient ->
            getPatientCRUDResult(patient, getPatientCRUDTarget(child_id, patientRepository), Patient::addChild);
        return ResponseEntity.ok().body(getPatient(id, func).getChildren());
    }

    /**
     * DELETE  /patients/:id/children/:child_id : delete the "child_id" child of "id" patient.
     *
     * @param id the id of the patient
     * @param child_id the id of the child
     * @return the ResponseEntity with status 200 (OK) and the list of children in body
     */
    @DeleteMapping("/patients/{id}/children/{child_id}")
    @Timed
    public ResponseEntity<Collection<Patient>> deletePatientChild(@PathVariable Long id, @PathVariable Long child_id) {
        log.debug("REST request to delete Child(id: {}) of Patient(id: {})", child_id, id);
        if (id.equals(child_id)) {
            throw new BadRequestAlertException("patient_id equals child_id not allow", ENTITY_NAME, "patient_id_equal_child_id");
        }

        Function<Patient, Patient> func = patient ->
            getPatientCRUDResult(patient, getPatientCRUDTarget(child_id, patientRepository), Patient::removeChild);
        return ResponseEntity.ok().body(getPatient(id, func).getChildren());
    }

    /**
     * GET  /patients/:id/spouse1S : get the spouse1S of "id" patient.
     *
     * @param id the id of the patient to retrieve spouse1S
     * @return the ResponseEntity with status 200 (OK) and the list of spouse1S in body
     */
    @GetMapping("/patients/{id}/spouse1S")
    @Timed
    public ResponseEntity<Collection<Patient>> getPatientSpouse1S(@PathVariable Long id) {
        log.debug("REST request to get spouse1S of Patient : {}", id);

        return ResponseEntity.ok().body(getPatient(id).getSpouse1S());
    }

    /**
     * POST  /patients/:id/spouse1S/:spouse1_id : Create a new spouse1 of patient.
     *
     * @param id the id of the patient
     * @param spouse1_id the id of the spouse1
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/patients/{id}/spouse1S/{spouse1_id}")
    @Timed
    public ResponseEntity<Collection<Patient>> createPatientSpouse1(@PathVariable Long id, @PathVariable Long spouse1_id) {
        log.debug("REST request to save Spouse1(id: {}) of Patient(id: {})", spouse1_id, id);
        if (id.equals(spouse1_id)) {
            throw new BadRequestAlertException("patient_id equals spouse1_id not allow", ENTITY_NAME, "patient_id_equal_spouse1_id");
        }

        Function<Patient, Patient> func = patient ->
            getPatientCRUDResult(patient, getPatientCRUDTarget(spouse1_id, patientRepository), Patient::addSpouse1);
        return ResponseEntity.ok().body(getPatient(id, func).getSpouse1S());
    }

    /**
     * DELETE  /patients/:id/spouse1S/:spouse1_id : delete the "spouse1_id" spouse1 of "id" patient.
     *
     * @param id the id of the patient
     * @param spouse1_id the id of the spouse1
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patients/{id}/spouse1S/{spouse1_id}")
    @Timed
    public ResponseEntity<Collection<Patient>> deletePatientSpouse(@PathVariable Long id, @PathVariable Long spouse1_id) {
        log.debug("REST request to delete Spouse1(id: {}) of Patient(id: {})", spouse1_id, id);
        if (id.equals(spouse1_id)) {
            throw new BadRequestAlertException("patient_id equals spouse1_id not allow", ENTITY_NAME, "patient_id_equal_spouse1_id");
        }

        Function<Patient, Patient> func = patient ->
            getPatientCRUDResult(patient, getPatientCRUDTarget(spouse1_id, patientRepository), Patient::removeSpouse1);
        return ResponseEntity.ok().body(getPatient(id, func).getSpouse1S());
    }

    /**
     * GET  /patients/:id/spouse2S : get the spouse2S of "id" patient.
     *
     * @param id the id of the patient to retrieve spouse2S
     * @return the ResponseEntity with status 200 (OK) and the list of spouse2S in body
     */
    @GetMapping("/patients/{id}/spouse2S")
    @Timed
    public ResponseEntity<Collection<Patient>> getPatientSpouse2S(@PathVariable Long id) {
        log.debug("REST request to get spouse2S of Patient : {}", id);

        return ResponseEntity.ok().body(getPatient(id).getSpouse2S());
    }

    /**
     * POST  /patients/:id/spouse2S/:spouse2_id : Create a new spouse2 of patient.
     *
     * @param id the id of the patient
     * @param spouse2_id the id of the spouse2
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/patients/{id}/spouse2S/{spouse2_id}")
    @Timed
    public ResponseEntity<Collection<Patient>> createPatientSpouse2(@PathVariable Long id, @PathVariable Long spouse2_id) {
        log.debug("REST request to save Spouse2(id: {}) of Patient(id: {})", spouse2_id, id);
        if (id.equals(spouse2_id)) {
            throw new BadRequestAlertException("patient_id equals spouse2_id not allow", ENTITY_NAME, "patient_id_equal_spouse2_id");
        }

        Function<Patient, Patient> func = patient ->
            getPatientCRUDResult(patient, getPatientCRUDTarget(spouse2_id, patientRepository), Patient::addSpouse2);
        return ResponseEntity.ok().body(getPatient(id, func).getSpouse2S());
    }

    /**
     * DELETE  /patients/:id/spouse2S/:spouse2_id : delete the "spouse2_id" spouse of "id" patient.
     *
     * @param id the id of the patient
     * @param spouse2_id the id of the spouse2
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patients/{id}/spouse2S/{spouse2_id}")
    @Timed
    public ResponseEntity<Collection<Patient>> deletePatientSpouse2(@PathVariable Long id, @PathVariable Long spouse2_id) {
        log.debug("REST request to delete Spouse2(id: {}) of Patient(id: {})", spouse2_id, id);
        if (id.equals(spouse2_id)) {
            throw new BadRequestAlertException("patient_id equals spouse2_id not allow", ENTITY_NAME, "patient_id_equal_spouse2_id");
        }

        Function<Patient, Patient> func = patient ->
            getPatientCRUDResult(patient, getPatientCRUDTarget(spouse2_id, patientRepository), Patient::removeSpouse2);
        return ResponseEntity.ok().body(getPatient(id, func).getSpouse2S());
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

        Function<Patient, Patient> func = patient ->
            getPatientCRUDResult(patient, getPatientCRUDTarget(tag_id, tagRepository), Patient::addTag);
        return ResponseEntity.ok().body(getPatient(id, func).getTags());
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

        Function<Patient, Patient> func = patient ->
            getPatientCRUDResult(patient, getPatientCRUDTarget(tag_id, tagRepository), Patient::removeTag);
        return ResponseEntity.ok().body(getPatient(id, func).getTags());
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
}
