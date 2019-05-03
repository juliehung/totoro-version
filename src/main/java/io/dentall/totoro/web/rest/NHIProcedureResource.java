package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NHIProcedure;
import io.dentall.totoro.repository.NHIProcedureRepository;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
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
 * REST controller for managing NHIProcedure.
 */
@RestController
@RequestMapping("/api")
public class NHIProcedureResource {

    private final Logger log = LoggerFactory.getLogger(NHIProcedureResource.class);

    private static final String ENTITY_NAME = "nhiProcedure";

    private final NHIProcedureRepository nhiProcedureRepository;

    public NHIProcedureResource(NHIProcedureRepository nhiProcedureRepository) {
        this.nhiProcedureRepository = nhiProcedureRepository;
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
    public ResponseEntity<NHIProcedure> createNHIProcedure(@Valid @RequestBody NHIProcedure nhiProcedure) throws URISyntaxException {
        log.debug("REST request to save NHIProcedure : {}", nhiProcedure);
        if (nhiProcedure.getId() != null) {
            throw new BadRequestAlertException("A new nhiProcedure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NHIProcedure result = nhiProcedureRepository.save(nhiProcedure);
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
    public ResponseEntity<NHIProcedure> updateNHIProcedure(@Valid @RequestBody NHIProcedure nhiProcedure) throws URISyntaxException {
        log.debug("REST request to update NHIProcedure : {}", nhiProcedure);
        if (nhiProcedure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NHIProcedure result = nhiProcedureRepository.save(nhiProcedure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiProcedure.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-procedures : get all the nhiProcedures.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nhiProcedures in body
     */
    @GetMapping("/nhi-procedures")
    @Timed
    public ResponseEntity<List<NHIProcedure>> getAllNHIProcedures(Pageable pageable) {
        log.debug("REST request to get a page of NHIProcedures");
        Page<NHIProcedure> page = nhiProcedureRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nhi-procedures");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /nhi-procedures/:id : get the "id" nhiProcedure.
     *
     * @param id the id of the nhiProcedure to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiProcedure, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-procedures/{id}")
    @Timed
    public ResponseEntity<NHIProcedure> getNHIProcedure(@PathVariable Long id) {
        log.debug("REST request to get NHIProcedure : {}", id);
        Optional<NHIProcedure> nhiProcedure = nhiProcedureRepository.findById(id);
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
    public ResponseEntity<Void> deleteNHIProcedure(@PathVariable Long id) {
        log.debug("REST request to delete NHIProcedure : {}", id);

        nhiProcedureRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
