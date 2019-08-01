package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.service.TreatmentProcedureService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.service.dto.TreatmentProcedureCriteria;
import io.dentall.totoro.service.TreatmentProcedureQueryService;
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
 * REST controller for managing TreatmentProcedure.
 */
@RestController
@RequestMapping("/api")
public class TreatmentProcedureResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentProcedureResource.class);

    private static final String ENTITY_NAME = "treatmentProcedure";

    private final TreatmentProcedureService treatmentProcedureService;

    private final TreatmentProcedureQueryService treatmentProcedureQueryService;

    public TreatmentProcedureResource(TreatmentProcedureService treatmentProcedureService, TreatmentProcedureQueryService treatmentProcedureQueryService) {
        this.treatmentProcedureService = treatmentProcedureService;
        this.treatmentProcedureQueryService = treatmentProcedureQueryService;
    }

    /**
     * POST  /treatment-procedures : Create a new treatmentProcedure.
     *
     * @param treatmentProcedure the treatmentProcedure to create
     * @return the ResponseEntity with status 201 (Created) and with body the new treatmentProcedure, or with status 400 (Bad Request) if the treatmentProcedure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/treatment-procedures")
    @Timed
    public ResponseEntity<TreatmentProcedure> createTreatmentProcedure(@Valid @RequestBody TreatmentProcedure treatmentProcedure) throws URISyntaxException {
        log.debug("REST request to save TreatmentProcedure : {}", treatmentProcedure);
        if (treatmentProcedure.getId() != null) {
            throw new BadRequestAlertException("A new treatmentProcedure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TreatmentProcedure result = treatmentProcedureService.save(treatmentProcedure);
        return ResponseEntity.created(new URI("/api/treatment-procedures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /treatment-procedures : Updates an existing treatmentProcedure.
     *
     * @param treatmentProcedure the treatmentProcedure to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated treatmentProcedure,
     * or with status 400 (Bad Request) if the treatmentProcedure is not valid,
     * or with status 500 (Internal Server Error) if the treatmentProcedure couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/treatment-procedures")
    @Timed
    public ResponseEntity<TreatmentProcedure> updateTreatmentProcedure(@Valid @RequestBody TreatmentProcedure treatmentProcedure) throws URISyntaxException {
        log.debug("REST request to update TreatmentProcedure : {}", treatmentProcedure);
        if (treatmentProcedure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TreatmentProcedure result = treatmentProcedureService.update(treatmentProcedure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, treatmentProcedure.getId().toString()))
            .body(result);
    }

    /**
     * GET  /treatment-procedures : get all the treatmentProcedures.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of treatmentProcedures in body
     */
    @GetMapping("/treatment-procedures")
    @Timed
    public ResponseEntity<List<TreatmentProcedure>> getAllTreatmentProcedures(TreatmentProcedureCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TreatmentProcedures by criteria: {}", criteria);
        Page<TreatmentProcedure> page = treatmentProcedureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/treatment-procedures");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /treatment-procedures/count : count all the treatmentProcedures.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/treatment-procedures/count")
    @Timed
    public ResponseEntity<Long> countTreatmentProcedures(TreatmentProcedureCriteria criteria) {
        log.debug("REST request to count TreatmentProcedures by criteria: {}", criteria);
        return ResponseEntity.ok().body(treatmentProcedureQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /treatment-procedures/:id : get the "id" treatmentProcedure.
     *
     * @param id the id of the treatmentProcedure to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the treatmentProcedure, or with status 404 (Not Found)
     */
    @GetMapping("/treatment-procedures/{id}")
    @Timed
    public ResponseEntity<TreatmentProcedure> getTreatmentProcedure(@PathVariable Long id) {
        log.debug("REST request to get TreatmentProcedure : {}", id);
        Optional<TreatmentProcedure> treatmentProcedure = treatmentProcedureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(treatmentProcedure);
    }

    /**
     * DELETE  /treatment-procedures/:id : delete the "id" treatmentProcedure.
     *
     * @param id the id of the treatmentProcedure to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/treatment-procedures/{id}")
    @Timed
    public ResponseEntity<Void> deleteTreatmentProcedure(@PathVariable Long id) {
        log.debug("REST request to delete TreatmentProcedure : {}", id);
        treatmentProcedureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
