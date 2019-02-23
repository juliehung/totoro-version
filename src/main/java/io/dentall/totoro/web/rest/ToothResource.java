package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Tooth;
import io.dentall.totoro.service.ToothService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.web.rest.util.PaginationUtil;
import io.dentall.totoro.service.dto.ToothCriteria;
import io.dentall.totoro.service.ToothQueryService;
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
 * REST controller for managing Tooth.
 */
@RestController
@RequestMapping("/api")
public class ToothResource {

    private final Logger log = LoggerFactory.getLogger(ToothResource.class);

    private static final String ENTITY_NAME = "tooth";

    private final ToothService toothService;

    private final ToothQueryService toothQueryService;

    public ToothResource(ToothService toothService, ToothQueryService toothQueryService) {
        this.toothService = toothService;
        this.toothQueryService = toothQueryService;
    }

    /**
     * POST  /teeth : Create a new tooth.
     *
     * @param tooth the tooth to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tooth, or with status 400 (Bad Request) if the tooth has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/teeth")
    @Timed
    public ResponseEntity<Tooth> createTooth(@Valid @RequestBody Tooth tooth) throws URISyntaxException {
        log.debug("REST request to save Tooth : {}", tooth);
        if (tooth.getId() != null) {
            throw new BadRequestAlertException("A new tooth cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tooth result = toothService.save(tooth);
        return ResponseEntity.created(new URI("/api/teeth/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /teeth : Updates an existing tooth.
     *
     * @param tooth the tooth to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tooth,
     * or with status 400 (Bad Request) if the tooth is not valid,
     * or with status 500 (Internal Server Error) if the tooth couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/teeth")
    @Timed
    public ResponseEntity<Tooth> updateTooth(@Valid @RequestBody Tooth tooth) throws URISyntaxException {
        log.debug("REST request to update Tooth : {}", tooth);
        if (tooth.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tooth result = toothService.update(tooth);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tooth.getId().toString()))
            .body(result);
    }

    /**
     * GET  /teeth : get all the teeth.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of teeth in body
     */
    @GetMapping("/teeth")
    @Timed
    public ResponseEntity<List<Tooth>> getAllTeeth(ToothCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Teeth by criteria: {}", criteria);
        Page<Tooth> page = toothQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teeth");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * GET  /teeth/count : count all the teeth.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/teeth/count")
    @Timed
    public ResponseEntity<Long> countTeeth(ToothCriteria criteria) {
        log.debug("REST request to count Teeth by criteria: {}", criteria);
        return ResponseEntity.ok().body(toothQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /teeth/:id : get the "id" tooth.
     *
     * @param id the id of the tooth to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tooth, or with status 404 (Not Found)
     */
    @GetMapping("/teeth/{id}")
    @Timed
    public ResponseEntity<Tooth> getTooth(@PathVariable Long id) {
        log.debug("REST request to get Tooth : {}", id);
        Optional<Tooth> tooth = toothService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tooth);
    }

    /**
     * DELETE  /teeth/:id : delete the "id" tooth.
     *
     * @param id the id of the tooth to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/teeth/{id}")
    @Timed
    public ResponseEntity<Void> deleteTooth(@PathVariable Long id) {
        log.debug("REST request to delete Tooth : {}", id);
        toothService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
