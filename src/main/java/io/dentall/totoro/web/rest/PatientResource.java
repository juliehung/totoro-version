package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Patient;
import io.dentall.totoro.repository.PatientRepository;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
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

    public PatientResource(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
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
    public ResponseEntity<Patient> updatePatient(@Valid @RequestBody Patient patient) throws URISyntaxException {
        log.debug("REST request to update Patient : {}", patient);
        if (patient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Patient result = patientRepository.save(patient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, patient.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patients : get all the patients.
     *
     * @param pageable the pagination information
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many)
     * @return the ResponseEntity with status 200 (OK) and the list of patients in body
     */
    @GetMapping("/patients")
    @Timed
    public ResponseEntity<List<Patient>> getAllPatients(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Patients");
        Page<Patient> page;
        if (eagerload) {
            page = patientRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = patientRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, String.format("/api/patients?eagerload=%b", eagerload));
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
    public ResponseEntity<Patient> getPatient(@PathVariable Long id) {
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
     * @return the ResponseEntity with status 200 (OK) and the list of patients in body
     */
    @GetMapping("/patients/{id}/parents")
    @Timed
    public ResponseEntity<Collection<Patient>> getPatientParents(@PathVariable Long id) {
        log.debug("REST request to get parents of Patient : {}", id);

        return ResponseEntity.ok().body(getPatientParentChildCRUDResult(id).getParents());
    }

    /**
     * POST  /patients/{id}/parents : Create a new parent of patient.
     *
     * @param id the id of the patient
     * @param parent_id the id of the parent
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/patients/{id}/parents/{parent_id}")
    @Timed
    public ResponseEntity<Collection<Patient>> createPatientParent(@PathVariable Long id, @PathVariable Long parent_id) {
        log.debug("REST request to save Parent(id: {}) of Patient(id: {})", parent_id, id);
        if (id.equals(parent_id)) {
            throw new BadRequestAlertException("patient_id equals parent_id not allow", ENTITY_NAME, "patient_id_equal_parent_id");
        }

        Function<Patient, Patient> func = patient -> {
            Patient parent = patientRepository
                .findById(parent_id)
                .orElseThrow(() -> new BadRequestAlertException("Invalid parent_id", ENTITY_NAME, "parent_not_found"));
            patient.getParents().add(parent);

            return patientRepository.save(patient);
        };

        return ResponseEntity.ok().body(getPatientParentChildCRUDResult(id, func).getParents());
    }

    /**
     * DELETE  /patients/:id/parents/:parent_id : delete the "parent_id" parent of "id" patient.
     *
     * @param id the id of the patient
     * @param parent_id the id of the parent
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patients/{id}/parents/{parent_id}")
    @Timed
    public ResponseEntity<Collection<Patient>> deletePatientParent(@PathVariable Long id, @PathVariable Long parent_id) {
        log.debug("REST request to delete Parent(id: {}) of Patient(id: {})", parent_id, id);
        if (id.equals(parent_id)) {
            throw new BadRequestAlertException("patient_id equals parent_id not allow", ENTITY_NAME, "patient_id_equal_parent_id");
        }

        Function<Patient, Patient> func = patient -> {
            Patient parent = patientRepository
                .findById(parent_id)
                .orElseThrow(() -> new BadRequestAlertException("Invalid parent_id", ENTITY_NAME, "parent_not_found"));
            patient.getParents().remove(parent);

            return patientRepository.save(patient);
        };

        return ResponseEntity.ok().body(getPatientParentChildCRUDResult(id, func).getParents());
    }

    /**
     * GET  /patients/:id/children : get the children of "id" patient.
     *
     * @param id the id of the patient to retrieve children
     * @return the ResponseEntity with status 200 (OK) and the list of patients in body
     */
    @GetMapping("/patients/{id}/children")
    @Timed
    public ResponseEntity<Collection<Patient>> getPatientChildren(@PathVariable Long id) {
        log.debug("REST request to get children of Patient : {}", id);

        return ResponseEntity.ok().body(getPatientParentChildCRUDResult(id).getChildren());
    }

    /**
     * POST  /patients/{id}/children : Create a new child of patient.
     *
     * @param id the id of the patient
     * @param child_id the id of the child
     * @return the ResponseEntity with status 200 (OK)
     */
    @PostMapping("/patients/{id}/children/{child_id}")
    @Timed
    public ResponseEntity<Collection<Patient>> createPatientChild(@PathVariable Long id, @PathVariable Long child_id) {
        log.debug("REST request to save Child(id: {}) of Patient(id: {})", child_id, id);
        if (id.equals(child_id)) {
            throw new BadRequestAlertException("patient_id equals child_id not allow", ENTITY_NAME, "patient_id_equal_child_id");
        }

        Function<Patient, Patient> func = patient -> {
            Patient child = patientRepository
                .findById(child_id)
                .orElseThrow(() -> new BadRequestAlertException("Invalid child_id", ENTITY_NAME, "child_not_found"));
            child.getParents().add(patient);
            patient.getChildren().add(child);
            patientRepository.save(child);

            return patient;
        };

        return ResponseEntity.ok().body(getPatientParentChildCRUDResult(id, func).getChildren());
    }

    /**
     * DELETE  /patients/:id/children/:child_id : delete the "child_id" child of "id" patient.
     *
     * @param id the id of the patient
     * @param child_id the id of the child
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patients/{id}/children/{child_id}")
    @Timed
    public ResponseEntity<Collection<Patient>> deletePatientChild(@PathVariable Long id, @PathVariable Long child_id) {
        log.debug("REST request to delete Child(id: {}) of Patient(id: {})", child_id, id);
        if (id.equals(child_id)) {
            throw new BadRequestAlertException("patient_id equals child_id not allow", ENTITY_NAME, "patient_id_equal_child_id");
        }

        Function<Patient, Patient> func = patient -> {
            Patient child = patientRepository
                .findById(child_id)
                .orElseThrow(() -> new BadRequestAlertException("Invalid child_id", ENTITY_NAME, "child_not_found"));
            child.getParents().remove(patient);
            patient.getChildren().remove(child);
            patientRepository.save(child);

            return patient;
        };

        return ResponseEntity.ok().body(getPatientParentChildCRUDResult(id, func).getChildren());
    }

    private Patient getPatientParentChildCRUDResult(Long id) {
        return getPatientParentChildCRUDResult(id, patient -> patient);
    }

    private Patient getPatientParentChildCRUDResult(Long id, Function<Patient, Patient> func) {
        return patientRepository
            .findById(id)
            .map(func)
            .orElseThrow(() -> new BadRequestAlertException("Invalid patient_id", ENTITY_NAME, "patient_not_found"));
    }
}
