package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.TreatmentProcedure;
import io.dentall.totoro.domain.TreatmentTask;
import io.dentall.totoro.domain.enumeration.TreatmentProcedureStatus;
import io.dentall.totoro.repository.TreatmentTaskRepository;
import io.dentall.totoro.service.ToothService;
import io.dentall.totoro.service.TreatmentProcedureService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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

    private final TreatmentTaskRepository treatmentTaskRepository;

    private final TreatmentProcedureService treatmentProcedureService;

    private final ToothService toothService;

    public TreatmentTaskResource(TreatmentTaskRepository treatmentTaskRepository, TreatmentProcedureService treatmentProcedureService, ToothService toothService) {
        this.treatmentTaskRepository = treatmentTaskRepository;
        this.treatmentProcedureService = treatmentProcedureService;
        this.toothService = toothService;
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

        TreatmentTask result = treatmentTaskRepository.save(treatmentTask);

        // add default TreatmentProcedure of TreatmentTask
        result.getTreatmentProcedures().add(treatmentProcedureService.save(new TreatmentProcedure().status(TreatmentProcedureStatus.HIDE).treatmentTask(result)));

        // create Tooth object relationship with TreatmentTask
        treatmentTask.getTeeth().forEach(tooth -> toothService.save(tooth.treatmentTask(result)));

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

        // update Tooth object
        treatmentTask.getTeeth().forEach(toothService::update);
        TreatmentTask result = treatmentTaskRepository.save(treatmentTask);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, treatmentTask.getId().toString()))
            .body(result);
    }

    /**
     * GET  /treatment-tasks : get all the treatmentTasks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of treatmentTasks in body
     */
    @GetMapping("/treatment-tasks")
    @Timed
    public ResponseEntity<List<TreatmentTask>> getAllTreatmentTasks(Pageable pageable) {
        log.debug("REST request to get a page of TreatmentTasks");
        Page<TreatmentTask> page = treatmentTaskRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/treatment-tasks");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
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
        Optional<TreatmentTask> treatmentTask = treatmentTaskRepository.findById(id);
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

        treatmentTaskRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
