package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Procedure;
import io.dentall.totoro.service.ProcedureService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.service.dto.ProcedureCriteria;
import io.dentall.totoro.service.ProcedureQueryService;
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
 * REST controller for managing Procedure.
 */
@RestController
@RequestMapping("/api")
public class ProcedureResource {

    private final Logger log = LoggerFactory.getLogger(ProcedureResource.class);

    private static final String ENTITY_NAME = "procedure";

    private final ProcedureService procedureService;

    private final ProcedureQueryService procedureQueryService;

    public ProcedureResource(ProcedureService procedureService, ProcedureQueryService procedureQueryService) {
        this.procedureService = procedureService;
        this.procedureQueryService = procedureQueryService;
    }

    /**
     * POST  /procedures : Create a new procedure.
     *
     * @param procedure the procedure to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procedure, or with status 400 (Bad Request) if the procedure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/procedures")
    @Timed
    public ResponseEntity<Procedure> createProcedure(@Valid @RequestBody Procedure procedure) throws URISyntaxException {
        log.debug("REST request to save Procedure : {}", procedure);
        if (procedure.getId() != null) {
            throw new BadRequestAlertException("A new procedure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Procedure result = procedureService.save(procedure);
        return ResponseEntity.created(new URI("/api/procedures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /procedures : Updates an existing procedure.
     *
     * @param procedure the procedure to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated procedure,
     * or with status 400 (Bad Request) if the procedure is not valid,
     * or with status 500 (Internal Server Error) if the procedure couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/procedures")
    @Timed
    public ResponseEntity<Procedure> updateProcedure(@Valid @RequestBody Procedure procedure) throws URISyntaxException {
        log.debug("REST request to update Procedure : {}", procedure);
        if (procedure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Procedure result = procedureService.save(procedure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, procedure.getId().toString()))
            .body(result);
    }

    /**
     * GET  /procedures : get all the procedures.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of procedures in body
     */
    @GetMapping("/procedures")
    @Timed
    public ResponseEntity<List<Procedure>> getAllProcedures(ProcedureCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Procedures by criteria: {}", criteria);
        Page<Procedure> page = procedureQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/procedures");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /procedures/count : count all the procedures.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/procedures/count")
    @Timed
    public ResponseEntity<Long> countProcedures(ProcedureCriteria criteria) {
        log.debug("REST request to count Procedures by criteria: {}", criteria);
        return ResponseEntity.ok().body(procedureQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /procedures/:id : get the "id" procedure.
     *
     * @param id the id of the procedure to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the procedure, or with status 404 (Not Found)
     */
    @GetMapping("/procedures/{id}")
    @Timed
    public ResponseEntity<Procedure> getProcedure(@PathVariable Long id) {
        log.debug("REST request to get Procedure : {}", id);
        Optional<Procedure> procedure = procedureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(procedure);
    }

    /**
     * DELETE  /procedures/:id : delete the "id" procedure.
     *
     * @param id the id of the procedure to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/procedures/{id}")
    @Timed
    public ResponseEntity<Void> deleteProcedure(@PathVariable Long id) {
        log.debug("REST request to delete Procedure : {}", id);
        procedureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
