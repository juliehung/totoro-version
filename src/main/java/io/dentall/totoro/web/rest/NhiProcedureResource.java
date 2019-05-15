package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NhiProcedure;
import io.dentall.totoro.service.NhiProcedureService;
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
 * REST controller for managing NhiProcedure.
 */
@RestController
@RequestMapping("/api")
public class NhiProcedureResource {

    private final Logger log = LoggerFactory.getLogger(NhiProcedureResource.class);

    private static final String ENTITY_NAME = "nhiProcedure";

    private final NhiProcedureService nhiProcedureService;

    public NhiProcedureResource(NhiProcedureService nhiProcedureService) {
        this.nhiProcedureService = nhiProcedureService;
    }

    /**
     * POST  /nhi-procedures : Create a new nhiProcedure.
     *
     * @param nhiProcedure the nhiProcedure to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiProcedure, or with status 400 (Bad Request) if the nhiProcedure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-procedures")
    @Timed
    public ResponseEntity<NhiProcedure> createNhiProcedure(@Valid @RequestBody NhiProcedure nhiProcedure) throws URISyntaxException {
        log.debug("REST request to save NhiProcedure : {}", nhiProcedure);
        if (nhiProcedure.getId() != null) {
            throw new BadRequestAlertException("A new nhiProcedure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhiProcedure result = nhiProcedureService.save(nhiProcedure);
        return ResponseEntity.created(new URI("/api/nhi-procedures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-procedures : Updates an existing nhiProcedure.
     *
     * @param nhiProcedure the nhiProcedure to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiProcedure,
     * or with status 400 (Bad Request) if the nhiProcedure is not valid,
     * or with status 500 (Internal Server Error) if the nhiProcedure couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-procedures")
    @Timed
    public ResponseEntity<NhiProcedure> updateNhiProcedure(@Valid @RequestBody NhiProcedure nhiProcedure) throws URISyntaxException {
        log.debug("REST request to update NhiProcedure : {}", nhiProcedure);
        if (nhiProcedure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhiProcedure result = nhiProcedureService.update(nhiProcedure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiProcedure.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-procedures : get all the nhiProcedures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of nhiProcedures in body
     */
    @GetMapping("/nhi-procedures")
    @Timed
    public List<NhiProcedure> getAllNhiProcedures() {
        log.debug("REST request to get all NhiProcedures");
        return nhiProcedureService.findAll();
    }

    /**
     * GET  /nhi-procedures/:id : get the "id" nhiProcedure.
     *
     * @param id the id of the nhiProcedure to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiProcedure, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-procedures/{id}")
    @Timed
    public ResponseEntity<NhiProcedure> getNhiProcedure(@PathVariable Long id) {
        log.debug("REST request to get NhiProcedure : {}", id);
        Optional<NhiProcedure> nhiProcedure = nhiProcedureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhiProcedure);
    }

    /**
     * DELETE  /nhi-procedures/:id : delete the "id" nhiProcedure.
     *
     * @param id the id of the nhiProcedure to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-procedures/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhiProcedure(@PathVariable Long id) {
        log.debug("REST request to delete NhiProcedure : {}", id);
        nhiProcedureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
