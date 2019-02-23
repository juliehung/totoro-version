package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Prescription;
import io.dentall.totoro.service.PrescriptionService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.service.dto.PrescriptionCriteria;
import io.dentall.totoro.service.PrescriptionQueryService;
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

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Prescription.
 */
@RestController
@RequestMapping("/api")
public class PrescriptionResource {

    private final Logger log = LoggerFactory.getLogger(PrescriptionResource.class);

    private static final String ENTITY_NAME = "prescription";

    private final PrescriptionService prescriptionService;

    private final PrescriptionQueryService prescriptionQueryService;

    public PrescriptionResource(PrescriptionService prescriptionService, PrescriptionQueryService prescriptionQueryService) {
        this.prescriptionService = prescriptionService;
        this.prescriptionQueryService = prescriptionQueryService;
    }

    /**
     * POST  /prescriptions : Create a new prescription.
     *
     * @param prescription the prescription to create
     * @return the ResponseEntity with status 201 (Created) and with body the new prescription, or with status 400 (Bad Request) if the prescription has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/prescriptions")
    @Timed
    public ResponseEntity<Prescription> createPrescription(@Valid @RequestBody Prescription prescription) throws URISyntaxException {
        log.debug("REST request to save Prescription : {}", prescription);
        if (prescription.getId() != null) {
            throw new BadRequestAlertException("A new prescription cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Prescription result = prescriptionService.save(prescription);
        return ResponseEntity.created(new URI("/api/prescriptions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prescriptions : Updates an existing prescription.
     *
     * @param prescription the prescription to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated prescription,
     * or with status 400 (Bad Request) if the prescription is not valid,
     * or with status 500 (Internal Server Error) if the prescription couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/prescriptions")
    @Timed
    public ResponseEntity<Prescription> updatePrescription(@Valid @RequestBody Prescription prescription) throws URISyntaxException {
        log.debug("REST request to update Prescription : {}", prescription);
        if (prescription.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Prescription result = prescriptionService.update(prescription);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, prescription.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prescriptions : get all the prescriptions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of prescriptions in body
     */
    @GetMapping("/prescriptions")
    @Timed
    public ResponseEntity<List<Prescription>> getAllPrescriptions(PrescriptionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Prescriptions by criteria: {}", criteria);
        Page<Prescription> page = prescriptionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prescriptions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /prescriptions/count : count all the prescriptions.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/prescriptions/count")
    @Timed
    public ResponseEntity<Long> countPrescriptions(PrescriptionCriteria criteria) {
        log.debug("REST request to count Prescriptions by criteria: {}", criteria);
        return ResponseEntity.ok().body(prescriptionQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /prescriptions/:id : get the "id" prescription.
     *
     * @param id the id of the prescription to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the prescription, or with status 404 (Not Found)
     */
    @GetMapping("/prescriptions/{id}")
    @Timed
    public ResponseEntity<Prescription> getPrescription(@PathVariable Long id) {
        log.debug("REST request to get Prescription : {}", id);
        Optional<Prescription> prescription = prescriptionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(prescription);
    }

    /**
     * DELETE  /prescriptions/:id : delete the "id" prescription.
     *
     * @param id the id of the prescription to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/prescriptions/{id}")
    @Timed
    public ResponseEntity<Void> deletePrescription(@PathVariable Long id) {
        log.debug("REST request to delete Prescription : {}", id);
        prescriptionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
