package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Treatment;
import io.dentall.totoro.service.TreatmentPlanService;
import io.dentall.totoro.service.TreatmentService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.service.dto.TreatmentCriteria;
import io.dentall.totoro.service.TreatmentQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Treatment.
 */
@RestController
@RequestMapping("/api")
public class TreatmentResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentResource.class);

    private static final String ENTITY_NAME = "treatment";

    private final TreatmentService treatmentService;

    private final TreatmentQueryService treatmentQueryService;

    private final TreatmentPlanService treatmentPlanService;

    public TreatmentResource(TreatmentService treatmentService, TreatmentQueryService treatmentQueryService, TreatmentPlanService treatmentPlanService) {
        this.treatmentService = treatmentService;
        this.treatmentQueryService = treatmentQueryService;
        this.treatmentPlanService = treatmentPlanService;
    }

    /**
     * POST  /treatments : Create a new treatment.
     *
     * @param treatment the treatment to create
     * @return the ResponseEntity with status 201 (Created) and with body the new treatment, or with status 400 (Bad Request) if the treatment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/treatments")
    @Timed
    public ResponseEntity<Treatment> createTreatment(@Valid @RequestBody Treatment treatment) throws URISyntaxException {
        log.debug("REST request to save Treatment : {}", treatment);
        if (treatment.getId() != null) {
            throw new BadRequestAlertException("A new treatment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Treatment result = treatmentService.save(treatment);
        return ResponseEntity.created(new URI("/api/treatments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /treatments : Updates an existing treatment.
     *
     * @param treatment the treatment to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated treatment,
     * or with status 400 (Bad Request) if the treatment is not valid,
     * or with status 500 (Internal Server Error) if the treatment couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/treatments")
    @Timed
    public ResponseEntity<Treatment> updateTreatment(@Valid @RequestBody Treatment treatment) throws URISyntaxException {
        log.debug("REST request to update Treatment : {}", treatment);
        if (treatment.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        treatment.getTreatmentPlans().forEach(treatmentPlanService::update);
        Treatment result = treatmentService.save(treatment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, treatment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /treatments : get all the treatments.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of treatments in body
     */
    @GetMapping("/treatments")
    @Timed
    public ResponseEntity<List<Treatment>> getAllTreatments(TreatmentCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Treatments by criteria: {}", criteria);
        Page<Treatment> page = treatmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/treatments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /treatments/count : count all the treatments.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/treatments/count")
    @Timed
    public ResponseEntity<Long> countTreatments(TreatmentCriteria criteria) {
        log.debug("REST request to count Treatments by criteria: {}", criteria);
        return ResponseEntity.ok().body(treatmentQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /treatments/:id : get the "id" treatment.
     *
     * @param id the id of the treatment to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the treatment, or with status 404 (Not Found)
     */
    @GetMapping("/treatments/{id}")
    @Timed
    public ResponseEntity<Treatment> getTreatment(@PathVariable Long id) {
        log.debug("REST request to get Treatment : {}", id);
        Optional<Treatment> treatment = treatmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(treatment);
    }

    /**
     * DELETE  /treatments/:id : delete the "id" treatment.
     *
     * @param id the id of the treatment to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/treatments/{id}")
    @Timed
    public ResponseEntity<Void> deleteTreatment(@PathVariable Long id) {
        log.debug("REST request to delete Treatment : {}", id);
        treatmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
