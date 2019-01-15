package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.PatientIdentity;
import io.dentall.totoro.repository.PatientIdentityRepository;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PatientIdentity.
 */
@RestController
@RequestMapping("/api")
public class PatientIdentityResource {

    private final Logger log = LoggerFactory.getLogger(PatientIdentityResource.class);

    private static final String ENTITY_NAME = "patientIdentity";

    private final PatientIdentityRepository patientIdentityRepository;

    public PatientIdentityResource(PatientIdentityRepository patientIdentityRepository) {
        this.patientIdentityRepository = patientIdentityRepository;
    }

    /**
     * POST  /patient-identities : Create a new patientIdentity.
     *
     * @param patientIdentity the patientIdentity to create
     * @return the ResponseEntity with status 201 (Created) and with body the new patientIdentity, or with status 400 (Bad Request) if the patientIdentity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/patient-identities")
    @Timed
    public ResponseEntity<PatientIdentity> createPatientIdentity(@Valid @RequestBody PatientIdentity patientIdentity) throws URISyntaxException {
        log.debug("REST request to save PatientIdentity : {}", patientIdentity);
        if (patientIdentity.getId() != null) {
            throw new BadRequestAlertException("A new patientIdentity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientIdentity result = patientIdentityRepository.save(patientIdentity);
        return ResponseEntity.created(new URI("/api/patient-identities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /patient-identities : Updates an existing patientIdentity.
     *
     * @param patientIdentity the patientIdentity to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated patientIdentity,
     * or with status 400 (Bad Request) if the patientIdentity is not valid,
     * or with status 500 (Internal Server Error) if the patientIdentity couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/patient-identities")
    @Timed
    public ResponseEntity<PatientIdentity> updatePatientIdentity(@Valid @RequestBody PatientIdentity patientIdentity) throws URISyntaxException {
        log.debug("REST request to update PatientIdentity : {}", patientIdentity);
        if (patientIdentity.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PatientIdentity result = patientIdentityRepository.save(patientIdentity);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, patientIdentity.getId().toString()))
            .body(result);
    }

    /**
     * GET  /patient-identities : get all the patientIdentities.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of patientIdentities in body
     */
    @GetMapping("/patient-identities")
    @Timed
    public List<PatientIdentity> getAllPatientIdentities() {
        log.debug("REST request to get all PatientIdentities");
        return patientIdentityRepository.findAll();
    }

    /**
     * GET  /patient-identities/:id : get the "id" patientIdentity.
     *
     * @param id the id of the patientIdentity to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the patientIdentity, or with status 404 (Not Found)
     */
    @GetMapping("/patient-identities/{id}")
    @Timed
    public ResponseEntity<PatientIdentity> getPatientIdentity(@PathVariable Long id) {
        log.debug("REST request to get PatientIdentity : {}", id);
        Optional<PatientIdentity> patientIdentity = patientIdentityRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(patientIdentity);
    }

    /**
     * DELETE  /patient-identities/:id : delete the "id" patientIdentity.
     *
     * @param id the id of the patientIdentity to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/patient-identities/{id}")
    @Timed
    public ResponseEntity<Void> deletePatientIdentity(@PathVariable Long id) {
        log.debug("REST request to delete PatientIdentity : {}", id);

        patientIdentityRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
