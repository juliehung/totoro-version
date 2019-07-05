package io.dentall.totoro.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.dentall.totoro.domain.Esign;
import io.dentall.totoro.service.EsignService;
import io.dentall.totoro.web.rest.errors.BadRequestAlertException;
import io.dentall.totoro.web.rest.util.HeaderUtil;
import io.dentall.totoro.service.dto.EsignCriteria;
import io.dentall.totoro.service.EsignQueryService;
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
 * REST controller for managing Esign.
 */
@RestController
@RequestMapping("/api")
public class EsignResource {

    private final Logger log = LoggerFactory.getLogger(EsignResource.class);

    private static final String ENTITY_NAME = "esign";

    private final EsignService esignService;

    private final EsignQueryService esignQueryService;

    public EsignResource(EsignService esignService, EsignQueryService esignQueryService) {
        this.esignService = esignService;
        this.esignQueryService = esignQueryService;
    }

    /**
     * POST  /esigns : Create a new esign.
     *
     * @param esign the esign to create
     * @return the ResponseEntity with status 201 (Created) and with body the new esign, or with status 400 (Bad Request) if the esign has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/esigns")
    @Timed
    public ResponseEntity<Esign> createEsign(@Valid @RequestBody Esign esign) throws URISyntaxException {
        log.debug("REST request to save Esign : {}", esign);
        if (esign.getId() != null) {
            throw new BadRequestAlertException("A new esign cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Esign result = esignService.save(esign);
        return ResponseEntity.created(new URI("/api/esigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /esigns : Updates an existing esign.
     *
     * @param esign the esign to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated esign,
     * or with status 400 (Bad Request) if the esign is not valid,
     * or with status 500 (Internal Server Error) if the esign couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/esigns")
    @Timed
    public ResponseEntity<Esign> updateEsign(@Valid @RequestBody Esign esign) throws URISyntaxException {
        log.debug("REST request to update Esign : {}", esign);
        if (esign.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Esign result = esignService.save(esign);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, esign.getId().toString()))
            .body(result);
    }

    /**
     * GET  /esigns : get all the esigns.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of esigns in body
     */
    @GetMapping("/esigns")
    @Timed
    public ResponseEntity<List<Esign>> getAllEsigns(EsignCriteria criteria) {
        log.debug("REST request to get Esigns by criteria: {}", criteria);
        List<Esign> entityList = esignQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
    * GET  /esigns/count : count all the esigns.
    *
    * @param criteria the criterias which the requested entities should match
    * @return the ResponseEntity with status 200 (OK) and the count in body
    */
    @GetMapping("/esigns/count")
    @Timed
    public ResponseEntity<Long> countEsigns(EsignCriteria criteria) {
        log.debug("REST request to count Esigns by criteria: {}", criteria);
        return ResponseEntity.ok().body(esignQueryService.countByCriteria(criteria));
    }

    /**
     * GET  /esigns/:id : get the "id" esign.
     *
     * @param id the id of the esign to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the esign, or with status 404 (Not Found)
     */
    @GetMapping("/esigns/{id}")
    @Timed
    public ResponseEntity<Esign> getEsign(@PathVariable Long id) {
        log.debug("REST request to get Esign : {}", id);
        Optional<Esign> esign = esignService.findOne(id);
        return ResponseUtil.wrapOrNotFound(esign);
    }

    /**
     * DELETE  /esigns/:id : delete the "id" esign.
     *
     * @param id the id of the esign to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/esigns/{id}")
    @Timed
    public ResponseEntity<Void> deleteEsign(@PathVariable Long id) {
        log.debug("REST request to delete Esign : {}", id);
        esignService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
