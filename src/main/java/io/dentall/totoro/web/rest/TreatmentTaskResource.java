package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.domain.TreatmentTask;
import io.dentall.totoro.domain.enumeration.TreatmentProcedureStatus;
import io.dentall.totoro.service.TreatmentProcedureService;
import io.dentall.totoro.service.TreatmentTaskService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.service.dto.TreatmentTaskCriteria;
import io.dentall.totoro.service.TreatmentTaskQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing TreatmentTask.
 */
@RestController
@RequestMapping("/api")
public class TreatmentTaskResource {

    private final Logger log = LoggerFactory.getLogger(TreatmentTaskResource.class);

    private static final String ENTITY_NAME = "treatmentTask";

    private final TreatmentTaskService treatmentTaskService;

    private final TreatmentTaskQueryService treatmentTaskQueryService;

    private final TreatmentProcedureService treatmentProcedureService;

    public TreatmentTaskResource(TreatmentTaskService treatmentTaskService, TreatmentTaskQueryService treatmentTaskQueryService, TreatmentProcedureService treatmentProcedureService) {
        this.treatmentTaskService = treatmentTaskService;
        this.treatmentTaskQueryService = treatmentTaskQueryService;
        this.treatmentProcedureService = treatmentProcedureService;
    }

    /**
     * POST  /treatment-tasks : Create a new treatmentTask.
     *
     * @param treatmentTask the treatmentTask to create
     * @return the ResponseEntity with status 201 (Created) and with body the new treatmentTask, or with status 400 (Bad Request) if the treatmentTask has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/treatment-tasks")
    @Timed
    public ResponseEntity<TreatmentTask> createTreatmentTask(@RequestBody TreatmentTask treatmentTask) throws URISyntaxException {
        log.debug("REST request to save TreatmentTask : {}", treatmentTask);
        if (treatmentTask.getId() != null) {
            throw new BadRequestAlertException("A new treatmentTask cannot already have an ID", ENTITY_NAME, "idexists");
        }

        TreatmentTask result = treatmentTaskService.save(treatmentTask);

        // add default TreatmentProcedure of TreatmentTask
        result.getTreatmentProcedures().add(treatmentProcedureService.save(new TreatmentProcedure().status(TreatmentProcedureStatus.HIDE).treatmentTask(result)));
        result.setTreatmentProcedures(result.getTreatmentProcedures());

        return ResponseEntity.created(new URI("/api/treatment-tasks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /treatment-tasks : Updates an existing treatmentTask.
     *
     * @param treatmentTask the treatmentTask to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated treatmentTask,
     * or with status 400 (Bad Request) if the treatmentTask is not valid,
     * or with status 500 (Internal Server Error) if the treatmentTask couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/treatment-tasks")
    @Timed
    public ResponseEntity<TreatmentTask> updateTreatmentTask(@RequestBody TreatmentTask treatmentTask) throws URISyntaxException {
        log.debug("REST request to update TreatmentTask : {}", treatmentTask);
        if (treatmentTask.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TreatmentTask result = treatmentTaskService.save(treatmentTask);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, treatmentTask.getId().toString()))
            .body(result);
    }

    /**
     * GET  /treatment-tasks : get all the treatmentTasks.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of treatmentTasks in body
     */
    @GetMapping("/treatment-tasks")
    @Timed
    public ResponseEntity<List<TreatmentTask>> getAllTreatmentTasks(TreatmentTaskCriteria criteria, Pageable pageable) {
        log.debug("REST request to get TreatmentTasks by criteria: {}", criteria);
        Page<TreatmentTask> page = treatmentTaskQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/treatment-tasks");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /treatment-tasks/count : count all the treatmentTasks.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/treatment-tasks/count")
    @Timed
    public ResponseEntity<Long> countTreatmentTasks(TreatmentTaskCriteria criteria) {
        log.debug("REST request to count TreatmentTasks by criteria: {}", criteria);
        return ResponseEntity.ok().body(treatmentTaskQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /treatment-tasks/:id : get the "id" treatmentTask.
     *
     * @param id the id of the treatmentTask to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the treatmentTask, or with status 404 (Not Found)
     */
    @GetMapping("/treatment-tasks/{id}")
    @Timed
    public ResponseEntity<TreatmentTask> getTreatmentTask(@PathVariable Long id) {
        log.debug("REST request to get TreatmentTask : {}", id);
        Optional<TreatmentTask> treatmentTask = treatmentTaskService.findOne(id);
        return ResponseUtil.wrapOrNotFound(treatmentTask);
    }

    /**
     * DELETE  /treatment-tasks/:id : delete the "id" treatmentTask.
     *
     * @param id the id of the treatmentTask to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/treatment-tasks/{id}")
    @Timed
    public ResponseEntity<Void> deleteTreatmentTask(@PathVariable Long id) {
        log.debug("REST request to delete TreatmentTask : {}", id);
        treatmentTaskService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
