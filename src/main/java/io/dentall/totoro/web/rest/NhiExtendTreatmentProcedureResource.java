package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.NhiExtendTreatmentProcedure;
import io.dentall.totoro.service.NhiExtendTreatmentProcedureService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * REST controller for managing NhiExtendTreatmentProcedure.
 */
@RestController
@RequestMapping("/api")
public class NhiExtendTreatmentProcedureResource {

    private final Logger log = LoggerFactory.getLogger(NhiExtendTreatmentProcedureResource.class);

    private static final String ENTITY_NAME = "nhiExtendTreatmentProcedure";

    private final NhiExtendTreatmentProcedureService nhiExtendTreatmentProcedureService;

    public NhiExtendTreatmentProcedureResource(NhiExtendTreatmentProcedureService nhiExtendTreatmentProcedureService) {
        this.nhiExtendTreatmentProcedureService = nhiExtendTreatmentProcedureService;
    }

    /**
     * POST  /nhi-extend-treatment-procedures : Create a new nhiExtendTreatmentProcedure.
     *
     * @param nhiExtendTreatmentProcedure the nhiExtendTreatmentProcedure to create
     * @return the ResponseEntity with status 201 (Created) and with body the new nhiExtendTreatmentProcedure, or with status 400 (Bad Request) if the nhiExtendTreatmentProcedure has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/nhi-extend-treatment-procedures")
    @Timed
    public ResponseEntity<NhiExtendTreatmentProcedure> createNhiExtendTreatmentProcedure(@Valid @RequestBody NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) throws URISyntaxException {
        log.debug("REST request to save NhiExtendTreatmentProcedure : {}", nhiExtendTreatmentProcedure);
        if (nhiExtendTreatmentProcedure.getId() != null) {
            throw new BadRequestAlertException("A new nhiExtendTreatmentProcedure cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NhiExtendTreatmentProcedure result = nhiExtendTreatmentProcedureService.save(nhiExtendTreatmentProcedure);
        return ResponseEntity.created(new URI("/api/nhi-extend-treatment-procedures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nhi-extend-treatment-procedures : Updates an existing nhiExtendTreatmentProcedure.
     *
     * @param nhiExtendTreatmentProcedure the nhiExtendTreatmentProcedure to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated nhiExtendTreatmentProcedure,
     * or with status 400 (Bad Request) if the nhiExtendTreatmentProcedure is not valid,
     * or with status 500 (Internal Server Error) if the nhiExtendTreatmentProcedure couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/nhi-extend-treatment-procedures")
    @Timed
    public ResponseEntity<NhiExtendTreatmentProcedure> updateNhiExtendTreatmentProcedure(@Valid @RequestBody NhiExtendTreatmentProcedure nhiExtendTreatmentProcedure) throws URISyntaxException {
        log.debug("REST request to update NhiExtendTreatmentProcedure : {}", nhiExtendTreatmentProcedure);
        if (nhiExtendTreatmentProcedure.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NhiExtendTreatmentProcedure result = nhiExtendTreatmentProcedureService.update(nhiExtendTreatmentProcedure);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, nhiExtendTreatmentProcedure.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nhi-extend-treatment-procedures : get all the nhiExtendTreatmentProcedure.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of nhiExtendTreatmentProcedure in body
     */
    @GetMapping("/nhi-extend-treatment-procedures")
    @Timed
    public ResponseEntity<List<NhiExtendTreatmentProcedure>> getAllNhiExtendTreatmentProcedures(Pageable pageable) {
        log.debug("REST request to get a page of NhiExtendTreatmentProcedure");
        Page<NhiExtendTreatmentProcedure> page = nhiExtendTreatmentProcedureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nhi-extend-treatment-procedures");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /nhi-extend-treatment-procedures/:id : get the "id" nhiExtendTreatmentProcedure.
     *
     * @param id the id of the nhiExtendTreatmentProcedure to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the nhiExtendTreatmentProcedure, or with status 404 (Not Found)
     */
    @GetMapping("/nhi-extend-treatment-procedures/{id}")
    @Timed
    public ResponseEntity<NhiExtendTreatmentProcedure> getNhiExtendTreatmentProcedure(@PathVariable Long id) {
        log.debug("REST request to get NhiExtendTreatmentProcedure : {}", id);
        Optional<NhiExtendTreatmentProcedure> nhiExtendTreatmentProcedure = nhiExtendTreatmentProcedureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nhiExtendTreatmentProcedure);
    }

    /**
     * DELETE  /nhi-extend-treatment-procedures/:id : delete the "id" nhiExtendTreatmentProcedure.
     *
     * @param id the id of the nhiExtendTreatmentProcedure to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/nhi-extend-treatment-procedures/{id}")
    @Timed
    public ResponseEntity<Void> deleteNhiExtendTreatmentProcedure(@PathVariable Long id) {
        log.debug("REST request to delete NhiExtendTreatmentProcedure : {}", id);

        nhiExtendTreatmentProcedureService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

}
