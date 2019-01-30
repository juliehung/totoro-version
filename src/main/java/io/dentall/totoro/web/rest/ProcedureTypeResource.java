package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.ProcedureType;
import io.dentall.totoro.repository.ProcedureTypeRepository;
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
 * REST controller for managing ProcedureType.
 */
@RestController
@RequestMapping("/api")
public class ProcedureTypeResource {

    private final Logger log = LoggerFactory.getLogger(ProcedureTypeResource.class);

    private static final String ENTITY_NAME = "procedureType";

    private final ProcedureTypeRepository procedureTypeRepository;

    public ProcedureTypeResource(ProcedureTypeRepository procedureTypeRepository) {
        this.procedureTypeRepository = procedureTypeRepository;
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
        ProcedureType result = procedureTypeRepository.save(procedureType);
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
        ProcedureType result = procedureTypeRepository.save(procedureType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, procedureType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /procedure-types : get all the procedureTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of procedureTypes in body
     */
    @GetMapping("/procedure-types")
    @Timed
    public List<ProcedureType> getAllProcedureTypes() {
        log.debug("REST request to get all ProcedureTypes");
        return procedureTypeRepository.findAll();
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
        Optional<ProcedureType> procedureType = procedureTypeRepository.findById(id);
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

        procedureTypeRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
