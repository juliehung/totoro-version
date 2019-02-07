package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.ProcedureType;
import io.dentall.totoro.service.ProcedureTypeService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.service.dto.ProcedureTypeCriteria;
import io.dentall.totoro.service.ProcedureTypeQueryService;
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
 * REST controller for managing ProcedureType.
 */
@RestController
@RequestMapping("/api")
public class ProcedureTypeResource {

    private final Logger log = LoggerFactory.getLogger(ProcedureTypeResource.class);

    private static final String ENTITY_NAME = "procedureType";

    private final ProcedureTypeService procedureTypeService;

    private final ProcedureTypeQueryService procedureTypeQueryService;

    public ProcedureTypeResource(ProcedureTypeService procedureTypeService, ProcedureTypeQueryService procedureTypeQueryService) {
        this.procedureTypeService = procedureTypeService;
        this.procedureTypeQueryService = procedureTypeQueryService;
    }

    /**
     * POST  /procedure-types : Create a new procedureType.
     *
     * @param procedureType the procedureType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procedureType, or with status 400 (Bad Request) if the procedureType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/procedure-types")
    @Timed
    public ResponseEntity<ProcedureType> createProcedureType(@Valid @RequestBody ProcedureType procedureType) throws URISyntaxException {
        log.debug("REST request to save ProcedureType : {}", procedureType);
        if (procedureType.getId() != null) {
            throw new BadRequestAlertException("A new procedureType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcedureType result = procedureTypeService.save(procedureType);
        return ResponseEntity.created(new URI("/api/procedure-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /procedure-types : Updates an existing procedureType.
     *
     * @param procedureType the procedureType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated procedureType,
     * or with status 400 (Bad Request) if the procedureType is not valid,
     * or with status 500 (Internal Server Error) if the procedureType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/procedure-types")
    @Timed
    public ResponseEntity<ProcedureType> updateProcedureType(@Valid @RequestBody ProcedureType procedureType) throws URISyntaxException {
        log.debug("REST request to update ProcedureType : {}", procedureType);
        if (procedureType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ProcedureType result = procedureTypeService.save(procedureType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, procedureType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /procedure-types : get all the procedureTypes.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of procedureTypes in body
     */
    @GetMapping("/procedure-types")
    @Timed
    public ResponseEntity<List<ProcedureType>> getAllProcedureTypes(ProcedureTypeCriteria criteria) {
        log.debug("REST request to get ProcedureTypes by criteria: {}", criteria);
        List<ProcedureType> entityList = procedureTypeQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /procedure-types/count : count all the procedureTypes.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/procedure-types/count")
    @Timed
    public ResponseEntity<Long> countProcedureTypes(ProcedureTypeCriteria criteria) {
        log.debug("REST request to count ProcedureTypes by criteria: {}", criteria);
        return ResponseEntity.ok().body(procedureTypeQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /procedure-types/:id : get the "id" procedureType.
     *
     * @param id the id of the procedureType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the procedureType, or with status 404 (Not Found)
     */
    @GetMapping("/procedure-types/{id}")
    @Timed
    public ResponseEntity<ProcedureType> getProcedureType(@PathVariable Long id) {
        log.debug("REST request to get ProcedureType : {}", id);
        Optional<ProcedureType> procedureType = procedureTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(procedureType);
    }

    /**
     * DELETE  /procedure-types/:id : delete the "id" procedureType.
     *
     * @param id the id of the procedureType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/procedure-types/{id}")
    @Timed
    public ResponseEntity<Void> deleteProcedureType(@PathVariable Long id) {
        log.debug("REST request to delete ProcedureType : {}", id);
        procedureTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
