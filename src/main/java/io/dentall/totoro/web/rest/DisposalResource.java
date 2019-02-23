package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Disposal;
import io.dentall.totoro.service.DisposalService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.service.dto.DisposalCriteria;
import io.dentall.totoro.service.DisposalQueryService;
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
 * REST controller for managing Disposal.
 */
@RestController
@RequestMapping("/api")
public class DisposalResource {

    private final Logger log = LoggerFactory.getLogger(DisposalResource.class);

    private static final String ENTITY_NAME = "disposal";

    private final DisposalService disposalService;

    private final DisposalQueryService disposalQueryService;

    public DisposalResource(DisposalService disposalService, DisposalQueryService disposalQueryService) {
        this.disposalService = disposalService;
        this.disposalQueryService = disposalQueryService;
    }

    /**
     * POST  /disposals : Create a new disposal.
     *
     * @param disposal the disposal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new disposal, or with status 400 (Bad Request) if the disposal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/disposals")
    @Timed
    public ResponseEntity<Disposal> createDisposal(@Valid @RequestBody Disposal disposal) throws URISyntaxException {
        log.debug("REST request to save Disposal : {}", disposal);
        if (disposal.getId() != null) {
            throw new BadRequestAlertException("A new disposal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Disposal result = disposalService.save(disposal);
        return ResponseEntity.created(new URI("/api/disposals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /disposals : Updates an existing disposal.
     *
     * @param disposal the disposal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated disposal,
     * or with status 400 (Bad Request) if the disposal is not valid,
     * or with status 500 (Internal Server Error) if the disposal couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/disposals")
    @Timed
    public ResponseEntity<Disposal> updateDisposal(@Valid @RequestBody Disposal disposal) throws URISyntaxException {
        log.debug("REST request to update Disposal : {}", disposal);
        if (disposal.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Disposal result = disposalService.update(disposal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, disposal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /disposals : get all the disposals.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of disposals in body
     */
    @GetMapping("/disposals")
    @Timed
    public ResponseEntity<List<Disposal>> getAllDisposals(DisposalCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Disposals by criteria: {}", criteria);
        Page<Disposal> page = disposalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/disposals");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /disposals/count : count all the disposals.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/disposals/count")
    @Timed
    public ResponseEntity<Long> countDisposals(DisposalCriteria criteria) {
        log.debug("REST request to count Disposals by criteria: {}", criteria);
        return ResponseEntity.ok().body(disposalQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /disposals/:id : get the "id" disposal.
     *
     * @param id the id of the disposal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the disposal, or with status 404 (Not Found)
     */
    @GetMapping("/disposals/{id}")
    @Timed
    public ResponseEntity<Disposal> getDisposal(@PathVariable Long id) {
        log.debug("REST request to get Disposal : {}", id);
        Optional<Disposal> disposal = disposalService.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(disposal);
    }

    /**
     * DELETE  /disposals/:id : delete the "id" disposal.
     *
     * @param id the id of the disposal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/disposals/{id}")
    @Timed
    public ResponseEntity<Void> deleteDisposal(@PathVariable Long id) {
        log.debug("REST request to delete Disposal : {}", id);
        disposalService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
