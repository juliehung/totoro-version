package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.TreatmentPlan;
import io.dentall.totoro.service.TreatmentPlanService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.service.dto.TreatmentPlanCriteria;
import io.dentall.totoro.service.TreatmentPlanQueryService;
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
 * REST controller for managing TreatmentPlan.
 */
@RestController
@RequestMapping("/api")
public class TreatmentPlanResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentPlanResource.class);

    private static final String ENTITY_NAME = "treatmentPlan";

    private final TreatmentPlanService treatmentPlanService;

    private final TreatmentPlanQueryService treatmentPlanQueryService;

    public TreatmentPlanResource(TreatmentPlanService treatmentPlanService, TreatmentPlanQueryService treatmentPlanQueryService) {
        this.treatmentPlanService = treatmentPlanService;
        this.treatmentPlanQueryService = treatmentPlanQueryService;
    }

    /**
     * POST  /treatment-plans : Create a new treatmentPlan.
     *
     * @param treatmentPlan the treatmentPlan to create
     * @return the ResponseEntity with status 201 (Created) and with body the new treatmentPlan, or with status 400 (Bad Request) if the treatmentPlan has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/treatment-plans")
    @Timed
    public ResponseEntity<TreatmentPlan> createTreatmentPlan(@Valid @RequestBody TreatmentPlan treatmentPlan) throws URISyntaxException {
        log.debug("REST request to save TreatmentPlan : {}", treatmentPlan);
        if (treatmentPlan.getId() != null) {
            throw new BadRequestAlertException("A new treatmentPlan cannot already have an ID", ENTITY_NAME, "idexists");
        }

        TreatmentPlan result = treatmentPlanService.save(treatmentPlan);

        return ResponseEntity.created(new URI("/api/treatment-plans/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /treatment-plans : Updates an existing treatmentPlan.
     *
     * @param treatmentPlan the treatmentPlan to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated treatmentPlan,
     * or with status 400 (Bad Request) if the treatmentPlan is not valid,
     * or with status 500 (Internal Server Error) if the treatmentPlan couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/treatment-plans")
    @Timed
    public ResponseEntity<TreatmentPlan> updateTreatmentPlan(@Valid @RequestBody TreatmentPlan treatmentPlan) throws URISyntaxException {
        log.debug("REST request to update TreatmentPlan : {}", treatmentPlan);
        if (treatmentPlan.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        TreatmentPlan result = treatmentPlanService.update(treatmentPlan);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, treatmentPlan.getId().toString()))
            .body(result);
    }

    /**
     * GET  /treatment-plans : get all the treatmentPlans.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of treatmentPlans in body
     */
    @GetMapping("/treatment-plans")
    @Timed
    public ResponseEntity<List<TreatmentPlan>> getAllTreatmentPlans(TreatmentPlanCriteria criteria) {
        log.debug("REST request to get TreatmentPlans by criteria: {}", criteria);
        List<TreatmentPlan> entityList = treatmentPlanQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /treatment-plans/count : count all the treatmentPlans.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/treatment-plans/count")
    @Timed
    public ResponseEntity<Long> countTreatmentPlans(TreatmentPlanCriteria criteria) {
        log.debug("REST request to count TreatmentPlans by criteria: {}", criteria);
        return ResponseEntity.ok().body(treatmentPlanQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /treatment-plans/:id : get the "id" treatmentPlan.
     *
     * @param id the id of the treatmentPlan to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the treatmentPlan, or with status 404 (Not Found)
     */
    @GetMapping("/treatment-plans/{id}")
    @Timed
    public ResponseEntity<TreatmentPlan> getTreatmentPlan(@PathVariable Long id) {
        log.debug("REST request to get TreatmentPlan : {}", id);
        Optional<TreatmentPlan> treatmentPlan = treatmentPlanService.findOne(id);
        return ResponseUtil.wrapOrNotFound(treatmentPlan);
    }

    /**
     * DELETE  /treatment-plans/:id : delete the "id" treatmentPlan.
     *
     * @param id the id of the treatmentPlan to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/treatment-plans/{id}")
    @Timed
    public ResponseEntity<Void> deleteTreatmentPlan(@PathVariable Long id) {
        log.debug("REST request to delete TreatmentPlan : {}", id);
        treatmentPlanService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
